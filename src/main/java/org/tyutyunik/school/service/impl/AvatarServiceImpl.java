package org.tyutyunik.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tyutyunik.school.exceptions.FileDeleteException;
import org.tyutyunik.school.exceptions.FileNotFoundException;
import org.tyutyunik.school.exceptions.IsNotValidException;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Avatar;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.model.dto.AvatarDto;
import org.tyutyunik.school.repository.AvatarRepository;
import org.tyutyunik.school.repository.StudentRepository;
import org.tyutyunik.school.service.AvatarService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Path pathDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository,
                             @Value("${image.path}") Path pathDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.pathDir = pathDir;
    }

    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Override
    public long addAvatar(long studentId, MultipartFile multipartFile) {
        logger.info("[INFO] [AvatarService] Was invoked addAvatar");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(Avatar.class, studentId));
        if (multipartFile == null) {
            throw new IsNotValidException(AvatarService.class, studentId, "MultipartFile is null");
        }
        Path path = saveLocal(multipartFile);
        return saveBD(student, multipartFile, path);
    }

    @Override
    @Transactional(readOnly = true)
    // Avoid org.postgresql.util.PSQLException: Large Objects may not be used in auto-commit mode.
    public Avatar getAvatarFromDb(long studentId) {
        logger.info("[INFO] [AvatarService] Was invoked getAvatarFromDb()");
        return Optional.ofNullable(avatarRepository.findByStudentId(studentId))
                .orElseThrow(() -> new NotFoundException(AvatarService.class, studentId));
    }

    @Override
    @Transactional(readOnly = true)
    // Avoid org.postgresql.util.PSQLException: Large Objects may not be used in auto-commit mode.
    public AvatarDto getAvatarFromLocal(long studentId) {
        logger.info("[INFO] [AvatarService] Was invoked getAvatarFromLocal()");
        Avatar avatar = avatarRepository.findByStudentId(studentId);
        if (avatar == null) {
            throw new NotFoundException(AvatarService.class, studentId);
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(avatar.getFilePath()))) {
            return new AvatarDto(bufferedInputStream.readAllBytes(), MediaType.parseMediaType(avatar.getMediaType()));
        } catch (IOException e) {
            throw new IsNotValidException(AvatarService.class, studentId, "Something went wrong");
        }
    }

    private Path saveLocal(MultipartFile multipartFile) {
        logger.info("[INFO] [AvatarService] Was invoked saveLocal()");
        if (Files.notExists(pathDir)) {
            try {
                Files.createDirectories(pathDir);
            } catch (IOException e) {
                logger.error("[ERROR] [AvatarService] Files.createDirectories IOException");
                throw new RuntimeException(e);
            }
        }
        Path path = Path.of(pathDir.toString(), UUID.randomUUID() + getExtension(multipartFile));
        try {
            Files.write(path, multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    private String getExtension(MultipartFile multipartFile) {
        logger.info("[INFO] [AvatarService] Was invoked getExtension()");
        if (multipartFile.getOriginalFilename() == null) {
            throw new IsNotValidException(AvatarService.class, null, "File format");
        }
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
    }

    private long saveBD(Student student, MultipartFile multipartFile, Path path) {
        logger.info("[INFO] [AvatarService] Was invoked saveBD()");
        byte[] multipartFileSize;

        try {
            multipartFileSize = multipartFile.getBytes();
        } catch (IOException e) {
            throw new FileNotFoundException(AvatarService.class, path);
        }

        Avatar avatar = new Avatar(
                path.toString(),
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFileSize,
                student
        );

        Avatar avatarExisting;
        try {
            avatarExisting = avatarRepository.findByStudentId(student.getId());
        } catch (Exception e) {
            logger.error("[ERROR] [AvatarService] Trying to search in the database");
            throw new RuntimeException("[EXCEPTION] [AvatarService] Trying to search in the database");
        }

        if (avatarExisting != null) {
            try {
                Files.delete(Path.of(avatarExisting.getFilePath()));
            } catch (IOException e) {
                throw new FileDeleteException(AvatarService.class, student.getId(), avatarExisting.getFilePath());
            }
            avatar.setId(avatarExisting.getId());
        }

        return avatarRepository.save(avatar).getId();
    }

    @Override
    public Page<Avatar> getPage(Pageable pageable) {
        logger.info("[INFO] [AvatarService] Was invoked getPage()");
        return avatarRepository.findAll(pageable);
    }
}

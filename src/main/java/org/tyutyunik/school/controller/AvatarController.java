package org.tyutyunik.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tyutyunik.school.model.Avatar;
import org.tyutyunik.school.model.dto.AvatarDto;
import org.tyutyunik.school.service.AvatarService;

@RequestMapping("/avatar")
@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long uploadAvatar(@RequestParam long studentId,
                             @RequestBody MultipartFile multipartFile) {
        return avatarService.addAvatar(studentId, multipartFile);
    }

    @GetMapping(value = "/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam long studentId) {
        Avatar avatar = avatarService.getAvatarFromDb(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping(value = "/get/from-local")
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam long studentId) {
        AvatarDto dto = avatarService.getAvatarFromLocal(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(dto.getMediaType())
                .body(dto.getData());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Avatar>> getAvatars(@RequestParam Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(avatarService.getPage(pageable));
    }
}

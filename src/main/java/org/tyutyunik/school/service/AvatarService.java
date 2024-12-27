package org.tyutyunik.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.tyutyunik.school.model.Avatar;
import org.tyutyunik.school.model.dto.AvatarDto;

public interface AvatarService {
    /**
     * Create avatar
     */
    long addAvatar(long studentId, MultipartFile multipartFile);

    /**
     * Read avatar from database
     */
    Avatar getAvatarFromDb(long studentId);

    /**
     * Read avatar from local
     */
    AvatarDto getAvatarFromLocal(long studentId);

    /**
     * Read page of avatar
     */
    Page<Avatar> getPage(Pageable pageable);
}

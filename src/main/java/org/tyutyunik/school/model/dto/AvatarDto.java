package org.tyutyunik.school.model.dto;

import org.springframework.http.MediaType;

public class AvatarDto {
    private final byte[] data;
    private final MediaType mediaType;

    public AvatarDto(byte[] data, MediaType mediaType) {
        this.data = data;
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}

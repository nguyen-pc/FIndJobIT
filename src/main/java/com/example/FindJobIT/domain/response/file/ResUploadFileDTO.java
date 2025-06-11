package com.example.FindJobIT.domain.response.file;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
// @AllArgsConstructor
public class ResUploadFileDTO {

    private String fileName;
    private Instant uploadedAt;

    // Constructor với 2 đối số
    public ResUploadFileDTO(String fileName, Instant uploadedAt) {
        this.fileName = fileName;
        this.uploadedAt = uploadedAt;
    }
}

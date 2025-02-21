package com.danitherev.jjwt.model.dto.email.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

public record EmailFileDto(
        String[] toUser,
        String subject,
        String message,
        MultipartFile file
) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmailFileDto that = (EmailFileDto) o;
        return Objects.equals(subject, that.subject) && Objects.equals(message, that.message) && Objects.deepEquals(toUser, that.toUser) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(toUser), subject, message, file);
    }

    @Override
    public String toString() {
        return "EmailFileDto{" +
                "toUser=" + Arrays.toString(toUser) +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", file=" + file +
                '}';
    }
}

package com.danitherev.jjwt.model.dto.email.request;

import java.util.Arrays;
import java.util.Objects;

public record EmailDto(
        String[] toUser,
        String subject,
        String message
) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmailDto emailDto = (EmailDto) o;
        return Objects.equals(subject, emailDto.subject) && Objects.equals(message, emailDto.message) && Objects.deepEquals(toUser, emailDto.toUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(toUser), subject, message);
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "toUser=" + Arrays.toString(toUser) +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

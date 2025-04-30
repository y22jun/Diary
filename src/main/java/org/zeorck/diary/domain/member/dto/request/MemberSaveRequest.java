package org.zeorck.diary.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MemberSaveRequest(

        @NotNull(message = "이메일을 입력해 주세요.")
        @Email
        String email,

        @NotNull(message = "비밀번호를 입력해 주세요.")
        String password

) {
}

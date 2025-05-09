package org.zeorck.diary.domain.member.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberInfoResponse(

        String email,
        LocalDateTime createdAt

) {
}

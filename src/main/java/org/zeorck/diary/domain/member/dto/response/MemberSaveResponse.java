package org.zeorck.diary.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSaveResponse(

        Long memberId

) {
}

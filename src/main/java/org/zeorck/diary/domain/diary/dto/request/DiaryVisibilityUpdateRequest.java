package org.zeorck.diary.domain.diary.dto.request;

import lombok.Builder;

@Builder
public record DiaryVisibilityUpdateRequest(

        String visibility

) {
}

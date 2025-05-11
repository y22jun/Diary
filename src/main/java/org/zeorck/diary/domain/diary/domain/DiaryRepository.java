package org.zeorck.diary.domain.diary.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zeorck.diary.domain.diary.dto.response.DiaryInfoResponse;
import org.zeorck.diary.global.response.PageableResponse;

import java.util.List;

public interface DiaryRepository {

    void save(Diary diary);

    Diary findByDiaryId(Long diaryId);

    void delete(Diary diary);

    Page<Diary> findByMemberId(Long memberId, Pageable pageable);

    Page<Diary> findByVisibility(Visibility visibility, Pageable pageable);

    PageableResponse<DiaryInfoResponse> findAllByTitleOrContentContaining(String keyword, Pageable pageable);
}

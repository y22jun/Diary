package org.zeorck.diary.domain.diary.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryRepository {

    void save(Diary diary);

    Diary findByDiaryId(Long diaryId);

    void delete(Diary diary);

    Page<Diary> findByMemberId(Long memberId, Pageable pageable);
}

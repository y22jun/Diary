package org.zeorck.diary.domain.diary.domain;

public interface DiaryRepository {

    void save(Diary diary);

    Diary findByDiaryId(Long diaryId);

    void delete(Diary diary);
}

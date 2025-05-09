package org.zeorck.diary.domain.diary.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.zeorck.diary.domain.diary.domain.Diary;
import org.zeorck.diary.domain.diary.domain.DiaryRepository;
import org.zeorck.diary.domain.diary.presentation.exception.DiaryNotFoundException;

@RequiredArgsConstructor
@Repository
public class DiaryRepositoryImpl implements DiaryRepository {

    private final DiaryJpaRepository diaryJpaRepository;

    @Override
    public void save(Diary diary) {
        diaryJpaRepository.save(diary);
    }

    @Override
    public Diary findByDiaryId(Long diaryId) {
        return diaryJpaRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);
    }

}

package org.zeorck.diary.domain.diary.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.zeorck.diary.domain.diary.domain.Diary;
import org.zeorck.diary.domain.diary.domain.DiaryRepository;
import org.zeorck.diary.domain.diary.domain.Visibility;
import org.zeorck.diary.domain.diary.dto.response.DiaryInfoResponse;
import org.zeorck.diary.domain.diary.presentation.exception.DiaryNotFoundException;
import org.zeorck.diary.global.response.PageableResponse;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DiaryRepositoryImpl implements DiaryRepository {

    private final DiaryJpaRepository diaryJpaRepository;
    private final DiaryQueryDslRepository diaryQueryDslRepository;

    @Override
    public void save(Diary diary) {
        diaryJpaRepository.save(diary);
    }

    @Override
    public Diary findByDiaryId(Long diaryId) {
        return diaryJpaRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);
    }

    @Override
    public void delete(Diary diary) {
        diaryJpaRepository.delete(diary);
    }

    @Override
    public Page<Diary> findByMemberId(Long memberId, Pageable pageable) {
        return diaryJpaRepository.findByMemberId(memberId, pageable);
    }

    @Override
    public Page<Diary> findByVisibility(Visibility visibility, Pageable pageable) {
        return diaryJpaRepository.findByVisibility(visibility, pageable);
    }

    @Override
    public PageableResponse<DiaryInfoResponse> findAllByTitleOrContentContaining(String keyword, Pageable pageable) {
        return diaryQueryDslRepository.findAllByTitleOrContentContaining(keyword, pageable);
    }

}

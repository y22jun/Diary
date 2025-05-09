package org.zeorck.diary.domain.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zeorck.diary.domain.diary.domain.Diary;
import org.zeorck.diary.domain.diary.domain.DiaryRepository;
import org.zeorck.diary.domain.diary.dto.request.DiarySaveRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryUpdateRequest;
import org.zeorck.diary.domain.diary.dto.response.DiarySaveResponse;
import org.zeorck.diary.domain.diary.dto.response.DiaryUpdateResponse;
import org.zeorck.diary.domain.diary.presentation.exception.DiaryNotForbiddenException;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.domain.MemberRepository;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public DiarySaveResponse saveDiary(Long memberId, DiarySaveRequest diarySaveRequest) {
        Member member = memberRepository.findById(memberId);

        Diary diary = Diary.builder()
                .member(member)
                .title(diarySaveRequest.title())
                .content(diarySaveRequest.content())
                .build();
        diaryRepository.save(diary);

        return DiarySaveResponse.builder()
                .diaryId(diary.getId())
                .build();
    }

    @Transactional
    public DiaryUpdateResponse updateDiary(Long memberId, Long diaryId, DiaryUpdateRequest diaryUpdateRequest) {
        Diary diary = diaryRepository.findByDiaryId(diaryId);

        Long diaryMemberId = diary.getMember().getId();
        validateForbidden(memberId, diaryMemberId);

        diary.diaryUpdate(diaryUpdateRequest.title(), diaryUpdateRequest.content());

        return DiaryUpdateResponse.builder()
                .diaryId(diaryId)
                .build();
    }

    private void validateForbidden(Long memberId, Long diaryMemberId) {
        if (!diaryMemberId.equals(memberId)) {
            throw new DiaryNotForbiddenException();
        }
    }

}

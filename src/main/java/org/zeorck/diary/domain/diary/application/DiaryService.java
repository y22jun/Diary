package org.zeorck.diary.domain.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zeorck.diary.domain.diary.domain.Diary;
import org.zeorck.diary.domain.diary.domain.DiaryRepository;
import org.zeorck.diary.domain.diary.domain.Visibility;
import org.zeorck.diary.domain.diary.dto.request.DiarySaveRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryUpdateRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryVisibilityUpdateRequest;
import org.zeorck.diary.domain.diary.dto.response.DiaryInfoResponse;
import org.zeorck.diary.domain.diary.dto.response.DiarySaveResponse;
import org.zeorck.diary.domain.diary.dto.response.DiaryUpdateResponse;
import org.zeorck.diary.domain.diary.dto.response.DiaryVisibilityUpdateResponse;
import org.zeorck.diary.domain.diary.presentation.exception.DiaryNotForbiddenException;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.domain.MemberRepository;
import org.zeorck.diary.global.response.PageableResponse;

import java.util.List;

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
                .visibility(Visibility.valueOf(diarySaveRequest.visibility()))
                .build();
        diaryRepository.save(diary);

        return DiarySaveResponse.builder()
                .diaryId(diary.getId())
                .build();
    }

    @Transactional
    public DiaryUpdateResponse updateDiary(Long memberId, Long diaryId, DiaryUpdateRequest diaryUpdateRequest) {
        Diary diary = getDiaryId(diaryId);

        Long diaryMemberId = diary.getMember().getId();
        validateForbidden(memberId, diaryMemberId);

        diary.diaryUpdate(diaryUpdateRequest.title(), diaryUpdateRequest.content());

        return DiaryUpdateResponse.builder()
                .diaryId(diaryId)
                .build();
    }

    @Transactional
    public DiaryVisibilityUpdateResponse updateDiaryVisibility(Long memberId, Long diaryId, DiaryVisibilityUpdateRequest diaryVisibilityUpdateRequest) {
        Diary diary = getDiaryId(diaryId);

        Long diaryMemberId = diary.getMember().getId();
        validateForbidden(memberId, diaryMemberId);

        diary.diaryVisibilityUpdate(
                Visibility.valueOf(diaryVisibilityUpdateRequest.visibility())
        );

        return DiaryVisibilityUpdateResponse.builder()
                .diaryId(diaryId)
                .build();
    }

    @Transactional
    public void deleteDiary(Long memberId, Long diaryId) {
        Diary diary = getDiaryId(diaryId);

        Long diaryMemberId = diary.getMember().getId();
        validateForbidden(memberId, diaryMemberId);

        diaryRepository.delete(diary);
    }

    @Transactional(readOnly = true)
    public DiaryInfoResponse getDiaryInfo(Long diaryId) {
        Diary diary = getDiaryId(diaryId);

        return DiaryInfoResponse.builder()
                .diaryId(diary.getId())
                .memberId(diary.getMember().getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public PageableResponse<DiaryInfoResponse> getAllMyDiaries(Long memberId, Pageable pageable) {
        Page<Diary> diaries = diaryRepository.findByMemberId(memberId, pageable);

        List<DiaryInfoResponse> diaryInfoResponses = diaries.stream()
                .map(diary -> DiaryInfoResponse.builder()
                        .diaryId(diary.getId())
                        .memberId(diary.getMember().getId())
                        .title(diary.getTitle())
                        .content(diary.getContent())
                        .createdAt(diary.getCreatedAt())
                        .build())
                .toList();

        return PageableResponse.of(pageable, diaryInfoResponses);
    }

    private Diary getDiaryId(Long diaryId) {
        return diaryRepository.findByDiaryId(diaryId);
    }

    private void validateForbidden(Long memberId, Long diaryMemberId) {
        if (!diaryMemberId.equals(memberId)) {
            throw new DiaryNotForbiddenException();
        }
    }

}

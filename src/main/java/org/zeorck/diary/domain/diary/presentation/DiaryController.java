package org.zeorck.diary.domain.diary.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zeorck.diary.domain.diary.application.DiaryService;
import org.zeorck.diary.domain.diary.dto.request.DiarySaveRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryUpdateRequest;
import org.zeorck.diary.domain.diary.dto.response.DiarySaveResponse;
import org.zeorck.diary.domain.diary.dto.response.DiaryUpdateResponse;
import org.zeorck.diary.global.annotation.MemberId;

@RequiredArgsConstructor
@RequestMapping("/diarys")
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiarySaveResponse> saveDiary(
            @MemberId Long memberId,
            @Valid @RequestBody DiarySaveRequest diarySaveRequest
    ) {
        DiarySaveResponse response = diaryService.saveDiary(memberId, diarySaveRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<DiaryUpdateResponse> updateDiary(
        @MemberId Long memberId,
        @PathVariable Long diaryId,
        @Valid @RequestBody DiaryUpdateRequest diaryUpdateRequest
    ) {
        DiaryUpdateResponse response = diaryService.updateDiary(memberId, diaryId, diaryUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @MemberId Long memberId,
            @PathVariable Long diaryId
    ) {
        diaryService.deleteDiary(memberId, diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

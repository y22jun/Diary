package org.zeorck.diary.domain.diary.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zeorck.diary.domain.diary.application.DiaryService;
import org.zeorck.diary.domain.diary.dto.request.DiarySaveRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryUpdateRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryVisibilityUpdateRequest;
import org.zeorck.diary.domain.diary.dto.response.DiaryInfoResponse;
import org.zeorck.diary.domain.diary.dto.response.DiarySaveResponse;
import org.zeorck.diary.domain.diary.dto.response.DiaryUpdateResponse;
import org.zeorck.diary.domain.diary.dto.response.DiaryVisibilityUpdateResponse;
import org.zeorck.diary.global.annotation.MemberId;
import org.zeorck.diary.global.response.PageableResponse;

import java.util.List;

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

    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryVisibilityUpdateResponse> updateVisibilityDiary(
            @MemberId Long memberId,
            @PathVariable Long diaryId,
            @RequestBody DiaryVisibilityUpdateRequest diaryVisibilityUpdateRequest
    ) {
        DiaryVisibilityUpdateResponse response = diaryService.updateDiaryVisibility(memberId, diaryId, diaryVisibilityUpdateRequest);
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

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryInfoResponse> getDiaryInfo(
            @PathVariable Long diaryId
    ) {
        DiaryInfoResponse response = diaryService.getDiaryInfo(diaryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<PageableResponse<DiaryInfoResponse>> getAllMyDiaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @MemberId Long memberId
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageableResponse<DiaryInfoResponse> response = diaryService.getAllMyDiaries(memberId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<PageableResponse<DiaryInfoResponse>> getAllPublicDiaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageableResponse<DiaryInfoResponse> response = diaryService.getAllPublicDiaries(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public PageableResponse<DiaryInfoResponse> searchDiaries(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return diaryService.searchDiaries(keyword, pageable);
    }

}

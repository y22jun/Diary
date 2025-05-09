package org.zeorck.diary.domain.diary.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zeorck.diary.domain.diary.application.DiaryService;
import org.zeorck.diary.domain.diary.dto.request.DiarySaveRequest;
import org.zeorck.diary.domain.diary.dto.response.DiarySaveResponse;
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

}

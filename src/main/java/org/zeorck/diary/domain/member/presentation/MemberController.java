package org.zeorck.diary.domain.member.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zeorck.diary.domain.member.application.MemberService;
import org.zeorck.diary.domain.member.dto.request.MemberSaveRequest;
import org.zeorck.diary.domain.member.dto.response.MemberSaveResponse;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberSaveResponse> saveMember(
            @Valid @RequestBody MemberSaveRequest request
    ) {
        MemberSaveResponse response = memberService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

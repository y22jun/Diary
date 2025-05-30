package org.zeorck.diary.domain.member.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zeorck.diary.domain.member.application.MemberService;
import org.zeorck.diary.domain.member.dto.request.MemberSaveRequest;
import org.zeorck.diary.domain.member.dto.response.MemberInfoResponse;
import org.zeorck.diary.domain.member.dto.response.MemberSaveResponse;
import org.zeorck.diary.global.annotation.MemberId;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberSaveResponse> saveMember(
            @Valid @RequestBody MemberSaveRequest request
    ) {
        MemberSaveResponse response = memberService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> getMyMemberInfo(
            @MemberId Long memberId
    ) {
        MemberInfoResponse response = memberService.getMyMemberInfo(memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

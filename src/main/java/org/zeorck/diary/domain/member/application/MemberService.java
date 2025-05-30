package org.zeorck.diary.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.domain.MemberRepository;
import org.zeorck.diary.domain.member.dto.request.MemberSaveRequest;
import org.zeorck.diary.domain.member.dto.response.MemberInfoResponse;
import org.zeorck.diary.domain.member.dto.response.MemberSaveResponse;
import org.zeorck.diary.domain.member.presentation.exception.EmailAlreadyExistsException;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberSaveResponse signUp(MemberSaveRequest request) {
        validateEmail(request.email());

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        memberRepository.save(member);

        return MemberSaveResponse.builder()
                .memberId(member.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMyMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId);

        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private void validateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }

}

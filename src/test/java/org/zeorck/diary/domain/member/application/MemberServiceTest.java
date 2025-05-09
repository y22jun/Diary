package org.zeorck.diary.domain.member.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.dto.request.MemberSaveRequest;
import org.zeorck.diary.domain.member.dto.response.MemberInfoResponse;
import org.zeorck.diary.domain.member.dto.response.MemberSaveResponse;
import org.zeorck.diary.domain.member.infrastructure.MemberJpaRepository;
import org.zeorck.diary.domain.member.presentation.exception.EmailAlreadyExistsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void tearDown() {
        memberJpaRepository.deleteAllInBatch();
    }

    @DisplayName("사용자로부터 받은 정보로 회원가입을 진행한다.")
    @Test
    void saveMember() {
        MemberSaveRequest request = MemberSaveRequest.builder()
                .email("test@naver.com")
                .password("test")
                .build();
        MemberSaveResponse response = memberService.signUp(request);

        assertThat(response).isNotNull();
        assertThat(response.memberId()).isEqualTo(1L);
    }

    @DisplayName("이미 존재하는 이메일로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpThrowExceptionCausedByDuplicateEmail() throws Exception {
        Member member = Member.builder()
                .email("test@naver.com")
                .password("test")
                .build();
        memberJpaRepository.save(member);

        MemberSaveRequest request = MemberSaveRequest.builder()
                .email("test@naver.com")
                .password("test")
                .build();

        assertThatThrownBy(() -> memberService.signUp(request))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("이메일이 이미 존재합니다.");
    }

    @DisplayName("이메일, 가입일자를 보여준다.")
    @Test
    void getMemberInfo() {
        Member member = Member.builder()
                .email("test@naver.com")
                .password("test")
                .build();
        memberJpaRepository.save(member);

        MemberInfoResponse response = memberService.getMyMemberInfo(member.getId());

        assertThat(response).isNotNull();
        assertThat(response.email()).isEqualTo("test@naver.com");
        assertThat(response.createdAt()).isNotNull();
    }

}
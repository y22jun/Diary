package org.zeorck.diary.domain.diary.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zeorck.diary.domain.diary.domain.Diary;
import org.zeorck.diary.domain.diary.domain.Visibility;
import org.zeorck.diary.domain.diary.dto.request.DiarySaveRequest;
import org.zeorck.diary.domain.diary.dto.request.DiaryUpdateRequest;
import org.zeorck.diary.domain.diary.dto.response.DiaryInfoResponse;
import org.zeorck.diary.domain.diary.infrastructure.DiaryJpaRepository;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.domain.member.infrastructure.MemberJpaRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class DiaryServiceTest {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @AfterEach
    void tearDown() {
        diaryJpaRepository.deleteAllInBatch();
        memberJpaRepository.deleteAllInBatch();
    }

    @DisplayName("새로운 일기를 작성한다.")
    @Test
    void saveDiary() {
        Member member = getNewMember();
        memberJpaRepository.save(member);

        DiarySaveRequest request = DiarySaveRequest.builder()
                .title("test")
                .content("test")
                .visibility(String.valueOf(Visibility.PUBLIC))
                .build();

        diaryService.saveDiary(member.getId(), request);

        List<Diary> diaryList = diaryJpaRepository.findAll();
        assertThat(diaryList.get(0).getTitle()).isEqualTo("test");
        assertThat(diaryList.get(0).getContent()).isEqualTo("test");
        assertThat(diaryList.get(0).getVisibility()).isEqualTo(Visibility.PUBLIC);
        assertThat(diaryList.get(0).getMember().getId()).isEqualTo(member.getId());
    }

    @DisplayName("특정 일기를 수정한다.")
    @Test
    void updateDiary() {
        Member member = getNewMember();
        memberJpaRepository.save(member);

        DiarySaveRequest request = DiarySaveRequest.builder()
                .title("test")
                .content("test")
                .visibility(String.valueOf(Visibility.PUBLIC))
                .build();

        diaryService.saveDiary(member.getId(), request);
        Diary savedDiary = diaryJpaRepository.findAll().get(0);

        DiaryUpdateRequest diaryUpdateRequest = DiaryUpdateRequest.builder()
                .title("test")
                .content("test")
                .build();
        diaryService.updateDiary(member.getId(), savedDiary.getId(), diaryUpdateRequest);

        Diary updatedDiary = diaryJpaRepository.findById(savedDiary.getId()).orElseThrow();
        assertThat(updatedDiary.getTitle()).isEqualTo("test");
        assertThat(updatedDiary.getContent()).isEqualTo("test");
    }

    @DisplayName("특정 일기를 삭제한다.")
    @Test
    void deleteDiary() {
        Member member = getNewMember();
        memberJpaRepository.save(member);

        DiarySaveRequest request = DiarySaveRequest.builder()
                .title("test")
                .content("test")
                .visibility(String.valueOf(Visibility.PUBLIC))
                .build();

        diaryService.saveDiary(member.getId(), request);
        Diary savedDiary = diaryJpaRepository.findAll().get(0);

        diaryService.deleteDiary(member.getId(), savedDiary.getId());

        assertThat(diaryJpaRepository.findById(savedDiary.getId())).isEmpty();
    }

    @DisplayName("특정 일기를 diaryId로 조회한다.")
    @Test
    void getDiaryInfo() {
        Member member = getNewMember();
        memberJpaRepository.save(member);

        DiarySaveRequest request = DiarySaveRequest.builder()
                .title("test")
                .content("test")
                .visibility(String.valueOf(Visibility.PUBLIC))
                .build();

        diaryService.saveDiary(member.getId(), request);
        Diary savedDiary = diaryJpaRepository.findAll().get(0);

        DiaryInfoResponse response = diaryService.getDiaryInfo(savedDiary.getId());

        assertThat(response.title()).isEqualTo("test");
        assertThat(response.content()).isEqualTo("test");
        assertThat(response.memberId()).isEqualTo(member.getId());
    }

    Member getNewMember() {
        return Member.builder()
                .email("test@naver.com")
                .password("test")
                .build();
    }

}
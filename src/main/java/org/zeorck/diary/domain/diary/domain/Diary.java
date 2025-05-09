package org.zeorck.diary.domain.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zeorck.diary.domain.member.domain.Member;
import org.zeorck.diary.global.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    @Builder
    private Diary(Member member, String title, String content, Visibility visibility) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.visibility = visibility;
    }

    public void diaryUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

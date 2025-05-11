package org.zeorck.diary.domain.diary.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.zeorck.diary.domain.diary.domain.Diary;
import org.zeorck.diary.domain.diary.domain.QDiary;
import org.zeorck.diary.domain.diary.domain.Visibility;
import org.zeorck.diary.domain.diary.dto.response.DiaryInfoResponse;
import org.zeorck.diary.global.response.PageableResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class DiaryQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public PageableResponse<DiaryInfoResponse> findAllByTitleOrContentContaining(String keyword, Pageable pageable) {

        QDiary diary = QDiary.diary;

        BooleanExpression whereClause = buildKeywordCondition(keyword, diary.title, diary.content)
                .and(diary.visibility.eq(Visibility.PUBLIC));

        List<Diary> diaries = queryFactory.select(diary)
                .from(diary)
                .where(whereClause)
                .orderBy(diary.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<DiaryInfoResponse> diaryInfoResponses = diaries.stream()
                .map(this::convertToDiaryInfoResponse)
                .collect(Collectors.toList());

        return PageableResponse.of(pageable, diaryInfoResponses);
    }

    private BooleanExpression buildKeywordCondition(String keyword, StringPath title, StringPath content) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return title.containsIgnoreCase(keyword).or(content.containsIgnoreCase(keyword));
    }

    private DiaryInfoResponse convertToDiaryInfoResponse(Diary diary) {
        return new DiaryInfoResponse(
                diary.getId(),
                diary.getMember().getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getCreatedAt()
        );
    }

}

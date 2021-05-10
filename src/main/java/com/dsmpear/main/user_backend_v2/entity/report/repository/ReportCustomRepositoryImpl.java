package com.dsmpear.main.user_backend_v2.entity.report.repository;

import com.dsmpear.main.user_backend_v2.entity.report.*;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Access;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Field;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Grade;
import com.dsmpear.main.user_backend_v2.entity.report.enums.Type;
import com.dsmpear.main.user_backend_v2.entity.user.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.dsmpear.main.user_backend_v2.entity.report.QReport.report;

@RequiredArgsConstructor
@Repository
public class ReportCustomRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Report> findAllByAccessAndGradeAndFieldAndType(Grade grade, Field field, Type type, Pageable pageable) {
        QueryResults<Report> results = jpaQueryFactory
                .select(report)
                .from(report)
                .where(report.status.isSubmitted.eq(true)
                        .and(report.status.isAccepted.eq(true)))
                .where(eqGrade(grade)
                        .and(eqType(type))
                        .and(eqAccess())
                        .and(eqField(field)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(report.id.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Page<Report> findAllByMembersContainsAndIsAcceptedAndIsSubmittedTrueAndReportTypeAccessOrderByReportIdDesc(User user, Pageable page) {
        QueryResults<Report> results = jpaQueryFactory
                .select(report)
                .from(report)
                .where(report.members.any().user.eq(user)
                    .and(report.status.isAccepted.eq(true))
                    .and(report.status.isSubmitted.eq(true))
                    .and(eqAccess()))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .orderBy(report.id.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), page, results.getTotal());
    }

    private BooleanExpression eqAccess() {
        return report.reportType.access.eq(Access.EVERY);
    }

    private BooleanExpression eqGrade(Grade grade) {
        return grade == null ? null : report.reportType.grade.eq(grade);
    }

    private BooleanExpression eqField(Field field) {
        return field == null ? null : report.reportType.field.eq(field);
    }

    private BooleanExpression eqType(Type type) {
        return type == null ? null : report.reportType.type.eq(type);
    }

}

package com.study.boardproject.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.study.boardproject.domain.ArticleComment;
import com.study.boardproject.domain.QArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment> {

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
        // QuerydslPredicateExecutor에 의해 Article의 모든 필드에 대한 검색이 가능하다.
        // 선택적인 검색이 가능하게끔
        bindings.excludeUnlistedProperties(true); // Listing을 하지 않는 필드는 검색에서 제외
        bindings.including(root.content, root.createdAt, root.createdBy);

        // bindings.bind(root.content).first(StringExpression::likeIgnoreCase); // like '${v}'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '${v}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}

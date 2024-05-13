package com.study.boardproject.repository;

import com.study.boardproject.config.JpaConfig;
import com.study.boardproject.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("testDb")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest // 슬라이스 테스트? //Config, Auditing 정보를 모른다.
class JpaRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        //given

        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(100);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        //given
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        //when
        articleRepository.save(article);

        //then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#game";
        article.setHashtag(updatedHashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article); // flsuh가 없으면 트랜잭션이 끝날 때, 롤백 되므로 flush 반영을 하지 않게 된다.

        //then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }


    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deleteCommentsSize = article.getArticleComments().size();


        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deleteCommentsSize);

    }
}
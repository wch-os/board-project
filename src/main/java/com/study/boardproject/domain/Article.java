package com.study.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // AuditingField도 같이 toString 하기 위함
@Entity
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Key 전략을 DB에 위임(MySQL에서는 AUTO_INCREMENT)
    private Long id;

    @Setter @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용

    @Setter
    private String hashtag; // 해시태그

    @ToString.Exclude // 순환 참조 방지
    @OrderBy("createdAt DESC") // 댓글 리스트를 시간순으로 정렬하기 위함
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {
    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메서드
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }


    // Lombok의 '@EqualsAndHashCode'는 모든 필드의 비교가 일어나므로 비효율적이다.
    // 따라서 Unique 값인 id만 비교하도록 한다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        // JAVA14. Pattern Matching for instanceof
        // o 객체의 instanceof 검사와 type casting을 한 번에 수행한다.
        if (!(o instanceof Article article)) return false;

        // 엔티티가 DB에 영속화 되지 않았을 때, id가 null이 될 수 있다. (insert 하기 전)
        // 위 경우 동등성 검사가 의미가 없는 걸로 생각한다.
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

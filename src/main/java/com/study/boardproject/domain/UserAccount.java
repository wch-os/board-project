package com.study.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // AuditingField도 같이 toString 하기 위함
@Entity
public class UserAccount extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String email;

    private String nickname;

    private String memo;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private final Set<Article> articles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected UserAccount() {
    }

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return id != null && id.equals(userAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

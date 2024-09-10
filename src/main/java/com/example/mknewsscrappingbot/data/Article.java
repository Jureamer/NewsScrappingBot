package com.example.mknewsscrappingbot.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String media;
    private String category;
    private int rank;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String link;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Article() {}

    @Override
    public String toString() {
        return "Article{" +
                "media='" + media + '\'' +
                ", category='" + category + '\'' +
                ", rank=" + rank +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public int getRank() {
        return rank;
    }

    // Static inner Builder class
    public static class ArticleBuilder {
        private String media;
        private String category;
        private int rank;
        private String title;
        private String content;
        private String link;

        public ArticleBuilder media(String media) {
            this.media = media;
            return this;
        }

        public ArticleBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ArticleBuilder rank(int rank) {
            this.rank = rank;
            return this;
        }

        public ArticleBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ArticleBuilder link(String link) {
            this.link = link;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }

    private Article(ArticleBuilder builder) {
        this.media = builder.media;
        this.category = builder.category;
        this.rank = builder.rank;
        this.title = builder.title;
        this.content = builder.content;
        this.link = builder.link;
    }
}

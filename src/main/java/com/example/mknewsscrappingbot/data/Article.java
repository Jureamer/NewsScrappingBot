package com.example.mknewsscrappingbot.data;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String media;
    private String category;
    private int rank;
    private String title;

    @Lob
    private String content;
    private String link;

    public Article(String media, String category, int rank, String title, String content, String link) {
        this.media = media;
        this.category = category;
        this.rank = rank;
        this.title = title;
        this.content = content;
        this.link = link;
    }

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
}

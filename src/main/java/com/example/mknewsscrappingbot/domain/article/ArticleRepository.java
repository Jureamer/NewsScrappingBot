package com.example.mknewsscrappingbot.domain.article;

import com.example.mknewsscrappingbot.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    ArrayList<Article> findByCategoryOrderByRank(String category);
    ArrayList<Article> findByMediaAndCategoryOrderByRank(String media, String category);
    ArrayList<Article> findByMediaAndCategoryAndCreatedAtAfterOrderByRank(String media, String category, LocalDateTime timeThreshold);

}

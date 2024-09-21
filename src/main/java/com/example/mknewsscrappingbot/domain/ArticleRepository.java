package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    ArrayList<Article> findByCategoryOrderByRank(String category);
    ArrayList<Article> findByMediaAndCategoryOrderByRank(String media, String category);
    ArrayList<Article> findByMediaAndCategoryAndCreatedAtAfterOrderByRank(String media, String category, LocalDateTime timeThreshold);

}

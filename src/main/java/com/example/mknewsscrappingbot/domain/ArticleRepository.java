package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    ArrayList<Article> findByCategory(String category);
    ArrayList<Article> findTop10ByCategoryOrderByRank(String category);
    ArrayList<Article> findByCategoryOrderByRank(String category);


}

package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}

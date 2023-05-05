package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.article.ArticleDto;
import dev.chrisjosue.groceryrestapi.entity.article.Article;

import java.util.List;

public interface IArticleService {
    List<Article> findAll(Integer limit, Integer from);

    Article findById(Long id);

    Article create(ArticleDto articleDto);

    Article update(Long id, ArticleDto articleDto);

    void remove(Long id);
}

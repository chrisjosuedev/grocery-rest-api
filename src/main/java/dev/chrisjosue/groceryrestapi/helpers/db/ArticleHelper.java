package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.models.article.ArticleDto;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleHelper {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleHelper(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Find Article By ID
     * @Params id
     * @Return Article if exists, otherwise Null
     */

    public Article findArticleById(Long id) {
        Optional<Article> articleFound = articleRepository.findByIdAndIsActiveIsTrue(id);
        return articleFound.orElse(null);
    }

    /**
     * Find article by Name
     * @Params name
     * @Return true if already exists, false otherwise
     */

    public boolean findArticleByName(String name) {
        Optional<Article> articleFound = articleRepository.findByArticleNameContainingIgnoreCase(name);
        return articleFound.isPresent();
    }

    /**
     * Build an Article from ArticleDTO
     * @Params productDTO
     * @Return Article Built.
     */
    public Article articleFromDto(ArticleDto articleDto) {
        return Article.builder()
                .articleName(articleDto.getArticleName())
                .description(articleDto.getDescription())
                .unitPrice(articleDto.getUnitPrice())
                .isActive(true)
                .build();
    }
}

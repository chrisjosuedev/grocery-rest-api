package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.article.ArticleDto;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.helpers.db.ArticleHelper;
import dev.chrisjosue.groceryrestapi.repository.ArticleRepository;
import dev.chrisjosue.groceryrestapi.service.IArticleService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService implements IArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleHelper articleHelper;

    @Override
    public List<Article> findAll(Integer limit, Integer from) {
        List<Article> allArticles = articleRepository.findAllByIsEnabledIsTrue();

        if (limit == null || from == null) return allArticles;

        allArticles = articleRepository.findAllByIsEnabledIsTrue(PageRequest.of(from, limit));
        return allArticles;
    }

    @Override
    public Article findById(Long id) {
        Article articleFound = articleHelper.findArticleById(id);
        if (articleFound == null)
            throw new MyBusinessException("Article not found with given id.", HttpStatus.NOT_FOUND);

        return articleFound;
    }

    @Override
    public Article create(ArticleDto articleDto) {
        if (articleHelper.findArticleByName(articleDto.getArticleName()))
            throw new MyBusinessException("Already exists a Article with given name.", HttpStatus.BAD_REQUEST);

        Article newArticle = articleHelper.articleFromDto(articleDto);
        articleRepository.save(newArticle);

        return newArticle;
    }

    @Override
    public Article update(Long id, ArticleDto articleDto) {
        Article articleFound = articleHelper.findArticleById(id);
        if (articleFound == null)
            throw new MyBusinessException("Article not found with given id.", HttpStatus.NOT_FOUND);

        articleFound.setArticleName(articleDto.getArticleName());
        articleFound.setDescription(articleDto.getDescription());
        articleFound.setStock(articleDto.getStock());
        articleFound.setUnitPrice(articleDto.getUnitPrice());

        articleRepository.save(articleFound);

        return articleFound;
    }

    @Override
    public void remove(Long id) {
        Article articleFound = articleHelper.findArticleById(id);
        if (articleFound == null)
            throw new MyBusinessException("Article not found with given id.", HttpStatus.NOT_FOUND);

        articleFound.setIsEnabled(false);
        articleRepository.save(articleFound);
    }
}

package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.article.ArticleDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseDataDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.service.IArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Validated
@RequestMapping("/articles")
public class ArticleController {
    private IArticleService articleService;

    @Autowired
    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<Object> findAllArticles(
            @RequestParam(required = false, name = "limit")
            @Min(value = 0, message = "From must be positive number.") Integer limit,
            @RequestParam(required = false, name = "from")
            @Positive(message = "From must be greater than 0.") Integer from
    ) {

        List<Article> allArticles = articleService.findAll(limit, from);

        // Contain Data
        ResponseDataDto<Article> dataResponse = ResponseDataDto.<Article>builder()
                .count(allArticles.size())
                .listFound(allArticles)
                .build();

        return ResponseHandler.responseBuilder(
                "All products registered.",
                HttpStatus.OK,
                dataResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getArticleById(@PathVariable("id") Long id) {
        return ResponseHandler.responseBuilder(
                "Article Found.",
                HttpStatus.OK,
                articleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        return ResponseHandler.responseBuilder(
                "Article created successfully.",
                HttpStatus.CREATED,
                articleService.create(articleDto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateArticle(@PathVariable("id") Long id, @Valid @RequestBody ArticleDto articleDto) {
        return ResponseHandler.responseBuilder(
                "Article updated successfully.",
                HttpStatus.OK,
                articleService.update(id, articleDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeArticle(@PathVariable("id") Long id) {
        articleService.remove(id);
        return ResponseHandler.responseBuilder(
                "Article remove successfully.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }
}

package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.article.ArticleDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseDataDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Validated
@RequestMapping("/articles")
@RequiredArgsConstructor
@Tag(name = "Article Management",
        description = "Serve as a key component for storing information in a database, allowing any user to save an article.")
public class ArticleController {
    private final IArticleService articleService;

    @Operation(summary = "Get Articles List.",
            description = "Obtaining a list of articles with pagination options, you can utilize the endpoint that accepts \"from\" and \"limit\" parameters",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Articles List OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Articles Data is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<Object> findAllArticles(
            @RequestParam(required = false, name = "limit")
            @Min(value = 0, message = "From must be positive number.") Integer limit,
            @RequestParam(required = false, name = "from")
            @Min(value = 0, message = "From must be greater than 0.") Integer from
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

    @Operation(summary = "Get Article by Id.",
            description = "To retrieve articles by ID, you can use the corresponding endpoint that accepts the article ID as a parameter and returns the specific article matching that ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Article OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Article Not Found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getArticleById(@PathVariable("id") Long id) {
        return ResponseHandler.responseBuilder(
                "Article Found.",
                HttpStatus.OK,
                articleService.findById(id));
    }

    @Operation(summary = "Create a new Article.",
            description = "Authenticated users (employees) have the ability to register articles through the POST method. This ensures that only authorized individuals with valid credentials can contribute and add articles to the database.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Article CREATED.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Article Information is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Article data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Object> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        return ResponseHandler.responseBuilder(
                "Article created successfully.",
                HttpStatus.CREATED,
                articleService.create(articleDto)
        );
    }

    @Operation(summary = "Update Article.",
            description = """
                 Modify existing information or make revisions as needed. This ensures that the article database remains accurate and up to date with the latest content and revisions made by authenticated users.
                 """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Article Updated OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Article Information is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Article Not Found.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateArticle(@PathVariable("id") Long id, @Valid @RequestBody ArticleDto articleDto) {
        return ResponseHandler.responseBuilder(
                "Article updated successfully.",
                HttpStatus.OK,
                articleService.update(id, articleDto)
        );
    }

    @Operation(summary = "Delete Article.",
            description = """
                 Delete articles from the database. It accepts the article ID as a parameter to identify the specific article to be deleted.
                 """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Article Removed OK."),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Article Not Found.",
                    content = @Content)
    })
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

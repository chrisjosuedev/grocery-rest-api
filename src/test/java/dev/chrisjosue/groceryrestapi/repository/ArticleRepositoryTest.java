package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.article.Article;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    private Validator validator;
    private Article article, article2;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        article = Article.builder()
                .articleName("Takis Picante")
                .description("Lemon")
                .stock(4)
                .unitPrice(14.53)
                .isEnabled(true)
                .build();

        article2 = Article.builder()
                .articleName("Galleta Oreo")
                .description("Vanilla")
                .stock(6)
                .unitPrice(7.98)
                .isEnabled(false)
                .build();

        articleRepository.saveAll(List.of(article, article2));
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
    }

    @Nested
    @DisplayName("JPA Queries Custom Implemented")
    class CustomJpaTest {
        @Test
        @DisplayName("Find Article by Name With Existing Article")
        void givenArticleName_whenFindByArticleNameContainingIgnoreCase_thenArticleIsPresent() {
            Optional<Article> artFound = articleRepository.findByArticleNameContainingIgnoreCase("takis picante");
            assertTrue(artFound.isPresent());
            assertThat(artFound.get())
                    .usingRecursiveComparison()
                    .isEqualTo(article);
        }

        @Test
        @DisplayName("Find Article by Name With A Non Existing Article")
        void givenArticleName_whenFindByArticleNameContainingIgnoreCase_thenArticleIsNotPresent() {
            Optional<Article> artFound = articleRepository.findByArticleNameContainingIgnoreCase("Non Existing");
            assertThat(artFound).isEmpty();
        }

        @Test
        @DisplayName("Find Article by ID with Active True")
        void givenArticleName_whenFindByIdAndIsEnabledIsTrue_thenArticleIsPresent() {
            Optional<Article> artFound = articleRepository.findByIdAndIsEnabledIsTrue(article.getId());
            assertTrue(artFound.isPresent());
            assertThat(artFound.get())
                    .usingRecursiveComparison()
                    .isEqualTo(article);
        }

        @Test
        @DisplayName("Find Article by ID with Active False returns Nothing")
        void givenArticleName_whenFindByIdAndIsEnabledIsTrue_thenArticleDoesntExists() {
            Optional<Article> artFound = articleRepository.findByIdAndIsEnabledIsTrue(article2.getId());
            assertThat(artFound).isEmpty();
        }

        @Test
        @DisplayName("Find All Active Article List")
        void givenArticles_whenFindAllByIsEnabledIsTrue_thenProductsListSizeWithStatusTrue() {
            List<Article> articlesList = articleRepository.findAllByIsEnabledIsTrue();
            assertThat(articlesList.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("JPA Queries Custom Implemented | Throws Exceptions")
    class CustomJpaTestWithExceptions {
        @Test
        @DisplayName("Save an Article with incorrect Data get Constraint Exceptions")
        void givenArticleData_WhenSaveProductWithInvalidStock_thenConstraintSetList() {
            Article saveArticle = Article.builder()
                    .articleName("Arti")
                    .description(null)
                    .stock(-4)
                    .unitPrice(-14.53)
                    .isEnabled(true)
                    .build();

            Set<ConstraintViolation<Article>> violations = validator.validate(saveArticle);
            assertThat(violations.size()).isEqualTo(4);
        }
    }
}
package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.article.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByIsEnabledIsTrue();
    List<Article> findAllByIsEnabledIsTrue(Pageable pageable);
    Optional<Article> findByIdAndIsEnabledIsTrue(Long id);
    Optional<Article> findByArticleNameContainingIgnoreCase(String articleName);
}

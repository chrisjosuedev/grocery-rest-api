package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.requests.article.ArticleDto;
import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDetailDto;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetail;
import dev.chrisjosue.groceryrestapi.repository.ArticleRepository;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticleHelper {
    private final ArticleRepository articleRepository;

    /**
     * Find Article By ID
     * @Params id
     * @Return Article if exists, otherwise Null
     */
    public Article updateStock(Article articleFound, Integer amount) {
        int currentStock = articleFound.getStock();

        if (amount > currentStock)
            throw new MyBusinessException("Amount article with ID: " + articleFound.getId() + " not available.", HttpStatus.BAD_REQUEST);

        articleFound.setStock(currentStock - amount);
        articleFound = articleRepository.save(articleFound);
        return articleFound;
    }

    /**
     * Find Article By ID and Restore Stock
     * @Params id
     */
    public void rollbackStock(List<Article> articlesPurchased) {
        articlesPurchased.forEach((article -> {
            articleRepository
                    .findByIdAndIsEnabledIsTrue(article.getId())
                    .ifPresent((item) -> {
                        item.setStock(article.getStock());
                        articleRepository.save(item);
                    });
        }));
    }

    /**
     * Find Article By ID
     * @Params id
     * @Return Article if exists, otherwise Null
     */

    public Article findArticleById(Long id) {
        Optional<Article> articleFound = articleRepository.findByIdAndIsEnabledIsTrue(id);
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
                .stock(articleDto.getStock())
                .isEnabled(true)
                .build();
    }
}

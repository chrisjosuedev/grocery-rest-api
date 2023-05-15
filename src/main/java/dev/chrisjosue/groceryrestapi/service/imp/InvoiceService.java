package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDetailDto;
import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDto;
import dev.chrisjosue.groceryrestapi.dto.responses.InvoiceResponse;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.entity.invoice.Invoice;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetail;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetailPK;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.helpers.db.ArticleHelper;
import dev.chrisjosue.groceryrestapi.helpers.db.InvoiceHelper;
import dev.chrisjosue.groceryrestapi.repository.CustomerRepository;
import dev.chrisjosue.groceryrestapi.repository.InvoiceDetailsRepository;
import dev.chrisjosue.groceryrestapi.repository.InvoiceRepository;
import dev.chrisjosue.groceryrestapi.service.IInvoiceService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailsRepository invoiceDetailsRepository;
    private final ArticleHelper articleHelper;
    private final InvoiceHelper invoiceHelper;
    private final List<Article> articlesToUpdateIfProcessFails = new ArrayList<>();

    @Override
    @Transactional
    public InvoiceResponse registerPurchase(InvoiceDto invoiceDto, Employee employee) {
        Optional<Person> customerFound = customerRepository.findByIdAndIsActiveIsTrueAndTypeIsFalse(invoiceDto.getCustomerId());
        if (customerFound.isEmpty())
            throw new MyBusinessException("Invoice not generated: Customer not found, please review registered data.", HttpStatus.NOT_FOUND);

        // Save Invoice
        Invoice newInvoice = invoiceHelper.invoiceFromDto(employee, customerFound.get());
        invoiceRepository.saveAndFlush(newInvoice);

        try {
            // Generate Detail Invoice Data and save it.
            List<InvoiceDetail> items = getItems(newInvoice, invoiceDto.getProductDetails());
            invoiceDetailsRepository.saveAll(items);

            // Clean up array List with Articles to save.
            articlesToUpdateIfProcessFails.clear();

            return invoiceHelper.invoiceResponse(newInvoice);
        } catch (Exception e) {
            // If fails, delete Invoice.
            invoiceRepository.delete(newInvoice);

            // Rollback stock changes
            articleHelper.rollbackStock(articlesToUpdateIfProcessFails);

            // Throw Exception and Show Message with Information
            throw new MyBusinessException("Invoice not generated: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param order
     * @param invoiceDetails
     * @return InvoiceDetail List
     * This method add Items to a List, if article does not exist, just
     * do not add it and go to next.
     */
    private List<InvoiceDetail> getItems(Invoice order, List<InvoiceDetailDto> invoiceDetails) {
        List<InvoiceDetail> itemsRegistered = new ArrayList<>();

        invoiceDetails.forEach((item) -> {
            InvoiceDetailPK key = new InvoiceDetailPK(order.getId(), item.getArticleId());

            // Find Article by ID, throws an Exception if article do not exist.
            Article article = articleHelper.findArticleById(item.getArticleId());
            if (article == null)
                throw new MyBusinessException("Article with ID: " + item.getArticleId() + " does not exists", HttpStatus.BAD_REQUEST);

            // Save entry or previous state of Article.
            articlesToUpdateIfProcessFails.add(article);

            // Updating Stock
            article = articleHelper.updateStock(article, item.getAmount());

            itemsRegistered.add(
                    InvoiceDetail.builder()
                            .id(key)
                            .amount(item.getAmount())
                            .unitPrice(article.getUnitPrice())
                            .invoice(order)
                            .article(article)
                            .build()
            );
        });
        return itemsRegistered;
    }
}

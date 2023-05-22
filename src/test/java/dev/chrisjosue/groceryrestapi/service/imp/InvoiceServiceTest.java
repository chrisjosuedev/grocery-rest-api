package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDetailDto;
import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDto;
import dev.chrisjosue.groceryrestapi.dto.responses.DetailDto;
import dev.chrisjosue.groceryrestapi.dto.responses.InvoiceResponse;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.entity.invoice.Invoice;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetail;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetailPK;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.entity.person.Role;
import dev.chrisjosue.groceryrestapi.helpers.db.ArticleHelper;
import dev.chrisjosue.groceryrestapi.helpers.db.InvoiceHelper;
import dev.chrisjosue.groceryrestapi.helpers.patterns.DetailDtoImp;
import dev.chrisjosue.groceryrestapi.repository.CustomerRepository;
import dev.chrisjosue.groceryrestapi.repository.InvoiceDetailsRepository;
import dev.chrisjosue.groceryrestapi.repository.InvoiceRepository;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InvoiceServiceTest {
    @InjectMocks
    private InvoiceService invoiceService;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private InvoiceDetailsRepository invoiceDetailsRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ArticleHelper articleHelper;
    @Mock
    private InvoiceHelper invoiceHelper;
    private Article article;
    private Person customer;
    private Employee employee;
    private Invoice invoice;
    private InvoiceDto invoiceDto;
    private InvoiceDetailDto invoiceDetailDto;
    private InvoiceDetail invoiceDetail;
    private InvoiceResponse invoiceResponse;
    private DetailDto detail;
    private final List<DetailDto> detailDto = new ArrayList<>();

    @BeforeEach
    void setUp() {
        article = Article.builder()
                .id(1L)
                .articleName("Takis Picante")
                .description("Lemon")
                .stock(4)
                .unitPrice(14.53)
                .isEnabled(true)
                .build();

        employee = Employee.builder()
                .id(1L)
                .dni("0309199900084")
                .firstName("Chris")
                .lastName("Martinez Lara")
                .genre(true)
                .phone("+50497828220")
                .email("cmartinez.lara99@gmail.com")
                .username("chris_martinez")
                .password("Bojack2020!")
                .role(Role.ADMIN)
                .hireDate(new Date())
                .isActive(true)
                .type(true)
                .build();

        customer = Person.builder()
                .id(1L)
                .dni("0109200000098")
                .firstName("Maria")
                .lastName("Martinez Lara")
                .genre(false)
                .phone("+50488138614")
                .isActive(true)
                .type(true)
                .build();

        invoice = Invoice.builder()
                .id(1L)
                .employee(employee)
                .person(customer)
                .build();

        invoiceDetail = InvoiceDetail.builder()
                .id(new InvoiceDetailPK(invoice.getId(), article.getId()))
                .invoice(invoice)
                .article(article)
                .amount(2)
                .unitPrice(article.getUnitPrice())
                .build();

        detail = new DetailDtoImp(
                invoiceDetail.getArticle().getId(),
                invoiceDetail.getArticle().getArticleName(),
                invoiceDetail.getArticle().getUnitPrice(),
                invoiceDetail.getAmount(),
                invoiceDetail.getAmount() * invoiceDetail.getUnitPrice());
        detailDto.add(detail);

        invoiceResponse = InvoiceResponse.builder()
                .id(invoice.getId())
                .date(invoice.getDate())
                .customerName(invoice.getPerson().getFirstName())
                .employeeName(invoice.getEmployee().getFirstName())
                .invoiceDetails(detailDto)
                .total(detail.getSubTotal())
                .build();

        invoiceDetailDto = InvoiceDetailDto.builder()
                .amount(2)
                .articleId(article.getId())
                .build();

        invoiceDto = InvoiceDto.builder()
                .customerId(customer.getId())
                .productDetails(Set.of(invoiceDetailDto))
                .build();

    }

    @Nested
    @DisplayName("Invoice Service Tests")
    class InvoiceServiceImpTest {
        @Test
        @DisplayName("Create a new Invoice")
        void givenNewInvoiceData_whenCreateANewInvoice_thenGenerateInvoice() {
            given(customerRepository.findByIdAndIsActiveIsTrueAndTypeIsFalse(customer.getId()))
                    .willReturn(Optional.of(customer));

            given(invoiceRepository.saveAndFlush(invoice)).willReturn(invoice);
            given(invoiceHelper.invoiceFromDto(employee, customer)).willReturn(invoice);

            given(articleHelper.findArticleById(article.getId())).willReturn(article);
            given(articleHelper.updateStock(article, invoiceDetail.getAmount())).willReturn(article);
            given(invoiceDetailsRepository.saveAll(List.of(invoiceDetail)))
                    .willReturn(List.of(invoiceDetail));

            given(invoiceHelper.invoiceResponse(invoice)).willReturn(invoiceResponse);

            InvoiceResponse newInvoice = invoiceService.registerPurchase(invoiceDto, invoice.getEmployee());

            assertThat(newInvoice).isNotNull();
        }

        @Test
        @DisplayName("Find Invoice By Id")
        void givenInvoiceId_whenFindInvoiceById_thenReturnOrder() {
            given(invoiceHelper.invoiceResponse(invoice)).willReturn(invoiceResponse);
            given(invoiceHelper.findById(invoice.getId())).willReturn(invoice);

            InvoiceResponse invoiceFound = invoiceService.findInvoiceById(invoice.getId());

            assertThat(invoiceFound).isNotNull();
        }

    }

    @Nested
    @DisplayName("Invoice Service Test | Exceptions")
    class InvoiceServiceImpTestException {
        @Test
        @DisplayName("Create a new Invoice with Invalid Customer | Throws an Exception")
        void givenNewInvoiceData_whenCreateANewInvoice_thenThrowsAnException() {
            given(customerRepository.findByIdAndIsActiveIsTrueAndTypeIsFalse(32L)).willThrow(
                    MyBusinessException.class
            );

            given(invoiceRepository.saveAndFlush(invoice)).willReturn(invoice);
            given(invoiceHelper.invoiceFromDto(employee, customer)).willReturn(invoice);

            given(articleHelper.findArticleById(article.getId())).willReturn(article);
            given(articleHelper.updateStock(article, invoiceDetail.getAmount())).willReturn(article);
            given(invoiceDetailsRepository.saveAll(List.of(invoiceDetail)))
                    .willReturn(List.of(invoiceDetail));

            given(invoiceHelper.invoiceResponse(invoice)).willReturn(invoiceResponse);

            assertThrows(MyBusinessException.class, () -> {
                invoiceService.registerPurchase(invoiceDto, invoice.getEmployee());
            });

            verify(invoiceHelper, never()).invoiceResponse(invoice);
        }

        @Test
        @DisplayName("Create a new Invoice with Invalid Article | Throws an Exception")
        void givenNewInvoiceData_whenCreateANewInvoice_thenGenerateInvoice() {
            given(customerRepository.findByIdAndIsActiveIsTrueAndTypeIsFalse(customer.getId()))
                    .willReturn(Optional.of(customer));

            given(invoiceRepository.saveAndFlush(invoice)).willReturn(invoice);
            given(invoiceHelper.invoiceFromDto(employee, customer)).willReturn(invoice);

            given(articleHelper.findArticleById(anyLong())).willThrow(
                    MyBusinessException.class
            );

            assertThrows(MyBusinessException.class, () -> {
                invoiceService.registerPurchase(invoiceDto, invoice.getEmployee());
            });

            verify(invoiceHelper, never()).invoiceResponse(invoice);
        }

        @Test
        @DisplayName("Find Invoice with Invalid Id | Throws an Exception")
        void givenInvoiceId_whenFindInvoiceById_thenThrowsAnException() {
            given(invoiceHelper.findById(invoice.getId())).willThrow(
                    MyBusinessException.class
            );

            assertThrows(MyBusinessException.class, () -> {
                invoiceService.findInvoiceById(32L);
            });

            verify(invoiceHelper, never()).invoiceResponse(invoice);
        }
    }
}

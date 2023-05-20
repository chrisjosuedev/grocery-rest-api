package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.dto.responses.DetailDto;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import dev.chrisjosue.groceryrestapi.entity.invoice.Invoice;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetail;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetailPK;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.entity.person.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvoiceDetailsRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;
    private Validator validator;
    private Article article;
    private Person customer;
    private Employee employee;
    private Invoice invoice;
    private InvoiceDetail invoiceDetail;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        article = Article.builder()
                .articleName("Takis Picante")
                .description("Lemon")
                .stock(4)
                .unitPrice(14.53)
                .isEnabled(true)
                .build();

        articleRepository.save(article);

        employee = Employee.builder()
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

        employeeRepository.save(employee);

        customer = Person.builder()
                .dni("0109200000098")
                .firstName("Maria")
                .lastName("Martinez Lara")
                .genre(false)
                .phone("+50488138614")
                .isActive(true)
                .type(true)
                .build();

        customerRepository.save(customer);

        invoice = Invoice.builder()
                .employee(employee)
                .person(customer)
                .build();

        invoiceRepository.save(invoice);

        invoiceDetail = InvoiceDetail.builder()
                .id(new InvoiceDetailPK(invoice.getId(), article.getId()))
                .invoice(invoice)
                .article(article)
                .amount(2)
                .unitPrice(article.getUnitPrice())
                .build();

        invoiceDetailsRepository.save(invoiceDetail);
    }

    @AfterEach
    void tearDown() {
        invoiceDetailsRepository.deleteAll();
        invoiceRepository.deleteAll();
        articleRepository.deleteAll();
        employeeRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Nested
    @DisplayName("JPA Queries Custom Implemented")
    class CustomJpaInvoiceTest {
        @Test
        @DisplayName("Find All Invoices Details By Order Id")
        void givenId_WhenGetInvoiceDetails_thenOrderGeneralList() {
            List<DetailDto> myInvoices = invoiceDetailsRepository.getInvoiceDetails(invoice.getId());
            assertThat(myInvoices.size()).isEqualTo(1);
            assertThat(myInvoices.get(0).getArticleName()).isEqualTo(invoiceDetail.getArticle().getArticleName());
        }

        @Test
        @DisplayName("Find All Invoices Details By Order Id with Non Existing ID")
        void givenId_WhenGetInvoiceDetails_thenOrderGeneralListIsEmpty() {
            List<DetailDto> myInvoices = invoiceDetailsRepository.getInvoiceDetails(3213L);
            assertTrue(myInvoices.isEmpty());
        }

        @Test
        @DisplayName("Find Subtotal by Id")
        void givenId_WhenGetTotal_thenReturnsSubTotal() {
            Double st = invoiceDetailsRepository.getTotal(invoice.getId());
            assertThat(st).isEqualTo(29.06);
        }
    }

    @Nested
    @DisplayName("JPA Queries Custom Implemented | Throws Exceptions")
    class CustomJpaInvoiceTestWithExceptions {
        @Test
        @DisplayName("Save an Invoice with incorrect Data get Constraint Exceptions")
        void givenInvoiceData_WhenSaveProductWithInvalidStock_thenConstraintSetList() {
            InvoiceDetail invoiceDetailIncorrect = InvoiceDetail.builder()
                    .id(new InvoiceDetailPK(invoice.getId(), article.getId()))
                    .invoice(invoice)
                    .article(article)
                    .amount(-2)
                    .unitPrice(null)
                    .build();

            Set<ConstraintViolation<InvoiceDetail>> violations = validator.validate(invoiceDetailIncorrect);
            assertThat(violations.size()).isEqualTo(2);
        }
    }
}
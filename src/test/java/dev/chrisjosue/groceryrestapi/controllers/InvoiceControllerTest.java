package dev.chrisjosue.groceryrestapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chrisjosue.groceryrestapi.config.Profiles;
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
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.helpers.patterns.DetailDtoImp;
import dev.chrisjosue.groceryrestapi.service.IInvoiceService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InvoiceController.class)
@ActiveProfiles(Profiles.TEST_ENV)
@Import(InvoiceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class InvoiceControllerTest {
    @Mock
    private Principal principal;
    @MockBean
    private IInvoiceService invoiceService;
    @MockBean
    private EmployeeHelper employeeHelper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    @DisplayName("Invoice Controllers Tests")
    class InvoiceControllerApiTest {
        @Test
        @DisplayName("Create Invoice then returns an Invoice Response")
        void givenOrderData_whenCreateInvoice_thenReturnInvoiceCreatedStatus() throws Exception {
            given(invoiceService.registerPurchase(invoiceDto, employee)).willReturn(invoiceResponse);
            given(employeeHelper.getLoggedEmployee(principal)).willReturn(employee);

            ResultActions response = mockMvc.perform(post("/invoices/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invoiceDto)));

            response.andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                            CoreMatchers.is("Invoice generated.")));
        }

        @Test
        @DisplayName("Get Invoice By Id")
        void givenInvoiceId_whenFindInvoiceById_thenReturnOk() throws Exception {
            given(invoiceService.findInvoiceById(invoice.getId()))
                    .willReturn(invoiceResponse);

            ResultActions response = mockMvc.perform(
                    get("/invoices/1")
                            .contentType(MediaType.APPLICATION_JSON)
            );

            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                            CoreMatchers.is("Invoice found.")));
        }
    }
}

package com.polarbookshop.catalog_service.web;

import com.polarbookshop.catalog_service.domain.BookNotFoundException;
import com.polarbookshop.catalog_service.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Test di integrazione a livello Web per {@link BookController}.
* <p>Verifica che il controller gestisca correttamente le richieste HTTP,
* isolando il livello web dagli altri componenti del sistema.</p>
 */
@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;
    /**
     * Verifica che una richiesta GET per un ISBN inesistente restituisca
     * uno status HTTP 404 (Not Found).
     */
    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "7374746173183";
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);
        mockMvc
                .perform(get("/books" + isbn))
                .andExpect(status().isNotFound());
    }

}

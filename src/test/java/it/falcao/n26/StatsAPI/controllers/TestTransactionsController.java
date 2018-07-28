package it.falcao.n26.StatsAPI.controllers;

import it.falcao.n26.StatsAPI.models.Transaction;
import it.falcao.n26.StatsAPI.services.TransactionService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest
public class TestTransactionsController {

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Autowired public TransactionService service;

    @Test
    public void shouldAddTransaction() throws Exception {
        MockHttpServletResponse response201 = sendTransaction(200d, 120L);
        assertThat(response201.getContentLength(), is(equalTo(0)));
        assertThat(response201.getStatus(), is(equalTo(SC_CREATED)));

        MockHttpServletResponse response204 = sendTransaction(200d, 10L);
        assertThat(response204.getStatus(), is(equalTo(SC_NO_CONTENT)));
        assertThat(response204.getContentLength(), is(equalTo(0)));
    }

    public MockHttpServletResponse sendTransaction(Double amount, Long timestamp) throws Exception {
        String json = String.format("{\"amount\": %d, \"timestamp\": %d}", amount.intValue(), timestamp.intValue());
        System.out.println(json);
        ResultActions request = mockMvc.perform(post("/transactions").contentType("application/json").content(json));
        return request.andReturn().getResponse();
    }
    public void addTransaction(Double amount, Long timestamp) {
        service.computeTransaction(Transaction.builder().amount(amount).timestamp(timestamp).build());
    }
    @Test
    public void shouldGetStatistics() throws Exception {
        // Given that I add 2 transactions
        sendTransaction(100d, 1L);
        sendTransaction(0d, 30L);

        // When I get the statistics
        MockHttpServletResponse response = mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))

                // Then they should match my expectation
                .andExpect(jsonPath("$.sum").value("100.0"))
                .andExpect(jsonPath("$.avg").value("50.0"))
                .andExpect(jsonPath("$.max").value("100.0"))
                .andExpect(jsonPath("$.min").value("0.0"))
                .andExpect(jsonPath("$.count").value("2")).andReturn().getResponse();
        assertThat(response.getStatus(), is(equalTo(SC_OK)));

    }
}

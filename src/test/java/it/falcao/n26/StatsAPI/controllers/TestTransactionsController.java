package it.falcao.n26.StatsAPI.controllers;

import it.falcao.n26.StatsAPI.config.TransactionDomainConfiguration;
import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static it.falcao.n26.StatsAPI.models.Statistics.createStatisticsFromJson;
import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = TransactionDomainConfiguration.class)
public class TestTransactionsController {

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    private MockMvcTool http() {
        return new MockMvcTool(mockMvc);
    }
    @Test
    public void shouldAddTransaction() throws Exception {
        // Background: Transactions database is reset
        resetStatistics();

        // Given that I send a transaction
        MockHttpServletResponse response201 = sendTransaction(200d, 120L);
        assertThat(response201.getContentLength(), is(equalTo(0)));
        assertThat(response201.getStatus(), is(equalTo(SC_CREATED)));

        // When I try to submit another with a timestamp that is too old
        MockHttpServletResponse response204 = sendTransaction(200d, 10L);

        // Then I get a 204 - No Content
        assertThat(response204.getStatus(), is(equalTo(SC_NO_CONTENT)));
        assertThat(response204.getContentLength(), is(equalTo(0)));
    }

    public MockHttpServletResponse sendTransaction(Double amount, Long timestamp) throws Exception {
        String json = Transaction.builder().amount(amount).timestamp(timestamp).build().toString();
        return http().requestJson(post("/transactions").content(json));
    }

    public Boolean resetStatistics() throws Exception {
        MockHttpServletResponse response = http().requestJson(delete("/transactions"));
        return response.getStatus() == SC_OK;
    }

    @Test
    public void shouldGetStatistics() throws Exception {
        // Background: Transactions database is reset
        resetStatistics();

        // Given that I add 2 transactions
        sendTransaction(100d, 1L);
        sendTransaction(0d, 30L);

        // When I get the statistics
        MockHttpServletResponse response = http().requestJson(get("/statistics"));

        // And deserialize the response
        Statistics result = createStatisticsFromJson(
                response.getContentAsString());

        // Then it matches my expectations
        assertThat(
                result,
                Matchers.is(Matchers.equalTo(
                        Statistics.builder()
                                .sum(1000d)
                                .avg(100d)
                                .max(200d)
                                .min(50d)
                                .count(10L)
                                .build()
                        )
                )
        );
    }
}

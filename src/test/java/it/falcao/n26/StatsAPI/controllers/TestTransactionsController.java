package it.falcao.n26.StatsAPI.controllers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldAddTransaction() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/transactions").contentType("application/json").content("{\"amount\": 200, \"timestamp\": 1}"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertThat(response.getContentLength(), is(equalTo(0)));
    }

    @Test
    public void shouldGetStatistics() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.sum").value("1000"))
                .andExpect(jsonPath("$.avg").value("100"))
                .andExpect(jsonPath("$.max").value("200"))
                .andExpect(jsonPath("$.min").value("50"))
                .andExpect(jsonPath("$.count").value("10"));
    }
}

package it.falcao.n26.StatsAPI.functional;


import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// Adds more sugar to tests
public class MockMvcTool {
    private MockMvc mvc;

    public MockMvcTool(MockMvc mvc) {
        this.mvc = mvc;
    }

    public ResultActions requestJsonAction(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mvc.perform(requestBuilder.contentType("application/json"));
    }
    public MockHttpServletResponse requestJson(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return requestJsonAction(requestBuilder).andReturn().getResponse();
    }

}

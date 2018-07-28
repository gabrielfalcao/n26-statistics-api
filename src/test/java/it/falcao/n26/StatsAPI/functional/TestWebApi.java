package it.falcao.n26.StatsAPI.functional;

import it.falcao.n26.StatsAPI.StatsApiApplication;
import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatsApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestWebApi {

    private HttpHeaders headers;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @BeforeEach
    private void setup() {
        this.headers = new HttpHeaders();
        this.headers.add("Content-Type", "application/json");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldAddTransaction() {
        // Background: Transactions database is reset
        resetStatistics();

        // Given a timestamp
        Long timestamp = freshTimestamp();

        // And that I send a transaction
        ResponseEntity<String> response201 = sendTransaction(200d, timestamp);

        // And it returns 201 - created
        assertThat(response201.getStatusCode(), is(equalTo(HttpStatus.CREATED)));

        // And the body is empty
        assertThat(response201.getBody(), is(nullValue()));


        // When I try to submit another with a timestamp that is 1 second too old
        ResponseEntity<String> response204 = sendTransaction(200d, timestamp - 61);

        // Then I get a 204 - No Content
        assertThat(response204.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));

        // And the body is empty
        assertThat(response201.getBody(), is(nullValue()));
    }

    public ResponseEntity<String> sendTransaction(Double amount, Long timestamp) {
        // Create Transaction pojo
        Transaction transaction = Transaction.builder().amount(amount).timestamp(timestamp).build();

        // Send a HTTP POST
        HttpEntity<Transaction> entity = new HttpEntity<>(transaction, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transactions"),
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            sleep(314l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }


    public Boolean resetStatistics() {
        return restTemplate.exchange(
                createURLWithPort("/transactions"),
                HttpMethod.DELETE,
                new HttpEntity<String>(null, headers),
                String.class
        ).getStatusCode() == HttpStatus.OK;
    }

    public Statistics requestStatistics() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        return restTemplate.exchange(
                createURLWithPort("/statistics"),
                HttpMethod.GET, entity, Statistics.class).getBody();
    }


    @Test
    public void shouldGetStatistics() throws Exception {
        // Background: Transactions database is reset
        resetStatistics();

        // Given 7 transactions of amount 100
        sendTransaction(100d, freshTimestamp());
        sendTransaction(100d, freshTimestamp());
        sendTransaction(100d, freshTimestamp());
        sendTransaction(100d, freshTimestamp());
        sendTransaction(100d, freshTimestamp());
        sendTransaction(100d, freshTimestamp());
        sendTransaction(100d, freshTimestamp());

        // Given 2 transactions of amount 50
        sendTransaction(50d, freshTimestamp());
        sendTransaction(50d, freshTimestamp());

        // Given 1 transaction of amount 200
        sendTransaction(200d, freshTimestamp());

        // When I get the statistics
        Statistics result = requestStatistics();

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

    private Long freshTimestamp() {
        return currentTimeMillis();
    }
}

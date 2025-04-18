package it.falcao.n26.StatsAPI.unit;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import it.falcao.n26.StatsAPI.repositories.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TransactionsRepositoryTest {
    private TransactionsRepository repository;
    private Long timestamp;

    @BeforeEach
    void setup() {
        repository = new TransactionsRepository();
        timestamp = 1532738863L;
    }

    @Test
    void putTransactionShouldCalculateAverage() {
        // Given a single transaction
        sendTransaction(100d);
        // When I get the statistics
        Statistics result = repository.getStatistics();
        // Then it should match the transaction
        assertThat(
                result,
                is(equalTo(
                        Statistics.builder()
                                .sum(100d)
                                .avg(100d)
                                .max(100d)
                                .min(100d)
                                .count(1L)
                                .build()
                        )
                )
        );
    }

    void sendTransaction(Double amount) {
        timestamp += 30;
        repository.putTransaction(Transaction.builder().timestamp(timestamp).amount(amount).build());
    }

    @Test
    void getStatisticsShouldReturnExpected() {
        // Given 7 transactions of amount 100
        sendTransaction(100d);
        sendTransaction(100d);
        sendTransaction(100d);
        sendTransaction(100d);
        sendTransaction(100d);
        sendTransaction(100d);
        sendTransaction(100d);

        // And 2 transactions of amount 50
        sendTransaction(50d);
        sendTransaction(50d);

        // And 1 transaction with amount 200
        sendTransaction(200d);

        // When I get the statistics
        Statistics statistics = repository.getStatistics();

        // Then they should match my expectation
        assertThat(
                statistics,
                is(equalTo(
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
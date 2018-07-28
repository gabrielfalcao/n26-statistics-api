package it.falcao.n26.StatsAPI.unit;

import it.falcao.n26.StatsAPI.models.Statistics;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static it.falcao.n26.StatsAPI.models.Statistics.createStatisticsFromJson;
import static it.falcao.n26.StatsAPI.models.Statistics.emptyStatistics;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

class StatisticsTest {

    @Test
    void emptyStatisticsShouldReturnZeroedValues() {
        MatcherAssert.assertThat(
                emptyStatistics(),
                Matchers.is(
                        equalTo(
                                Statistics.builder()
                                        .sum(0d)
                                        .avg(0d)
                                        .max(0d)
                                        .min(0d)
                                        .count(0L).build())));
    }

    @Test
    void copy() {
        assertThat(
                emptyStatistics().copy(),
                is(equalTo(
                        emptyStatistics())));
    }

    @Test
    void createStatisticsFromJsonShouldDeserializeIntoNewInstance() throws Exception {
        assertThat(
                createStatisticsFromJson(
                        "{\n" +
                                "    \"sum\": 50,\n" +
                                "    \"avg\": 50,\n" +
                                "    \"max\": 50,\n" +
                                "    \"min\": 50,\n" +
                                "    \"count\": 1\n" +
                                "}"
                ),
                is(equalTo(
                        Statistics.builder()
                                .sum(50d)
                                .avg(50d)
                                .max(50d)
                                .min(50d)
                                .count(1L)
                                .build())));
    }

    @Test
    void toReprShouldReturnDummyRepresentation() {
        assertThat(
                emptyStatistics(),
                is(
                        equalTo(
                                Statistics.builder()
                                        .sum(0d)
                                        .avg(0d)
                                        .max(0d)
                                        .min(0d)
                                        .count(0L).build())));
    }
}
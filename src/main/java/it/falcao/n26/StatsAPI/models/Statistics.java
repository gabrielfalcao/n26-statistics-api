package it.falcao.n26.StatsAPI.models;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.StringWriter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Statistics {
    @Getter
    @Setter
    private Double sum;
    @Getter
    @Setter
    private Double avg;
    @Getter
    @Setter
    private Double max;
    @Getter
    @Setter
    private Double min;
    @Getter
    @Setter
    private Long count;

    public static Statistics emptyStatistics() {
        return Statistics.builder().sum(0d).avg(0d).max(0d).min(0d).count(0L).build();
    }

    public static Statistics createStatisticsFromJson(String raw) throws Exception {
        Statistics data;
        ObjectMapper objectMapper = new ObjectMapper();
        data = objectMapper.readValue(raw, Statistics.class);
        return data;
    }

    public Statistics copy() {
        return this.toBuilder().build();
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        // uncomment for pretty json output (useful while debugging tests)
        // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, this);
        } catch (Exception e) {
            return toRepr();
        }
        return writer.toString();
    }

    public String toRepr() {
        return "Statistics(" +
                "sum=" + getSum().toString() +
                ", avg=" + getAvg().toString() +
                ", max=" + getMax().toString() +
                ", min=" + getMin().toString() +
                ", count=" + getCount().toString() +
                ")";
    }
}

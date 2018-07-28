package it.falcao.n26.StatsAPI.models;


import lombok.*;

@Data
@AllArgsConstructor
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
        return Statistics.builder().build();
    }

    public Statistics copy() {
        return this.toBuilder().build();
    }
}

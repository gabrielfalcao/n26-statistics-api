package it.falcao.n26.StatsAPI.models;


import lombok.*;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Statistics {
    @Getter
    @Setter
    private int sum;
    @Getter
    @Setter
    private int avg;
    @Getter
    @Setter
    private int max;
    @Getter
    @Setter
    private int min;
    @Getter
    @Setter
    private int count;

    public static Statistics emptyStatistics() {
        return Statistics.builder().build();
    }

    public Statistics copy() {
        return this.toBuilder().build();
    }
}

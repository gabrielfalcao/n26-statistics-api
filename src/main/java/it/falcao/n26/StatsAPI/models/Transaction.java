package it.falcao.n26.StatsAPI.models;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Transaction {
    @Getter
    @Setter
    private Double amount;
    @Getter
    @Setter
    private Long timestamp;

}

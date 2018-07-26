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
    private float amount;
    @Getter
    @Setter
    private int timestamp;

}

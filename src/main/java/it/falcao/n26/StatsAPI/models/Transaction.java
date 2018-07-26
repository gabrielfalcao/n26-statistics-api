package it.falcao.n26.StatsAPI.models;


import lombok.*;

@Data
public class Transaction {
    @Getter @Setter private float amount;
    @Getter @Setter private int timestamp;
}

package it.falcao.n26.StatsAPI;

import it.falcao.n26.StatsAPI.config.TransactionDomainConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TransactionDomainConfiguration.class)
public class StatsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatsApiApplication.class, args);
    }
}

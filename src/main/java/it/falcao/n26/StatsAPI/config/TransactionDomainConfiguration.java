package it.falcao.n26.StatsAPI.config;

import it.falcao.n26.StatsAPI.services.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionDomainConfiguration {
    public TransactionDomainConfiguration() {
    }

    @Bean(name = "transactions")
    public TransactionService transactionService() {
        return new TransactionService();
    }
}
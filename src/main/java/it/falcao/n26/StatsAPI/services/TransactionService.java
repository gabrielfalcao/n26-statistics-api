package it.falcao.n26.StatsAPI.services;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import it.falcao.n26.StatsAPI.repositories.TransactionsRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class TransactionService {

    private TransactionsRepository repository;

    @PostConstruct
    public void init() {
        repository = new TransactionsRepository();
    }

    public boolean computeTransaction(Transaction transaction) {
        synchronized (this) {
            Long timestamp = transaction.getTimestamp();
            if (timestamp < 0) {
                return false;
            }
            long diff = timestamp - repository.latestTimestamp();
            if (diff > 60) {
                repository.putTransaction(transaction);
                return true;
            } else {
                return false;
            }
        }
    }

    public Statistics getStatistics() {
        synchronized (this) {
            return repository.getStatistics();
        }
    }

    public Long flushMemory() {
        return repository.flush();
    }
}
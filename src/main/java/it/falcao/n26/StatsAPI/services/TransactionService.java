package it.falcao.n26.StatsAPI.services;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import it.falcao.n26.StatsAPI.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static it.falcao.n26.StatsAPI.models.Statistics.emptyStatistics;


@Component
public class TransactionService {

    private TransactionsRepository repository;
    private Statistics statistics;

    @PostConstruct
    public void init() {
        repository = new TransactionsRepository();
        statistics = emptyStatistics();
    }

    public void computeTransaction(Transaction transaction) {
        synchronized (repository) {
            repository.putTransaction(transaction);
        }
    }

    public Statistics getStatistics() {
        synchronized (repository) {
            return repository.getStatistics();
        }
    }
}
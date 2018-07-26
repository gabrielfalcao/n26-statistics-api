package it.falcao.n26.StatsAPI.repositories;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static it.falcao.n26.StatsAPI.models.Statistics.emptyStatistics;
import static java.util.Collections.synchronizedList;

public class TransactionsRepository {
    private Statistics statistics;
    private List<Transaction> transactions;

    public TransactionsRepository() {
        transactions = synchronizedList(new ArrayList());
        statistics = emptyStatistics();
    }
    public void putTransaction(Transaction transaction) {
        synchronized (transactions) {
            transactions.add(transaction);
        }
    }

    public Statistics getStatistics() {
        synchronized (statistics) {
            statistics.setSum(1000);
            statistics.setCount(10);
            statistics.setAvg(100);
            statistics.setMax(200);
            statistics.setMin(50);
            return statistics.copy();
        }
    }

    public List<Transaction> getTransactions() {
        synchronized (transactions) {
            return transactions.stream().distinct().collect(Collectors.toList());
        }
    }
}

package it.falcao.n26.StatsAPI.controllers;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import it.falcao.n26.StatsAPI.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;


@RestController
public class TransactionsController {

    public @Autowired
    @Qualifier("transactions")
    TransactionService engine;
    Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTransaction(@RequestBody Transaction transaction) {
        synchronized (this) {
            if (engine.computeTransaction(transaction)) {
                logger.info(format("Computed transaction: %s", transaction.toString()));
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                logger.info(format("Rejected transaction: %s", transaction.toString()));
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics getStatistics() {
        synchronized (this) {
            Statistics statistics = engine.getStatistics();
            logger.info("Computed transaction: %s", statistics.toString());
            return statistics;
        }
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Long> resetStatistics() {
        HashMap<String, Long> result = new HashMap<>();

        synchronized (this) {
            result.put("count", engine.flushMemory());
        }
        logger.warn("Flushing database");
        return result;
    }
}

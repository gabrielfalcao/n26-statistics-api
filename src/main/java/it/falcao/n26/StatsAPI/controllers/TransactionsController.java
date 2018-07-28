package it.falcao.n26.StatsAPI.controllers;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import it.falcao.n26.StatsAPI.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class TransactionsController {

    public  @Autowired
    @Qualifier("transactions") TransactionService engine;


    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTransaction(@RequestBody Transaction transaction) {
        synchronized (this) {
            if (engine.computeTransaction(transaction)) {
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics getStatistics() {
        synchronized (this) {
            return engine.getStatistics();
        }
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Long> resetStatistics() {
        HashMap<String, Long> result = new HashMap<>();

        synchronized (this) {
            result.put("count", engine.flushMemory());
        }
        return result;
    }
}

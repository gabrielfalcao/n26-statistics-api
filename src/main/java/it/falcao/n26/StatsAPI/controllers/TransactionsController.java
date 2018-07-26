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


@Controller
public class TransactionsController {

    private @Autowired
    @Qualifier("transactions")
    TransactionService engine;


    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTransaction(@RequestBody Transaction transaction) {
        synchronized (engine) {
            engine.computeTransaction(transaction);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics getStatistics() {
        synchronized (engine) {
            return engine.getStatistics();
        }
    }
}

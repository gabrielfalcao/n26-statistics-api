package it.falcao.n26.StatsAPI.controllers;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Component
public class TransactionsController {
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseBody
    public String addTransaction(@RequestBody Transaction transaction) {
        return "";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics getStatistics() {
        return new Statistics(1000, 100, 200, 50, 10);
    }
}

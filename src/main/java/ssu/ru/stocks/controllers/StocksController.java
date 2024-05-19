package ssu.ru.stocks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.services.AccountsService;
import ssu.ru.stocks.services.StocksService;

import java.util.Optional;

@Controller
@RequestMapping("/stocks")
public class StocksController {

    private final StocksService stocksService;
    private final AccountsService accountsService;

    @Autowired
    public StocksController(StocksService stocksService, AccountsService accountsService) {
        this.stocksService = stocksService;
        this.accountsService = accountsService;
    }

    @GetMapping
    public String index(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> account = accountsService.getAccountByUsername(username);
        if (account.isPresent()) {
            model.addAttribute("stocks", stocksService.getStocks());
            model.addAttribute("account", account.get());
            return "stocks/index";
        }
        throw new UsernameNotFoundException(username);
    }
}

package ssu.ru.stocks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.models.Stock;
import ssu.ru.stocks.services.AccountsService;
import ssu.ru.stocks.services.StocksService;

import java.util.List;
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
            List<Stock> stocks = stocksService.getStocks();
            stocks.forEach(s -> System.out.printf("%s: %d%n", s.getCompanyName(), s.getQuantity()));
            boolean fullBuyout = stocks.stream().noneMatch(s -> s.getQuantity() != 0);
            model.addAttribute("fullBuyout", fullBuyout);
            if (!fullBuyout) {
                model.addAttribute("stocks", stocks);
            }
            model.addAttribute("account", account.get());
            model.addAttribute("isOpen", stocksService.isStockExchangeAvailable());
            return "stocks/index";
        }
        throw new UsernameNotFoundException(username);
    }

    @PostMapping("{id}/purchase")
    public String purchase(@PathVariable int id, String companyAndPrice, int amount) {
        stocksService.purchaseStocks(id, companyAndPrice.split("\\|")[0], amount);
        return "redirect:/stocks";
    }

    @PostMapping("{id}/sell")
    public String sell(@PathVariable int id, @RequestParam("id") List<Integer> stockIds,
                       @RequestParam("amount") List<Integer> amounts) {
        stocksService.sellStocks(id, stockIds, amounts);
        return "redirect:/profile";
    }
}

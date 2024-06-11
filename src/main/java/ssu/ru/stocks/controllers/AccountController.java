package ssu.ru.stocks.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.services.AccountStockService;
import ssu.ru.stocks.services.AccountsService;
import ssu.ru.stocks.services.StocksService;

import java.util.Optional;

@Controller
@Tag(name = "Account Controller", description = "Account Controller for performing account operations")
public class AccountController {

    private final AccountsService accountsService;
    private final AccountStockService accountStockService;
    private final StocksService stocksService;

    @Autowired
    public AccountController(AccountsService accountsService, AccountStockService accountStockService,
                             StocksService stocksService) {
        this.accountsService = accountsService;
        this.accountStockService = accountStockService;
        this.stocksService = stocksService;
    }

    @GetMapping("/profile")
    @Operation(summary = "Show Account", description = "Returns a view with account details")
    public String showAccount(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> accountToShow = accountsService.getAccountByUsernameEagerly(username);
        if (accountToShow.isPresent()) {
            model.addAttribute("account", accountToShow.get());
            model.addAttribute("quantities",
                    accountStockService.getAllByAccountId(accountToShow.get().getId()));
            model.addAttribute("isOpen", stocksService.isStockExchangeAvailable());
            return "profile/show";
        }
        return "redirect:stocks/index";
    }
}

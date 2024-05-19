package ssu.ru.stocks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.services.AccountStockService;
import ssu.ru.stocks.services.AccountsService;

import java.util.Optional;

@Controller
public class AccountController {

    private final AccountsService accountsService;
    private final AccountStockService accountStockService;

    @Autowired
    public AccountController(AccountsService accountsService, AccountStockService accountStockService) {
        this.accountsService = accountsService;
        this.accountStockService = accountStockService;
    }

    @GetMapping("/profile")
    public String showAccount(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> accountToShow = accountsService.getAccountByUsernameEagerly(username);
        if (accountToShow.isPresent()) {
            model.addAttribute("account", accountToShow.get());
            model.addAttribute("quantities",
                    accountStockService.getAllByAccountId(accountToShow.get().getId()));
            return "/profile/show";
        }
        return "redirect:/stocks/index";
    }
}

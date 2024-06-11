package ssu.ru.stocks.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Controller for performing login")
public class AuthController {

    @GetMapping("/login")
    @Operation(summary = "Login", description = "Return login page")
    public String loginPage() {
        return "auth/login";
    }
}

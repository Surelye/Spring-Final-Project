package ssu.ru.stocks.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssu.ru.stocks.security.JwtUtil;

@Controller
@RequestMapping("/auth")
public class AuthController {

//    private final JwtUtil jwtUtil;
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public AuthController(JwtUtil jwtUtil, ModelMapper modelMapper) {
//        this.jwtUtil = jwtUtil;
//        this.modelMapper = modelMapper;
//    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }
}

package ssu.ru.stocks.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ssu.ru.stocks.models.Account;
import ssu.ru.stocks.repositories.AccountsRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AccountDetailsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountDetailsService accountDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsernameWithValidCredentials() {
        String validUsername = "unlimited";
        Account validAccount = new Account();
        validAccount.setUsername(validUsername);
        when(accountsRepository.findByUsername(validUsername)).thenReturn(Optional.of(validAccount));

        UserDetails userDetails = accountDetailsService.loadUserByUsername(validUsername);

        assertNotNull(userDetails);
        assertEquals(validUsername, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameWithInvalidCredentials() {
        String invalidUsername = "invalidUsername";
        when(accountsRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> accountDetailsService.loadUserByUsername(invalidUsername));
    }
}

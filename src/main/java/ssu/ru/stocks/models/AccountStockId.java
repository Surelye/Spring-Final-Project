package ssu.ru.stocks.models;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class AccountStockId implements Serializable {

    private int accountId;
    private int stockId;
}

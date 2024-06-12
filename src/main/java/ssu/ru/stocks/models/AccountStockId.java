package ssu.ru.stocks.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AccountStockId implements Serializable {

    private int accountId;
    private int stockId;
}

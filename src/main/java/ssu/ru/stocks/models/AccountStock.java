package ssu.ru.stocks.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(AccountStockId.class)
@Table(name = "Account_Stock")
public class AccountStock {

    @Id
    @Column(name = "account_id")
    private int accountId;

    @Id
    @Column(name = "stock_id")
    private int stockId;

    @Min(value = 1, message = "Количество акций во владении должно быть больше нуля!")
    @Column(name = "quantity_held")
    private int quantityHeld;
}

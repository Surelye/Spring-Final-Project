package ssu.ru.stocks.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
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

    @Min(value = 0, message = "Количество акций во владении должно быть >= 0!")
    @Column(name = "quantity_held")
    private int quantityHeld;
}

package ssu.ru.stocks.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Size(min = 3, max = 50, message = "Название компании должно быть длиной от 3 до 20 символов!")
    @Column(name = "company_name")
    private String companyName;

    @Min(value = 1, message = "Стоимость акции не может быть меньше 1 рубля!")
    @Column(name = "purchase_price")
    private double purchasePrice;

    @Min(value = 0, message = "Стоимость продажи акции не может быть отрицательным!")
    @Column(name = "sell_price")
    private double sellPrice;

    @Min(value = 1, message = "Общее количество акций должно быть больше нуля!")
    @Column(name = "quantity")
    private int quantity;

    @ManyToMany(mappedBy = "stocks")
    private List<Account> accounts;
}

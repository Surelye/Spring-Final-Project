package ssu.ru.stocks.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Size(min = 3, max = 40, message = "Имя пользователя должно быть длиной от 3 до 40 символов!")
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Size(min = 8, max = 40, message = "Пароль должен быть длиной от 8 до 40 символов!")
    @Column(name = "password")
    private String password;

    @Min(value = 0, message = "Значение баланса не может быть отрицательным!")
    @Column(name = "balance")
    private double balance;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;

    @DecimalMin(value = "0.5", message = "Минимальное значение комиссии составляет 0.5%!")
    @Max(value = 5, message = "Максимальное значение комиссии составляет 5%!")
    @Column(name = "commission")
    private double commission;

    @ManyToMany
    @JoinTable(name = "Account_Stock",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id"))
    private List<Stock> stocks;
}

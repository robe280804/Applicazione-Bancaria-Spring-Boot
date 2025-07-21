package com.app_bancaria.my_bnl_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@SuperBuilder //per classi che estendono altre classi
@AllArgsConstructor
@NoArgsConstructor
@Immutable  //se avrò uno 'stato' (completata, in esecuzione...) dovrò levarlo
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(nullable = false)
    private String motivation;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount fromAccount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime date;
}

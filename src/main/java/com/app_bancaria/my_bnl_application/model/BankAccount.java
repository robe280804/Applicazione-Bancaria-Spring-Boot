package com.app_bancaria.my_bnl_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bank_account_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String codiceFiscale;

    @Column(nullable = false, unique = true)
    private String iban;

    @Column(nullable = false, unique = true)
    private String numeroConto;

    @Column(nullable = false)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private TipoConto tipologia;

    @Enumerated(EnumType.STRING)
    private Valuta valuta;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

     @PrePersist
     private void prePersist(){
         if (iban == null || iban.isBlank()){
             this.iban = generateIban();
         }
         if (numeroConto == null || numeroConto.isBlank()){
             this.numeroConto = generateNumeroConto();
         }
     }

    private String generateIban() {
        // Esempio semplificato: "IT" + 2 cifre controllo + 22 cifre randomiche
        String countryCode = "IT";
        String checkDigits = String.format("%02d", ThreadLocalRandom.current().nextInt(10, 99));
        StringBuilder sb = new StringBuilder();
        sb.append(countryCode).append(checkDigits);
        for (int i = 0; i < 22; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return sb.toString();
    }

    private String generateNumeroConto() {
        // Esempio: numero conto a 10 cifre casuali
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return sb.toString();
    }
}

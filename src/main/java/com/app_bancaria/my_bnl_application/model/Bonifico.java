package com.app_bancaria.my_bnl_application.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder //per classi che estendono altre classi
public class Bonifico extends Transaction{

    @NotNull
    private String ibanDestinatario;

    @NotNull
    private String nomeDestinatario;

    @NotNull
    private String cognomeDestinatario;
}

package com.tests.junit.model;

import lombok.*;

import java.util.Date;

/**
 * @author Adriano Rabello 27/05/2022 19:49:01
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Locacao {

    private Usuario usuario;
    private Filme filme;
    private Date dataLocacao;
    private Date dataRetorno;
    private  double valor;
}

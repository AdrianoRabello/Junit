package com.tests.junit.model;

import lombok.*;

/**
 * @author Adriano Rabello 27/05/2022 19:48:03
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Filme {

    private String nome;
    private Integer estoque;
    private Double precoLocacao;
}

package com.tests.junit.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
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
    private Collection<Filme> filmes = new ArrayList<>();
    private Date dataLocacao;
    private Date dataRetorno;
    private  double valor;

    public void adicionarFilme(Filme filme){
        this.filmes.add(filme);
    }

    public void somarValorTotalLocacao(){
        this.valor = filmes.stream().map(x -> x.getPrecoLocacao())
                .reduce((a, b) -> a + b).get();
    }
}

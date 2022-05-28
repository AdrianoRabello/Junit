package com.tests.junit.services;

import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Adriano Rabello 27/05/2022 19:54:18
 **/
public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, Collection<Filme> filmes) throws Exception {


        if (Objects.isNull(filmes) || filmes.isEmpty()) {
            throw new LocadoraException("Pelo menos um filme deve ser informado");
        }

        if (filmes.stream().anyMatch(filme -> filme.getEstoque().equals(0))) {
            throw new FilmeSemEstoqueException("O estoque do filme deve ser maior que 0");
        }

        if (Objects.isNull(usuario)) {
            throw new LocadoraException("O usuário deve ser informado");
        }


        Optional<Double> valorDosFilmes = filmes.stream().map(x -> x.getPrecoLocacao())
                .reduce((a, b) -> a + b);

        return new Locacao().builder()
                .filmes(filmes)
                .usuario(usuario)
                .dataRetorno(DataUtils.adicionarDias(new Date(), 1))
                .valor(somarValoresFilme(filmes))
                .dataLocacao(new Date())
                .dataRetorno(DataUtils.adicionarDias(new Date(), 1))
                .build();
    }

    private Double somarValoresFilme(Collection<Filme> filmes){
        Optional<Double> valorOptional = filmes.stream().map(x -> x.getPrecoLocacao())
                .reduce((a, b) -> a + b);
        return valorOptional.get();
    }

}

package com.tests.junit.services;

import com.tests.junit.exceptions.FilmeSemEstoqueException;
import com.tests.junit.exceptions.LocadoraException;
import com.tests.junit.model.Filme;
import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;

import java.util.Date;

/**
 * @author Adriano Rabello 27/05/2022 19:54:18
 **/
public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {


        if(filme == null){
            throw new LocadoraException("O filme deve ser informado");
        }

        if(filme.getEstoque() == 0){
            throw new FilmeSemEstoqueException("Estoque é 0");
        }

        if(usuario == null){
            throw new LocadoraException("O usuário deve ser informado");
        }




        return new Locacao().builder()
                .filme(filme)
                .usuario(usuario)
                .dataRetorno(DataUtils.adicionarDias(new Date(),1))
                .valor(filme.getPrecoLocacao())
                .dataLocacao(new Date())
                .dataRetorno(DataUtils.adicionarDias(new Date(),1))
                .build();
    }

}

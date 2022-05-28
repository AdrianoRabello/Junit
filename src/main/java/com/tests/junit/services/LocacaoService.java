package com.tests.junit.services;

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

        if(filme.getEstoque() == 0){
            throw new Exception("Estoque é 0");
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

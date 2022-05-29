package com.tests.junit.builders;

import com.tests.junit.model.Filme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adriano Rabello 29/05/2022 14:22:55
 **/
public class FilmeBuilder {


    private Filme filme;

    private FilmeBuilder(){

    }

    public static FilmeBuilder filmeBuilder(){
      FilmeBuilder builder = new FilmeBuilder();
      builder.filme = new Filme("Filme 01",1,4.0);
      return builder;
    }

    public Filme umFilme(){
        return  filme;
    }

    public FilmeBuilder semEstoque(){
        filme.setEstoque(0);
        return this;
    }

    public List<Filme> variosFilmes(Integer quantidade){

        List<Filme> filmes = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            filmes.add(umFilme());
        }
        return filmes;
    }

    public FilmeBuilder setPreco(double preco){
        filme.setPrecoLocacao(preco);
        return this;
    }


}

package com.tests.junit.builders;

import com.tests.junit.model.Locacao;
import com.tests.junit.model.Usuario;
import com.tests.junit.utils.DataUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Adriano Rabello 29/05/2022 16:52:30
 **/
public class LocacaoBuilder {

    private Locacao locacao;

    private LocacaoBuilder(){

    }

    public static LocacaoBuilder umaLocacao(){

        LocacaoBuilder builder = new LocacaoBuilder();
        builder.locacao = new Locacao(UsuarioBuilder.umUsuario().agora(),
                FilmeBuilder.filmeBuilder().variosFilmes(1),
                DataUtils.adicionarDias(new Date(),0),
                DataUtils.criarDataDevolucao(),4.0);

        return builder;
    }

    public Locacao agora(){
        return locacao;
    }



    public LocacaoBuilder comValor(double valor){
        locacao.setValor(valor);
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date dataLocacao){
        locacao.setDataLocacao(dataLocacao);
        return this;
    }

    public LocacaoBuilder comDataretorno(Date dataRetorno){
        locacao.setDataRetorno(dataRetorno);
        return this;
    }

    public LocacaoBuilder comAtraso(){
        locacao.setDataLocacao(DataUtils.adicionarDias(new Date(),-2));
        locacao.setDataRetorno(DataUtils.adicionarDias(new Date(),-4));
        return this;
    }

    public LocacaoBuilder comUsuario(Usuario usuario){
        locacao.setUsuario(usuario);
        return this;
    }


    public List<Locacao> variasLocacoes(Integer quantidade){
        List<Locacao> locacoes = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            locacoes.add(agora());
        }
        return locacoes;
    }
}

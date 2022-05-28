package com.tests.junit.model;

import lombok.*;

import java.util.Objects;

/**
 * @author Adriano Rabello 27/05/2022 19:47:19
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    private String nome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nome, usuario.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}

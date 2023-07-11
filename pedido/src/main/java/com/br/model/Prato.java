package com.br.model;

import org.bson.types.Decimal128;

import java.math.BigDecimal;

public class Prato {

    private String nome;

    private String descricao;

    private Restaurante restaurante;

    private Decimal128 preco;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Decimal128 getPreco() {
        return preco;
    }

    public void setPreco(Decimal128 preco) {
        this.preco = preco;
    }
}
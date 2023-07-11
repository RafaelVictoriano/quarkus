package br.com.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Prato extends PanacheEntity {

    private String nome;

    private String descricao;

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "prato_id"),
            inverseJoinColumns = @JoinColumn(name = "carrinho_id"))
    private List<Carrinho> carrinho;

    @ManyToOne()
    private Restaurante restaurante;

    public List<Carrinho> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<Carrinho> carrinho) {
        this.carrinho = carrinho;
    }

    private BigDecimal preco;


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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public static Uni<List<Prato>> findAllPratos() {
        return Prato.listAll();
    }

    public static Uni<List<Prato>> finPratosByIdRestaurante(final Long id) {
        return Prato.list("restaurante.id", id);
    }

}
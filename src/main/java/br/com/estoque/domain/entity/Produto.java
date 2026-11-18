package br.com.estoque.domain.entity;

import br.com.estoque.domain.exception.ProdutoInvalidoException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Entidade de domínio. Contém as regras de negócio do produto,
 * sem depender de nenhuma tecnologia (UI, banco, framework).
 */
public class Produto {

    private final Long id;
    private final String nome;
    private final int quantidade;
    private final BigDecimal valorUnitario;

    public Produto(Long id, String nome, int quantidade, BigDecimal valorUnitario) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ProdutoInvalidoException("O nome do produto é obrigatório.");
        }
        if (quantidade < 0) {
            throw new ProdutoInvalidoException("A quantidade não pode ser negativa.");
        }
        if (valorUnitario == null || valorUnitario.signum() < 0) {
            throw new ProdutoInvalidoException("O valor unitário não pode ser negativo.");
        }
        this.id = id;
        this.nome = nome.trim();
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario.setScale(2, RoundingMode.HALF_UP);
    }

    /** Regra de negócio: valor total = quantidade x valor unitário. */
    public BigDecimal getValorTotal() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public Produto comId(Long novoId) {
        return new Produto(novoId, nome, quantidade, valorUnitario);
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
}

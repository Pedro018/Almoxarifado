package br.com.estoque.application.dto;

import java.math.BigDecimal;

/** Objeto de transferência entre a camada de apresentação e os casos de uso. */
public record ProdutoDTO(Long id, String nome, int quantidade,
                         BigDecimal valorUnitario, BigDecimal valorTotal) {
}

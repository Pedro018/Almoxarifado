package br.com.estoque.domain.repository;

import br.com.estoque.domain.entity.Produto;

import java.util.List;

/**
 * Porta (interface) definida no domínio e implementada pela infraestrutura.
 * Inversão de dependência: o núcleo não conhece o mecanismo de persistência.
 */
public interface ProdutoRepository {
    Produto salvar(Produto produto);
    List<Produto> listarTodos();
}

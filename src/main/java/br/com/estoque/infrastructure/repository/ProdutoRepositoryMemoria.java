package br.com.estoque.infrastructure.repository;

import br.com.estoque.domain.entity.Produto;
import br.com.estoque.domain.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação em memória. Para usar banco de dados, basta criar outra
 * implementação de ProdutoRepository (ex.: JDBC) e trocá-la no Main.
 */
public class ProdutoRepositoryMemoria implements ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();
    private final AtomicLong sequencia = new AtomicLong(1);

    @Override
    public synchronized Produto salvar(Produto produto) {
        Produto salvo = produto.comId(sequencia.getAndIncrement());
        produtos.add(salvo);
        return salvo;
    }

    @Override
    public synchronized List<Produto> listarTodos() {
        return List.copyOf(produtos);
    }
}

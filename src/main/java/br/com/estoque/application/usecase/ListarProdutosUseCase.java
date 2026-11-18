package br.com.estoque.application.usecase;

import br.com.estoque.application.dto.ProdutoDTO;
import br.com.estoque.domain.repository.ProdutoRepository;

import java.util.List;

public class ListarProdutosUseCase {

    private final ProdutoRepository repository;

    public ListarProdutosUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoDTO> executar() {
        return repository.listarTodos().stream().map(ProdutoMapper::paraDTO).toList();
    }
}

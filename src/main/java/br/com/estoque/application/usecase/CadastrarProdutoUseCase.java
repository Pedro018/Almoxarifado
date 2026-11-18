package br.com.estoque.application.usecase;

import br.com.estoque.application.dto.ProdutoDTO;
import br.com.estoque.domain.entity.Produto;
import br.com.estoque.domain.repository.ProdutoRepository;

import java.math.BigDecimal;

public class CadastrarProdutoUseCase {

    private final ProdutoRepository repository;

    public CadastrarProdutoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoDTO executar(String nome, int quantidade, BigDecimal valorUnitario) {
        Produto produto = new Produto(null, nome, quantidade, valorUnitario); // valida regras
        return ProdutoMapper.paraDTO(repository.salvar(produto));
    }
}

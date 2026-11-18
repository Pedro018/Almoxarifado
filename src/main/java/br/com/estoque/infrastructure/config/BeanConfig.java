package br.com.estoque.infrastructure.config;

import br.com.estoque.application.usecase.CadastrarProdutoUseCase;
import br.com.estoque.application.usecase.ListarProdutosUseCase;
import br.com.estoque.domain.repository.ProdutoRepository;
import br.com.estoque.infrastructure.repository.ProdutoRepositoryMemoria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition root: o Spring só aparece aqui e na camada web.
 * Domain e application continuam sem nenhuma anotação do framework.
 */
@Configuration
public class BeanConfig {

    @Bean
    public ProdutoRepository produtoRepository() {
        return new ProdutoRepositoryMemoria();
    }

    @Bean
    public CadastrarProdutoUseCase cadastrarProdutoUseCase(ProdutoRepository repository) {
        return new CadastrarProdutoUseCase(repository);
    }

    @Bean
    public ListarProdutosUseCase listarProdutosUseCase(ProdutoRepository repository) {
        return new ListarProdutosUseCase(repository);
    }
}

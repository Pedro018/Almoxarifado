package br.com.estoque.application.usecase;

import br.com.estoque.application.dto.ProdutoDTO;
import br.com.estoque.domain.entity.Produto;

final class ProdutoMapper {
    private ProdutoMapper() {}

    static ProdutoDTO paraDTO(Produto p) {
        return new ProdutoDTO(p.getId(), p.getNome(), p.getQuantidade(),
                p.getValorUnitario(), p.getValorTotal());
    }
}

package br.com.estoque.presentation.controller;

import br.com.estoque.application.dto.ProdutoDTO;
import br.com.estoque.application.usecase.CadastrarProdutoUseCase;
import br.com.estoque.application.usecase.ListarProdutosUseCase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Adapta a entrada da UI (textos) para os casos de uso. */
public class ProdutoController {

    private final CadastrarProdutoUseCase cadastrar;
    private final ListarProdutosUseCase listar;

    public ProdutoController(CadastrarProdutoUseCase cadastrar, ListarProdutosUseCase listar) {
        this.cadastrar = cadastrar;
        this.listar = listar;
    }

    public ProdutoDTO cadastrar(String nome, String quantidadeTexto, String valorUnitarioTexto) {
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeTexto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantidade inválida. Informe um número inteiro.");
        }
        return cadastrar.executar(nome, quantidade, converterValor(valorUnitarioTexto));
    }

    public List<ProdutoDTO> listar() {
        return listar.executar();
    }

    /** Calcula o total para pré-visualização na tela; retorna null se os campos forem inválidos. */
    public BigDecimal calcularTotal(String quantidadeTexto, String valorUnitarioTexto) {
        try {
            int q = Integer.parseInt(quantidadeTexto.trim());
            return converterValor(valorUnitarioTexto).multiply(BigDecimal.valueOf(q));
        } catch (RuntimeException e) {
            return null;
        }
    }

    private BigDecimal converterValor(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe o valor unitário.");
        }
        String limpo = texto.trim().replace("R$", "").replace(" ", "");
        if (limpo.contains(",")) {                     // formato brasileiro: 1.234,56
            limpo = limpo.replace(".", "").replace(",", ".");
        }
        try {
            return new BigDecimal(limpo).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor unitário inválido. Ex.: 12,50");
        }
    }
}

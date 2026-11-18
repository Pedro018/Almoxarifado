package br.com.estoque;

import br.com.estoque.application.usecase.CadastrarProdutoUseCase;
import br.com.estoque.application.usecase.ListarProdutosUseCase;
import br.com.estoque.domain.repository.ProdutoRepository;
import br.com.estoque.infrastructure.repository.ProdutoRepositoryMemoria;
import br.com.estoque.presentation.controller.ProdutoController;
import br.com.estoque.presentation.view.JanelaPrincipal;

import javax.swing.*;

/** Composition root: único lugar que conhece todas as camadas e monta as dependências. */
public class Main {
    public static void main(String[] args) {
        ProdutoRepository repository = new ProdutoRepositoryMemoria();
        ProdutoController controller = new ProdutoController(
                new CadastrarProdutoUseCase(repository),
                new ListarProdutosUseCase(repository));

        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new JanelaPrincipal(controller).setVisible(true);
        });
    }
}

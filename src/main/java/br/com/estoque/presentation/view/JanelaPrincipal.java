package br.com.estoque.presentation.view;

import br.com.estoque.presentation.controller.ProdutoController;

import javax.swing.*;

/** Janela que hospeda as duas telas em abas. */
public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal(ProdutoController controller) {
        super("Controle de Estoque");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ListaProdutosView lista = new ListaProdutosView(controller);
        CadastroProdutoView cadastro = new CadastroProdutoView(controller, lista::recarregar);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastro", cadastro);
        abas.addTab("Produtos", lista);
        abas.addChangeListener(e -> { if (abas.getSelectedComponent() == lista) lista.recarregar(); });

        setContentPane(abas);
        setSize(640, 420);
        setLocationRelativeTo(null);
    }
}

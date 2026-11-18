package br.com.estoque.presentation.web;

import br.com.estoque.application.dto.ProdutoDTO;
import br.com.estoque.application.usecase.CadastrarProdutoUseCase;
import br.com.estoque.application.usecase.ListarProdutosUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
public class ProdutoWebController {

    private final CadastrarProdutoUseCase cadastrar;
    private final ListarProdutosUseCase listar;

    public ProdutoWebController(CadastrarProdutoUseCase cadastrar, ListarProdutosUseCase listar) {
        this.cadastrar = cadastrar;
        this.listar = listar;
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/cadastro";
    }

    /** Tela 1: formulário de cadastro. */
    @GetMapping("/cadastro")
    public String telaCadastro(Model model) {
        model.addAttribute("form", new ProdutoForm());
        model.addAttribute("pagina", "cadastro");
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute("form") ProdutoForm form,
                            Model model, RedirectAttributes redirect) {
        try {
            ProdutoDTO criado = cadastrar.executar(
                    form.getNome(),
                    converterQuantidade(form.getQuantidade()),
                    converterValor(form.getValorUnitario()));
            redirect.addFlashAttribute("sucesso", "Produto \"" + criado.nome() + "\" cadastrado com sucesso.");
            return "redirect:/produtos";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("pagina", "cadastro");
            return "cadastro";
        }
    }

    /** Tela 2: tabela com todos os produtos. */
    @GetMapping("/produtos")
    public String telaProdutos(Model model) {
        List<ProdutoDTO> produtos = listar.executar();
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalGeral",
                produtos.stream().map(ProdutoDTO::valorTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("totalUnidades",
                produtos.stream().mapToInt(ProdutoDTO::quantidade).sum());
        model.addAttribute("pagina", "produtos");
        return "produtos";
    }

    private int converterQuantidade(String texto) {
        try {
            return Integer.parseInt(texto == null ? "" : texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantidade inválida. Informe um número inteiro.");
        }
    }

    private BigDecimal converterValor(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe o valor unitário.");
        }
        String limpo = texto.trim().replace("R$", "").replace(" ", "");
        if (limpo.contains(",")) {                    // formato brasileiro: 1.234,56
            limpo = limpo.replace(".", "").replace(",", ".");
        }
        try {
            return new BigDecimal(limpo).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor unitário inválido. Ex.: 12,50");
        }
    }
}

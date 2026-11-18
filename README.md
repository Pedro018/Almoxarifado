# Almoxarifado
Sistema de gerenciamento de almoxarifado.
Almoxarifado — Controle de Estoque

Aplicação web em Java 25 + Spring Boot + Thymeleaf para gerenciar o estoque de produtos, construída com Clean Architecture em camadas.

Funcionalidades
Tela	Rota	O que faz
Cadastro	/cadastro	Formulário com Nome, Quantidade, Valor unitário e Valor total (calculado automaticamente, somente leitura).
Produtos	/produtos	Tabela com Nome, Quantidade, Valor unitário e Valor total de todos os produtos, mais totais gerais (produtos, unidades e valor em estoque).
Valor total = quantidade × valor unitário (regra no domínio, Produto.getValorTotal()).
Aceita valores no formato brasileiro (1.234,50) e com ponto (1234.50).
Validações: nome obrigatório, quantidade inteira não negativa, valor unitário não negativo.
Tema claro/escuro automático conforme o sistema do usuário.
Tecnologias
Java 25
Spring Boot 3.3 (Web + Thymeleaf)
Maven
HTML/CSS/JS puros nas views (sem dependência de front-end)
Como executar

Pré-requisitos: JDK 25+ e Maven 3.8+.

bash
mvn spring-boot:run

Acesse: http://localhost:8080

Para gerar o JAR executável:

bash
mvn clean package
java -jar target/controle-estoque-web-1.0.0.jar

A porta pode ser alterada em src/main/resources/application.properties (server.port).

Arquitetura
src/main/java/br/com/estoque
├── domain                 # Regras de negócio. Não conhece Spring.
│   ├── entity             #   Produto
│   ├── exception          #   ProdutoInvalidoException
│   └── repository         #   ProdutoRepository (porta/interface)
├── application            # Casos de uso. Não conhece Spring.
│   ├── usecase            #   CadastrarProdutoUseCase, ListarProdutosUseCase
│   └── dto                #   ProdutoDTO
├── infrastructure         # Detalhes técnicos
│   ├── repository         #   ProdutoRepositoryMemoria (implementa a porta)
│   └── config             #   BeanConfig (registra os beans no Spring)
├── presentation
│   └── web                #   ProdutoWebController, ProdutoForm
└── EstoqueApplication     # Ponto de entrada

src/main/resources
├── templates              # cadastro.html, produtos.html, layout.html (Thymeleaf)
└── static                 # css/estoque.css, js/cadastro.js
Regra de dependência
presentation ──► application ──► domain ◄── infrastructure
domain e application não possuem nenhuma anotação ou import do Spring.
infrastructure implementa as interfaces definidas no domain (inversão de dependência).
O Spring aparece apenas em BeanConfig e na camada presentation.web.
Fluxo de uma requisição
POST /cadastro chega ao ProdutoWebController.
O controller converte o texto do formulário e chama CadastrarProdutoUseCase.
O caso de uso cria a entidade Produto (que valida as regras) e usa ProdutoRepository para salvar.
Em caso de sucesso, redireciona para /produtos; em caso de erro, volta ao formulário com a mensagem.
Persistência

Os dados ficam em memória e são perdidos ao reiniciar a aplicação.

Para usar banco de dados, basta:

Criar uma classe que implemente ProdutoRepository (por exemplo, com Spring Data JPA ou JDBC).
Trocar o bean produtoRepository() em infrastructure/config/BeanConfig.java.

Nenhuma alteração é necessária no domínio, nos casos de uso ou nas telas.

Próximos passos sugeridos
Editar e excluir produtos
Persistência com PostgreSQL/H2 via JPA
Testes unitários dos casos de uso e testes de integração do controller
Busca e ordenação na tabela de produtos

Feito por Davi Rocha, Eduardo Qi, e Pedro Henrique

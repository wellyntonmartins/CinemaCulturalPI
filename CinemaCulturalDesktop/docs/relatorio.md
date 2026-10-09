# Relatório Técnico — Etapa 6 do Projeto Integrador

## 1. Projeto desenvolvido

Foi criado o Cinema Cultural Desktop, uma aplicação Java Swing para cadastro e manutenção de filmes e análises. Como o código e o repositório da fase desktop anterior não estavam disponíveis, este projeto foi refeito como uma nova implementação, aproveitando o domínio de filmes e análises. A aplicação utiliza armazenamento em memória e não depende de um banco de dados.

## 2. Princípios SOLID aplicados

### Responsabilidade Única (SRP)

- `Filme` e `Analise` representam os dados do domínio.
- `FilmeService` concentra regras e validações de filmes.
- `AnaliseService` concentra regras e validações das análises.
- `FilmeRepository` e `AnaliseRepository` definem contratos de armazenamento.
- `FilmeRepositoryMemoria` e `AnaliseRepositoryMemoria` implementam o armazenamento.
- `FilmeController` e `AnaliseController` expõem operações para a interface.
- `TelaPrincipal`, `PainelFilmes`, `PainelAnalises` e os formulários tratam da interface gráfica.
- `TestesAplicacao` valida o comportamento básico sem abrir a interface.

Cada classe tem um motivo principal para mudar. Por exemplo, trocar a interface Swing não exige alterar as regras de validação de filmes.

### Aberto/Fechado (OCP)

As interfaces `FilmeRepository` e `AnaliseRepository` permitem adicionar outra implementação de armazenamento sem modificar os serviços, desde que o novo tipo implemente os mesmos contratos. A versão atual usa implementações em memória.

### Substituição de Liskov (LSP)

Os serviços dependem das interfaces de repositório, não das classes concretas. Uma implementação alternativa que respeite os contratos pode substituir as implementações em memória sem alterar o contrato esperado pelos serviços.

### Segregação de Interfaces (ISP)

Os contratos de repositório foram divididos por responsabilidade: um gerencia filmes e outro gerencia análises. Assim, uma classe não precisa depender de operações que não correspondem ao tipo de dado que administra.

### Inversão de Dependência (DIP)

`FilmeService` e `AnaliseService` recebem interfaces por construtor. Eles não instanciam diretamente `FilmeRepositoryMemoria` ou `AnaliseRepositoryMemoria`. A composição das implementações concretas acontece na classe principal.

## 3. Refatorações e code smells evitados

- Separação da interface gráfica das regras de negócio e do acesso aos dados.
- Remoção da ideia de concentrar cadastro, validação, armazenamento e exibição em uma única classe.
- Eliminação de dependência direta dos serviços em classes concretas de armazenamento.
- Centralização das validações de filmes e análises nos serviços.
- Extração dos formulários em classes próprias para evitar duplicar lógica de entrada de dados na janela principal.
- Uso de interfaces de repositório para reduzir acoplamento.
- Exclusão das análises associadas no serviço de filmes para evitar registros órfãos.
- Testes de negócio executáveis sem abrir a interface Swing.

Como o código original da fase anterior não estava disponível, não foi possível identificar ou comparar code smells específicos daquele código. As refatorações descritas dizem respeito à estrutura criada nesta nova implementação.

## 4. Padrão de projeto utilizado

Foi utilizado o padrão Repository. Os serviços acessam contratos de repositório, enquanto as implementações concretas cuidam de guardar e recuperar os objetos. Isso reduz o acoplamento e permite substituir o armazenamento em memória por outra solução sem reescrever as regras de negócio.

## 5. Testes realizados

A classe `TestesAplicacao` verifica:

1. Cadastro e geração de ID do filme.
2. Listagem de filmes.
3. Edição de filme.
4. Cadastro e listagem de análise.
5. Edição de análise.
6. Rejeição de nota maior que 10.
7. Exclusão de filme e remoção das análises associadas.

Para executar, rode `TestesAplicacao` pelo NetBeans. A mensagem esperada ao final é:

`Testes concluídos: cadastro, listagem, edição, validação e exclusão de filmes e análises.`

## 6. Repositório GitHub

Após criar o repositório GitHub, inserir aqui:

- URL do repositório: `PREENCHER COM A URL REAL`.
- Evidência 1: captura da página inicial do repositório.
- Evidência 2: captura da árvore de arquivos e dos commits.

As capturas devem ser adicionadas antes da entrega. Não foi criado um repositório remoto automaticamente; a publicação precisa ser feita na conta do aluno.

## 7. Conclusão

O projeto separa domínio, regras de negócio, contratos de armazenamento, controladores e interface gráfica. A organização facilita testar o comportamento sem depender da interface e permite trocar a implementação de armazenamento com menos alterações. A limitação principal é que os dados são temporários e desaparecem ao encerrar o programa.

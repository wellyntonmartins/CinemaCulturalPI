# Cinema Cultural Desktop

Aplicação desktop em Java Swing para cadastrar, editar, listar e excluir filmes e suas análises. O projeto foi estruturado em camadas para manter a interface separada das regras de negócio e do armazenamento.

## Requisitos

- JDK 17 ou superior
- Apache NetBeans com suporte a Maven

## Abrir no NetBeans

1. Extraia a pasta do projeto.
2. No NetBeans, selecione **File > Open Project**.
3. Escolha a pasta `CinemaCulturalDesktop` que contém o `pom.xml`.
4. Aguarde a leitura das dependências/plugins do Maven.
5. Execute `br.com.cinemacultural.CinemaCulturalApplication`.

## Funcionalidades

- Cadastro, edição, listagem e exclusão de filmes.
- Cadastro, edição, listagem e exclusão de análises.
- Nota de análise validada entre 0 e 10.
- Exclusão das análises vinculadas quando um filme é removido.
- Armazenamento em memória: os dados são perdidos ao encerrar a aplicação.

## Testes

Execute a classe `br.com.cinemacultural.TestesAplicacao` pelo NetBeans, usando **Run File**. Ela executa testes de cadastro, listagem, edição, validação e exclusão sem abrir a janela Swing.

Também é possível executar o modo de teste pelo argumento `--test` ao iniciar a classe `CinemaCulturalApplication`.

## Organização

- `model`: objetos de domínio.
- `repository`: contratos de armazenamento.
- `repository.memory`: implementação em memória dos repositórios.
- `service`: validação e regras de negócio.
- `controller`: operações chamadas pela interface.
- `view`: telas e formulários Swing.

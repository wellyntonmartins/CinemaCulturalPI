# Cinema Cultural Desktop

Aplicação desktop em Java Swing para cadastrar, editar, listar e excluir filmes e suas análises. O projeto separa interface, regras de negócio e armazenamento em memória.

## Requisitos

- JDK 17 ou superior
- Apache NetBeans com suporte a Maven
- Acesso à internet na primeira execução para baixar JUnit 5 pelo Maven

## Abrir no NetBeans

1. Extraia a pasta do projeto.
2. No NetBeans, escolha **File > Open Project**.
3. Selecione a pasta `CinemaCulturalDesktop`, que contém o `pom.xml`.
4. Se o NetBeans pedir, recarregue o projeto Maven e aguarde o download das dependências.
5. Execute `br.com.cinemacultural.CinemaCulturalApplication` para abrir a aplicação.

## Funcionalidades

- Cadastro, edição, listagem e exclusão de filmes.
- Cadastro, edição, listagem e exclusão de análises.
- Nota de análise validada entre 0 e 10.
- Exclusão das análises vinculadas quando um filme é removido.
- Armazenamento em memória: os dados são perdidos ao encerrar a aplicação.

## Testes unitários

Os testes JUnit 5 estão em `src/test/java/br/com/cinemacultural/service` e verificam as regras de negócio sem abrir a interface Swing nem acessar banco de dados.

No NetBeans, use **Test > Test Project** ou clique com o botão direito no projeto e escolha **Test**. Para rodar uma classe individual, abra `FilmeServiceTest.java` ou `AnaliseServiceTest.java` e selecione **Test File**.

Se houver Maven disponível no terminal, execute:

```bash
mvn test
```

O resultado e os relatórios de execução ficam em `target/surefire-reports`.

## Organização

- `model`: objetos de domínio.
- `repository`: contratos de armazenamento.
- `repository.memory`: implementação em memória dos repositórios.
- `service`: validação e regras de negócio.
- `controller`: operações chamadas pela interface.
- `view`: telas e formulários Swing.
<<<<<<< HEAD
=======
- `src/test`: testes automatizados JUnit 5.

## Versionamento para a Etapa 7

No repositório GitHub criado na etapa anterior, inclua as alterações e faça um commit próprio para os testes. Na entrega, guarde capturas da página do repositório e do commit que contém `src/test/java` e o `pom.xml` atualizado.

```bash
git add .
git commit -m "Adiciona testes unitarios com JUnit"
git push
```
>>>>>>> 77c3f3b (Adiciona testes unitarios com JUnit)

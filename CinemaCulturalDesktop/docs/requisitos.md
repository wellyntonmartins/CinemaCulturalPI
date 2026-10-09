# Documento de Projeto de Software — Cinema Cultural Desktop

## 1. Objetivo

Criar uma aplicação desktop simples para organizar filmes de uma casa cultural e registrar análises com notas, separando a interface, as regras do sistema e o armazenamento para facilitar manutenção e futuras mudanças.

## 2. Escopo

A aplicação permite cadastrar, consultar, editar e excluir filmes. Cada filme pode ter uma ou mais análises, contendo texto e nota de 0 a 10. A exclusão de um filme remove suas análises associadas.

Os dados ficam em memória durante a execução. A persistência em arquivo ou banco de dados não faz parte do escopo desta versão.

## 3. Requisitos funcionais

- RF01: cadastrar filme com título, sinopse, gênero e ano de lançamento.
- RF02: listar os filmes cadastrados.
- RF03: editar dados de um filme existente.
- RF04: excluir um filme.
- RF05: cadastrar análise para um filme existente.
- RF06: listar análises do filme selecionado.
- RF07: editar uma análise.
- RF08: excluir uma análise.
- RF09: validar os campos obrigatórios dos filmes.
- RF10: validar o texto da análise e aceitar nota somente entre 0 e 10.
- RF11: remover análises vinculadas quando um filme for excluído.

## 4. Requisitos não funcionais

- RNF01: interface desktop desenvolvida com Java Swing.
- RNF02: código organizado em camadas para separar responsabilidades.
- RNF03: aplicação compatível com Java 17 ou superior.
- RNF04: código de negócio testável sem iniciar a interface gráfica.
- RNF05: não utilizar bibliotecas externas para as funções principais.
- RNF06: dados são mantidos somente durante a execução da aplicação.

## 5. Regras de negócio

- O título, sinopse e gênero do filme não podem ficar vazios.
- O ano deve ser igual ou posterior a 1888 e não pode ultrapassar dez anos além do ano atual.
- Uma análise deve conter texto.
- A nota deve ser um número finito entre 0 e 10.
- Uma análise deve estar vinculada a um filme existente.
- Excluir um filme também exclui suas análises.

## 6. Usuários, limitações e melhorias futuras

O público-alvo são usuários internos da casa cultural que precisam manter o catálogo de filmes e suas análises. Como os dados ficam em memória, eles são perdidos quando o programa é encerrado. Em uma versão futura, pode ser implementada persistência com banco de dados ou arquivo, além de autenticação e exportação de relatórios.

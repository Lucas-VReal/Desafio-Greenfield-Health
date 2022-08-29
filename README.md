# Desafio-Greenfield-Health 🏥


## Introdução

A Greenfield Health precisa disponibilizar um serviço para um hospital em que médicos possam
cadastrar prescrições médicas para pacientes que realizam uma consulta e necessitam de
uma receita com os devidos medicamentos. 
<br></br>

## Requisitos Técnicos
Na reunião técnica sobre o sistema, ficou decidido que o hospital iria se integrar com o sistema da Greenfield Health via API REST. A aplicação deve ser construída com as seguintes tecnologias:
<br></br>
- Backend: Java com Spring
- Frontend: JS com React
- Banco de dados: SQL com Postgres
<br></br>
Foram definidos os seguintes requisitos para o *backend*:
<br></br>
- Testes unitários com JUnit com cobertura acima de 60% sobre as funcionalidades disponibilizadas
- Documentação da API com Swagger
<br></br>
Foram definidos os seguintes requisitos para o *frontend*:
<br></br>
- Utilização de componentes baseados em função
- Utilização dos hooks useState e useEffect
- Utilização de alguma biblioteca de design (Bootstrap, PrimeFaces, MUI, Tailwind, etc)
<br></br>
## Requisitos Não Técnicos
Alguns requisitos não técnicos foram pré-estabelecidos, como por exemplo:
<br></br>

### Médico
- Deve ser possível cadastrar um médico
- Deve ser possível alterar um médico
- Deve ser possível obter todos os médicos
- Deve ser possível obter um médico
- Deve ser possível deletar um médico, e em consequência, deletar as respectivas prescrições médicas em cascata
- Dever ser possível autenticar um médico
<br></br>
### Paciente
- Deve ser possível cadastrar um paciente
- Deve ser possível alterar um paciente
- Deve ser possível obter todos os pacientes
- Deve ser possível obter um paciente
- Deve ser possível deletar um paciente, e em consequência, deletar as respectivas prescrições médicas em cascata
<br></br>
### Prescrição
- Deve ser possível cadastrar uma prescrição médica
- Deve ser possível alterar uma prescrição médica
- Deve ser possível obter todas as prescrições médicas de um médico
- Deve ser possível obter todas as prescrições médicas de um paciente
- Deve ser possível obter uma prescrição médica
- Deve ser possível deletar uma prescrição médica
- Uma prescrição médica não pode ser consultada, alterada ou deletada por um médico que não a cadastrou, apenas o médico que emitiu a receita pode realizar essas ações
<br></br>
## Modelagem
Com os requisitos técnicos pré-estabelecidos a Greenfield Health poderá seguir com o planejamento e desenvolvimento de acordo com o seguinte modelo de dados:
<br></br>
- Médico 🩺
<br></br>
  - CPF
  - Email
  - Nome
  - Data de nascimento
  - CRM
  - Estado de registro do CRM
  - Sexo
  - Senha
  - Confirmação da Senha
 <br></br>
- Paciente 😷🤕
<br></br>
  - CPF
  - Nome
  - Data de nascimento
  - Sexo
 <br></br>
- Medicamentos 💉💊
<br></br>
  - Descrição
  - Quantidade
  - Dosagem
  - Frequência de uso
 <br></br>
- Prescrição médica 📑
<br></br>
  - Médico
  - Paciente
  - Medicamentos
<br></br>

## O Desafio 🚀
Sabendo disto, você foi contratado para que possa seguir com o desenvolvimento das funcionalidades planejadas para a primeira entrega da Greenfield Health ao hospital contratante
do serviço. 
<<br></br>
Crie uma aplicação com os requisitos técnicos levantados pela equipe técnica do hospital. Você pode utilizar quaisquer outras bibliotecas que desejar. Use a criatividade, buscando sempre respeitar os requisitos e as melhores práticas de desenvolvimento.
<br></br>
Itens opcionais:
<br></br>
## Backend:
  - Docker
  - JWT
  - DTOs
  - Lombok
  - Ciframento da senha
  - Tratamento de erros global
  - Testes de integração
  - Arquitetura Hexagonal
  - Hospedagem da API
<br></br>
## Frontend:
  - useContext
  - React Hook Form
  - MobX
  - Vite
  - Storybook
  - Hospedagem do sistema
<br></br>
## Entrega 📬
Após o término do projeto, enviar arquivo compactado para:
brunno.oliver7@gmail.com
<br></br>
Faça um bom teste e boa sorte!

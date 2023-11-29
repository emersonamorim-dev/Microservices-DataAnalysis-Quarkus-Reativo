# Microservices Java Quarkus Reativo 🚀


Codificação desenvolvido com programação reativa que consiste em dois microserviços, `account-service` e `transaction-service`, que são usados para análise de dados bancários. Ambos os microserviços são construídos usando Java com o framework Quarkus e o banco de dados Oracle. A aplicação está integrada com Kubernetes e Docker o que facilita rodar a aplicação em conteriner.

## Pré-requisitos

- Java 11
- Maven
- Docker
- Kubernetes
- Terraform

## Como executar localmente

1. Clone o repositório:

git clone https://github.com/seuusuario/data-analysis-microservices.git
cd data-analysis-microservices


2. Construa os microserviços:


cd account-service
mvn package
cd ../transaction-service
mvn package


3. Construa as imagens Docker:


cd ../account-service
docker build -t account-service .
cd ../transaction-service
docker build -t transaction-service .


4. Inicie os microserviços usando Docker Compose:

docker-compose up

## Como implantar no Kubernetes

1. Crie a infraestrutura usando Terraform:


cd terraform
terraform init
terraform apply

2. Implante os microserviços no Kubernetes:

kubectl apply -f kubernetes/account-service.yaml
kubectl apply -f kubernetes/transaction-service.yaml


## API

Os microserviços expõem as seguintes APIs:

- `account-service`:
  - `POST /accounts`: Cria uma nova conta.
  - `GET /accounts/{id}`: Obtém uma conta pelo ID.
  - `PUT /accounts/{id}/balance\`: Atualiza o saldo de uma conta.
  - `DELETE /accounts/{id}`: Exclui uma conta.

- `transaction-service`:
  - `POST /transactions`: Cria uma nova transação.
  - `GET /transactions/{id}`: Obtém uma transação pelo ID.
  - `PUT /transactions/{id}\`: Atualiza uma transação.
  - `DELETE /transactions/{id}`: Exclui uma transação.

## Autor
Emerson Amorim

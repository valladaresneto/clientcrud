This project was made for the purpose to show how to use some technologies, such as:

 - Spring Boot
 - Spring Data
 - Spring Security
 - Docker
 - Docker-compose
 - Swagger

The module client-service provides a REST API with endpoints to find, create and update clients.

It is possible to filter by any client field, paginate and ordinate. 

To see the API documentation:

- http://localhost:5000/api/v1/swagger-ui-custom.html
- https://clients-service-101.herokuapp.com/api/v1/swagger-ui-custom.html

To run the project in the main directory run the following commands:

 - In the "client-service" directory:
    - mvn clean install
 - In the root directory:
    - docker-compose build
    - docker-compose up -d

REST requests examples can be found in the file bellow:
 - client-service.http 

Deploy on Heroku:
 - heroku auth:token
 - docker login --username=login password=(token from the command above) registry.heroku.com
 - docker tag clientcrud_client-service:latest registry.heroku.com/clients-service-101/web
 - docker push registry.heroku.com/clients-service-101/web
 - heroku container:release web -a clients-service-101

==========================================

Processo criativo do projeto:

- Definição pelo uso do Spring Boot para a disponibilização do serviço REST de Cliente.
- Definição de onde salvar os dados.
- Criação da infraestrutura utilizando docker e docker-compose (dois containers: banco e serviço REST de Cliente).
- Definição do objeto Cliente e das validações necessárias.
- Definição dos endpoints necessários.
- Criação da camada de serviço independente da camada controller.
- Segurança da API (implementada com Spring Security, mas desabilitada para facilitar o acesso à documentação da API no swagger).
- Criação da estrutura para testes de integração e implementação dos cenários de testes.
- Definição pelo uso do Heroku como serviço de Cloud. 
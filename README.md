# Task Manager API

API REST para gerenciamento de tarefas desenvolvida com Spring Boot.

## 🚀 Tecnologias

- Java 17
- Spring Boot 4.0.1
- Spring Data JPA
- H2 Database (em memória)
- Lombok
- Maven

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+

## 🏃 Como Executar

### 1. Compilar o projeto
```bash
mvn clean install
```

### 2. Executar a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 3. Acessar Console H2
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:taskdb`
- Usuário: `sa`
- Senha: (deixe em branco)

## 📚 Documentação da API

### Endpoints

- `GET /api/tasks` - Listar todas as tarefas
- `GET /api/tasks?status={status}` - Listar tarefas por status
- `GET /api/tasks/{id}` - Buscar tarefa por ID
- `POST /api/tasks` - Criar nova tarefa
- `PUT /api/tasks/{id}` - Atualizar tarefa
- `DELETE /api/tasks/{id}` - Deletar tarefa

### Modelo de Dados

```json
{
  "id": 1,
  "title": "Título da tarefa",
  "description": "Descrição da tarefa",
  "status": "PENDING",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

**Status disponíveis:** `PENDING`, `COMPLETED`

## 🧪 Testes

### Executar testes automatizados
```bash
mvn test
```

### Testar manualmente

Consulte o arquivo [TESTES.md](./TESTES.md) para exemplos detalhados de como testar a API usando curl, Postman ou outras ferramentas.

## 📝 Exemplo de Uso

### Criar uma tarefa
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Minha primeira tarefa",
    "description": "Descrição da tarefa",
    "status": "PENDING"
  }'
```

### Listar todas as tarefas
```bash
curl -X GET http://localhost:8080/api/tasks
```

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/sabakeviski/task_manager_api/
│   │   ├── controller/     # Controllers REST
│   │   ├── service/         # Lógica de negócio
│   │   ├── repository/     # Repositórios JPA
│   │   ├── model/          # Entidades
│   │   ├── dto/            # Data Transfer Objects
│   │   └── exception/      # Tratamento de exceções
│   └── resources/
│       └── application.properties
└── test/                   # Testes
```

## 📄 Licença

Este projeto é open source e está disponível sob a licença MIT.
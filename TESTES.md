# Guia de Testes - Task Manager API

Este documento explica como testar a API de gerenciamento de tarefas.

## 🚀 Como Executar a Aplicação

### 1. Compilar e Executar

```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 2. Console H2 (Banco de Dados em Memória)

Acesse o console do H2 em: `http://localhost:8080/h2-console`

**Configurações de conexão:**
- JDBC URL: `jdbc:h2:mem:taskdb`
- Usuário: `sa`
- Senha: (deixe em branco)

## 📋 Endpoints da API

Base URL: `http://localhost:8080/api/tasks`

### 1. Criar Tarefa
**POST** `/api/tasks`

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Minha primeira tarefa",
    "description": "Descrição da tarefa",
    "status": "PENDING"
  }'
```

### 2. Listar Todas as Tarefas
**GET** `/api/tasks`

```bash
curl -X GET http://localhost:8080/api/tasks
```

### 3. Listar Tarefas por Status
**GET** `/api/tasks?status=PENDING`

```bash
curl -X GET "http://localhost:8080/api/tasks?status=PENDING"
```

Status disponíveis: `PENDING`, `COMPLETED`

### 4. Buscar Tarefa por ID
**GET** `/api/tasks/{id}`

```bash
curl -X GET http://localhost:8080/api/tasks/1
```

### 5. Atualizar Tarefa
**PUT** `/api/tasks/{id}`

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tarefa atualizada",
    "description": "Nova descrição",
    "status": "COMPLETED"
  }'
```

### 6. Deletar Tarefa
**DELETE** `/api/tasks/{id}`

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

## 🧪 Executar Testes Automatizados

### Executar todos os testes
```bash
mvn test
```

### Executar testes específicos
```bash
mvn test -Dtest=TaskControllerTest
```

## 📝 Exemplos de Respostas

### Sucesso ao Criar (201 Created)
```json
{
  "id": 1,
  "title": "Minha primeira tarefa",
  "description": "Descrição da tarefa",
  "status": "PENDING",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Erro - Tarefa Não Encontrada (404 Not Found)
```json
{
  "status": 404,
  "message": "Task not found",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Erro - Validação (400 Bad Request)
```json
{
  "status": 400,
  "message": "Title is required",
  "timestamp": "2024-01-15T10:30:00"
}
```

## 🛠️ Testando com Postman

1. Importe a coleção abaixo ou crie manualmente:

### Coleção Postman

**Variável de ambiente:**
- `baseUrl`: `http://localhost:8080`

**Requests:**

1. **Criar Tarefa**
   - Method: `POST`
   - URL: `{{baseUrl}}/api/tasks`
   - Body (JSON):
   ```json
   {
     "title": "Nova tarefa",
     "description": "Descrição",
     "status": "PENDING"
   }
   ```

2. **Listar Tarefas**
   - Method: `GET`
   - URL: `{{baseUrl}}/api/tasks`

3. **Buscar por ID**
   - Method: `GET`
   - URL: `{{baseUrl}}/api/tasks/1`

4. **Atualizar Tarefa**
   - Method: `PUT`
   - URL: `{{baseUrl}}/api/tasks/1`
   - Body (JSON):
   ```json
   {
     "title": "Tarefa atualizada",
     "description": "Nova descrição",
     "status": "COMPLETED"
   }
   ```

5. **Deletar Tarefa**
   - Method: `DELETE`
   - URL: `{{baseUrl}}/api/tasks/1`

## ✅ Validações

- **Título**: Obrigatório, máximo 100 caracteres
- **Descrição**: Opcional, máximo 500 caracteres
- **Status**: Deve ser `PENDING` ou `COMPLETED`

## 🔍 Verificar Logs

A aplicação está configurada para mostrar SQL no console. Você verá as queries sendo executadas quando fizer requisições.

## 💻 Testando com PowerShell (Windows)

### Criar Tarefa
```powershell
$body = @{
    title = "Minha tarefa"
    description = "Descrição"
    status = "PENDING"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/tasks" `
    -Method POST `
    -Headers @{ "Content-Type" = "application/json" } `
    -Body $body
```

### Listar Tarefas
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/tasks" -Method GET
```

### Buscar por ID
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/tasks/1" -Method GET
```

### Atualizar Tarefa
```powershell
$body = @{
    title = "Tarefa atualizada"
    description = "Nova descrição"
    status = "COMPLETED"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/tasks/1" `
    -Method PUT `
    -Headers @{ "Content-Type" = "application/json" } `
    -Body $body
```

### Deletar Tarefa
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/tasks/1" -Method DELETE
```

### Ver Resposta Formatada
Para ver a resposta formatada em JSON:
```powershell
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/tasks" -Method GET
$response.Content | ConvertFrom-Json | ConvertTo-Json
```


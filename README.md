# Sistema de Controle de Empréstimos de Equipamentos

## Descrição
Sistema desenvolvido em Java com interface gráfica Swing para controle de empréstimos de equipamentos em uma escola técnica. O projeto segue o padrão arquitetural MVC (Model-View-Controller) e utiliza persistência em arquivos texto.

## Funcionalidades Implementadas

### ✅ 1. Tela de Login
- **Validação de usuários**: Login e senha validados contra arquivo `dados/usuarios.txt`
- **Cadastro de novos usuários**: Interface para registrar novos usuários no sistema
- **Usuário padrão**: admin/admin (criado automaticamente se não existir)
- **Redirecionamento**: Após login bem-sucedido, redireciona para tela de equipamentos

### ✅ 2. Tela de Listagem de Equipamentos
- **Exibição em JTable**: Equipamentos mostrados em tabela com colunas: Nome, Tipo, Status
- **Carregamento automático**: Dados carregados do arquivo `dados/equipamentos.txt`
- **Interface responsiva**: Botões habilitados/desabilitados conforme seleção e status

### ✅ 3. Funcionalidades de Controle
- **Listar equipamentos**: Visualização completa dos equipamentos cadastrados
- **Emprestar equipamento**: Alterar status de "disponivel" para "emprestado"  
- **Devolver equipamento**: Alterar status de "emprestado" para "disponivel"
- **Adicionar equipamento**: Interface para cadastrar novos equipamentos
- **Atualizar lista**: Recarregar dados do arquivo
- **Persistência automática**: Todas as alterações são salvas automaticamente

### ✅ 4. Classe Persistencia (Camada Controller)
- **Importação de dados**: Leitura de arquivos texto com tratamento de erros
- **Exportação de dados**: Escrita de arquivos texto com validação
- **Operações CRUD**: Create, Read, Update usando ArrayList
- **Validações**: Verificação de duplicatas e integridade dos dados

### ✅ 5. Interface Gráfica com Swing
- **Componentes nativos**: JFrame, JDialog, JTable, JButton, JTextField, etc.
- **Layout responsivo**: GridBagLayout e BorderLayout para organização
- **Look and Feel nativo**: Interface adaptada ao sistema operacional
- **Validações de entrada**: Campos obrigatórios e confirmação de senhas

## Estrutura do Projeto (Padrão MVC)

```
src/
├── App.java                          # Classe principal
├── model/                            # Camada Model
│   ├── Usuario.java                 # Entidade Usuário
│   └── Equipamento.java            # Entidade Equipamento
├── view/                            # Camada View
│   ├── TelaLogin.java              # Interface de login
│   ├── TelaCadastroUsuario.java    # Interface de cadastro
│   └── TelaListagemEquipamentos.java # Interface principal
└── controller/                      # Camada Controller
    ├── SistemaController.java      # Controller principal
    └── Persistencia.java          # Gerenciamento de dados

dados/                              # Arquivos de persistência
├── usuarios.txt                   # Dados dos usuários
└── equipamentos.txt              # Dados dos equipamentos
```

## Arquivos de Dados

### usuarios.txt
Formato: `nome,login,senha`
```
Administrador,admin,admin
Professor Silva,prof.silva,123456
João Santos,joao,senha123
```

### equipamentos.txt
Formato: `nome,tipo,status`
```
Notebook Dell Inspiron,Notebook,disponivel
Projetor Epson PowerLite,Projetor,disponivel
Tablet Samsung Galaxy,Tablet,emprestado
```

## Como Compilar e Executar

### Pré-requisitos
- Java Development Kit (JDK) 8 ou superior
- Sistema operacional Windows, Linux ou macOS

### Compilação
```bash
# Navegar para o diretório do projeto
cd "D:\Projetos\ATIVIDE2\Main"

# Compilar todas as classes
javac -d . src\**\*.java

# Ou compilar individualmente
javac -d . src\model\*.java
javac -d . src\controller\*.java
javac -d . src\view\*.java
javac -d . src\App.java
```

### Execução
```bash
# Executar o sistema
java App
```

## Uso do Sistema

### 1. Login
1. Execute o sistema
2. Use as credenciais padrão: **admin** / **admin**
3. Ou cadastre um novo usuário clicando em "Cadastrar Novo Usuário"

### 2. Gerenciar Equipamentos
1. Após o login, você verá a lista de equipamentos
2. Selecione um equipamento na tabela
3. Use os botões:
   - **Emprestar**: Para equipamentos disponíveis
   - **Devolver**: Para equipamentos emprestados
   - **Adicionar Equipamento**: Para cadastrar novos equipamentos
   - **Atualizar Lista**: Para recarregar dados
4. **Logout**: Para sair do sistema

### 3. Cadastrar Usuários
1. Na tela de login, clique em "Cadastrar Novo Usuário"
2. Preencha: Nome, Login, Senha e Confirmação
3. O sistema validará se o login já existe
4. Após cadastro, use as novas credenciais para entrar

## Características Técnicas

### Padrão MVC Implementado
- **Model**: Entidades `Usuario` e `Equipamento` com encapsulamento
- **View**: Interfaces gráficas separadas para cada funcionalidade
- **Controller**: Lógica de negócio e coordenação entre View e Model

### Persistência
- **Arquivos texto**: Formato CSV simples para fácil edição manual
- **Criação automática**: Arquivos e dados padrão criados automaticamente
- **Tratamento de erros**: Mensagens informativas para problemas de I/O

### Interface Gráfica
- **Swing nativo**: Componentes padrão do Java
- **Responsividade**: Botões habilitados conforme contexto
- **Validações**: Campos obrigatórios e confirmações
- **Look nativo**: Aparência adaptada ao sistema operacional

## Possíveis Melhorias Futuras
- Banco de dados SQL em vez de arquivos texto
- Relatórios de empréstimos por período
- Controle de prazo de devolução
- Log de histórico de empréstimos
- Interface web ou mobile
- Autenticação mais robusta
- Backup automático dos dados

## Autor
Sistema desenvolvido seguindo os requisitos especificados para controle de empréstimos de equipamentos escolares.

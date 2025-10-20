# Documentação Técnica - Sistema de Controle de Empréstimos

## Visão Geral Técnica

O Sistema de Controle de Empréstimos foi desenvolvido seguindo o padrão arquitetural **Model-View-Controller (MVC)** em Java, utilizando a biblioteca **Swing** para interface gráfica e **persistência em arquivos texto** para armazenamento dos dados.

## Arquitetura do Sistema

### 1. Camada Model (Modelo)
Responsável pela representação das entidades de dados e regras de negócio básicas.

#### `Usuario.java`
- **Responsabilidades**: Representar os usuários do sistema
- **Atributos**: nome, login, senha
- **Métodos principais**: getters/setters, toString(), equals()
- **Validações**: Encapsulamento através de métodos privados

#### `Equipamento.java`
- **Responsabilidades**: Representar os equipamentos disponíveis
- **Atributos**: nome, tipo, status (disponível/emprestado)
- **Métodos principais**: getters/setters, isDisponivel(), isEmprestado()
- **Validações**: Verificação de status através de métodos auxiliares

### 2. Camada View (Visão)
Responsável pela interface gráfica e interação com o usuário.

#### `TelaLogin.java`
- **Responsabilidades**: Interface de autenticação
- **Componentes**: JTextField (login), JPasswordField (senha), JButton (login/cadastrar)
- **Layout**: GridBagLayout para organização responsiva
- **Eventos**: ActionListener para botões e Enter no campo senha

#### `TelaCadastroUsuario.java`
- **Responsabilidades**: Interface para cadastro de novos usuários
- **Componentes**: JTextField (nome/login), JPasswordField (senha/confirmação)
- **Validações**: Campos obrigatórios, confirmação de senha, tamanho mínimo
- **Modal**: JDialog para comportamento modal sobre a tela de login

#### `TelaListagemEquipamentos.java`
- **Responsabilidades**: Interface principal do sistema
- **Componentes**: JTable (lista equipamentos), JButton (ações)
- **Modelo de dados**: DefaultTableModel não editável
- **Interatividade**: Habilitação dinâmica de botões baseada na seleção

### 3. Camada Controller (Controlador)
Responsável pela lógica de negócio e coordenação entre Model e View.

#### `SistemaController.java`
- **Responsabilidades**: Coordenação geral do sistema
- **Funcionalidades**:
  - Gerenciamento de sessão de usuário
  - Validação de login
  - Navegação entre telas
  - Operações de empréstimo/devolução
- **Padrão**: Singleton implícito (uma instância por execução)

#### `Persistencia.java`
- **Responsabilidades**: Gerenciamento de dados em arquivos
- **Operações CRUD**:
  - Create: adicionarUsuario(), adicionarEquipamento()
  - Read: importarUsuarios(), importarEquipamentos()
  - Update: atualizarStatusEquipamento()
  - Delete: Não implementado (fora do escopo)
- **Tratamento de erros**: Try-catch com JOptionPane para feedback
- **Formato de dados**: CSV simples (separado por vírgulas)

## Fluxo de Execução

### 1. Inicialização
```
App.main() → SistemaController() → TelaLogin.setVisible(true)
```

### 2. Autenticação
```
TelaLogin.realizarLogin() → SistemaController.validarLogin() → 
Persistencia.importarUsuarios() → [Validação] → TelaListagemEquipamentos
```

### 3. Operações de Equipamento
```
TelaListagemEquipamentos.[ação] → SistemaController.alterarStatusEquipamento() → 
Persistencia.atualizarStatusEquipamento() → [Atualização arquivo] → [Recarregar tabela]
```

## Persistência de Dados

### Formato dos Arquivos

#### `usuarios.txt`
```
nome,login,senha
Administrador,admin,admin
Professor Silva,prof.silva,123456
```

#### `equipamentos.txt`
```
nome,tipo,status
Notebook Dell Inspiron,Notebook,disponível
Projetor Epson PowerLite,Projetor,emprestado
```

### Estratégias de Persistência

1. **Leitura completa**: ArrayList carregado inteiramente na memória
2. **Escrita completa**: Arquivo reescrito por completo a cada alteração
3. **Backup implícito**: Dados preservados mesmo com falhas parciais
4. **Criação automática**: Arquivos e dados padrão criados se não existirem

## Padrões de Design Aplicados

### 1. Model-View-Controller (MVC)
- **Separação de responsabilidades** clara entre camadas
- **Baixo acoplamento** entre View e Model
- **Alto coesão** dentro de cada camada

### 2. Data Access Object (DAO) Simplificado
- Classe `Persistencia` abstrai acesso aos dados
- Operações de banco centralizadas
- Interface consistente para diferentes entidades

### 3. Observer Pattern (Implícito)
- ActionListeners conectam View ao Controller
- TableSelectionListener para atualização de estado

### 4. Template Method (Nas Views)
- Estrutura comum: inicializar → configurar → eventos → janela
- Reutilização de padrão em todas as telas

## Tratamento de Erros

### 1. Validações de Entrada
- Campos obrigatórios verificados antes do processamento
- Confirmação de senhas no cadastro
- Verificação de duplicatas (login de usuários)

### 2. Tratamento de I/O
- Try-catch em todas as operações de arquivo
- Mensagens informativas via JOptionPane
- Fallback para dados padrão em caso de arquivo inexistente

### 3. Validações de Estado
- Verificação de seleção antes de operações
- Validação de status antes de empréstimo/devolução
- Confirmações para operações críticas

## Segurança Básica

### 1. Autenticação
- Validação de credenciais contra arquivo
- Sessão de usuário mantida durante execução
- Logout com confirmação

### 2. Autorização
- Todas as operações requerem usuário logado
- Interface adaptada conforme usuário (nome exibido)

### 3. Limitações
- **Senhas em texto plano** (não recomendado para produção)
- **Sem criptografia de arquivos**
- **Sem controle de sessão por tempo**

## Performance e Escalabilidade

### 1. Pontos Fortes
- **Carregamento rápido** para volumes pequenos de dados
- **Interface responsiva** com Swing
- **Baixo consumo de memória**

### 2. Limitações
- **Escalabilidade limitada** pelo uso de ArrayList
- **Concorrência não suportada** (acesso simultâneo aos arquivos)
- **Backup manual** necessário

### 3. Melhorias Futuras
- Migração para banco de dados SQL
- Implementação de cache para leitura
- Pool de conexões para múltiplos usuários

## Dependências e Requisitos

### 1. Runtime
- **Java SE 8+** (compatível até versões mais recentes)
- **Sistema operacional**: Windows, Linux, macOS
- **Memória**: Mínimo 64MB de RAM

### 2. Desenvolvimento
- **JDK 8+** com javac
- **IDE**: Qualquer IDE Java (VS Code, IntelliJ, Eclipse)
- **Ferramentas**: Nenhuma dependência externa

### 3. Bibliotecas
- **javax.swing.***: Interface gráfica
- **java.io.***: Operações de arquivo
- **java.util.***: Collections (ArrayList)

## Conclusão

O sistema implementa com sucesso todos os requisitos solicitados, fornecendo uma base sólida para controle de empréstimos. A arquitetura MVC facilita manutenção e extensões futuras, enquanto a interface Swing oferece uma experiência de usuário intuitiva e responsiva.

A escolha por persistência em arquivos texto torna o sistema simples de deployar e manter, ideal para o contexto educacional proposto, embora limitações de escalabilidade devam ser consideradas para uso em larga escala.
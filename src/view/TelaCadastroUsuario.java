package view;

import controller.SistemaController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela para cadastro de novos usuários
 */
public class TelaCadastroUsuario extends JDialog {
    private SistemaController controller;
    private JTextField txtNome;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    
    public TelaCadastroUsuario(JFrame parent, SistemaController controller) {
        super(parent, "Cadastro de Usuário", true);
        this.controller = controller;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        txtNome = new JTextField(20);
        txtLogin = new JTextField(20);
        txtSenha = new JPasswordField(20);
        txtConfirmarSenha = new JPasswordField(20);
        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Novo Usuário");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(lblTitulo, gbc);
        
        // Nome
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(txtNome, gbc);
        
        // Login
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Login:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(txtLogin, gbc);
        
        // Senha
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(txtSenha, gbc);
        
        // Confirmar Senha
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Confirmar Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(txtConfirmarSenha, gbc);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnCancelar);
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private void configurarEventos() {
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCadastro();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void configurarJanela() {
        setSize(350, 280);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Foco inicial no campo nome
        SwingUtilities.invokeLater(() -> txtNome.requestFocus());
    }
    
    private void realizarCadastro() {
        String nome = txtNome.getText().trim();
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarSenha.getPassword());
        
        // Validações
        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!");
            return;
        }
        
        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            txtSenha.setText("");
            txtConfirmarSenha.setText("");
            txtSenha.requestFocus();
            return;
        }
        
        if (senha.length() < 3) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 3 caracteres!");
            txtSenha.setText("");
            txtConfirmarSenha.setText("");
            txtSenha.requestFocus();
            return;
        }
        
        // Tentar cadastrar
        if (controller.cadastrarUsuario(nome, login, senha)) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
        }
        // Mensagem de erro já é exibida pelo controller
    }
}
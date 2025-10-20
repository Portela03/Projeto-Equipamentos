package view;

import controller.SistemaController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de login do sistema
 */
public class TelaLogin extends JFrame {
    private SistemaController controller;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCadastrar;
    
    public TelaLogin(SistemaController controller) {
        this.controller = controller;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        txtLogin = new JTextField(20);
        txtSenha = new JPasswordField(20);
        btnLogin = new JButton("Login");
        btnCadastrar = new JButton("Cadastrar Novo Usuário");
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel lblTitulo = new JLabel("Sistema de Controle de Empréstimos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(lblTitulo, gbc);
        
        // Espaço
        gbc.gridy = 1;
        gbc.ipady = 10;
        painelPrincipal.add(new JLabel(), gbc);
        
        // Login
        gbc.gridwidth = 1;
        gbc.ipady = 0;
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
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnLogin);
        painelBotoes.add(btnCadastrar);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private void configurarEventos() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });
        
        // Enter no campo senha faz login
        txtSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }
    
    private void configurarJanela() {
        setTitle("Login - Sistema de Empréstimos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Foco inicial no campo login
        SwingUtilities.invokeLater(() -> txtLogin.requestFocus());
    }
    
    private void realizarLogin() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());
        
        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha login e senha!");
            return;
        }
        
        if (controller.validarLogin(login, senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            controller.abrirTelaListagem();
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha incorretos!");
            txtSenha.setText("");
            txtLogin.requestFocus();
        }
    }
    
    private void abrirTelaCadastro() {
        TelaCadastroUsuario telaCadastro = new TelaCadastroUsuario(this, controller);
        telaCadastro.setVisible(true);
    }
}
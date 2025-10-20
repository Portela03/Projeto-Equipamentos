package view;

import controller.SistemaController;
import model.Equipamento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela para cadastro de novos equipamentos
 */
public class TelaCadastroEquipamento extends JDialog {
    private SistemaController controller;
    private JTextField txtNome;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbStatus;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    
    public TelaCadastroEquipamento(JFrame parent, SistemaController controller) {
        super(parent, "Cadastro de Equipamento", true);
        this.controller = controller;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        txtNome = new JTextField(25);
        

        String[] tipos = {"Notebook", "Projetor", "Tablet", "Camera", "Monitor", "Impressora", "Outros"};
        cmbTipo = new JComboBox<>(tipos);
        cmbTipo.setEditable(true); 
        

        String[] status = {"disponivel", "emprestado"};
        cmbStatus = new JComboBox<>(status);
        cmbStatus.setSelectedIndex(0); 
        
        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        

        JLabel lblTitulo = new JLabel("Cadastro de Novo Equipamento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(lblTitulo, gbc);
        

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Nome do Equipamento:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(txtNome, gbc);
        
    
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(cmbTipo, gbc);
        
   
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Status:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(cmbStatus, gbc);
        

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnCancelar);
        
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
        
        txtNome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCadastro();
            }
        });
    }
    
    private void configurarJanela() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        

        SwingUtilities.invokeLater(() -> txtNome.requestFocus());
    }
    
    private void realizarCadastro() {
        String nome = txtNome.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();
        String status = (String) cmbStatus.getSelectedItem();
        

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do equipamento deve ser preenchido!");
            txtNome.requestFocus();
            return;
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O tipo do equipamento deve ser selecionado!");
            cmbTipo.requestFocus();
            return;
        }

        Equipamento novoEquipamento = new Equipamento(nome, tipo.trim(), status);
        
        if (controller.adicionarEquipamento(novoEquipamento)) {
            JOptionPane.showMessageDialog(this, "Equipamento cadastrado com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Equipamento já existe ou ocorreu um problema!");
            txtNome.requestFocus();
        }
    }
}
package view;

import controller.SistemaController;
import model.Equipamento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Tela principal para listagem e gerenciamento de equipamentos
 */
public class TelaListagemEquipamentos extends JFrame {
    private SistemaController controller;
    private JTable tabelaEquipamentos;
    private DefaultTableModel modeloTabela;
    private JButton btnEmprestar;
    private JButton btnDevolver;
    private JButton btnAdicionarEquipamento;
    private JButton btnAtualizar;
    private JButton btnLogout;
    private JLabel lblUsuarioLogado;
    
    public TelaListagemEquipamentos(SistemaController controller) {
        this.controller = controller;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarJanela();
        carregarEquipamentos();
    }
    
    private void inicializarComponentes() {
        // Modelo da tabela
        String[] colunas = {"Nome do Equipamento", "Tipo", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        
        // Tabela
        tabelaEquipamentos = new JTable(modeloTabela);
        tabelaEquipamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaEquipamentos.getTableHeader().setReorderingAllowed(false);
        
        // Botões
        btnEmprestar = new JButton("Emprestar");
        btnDevolver = new JButton("Devolver");
        btnAdicionarEquipamento = new JButton("Adicionar Equipamento");
        btnAtualizar = new JButton("Atualizar Lista");
        btnLogout = new JButton("Logout");
        
        // Label do usuário logado
        lblUsuarioLogado = new JLabel();
        if (controller.getUsuarioLogado() != null) {
            lblUsuarioLogado.setText("Usuário: " + controller.getUsuarioLogado().getNome());
        }
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Painel superior com informações do usuário
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        JLabel lblTitulo = new JLabel("Sistema de Controle de Empréstimos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelUsuario.add(lblUsuarioLogado);
        painelUsuario.add(btnLogout);
        
        painelSuperior.add(lblTitulo, BorderLayout.CENTER);
        painelSuperior.add(painelUsuario, BorderLayout.EAST);
        
        // Painel central com a tabela
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JScrollPane scrollPane = new JScrollPane(tabelaEquipamentos);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        painelCentral.add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior com botões
        JPanel painelInferior = new JPanel(new FlowLayout());
        painelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        painelInferior.add(btnEmprestar);
        painelInferior.add(btnDevolver);
        painelInferior.add(btnAdicionarEquipamento);
        painelInferior.add(btnAtualizar);
        
        // Adicionando os painéis ao frame
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        btnEmprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emprestarEquipamento();
            }
        });
        
        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolverEquipamento();
            }
        });
        
        btnAdicionarEquipamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroEquipamento();
            }
        });
        
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarEquipamentos();
                JOptionPane.showMessageDialog(TelaListagemEquipamentos.this, "Lista atualizada!");
            }
        });
        
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcao = JOptionPane.showConfirmDialog(
                    TelaListagemEquipamentos.this,
                    "Deseja realmente fazer logout?",
                    "Confirmar Logout",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (opcao == JOptionPane.YES_OPTION) {
                    controller.logout();
                }
            }
        });
        
        // Listener para habilitar/desabilitar botões baseado na seleção
        tabelaEquipamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarEstadoBotoes();
            }
        });
    }
    
    private void configurarJanela() {
        setTitle("Lista de Equipamentos - Sistema de Empréstimos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
        
        // Inicialmente desabilitar os botões
        atualizarEstadoBotoes();
    }
    
    private void carregarEquipamentos() {
        // Limpar tabela
        modeloTabela.setRowCount(0);
        
        // Carregar equipamentos
        ArrayList<Equipamento> equipamentos = controller.obterEquipamentos();
        
        for (Equipamento equipamento : equipamentos) {
            Object[] linha = {
                equipamento.getNome(),
                equipamento.getTipo(),
                equipamento.getStatus()
            };
            modeloTabela.addRow(linha);
        }
        
        atualizarEstadoBotoes();
    }
    
    private void atualizarEstadoBotoes() {
        int linhaSelecionada = tabelaEquipamentos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            btnEmprestar.setEnabled(false);
            btnDevolver.setEnabled(false);
        } else {
            String status = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
            
            btnEmprestar.setEnabled("disponivel".equalsIgnoreCase(status));
            btnDevolver.setEnabled("emprestado".equalsIgnoreCase(status));
        }
    }
    
    private void emprestarEquipamento() {
        int linhaSelecionada = tabelaEquipamentos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um equipamento para emprestar!");
            return;
        }
        
        String nomeEquipamento = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        
        if (!"disponivel".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Este equipamento não está disponível para empréstimo!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Confirma o empréstimo do equipamento: " + nomeEquipamento + "?",
            "Confirmar Empréstimo",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controller.alterarStatusEquipamento(nomeEquipamento, "emprestado")) {
                JOptionPane.showMessageDialog(this, "Equipamento emprestado com sucesso!");
                carregarEquipamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao emprestar equipamento!");
            }
        }
    }
    
    private void devolverEquipamento() {
        int linhaSelecionada = tabelaEquipamentos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um equipamento para devolver!");
            return;
        }
        
        String nomeEquipamento = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        
        if (!"emprestado".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Este equipamento não está emprestado!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Confirma a devolução do equipamento: " + nomeEquipamento + "?",
            "Confirmar Devolução",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controller.alterarStatusEquipamento(nomeEquipamento, "disponivel")) {
                JOptionPane.showMessageDialog(this, "Equipamento devolvido com sucesso!");
                carregarEquipamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao devolver equipamento!");
            }
        }
    }
    
    private void abrirTelaCadastroEquipamento() {
        TelaCadastroEquipamento telaCadastro = new TelaCadastroEquipamento(this, controller);
        telaCadastro.setVisible(true);
        // Atualizar a lista após fechar a tela de cadastro
        carregarEquipamentos();
    }
}
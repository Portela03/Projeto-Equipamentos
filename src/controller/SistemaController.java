package controller;

import model.Usuario;
import model.Equipamento;
import view.TelaLogin;
import view.TelaListagemEquipamentos;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Controller principal do sistema
 */
public class SistemaController {
    private TelaLogin telaLogin;
    private TelaListagemEquipamentos telaListagem;
    private Usuario usuarioLogado;
    
    public SistemaController() {
        inicializarSistema();
    }
    
    private void inicializarSistema() {
        telaLogin = new TelaLogin(this);
        telaLogin.setVisible(true);
    }
    
    public boolean validarLogin(String login, String senha) {
        ArrayList<Usuario> usuarios = Persistencia.importarUsuarios();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                return true;
            }
        }
        
        return false;
    }
    
    public boolean cadastrarUsuario(String nome, String login, String senha) {
        if (nome.trim().isEmpty() || login.trim().isEmpty() || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!");
            return false;
        }
        
        Usuario novoUsuario = new Usuario(nome, login, senha);
        boolean sucesso = Persistencia.adicionarUsuario(novoUsuario);
        
        if (!sucesso) {
            JOptionPane.showMessageDialog(null, "Login já existe! Escolha outro login.");
        }
        
        return sucesso;
    }
    
    public void abrirTelaListagem() {
        if (telaLogin != null) {
            telaLogin.dispose();
        }
        
        telaListagem = new TelaListagemEquipamentos(this);
        telaListagem.setVisible(true);
    }
    
    public ArrayList<Equipamento> obterEquipamentos() {
        return Persistencia.importarEquipamentos();
    }
    
    public boolean alterarStatusEquipamento(String nomeEquipamento, String novoStatus) {
        return Persistencia.atualizarStatusEquipamento(nomeEquipamento, novoStatus);
    }
    
    public boolean adicionarEquipamento(Equipamento equipamento) {
        return Persistencia.adicionarEquipamento(equipamento);
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public void logout() {
        usuarioLogado = null;
        if (telaListagem != null) {
            telaListagem.dispose();
        }
        inicializarSistema();
    }
}
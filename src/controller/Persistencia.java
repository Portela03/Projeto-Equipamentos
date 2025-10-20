package controller;

import model.Usuario;
import model.Equipamento;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Persistencia {
    private static final String CAMINHO_USUARIOS = "dados/usuarios.txt";
    private static final String CAMINHO_EQUIPAMENTOS = "dados/equipamentos.txt";

    public static ArrayList<Usuario> importarUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        File arquivo = new File(CAMINHO_USUARIOS);
        
        if (!arquivo.exists()) {

            usuarios.add(new Usuario("Administrador", "admin", "admin"));
            exportarUsuarios(usuarios);
            return usuarios;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] dados = linha.split(",");
                    if (dados.length >= 3) {
                        usuarios.add(new Usuario(dados[0].trim(), dados[1].trim(), dados[2].trim()));
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo de usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    public static void exportarUsuarios(ArrayList<Usuario> usuarios) {
        File diretorio = new File("dados");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(CAMINHO_USUARIOS))) {
            for (Usuario usuario : usuarios) {
                pw.println(usuario.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo de usuários: " + e.getMessage());
        }
    }
    
    public static boolean adicionarUsuario(Usuario novoUsuario) {
        ArrayList<Usuario> usuarios = importarUsuarios();
  
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(novoUsuario.getLogin())) {
                return false; 
            }
        }
        
        usuarios.add(novoUsuario);
        exportarUsuarios(usuarios);
        return true;
    }
    

    public static ArrayList<Equipamento> importarEquipamentos() {
        ArrayList<Equipamento> equipamentos = new ArrayList<>();
        File arquivo = new File(CAMINHO_EQUIPAMENTOS);
        
        if (!arquivo.exists()) {

            equipamentos.add(new Equipamento("Notebook Dell", "Notebook", "disponivel"));
            equipamentos.add(new Equipamento("Projetor Epson", "Projetor", "disponivel"));
            equipamentos.add(new Equipamento("Tablet Samsung", "Tablet", "emprestado"));
            equipamentos.add(new Equipamento("Notebook HP", "Notebook", "disponivel"));
            equipamentos.add(new Equipamento("Camera Canon", "Camera", "disponivel"));
            exportarEquipamentos(equipamentos);
            return equipamentos;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] dados = linha.split(",");
                    if (dados.length >= 3) {
                        equipamentos.add(new Equipamento(dados[0].trim(), dados[1].trim(), dados[2].trim()));
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo de equipamentos: " + e.getMessage());
        }
        
        return equipamentos;
    }
    
    public static void exportarEquipamentos(ArrayList<Equipamento> equipamentos) {
        File diretorio = new File("dados");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(CAMINHO_EQUIPAMENTOS))) {
            for (Equipamento equipamento : equipamentos) {
                pw.println(equipamento.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo de equipamentos: " + e.getMessage());
        }
    }
    
    public static boolean adicionarEquipamento(Equipamento novoEquipamento) {
        ArrayList<Equipamento> equipamentos = importarEquipamentos();
        

        for (Equipamento equipamento : equipamentos) {
            if (equipamento.equals(novoEquipamento)) {
                return false; 
            }
        }
        
        equipamentos.add(novoEquipamento);
        exportarEquipamentos(equipamentos);
        return true;
    }
    
    public static boolean atualizarStatusEquipamento(String nomeEquipamento, String novoStatus) {
        ArrayList<Equipamento> equipamentos = importarEquipamentos();
        boolean encontrado = false;
        
        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getNome().equals(nomeEquipamento)) {
                equipamento.setStatus(novoStatus);
                encontrado = true;
                break;
            }
        }
        
        if (encontrado) {
            exportarEquipamentos(equipamentos);
        }
        
        return encontrado;
    }
}
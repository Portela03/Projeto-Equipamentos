package model;

/**
 * Classe que representa um equipamento do sistema
 */
public class Equipamento {
    private String nome;
    private String tipo;
    private String status; // "disponível" ou "emprestado"
    
    public Equipamento() {
    }
    
    public Equipamento(String nome, String tipo, String status) {
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean isDisponivel() {
        return "disponivel".equalsIgnoreCase(status);
    }
    
    public boolean isEmprestado() {
        return "emprestado".equalsIgnoreCase(status);
    }
    
    @Override
    public String toString() {
        return nome + "," + tipo + "," + status;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Equipamento equipamento = (Equipamento) obj;
        return nome.equals(equipamento.nome) && tipo.equals(equipamento.tipo);
    }
}
import controller.SistemaController;
import javax.swing.SwingUtilities;

/**
 * Classe principal do Sistema de Controle de Empréstimos
 */
public class App {
    public static void main(String[] args) {
        // Inicializar o sistema na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaController();
            }
        });
    }
}

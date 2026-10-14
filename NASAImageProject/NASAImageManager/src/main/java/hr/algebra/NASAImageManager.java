package hr.algebra;

import com.formdev.flatlaf.FlatIntelliJLaf;
import hr.algebra.view.LoginFrame;
import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 *
 * @author vitoz
 */
public class NASAImageManager {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

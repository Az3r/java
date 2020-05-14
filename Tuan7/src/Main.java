import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new Font(Font.SANS_SERIF, Font.PLAIN, 26));

        Calculator app = new Calculator();
        app.setVisible(true);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

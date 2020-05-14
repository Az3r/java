import javax.swing.*;
import java.awt.*;

public class Calculator extends  JFrame {

    private JTextField InputField;
    private JPanel ActionPanel;
    private JButton ACButton;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton a7Button;
    private JButton a4Button;
    private JButton a1Button;
    private JButton a0Button;
    private JButton button9;
    private JButton button10;
    private JButton a8Button;
    private JButton a9Button;
    private JButton xButton;
    private JButton button14;
    private JButton a6Button;
    private JButton a5Button;
    private JButton button17;
    private JButton a3Button;
    private JButton a2Button;
    private JPanel RootPanel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    public Calculator(){
        setTitle("Calculator");
        setResizable(true);
        setSize(400, 600);
        setBackground(new Color(44,45,47));

        // add components
        add(RootPanel);
    }


}

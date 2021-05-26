import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainmenuGUI {
    public JButton bestätigenButton;
    public JCheckBox mausCheckBox, mauspadCheckBox;
    public JPanel mainMenu;
    Mode currentMode = null;

    public enum Mode{
        MOUSE, MOUSEPAD;
    }

    public MainmenuGUI(boolean visible){
        JFrame frame = new JFrame("EMSE - Mainmenu");
        frame.setResizable(false);
        frame.setContentPane(mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200,150);
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(visible);

        bestätigenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentMode != null){
                    GameGUI gameGUI = new GameGUI(true, currentMode);
                }else{
                    JOptionPane.showMessageDialog(frame, "Bitte Eigabegerät auswählen!");
                }

            }
        });

        mausCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==1){
                    currentMode = Mode.MOUSE;
                    System.out.println("Currentmode: " + currentMode);
                }
            }
        });

        mauspadCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==1){
                    currentMode = Mode.MOUSEPAD;
                    System.out.println("Currentmode: " + currentMode);
                }
            }
        });

    }
}

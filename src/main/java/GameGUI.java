import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.sqrt;
// gibt es eine möglichkeit, den Vorteil in einen Nachteil zu schaffen
// von pro maus zu pro mauspad
// Mauspad soll besser dastehen als Maus
// Zeitunterschied von Hand auf die Tastaur zu hand auf die Maus

public class GameGUI {
    public JButton button1;
    public JPanel mainPanel;
    public JLabel hitLabel;
    private static int xMax = 900, yMax = 600, rounds = 10, hitPerRound = 10, buttonSize = 90;
    public int hitCount = 0, round = 1;
    public List<Long> times = new ArrayList<>();
    long start;
    boolean pressed;
    int next_button_to_press = 0; //0 == Links ; 1 == Rechts

    public GameGUI(boolean visible, MainmenuGUI.Mode currentmode) {
        JFrame frame = new JFrame("EMSE");
        frame.setResizable(false);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(visible);

        if(currentmode.name().equals("MOUSE")){
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(SwingUtilities.isLeftMouseButton(e) && next_button_to_press == 0){
                        Game_Logic(visible,currentmode,frame);
                        next_button_to_press = Random_Number_Generator(0,1);
                        System.out.println("\n Current button to Press: " + next_button_to_press + "\n");
                    }else if(SwingUtilities.isRightMouseButton(e) && next_button_to_press == 1){
                        Game_Logic(visible,currentmode,frame);
                        next_button_to_press = Random_Number_Generator(0,1);
                        System.out.println("\n Current button to Press: " + next_button_to_press + "\n");
                    }

                    if(next_button_to_press == 0)
                        button1.setText("Links");
                    else
                        button1.setText("Rechts");
                }
            });
        }else{
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1.setText("Click");
                    Game_Logic(visible, currentmode, frame);

                }
            });
        }

    }

    private void changePosition(JButton button, MainmenuGUI.Mode currentmod){
        int xmin = -30, xmax = xMax - 45, xPos = 0;
        int ymin = -30, ymax = yMax - 45, yPos = 0;
        Random random = new Random();
        double distance = 0;

        //hier alternativ eine min distanz für die Maus
        do{
            //Hier ggf. ein 200ms delay für den respawn
            xPos = random.nextInt(xmin + xmax ) + xmin;
            yPos = random.nextInt(ymin + ymax ) + ymin;

            distance =  sqrt((yPos - button1.getBounds().y) * (yPos - button1.getBounds().y) +
                    (xPos - button1.getBounds().x) * (xPos - button1.getBounds().x));
        }while(distance < 0 || distance > 150);

        System.out.println("Button Position:  " + xPos + "  " + yPos + "\n Distance: " + distance);

        button.setBounds(xPos, yPos, buttonSize,buttonSize);
    }

    private void Game_Logic(boolean visible, MainmenuGUI.Mode currentmode, JFrame frame){
        System.out.println("Current Round:  " + round + "Current hit: " + hitCount);
        if(hitCount == 0){
            start = System.currentTimeMillis();
        }
        if((hitCount == hitPerRound - 1) && round < rounds){
            long elapsed = System.currentTimeMillis() - start;
            hitCount = -1;
            times.add(elapsed);

            JOptionPane.showMessageDialog(frame, "Runde Nr. " + round + " vorbei! Dauer: " +
                    elapsed +"ms");
            round++;
        }

        if((hitCount == hitPerRound - 1) && round == rounds){
            long elapsed = System.currentTimeMillis() - start;
            times.add(elapsed);

            JOptionPane.showMessageDialog(frame, "Runde Nr. " + round + " vorbei! Dauer: "
                    + elapsed + "ms. \n Spiel vorbei!");
            frame.dispose();
            System.out.println("Zeiten aus Times vor export:  \n" );

            for(long x : times)
                System.out.println(x + "ms");

            try {
                CSVExport.createCSV(times, currentmode.name());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        ++hitCount;
        hitLabel.setText("Hit no: " + hitCount);
        changePosition(button1, currentmode);
    }

    private static int Random_Number_Generator(int min, int max){
        // create random object
        Random randomno = new Random();
        // setting seed
        return randomno.nextInt((max - min) + 1) + min;
    }


}

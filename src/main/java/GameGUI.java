import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GameGUI {
    public JButton button1;
    public JPanel mainPanel;
    public JLabel hitLabel;
    private static int xMax = 900, yMax = 600, rounds = 10, hitPerRound = 10;
    public int hitCount = 0, round = 1;
    public List<Long> times = new ArrayList<>();
    long start;

    private void changePosition(JButton button){
        int xmin = -30, xmax = xMax - 45, xPos = 0;
        int ymin = -30, ymax = yMax - 45, yPos = 0;
        Random random = new Random();
        double distance = 0;
        do{
            xPos = random.nextInt(xmin + xmax) + xmin;
            yPos = random.nextInt(ymin + ymax) + ymin;
            distance =  sqrt((yPos - button1.getBounds().y) * (yPos - button1.getBounds().y) +
                    (xPos - button1.getBounds().x) * (xPos - button1.getBounds().x));
        }while(distance < 0 || distance > 250);

        button.setText("Click");
        System.out.println("Button Position:  " + xPos + "  " + yPos + "\n Distance: " + distance);
        button.setBounds(xPos, yPos, 90,90);
    }


    public GameGUI(boolean visible, MainmenuGUI.Mode currentmode) {
        JFrame frame = new JFrame("EMSE");
        frame.setResizable(false);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(visible);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    JOptionPane.showMessageDialog(frame, "Runde Nr. " + round + " vorbei! Dauer: " + elapsed + "ms. \n Spiel vorbei!");
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
                changePosition(button1);
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CubeGameGUI extends JFrame {

    private int cubePosition;
    private Random random = new Random();
    private JButton[] cupButtons = new JButton[3];
    private JLabel statusLabel;
    private JPanel cupPanel;
    private JPanel messagePanel;
    private int userChoice;

    public CubeGameGUI() {
        setTitle("Guess the Cube Game");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cupPanel = new JPanel();
        cupPanel.setLayout(new GridLayout(1, 3, 10, 10));
        initializeCupButtons();

        messagePanel = new JPanel();
        statusLabel = new JLabel("Welcome to My Cube Game!", SwingConstants.CENTER);
        messagePanel.add(statusLabel);

        setLayout(new BorderLayout());
        add(cupPanel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        initializeGame();
        setVisible(true);
    }

    private void initializeCupButtons() {
        for (int i = 0; i < 3; i++) {
            cupButtons[i] = new JButton("[]");
            cupButtons[i].setPreferredSize(new Dimension(100, 100));
            int cupIndex = i;
            cupButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCupChoice(cupIndex + 1);
                }
            });
            cupPanel.add(cupButtons[i]);
        }
    }

    private void initializeGame() {
        cubePosition = random.nextInt(3) + 1;
        updateStatus("Select a cup to guess where the cube is.");
    }

    private void handleCupChoice(int userChoice) {
        this.userChoice = userChoice;
        updateStatus("Revealing the cube...");
        Timer revealTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revealCube();
                String resultMessage;

                if (userChoice == cubePosition) {
                    resultMessage = "Congratulations! You guessed correctly. The cube was under cup " + userChoice + ".";
                } else {
                    resultMessage = "Sorry! You guessed cup " + userChoice + ". The cube was under cup " + cubePosition + ".";
                }

                updateStatus(resultMessage);
                disableButtons();
                JOptionPane.showMessageDialog(CubeGameGUI.this, resultMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        revealTimer.setRepeats(false);
        revealTimer.start();
    }

    private void revealCube() {
        for (int i = 0; i < 3; i++) {
            if (i + 1 == cubePosition) {
                cupButtons[i].setText("[X]");
            } else {
                cupButtons[i].setText("[O ]");
            }
        }
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void disableButtons() {
        for (JButton button : cupButtons) {
            button.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CubeGameGUI());
    }
}

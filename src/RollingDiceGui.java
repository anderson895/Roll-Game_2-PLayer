import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RollingDiceGui extends JFrame {
    private int currentPlayer = 1;
    private int player1Score = 0;
    private int player2Score = 0;
    private JLabel scoreLabel;
    private JLabel diceOneImg;
    private JLabel diceTwoImg;
    private MainMenuGui mainMenu;

    public RollingDiceGui(MainMenuGui menu) {
        super("Rolling Random Dice");
        this.mainMenu = menu;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700));
        setResizable(false);
        setLocationRelativeTo(null);

        addGameComponents();
        pack();
    }

    private void addGameComponents() {
        JPanel jPanel = new JPanel(null);
        jPanel.setBackground(new Color(30, 30, 30)); // Dark background

        JLabel titleLabel = new JLabel("Player " + currentPlayer + "'s Turn", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setBounds(0, 20, 700, 60);
        jPanel.add(titleLabel);

        diceOneImg = ImgService.loadImage("resources/dice1.png");
        diceOneImg.setBounds(100, 200, 200, 200);
        jPanel.add(diceOneImg);

        diceTwoImg = ImgService.loadImage("resources/dice1.png");
        diceTwoImg.setBounds(390, 200, 200, 200);
        jPanel.add(diceTwoImg);

        scoreLabel = new JLabel("Player 1: 0   Player 2: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        scoreLabel.setForeground(Color.WHITE); // White color for contrast
        scoreLabel.setBounds(0, 480, 700, 50);
        jPanel.add(scoreLabel);

        JButton rollButton = createStyledButton("Roll Dice");
        rollButton.setBounds(250, 550, 200, 50);
        rollButton.addActionListener(e -> rollDice(titleLabel, rollButton));
        jPanel.add(rollButton);

        JButton quitButton = createStyledButton("Quit");
        quitButton.setBounds(250, 610, 200, 50);
        quitButton.addActionListener(e -> {
            setVisible(false);
            mainMenu.setVisible(true);
        });
        jPanel.add(quitButton);

        this.getContentPane().add(jPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(220, 20, 60)); // Crimson color for buttons
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return button;
    }

    private void rollDice(JLabel titleLabel, JButton rollButton) {
        rollButton.setEnabled(false);

        // Dice animation loop
        Timer animationTimer = new Timer(50, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 30) { // Duration of animation (30*50ms = 1500ms)
                    int diceOne = (int) (Math.random() * 6) + 1;
                    int diceTwo = (int) (Math.random() * 6) + 1;

                    ImgService.updateImage(diceOneImg, "resources/dice" + diceOne + ".png");
                    ImgService.updateImage(diceTwoImg, "resources/dice" + diceTwo + ".png");

                    count++;
                } else {
                    ((Timer) e.getSource()).stop();
                    finalizeRoll(titleLabel, rollButton);
                }
            }
        });
        animationTimer.start();
    }

    private void finalizeRoll(JLabel titleLabel, JButton rollButton) {
        int diceOne = (int) (Math.random() * 6) + 1;
        int diceTwo = (int) (Math.random() * 6) + 1;

        ImgService.updateImage(diceOneImg, "resources/dice" + diceOne + ".png");
        ImgService.updateImage(diceTwoImg, "resources/dice" + diceTwo + ".png");

        int rollScore = diceOne + diceTwo;
        if (currentPlayer == 1) {
            player1Score += rollScore;
            currentPlayer = 2;
        } else {
            player2Score += rollScore;
            currentPlayer = 1;
        }

        // Update the score display
        scoreLabel.setText("Player 1: " + player1Score + "   Player 2: " + player2Score);

        // Update the title label to reflect the next player's turn
        if (currentPlayer == 1) {
            titleLabel.setText("Player 1's Turn");
            showResult(); // Check if the game is over and show the result
        } else {
            titleLabel.setText("Player 2's Turn");
        }
        
        rollButton.setEnabled(true);
    }

    private void showResult() {
        String message;
        if (player1Score > player2Score) {
            message = "Player 1 Wins!";
        } else if (player2Score > player1Score) {
            message = "Player 2 Wins!";
        } else {
            message = "It's a Tie!";
        }
        int response = JOptionPane.showOptionDialog(this, message, "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Retry", "Main Menu"}, JOptionPane.YES_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            player1Score = 0;
            player2Score = 0;
            scoreLabel.setText("Player 1: 0   Player 2: 0");
            currentPlayer = 1; // Start with Player 1
        } else {
            setVisible(false);
            mainMenu.setVisible(true);
        }
    }
}

import javax.swing.*;
import java.awt.*;

public class MainMenuGui extends JFrame {

    public MainMenuGui() {
        super("Main Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        addMenuComponents();
        pack();
    }

    private void addMenuComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("2-Player Dice Roll Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JButton playButton = createStyledButton("PLAY");
        playButton.addActionListener(e -> {
            new RollingDiceGui(this).setVisible(true);
            setVisible(false);
        });

        JButton helpButton = createStyledButton("HELP");
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Roll the dice, highest score wins!", "Help", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton quitButton = createStyledButton("QUIT");
        quitButton.addActionListener(e -> System.exit(0));

        buttonsPanel.add(playButton);
        buttonsPanel.add(helpButton);
        buttonsPanel.add(quitButton);

        panel.add(buttonsPanel, BorderLayout.CENTER);

        this.getContentPane().add(panel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 123, 255)); // Bootstrap primary blue color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGui().setVisible(true));
    }
}

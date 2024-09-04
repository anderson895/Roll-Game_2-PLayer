import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenuGui extends JFrame {

    private static final Color CRIMSON = new Color(220, 20, 60); // Crimson color
    private static final Color BLACK = Color.BLACK; // Black color
    private static final Color WHITE = Color.WHITE; // White color

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
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bgImage = ImageIO.read(getClass().getResource("resources/background.jpg"));
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        panel.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("2-Player Dice Roll Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setOpaque(false);

        JButton playButton = createStyledButton("PLAY", "resources/play_icon.png");
        playButton.addActionListener(e -> {
            new RollingDiceGui(this).setVisible(true);
            setVisible(false);
        });

        JButton helpButton = createStyledButton("HELP", "resources/help_icon.png");
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Roll the dice, highest score wins!", "Help", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton quitButton = createStyledButton("QUIT", "resources/quit_icon.png");
        quitButton.addActionListener(e -> System.exit(0));

        buttonsPanel.add(playButton);
        buttonsPanel.add(helpButton);
        buttonsPanel.add(quitButton);

        panel.add(buttonsPanel, BorderLayout.CENTER);

        this.getContentPane().add(panel);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(WHITE);
        button.setBackground(CRIMSON); // Crimson background color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 99, 71)); // Tomato color on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(CRIMSON); // Original crimson color
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGui().setVisible(true));
    }
}

package japaMala;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChantingBeads implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JLabel countLabel, counterLabel, timeLabel, statsLabel;
    private JButton chantButton, recountButton, resetButton, pauseButton;
    private int count = 0;
    private int counter = 1;
    private int bellCounter = 0;
    private Timer timer;
    private int elapsedTime = 0;
    private int roundStartTime = 0;
    private StringBuilder statistics = new StringBuilder("Round Statistics:\n");

    public ChantingBeads() {
        frame = new JFrame("Digital Chanting Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(new Color(255, 223, 186));
        panel.setLayout(new GridLayout(5, 1));

        countLabel = new JLabel("0", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.BOLD, 60));
        panel.add(countLabel);

        counterLabel = new JLabel("Round completed: 0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(counterLabel);

        timeLabel = new JLabel("Elapsed Time: 0 seconds", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(timeLabel);

        statsLabel = new JLabel("Round Statistics:\n", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(statsLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        chantButton = createStyledButton("Chant", Color.ORANGE, Color.BLACK);
        chantButton.addActionListener(this);
        buttonPanel.add(chantButton);

        recountButton = createStyledButton("Recount", Color.BLACK, Color.WHITE);
        recountButton.addActionListener(this);
        buttonPanel.add(recountButton);

        resetButton = createStyledButton("RESET", Color.RED, Color.WHITE);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        pauseButton = createStyledButton("Start Timer", Color.GREEN, Color.WHITE);
        pauseButton.addActionListener(this);
        buttonPanel.add(pauseButton);

        panel.add(buttonPanel);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        timer = new Timer(1000, e -> updateTimer()); // Timer updates every second
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setPreferredSize(new Dimension(150, 80));
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chantButton) {
            count++;
            if (count == 108) {
                Toolkit.getDefaultToolkit().beep();
                bellCounter++;
                count = 1;
                counterLabel.setText("Round completed: " + bellCounter);
                int roundTime = elapsedTime - roundStartTime;
                statistics.append("Round ").append(bellCounter).append(": ").append(roundTime).append(" seconds\n");
                statsLabel.setText(statistics.toString());
                roundStartTime = elapsedTime;
            }
        } else if (e.getSource() == recountButton) {
            count--;
            if (count < 1) {
                count = 108;
            }
        } else if (e.getSource() == resetButton) {
            count = 0;
            counter = 0;
            bellCounter = 0;
            timer.stop();
            pauseButton.setText("Start Timer");
            elapsedTime = 0;
            timeLabel.setText("Elapsed Time: 0 seconds");
            roundStartTime = 0;
            statistics = new StringBuilder("Round Statistics:\n");
            statsLabel.setText(statistics.toString());
        } else if (e.getSource() == pauseButton) {
            if (timer.isRunning()) {
                timer.stop();
                pauseButton.setText("Resume Timer");
            } else {
                timer.start();
                pauseButton.setText("Pause Timer");
            }
        }
        countLabel.setText(String.valueOf(count));
    }

    private void updateTimer() {
        elapsedTime++;
        timeLabel.setText("Elapsed Time: " + elapsedTime + " seconds");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChantingBeads());
    }
}

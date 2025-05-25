import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JPanel control_panel = new JPanel(); // Panel for restart and play again
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton restartButton = new JButton("Restart");
    JButton exitButton = new JButton("Exit");
    boolean player1_turn;

    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.getContentPane().setBackground(new Color(205, 65, 65));
        frame.setLayout(new BorderLayout());

        frame.setIconImage(new ImageIcon(
                "https://png.pngtree.com/png-clipart/20250417/original/pngtree-d-illustration-of-a-tic-tac-toe-icon-with-fun-and-png-image_20757013.png")
                .getImage());

        // Text Field Setup
        textfield.setBackground(new Color(0, 0, 180));
        textfield.setForeground(new Color(200, 55, 55));
        textfield.setFont(new Font("Arial", Font.ITALIC, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        // Title Panel
        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);
        title_panel.add(textfield);

        // Button Panel
        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(25, 25, 24));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        // Control Panel (Restart & Exit)
        control_panel.setLayout(new FlowLayout());
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));
        control_panel.add(restartButton);
        control_panel.add(exitButton);

        // Add Panels to Frame
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel, BorderLayout.CENTER);
        frame.add(control_panel, BorderLayout.SOUTH);

        frame.setVisible(true);
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttons[i].getText().equals("")) {
                    if (player1_turn) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O Turn");
                    } else {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X Turn");
                    }
                    check();
                }
            }
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X Turn");
        } else {
            player1_turn = false;
            textfield.setText("O Turn");
        }
    }

    public void check() {
        int[] winCombination = checkWin("X");
        if (winCombination != null) {
            xWins(winCombination);
            return;
        }

        winCombination = checkWin("O");
        if (winCombination != null) {
            oWins(winCombination);
            return;
        }

        if (isDraw()) {
            draw();
        }
    }

    public int[] checkWin(String player) {
        int[][] winConditions = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Horizontal
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Vertical
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonal
        };

        for (int[] condition : winConditions) {
            if (buttons[condition[0]].getText().equals(player) &&
                    buttons[condition[1]].getText().equals(player) &&
                    buttons[condition[2]].getText().equals(player)) {
                return condition;
            }
        }
        return null;
    }

    public boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    public void xWins(int[] winCombination) {
        highlightWinningCombination(winCombination);
        textfield.setText("X Wins");
        disableButtons();
    }

    public void oWins(int[] winCombination) {
        highlightWinningCombination(winCombination);
        textfield.setText("O Wins");
        disableButtons();
    }

    public void draw() {
        textfield.setText("Draw");
        disableButtons();
    }

    public void highlightWinningCombination(int[] winCombination) {
        for (int index : winCombination) {
            buttons[index].setBackground(Color.GREEN);
        }
    }

    public void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public void restartGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(null);
        }
        firstTurn();
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}

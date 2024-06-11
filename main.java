import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel resetPanel = new JPanel();
    JLabel textfield = new JLabel();
    JButton resetButton = new JButton();
    JButton[] buttons = new JButton[9];
    boolean player1Turn;
    int moveCount = 0;

    TicTacToe() {
        setupFrame();
        setupTextfield();
        setupPanels();
        setupButtons();
        firstTurn();
    }

    private void setupFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1000);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    private void setupTextfield() {
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);
    }

    private void setupPanels() {
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        resetPanel.setLayout(new BorderLayout());
        resetPanel.setBounds(0, 800, 800, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        titlePanel.add(textfield);
        resetPanel.add(resetButton);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(resetPanel, BorderLayout.SOUTH);
        frame.add(buttonPanel);
    }

    private void setupButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(100, 255, 255));
            buttons[i].setBorder(BorderFactory.createBevelBorder(1, new Color(148, 0, 211), new Color(75, 0, 130), Color.blue, Color.green));
            buttons[i].addActionListener(this);
            buttons[i].setToolTipText("Click to make a move");
        }

        resetButton.setBackground(new Color(25, 25, 25));
        resetButton.setForeground(new Color(255, 0, 255));
        resetButton.setFont(new Font("Ink Free", Font.BOLD, 75));
        resetButton.setHorizontalAlignment(JLabel.CENTER);
        resetButton.setText("New Game");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
        } else {
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == buttons[i]) {
                    if (player1Turn && buttons[i].getText().isEmpty()) {
                        buttons[i].setForeground(Color.BLACK);
                        buttons[i].setText("X");
                        player1Turn = false;
                        textfield.setText("O turn");
                        moveCount++;
                        checkGameState();
                    } else if (!player1Turn && buttons[i].getText().isEmpty()) {
                        buttons[i].setForeground(Color.WHITE);
                        buttons[i].setText("O");
                        player1Turn = true;
                        textfield.setText("X turn");
                        moveCount++;
                        checkGameState();
                    }
                }
            }
        }
    }

    private void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player1Turn = random.nextInt(2) == 0;
        textfield.setText(player1Turn ? "X turn" : "O turn");
    }

    private void checkGameState() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        if (checkWin(board, "X")) {
            announceWinner("X");
        } else if (checkWin(board, "O")) {
            announceWinner("O");
        } else if (moveCount == 9) {
            announceDraw();
        }
    }

    private boolean checkWin(String[][] board, String player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) ||
                (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player))) {
                return true;
            }
        }
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
               (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private void announceWinner(String player) {
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText(player + " wins");
    }

    private void announceDraw() {
        textfield.setText("Draw");
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(new Color(100, 255, 255));
        }
        firstTurn();
        moveCount = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        new TicTacToe();
    }
}
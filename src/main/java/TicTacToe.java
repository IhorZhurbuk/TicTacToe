import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToe extends JComponent {
    private static final String EMPTY_CELL = "";
    private static final String CROSS = "X";
    private static final String ZERO = "O";
    private String[][] board;
    private String winner = "";
    private int count = 0;

    public TicTacToe() {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        initGame();
    }

    public void run() {
        JFrame frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.setLayout(new BorderLayout());// менеджер компоновки??
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
    }

    public void initGame() {
        board = new String[][]{
                {"", "", ""},
                {"", "", ""},
                {"", "", ""},
        };
        count = 0;
        winner = "";
        repaint();
    }


    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            int i = (x / (getWidth() / 3));
            int j = (y / (getHeight() / 3));

            if (board[i][j].equals(EMPTY_CELL)) {
                board[i][j] = CROSS;
                count++;

                if (count < 5) {
                    compMove();
                }

                repaint();
                defineWinner();
                displayWinner();
            }
        }
    }


    public void displayWinner() {
        if (winner.equals(CROSS)) {
            JOptionPane.showMessageDialog(this, "CROSS", "win", JOptionPane.INFORMATION_MESSAGE);
            initGame();
        }

        if (winner.equals(ZERO)) {
            JOptionPane.showMessageDialog(this, "ZERO", "win", JOptionPane.INFORMATION_MESSAGE);
            initGame();
        }

        if (count == 5) {
            JOptionPane.showMessageDialog(this, "DRAW", "Draw", JOptionPane.INFORMATION_MESSAGE);
            initGame();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        drawBoard((Graphics2D) graphics);
        drawElements((Graphics2D) graphics);

    }

    public void compMove() {
        int x = ThreadLocalRandom.current().nextInt(0, 3);
        int y = ThreadLocalRandom.current().nextInt(0, 3);
        while (!board[x][y].isEmpty()) {
            x = ThreadLocalRandom.current().nextInt(0, 3);
            y = ThreadLocalRandom.current().nextInt(0, 3);
        }
        board[x][y] = ZERO;
    }

    public void defineWinner() {
        String crossWinner = CROSS + CROSS + CROSS;
        String zeroWinner = ZERO + ZERO + ZERO;
        StringBuilder diagonal1 = new StringBuilder();
        StringBuilder diagonal2 = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            diagonal1.append(board[i][i]);
            diagonal2.append(board[i][2 - i]);
        }

        if (diagonal1.toString().equals(crossWinner) || diagonal1.toString().equals(zeroWinner)) {
            winner = String.valueOf(diagonal1.charAt(0));
        }

        if (diagonal2.toString().equals(crossWinner) || diagonal2.toString().equals(zeroWinner)) {
            winner = String.valueOf(diagonal2.charAt(0));
        }

        for (int i = 0; i < 3; i++) {
            StringBuilder check_i = new StringBuilder();
            StringBuilder check_j = new StringBuilder();

            for (int j = 0; j < 3; j++) {
                check_i.append(board[i][j]);
                check_j.append(board[j][i]);
            }

            if (check_i.toString().equals(crossWinner) || check_i.toString().equals(zeroWinner)) {
                winner = String.valueOf(check_i.charAt(0));
            }

            if (check_j.toString().equals(crossWinner) || check_j.toString().equals(zeroWinner)) {
                winner = String.valueOf(check_j.charAt(0));
            }
        }
    }

    public void drawBoard(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        int width = getWidth();
        int height = getHeight();
        int dw = width / 3;
        int dh = height / 3;
        g.setColor(Color.BLACK);

        for (int i = 1; i < 3; i++) {
            g.drawLine(0, dh * i, width, dh * i);
            g.drawLine(dw * i, 0, dw * i, height);
        }
    }

    public void drawX(int i, int j, Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        g.drawLine(x, y, x + dw, y + dh);
        g.drawLine(x, y + dh, x + dw, y);
    }

    public void drawO(int i, int j, Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        g.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);

    }

    public void drawElements(Graphics2D graphics) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(CROSS)) {
                    drawX(i, j, graphics);
                } else if (board[i][j].equals(ZERO)) {
                    drawO(i, j, graphics);
                }
            }
        }
    }
}


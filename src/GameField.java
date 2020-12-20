import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 1000; //set size
    private int TIME = 250;    ///set time

    public static int getAppleSize() {
        return APPLE_SIZE;
    }

    public static int APPLE_SIZE = 30;
    BotFather myBot = new BotFather();
    private Image pacman;
    private Image apple;
    private Image star;
    private Image notApple;

    public static int getPacX() {
        return pacX;
    }

    public static int getPacY() {
        return pacY;
    }

    private static int pacX = APPLE_SIZE * 7;
    private static int pacY = APPLE_SIZE * 6;
    private int score;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    public static void setInGame(boolean inGame) {

        GameField.inGame = inGame;
    }

    private static boolean inGame = true;

    public GameField() {

        setBackground(Color.black);

        loadImages();
        myBot.loadImages("src\\images\\ghost.png");
        myBot.setMyPacX(APPLE_SIZE);
        myBot.setMyPacY(APPLE_SIZE * 5);
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame() {
        timer = new Timer(TIME, this);
        timer.start();
        createApple();
    }

    public void createApple() {
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("src\\images\\apple.jpg");
        apple = iia.getImage();

        ImageIcon iis = new ImageIcon("src\\images\\star.png");
        star = iis.getImage();

        ImageIcon iina = new ImageIcon("src\\images\\pixel.jpg");
        notApple = iina.getImage();

        ImageIcon iip = new ImageIcon("src\\images\\Kisha.png");
        pacman = iip.getImage();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            for (int i = 0; i < mp.length; i++) {
                for (int j = 0; j < mp[i].length; j++) {
                    if (mp[i][j] == 'o') {
                        g.drawImage(apple, i * APPLE_SIZE, j * APPLE_SIZE, this);
                    }
                    if (mp[i][j] == 'x') {
                        g.drawImage(notApple, i * APPLE_SIZE, j * APPLE_SIZE, this);
                    }

                    if (mp[i][j] == '-') {
                        g.drawImage(star, i * APPLE_SIZE, j * APPLE_SIZE, this);
                    }
                }
            }

            g.drawImage(pacman, pacX, pacY, this);
            g.drawImage(myBot.getBotImg(), myBot.getPacX(), myBot.getPacY(), this);
            g.setColor(Color.red);
            g.drawString("" + score, (SIZE - 50), 25);

        } else {
            String str = "GAME OVER";
            g.setColor(Color.red);
            g.drawString(str, (SIZE / 2) - str.length() * 2, SIZE / 2);
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < mp.length; i++) {
            for (int j = 0; j < mp[i].length; j++) {
                if (pacX / APPLE_SIZE == i && pacY / APPLE_SIZE == j && (mp[i][j] == 'o' || mp[i][j] == '-')) {
                    if (mp[i][j] == '-')
                        score += 20;
                    mp[i][j] = ' ';
                    score++;
                }
            }
        }
    }

    public void move() {

        if (left) {
            if (mp[(pacX / APPLE_SIZE) - 1][pacY / APPLE_SIZE] != 'x') {
                pacX -= APPLE_SIZE;
            }
        }
        if (right) {
            if (mp[(pacX / APPLE_SIZE) + 1][pacY / APPLE_SIZE] != 'x') {
                pacX += APPLE_SIZE;
            }
        }
        if (up) {
            if (mp[(pacX / APPLE_SIZE)][(pacY / APPLE_SIZE) - 1] != 'x') {
                pacY -= APPLE_SIZE;
            }
        }
        if (down) {
            if (mp[(pacX / APPLE_SIZE)][(pacY / APPLE_SIZE) + 1] != 'x') {
                pacY += APPLE_SIZE;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkCollisions();
            move();
            myBot.botCheck();
            myBot.botMove();
            myBot.botCheck();

        }
        repaint();
    }


    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && mp[(pacX / APPLE_SIZE) - 1][pacY / APPLE_SIZE] != 'x') {
                left = true;
                up = false;
                down = false;
                right = false;
            }

            if (key == KeyEvent.VK_RIGHT && mp[(pacX / APPLE_SIZE) + 1][pacY / APPLE_SIZE] != 'x') {
                right = true;
                left = false;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && mp[(pacX / APPLE_SIZE)][(pacY / APPLE_SIZE) - 1] != 'x') {
                right = false;
                up = true;
                left = false;
                down = false;
            }

            if (key == KeyEvent.VK_DOWN && mp[(pacX / APPLE_SIZE)][(pacY / APPLE_SIZE) + 1] != 'x') {
                right = false;
                down = true;
                up = false;
                left = false;
            }
        }
    }

    public char mp[][] = {//    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18   19   20   21   22   23   24   25   26   27   28
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},//01
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//02
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//03
            {'x', '-', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', '-', 'x'},//04
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//05
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//06
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//07
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//08
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//09
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//10
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//11
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//12
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//13
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//14
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//15
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//16
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//17
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//18
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//19
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//20
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//21
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//22
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//23
            {'x', '-', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', '-', 'x'},//24
            {'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x'},//25
            {'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x'},//26
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//27
            {'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x'},//28
            {'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x'},//29
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//30
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};//31
}

class BotFather {

    private int APPLE_SIZE = GameField.getAppleSize();

    public Image getBotImg() {
        return botImg;
    }

    private Image botImg;

    public void setMyPacX(int myPacX) {
        this.myPacX = myPacX;
    }

    public void setMyPacY(int myPacY) {
        this.myPacY = myPacY;
    }

    private int myPacX = APPLE_SIZE;
    private int myPacY = APPLE_SIZE;
    private boolean leftBot = false;
    private boolean rightBot = true;
    private boolean upBot = false;
    private boolean downBot = false;
    private int a = 1;

    public void botCheck() {
        if (myPacX == GameField.getPacX() && myPacY == GameField.getPacY()) {
            GameField.setInGame(false);
        }
    }

    public void botMove() {

        if ((GameField.getPacX() - myPacX >= 0)) {
            leftBot = false;
            rightBot = true;
            upBot = upBot || false;
            downBot = downBot || false;
            if (mp[(myPacX / APPLE_SIZE) + 1][myPacY / APPLE_SIZE] == 'x') {
                rightBot = false;
            }
        } else {
            leftBot = true;
            rightBot = false;
            upBot = upBot || false;
            downBot = downBot || false;
            if (mp[(myPacX / APPLE_SIZE) - 1][myPacY / APPLE_SIZE] == 'x') {
                leftBot = false;
            }
        }

        if (GameField.getPacY() - myPacY >= 0) {
            upBot = false;
            downBot = true;
            leftBot = leftBot || false;
            rightBot = rightBot || false;
            if (mp[(myPacX / APPLE_SIZE)][(myPacY / APPLE_SIZE) + 1] == 'x') {
                downBot = false;
            }
        } else {
            upBot = true;
            downBot = false;
            leftBot = leftBot || false;
            rightBot = rightBot || false;
            if (mp[(myPacX / APPLE_SIZE)][(myPacY / APPLE_SIZE) - 1] == 'x') {
                upBot = false;
            }
        }

        if ((leftBot || rightBot) && (upBot || downBot)) {
            if (Math.abs(GameField.getPacX() - myPacX) >= Math.abs(GameField.getPacY() - myPacY)) {
                upBot = false;
                downBot = false;
            } else {
                leftBot = false;
                rightBot = false;
            }

        }

        if (leftBot) {
            if (mp[(myPacX / APPLE_SIZE) - 1][myPacY / APPLE_SIZE] != 'x')

                myPacX -= APPLE_SIZE;

        }
        if (rightBot) {
            if (mp[(myPacX / APPLE_SIZE) + 1][myPacY / APPLE_SIZE] != 'x')

                myPacX += APPLE_SIZE;

        }
        if (upBot) {
            if (mp[(myPacX / APPLE_SIZE)][(myPacY / APPLE_SIZE) - 1] != 'x')

                myPacY -= APPLE_SIZE;
        }

        if (downBot) {
            if (mp[(myPacX / APPLE_SIZE)][(myPacY / APPLE_SIZE) + 1] != 'x')

                myPacY += APPLE_SIZE;
        }

    }


    public void loadImages(String str) {
        ImageIcon iibot = new ImageIcon(str);
        botImg = iibot.getImage();
    }

    public int getPacX() {
        return myPacX;
    }

    public int getPacY() {
        return myPacY;
    }


    public char mp[][] = {//    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18   19   20   21   22   23   24   25   26   27   28
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},//01
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//02
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//03
            {'x', '-', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', '-', 'x'},//04
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//05
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//06
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//07
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//08
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//09
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//10
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//11
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//12
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//13
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//14
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//15
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//16
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//17
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//18
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//19
            {'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x'},//20
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//21
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//22
            {'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'o', 'x'},//23
            {'x', '-', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', '-', 'x'},//24
            {'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x'},//25
            {'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x'},//26
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'x', 'x', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//27
            {'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x'},//28
            {'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x', 'x', 'o', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'o', 'x'},//29
            {'x', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'x'},//30
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}};//31
}

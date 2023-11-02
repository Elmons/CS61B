package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.TreeMap;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(seed, 40, 40);
        game.startGame();
    }

    public MemoryGame(int seed, int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        char[] s = new char[n];
        for (int i = 0; i < n; i++) {
            int index = rand.nextInt(CHARACTERS.length);
            s[i] = CHARACTERS[index];
        }
        return String.valueOf(s);
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        Font font = new Font("default", Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        int topY = this.height - 1;
        int topX = this.width - 1;
        int centralX = this.width/2;
        int centralY = this.height/2;

        String text = "Round: " + round;
        //draw info
        StdDraw.text(text.length() / 3, topY, text);

        if (playerTurn) {
            text = "Type!";
        } else {
            text = "Watch!";
        }
        StdDraw.text(centralX, topY, text);
        text = ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)];
        StdDraw.text(topX - (text.length() / 3), topY, text);
        text = "-";
        for (int i = 0; i <= topX; i++) {
            StdDraw.text(i, topY - 1, text);
        }

        if (s != null) { // draw text
            font = new Font("bold", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(centralX, centralY, s);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++){
            char c = letters.charAt(i);
            drawFrame(String.valueOf(c));
            StdDraw.pause(1000);
            drawFrame(null);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        char[] s = new char[n];
        drawFrame(null);
        for (int i = 0;i < n; i++){
            while (!StdDraw.hasNextKeyTyped());
            s[i] = StdDraw.nextKeyTyped();
            drawFrame(String.valueOf(s));
        }
        return String.valueOf(s);
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        String r = "Round: ";
        gameOver = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame(r + round);
            String s = generateRandomString(round);
            StdDraw.pause(1000);
            flashSequence(s);
            playerTurn = true;
            String res = solicitNCharsInput(round);
            if (res.compareTo(s) == 0) {
                round += 1;
                StdDraw.pause(500);
            } else {
                gameOver = true;
                StdDraw.pause(500);
            }
        }
        String f = "Game Over! You made it to round:";
        drawFrame(f + round);
    }
}

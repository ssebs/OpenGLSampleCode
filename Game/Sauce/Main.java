import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * @author ssebs
 * @version 2.0
 */
public class Main
{
    private final int WIDTH = 1280, HEIGHT = 720;
    private Paddle r1, r2;
    private Ball ball, ball2;
    private ArrayList<Block> blocks;
    public int r1X, r2X, r1Y, r2Y, score1, score2, r, g, b, blockCount, counterA;
    public int maxScore = 30;
    private boolean stopper, over;
    private Font font, font2;
    private TrueTypeFont ttf, ttf2;
    private Texture texture;

    /**
     * initializes multiple things
     */
    public void init()
    {

        try
        {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Loading...");
            Display.setInitialBackground(0, 0, 0);
            Display.setVSyncEnabled(true);
            Display.create();
        } catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        // create objects here
        blocks = new ArrayList<Block>();
        r1 = new Paddle(WIDTH - 50, HEIGHT / 2 - 50, 25, 80, 0, 0, 0, "r1Tex");// arrow keys
        r2 = new Paddle(25, HEIGHT / 2 - 50, 25, 80, 0, 0, 0, "r2Tex");// w a s d
        ball = new Ball(1000, 300, 25, 25, 1, 0, 0, true, 10, "ballTex");// ball
        ball2 = new Ball(300, 300, 25, 25, 0, 0, 1, false, 10, "ballTex");// ball

        for (int i = 400; i <= 785; i += 55)
        {
            columns(i);
        }

        // create fonts
        font = new Font("Verdana", Font.PLAIN, 48);
        ttf = new TrueTypeFont(font, true);
        font2 = new Font("Verdana", Font.PLAIN, 24);
        ttf2 = new TrueTypeFont(font2, true);

        // try to load background texture
        try
        {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background1.png"));
            // System.out.println("Texture laoded: " + texture);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param ex
     */
    public void columns(int ex)
    {
        int wy = 0;
        for (int i = 0; i <= 10; i++)
        {
            double c = (Math.random()) + .25;
            blockMaker(ex, wy, c);
            wy += 80;
        }
    }

    /**
     * @param x
     * @param y
     * @param c
     */
    public void blockMaker(int x, int y, double c)
    {
        blocks.add(new Block(x, y, 50, 75, c, c, c));
    }

    /**
     * first method that is called; holds game loop
     */
    public void start()
    {
        init();
        // init vars
        counterA = 1;
        blockCount = 0;
        score1 = 0;
        stopper = false;
        over = false;
        // end init vars
        while (stopper == false)
        {
            if (Display.isCloseRequested())
            {
                stopper = true;
            }
            // init openGl here
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
            glMatrixMode(GL_MODELVIEW);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            Display.setTitle("Breakout Pong*");
            //game rules
            gamePlay();
            render();
            Display.update();// updates screen
        }
        Display.destroy();
    }

    public void render()
    {
        texture1();
        r1.draw();// draws first rectangle
        r2.draw();// draws second rectangle
        ball.draw();// draws ball
        ball2.draw();// draws second ball

        for (Block b : blocks)
        {
            if (b.isVisible() == true)
            {
                b.draw();
            }
        }
        // need to enable and disable blend to render text
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (over == false)
        {
            ttf.drawString((WIDTH / 2) - 550f, HEIGHT / 16f, "Score: " + (maxScore - score1), Color.magenta);// score left
            ttf.drawString((WIDTH / 2) + 275f, HEIGHT / 16f, "Score: " + (maxScore - score2), Color.magenta);// score right
        }
        glDisable(GL_BLEND);
        // end render text
    }

    /**
     * does all game rules/etc
     */
    public void gamePlay()
    {
        won();
        checkScore(ball);
        checkScore(ball2);
        refreshVars();
        ballTouchesPaddle(ball);// checks if ball is being touched by paddle
        ballTouchesPaddle(ball2);// checks if ball is being touched by paddle

        // checks block collisions
        for (int i = 0; i < blocks.size(); i++)
        {
            blockCollision(ball, blocks.get(i).getX(), blocks.get(i).getY(), blocks.get(i).getW(), blocks.get(i).getH(), i);
            blockCollision(ball2, blocks.get(i).getX(), blocks.get(i).getY(), blocks.get(i).getW(), blocks.get(i).getH(), i);
        }

        //checks if all blocks are gone, makes ball and paddles go faster afterwards
        if(blockCount == 72 && counterA > 0)
        {
            counterA--;
            ball.setSpeed(15);
            ball2.setSpeed(15);
            r1.setSpeed(25);
            r2.setSpeed(25);
        }

        pollInput();// gets input from keyboard and mouse
    }

    /**
     * creates background texture
     */

    public void texture1()
    {
        glEnable(GL_TEXTURE_2D);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // enable alpha blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glViewport(0, 0, WIDTH, HEIGHT);
        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        Color.white.bind();
        texture.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);// top left coord
        glTexCoord2f(1, 0);
        glVertex2f(0 + texture.getTextureWidth(), 0);// top right coord
        glTexCoord2f(1, 1);
        glVertex2f(0 + texture.getTextureWidth(), 0 + texture.getTextureHeight());// bottom right coord
        glTexCoord2f(0, 1);
        glVertex2f(0, 0 + texture.getTextureHeight());// bottom left coord
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * ends game if the score >= maxScore
     */
    public void won()
    {
        if (score1 >= maxScore || Display.isCloseRequested())
        {
            over = true;
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            ttf.drawString((WIDTH / 2) - 150, HEIGHT / 16, "GAME OVER");
            ttf2.drawString((WIDTH / 2) - 150, HEIGHT / 6, "Pong made by Matt Sanfilippo and Sebastian Safari.");
            ttf2.drawString((WIDTH / 2) - 150, HEIGHT / 3, "Developed by ssebs inc. http://ssebs.com");
            ttf2.drawString((WIDTH / 2) - 150, HEIGHT / 2, "In partnership with Matt Co.  http://BlackpeopleMeet.com");
            ttf2.drawString((WIDTH / 2) - 150, 600, "Score L: " + (maxScore - score1) + " | Score R: " + (maxScore - score2));
            Display.update();
            try
            {
                Thread.sleep(3500);
                stopper = true;
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        } else if (score2 >= maxScore || Display.isCloseRequested())
        {
            over = true;
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            ttf.drawString((WIDTH / 2) - 150, HEIGHT / 16, "GAME OVER");
            ttf2.drawString((WIDTH / 2) - 150, HEIGHT / 6, "Pong made by Matt Sanfilippo and Sebastian Safari.");
            ttf2.drawString((WIDTH / 2) - 150, HEIGHT / 3, "Developed by ssebs inc. http://ssebs.com");
            ttf2.drawString((WIDTH / 2) - 150, HEIGHT / 2, "In partnership with Matt Co.  http://BlackpeopleMeet.com");
            ttf2.drawString((WIDTH / 2) - 150, 600, "Score L: " + (maxScore - score1) + " | Score R: " + (maxScore - score2));
            Display.update();
            try
            {
                Thread.sleep(3500);
                stopper = true;
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * checks the score
     */
    public void checkScore(Ball ball)
    {
        score(ball);
        score2(ball);
    }

    /**
     * refreshes the coordinates of the paddles
     */
    public void refreshVars()
    {
        this.r1X = r1.getX();
        this.r2X = r2.getX();
        this.r1Y = r1.getY() + (r1.getH() / 2);
        this.r2Y = r2.getY() + (r2.getH() / 2);
    }

    /**
     * @param ball
     * paddle collision method
     */
    public void ballTouchesPaddle(Ball ball)
    {
        // begin paddle collision
        if (((Math.abs(ball.getX() - r2X) - 10) <= 5) && ((Math.abs(ball.getY() - r2Y) - 15)) <= 35)// paddle one
        {
            Sound sound = new Sound();
            sound.playSound();
            boolean right = ball.isRight();
            ball.setRight(!right);
        } else if (((Math.abs(ball.getX() - r1X) - 10) <= 5) && ((Math.abs(ball.getY() - r1Y) - 15)) <= 35)// paddle two
        {
            Sound sound = new Sound();
            sound.playSound();
            boolean right = ball.isRight();
            ball.setRight(!right);
        }
        // end paddle collision
    }

    /**
     * @param ball
     * @param bX
     * @param bY
     * @param bW
     * @param bH
     * @param index
     * block collision method
     */
    public void blockCollision(Ball ball, int bX, int bY, int bW, int bH, int index)
    {
        if (((ball.getX() <= (bX + bW) && ball.getX() >= bX) && ball.getY() <= (bY + bH) && ball.getY() >= bY) && blocks.get(index).isVisible() == true)
        {
            Sound sound = new Sound();
            sound.playSound();
            boolean right = ball.isRight();
            if (right == true)
            {
                // score2++;
            } else if (right == false)
            {
                // score1++;
            }
            ball.setRight(!right);
            blocks.get(index).setVisible(false);
            blockCount++;
        }
    }

    /**
     * checks the score of paddle1
     * @param ball
     */
    public void score(Ball ball)
    {
        if (ball.getX() <= 2)
        {
            score1++;
        }
    }

    /**
     * checks score of paddle2
     * 
     * @param ball
     */
    public void score2(Ball ball)
    {
        if (ball.getX() >= WIDTH - ball.getW())
        {
            score2++;
        }
    }

    /**
     * checks the input of the controlls
     */
    public void pollInput()
    {
        /** ARROW KEYS */
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            r1.moveUp();
        }
        // up arrow
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            r1.moveDown();
        }
        // down arrow
        /** W A S D */
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            r2.moveUp();
        }
        // up (W)
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            r2.moveDown();
        }
        // down (S)
        ball.move();
        ball2.move();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Main de = new Main();
        de.start();
    }
}
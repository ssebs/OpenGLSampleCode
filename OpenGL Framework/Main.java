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
 * Framework made by Sebastian Safari
 * @author ssebs
 * @version 2.0
 */
public class Main
{
    private final int WIDTH = 1280, HEIGHT = 720;
    private Rect r1;
    public int r1X, r1Y, r, g, b;
    private boolean stopper, close;
    private Font font, font2;
    private TrueTypeFont ttf, ttf2;
    private Texture texture;
    // private Runnable run1;

    /**
     * this is a new thread, this will change the color every 10th of a second.
     */
    Runnable run1 = new Runnable()
        {
            public void run()
            {
                close = false;
                while(close == false)
                {
                    r1.randomColor();
                    try
                    {
                        Thread.sleep(100);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        
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
        r1 = new Rect(100, 100, 50, 50, 0, 1, 1,15);

        // create fonts, uncomment to use them. using them will add load time
        /**
        font = new Font("Verdana", Font.PLAIN, 48);//font 1
        ttf = new TrueTypeFont(font, true);//font 1

        font2 = new Font("Verdana", Font.PLAIN, 24);//font 2
        ttf2 = new TrueTypeFont(font2, true);// font 2
         */

        // init vars
        stopper = false;
        // end init vars

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
     * first method that is called; holds game loop
     */
    public void start()
    {
        init();
        Thread th1 = new Thread(run1);//multithreading
        th1.start();
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
        close = true;
    }

    public void render()
    {
        //remember, rendered things will go ontop of another
        texture1();//draws background
        r1.draw();// draws first rectangle

        // need to enable and disable blend to render text
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (stopper == false)
        {
            //enable to render text, will need to uncomment the font init
            //ttf.drawString(200, 200,"some text", Color.magenta);// score left
            //ttf.drawString(400,300, "some other text", Color.red);// score right
        }
        glDisable(GL_BLEND);
        // end render text
    }

    /**
     * does all game rules/etc
     */
    public void gamePlay()
    {
        pollInput();// gets input from keyboard and mouse
    }

    /**
     * creates background texture
     */

    public void texture1()
    {
        glEnable(GL_TEXTURE_2D);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            r1.moveRight();
        }
        // right arrow 
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            r1.moveLeft();
        }
        // left arrow
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
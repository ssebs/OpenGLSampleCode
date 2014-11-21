import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Ball
{
    private final int WIDTH = 1280, HEIGHT = 720;
    private int x, y, w, h, speed, incX = 0, incY = 0;
    private double r, g, b;
    private boolean c, up, right;
    private Texture texture;

    /**
     * @param ex
     * @param wy
     * @param wi
     * @param hi
     * @param re
     * @param ge
     * @param be
     * @param right
     * @param speed
     */
    public Ball(int ex, int wy, int wi, int hi, double re, double ge, double be, boolean right, int speed, String name)
    {
        this.x = ex;
        this.y = wy;
        this.w = wi;
        this.h = hi;
        this.r = re;
        this.g = ge;
        this.b = be;
        this.speed = speed;
        this.right = right;
        up = false;
        loadTexture(name);
    }

    /**
     * @param speed
     * sets speed of ball
     */
    
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
    
    /**
     * Loads texture for the ball
     */
    public void loadTexture(String name)
    {
        // try to load texture
        try
        {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + name + ".png"));
            // System.out.println("Texture laoded: " + texture);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * draws the ball
     */
    public void draw()
    {
        // glBegin(GL_QUADS);// start rect
        // glColor3d(r, g, b);// color
        // glVertex2d(x, y);// top left
        // glVertex2d(x + w, y);// top right
        // glVertex2d(x + w, y + h);// bottom right
        // glVertex2d(x, y + h);// bottom left
        // glEnd();// end rect
        // randomColor();
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
        glVertex2f(x, y);// top left coord
        glTexCoord2f(1, 0);
        glVertex2f(x + texture.getTextureWidth(), y);// top right coord
        glTexCoord2f(1, 1);
        glVertex2f(x + texture.getTextureWidth(), y + texture.getTextureHeight());// bottom right coord
        glTexCoord2f(0, 1);
        glVertex2f(x, y + texture.getTextureHeight());// bottom left coord
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * makes a random color for the ball
     */
    public void randomColor()
    {
        r = (double) (Math.random());
        g = (double) (Math.random());
        b = (double) (Math.random());
    }

    /**
     * moves the ball
     */
    public void move()
    {
        // up will switch when a wall is touched, same for right
        boolMove();
        x += incX;
        y += incY;
        if (y < 1)
        {
            up = !up;

        }
        if (y > HEIGHT - 25)
        {
            up = !up;
        }

        if (x <= 1)
        {
            right = !right;
        }
        if (x >= WIDTH - 25)
        {
            right = !right;
        }
    }

    /**
     * decides which direction to go
     */
    public void boolMove()
    {
        if (right)
        {
            incX = speed;
        } else
        {
            incX = -speed;
        }
        if (up)
        {
            incY = speed;
        } else
        {
            incY = -speed;
        }
    }

    /**
     * @param x
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * @param y
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * @param w
     */
    public void setW(int w)
    {
        this.w = w;
    }

    /**
     * @param h
     */
    public void setH(int h)
    {
        this.h = h;
    }

    /**
     * @param r
     */
    public void setR(double r)
    {
        this.r = r;
    }

    /**
     * @param g
     */
    public void setG(double g)
    {
        this.g = g;
    }

    /**
     * @param b
     */
    public void setB(double b)
    {
        this.b = b;
    }

    /**
     * @param c
     */
    public void setC(boolean c)
    {
        this.c = c;
    }

    /**
     * @return
     */
    public int getWIDTH()
    {
        return WIDTH;
    }

    /**
     * @return
     */
    public int getHEIGHT()
    {
        return HEIGHT;
    }

    /**
     * @return
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return
     */
    public int getY()
    {
        return y;
    }

    /**
     * @return
     */
    public int getW()
    {
        return w;
    }

    /**
     * @return
     */
    public int getH()
    {
        return h;
    }

    /**
     * @return
     */
    public double getR()
    {
        return r;
    }

    /**
     * @return
     */
    public double getG()
    {
        return g;
    }

    /**
     * @return
     */
    public double getB()
    {
        return b;
    }

    /**
     * @return
     */
    public boolean isC()
    {
        return c;
    }

    /**
     * @return
     */
    public boolean isUp()
    {
        return up;
    }

    /**
     * @param up
     */
    public void setUp(boolean up)
    {
        this.up = up;
    }

    /**
     * @return
     */
    public boolean isRight()
    {
        return right;
    }

    /**
     * @param right
     */
    public void setRight(boolean right)
    {
        this.right = right;
    }
}
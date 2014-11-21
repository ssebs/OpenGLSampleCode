import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;

public class Rect 
{
    private final int WIDTH = 1280, HEIGHT = 720;
    private int x, y, w, h, speed;
    private double r, g, b;

    /**
     * @param ex
     * @param wy
     * @param wi
     * @param hi
     * @param re
     * @param ge
     * @param be
     * @param speed
     */
    public Rect(int ex, int wy, int wi, int hi, double re, double ge, double be, int speed)
    {
        this.x = ex;
        this.y = wy;
        this.w = wi;
        this.h = hi;
        this.r = re;
        this.g = ge;
        this.b = be;
        this.speed = speed;

    }

    /**
     * @param speed
     * sets speed 
     */

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    /**
     * draws the ball
     */
    public void draw()
    {
        glBegin(GL_QUADS);// start rect
        glColor3d(r, g, b);// color
        glVertex2d(x, y);// top left
        glVertex2d(x + w, y);// top right
        glVertex2d(x + w, y + h);// bottom right
        glVertex2d(x, y + h);// bottom left
        glEnd();// end rect
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
     * moves the paddle up
     */
    public void moveUp()
    {
        if (y > 0)
        {
            y -= speed;
        }
    }

    /**
     * moves the paddle down
     */
    public void moveDown()
    {
        if (y < HEIGHT - w )
        {
            y += speed;
        }
    }

    /**
     * moves the paddle up
     */
    public void moveLeft()
    {
        if (x > 0)
        {
            x -= speed;
        }
    }

    /**
     * moves the paddle down
     */
    public void moveRight()
    {
        if (x < WIDTH - w)
        {
            x += speed;
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
}
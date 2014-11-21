import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Paddle extends Rekts
{
    private int speed;
    private Texture texture;

    /**
     * @param ex
     * @param wy
     * @param wi
     * @param hi
     * @param re
     * @param ge
     * @param be
     */
    public Paddle(int ex, int wy, int wi, int hi, double re, double ge, double be, String name)
    {
        super(ex, wy, wi, hi, re, ge, be);
        int speed = 20;
        this.speed = speed;
        loadTexture(name);
    }

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

    public void draw()
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

    public void setSpeed(int s)
    {
        this.speed = s;
    }
    
    /**
     * moves the paddle up
     */
    public void moveUp()
    {
        if (y > 10)
        {
            y -= speed;
        }
    }

    /**
     * moves the paddle down
     */
    public void moveDown()
    {
        if (y < HEIGHT - getH() - 20)
        {
            y += speed;
        }
    }
}
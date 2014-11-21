import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Block extends Rekts
{
	private boolean visible;

	/**
	 * @param ex
	 * @param wy
	 * @param wi
	 * @param hi
	 * @param re
	 * @param ge
	 * @param be
	 */
	public Block(int ex, int wy, int wi, int hi, double re, double ge, double be)
	{
		super(ex, wy, wi, hi, re, ge, be);
		this.visible = true;
	}

	/**
	 * draws the rectangle
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
	 * @return if its visible
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * @param bool
	 */
	public void setVisible(boolean bool)
	{
		visible = bool;
	}
}
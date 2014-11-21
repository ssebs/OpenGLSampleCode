public abstract class Rekts
{
	public final int WIDTH = 1280, HEIGHT = 720;
	public int x, y, w, h;
	public double r, g, b;

	/**
	 * @param ex
	 * @param wy
	 * @param wi
	 * @param hi
	 * @param re
	 * @param ge
	 * @param be
	 */
	public Rekts(int ex, int wy, int wi, int hi, double re, double ge, double be)
	{
		this.x = ex;
		this.y = wy;
		this.w = wi;
		this.h = hi;
		this.r = re;
		this.g = ge;
		this.b = be;
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
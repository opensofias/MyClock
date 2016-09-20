package sofias.clock;



import java.awt.*;
import javax.swing.*;

/**
 * @author sofias.
 */
public class Display extends JPanel
{
	
	private int scale;
	private int segSpacing = 1;
	private int charSpacing = 24;
	private int bold = 4 ;
	private int grid = 24;
	
	private Color color, bgcolor, incolor;
	
	private String string;
	
	Graphics gfx;
	
	public Display (String text, Color fg, Color bg, Color in, int skalierung)
	{
		string = text; color = fg; scale = skalierung; bgcolor = bg; incolor = in;
	
		setBackground (bgcolor);
		setLayout (null);
	}
	
	public Display (String text, Color fg, Color bg, int skalierung)
	{ this (text, fg, bg, new Color (fg.getRed (), fg.getGreen (), fg.getBlue (), 32), skalierung); } 
	
	public Display (String text, Color fg, int skalierung)
	{ this (text, fg, Color.black, skalierung); }
	
	public Display (String text, int skalierung)
	{ this (text, Color.orange, skalierung); }
	
	public Display (int skalierung)
	{ this ("bad:cafe", skalierung);}
	
	public Display (String text)
	{ this (text, 1); }
	
	public Display ()
	{ this ("bad:cafe"); }
	
	@Override public void paint (Graphics grfx)
	{
		gfx = grfx;
		drawString (string, 0, 0, grfx);
	}

	public void setString (String text)
	{ string = text; }
	
	public void drawString (String string, int x, int y, Graphics grfx)
	{
		//grfx.clearRect (-1000, -1000, 2000, 2000);
		for (int i = 0; i < string.length (); i++)
			drawChar (string.charAt (i), x + scale * (grid + charSpacing) * i, y, grfx);
	}
	
	public void drawChar (char chara, int x, int y, Graphics grfx)
	{
		boolean [] code = charCode(chara);
		
		for (int i = 0; i < 8; i++)
		{
			if (code[i]) grfx.setColor (color);
			else grfx.setColor (incolor);
			
			if (i < 4)
				drawHexagon (false, x + ((i * scale * grid) % (scale * grid * 2)), i < 2 ? y : y + scale * grid, grfx);
			else if (i < 7) 
				drawHexagon (true, x, y + ((i - 4) * scale * grid), grfx);
			else
				drawColon (x, y, grfx);
		}
		
	}
	
	public boolean [] charCode (char c)
	{
		switch (Character.toUpperCase (c))
		{
			case '0': return new boolean[] {true, true, true, true, true, false, true, false};
			case '1': return new boolean[] {false, true, false, true, false, false, false, false};
			case '2': return new boolean[] {false, true, true, false, true, true, true, false};
			case '3': return new boolean[] {false, true, false, true, true, true, true, false};
			case '4': return new boolean[] {true, true, false, true, false, true, false, false};
			case '5': return new boolean[] {true, false, false, true, true, true, true, false};
			case '6': return new boolean[] {true, false, true, true, true, true, true, false};
			case '7': return new boolean[] {false, true, false, true, true, false, false, false};
			case '8': return new boolean[] {true, true, true, true, true, true, true, false};
			case '9': return new boolean[] {true, true, false, true, true, true, true, false};
			case 'A': return new boolean[] {true, true, true, true, true, true, false, false};
			case 'B': return new boolean[] {true, false, true, true, false, true, true, false};
			case 'C': return new boolean[] {true, false, true, false, true, false, true, false};
			case 'D': return new boolean[] {false, true, true, true, false, true, true, false};
			case 'E': return new boolean[] {true, false, true, false, true, true, true, false};
			case 'F': return new boolean[] {true, false, true, false, true, true, false, false};
			case ':': return new boolean[] {false, false, false, false, false, false, false, true};
			case ' ': return new boolean[] {false, false, false, false, false, false, false, false};
			default: return new boolean[] {false, false, false, false, true, true, true, false};
		}
	}
	
	public void drawHexagon (boolean horiz, int xShift, int yShift, Graphics grfx)
	{
		int [] length = new int[]
		{
			bold + segSpacing,
			bold * 2 + segSpacing,
			grid - segSpacing,
			grid + bold - segSpacing,
			grid - segSpacing,
			bold * 2 + segSpacing
		};
		int [] width = new int[]
		{
			bold,
			0,
			0,
			bold,
			bold * 2,
			bold * 2
		};
		
		Polygon p = new Polygon
		(
			shift (scale (horiz ? length : width, scale), xShift),
			shift (scale (horiz ? width : length, scale), yShift),
			6
		);
		grfx.fillPolygon (p);
	}
	
	public void drawColon (int xShift, int yShift, Graphics grfx)
	{
		int [] length = new int[]
		{
			grid / 2, 
			grid / 2, 
			grid / 2 + bold * 2, 
			grid / 2 + bold * 2, 
		};
		int [] width = new int[]
		{
			grid / 2, 
			grid / 2 + bold * 2, 
			grid / 2 + bold * 2, 
			grid / 2, 
		};
		
		Polygon p = new Polygon
		(
			shift (scale (length, scale), xShift),
			shift (scale (width, scale), yShift),
			4
		);
		grfx.fillPolygon (p);
		
		p = new Polygon
		(
			shift (scale (length, scale), xShift),
			shift (scale (width, scale), yShift + scale * grid),
			4
		);
		grfx.fillPolygon (p);
	}
	
	private int [] shift (int [] array, int amount)
	{
		int [] result = new int [array.length];
		for (int i = 0; i < array.length; i++)
		{ result [i] = array [i] + amount; }
		return result;
	}
	
	private int [] scale (int [] array, int amount)
	{
		int [] result = new int [array.length];
		for (int i = 0; i < array.length; i++)
		{ result [i] = array [i] * amount; }
		return result;
	}
	
}

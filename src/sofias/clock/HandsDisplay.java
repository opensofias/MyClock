package sofias.clock;



import java.awt.*;
import javax.swing.*;

/**
 * @author sofias.
 */
public class HandsDisplay extends JPanel
{
	
	private int diameter = 1024;
	private final int handSize = 16 ;
	private final int grid = 24;
	private double time, shrinkage;
	
	private final int [] format;
	
	private final Color color, bgcolor;
	
	Graphics gfx;
	
	public HandsDisplay (double pTime, int [] pFormat, double pShrinkage, int pDiameter, Color fg, Color bg)
	{
		time = pTime; format = pFormat; shrinkage = pShrinkage; color = fg; bgcolor = bg; diameter = pDiameter;
		setBackground (bgcolor);
		setLayout (null);
	}
	
	public void setTime (double pTime)
	{ time = pTime; }
	
	@Override public void paint (Graphics grfx)
	{
		gfx = grfx;
		drawHands (gfx);
	}
	
	private void drawHands (Graphics gfx)
	{
		double angle = - time * Math.PI * 2;
		gfx.setColor (color);
		
		for (int i = 0; i < format.length; i++)
		{
			int [] triaX = new int []
			{
				(int) ( diameter / 2. + Math.sin (angle - Math.PI / 2.) * handSize),
				(int) ( diameter / 2. + Math.sin (angle + Math.PI) * diameter / 2. * Math.pow (shrinkage, format.length - i)),
				(int) ( diameter / 2. + Math.sin (angle + Math.PI / 2.) * handSize)
			};
			int [] triaY = new int []
			{
				(int) ( diameter / 2. + Math.cos (angle - Math.PI / 2.) * handSize),
				(int) ( diameter / 2. + Math.cos (angle + Math.PI) * diameter / 2. * Math.pow (shrinkage, format.length - i)),
				(int) ( diameter / 2. + Math.cos (angle + Math.PI / 2.) * handSize)
			};
			gfx.fillPolygon (triaX, triaY, 3);
			angle *= format[i];
		}
		
		for (int i = 0; i < format[0]; i++)
		{
			gfx.fillOval
			(
				(int) (diameter / 2. + Math.sin ((Math.PI * 2. * i) / format[0]) * diameter / 2 - handSize / 2),
				(int) (diameter / 2. + Math.cos ((Math.PI * 2. * i) / format[0]) * diameter / 2 - handSize / 2),
				handSize ,
				handSize
			);
		}
			
		gfx.fillOval (diameter / 2 - handSize, diameter / 2 - handSize, handSize * 2, handSize * 2);
	}
}

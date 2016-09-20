package sofias.clock;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.Timer;

/**
 * @author sofias.
 */
public class Clock extends JFrame implements ActionListener
{
	
	private String zeitString;

	private final Timer time;

	private Display disp;
	private HandsDisplay hDisp;
	
	private String type;
	
	private int base = 16; 
	private int[] format = new int[] {16,16,16,16};
	private String separator = "";

	public Clock (String clockType, int size)
	{
		this.type = clockType;
		
//		switch (type)
//		{
//			case "hex":
//			case "babyhex":
//			case "babyduodec":
//			case "babydec-mils":
//			case "babydec-mins": 
//			case "babydec": default: 
//		}
		
		this.time = new Timer (1000/60, this);
		
		JSplitPane split;
		
		add ( split = new JSplitPane
		(
			JSplitPane.VERTICAL_SPLIT,
			disp = new Display (size * 4),
			hDisp = new HandsDisplay (size, format, 0.75, 1024, Color.white, Color.black)
		));
		
		split.setBackground (Color.black);
		
		

		setTitle ("uhr der uhren der uhrenen");
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		setLocationRelativeTo (null);
		setVisible (true);
		setBackground(Color.black);
		time.addActionListener (this);
		time.start ();
		pack();
	}
	
	public Clock (String type) { this (type, 1); }

	public Clock () { this ("dec"); }
	
	/**
	 * @param args the command line arguments
	 */
	public static void main (String[] args)
	{
		Clock main = new Clock ();
	}
	
	@Override public void actionPerformed (ActionEvent e)
	{
//		GregorianCalendar cal = new GregorianCalendar ();
//		zeitString =
//			String.format (Locale.UK, "%02d", cal.get (Calendar.HOUR_OF_DAY)) + ":" +
//			String.format (Locale.UK, "%02d", cal.get (Calendar.MINUTE)) + ":" +
//			String.format (Locale.UK, "%02d", cal.get (Calendar.SECOND)) + ":" +
//			String.format (Locale.UK, "%03d", cal.get (Calendar.MILLISECOND));
//			
//		disp.setString (zeitString);
//		repaint();
		
		
		ClockType ct;
		ct = new ClockType (base, format, separator);
		ct.readCurrent ();
		disp.setString (ct.toString ());
		hDisp.setTime (ct.toDouble ());
		repaint ();
			
	}
	
	private class ClockType
	{
		private int base; private int[] places; private String separator;
		
		private int millisecs;
		
		public ClockType (int pBase, int[] pPlaces, String pSeparator)
		{ base = pBase; places = pPlaces; separator = pSeparator; }
		
		public ClockType () { this (10, new int[] {24, 60, 60}, ":"); }
		
		public int getMillisecs () { return millisecs; }

		public void setMillisecs (int millisecs) { this.millisecs = millisecs; }
		
		public void readCurrent ()
		{
			GregorianCalendar cal = new GregorianCalendar ();
			int result = cal.get(Calendar.HOUR_OF_DAY);
			result = result * 60 + cal.get(Calendar.MINUTE);
			result = result * 60 + cal.get(Calendar.SECOND);
			result = result * 1000 + cal.get(Calendar.MILLISECOND);
			setMillisecs (result);
		}
		
		public double toDouble () { return getMillisecs () / (24. * 60. * 60. * 1000.); }
		
		public int[] toTimeArray ()
		{
			int[] result = new int [places.length];
			double working = toDouble ();
			
			for (int i = 0; i < places.length; i++)
			{
				working *= places[i];
				result [i] = (int) working;
				working %= 1;
			}
			return result;
		}
		
		public String toString ()
		{
			String result = "";
			int[] timeArray = toTimeArray ();
			for (int i = 0; i < timeArray.length; i++)
			{
				String group = "";
				group = Integer.toString (timeArray [i], base);
				while (group.length () < (Math.log (places [i]) / Math.log (base)))
					group = "0" + group;
				result += group + separator;
			}
			result = result.substring (0, result.length () - separator.length ());
			return result;
		}
		
		
	}
}

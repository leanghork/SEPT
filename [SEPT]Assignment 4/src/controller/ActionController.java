package controller;

import view.*;
import java.awt.event.*;

public class ActionController 
	implements ActionListener
{
	View gui;
	
	public ActionController(View gui)
	{
		this.gui=gui;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("new"))
			gui.newFile();
		if(e.getActionCommand().equals("open"))
			gui.openFile();
		if(e.getActionCommand().equals("save"))
			gui.saveFile();
		if(e.getActionCommand().equals("zoomin"))
			gui.zoomIn();
		if(e.getActionCommand().equals("zoomout"))
			gui.zoomOut();		
	}
}

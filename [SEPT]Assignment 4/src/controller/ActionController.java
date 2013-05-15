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
		if(e.getActionCommand().equals("select"))
			;
		if(e.getActionCommand().equals("circle"))
			;	
		if(e.getActionCommand().equals("rect"))
			;
		if(e.getActionCommand().equals("line"))
			;
		if(e.getActionCommand().equals("group"))
			;
		if(e.getActionCommand().equals("ungroup"))
			;
		if(e.getActionCommand().equals("delete"))
			;
		if(e.getActionCommand().equals("stroke"))
			;
		if(e.getActionCommand().equals("strokeWidth"))
			;
		if(e.getActionCommand().equals("fill"))
			;
	}
}

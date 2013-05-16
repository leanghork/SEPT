package controller;

import model.*;

import java.awt.event.*;

public class ToolbarActionController 
	implements ActionListener
{
	private Model model = null;
	
	public ToolbarActionController(Model model)
	{
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("select"))
			model.setOption(Model.clear);
		if(e.getActionCommand().equals("circle"))
			model.setOption(Model.circle);	
		if(e.getActionCommand().equals("rect"))
			model.setOption(Model.rectangle);
		if(e.getActionCommand().equals("line"))
			model.setOption(Model.line);
		if(e.getActionCommand().equals("group"))
			;
		if(e.getActionCommand().equals("ungroup"))
			;
		if(e.getActionCommand().equals("delete"))
		{
			model.remove();
		}
		if(e.getActionCommand().equals("stroke"))
			;
		if(e.getActionCommand().equals("strokeWidth"))
			;
		if(e.getActionCommand().equals("fill"))
			;
		
	}
}

package model;

import java.util.*;
import java.io.*;

import javax.swing.*;

public class Model 
	extends JFrame
{
	LinkedList<File> svgFiles = new LinkedList<File>();
	
	public Model()
	{	
		
	}
	
	public Model(String[] args)
	{
		for(int i=0; i<args.length;i++)
		{
			if(checkFile(new File(args[i])))
			{
				svgFiles.add(new File(args[i]));
			}
		}
		
		System.out.println("Size: "+svgFiles.size());
	}
	
	public LinkedList<File> getFileList()
	{
		return svgFiles;
	}
	
	public LinkedList<DrawingBoard> getBoard()
	{
		LinkedList<DrawingBoard> board = new LinkedList<DrawingBoard>();
		
		for(int i=0; i<svgFiles.size();i++)
		{
			if(svgFiles.get(i)!=null)
				board.add(new DrawingBoard(svgFiles.get(i)));
			else
				board.add(new DrawingBoard());
		}
		
		return board;
	}
	
	public void addEmptyBoard()
	{
		svgFiles.add(null);
	}
	
	public boolean openFile(File f)
	{
		if(checkFile(f))
		{
			svgFiles.add(f);
			
			return true;
		}
		
		return false;
	}
	
	public boolean checkFile(File f)
	{
		
		if(!f.exists())
		{
			String msg = "File <"+f.getName()+"> does not exists";
			
			System.out.println(msg);
			JOptionPane.showMessageDialog(this,msg,"Unable to open file",JOptionPane.ERROR_MESSAGE);
			this.dispose();
			
			return false;
		}
		
		if(!f.getName().toLowerCase().endsWith("svg"))
		{
			String msg = "File: <"+f.getName()+"> is not a valid SVG file";
			
			System.out.println(msg);
			JOptionPane.showMessageDialog(this,msg,"Unable to open file",JOptionPane.ERROR_MESSAGE);
			this.dispose();
			
			return false;
		}
		
		return true;
	}
}

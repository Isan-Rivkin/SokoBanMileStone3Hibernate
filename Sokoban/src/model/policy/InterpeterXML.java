package model.policy;

import java.io.FileNotFoundException;

import sokoban_utils.XMLUtil;

public class InterpeterXML implements IinterpeterCreator 
{

	XMLUtil xmlHandler;
	public InterpeterXML() 
	{
		xmlHandler=new XMLUtil();
	}
	@Override
	public Iinterpeter loadInterpeter(String path) 
	{
		try 
		{
			return (InterpeterMove) xmlHandler.decodeXML(path);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveInterpeter(Iinterpeter i, String path)
	{
		try
		{
			xmlHandler.encodeXML(i, path);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

}

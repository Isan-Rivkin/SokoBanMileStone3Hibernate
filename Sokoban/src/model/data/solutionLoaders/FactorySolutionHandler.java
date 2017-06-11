package model.data.solutionLoaders;

import java.util.HashMap;

import model.data.levelLoaders.ILevelLoader;
import model.data.levelLoaders.ObjLevelLoader;
import model.data.levelLoaders.TxtLevelLoader;
import model.data.levelLoaders.XMLLevelLoader;

import sokoban_utils.SokoUtil;

public class FactorySolutionHandler
{
	HashMap<String, Creator> solution_loaders;
	SokoUtil util;
	
	public FactorySolutionHandler() 
	{
		solution_loaders= new HashMap<String, Creator>();
		solution_loaders.put("txt", new TxtSolutionCreator());
		solution_loaders.put("obj", new ObjSolutionCreator());
		solution_loaders.put("xml", new XMLSolutionCreator());
		solution_loaders.put("ser", new ObjSolutionCreator());
		util=new SokoUtil();
	}
	
	//creator interface
	private interface Creator
	{
		public ISolutionHandler create();
	}
	//create TxtLoader 
	private class TxtSolutionCreator implements Creator
	{
		public ISolutionHandler create(){
			return new TxtSolutionHandler();
		}
	}
	//create XMLLoader
		private class XMLSolutionCreator implements Creator
		{

		@Override
		public ISolutionHandler create() {
	
			return new XMLSolutionHandler();
		}
			
	}
    //create ObjectLoader
		private class ObjSolutionCreator implements Creator{

			@Override
			public ISolutionHandler create() {
				return new ObjectSolutionHandler();
			}
			
		}
	// public method can be called form main, returns the actuall required LevelLoader

		public ISolutionHandler getLevelLoader(String path){
			//identifhy the text format and save into type
			String type="";
	           int i = path.lastIndexOf('.');
	           if (i > 0) {
	           type = path.substring(i+1);
	          }
	          
	           
	           //create the instance of the loader required
	        if(util.isValidFileType(type))
	        {
				ISolutionHandler solutionLoader=this.solution_loaders.get(type).create();
				if (solutionLoader != null)
					return solutionLoader;
				else
					return null;
			}else{
	        	System.out.println("Invalid File Type");
	        	return null;
	        }
		}
	//

}

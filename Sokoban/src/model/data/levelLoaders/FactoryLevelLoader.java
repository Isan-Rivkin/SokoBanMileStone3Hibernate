package model.data.levelLoaders;

import java.util.HashMap;

import sokoban_utils.SokoUtil;
/**
 * Generate the right level-loader
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class FactoryLevelLoader{
	HashMap<String, Creator> levels_loaders;
	SokoUtil util;
	
	public FactoryLevelLoader() {
		levels_loaders= new HashMap<String, Creator>();
		levels_loaders.put("txt", new TxtLoaderCreator());
		levels_loaders.put("obj", new ObjLoaderCreator());
		levels_loaders.put("xml", new XMLLoaderCreator());
		levels_loaders.put("ser", new ObjLoaderCreator());
		util=new SokoUtil();
	}
	
	//creator interface
	private interface Creator{
		public ILevelLoader create();
	}
	//create TxtLoader 
	private class TxtLoaderCreator implements Creator{
		public ILevelLoader create(){
			return new TxtLevelLoader();
		}
	}
	//create XMLLoader
		private class XMLLoaderCreator implements Creator{

		@Override
		public ILevelLoader create() {
	
			return new XMLLevelLoader();
		}
			
	}
    //create ObjectLoader
		private class ObjLoaderCreator implements Creator{

			@Override
			public ILevelLoader create() {
				return new ObjLevelLoader();
			}
			
		}
	// public method can be called form main, returns the actuall required LevelLoader

		public ILevelLoader getLevelLoader(String path){
			//identifhy the text format and save into type
			String type="";
	           int i = path.lastIndexOf('.');
	           if (i > 0) {
	           type = path.substring(i+1);
	          }
	          
	           
	           //create the instance of the loader required
	        if(util.isValidFileType(type)){
				ILevelLoader levelLoader=this.levels_loaders.get(type).create();
				if (levelLoader != null)
					return levelLoader;
				else
					return null;
			}else{
	        	System.out.println("Invalid File Type");
	        	return null;
	        }
		}
	//
}

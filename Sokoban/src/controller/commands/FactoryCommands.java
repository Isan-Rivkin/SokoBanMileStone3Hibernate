//package controller.commands;
//
//import java.util.HashMap;
//
//import FileUtilsIO.SokoUtil;
///**
// * generate the right command for the user
// * @author Daniel Hake & Isan Rivkin
// *
// */
//public class FactoryCommands {
//
//	HashMap<String, Creator> commands_creators;
//	SokoUtil util;
//	
//	public FactoryCommands() {
//		util=new SokoUtil();
//		commands_creators=new HashMap<String,Creator>();
//		commands_creators.put("load", new LevelLoaderCmdCreator());
//		commands_creators.put("display", new DisplayerCmdCreator());
//		commands_creators.put("save", new LevelSaverCmdCreator());
//		commands_creators.put("exit", new ExitCmdCreator());
//		commands_creators.put("move", new MoveCommandCreator());
//	}
//	
//
//	private interface Creator{
//		Command create();
//	}
//	private class LevelLoaderCmdCreator implements Creator {
//
//		@Override
//		public Command create() {
//			return new CommandLoadLevel();
//		}
//		
//	}
//	
//	private class DisplayerCmdCreator implements Creator{
//
//		@Override
//		public Command create() {
//		      return new CommandDisplay();
//		}
//		
//	}
//	private class LevelSaverCmdCreator implements Creator{
//		@Override
//		public Command create() {
//			return new CommandLevelSaver();
//		}
//	}
//	private class ExitCmdCreator implements Creator{
//
//		@Override
//		public Command create() {
//			return new CommandExit();
//		}
//		
//	}
//	private class MoveCommandCreator implements Creator{
//
//		@Override
//		public Command create() {
//			return new CommandMove();
//		}
//		
//	}
//	public Command getCommand(String key){
//	   String val=util.extractCmd(key);
//	   Command cmd=commands_creators.get(val).create();
//       if(cmd != null)
//    	   return cmd;
//	   return null;
//		
//	}
//}

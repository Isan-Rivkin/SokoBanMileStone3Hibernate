package model.database;

import java.util.List;

public class TempMain {

	
	public static void main(String[] args) {
		
	//	DBHighScoresManager manager = new DBHighScoresManager();
		 //manager.savePlayer(new PlayerP("Hake The Daniel"));
		//	manager.saveLevel(new LevelP( "FinalObjTestLevel"));
	 //  manager.saveHighscore(new HighScoreP(666, 16, 11, 165245));
		//manager.saveToDB(new Student(199, "obj name", "obh l name", 999));
		SokoDBMapper mapper= new SokoDBMapper();
	//	mapper.savePOJO(new PlayerP("Haimke shlomi"));
		//mapper.savePOJO(new PlayerP("Daniel Yohpaz"));
	//	mapper.savePOJO(new PlayerP("Daniel Cohen"));
//		List<PlayerP> p=mapper.getAllPlayers();
//		for(PlayerP pp : p)
//		{
//			System.out.println(p.toString());
//			mapper.deletePOJO(pp);
//		}		
//
//		mapper.savePOJO(new LevelP("testlevel1", "testPath"));
//		mapper.savePOJO(new HighScoreP("Haimke shlomi", "testlevel1", new Integer(51), new Long(343)));
		IQuery query= new HighScoreQuery();
		query.setPlayerName("Haimke shlomi");
		query.setMaxResults(50);
		HighScoreP[] list= mapper.searchHighScore(query);
		System.out.println(list.length);
		for(int i=0; i<list.length;++i)
		{
			System.out.println(list[i].toString());
		}
		
		
		
	}

}

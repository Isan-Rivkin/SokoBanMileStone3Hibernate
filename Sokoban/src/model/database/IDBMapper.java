package model.database;

import java.util.List;

public interface IDBMapper 
{
	public HighScoreP[] searchHighScore(IQuery query);
	public List<LevelP> getAllLevels();
	public List<PlayerP> getAllPlayers();
	public List<HighScoreP> getAllHighScores();
	
	public boolean savePOJO(POJO pojo);
	public boolean deletePOJO(POJO pojo);
	List<? extends POJO> getAllrows(String tableName);
	public boolean isEntityExist(POJO pojo);	

}

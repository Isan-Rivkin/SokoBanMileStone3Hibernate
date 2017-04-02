package model.database;

public class TempMain {

	public static void main(String[] args) {
		DBHighScoresManager manager = new DBHighScoresManager();
		manager.saveToDB(new Student(11,"shlomi","cohen",54));
	}

}

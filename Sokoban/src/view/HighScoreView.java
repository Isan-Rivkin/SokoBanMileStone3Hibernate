package view;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.database.HighScoreP;

public class HighScoreView extends Observable implements Initializable
{
	@FXML
	private TableView table;
	@FXML 
	private Button button1;
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
	

	}
	public void ILikeBeingControllerAndShit(String s)
	{
		setChanged();
		notifyObservers("I NOTIFY");
	}
	public void initTable()
	{
		
	}
	public void updateTable(List<HighScoreP> list)
	{
		
	}
	public void onStepsSortClick()
	{
		
	}
	public void onTimeSortClick()
	{
		
	}

}

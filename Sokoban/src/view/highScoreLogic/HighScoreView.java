package view.highScoreLogic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.database.HighScoreP;

public class HighScoreView extends Observable implements Initializable
{
	private ArrayList<HighScoreTableRow> table_rows;
	@FXML
	private TableView<HighScoreTableRow> table;
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
		table_rows= new ArrayList<HighScoreTableRow>();
		for(HighScoreP hs : list)
		{
			table_rows.add(new HighScoreTableRow(hs.getLevelName(), hs.getPlayerName(), hs.getPlayerSteps(), hs.getPlayerTime()));
		}
		//update data for table
		ObservableList<HighScoreTableRow> data = FXCollections.observableArrayList();
		  for (HighScoreTableRow r : table_rows) 
		  {
			  data.add(r);
		  }
		  table.getColumns().remove(0,table.getColumns().size());
		  TableColumn<HighScoreTableRow, String> col_level = new TableColumn<HighScoreTableRow, String>("Level Name");
		  TableColumn<HighScoreTableRow, String> col_player = new TableColumn<HighScoreTableRow, String>("Player Name");
		  TableColumn<HighScoreTableRow, String> col_steps = new TableColumn<HighScoreTableRow, String>("Steps");
		  TableColumn<HighScoreTableRow, String> col_time = new TableColumn<HighScoreTableRow, String>("Time");
		  col_level.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("levelName"));
		  col_player.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("levelName"));
		  col_steps.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("playerSteps"));
		  col_time.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("playerTime"));
		  table.getColumns().setAll(col_level,col_player,col_steps,col_time);
		  table.setItems(data);
		  
	}
	public void onStepsSortClick()
	{
		
	}
	public void onTimeSortClick()
	{
		
	}

}

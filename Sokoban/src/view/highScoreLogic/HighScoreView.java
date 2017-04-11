package view.highScoreLogic;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Stack;

import com.sun.jmx.remote.internal.ArrayQueue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import model.database.HighScoreP;

public class HighScoreView extends Observable implements Initializable
{
	private ArrayList<HighScoreTableRow> table_rows;
	@FXML
	private TableView<HighScoreTableRow> table;
	@FXML 
	private Button button1;
	@FXML 
	private ComboBox<String> orderList;
	@FXML
	private TextArea textPlayerName;
	@FXML
	private TextArea textLevelName;
	private Stack<Dialog<ButtonType>> dialog_stack;
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
			
			orderList.getItems().addAll("name","steps","time");
			table.setOnMouseClicked((event)->{
				HighScoreTableRow row = table.getSelectionModel().getSelectedItem();
				searchQuery(row);
			});
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
		  col_player.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("playerName"));
		  col_steps.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("playerSteps"));
		  col_time.setCellValueFactory(new PropertyValueFactory<HighScoreTableRow,String>("playerTime"));
		  table.getColumns().setAll(col_level,col_player,col_steps,col_time);
		  table.setItems(data);
		  //css
		  
	}
	public void onStepsSortClick()
	{
		
	}
	public void onTimeSortClick()
	{
		
	}
	private void searchQuery(HighScoreTableRow row)
	{
		if(row == null)
			return;
		String userName = row.getPlayerName();
		updateObserver("search", "p","",userName,"");
	}
	public void updateObserver(String ...strings)
	{
		List<String> params = new LinkedList<String>();
		for(String s : strings)
		{
			params.add(s);
		}
		setChanged();
		notifyObservers(params);
	}

	public void onSearchHighScoreButton()
	{
		String levelName="";
		String playerName="";
		
		String orderBy=orderList.getValue();
		if(orderBy == null)
		{
			orderBy="";
		}
		if(!textLevelName.getText().isEmpty() && !textPlayerName.getText().isEmpty() )
		{
			levelName=textLevelName.textProperty().get();
			playerName=textPlayerName.textProperty().get();
			updateObserver("search","",levelName,playerName,orderBy);

		}
		else if(!textLevelName.getText().isEmpty())
		{
			levelName=textLevelName.textProperty().get();
			updateObserver("search","l",levelName,playerName,orderBy);

		}
		else if(!textPlayerName.getText().isEmpty())
		{
			playerName=textPlayerName.textProperty().get();
			updateObserver("search","p",levelName,playerName,orderBy);

		}
	}

}

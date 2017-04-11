package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.database.HighScoreP;
import model.policy.Iinterpeter;

public class MyView extends Observable implements FView,Initializable,Observer
{
	private char[][] map_mov,map_stat;
	private Iinterpeter interpeterMove;
	private MediaPlayer m_BackgroundPlayer;
	private Stage stage;
	private boolean gameAlive=false;
	private boolean keepRunning=true;
	private int minTime,secTime,steps=0;
	private String time;
	private StringProperty timer,steper;
	private Timer t,t2;
	private int port=0;
	private Resources img_resources;


	@FXML
	private SokoDisplayer sokoDisplayer;
	@FXML
	private Label timeLabel;
	@FXML
	private Label stepsLabel;
	@FXML
	private Label headerMsgLabel;
	@FXML
	private Label footerMsgLabel;
	//temp stuff for hhighscore
	@FXML 
	private HighScoreView hs_view;
	@FXML
	private Button button1;

	public void MyView() {
		stage =null;
		setFocus();
	}

	@Override
	public void displayHeaderMessage(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				headerMsgLabel.setText(message);
			}
		});
	}


	@Override
	public void displayAlert(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Sokoban Alert");
				alert.setContentText(message);
				alert.showAndWait();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chooseGender();
		playSong();
		sokoDisplayer.drawOpenningImage();

		sokoDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->sokoDisplayer.requestFocus());
		sokoDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				LinkedList<String> params=new LinkedList<String>();
				params=interpeterMove.interperate(event.getCode().toString());
				setChanged();
				notifyObservers(params);
			}
		});
		setFocus();
		timer=new SimpleStringProperty();
		steper = new SimpleStringProperty();
		Timer t = new Timer();
		Timer t2=new Timer();
		
		t2.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(()->{
					if(gameAlive)
						steper.set(""+steps);
				});
			}
		},0,50);
		t.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(()->{
					
					if(gameAlive)
						timer.set("" + showTime());	
					
					else timer.set("00"+":"+"00");
				});
			}
		},0,1000);
	
		timeLabel.textProperty().bind(timer);
		stepsLabel.textProperty().bind(steper);
	}

	@Override
	public void redraw(char[][] map_static, char[][] map_movables,int steps) {
		this.steps=steps;
		if(sokoDisplayer != null){
			sokoDisplayer.updateMap(map_static, map_movables);
			 int temp=0;
			 if (steps!=0 && temp==0){
			 	startTimer();	
			 	temp=steps;
			 }
			}
		}


	public void onLoadLevel(){
		FileChooser fc = new FileChooser();
		fc.setTitle("Load Level");
		fc.setInitialDirectory(new File("./levels"));
		fc.getExtensionFilters().addAll(				
				new FileChooser.ExtensionFilter("All Files", "*.ser","*.obj","*.png","*.xml","*.txt"),
				new FileChooser.ExtensionFilter("Txt: ","*.txt"),
				new FileChooser.ExtensionFilter("Xml: ","*.xml"),
				new FileChooser.ExtensionFilter("Png: ","*.png"),
				new FileChooser.ExtensionFilter("Ser: ",".*ser",".*obj"));
		File file = fc.showOpenDialog(stage);
		if (file!=null){
			LinkedList<String> params = new LinkedList<String>();
			params.add("load");
			params.add(file.getPath());
			setChanged();
			notifyObservers(params);
			zeroTime();
		}
	}
	public void onSaveLevel(){
		 FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Save Level");
         
 		fileChooser.getExtensionFilters().addAll(				
				new FileChooser.ExtensionFilter("Txt: ","*.txt"),
				new FileChooser.ExtensionFilter("Xml: ","*.xml"),
				new FileChooser.ExtensionFilter("Ser: ",".*ser"),
 				new FileChooser.ExtensionFilter("Obj: ",".*obj"));
 		fileChooser.setInitialDirectory(new File("./myLevels"));
         File file = fileChooser.showSaveDialog(stage);
         if (file != null) {
    			LinkedList<String> params = new LinkedList<String>();
    			params.add("save");
    			params.add(file.getPath());
    			setChanged();
    			notifyObservers(params);
         }
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		//stage.setFullScreen(true);
		//x button
				this.stage.setOnCloseRequest((WindowEvent event)->onExit());
						/*new EventHandler<WindowEvent>() {
			        @Override
			        public void handle(WindowEvent event) {
			            onExit();
			        }
			    });*/
	}

	@Override
	public void setInterpeterMove(Iinterpeter interpeterMove) {
		this.interpeterMove=interpeterMove;
		
	}
	private void setFocus()
	{
		sokoDisplayer.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) 
            {
                Platform.runLater(new Runnable()
                {
                    public void run() 
                    {
                    	sokoDisplayer.requestFocus();
                    }
                });                    
            }
        });
	}
  public void playSong(){
				m_BackgroundPlayer = new MediaPlayer(new Media(new File(img_resources.getSongFileName()).toURI().toString()));
				m_BackgroundPlayer.setVolume(0.5);
				m_BackgroundPlayer.play();
  }
  public void muteSong(){
	  m_BackgroundPlayer.setMute(true);
  }
	
	public int incMin(){
		return ++minTime;
	}
	
	public String showTime(){
		incSec();
		time = ""+getMin() + ":" + getSec();
		return time;
	}
	public void startTimer(){
		this.gameAlive=true;
	}
	public int incSec(){
		secTime+=1;
	if(secTime==60){
		secTime=0;
		this.minTime=incMin();
	}
		return secTime;
	}
	public int getSec(){
		return secTime;
	}
	public int getMin(){
		return minTime;
	}
	
	public void setSec(int sec){
		this.secTime=sec;
	}
	public void setMin(int min){
		this.minTime=min;
	}
	public void zeroTime(){
		setSec(0);
		setMin(0);
	}

	@Override
	public void displayFooterMessage(String message) {
		Platform.runLater(new Runnable() {
			@Override
			
			public void run() {
				footerMsgLabel.setText(message);
			}
		});
		
	}
	public void onAbout(){
		this.displayAlert("Soko-Ban and the missing Ack\nDevelopers:\nIsan Rivkin & Daniel Hake.\nMusic:\nDaniel Hake & Isan Rivkin.");		
	}
	public void onExit(){
		this.gameAlive=false;
		LinkedList<String> params=new LinkedList<String>();	
		params.add("exit");
		int oNum=countObservers();
		if(oNum>0){
		setChanged();
		notifyObservers(params);
		}
		Platform.exit();
		System.exit(0);
	}
	public void chooseGender(){
		img_resources=new Resources();
		List<String> choices = new ArrayList<>();
		choices.add("Male");
		choices.add("Female");
		choices.add("Not Sure");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Male", choices);


		dialog.setTitle("Choose Charachter");
		dialog.setHeaderText("Hi You!");
		dialog.setContentText("Choose your Soko gender:");
		Optional<String> result = dialog.showAndWait(); 
		if(!result.isPresent())
			{
			onExit();
			}else{
		String res=result.get();
		switch(res){
			case"Male":
				img_resources.setPlayerFileName(img_resources.getPlayerFileName());
				break;
			case"Female":	
				img_resources.setPlayerFileName(img_resources.getPlayerGirlFileName());
				break;
			case"Not Sure":
				img_resources.setPlayerFileName(img_resources.getPlayerNotSureFileName());
				break;	
			default:
				break;
		}
		//user press x 
		dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
			@Override
			public void handle(DialogEvent event) {
			}
		});
		img_resources.reloadResources();
		sokoDisplayer.setResources(img_resources);
			}

	}
	//reset level button
	public void onResetButton(){
		LinkedList<String> params = new LinkedList<String>();
		params.add("reset");
		zeroTime();
		setChanged();
		notifyObservers(params);
		
		
	}
	public void onServer(){
		if(port>0)
			this.displayAlert("The Server Port is: " + getPort());		
		else 
			this.displayAlert("No server connected");		

	}
	public void setPort(int port){
		this.port=port;
	}

	public int getPort(){
		return this.port;
	}
	// test
	public void onTestHSbutton(List<HighScoreP> list )
	{
		MyView parent = this;
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
			    BorderPane root=null;
			    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("highscore.fxml"));
			    try
			    {
			    	root = (BorderPane) fxmlLoader.load();
			    }
			    catch(IOException e)
			    {
			    	e.printStackTrace();
			    }
			    
			    hs_view = (HighScoreView)fxmlLoader.getController();
			    hs_view.addObserver(parent);
			    hs_view.updateTable(list);
			    Dialog<ButtonType> dialog = new Dialog<ButtonType>();
			    dialog.setTitle("High-Scores Table ");
			    dialog.getDialogPane().setContent(root);
			    dialog.setWidth(500);
			    dialog.setHeight(400);
			    dialog.setResizable(false);
			    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			    Node closeButton  = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
			    closeButton.managedProperty().bind(closeButton.visibleProperty());
			    closeButton.setVisible(false);
			    dialog.showAndWait();
			    
			}
		});
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("I am being invoked UR MAMA ");
	}

}
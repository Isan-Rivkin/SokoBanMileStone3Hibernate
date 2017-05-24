        package view;
	
import controller.SokobanController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.MyModel;
import model.policy.Iinterpeter;
import model.policy.IinterpeterCreator;
import model.policy.InterpeterXML;
import model.policy.Policy;
import model.policy.SokobanPolicy;

//test comment
public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//extract port number
			Parameters argu = getParameters();
			int size = argu.getRaw().toString().lastIndexOf(",");
			String server = argu.getRaw().toString();
			String p=argu.getRaw().toString();
			int port=-1;
			if(p.length()>0 && size>1){
				server=server.substring(1, size);
				p=p.substring(size+2,(argu.getRaw().toString().length()-1));
				if(server.equals("-server")){
				port = Integer.parseInt(p);
				if(port<1000 || port >9999)
					port = -1;
				}
			}
			//
			FXMLLoader fxml=new FXMLLoader();			
			BorderPane root = fxml.load(getClass().getResource("MainView.fxml").openStream());
			//soko stuff
			IinterpeterCreator xmlconfigCreator=new InterpeterXML();
			Iinterpeter interpeter=xmlconfigCreator.loadInterpeter("./configuration/KeyConfig.xml");			
			MyView gui = fxml.getController();
			gui.setInterpeterMove(interpeter);
			Policy policy = new SokobanPolicy();
			MyModel model = new MyModel(policy);
			SokobanController controller = new SokobanController(model, gui,interpeter, port);
			gui.addObserver(controller);
			model.addObserver(controller);
			gui.setStage(primaryStage);
			controller.start();
			//end of soko stuff
			
			Scene scene = new Scene(root,1000,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package oldView.displayers;

import java.io.OutputStream;
import java.io.PrintWriter;

import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.level.Level;

public class DisplayerChar implements GeneralDisplay {

	@Override
	public void display(char[][] map, OutputStream out) {
		PrintWriter writer=new PrintWriter(out);
		if(map != null){
		int col=map[0].length;
		int row=map.length;
		for(int i=0;i<row;++i){
			for(int j=0;j<col;j++){	
				if(map[i][j] == new PlayerOnTarget().getId_char() ){
					writer.print(new Player().getId_char());
				}else if(map[i][j]== new BoxOnTarget().getId_char()){
					writer.print(new Box().getId_char());
				}else
				writer.print(map[i][j]);
			}
			writer.println();
		}
		}
		writer.flush();
	}

	@Override
	public void display(Level l, OutputStream out) {
		// TODO Auto-generated method stub
		
	}

}

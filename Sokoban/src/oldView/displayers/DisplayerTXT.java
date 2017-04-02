package oldView.displayers;

import java.io.OutputStream;
import java.io.PrintWriter;

import common_data.item.Item;
import common_data.item.Movable;
import common_data.level.Level;
/**
 * The txt displayer
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class DisplayerTXT implements GeneralDisplay {
	
	Item map[][];
	Movable movables[][];
    int row,col;
    OutputStream out;
    PrintWriter writer;
	
	public DisplayerTXT() {
		map=null;
		movables=null;
	}
	@Override
	/**
	 * 
	 * @param l
	 * @param out
	 */
	
	//add flush probablly
	public void display(Level l, OutputStream out) {
		
		
		writer=new PrintWriter(out);
		//this.out=out;
		char ch;
		map=l.getMap();
		movables=l.getMovables();
		row=l.getHeight();
		col=l.getWidth();
		for(int i=0;i<row;++i){
			for(int j=0;j<col;j++){
				
				if(movables[i][j]==null){
				     ch=map[i][j].getId_char();
				}else{
					ch=movables[i][j].getId_char();
				}
				writer.print(ch);
			}
			writer.println();
		}
		
		writer.flush();
	}
	@Override
	public void display(char[][] map, OutputStream out) {
		// TODO Auto-generated method stub
		
	}

}

package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SokoDisplayer extends Canvas{
	private char[][] map;
	private char[][] map_statics;
	private char[][] map_movables;
	private HashMap<Character, Image> images;
	
	private Resources resources;
	private StringProperty temp;
	private GraphicsContext gc;
	
	public SokoDisplayer() {
		gc=getGraphicsContext2D();
		map_statics=null;
		map_movables=null;
		map=null;
		resources=null;
		

	}
	
	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		images=new HashMap<Character,Image>();		
		images.put('@', resources.getBox());
		images.put('A', resources.getPlayer());
		images.put('o', resources.getTarget());
		images.put('#', resources.getWall());
		images.put(' ', resources.getFloor());
		this.resources = resources;
	}

	public void updateMap(char[][] map_statics,char [][] map_movables){
		this.map_statics=map_statics;
		this.map_movables=map_movables;
		redraw();
	}
	public void redraw(){
		if (map_statics == null || map_movables == null)
			return;
		
		double winH=getHeight();
		double winW=getWidth();
		double itemW=winW/map_statics[0].length;
		double itemH=winH/map_statics.length;
		gc.clearRect(0, 0, winW, winH);
		gc.setFill(Color.rgb(50, 50, 50, 0.7));
		gc.fillRect(0, 0, winW, winH);
		
		for(int i=0;i<map_statics.length;++i){
			for(int j=0;j<map_statics[0].length;++j){
				gc.drawImage(images.get(map_statics[i][j]), itemW*j, itemH*i, itemW,itemH);
			}
		}
		for(int i=0;i<map_movables.length;++i){
			for(int j=0;j<map_movables[0].length;++j){
				if(map_movables[i][j]!='N')
				gc.drawImage(images.get(map_movables[i][j]), itemW*j, itemH*i, itemW,itemH);
			}
		}
		
	}
	public void drawOpenningImage(){
		double winH=getHeight();
		double winW=getWidth();
		gc.clearRect(0, 0, winW, winH);
		gc.setFill(Color.rgb(50, 50, 50, 0.7));
		gc.fillRect(0, 0, winW, winH);
		gc.drawImage(resources.getOpenningImg(), 0, 0, winW, winH);
	}
	
}

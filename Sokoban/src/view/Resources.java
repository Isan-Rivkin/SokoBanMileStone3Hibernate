package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Resources extends Canvas{
private Image player,wall,box,target,floor,openningImage,playerGirl,playerNotSure;

private String playerFileName,boxFileName,wallFileName,targetFileName,floorFileName,nextButton,prevButtn,openningFileName,playerGirlFileName,playerNotSureFileName,songFileName;

private Media song;


public Resources() {

playerFileName = "./resources/player.png";
wallFileName = "./resources/wall.png";
targetFileName = "./resources/target.png";
boxFileName = "./resources/box.png";
floorFileName = "./resources/floor.png";
openningFileName="./resources/openning.png";
songFileName = "./resources/sokoSound.mp3";	
playerGirlFileName = "./resources/playerGirl.png";
playerNotSureFileName ="./resources/playerNotSure.png";



}

public String getNextButton() {
	return nextButton;
}
public void setNextButton(String nextButton) {
	this.nextButton = nextButton;
}
public String getPrevButtn() {
	return prevButtn;
}
public void setPrevButtn(String prevButtn) {
	this.prevButtn = prevButtn;
}

public Image getPlayer() {
	return player;
}
public void setPlayer(Image player) {
	this.player = player;
}

public Image getFloor() {
	return floor;
}
public void setFloor(Image floor) {
	this.floor = floor;
}
public String getFloorFileName() {
	return this.floorFileName;
}
public void setFloorFileName(String floorFileName) {
	this.floorFileName=floorFileName;
}
public Image getWall() {
	return wall;
}
public void setWall(Image wall) {
	this.wall = wall;
}
public Image getBox() {
	return box;
}
public void setBox(Image box) {
	this.box = box;
}
public Image getTarget() {
	return target;
}
public Image getOpenningImg(){
	return this.openningImage;
}
public void setTarget(Image target) {
	this.target = target;
}
public String getPlayerFileName() {
	return this.playerFileName;
}
public void setPlayerFileName(String playerFilName) {
	this.playerFileName=playerFilName;
}
public String getBoxFileName() {
	return this.boxFileName;
}
public void setBoxFileName(String boxFileName) {
	this.boxFileName=boxFileName;
}
public String getWallFileName() {
	return this.wallFileName;
}
public void setWallFileName(String wallFileName) {
	this.wallFileName=wallFileName;
}
public String getTargetFileName() {
	return this.targetFileName;
}
public String getOpenningImageFileName(){
	return this.openningFileName;
}
public void setOpenningImage(String fname){
	this.openningFileName=fname;
}
public void setTargetFileName(String targetFileName) {
	this.targetFileName=targetFileName;
}
public void reloadResources(){
	try {
		player = new Image(new FileInputStream(getPlayerFileName()));
		box = new Image(new FileInputStream(getBoxFileName()));
		target = new Image(new FileInputStream(getTargetFileName()));
		wall = new Image(new FileInputStream(getWallFileName()));
		floor = new Image(new FileInputStream(getFloorFileName()));
		openningImage=new Image(new FileInputStream(getOpenningImageFileName()));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}

public Media getSong() {
	return song;
}
public void setSong(Media song) {
	this.song = song;
}
public String getSongFileName() {
	return songFileName;
}
public void setSongFileName(String songFileName) {
	this.songFileName = songFileName;
}
public Image getPlayerGirl() {
	return playerGirl;
}

public void setPlayerGirl(Image playerGirl) {
	this.playerGirl = playerGirl;
}

public Image getPlayerNotSure() {
	return playerNotSure;
}

public void setPlayerNotSure(Image playerNotSure) {
	this.playerNotSure = playerNotSure;
}

public String getPlayerGirlFileName() {
	return playerGirlFileName;
}

public void setPlayerGirlFileName(String playerGirlFile) {
	this.playerGirlFileName = playerGirlFile;
}

public String getPlayerNotSureFileName() {
	return playerNotSureFileName;
}

public void setPlayerNotSureFileName(String playerNotSureFile) {
	this.playerNotSureFileName = playerNotSureFile;
}

}

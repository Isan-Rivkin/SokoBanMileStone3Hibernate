package controller;

import java.util.Observable;
import java.util.Observer;

public  interface FController extends Observer{

	public void start();
	public void stop();
}

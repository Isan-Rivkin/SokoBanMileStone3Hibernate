package model.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metadata.METADATA;

public class ServiceRequester
{
	public class ServiceResponse
	{
		public ServiceResponse(String metaData, String jsonObject) {
			super();
			this.metaData = metaData;
			this.jsonObject = jsonObject;
		}
		private String metaData;
		private String jsonObject;
		public String getMetaData() {
			return metaData;
		}
		public void setMetaData(String metaData) {
			this.metaData = metaData;
		}
		public String getJsonObject() {
			return jsonObject;
		}
		public void setJsonObject(String jsonObject) {
			this.jsonObject = jsonObject;
		}
		
	}
	private GsonBuilder b;	
	private Gson g;
	public ServiceRequester() 
	{
		b= new GsonBuilder();
		g = b.create();
	}
	public Gson getGson()
	{
		return g;
	}
	public ServiceResponse requestService(String jsonObject, String jsonMeta, String ip, int port)
	{
		String metaFromServerJson="";
		String jsonObjectFromServer="";
		
		try {
		
		Socket theServer;

			theServer = new Socket(ip, port);
		
		if (theServer.isConnected()) 
		{
			// initialize server streams.
			InputStream is = theServer.getInputStream();
			PrintWriter writer = new PrintWriter(theServer.getOutputStream());
			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(is));
			// start session
			// send protocol to server
			writer.println(jsonMeta);
			writer.flush();
			//send data model to server 
			writer.println(jsonObject);
			writer.flush();
			// read from server 
			
			
			metaFromServerJson = readFromServer.readLine();
			jsonObjectFromServer = readFromServer.readLine();
			theServer.close();
			
		}
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return new ServiceResponse(metaFromServerJson, jsonObjectFromServer);
	}
}

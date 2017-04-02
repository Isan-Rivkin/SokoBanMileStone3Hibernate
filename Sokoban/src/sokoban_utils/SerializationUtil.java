package sokoban_utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class SerializationUtil {
	
	// Serialize an object 
	
	public static void serialize(Object obj, String path) throws IOException{
		FileOutputStream out=new FileOutputStream(path);
		ObjectOutputStream oos=new ObjectOutputStream(out);
		oos.writeObject(obj);
		out.close();
		
	}

	public static void serialize(Object obj, OutputStream o) throws IOException{
		//FileOutputStream out=(FileOutputStream) o;
		ObjectOutputStream oos=new ObjectOutputStream(o);
		oos.writeObject(obj);
		o.close();
		
	}
	
	//Desitialize an object
	
	public static Object deserialize(InputStream in) throws IOException, ClassNotFoundException{
		//FileInputStream in=new FileInputStream(path);
		ObjectInputStream ois=new ObjectInputStream(in);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}
	
	

}

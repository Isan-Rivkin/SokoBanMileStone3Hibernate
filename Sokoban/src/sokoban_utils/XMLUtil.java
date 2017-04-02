package sokoban_utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class XMLUtil {

	// encode file
	public static void encodeXML(Object obj, String path) throws FileNotFoundException{
		XMLEncoder e=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
		e.writeObject(obj);
		e.close();
		
	}
	
	public static void encodeXML(Object obj, OutputStream out) throws FileNotFoundException{
		XMLEncoder e=new XMLEncoder(new BufferedOutputStream(out));
		e.writeObject(obj);
		e.close();
		
	}
	
	
	
	//decode file
	
	public static Object decodeXML(String path) throws FileNotFoundException{
		XMLDecoder d=new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
		Object obj=d.readObject();
		d.close();
		return obj;
	}
	public static Object decodeXML(InputStream in) throws FileNotFoundException{
		XMLDecoder d=new XMLDecoder(new BufferedInputStream(in));
		Object obj=d.readObject();
		d.close();
		return obj;
	}
	
}

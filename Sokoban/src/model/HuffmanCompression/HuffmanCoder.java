package model.HuffmanCompression;
import java.util.HashMap;

public interface HuffmanCoder {
	public String encode(String text);
	public String decode(String text);
	public HashMap<String,Character> getDecodeTree();
	public HashMap<Character,String> getEncodeTree();
}

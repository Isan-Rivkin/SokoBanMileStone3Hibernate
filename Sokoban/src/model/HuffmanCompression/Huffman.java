package model.HuffmanCompression;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman implements HuffmanCoder
{
	   int IdCounter = 0;                               // used to number each node with unique Id
	   HashMap<Character, String> encoding;
	   HashMap<String,Character> decoding;
       
	   /**
        * the node used to create the Huffman tree
        * @author thiebaut
        *
        */
	   
	   public Huffman() 
	   {
		   encoding = new HashMap<>();
		   decoding = new HashMap<>();
	   }
	   
	   
      public static class Node implements Comparable<Node> 
       {
               public char letter;                             // the letter from the string, or '#' if inner node
               public int Id;                                  // unique Id (used to generate DOT graph)       
               public int freq;                                // counts number of occurrences
               public Node left;                               // pointer to left child, if any
               public Node right;                              // pointer to right child, if nay
               Node(char l, int f, Node lft, Node rt ,int id) 
               {
                       //Id = IdCounter++;
                       Id=id;
                       letter = l; freq = f; left = lft; right = rt; 
               }
               /**
                * returns whether node is a leaf.
                * @return true if leaf, false otherwise.
                */
               public boolean isLeaf() { return left==null && right==null; }
               
               @Override
               /**
                * compareTo: needed because nodes will be kept in priority queue that
                * keeps node with smallest freq value at the root. 
                */
               public int compareTo(Node n) 
               {
                       return freq - n.freq;
               }
       }
       
       /**
        * retuns a synthetic string of characters (a, e, i, o, u) with a fixed distribution.
        * @return the string.
        */
       // String s 
       public String getStringOfLetters() 
       {
               String s = "";
               for ( int i=0; i<50; i++ ) s += "e";   // 50% of e
               for ( int i=0; i<30; i++ )  s += "a";   // 30% of a
               for ( int i=0; i<10; i++ )  s += "i";   // 10% of i
               for ( int i=0; i<8; i++ )   s += "o";   // 8% of o
               for ( int i=0; i<2; i++ )   s += "u";   // 2% of u
               return s;
       }
       
       /**
        * computes the frequency associated with each letter found in the string s.
        * @param s the string containing the characters to encode.
        * @return a hashMap of the characters and their associated frequencies (as ints)
        */
       private HashMap<Character, Integer> getLetterFrequencies( String s ) 
       {
               HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
               for ( int i=0; i< s.length(); i++ ) 
               {
                       char c = s.charAt(i);
                       if (freq.containsKey( c ))
                               freq.put( c, freq.get( c )+1 );
                       else
                               freq.put( c,  1 );
               }
               return freq;
       }
       
       /**
        * buildHuffmanTree: builds a Huffman trie 
        * @param freqs the hashmap of frequencies associated with each char.
        * @return the root of the Huffman tree.
        */
       private Node buildHuffmanTree( HashMap<Character, Integer> freqs ) 
       {
               PriorityQueue<Node> prioQ = new PriorityQueue<Node>();
               for ( char c: freqs.keySet() ) 
               {
            	   IdCounter++;
                       prioQ.add( new Node( c, freqs.get(c), null, null,IdCounter) );
               }
               while ( prioQ.size() > 1 ) 
               {
                       Node left = prioQ.poll();
                       Node right = prioQ.poll();
                       IdCounter++;
                       prioQ.add( new Node( '#', left.freq + right.freq, left, right,IdCounter));
               }
               
               return prioQ.poll();
       }

       /**
        * generates the DOT representation of the Hoffman tree
        * @param root
        */
       private void generateHuffmanTreeDOT(Node root) 
       {
               System.out.println( "\ndigraph Huffman {" );
               DotDFS( root );
               System.out.println( "}\n" );
       }
       
       /**
        * recursive trunk of the DOT generation.
        * @param node
        */
       private void DotDFS( Node node ) 
       {
               if ( node.isLeaf() ) 
               {
                       System.out.println(  String.format( "%d [label=\"%s (%d)\"];", 
                                       node.Id, node.letter, node.freq) );
                       return;
               }
               if ( node.left != null ) 
               {
                       System.out.println(  String.format( "%d [label=\"%s (%d)\"];", 
                                       node.Id, node.letter, node.freq) );
                       System.out.println( String.format( "%d -> %d [label=\"0\"];", 
                                       node.Id, node.left.Id ) );
                       DotDFS( node.left );
               }
               if ( node.right != null ) 
               {
                       System.out.println(  String.format( "%s [label=\"%s (%d)\"];", 
                                       node.Id, node.letter, node.freq) );
                       System.out.println( String.format( "%s -> %s [label=\"1\"];", 
                                       node.Id, node.right.Id ) );
                       DotDFS( node.right );
               }
       }

       /**
        * gets the string of 0s and 1s associated with each character.  Should normally
        * be a bit pattern, but is a String for the purpose of simplifying the code.
        * @param root
        * @return a hashMap of characters and their associated code.
        */
       private HashMap<Character, String> getEncoding(Node root) 
       {
               HashMap<Character, String> encoding = new HashMap<Character, String>();
               DFS( root, "", encoding );
               return encoding;
       }

       /**
        * Recursively performs a DFS to visit the Huffman trie and assign code to each
        * leaf. 
        * @param node
        * @param code
        * @param encoding
        */
       private void DFS(Node node, String code, HashMap<Character, String> encoding) 
       {
               if ( node.isLeaf() ) 
                       encoding.put( node.letter, code );
               else {
                       if ( node.left != null ) 
                               DFS( node.left, code+"0", encoding );
                       if ( node.right != null )
                               DFS( node.right, code+"1", encoding );
               }
       }

       
       private HashMap<String,Character> getDecoder(HashMap<Character,String> encoder)
       {
    	   HashMap<String,Character> decoding = new HashMap<>();
    	   for ( char c: encoder.keySet() )
           {
                   String code = encoder.get( c );
                   decoding.put(code, c);
           }
    	   return decoding;
       }
       
       private String encodeText(String text, HashMap<Character,String> encoder)
       {
    	   int i=0;
    	   String code = "";
    	   while (i<text.length())
           {
        	   String tempCode="";
        	   char tempChar=' ';
        	   tempChar = text.charAt(i);
        	   if((tempCode = encoder.get(tempChar)).equals(""))
        	   {
        		   i++;
        	   }
        	   else{
        		   i++;
        		   code += tempCode;  
        	   }
           }
    	   return code;
       }
       private String decodeText(String text, HashMap<String,Character> decoder)
       {
           String original = "";
           int i=0;
           int j=1;
           while(j<=text.length())
           {
        	   String tempCode="";
        	   Character tempChar=' ';
        	   tempCode = text.substring(i, j);
        	   if((tempChar = decoder.get(tempCode))==null)
        	   {
        		   j++;
        	   }
        	   else {
        		  i=j;
        		  j++;
        		  original+=tempChar;
        	   }
           }
    	   return original;
       }
       /**
        * MAIN ENTRY POINT
        * @param args
        */

	@Override
	public String encode(String text) 
	{
		 HashMap<Character, Integer> freqs = getLetterFrequencies(text);
         Node root = buildHuffmanTree(freqs);         
         encoding = getEncoding(root);
//         System.out.println(encoding);

      
         /**
          * See how the encoding looks like + initialize the decoder HashMap 
          */               
         decoding = getDecoder(encoding);
         
         /**
          *  encoding!!!!!
          */
         String code="";
         code = encodeText(text,encoding);
		return code;
	}

	@Override
	public String decode(String text) 
	{
      
		String original = "";
        original = decodeText(text,decoding);
   
		return original;
	}

	@Override
	public HashMap<String, Character> getDecodeTree() 
	{
		if (decoding!=null)
			return decoding;
		else return null;
	}

	@Override
	public HashMap<Character, String> getEncodeTree() 
	{
		if (encoding!=null)
			return encoding;
		else return null;	
	}



}

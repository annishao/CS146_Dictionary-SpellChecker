package cs146F20.shao.project4;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class RBTTester {

	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree<String> rbt = new RedBlackTree<String>();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        rbt.printTree();
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
            
    }
    
    // Spell checker
	@Test
	public void testSpellCheck() {
		// Initialize RedBlackTree instance for dictionary.
		RedBlackTree<String> dictionary = new RedBlackTree<String>();
		// Initialize time.
		double time = System.currentTimeMillis();
		try {
			// Initialize BufferedReader to read lines from dictionary.txt.
			BufferedReader dictInput = new BufferedReader(new FileReader("dictionary.txt"));
			// Declare string variable.
			String string;
			// Insert strings from dictionary.txt into dictionary RedBlackTree 
			// until there are no more lines in dictionary.txt.
			while((string = dictInput.readLine()) != null) {
				dictionary.insert(string);
			}
			// Print out time it took to create dictionary and close dictInput.
			System.out.println();
			System.out.println("Time to create dictionary: " + (System.currentTimeMillis() - time));
			dictInput.close();
			
			// Spell checks poem.txt.
			BufferedReader poemInput = new BufferedReader(new FileReader("poem.txt"));
			int missingWords = 0;
			int totalWords = 0;
			time = System.currentTimeMillis();
			while((string = poemInput.readLine()) != null) {
				String[] line = string.split(" ");
				for(int i = 0; i < line.length; i++) {
					totalWords++;
					// Removes all non-letters and converts to lowercase.
					line[i] = line[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
					Node<String> found = dictionary.lookup(line[i]);
					if(found == null) {
						missingWords++;
					}
				}
			}
			System.out.println("Time to look up words: " + (System.currentTimeMillis() - time));
			System.out.println("Total words: " + totalWords);
			System.out.println("Missing words: " + missingWords);
			poemInput.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
    
    public static String makeString(RedBlackTree<String> t)
    {
       class MyVisitor implements Visitor {
          String result = "";
          public void visit(Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree t) {
    	{
    	       class MyVisitor implements Visitor {
    	          String result = "";
    	          public void visit(Node n)
    	          {
    	        	  if(!(n.key).equals("") && n.parent == null) {
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: \n";
    	        	  } else if(!(n.key).equals("")) {
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
    	        	  }
    	             
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
 }
  

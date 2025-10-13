import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Four {
    public static void main(String[] args) {
    	String[] words = new String[500];
    	int[] frequencies = new int[500];
    	int size = 0;

    	// get stop words
    	String[] stopWords = new String[200];
    	// Fixed location.
    	Path filePath = Paths.get("../stop_words.txt");
    	try {
    		String content = Files.readString(filePath);
        	stopWords = content.split(",");
    	} catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        String filename = args[0];
        File file = new File(filename);

    	// scan the file line by line
    	try {
    		Scanner scanner = new Scanner(file);

    		while (scanner.hasNextLine()) {
    			String line = scanner.nextLine();
    			int startChar = -1;
	    		for (int i = 0; i < line.length(); i++) {
	    			char c = line.charAt(i);
	    			//System.out.println("startChar = " + startChar);
	    			//System.out.println("c = " + c);
	    			//System.out.println("((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) = " + ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')));
	    			if (startChar == -1) {
		    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
		    				//System.out.println("found startchar: " + i);
	                		startChar = i;
	            		}
	            	} else {
		    			if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
		    				//System.out.println("found end: " + i);
		    				boolean found = false;
		    				String word = line.substring(startChar, i).toLowerCase();

		    				boolean isWordInStopwords = false;
		    				for (int j = 0; j < stopWords.length; j++) {    		
					    		if (word.equals(stopWords[j])) {
					    			isWordInStopwords = true;
					    		}
					    	}

		    				if (!isWordInStopwords) {
		    					// see if it already exists,count
		    					int wordIndex = -1;
		    					for (int j = 0; j < size; j++) {
		    						if (word.equals(words[j])) {
		    							frequencies[j] +=1;
		    							wordIndex = j;
			    						found = true;
			    						break;
		    						}
		    					}
		    					// if not, add
		    					if (!found) {
		    						//System.out.println("found new word: " + word);
		    						if (size == words.length) {
		    							//expand array
										String[] wordsBig = new String[words.length * 2];
										int[] frequenciesBig = new int[frequencies.length * 2];

										for (int k = 0; k < words.length; k++) {
											wordsBig[k] = words[k];
											frequenciesBig[k] = frequencies[k];
										}
										words = wordsBig;
										frequencies = frequenciesBig;
		    						}
		    						words[size] = word;
		    						frequencies[size] = 1;
		    						size++;
		    					} else if (size > 1) {
		    						// reorder
		    						for (int p = wordIndex - 1; p >= 0; p--) {
		    							if (frequencies[wordIndex] > frequencies[p]) {
		    								int tempInt = frequencies[p];
		    								frequencies[p] = frequencies[wordIndex];
		    								frequencies[wordIndex] = tempInt;
		    								String tempStr = words[p];
		    								words[p] = words[wordIndex];
		    								words[wordIndex] = tempStr;
		    								wordIndex = p;
		    							}
		    						}
		    					}		    							    					
		    				}
		    				startChar = -1;
		    			}
	    			}
	    		}
	    	}
    		scanner.close();
    	} catch (FileNotFoundException e) {
    		System.err.println("Error: File not found at " + filename);
    	}

    	// Print results
    	for (int i = 0; i < 25; i++) {
    		System.out.println(words[i] + " - " + frequencies[i]);
    	}
    }
}
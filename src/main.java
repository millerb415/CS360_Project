import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner fscan;
		BufferedReader br;
		FileReader fr;
		String line;
		try {
			 // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader("APU_CS370_input.txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                lex(line);
            }   

            // Always close files.
            bufferedReader.close();  
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
	}

	private static void lex(String line) throws IOException {
		// TODO Auto-generated method stub
		
          char[] cArray = line.toCharArray();
          for (char c : cArray) {
        	  if (c != ' ')
			System.out.println(c);
		}
		}

}

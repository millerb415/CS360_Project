import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class main {
	private static final String[] keywords = new String[] {	
			"Integer", "Float", "Function", 
			"If", "Else", "Return", "Write", 
			"DOWhile"
															};
	private static final String[] Operators = new String[] {	
			"+", "-", "*", 
			"/", "<=", ">=", "=", 
			"++"
			};
	private static final String[] Separators = new String[] {	
			"{", "}", "(", 
			")", ",", ";"
			};
	
	public static void main(String[] args) {
		BufferedReader br;
		FileReader fr;
		BufferedWriter bw;
		String line;
		try {
			 // FileReader reads text files in the default encoding.
            fr = new FileReader("APU_CS370_input.txt");

            // Always wrap FileReader in BufferedReader.
            br = new BufferedReader(fr);
            bw = new BufferedWriter(new FileWriter("APU_CS370_output.txt"));

            while((line = br.readLine()) != null) {
                lex(line, bw );
            }   

            // Always close files.
            br.close();  
		}catch (Exception e) {
		
			System.err.println(e);
		}
		
	}

	private static void lex(String line, BufferedWriter bw) throws IOException {
		
		
          char[] cArray = line.toCharArray();
          for (char c : cArray) {
        	  if (c != ' ')
			bw.write(c);
		}
		}

}

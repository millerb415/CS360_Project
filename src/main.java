import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
		PrintWriter pw;
		String line;
		FileWriter fw;
		try {
			 // FileReader reads text files in the default encoding.
            fr = new FileReader("APU_CS370_input.txt");
            fw = new FileWriter("APU_CS370_output.txt");
            // Always wrap FileReader in BufferedReader.
            br = new BufferedReader(fr);
            pw = new PrintWriter(fw, true);
            //pw.println("hi");
            while(( line = br.readLine() ) != null ) {
                lex(line, pw );
            }   

            // Always close files.
            br.close();
            System.out.println("Done!");
		}catch (Exception e) {
		
			System.err.println(e);
		}
		
	}

	private static void lex(String line, PrintWriter pw) throws IOException {
		Scanner scan = new Scanner(line);
		String word = "";
		//pw.print(scan.next());
		char[] cArray = line.toCharArray();
        for (char c : cArray) {
          switch (c) {
         case ' ':
        	 checkword(word, pw);
        	 word = "";
        	 break;
         case '+': 	 
         case '-': 
         case '/':
         case '*':
         case '>':
         case '<':
         case '=':
        	 pw.printf("%10c      Operator\n", c);
        	 checkword(word, pw);
        	 word = "";
        	 break;
         case '(': 	 
         case ')': 
         case '{':
         case '}':
         case ';':
         case ',':
        	 pw.printf("%10c      Separator\n", c);
        	 checkword(word, pw);
        	 word = "";
        	 break;
         default: 
        	 word += c;
          }	  
		}
		
//		while (() != null ) 
//		{
//			;
//			pw.print(word + "    ");
//			for (String Op : Operators) {
//				if (Op == word)
//				{
//					pw.println("Operand");
//				break;
//				}	
//			}
//			
//			}
//			for (String Op : Operators) {
//				if (Op == word)
//				{
//					pw.println("Operand");
//				break;
//				}	
//			}
//			for (String Op : Operators) {
//				if (Op == word)
//				{
//					pw.println("Operand");
//				break;
//				}	
//			}
//		
//		}
//		
          
		}

	private static void checkword(String word, PrintWriter pw) {
		// TODO Auto-generated method stub
		switch (word) {
		case "Integer":
		case "Float":
		case "Function": 
		case "If":
		case "Else":
		case "Return":
		case "Write": 
		case "DOWhile":
			pw.printf("%10s       Keyword\n", word);
			break;
		case "":
			break;
		case " ":
			break;			
		default:
			pw.printf("%10s       Identifier\n", word);
		}
	}

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class main 
{
    ////////////////////////////////////////////////////
	//
	//
	//
	public static void main(String[] args) 
	{
		BufferedReader br;
		FileReader fr;
		PrintWriter pw;
		String line;
		FileWriter fw;
		try 
		{
            fr = new FileReader("APU_CS370_input.txt");
            fw = new FileWriter("APU_CS370_output.txt");
            br = new BufferedReader(fr);
            pw = new PrintWriter(fw, true);
            pw.print("Group Name(3) – Members(Micah Edington, Ryan Alcantra, Brandon Miller)\n");
            pw.printf("%10s%15s\n", "Lexeme", "Token");
            pw.println("    ------          -----");
            while(( line = br.readLine() ) != null ) 
            {
                lex(line, pw );
            }   
            // Always close files.
            br.close();
            pw.close();
            System.out.println("Done!");
		}
		catch (Exception e) 
		{
		
			System.err.println(e);
		}
		
	}
	
    ////////////////////////////////////////////////////////////////
	//
	//
	//
	private static void lex(String line, PrintWriter pw) throws IOException 
	{
		String word = "";
        for (char c : line.toCharArray()) 
        {
          switch (c) 
          {
          	case ' ':
          	case '	':
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
          		checkword(word, pw);
          		pw.printf("%10c%15s\n", c, "Operator");
          		word = "";
          		break;
          	case '(': 	 
          	case ')': 
          	case '{':
          	case '}':
          	case ';':
          	case ',':
          		checkword(word, pw);
          		pw.printf("%10c%15s\n", c, "Separator");
          		word = "";
          		break;
          	default: 
          		word += c;
          }
          
		}
        checkword(word, pw); 
		}
   
	///////////////////////////////////////////////////////////
	//
	//
	//
	private static void checkword(String word, PrintWriter pw) 
	{
	   switch (word) 
		{
		case "Integer":
		case "Float":
		case "Function": 
		case "If":
		case "Print":
		case "Else":
		case "Return":
		case "Write": 
		case "DOWhile":
			pw.printf("%10s%15s\n", word, "Keyword");
			break;
		case "":
		case " ":
		case "	":
			break;			
		default:
			if (word.charAt(0)>= '0' && word.charAt(0) <= '9')
			{
				for (char c : word.toCharArray()) 
				{
					if(c == '.') 
					{
						pw.printf("%10s%15s\n", word, "float");
						return;
					}	
				}
				pw.printf("%10s%15s\n", word, "Integer");
				return;
			}
			pw.printf("%10s%15s\n", word,"Identifier");
			return;
		}
	}
}

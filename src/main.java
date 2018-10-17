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
		apuParse apu = new apuParse("APU_CS370_input.txt", "APU_CS370_output.txt" );
		
	}

	////////////////////////////////////////////////////////////////
	//
	//
	//
	//	private static void lex(String line, PrintWriter pw) throws IOException 
	//	{
	//		String word = "";
	//        for (char c : line.toCharArray()) 		// loops through each character
	//        {
	//          switch (c) 							// switch statement for each character case
	//          {
	//          	case ' ':
	//          	case '	':
	//          		checkword(word, pw);			// calls checkword function to show type of char
	//          		word = "";
	//          		break;
	//          	case '+': 	 						// cases for operators
	//          	case '-': 
	//          	case '/':
	//          	case '*':
	//          	case '>':
	//          	case '<':
	//          	case '=':
	//          		checkword(word, pw);
	//          		pw.printf("%10c%15s\n", c, "Operator");		// formatted print statement
	//          		word = "";
	//          		break;
	//          	case '(': 	 						// cases for separators
	//          	case ')': 
	//          	case '{':
	//          	case '}':
	//          	case ';':
	//          	case ',':
	//          		checkword(word, pw);
	//          		pw.printf("%10c%15s\n", c, "Separator");
	//          		word = "";
	//          		break;
	//          	default: 
	//          		word += c;						// moves on to next character
	//          }
	//          
	//		}
	//        checkword(word, pw); 
	//		}
	//   
	//	///////////////////////////////////////////////////////////
	//	//
	//	//
	//	//
	//	private static void checkword(String word, PrintWriter pw) 
	//	{
	//	   switch (word) 							// switch statement for 
	//		{
	//		case "Integer":							// prints the word in each respective case
	//		case "Float":
	//		case "Function": 
	//		case "If":
	//		case "Print":
	//		case "Else":
	//		case "Return":
	//		case "Write": 
	//		case "DOWhile":
	//			pw.printf("%10s%15s\n", word, "Keyword");		// formatted print statement
	//			break;
	//		case "":
	//		case " ":
	//		case "	":
	//			break;			
	//		default:
	//			if (word.charAt(0)>= '0' && word.charAt(0) <= '9')	// checks to see if char is a number
	//			{
	//				for (char c : word.toCharArray()) 
	//				{
	//					if(c == '.') 								// checks for decimal to change to float
	//					{
	//						pw.printf("%10s%15s\n", word, "float");
	//						return;
	//					}	
	//				}
	//				pw.printf("%10s%15s\n", word, "Integer");		// if no decimal is found change to int
	//				return;
	//			}
	//			pw.printf("%10s%15s\n", word,"Identifier");			// if no number is found change to identifier
	//			return;
	//		}
	//	}
}

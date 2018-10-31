import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class apuParse{
	//	private String readin;
	//	private String readout;
	private BufferedReader br;
	private FileReader fr;
	private PrintWriter pw;
	private FileWriter fw;
	private boolean ifRead;
	private Scanner scan;
	private String lastWord = "";
	private boolean scanOpen = false;

	public apuParse(String in, String out) 
	{
		try 												// code for reading in file
		{
			fr = new FileReader(in);
			fw = new FileWriter(out);
			br = new BufferedReader(fr);
			pw = new PrintWriter(fw, true);
			pw.println("Group Name(3) – Members(Micah Edington, Ryan Alcantra, Brandon Miller)\n");
			ifRead = true;
		}
		catch (Exception e) 
		{

			System.err.println(e);
		}	
	}

	public boolean checkLex() throws IOException 
	{
		boolean check = APUMAIN();

		if (check) {
			pw.println("Syntax is correct No errors Found");
		}
		return check;
	}
	////////////////////////////////////
	//
	private boolean APUMAIN() throws IOException 
	{
		pw.println("<APU_CS370> ::= void main() { <statement> }");
		boolean valid = false;
		if (getNextWord().equals("void")) 
		{
			if (getNextWord().equals("main")) 
			{
				if(getNextWord().equals("(")) 
				{
					if (getNextWord().equals(")")) 
					{
						if(getNextWord().equals("{"))
						{
							valid =  APUSTATE();
						}
						else 
						{
							error("{");
						}
					}
					else 
					{
						error(")");
					}
				}
				else 
				{
					error("(");
				}
			}
			else 
			{
				error("main");
			}
		}
		else 
		{
			error("void");
		}
		return valid;
	}


	private boolean APUSTATE() throws IOException {
		boolean isEmpty = true;
		boolean isvalid = true;
		String word = "";
		while (!word.equals("}") && br.ready() ) 
		{
			word = getNextWord();
			switch (word) {	
			case "Float":
			case "Integer":
				pw.println("<Statement> ::= <Declaration>");
				isvalid = APUDECL(word);
				isEmpty = false;
				break;
			case "If": 
				pw.println("<Statement> ::= <If statement>");
				isvalid = APUIF();
				isEmpty = false;
				break;
			case "DOWhile":
				pw.println("<Statement> ::= <DOWhile statement>");
				isvalid = APUINTEGER();
				isEmpty = false;
				break;
			default:
				break;
			}
			if (!isvalid) {
				break;
			}
		}
		scan.close();
		if (isEmpty)
		{
			pw.println("< Empty >");
			pw.println("< Empty > ::= Epsilon");
		}
		if (!word.equals("}"))
		{
			error("}");
			return false;
		}
		return true;
	}
	private boolean APUIF() 
	{

		return false;
	}
	private boolean APUDOWHILE() 
	{

		return false;
	}
	private boolean APUDECL(String type) 
	{
		if (type.equals("Integer")) {
			return APUINTEGER();
		}
		return APUFLOAT();
	}
	private boolean APUINTEGER() 
	{
		pw.println("<Declaration> ::= Integer <Identifier> = <Integer>;");
		return false;
	}
	private boolean APUFLOAT() 
	{
		pw.println("<Declaration> ::= Float <Identifier> = <Float>;");
		return false;
	}
	private boolean APUASSIGN() 
	{

		return false;
	}
	private boolean APUCONDI() 
	{

		return false;
	}
	private boolean APUCOMPOP() throws IOException 
	{
		String word = getNextWord();
		pw.println("<CompOperator> ::= == | > | < | >= | <= | != ");
		if (word.substring(1).equals("CompOperator")) 
		{
			if(word.charAt(0) == '1') 
			{
				word = "" + lastWord.charAt(0);
				if (lastWord.length() > 1) 
				{
				lastWord = lastWord.substring(1);
				}
				else {
					lastWord = "";
				}
			}
			else 
			{
				word = "" + lastWord.charAt(0);
				word += lastWord.charAt(1);
				if (lastWord.length() > 2) 
				{
				lastWord = lastWord.substring(2);
				}
				else {
					lastWord = "";
				}
			}
			pw.println("<CompOperator> ::= " + word );
			return true;
		}

		return false;
	}
	private boolean APUEXPRESS() 
	{
		return false;
	}
	private boolean APUTERM() 
	{
		return false;
	}
	private boolean APUAPUFACTOR() 
	{

		return false;
	}
	private boolean APUIDENTIFIER() 
	{

		return false;
	}
	private boolean APULETTER() 
	{

		return false;
	}
	private boolean APUINT() 
	{

		return false;
	}
	private boolean APUFLO() 
	{

		return false;
	}
	public void close() throws IOException 
	{
		br.close();
		pw.close();
		System.out.println("Done!");
	}
	private void error(String err) 
	{ 
		pw.println("***************************************************");
		pw.println("An error Is found: description Below");
		pw.println("Missing '" + err + "'");
		pw.println("*****************************************************");
		pw.println();
		pw.println();
		pw.println();
		pw.println("Syntax is Incorrect. Check error above. Total error(s) found 1");
	}
	private String getNextWord() throws IOException{
		String word = "";
		if (!scanOpen)
		{
			scan = new Scanner(br.readLine());
			scanOpen = true;
			return genareateNextWord(scan.next());
		}
		if(!scan.hasNext() || !lastWord.isEmpty())
		{
			if(!lastWord.isEmpty()) {
				return genareateNextWord(lastWord);
			}

			String word1 = br.readLine();
			if (word1.length() == 0)
				return "";
			scan.close();
			scan = new Scanner(word1);
		}
		return genareateNextWord(scan.next());


	}

	private String genareateNextWord(String word1) 
	{
		char[] carray = word1.toCharArray();
		String word= "";

		for (int i =0; i < carray.length; i ++ ) 		
		{
			switch (carray[i]) 							
			{
			case '+': 	 						
			case '-': 
			case '/':
			case '*':
				break;
			case '!':
			case '>':
			case '<':
			case '=':
				if (carray.length > 1 && i == 0) 
				{
					if ((carray[1] == '=') )
					{
						for (int j = i; j < carray.length; j++)
							lastWord += carray[j];
						return "2CompOperator";
					}
					else if(carray[0] != '=' && carray[0] != '!') {
						for (int j = i; j < carray.length; j++)
							lastWord += carray[j];
						return "1CompOperator";
					}
				}
				else if (carray[0] != '=' && 
						 carray[0] != '!' && 
						 i == 0 			) 
				{
					for (int j = i; j < carray.length; j++)
						lastWord += carray[j];
					return "1CompOperator";
				}
			case '(': 	 					
			case ')': 
			case '{':
			case '}':
			case ';':
			case ',':
				if (i == 0) {
					word += carray[0];
					i = 1;
					lastWord = "";
				}
				for (int j = i; j < carray.length; j++)
					lastWord += carray[j];
				return word;
			default: 
				word += carray[i];					
			}
		}
		return word;
	}
}

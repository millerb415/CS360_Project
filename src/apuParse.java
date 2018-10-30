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
		pw.print("<Statement> ::= ");
		boolean isvalid = true;
		String word = "";
		while (!word.equals("}") && br.ready() ) 
		{
			word = getNextWord();
			switch (word) {	
			case "Float":
			case "Function": 
			case "Integer":
				pw.println("<Declaration>");
				isvalid = APUINT();
				isEmpty = false;
				break;
			case "Print":
			case "Else":
			case "Return":
			case "Write": 
			case "If": 
				pw.println("<If statement>");
				isvalid = APUIF();
				isEmpty = false;
				break;
			case "DOWhile":
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

	private boolean APUINT() 
	{
		pw.println("<Declaration> ::= Integer <Identifier> = <Integer>;");
		return false;
	}

	private boolean APUFLOAT() 
	{

		return false;
	}


	private boolean APUFUN() 
	{

		return false;
	}


	private boolean APUIF() 
	{

		return false;
	}

	private boolean APUPRINT() 
	{

		return false;
	}


	private boolean APUELSE() 
	{

		return false;
	}


	private boolean APURETURN() 
	{

		return false;
	}

	private boolean APUWRITE() 
	{

		return false;
	}

	private boolean APUDOWHILE() 
	{

		return false;
	}

	private String nextline() 
	{
		String line = "";

		return line;
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
		if (!scanOpen){
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
		if (word1.length() == 1) 
		{
			lastWord = "";
			return word1;
		}
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
			case '>':
			case '<':
			case '=':
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

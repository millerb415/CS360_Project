import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class apuParse{
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
				word = getNextWord();
				isEmpty = false;
				break;
			case "If": 
				pw.println("<Statement> ::= <If statement>");
				isvalid = APUIF();
				word = getNextWord();
				isEmpty = false;
				break;
			case "DOWhile":
				pw.println("<Statement> ::= <DOWhile statement>");
				isvalid = APUDOWHILE();
				word = getNextWord();
				isEmpty = false;
				break;
			default:
				break;
			}
			if (!isvalid) {
				break;
			}
		}
		
		if (isEmpty)
		{
			pw.println("<Statement> ::= < Empty >");
			pw.println("< Empty > ::= Epsilon");
		}
		if (!word.equals("}") && isvalid)
		{
			error("}");
		}
		else if(word.equals("}"))
		{
			lastWord  = "}" + "" + lastWord;
		}
		return isvalid;
	}
	private boolean APUIF() throws IOException 
	{	
		boolean ifOnly = false;
		boolean isValid = false;
		pw.println("<if Statement > ::= If( < Condition > ) { < Statement > }");
		if( getNextWord().equals("(")) 
		{
			if(APUCONDI())
			{
				if(getNextWord().equals(")"))
				{
					if (getNextWord().equals("{")) 
					{
						if (APUSTATE()) {
							if(getNextWord().equals("}")) 
							{
								
								String word = getNextWord();
								if (word.equals("Else")) 
								{
									if (getNextWord().equals("{")) 
									{
										if (APUSTATE()) 
										{
											if(getNextWord().equals("}")) 
											{
												isValid= true;
											}
											else 
											{
												error("}");	
											}
										}
									}
									else
									{
										error("{");
									}
								}
								else
								{
									lastWord = word + "" + lastWord;
									isValid = true;
								}
							}
							else 
							{
								error("}");	
							}
						}
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
		}
		else
		{
			error("(");
		}
		
		return isValid;
	}
	private boolean APUDOWHILE() throws IOException
	{
		boolean isValid = false;
		if (getNextWord().equals("("))
		{
			if (APUCONDI())
			{
				if (getNextWord().equals(")"))
				{
					if (getNextWord().equals("{"))
					{
						if (APUSTATE())
						{
							if (getNextWord().equals("}"))
							{
								isValid = true;
							}
							else
							{
								error("}");
							}
						}
					}
					else
					{
						error("}");
					}
				}
				else 
				{
					error(")");
				}
			}
		}
		else 
		{
			error("(");
		}
		return isValid;
	}
	private boolean APUDECL(String type) throws IOException 
	{ 
		if (type.equals("Integer")) {
			return APUINTEGER();
		}
		return APUFLOAT();
	}
	private boolean APUINTEGER() throws IOException 
	{  
		pw.println("<Declaration> ::= Integer <Identifier> = <Integer>;");
		boolean isValid = false;
		if (APUIDENTIFIER(false)) 
		{
			if(getNextWord().equals("=")) 
			{
				if(APUINT(false)) 
				{
					if(getNextWord().equals(";"))
					{
						isValid = true;
					}
					else
					{
						error(";");
					}
				}
			}
			else 
			{
				error("=");
			}

		}
		else
		{
			error("identifier");
		}
		return isValid;
	}
	private boolean APUFLOAT() throws IOException 
	{
		pw.println("<Declaration> ::= Float <Identifier> = <Float>;");
		boolean isValid = false;
		if (APUIDENTIFIER(false)) 
		{
			if(getNextWord().equals("=")) 
			{
				if(APUFLO(false)) 
				{
					if(getNextWord().equals(";"))
					{
						isValid = true;
					}
					else
					{
						error(";");
					}
				}
			}
			else {
				error("=");
			}
		}

		return isValid;
	}
	private boolean APUASSIGN() throws IOException
	{
		pw.println("<Condition> ::= <Identifier> <CompOperator> <Identifier>");
		boolean isValid = false;
		if (APUIDENTIFIER(false))
		{

			if (getNextWord().equals("="))
			{
				if (APUEXPRESS())
				{
					if (getNextWord().equals(";"))
					{
						isValid = true;
					}
					else 
					{
						error(";");
					}
				}
			}
			else 
			{
				error("=");
			}
		}
		return isValid;
	}
	private boolean APUCONDI() throws IOException
	{
		pw.println("<Condition> ::= <Identifier> <CompOperator> <Identifier>");
		boolean isValid = false;
		if (APUIDENTIFIER(false))
		{
			if (APUCOMPOP())
			{
				if (APUIDENTIFIER(false)) 
				{
					isValid = true;
				}
			}
		}
		return isValid;
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
	private boolean APUEXPRESS() throws IOException
	{
		boolean isValid = false;

		if (APUTERM()) 
		{
			isValid = true;
		}
		else 
		{

		}
		if (APUEXPRESS())
		{
			if (getNextWord().equals("+"))
			{
				if (APUTERM())
				{
					isValid = true;
				}
			}
			else if (getNextWord().equals("-"))
			{
				if (APUTERM())
				{
					isValid = true;
				}
			}
			else 
			{
				error("+");
			}
		}

		return isValid;
	}
	private boolean APUTERM() throws IOException
	{ 
		String word = "";
		pw.println("<Term> ::= <Term> * <Factor> | <Term> /<Factor> | <Factor>");
		boolean isValid = false;
		if (APUFACTOR(true)) 
		{
			isValid = true;
		}
		else if (APUTERM())
		{
			word = getNextWord();
			if (word.equals("*"))
			{
				if (APUFACTOR(false))
				{
					isValid = true;
				}
			}
			else if (word.equals("/"))
			{
				if (APUFACTOR(false))
				{
					pw.println("<Identifier> ::=<Letter>");
					isValid = true;
				}
			}
			else 
			{
				error("* or / ");
			}
		}

		return isValid;
	}
	private boolean APUFACTOR(boolean option) throws IOException
	{
		
		boolean isValid = false;
		if (APUIDENTIFIER(true)) 
		{
			pw.println("<Factor> ::= <identifier> | <Integer> | <Float>");
			isValid= true;
		}
		else if (APUINT(true)) 
		{
			pw.println("<Factor> ::= <identifier> | <Integer> | <Float>");
			isValid= true;
		}
		else if (APUFLO(true)) 
		{
			pw.println("<Factor> ::= <identifier> | <Integer> | <Float>");
			isValid = true;
		}
		else if(!option)
		{
		 error("Factor");
		}

		return isValid;
	}
	private boolean APUIDENTIFIER(boolean option) throws IOException
	{
		pw.println("<Identifier> ::=<Letter>");
		boolean isValid = false;
		if (APULETTER(option)) 
		{
			
			isValid = true;
		}
		return isValid;
	}
	private boolean APULETTER(boolean option) throws IOException
	{  
		String word = getNextWord();
		boolean isValid = false;
		if (word.matches("[a-zA-Z]+"))
		{
			pw.println("<Letter> ::= a|...|Z");
			isValid = true;
		}
		else if (!option)
		{
			
		}
		else 
		{
			
			lastWord = word + " " + lastWord;
		}
		return isValid;
	}
	private boolean APUINT(boolean option) throws IOException 
	{
		String word = getNextWord();
		boolean isValid = false;
		if (word.matches("[0-9]+"))
		{
			pw.println("<Integer> ::= 0|...|9");
			isValid = true;
		}
		else if (!option)
		{
			error("integer value");
		}
		else 
		{
			lastWord = word + " " + lastWord;
		}
		return isValid;
	}
	private boolean APUFLO(boolean option) throws IOException
	{
		String word = getNextWord();
		boolean isValid = false;
		if (word.matches("[-+]?[0-9]*\\.?[0-9]+"))
		{
			pw.println("<Float> ::= .|0|...|9");
			isValid = true;
		}
		else if (!option)
		{
			error("Float value");
		}
		else 
		{
			lastWord = word + " " + lastWord;
		}
		return isValid;
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
			if (word1 == null)
				word1 = "";
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
			case '!':
			case '>':
			case '<':
			case '=':
				if (carray.length > 1 && i == 0) 
				{
					if ((carray[1] == '=') )
					{
						lastWord = "";
						for (int j = i; j < carray.length; j++)
							lastWord += carray[j];
						return "2CompOperator";
					}
					else if(carray[0] != '=' && carray[0] != '!') {
						lastWord = "";
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
			case '+': 	 						
			case '-': 
			case '/':
			case '*':
				if (i == 0) {
					word += carray[0];
					i = 1;
					;
				}
				lastWord = "";
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

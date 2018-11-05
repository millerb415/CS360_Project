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
	private boolean APUIF() throws IOException 
	{
		boolean isValid = false;
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
										if (APUSTATE()) {
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
									lastWord = word + " " + lastWord;
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
		if (APUIDENTIFIER()) 
		{
			if(getNextWord().equals("=")) 
			{
				if(APUINT()) 
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
		return isValid;
	}
	private boolean APUFLOAT() throws IOException 
	{
		pw.println("<Declaration> ::= Float <Identifier> = <Float>;");
		boolean isValid = false;
		if (APUIDENTIFIER()) 
		{
			if(getNextWord().equals("=")) 
			{
				if(APUFLO()) 
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
		boolean isValid = false;
		if (APUIDENTIFIER())
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
		boolean isValid = false;
		if (APUEXPRESS())
		{
			if (APUCOMPOP())
			{
				if (APUEXPRESS()) 
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
		else if (APUTERM()) 
		{
			isValid = true;
		}
		
		return isValid;
	}
	private boolean APUTERM() throws IOException
	{
		boolean isValid = false;
		if (APUTERM())
		{
			if (getNextWord().equals("*"))
			{
				if (APUFACTOR())
				{
					isValid = true;
				}
			}
			else if (getNextWord().equals("/"))
			{
				if (APUFACTOR())
				{
					isValid = true;
				}
			}
			else 
			{
				error("*");
			}
		}
		else if (APUFACTOR()) 
		{
			isValid = true;
		}
		
		return isValid;
	}
	private boolean APUFACTOR() throws IOException
	{
		boolean isValid = false;
		if (APUIDENTIFIER()) 
		{
			if (APUINT()) 
			{
				if (APUFLO()) 
				{
					isValid = true;
				}
			}
		}
		return isValid;
	}
	private boolean APUIDENTIFIER() throws IOException
	{
		boolean isValid = false;
		if (APULETTER()) 
		{
			isValid = true;
		}
		return isValid;
	}
	private boolean APULETTER() throws IOException
	{
		boolean isValid = false;
		if (getNextWord().matches("[a-zA-Z]+"))
		{
			isValid = true;
		}
		return isValid;
	}
	private boolean APUINT() throws IOException 
	{
		boolean isValid = false;
		if (getNextWord().matches("[0-9]+"))
		{
			isValid = true;
		}
		return isValid;
	}
	private boolean APUFLO() throws IOException
	{
		boolean isValid = false;
		if (getNextWord().matches("[-+]?[0-9]*\\.?[0-9]+"))
		{
			isValid = true;
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

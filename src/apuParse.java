import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class apuParse{
	private String readin;
	private String readout;
	private BufferedReader br;
	private FileReader fr;
	private PrintWriter pw;
	
	private FileWriter fw;
	private boolean ifRead;
	public void
	apuParse(String in, String out) {
		readin = in;
		readout = out;		
	}

	public void read() {
		BufferedReader br;
		FileReader fr;
		PrintWriter pw;
		String line;
		FileWriter fw;
		try 												// code for reading in files
		{
            fr = new FileReader(readin);
            fw = new FileWriter(readout);
            br = new BufferedReader(fr);
            pw = new PrintWriter(fw, true);
            pw.print("Group Name(3) – Members(Micah Edington, Ryan Alcantra, Brandon Miller)\n");
            pw.printf("%10s%15s\n", "Lexeme", "Token");
            pw.println("    ------          -----");
            ifRead = true;
		}
		catch (Exception e) 
		{
		
			System.err.println(e);
		}
	}
	
	
	private void APUMAIN() {
		// TODO Auto-generated method stub
		
	}

	
	private void APUINT() {
		// TODO Auto-generated method stub
		
	}

	private void APUFLOAT() {
		// TODO Auto-generated method stub
		
	}

	
	private void APUFUN() {
		// TODO Auto-generated method stub
		
	}

	
	private void APUIF() {
		// TODO Auto-generated method stub
		
	}

	private void APUPRINT() {
		// TODO Auto-generated method stub
		
	}

	
	private void APUELSE() {
		// TODO Auto-generated method stub
		
	}


	private void APURETURN() {
		// TODO Auto-generated method stub
		
	}

	private void APUWRITE() {
		// TODO Auto-generated method stub
		
	}

	private void APUDOWHILE() {
		// TODO Auto-generated method stub
		
	}
	private String nextline() {
	 String line = "";
		
		return line;
	}
	public void close() throws IOException {
		br.close();
        pw.close();
        System.out.println("Done!");
	}

}

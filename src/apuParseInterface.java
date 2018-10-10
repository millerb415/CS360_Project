import java.io.BufferedReader;
import java.io.PrintWriter;


/**
 * @author Brandon Miller
 */
 
public interface apuParseInterface {

/**
 * checks to see if main() has a format of ::= void main() { statement }
 * 
 * 
 */
	void APUMAIN();
	
	/**
	 * 
	 */
	void APUINT();
	
	/**
	 * 
	 */
	void APUFLOAT();
	
	
	/**
	 * 
	 */
	void APUFUN();
	
	/**
	 * 
	 */
	void APUIF();

	/**
	 * 
	 */
	void APUPRINT();
	
	/**
	 * 
	 */
	void APUELSE();
	
	/**
	 * 
	 */
	void APURETURN();
	/**
	 * 
	 */
	void APUWRITE();
	/**
	 * 
	 */
	void APUDOWHILE();
}

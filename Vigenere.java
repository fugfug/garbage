import java.util.Scanner;

public class Vigenere {
	// https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher#Algebraic_description
	// i cheated. i used the algebraic description to make this.
	// used this to check: https://www.dcode.fr/vigenere-cipher
	
	// **************************************************************************
	// NOTE: 
	// encrypted results will neither reflect whitespaces nor punctuations of the original message.
	// decrypted results are similar to encrypted result, in that it will not return the EXACT original message;
	// 		which is to say it will not reflect whitespaces or punctuations.
	// 
	// **************************************************************************
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		// String decrypt = scan.nextLine().replaceAll("\\W", "").toUpperCase();
		
		// uncomment this and comment out entire do-while chunk for easy check
	/*	
		String message = "ATTACKATDAWN".replaceAll("\\W", "");//.toLowerCase();
		String keyIn =   "LEMON";//.toLowerCase();
		String decrypt = "LXFOPVEFRNHR";
		
		String key = key(keyIn, message.length());
		String encrypted = encrypt(message, key);
		System.out.println("key: "+key);
		System.out.println("encrypted mesage: "+encrypted);
		System.out.println("original message: "+decrypt(encrypted, key(keyIn, encrypted.length())));
	*/	
		
		

		
		String cont = "y";
		do {
			//while(cont.equalsIgnoreCase("y") || cont.equalsIgnoreCase("yes")) {
				System.out.println("Encrypt or Decrypt? Enter 'e' or 'd': ");
				String what = scan.nextLine().replaceAll("\\W", "");
				if(what.equalsIgnoreCase("e") || what.equalsIgnoreCase("encrypt")) {
					System.out.println("Enter message: ");
					String message = scan.nextLine().replaceAll("[^a-zA-Z]", "").toUpperCase();
					System.out.println("Enter key: ");
					String keyIn = scan.nextLine().toUpperCase();
					// System.out.println("Encrypted message: "+encrypt(message, key(keyIn, message.length())));
					System.out.println("Encrypted message: "+process(message, keyIn, what));
					
					System.out.println("\nContinue? Enter 'y' or 'yes' to continue: ");
					cont = scan.nextLine();
					System.out.println();
					
				} else if(what.equalsIgnoreCase("d")|| what.equalsIgnoreCase("decrypt")){
					System.out.println("Enter encrypted message: ");
					String decrypt = scan.nextLine().replaceAll("[^a-zA-Z]", "").toUpperCase();
					System.out.println("Enter key: ");
					String keyIn = scan.nextLine().toUpperCase();
					// System.out.println("Original message: "+decrypt(decrypt, key(keyIn, decrypt.length())));
					System.out.println("Original message: "+process(decrypt, keyIn, what));
					
					System.out.println("\nContinue? Enter 'y' or 'yes' to continue: ");
					cont = scan.nextLine();
					System.out.println();
				} // anything but d, decrypt, e, or encrypt will end program
				
			} while(cont.equalsIgnoreCase("y") || cont.equalsIgnoreCase("yes"));
	}
	
	// only created this method as a check against non alphabetic chars in key
	public static String process(String s, String k, String w) {
		for (int i = 0; i < k.length(); i++) {
			if((k.charAt(i)+"").matches("[^a-zA-Z]") ){ // not a-z A-Z
				return "INVALID";
			} 
		}
		// i know i repeated code and tHaTs BaD, but i cant think of another way, 
		// and im getting tired of this thing already
		if(w.equalsIgnoreCase("e") || w.equalsIgnoreCase("encrypt"))
			return (encrypt(s, key(k, s.length())));
		else if(w.equalsIgnoreCase("d") || w.equalsIgnoreCase("decrypt"))
			return (decrypt(s, key(k, s.length())));
		return "";
		
	}
	
	// (encrypted-key)+26 % 26
	private static String decrypt(String d, String k) {
		String dec = "";
		for(int i = 0; i < d.length(); i++) {
			dec += (char) ( ( ((d.charAt(i)-k.charAt(i))+26) % 26 ) +65 );
		}
		return dec;
	}
	
	private static String key(String k, int mLength) {
		String key = "";
		if(k.length() < mLength) { 
			while(key.length() < mLength) {
				for(int i = 0; i < k.length(); i++)
					key+=k.charAt(i);
			}
			key = key.substring(0, mLength);
		} else if(k.length() > mLength) { 
			key = k.substring(0, mLength);
		} else // ==
			key = k;
		
		return key;
	}
	

	private static String encrypt(String m, String k) {
		// (message + key) % 26
		
		String enc = "";
		for (int i = 0; i < m.length(); i++) {
			enc += (char) ((((m.charAt(i) - 65) + (k.charAt(i) - 65)) % 26) + 65);
		}
		return enc;
	}
	
	

}

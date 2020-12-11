import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
// https://en.wikipedia.org/wiki/ADFGVX_cipher



public class ADFGVX {
	
	static String ADFGVX = " ADFGVX";
	static String str = "";
	
	public static void main(String[] args) {

		// for the full experience, use this method.
		theFullExperience(); // comment this out and uncomment the above chunk for quick test
		
/*		
		// for quick test, uncomment this chunk and comment out theFullExperience()
		// to encrypt
		String encrypt = "l3m0n tr33 v3ry pr477y".toUpperCase();
		String encrypt2 = "the quick brown 420 fox jumped over the lazy 666 dog".toUpperCase();
		System.out.println(encrypt.length());
		String boxLetters = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789".toUpperCase();
		System.out.println(encrypt(fill("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"), encrypt));
		//System.out.println(encrypt(fillRandom(), encrypt));
		decrypt(fill("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"), "DXVXFAVFFDGDFXVXVXGGVXFXVAFGFXXAXGXGVA");
		
		
		// to decrypt
		decrypt(fill("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"), "DX VX FA VF FD GD FX VX VX GG VX FX VA FG FX XA XG XG VA ".replaceAll(" ", ""));
		decrypt(fill("9KYWUNS17MZ4H8EGC0LAIXJ2V5RTPQBF3D6O"), "VGFAFFVXAVGFFVADXAVFXXAGAXXDXXGGGVAVDGVVFFXGXXVAFFVFVGFAFFGAGDDVAFXGXXFG");
*/		
		

	}
	
	public static String theFullExperience() {	// string to force the program to end when necessary. see below
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		boolean keepGoing = true;
		do {
		//while(keepGoing) {
			System.out.println("Encrypt or decrypt? \n(Enter 'e' to encrypt, 'd' to decrypt, and 'q' to end program): ");
			String choose = scan.nextLine();
			
			boolean isInvalid = true;
			while(isInvalid) {
			
				if(choose.equalsIgnoreCase("e") || choose.equalsIgnoreCase("encrypt")) {
					System.out.println("Enter message to encrypt: ");
					String message = scan.nextLine().toUpperCase().replaceAll("\\W", "");
					System.out.println("Generate cipher letters or enter your own?"
							+ "\nEnter 'g' to generate or enter 'own' or 'o' to enter your own: ");
					
					String generate = scan.nextLine();
					boolean isInvalidOption = true;
					while(isInvalidOption) {
						if(generate.equalsIgnoreCase("g")) {
							System.out.println(encrypt(fillRandom(), message));
							isInvalidOption = false;
						} else if(generate.equalsIgnoreCase("o") || generate.equalsIgnoreCase("own")){ // user enters own
							System.out.println("Enter your cipher letters: ");
							
							String boxLetters = scan.nextLine().toUpperCase().replaceAll("\\W", "");
							// cipher letter same thing as box letters
							
							// isInvalidCipher checks for duplicates and length
							while(isInvalidCipher(boxLetters)) { // is a duplicate or not 36 chars
								System.out.println("either dupes present or not 36 letters. enter cipher letters again: ");
								boxLetters = scan.nextLine().toUpperCase().replaceAll("\\W", "");
								isInvalidCipher(boxLetters);
							}
							
							System.out.println(encrypt(fill(boxLetters), message));
							isInvalidOption = false;
						} else {
							System.out.print("Not an option. Enter 'g' or 'o': ");
							generate = scan.nextLine();
						}
					}
					isInvalid = false;
					
					
				} else if(choose.equalsIgnoreCase("d") || choose.equalsIgnoreCase("decrypt")){ // decrypt
					System.out.print("Enter message to decrypt: ");
					String message = scan.nextLine().toUpperCase().replaceAll("\\W", "");
					System.out.print("\nGenerated cipher letters for decryption will most likely result in nonsense."
							+ "\nPlease enter your own cipher letters: ");
					String boxLetters = scan.nextLine().toUpperCase().replaceAll("\\W", "");
					decrypt(fill(boxLetters), message);
					isInvalid = false;
					
				} else if(choose.equalsIgnoreCase("q") || choose.equalsIgnoreCase("quit")|| choose.equalsIgnoreCase("end")) {
					
					isInvalid = false;
					keepGoing = false;
					System.out.println("bye");
					return ""; // force the method to end right here
				} else {
					System.out.println("Not Valid. Enter again: ");
					choose = scan.nextLine();
				}
				
			}
			
			System.out.println("\n\nWould you like to keep going?: ");
			String cont = scan.nextLine();
			System.out.println();
			isInvalid = true;
			while(isInvalid) {
				if (cont.equalsIgnoreCase("y")) { // keepgoing
					keepGoing = true;
					isInvalid = false;
					System.out.println();
				} else if (cont.equalsIgnoreCase("n")) {
					keepGoing = false;
					isInvalid = false;
					System.out.println("bye");
				} else {
					System.out.println("Not Valid. Enter again: ");
					cont = scan.nextLine();
				}
			}
			
			
		} while(keepGoing); // keepGoing = true
		return "";
		
	}
	
	
	
	// decrypt() is used in conjunction with fill(), NOT fillRandom()
	public static void decrypt(char[][] c, String m) {
		
		//displays box[][]
		for (char[] i : c) {
			for (char j : i) 
				System.out.print(j + " ");
			System.out.println();
		}
		
		String decrypted = "";
		int[] res = new int[m.length()];
		int j = 0;
		while(j < m.length()) {
			for(int i = 0; i < c.length; i++) { 
				if(m.charAt(j) == c[i][0]) {
					res[j] = i;
				}
			}
			j++;
		}
		for(int i = 1; i < m.length(); i+=2) {
			// System.out.print(c[res[i-1]][res[i]]); 
			decrypted += c[res[i-1]][res[i]];
		}
		System.out.println("Original message: " + decrypted);
	}
	
	
	
	// y, x = i, j
	public static String encrypt(char[][] c, String s) {
		String r = "";
		// what a mess
		for(int k = 0; k < s.length(); k++) {
			for(int i = 0; i < c.length; i++) {	
				for (int j = 0; j < c.length; j++) {
					if(c[i][j] == s.charAt(k) ) { 
						if(i != 0 && j != 0) { // ignore c[0][i]'s and c[j][0]'s
							// System.out.println(i + ", " + j); // for test
							// System.out.println(c[0][i] +"" + c[j][0]); // test
							// r+=c[0][i] +"" + c[j][0]; // test
							 r+=encryptIndex(j, i) + "";
						} 
					}
				}
			}
		}
		
		//displays box[][]
		for (char[] i : c) {
			for (char j : i) 
				System.out.print(j + " ");
			System.out.println();
		}
		
		// displays box[][] letters as a string
		str = ""; // empty str
		for (int i = 1; i < c.length; i++) { // 1 to skip ADFGVX
			for (int j = 1; j < c.length; j++) { // 1 to skip ADFGVX
				str += c[i][j];
			}
		}
		
		// copy&paste somewhere for future decrypting
		System.out.println("Box letters: "+str);
		
		
		return r;
	}
	
	// had to separate from encrypt b/c encrypt would get too long, confusing, and convoluted.
	// im already confused
	public static String encryptIndex(int y, int x) {
		String r = "";
		char[][] box2 = new char[7][7];
		
  		for (int i = 0; i < box2.length; i++) {
			for (int j = 0; j < box2.length; j++)
				box2[0][j] = ADFGVX.charAt(j);
			box2[i][0] = ADFGVX.charAt(i);
		}
  		r+=box2[x][0] +"" + box2[0][y];  		
  		return r;
	}
	
	// using fillRandom() to decrypt will most likely result in nonsense, ie something like this:
	// " MSK2JOTL60YCFGY54JRXKIYHK0MSKVQ89IY3 "
	// requires memorization or copy&pasting of letter box to decrypt message
	public static char[][] fillRandom() {
		String s = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789"; 
		Random rand = new Random();
		ArrayList<Integer> aList = new ArrayList<>();
		char[][] box = new char[7][7];
		
		for (int i = 1; i < box.length; i++) {
			for (int j = 1; j < box.length; j++) {
				int r = rand.nextInt(s.length());
				while (aList.contains(r)) {
					r = rand.nextInt(s.length());
				}
				box[i][j] = s.charAt(r);
				aList.add(r);
			}
		}
		
		// add ADFGVX to box 
  		for (int i = 0; i < box.length; i++) {
			for (int j = 0; j < box.length; j++)
				box[0][j] = ADFGVX.charAt(j);
			box[i][0] = ADFGVX.charAt(i);
		}
		
		return box;
	}
	
	// probs couldve combined fill() & fillRandom(), but w/e
	// fill() should really only be used when decrypting or for a specific order
	public static char[][] fill(String s) { // doesn't fill box randomly
		int k = 0;
		char[][] box = new char[7][7];
		for (int i = 1; i < box.length; i++) {
			for (int j = 1; j < box.length; j++) {
				box[i][j] = s.charAt(k);
				k++; // took way too long to figure out
			}
		}
		
		// add ADFGVX to box
  		for (int i = 0; i < box.length; i++) {
			for (int j = 0; j < box.length; j++)
				box[0][j] = ADFGVX.charAt(j); // x
			box[i][0] = ADFGVX.charAt(i); // y
		}
  		
		return box;
	}
	
	private static boolean isInvalidCipher(String s) {
		//str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // for testing
		
		//https://javarevisited.blogspot.com/2015/06/3-ways-to-find-duplicate-elements-in-array-java.html
		if (s.length() == 36) { // valid
			for(int i = 0; i < s.length(); i++) {
				for(int j = i+1; j < s.length(); j++) {	
					if(s.charAt(i) == s.charAt(j)) { // not valid
						return true;
					}
				}
			}
		} else // s.length != 36
			return true; // not valid
		
		return false;
	}
	
}


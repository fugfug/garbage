import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

// https://en.wikipedia.org/wiki/Alberti_cipher_disk
// https://en.wikipedia.org/wiki/Alberti_cipher
// ABCDEFGILMNOPQRSTVXZ1234 

// g!lnprtujkvw?*z&xysomqihfdbace

// this was harder than i though it would be. so long and convoluted 
// i got lost multiple times trying to get things to work why did i even do this

public class Alberti {
	static Map<Character, Character> map ;
	static Random rand = new Random();
	final static String DEFAULT_MOVABLE_DISK = "g!lnprtujkvw?*z&xysomqihfdbace";
	final static String STATIONARY_DISK = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234";
	final static char[] outer = STATIONARY_DISK.toCharArray();
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String movable_disk = DEFAULT_MOVABLE_DISK;


/**/
		System.out.print("default inner (movable) disk: "+DEFAULT_MOVABLE_DISK+
				"\nuse default as inner disk (enter d or D), "+
				"\nauto generate a new inner disk (g/G), "
				+"\nor enter your own (o/O)?");
		String useDefault = scan.nextLine();
		if(useDefault.equalsIgnoreCase("o") || useDefault.equalsIgnoreCase("own")) { // own
			System.out.print("enter movable disk (.,!@#$%^&*+- ok): ");
			movable_disk = scan.nextLine();
			while(isDuplicate(movable_disk) || movable_disk.length() != STATIONARY_DISK.length() ) { // duplicates present
				System.out.println("duplicate(s) present or not long enough (30 chars). please re enter: ");
				movable_disk = scan.nextLine();
			}
		} else if(useDefault.equalsIgnoreCase("g") || useDefault.equalsIgnoreCase("generate")) { // generate
			char[] tt = generateInner(movable_disk.toCharArray());
			movable_disk = "";
			for(int i=0; i < tt.length; i++)
				movable_disk += tt[i];
		}
  		System.out.println("\nstationary disk: "+STATIONARY_DISK+"\nmovable disk:    " + movable_disk); // default
		
		String keepGoing = "y";
		do {
			System.out.print("\nEncrypt or Decrypt? (e / d): ");
			String encryptOrDecrypt = scan.nextLine();
			if(encryptOrDecrypt.equalsIgnoreCase("e") || encryptOrDecrypt.equalsIgnoreCase("encrypt")) {
				System.out.print("Enter message: ");
				String encryptThis = scan.nextLine().toUpperCase().replaceAll("\\s", "");
				char[] inner = movable_disk.toCharArray();
				fillMap(inner);
				System.out.println(encrypt(outer, inner, encryptThis) );
				
			} else { // decrypt
				System.out.print("Enter encrypted message"+"\n(NOTE: Decoded result will not display the "+
						"original message's whitespaces or punctuations.): ");
				String decryptThis = scan.nextLine();
				char[] inner = movable_disk.toCharArray();
				decrypt(inner, movable_disk, decryptThis.replaceAll("\\s", ""));
			}
			System.out.print("\nKeep going? (y/n): "); // ackshually anything but y/yes will end program.
			keepGoing = scan.nextLine();
		} while(keepGoing.equalsIgnoreCase("y") || keepGoing.equalsIgnoreCase("yes"));
		
		/////////////////////////////
		
		/*		// for quick testing
		String encryptThis = "lemon tree very pretty and the.. lemon flower sweet".toUpperCase().replaceAll("\\s", "").replaceAll("\\W", "");
		String encryptThis2 = "im so proud of my bobby baby teeth".toUpperCase().replaceAll("\\W", "");
		String decryptThis = "GwVzifheazz!zapdazeepvh*eF!crctjuerjxTvQjk*dTvd"; 
		
		
		char[] inner = movable_disk.toCharArray(); // for short testing of fillmap & encrypt vv vv
		fillMap(inner); // out, inner -> inner
		
		String e = encrypt(outer, inner, encryptThis); 
		System.out.println(e+"\n"); // test
		System.out.println("in: " + inner.length + "\nd:  " + decryptThis.length());
		
		char[] fff = shiftInnerDisk(inner, 'c');
		for(char ch : fff) System.out.print(ch); // check
		 
		decrypt(inner, movable_disk, decryptThis); // for testing
		decrypt(inner, movable_disk, e.replaceAll("\\s", "")); // for testing - ("\\s", "") - remove whitespaces 
		// https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
		
		char[] tt = generateInner(inner);
		for(char c:tt) System.out.print(c);
		System.out.println("\n"+tt.length); // test
		*/	
		
	}
	
	
	public static char[] generateInner(char[] in) {
		ArrayList<Character> inList = new ArrayList<>();
		for(char c : in)
			inList.add(c);
		Collections.shuffle(inList);
		
		// this doesn't allow non alphabetic chars to be the index
		while(Pattern.matches("\\W", inList.get(0)+"")) { // ! ? . *
			Collections.shuffle(inList);
		}
		
		char[] temp = new char[in.length];
		for(int i = 0; i < inList.size(); i++) {
			temp[i] = inList.get(i);
		}
		return temp;
	}
	
	public static void decrypt(char[] in, String movable, String d) {
		String decrypted = "";
		ArrayList<Integer> shifted = new ArrayList<>();
		ArrayList<String> encryptedSplit = new ArrayList<>();
		for(int i = 0; i < d.length(); i++) {
			if(Pattern.matches("[A-Z]", d.charAt(i)+"")) {
				shifted.add(i);
			}
			else if (Pattern.matches("\\d", d.charAt(i)+"")) {
				shifted.add(i+1);
			}
		} 
		// System.out.println(shifted+"\n" + "\n" + d); // check
		
		for(int i = 1; i < shifted.size(); i++) {
			encryptedSplit.add(d.substring(shifted.get(i-1), shifted.get(i)));
		} 
		encryptedSplit.add(d.substring(shifted.get(shifted.size()-1)));
		encryptedSplit.add(" "); // had to add this to get the last substring (the substring above)
		// System.out.println(encryptedSplit); // check
/**/	
		for(int j = 0; j < encryptedSplit.size()-1; j++) { // idk why -1, it just works. idc anymore
			char c = encryptedSplit.get(j).toLowerCase().charAt(0); 
			// decrypted += " ";	// for testing
			for(int k = 0; k < encryptedSplit.get(j).length()-1; k++) { // same comment as above
				char[] tempIn = shiftInnerDisk(in, c);	
				for(int z= 0; z < tempIn.length; z++) {
					if(encryptedSplit.get(j).toLowerCase().charAt(k+1) == tempIn[z]) // k+1 to skip the index
						decrypted += outer[z];
					// https://stackoverflow.com/questions/18581531/in-java-how-can-i-determine-if-a-char-array-contains-a-particular-character
					
				}
			}
		}
		System.out.println(decrypted);
	}
	
	//for k < shiftfreq
//	 	generate new index / shifted -> shiftInnerDisk(shifted)
//	public static void encrypt(char[] out, char[] in, String input) {
	public static String encrypt(char[] out, char[] in, String input) {
		String output = (in[0]+"").toUpperCase();
	//	System.out.println("input length: " + input.length() ); // test
	//	System.out.println("inner ring length: " + in.length+" \touter length: "+out.length); // test
		ArrayList<Integer> shifts = innerDiskShifts(input);
		
		for( int k=0; k < shifts.size(); k++) {
			for(int j = 0; j < input.length(); j++) {	
				if(j == shifts.get(k)) {
					char[] shiftedInner = shiftInnerDisk(in, rand.nextInt(in.length));
					fillMap(shiftedInner); 
					
					if(Pattern.matches("\\w", shiftedInner[0]+"")) // https://www.javatpoint.com/java-regex
						output += (shiftedInner[0]+"").toUpperCase(); 
					
					output += map.get(input.charAt(j)); 
				} else {
					output += map.get(input.charAt(j));
				}
				
			}
			output += " ";
		}
		// System.out.println("output: \n"+output +"\noutput length: "+output.length()); // test
		
		// such a  convoluted way of doing things, but i cant get pattern.matches to work so whatever
		String ex = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM"; 
		int[] shiftsExtended = new int[shifts.size()]; 
		int k = 0;
		while( k < shiftsExtended.length) { 
			for(int i = 1; i < output.length(); i++) {
				for(int j = 0; j < ex.length();j++) {
					if(output.charAt(i) == ex.charAt(j)) {
						shiftsExtended[k] = i;
						k++;
					}
				}
			}
		}
		
		/*System.out.print("\nshiftsExtended: ");
		for(int i : shiftsExtended) System.out.print(i + ", ");*/ // testing
		
		String output2 = "";
		if(shifts.size()>1) {
			output2 = output.substring(0, shifts.get(1) +1) +""; //why +1? idfk but it works
				for(int i = 2; i < shifts.size(); i++) {
					output2 += output.substring( shiftsExtended[i-1]-1, ( (shiftsExtended[i-1]+1)+(shifts.get(i)-shifts.get(i-1))-1 ) );
				}
			output2 += output.substring(shiftsExtended[shiftsExtended.length-1]-1);
		} else output2 += output;
		// System.out.println("\n"+finalOutput); // test
	/**/	

		// just to make encrypted result look more... realistic??? idk. ok to comment out
		int punctuations = 0;
		// https://stackoverflow.com/questions/39559218/add-a-char-into-a-string-at-position-x/39559259
		StringBuilder finalOutput = new StringBuilder(output2);
		for(int f = 0; f < output2.length(); f++) {
			//if(Pattern.matches("[/?/./!]", finalOutput.charAt(f)+"")){
			if(output2.charAt(f)=='.' || output2.charAt(f)=='?' || output2.charAt(f)=='!') {
				punctuations++;
				finalOutput.insert(f+punctuations, ' '); // heilige scheisse i was just guessing
			}
		}
		return finalOutput.toString();
		// System.out.println("\n"+finalOutput+"\n"+finalOutput.length()); // test
		
	//	return finalOutput;
	}
	
	
	public static ArrayList<Integer> innerDiskShifts(String input) {
		int shift = 0;
		int shiftFrequency = 0;
		if(input.length() > 10) {
			shiftFrequency = rand.nextInt((input.length()/8))+1; // limit amt of shifts allowed
		}
		
		ArrayList<Integer> listOfShifts = new ArrayList<>();
        for(int i=0; i<shiftFrequency ;i++){
            shift = rand.nextInt((input.length()-2))+1; // dont shift at the end
            while(listOfShifts.contains(shift)) {
            	shift = rand.nextInt((input.length()-1))+1;
            }
            listOfShifts.add(shift);
        }
        Collections.sort(listOfShifts);
        // System.out.println("shifts: "+listOfShifts); // test
        // System.out.println(shiftFrequency); //test
        // https://stackoverflow.com/questions/31655439/how-to-generate-20-unique-random-numbers-with-order-using-java
        return listOfShifts;
	}
	
	// changed out -> outer b/c outer is declared outside main now. maybe change it back?
	public static Map<Character, Character> fillMap(char[] in) { //char[] out, char[] in -> char[] in
		map = new HashMap<Character, Character>();
		for(int i = 0; i < outer.length; i++) { // out -> outer
			map.put(Character.valueOf(outer[i]), Character.valueOf(in[i])); // out->outer
		}
		return map;
	}
	
	//public static String shiftInnerDisk(char[] out, char[] in, int shift) {
	public static char[] shiftInnerDisk(char[] in, int shift) {
		String shiftedInner= "";
		
		if(shift < in.length) {
			
			for(int j = shift; j < in.length; j++) {
				shiftedInner += in[j];				
			}
			for(int i = 0; i < shift; i++) {
				shiftedInner += in[i];
			}
			
			
		}
		
		// don't allow non alphabetic chars be the index
		if(Pattern.matches("\\W", shiftedInner.charAt(0)+"")) { // !, *, &, etc 
			return in; // this will allow indices to be repeated and thus a bit easier to decode
		}
		
		return shiftedInner.toCharArray();
	}
	
	
/*	*/ 
	// only used in decrypt
	public static char[] shiftInnerDisk(char[] in, char newIndex) {
		String shiftedInner= "";
		int shift = 0;
		
		for(int i = 0; i < in.length; i++) {
			if(newIndex == in[i]) {
				shift = i;
				if(shift < in.length) {
					for(int j = shift; j < in.length; j++) {
						shiftedInner += in[j];				
					}
					for(int k = 0; k < shift; k++) {
						shiftedInner += in[k];
					}
				}
			}
		}
		//System.out.println(shiftedInner);
		return shiftedInner.toCharArray();
	}
	
	
	// makes sure movable_disk does not have any repeat characters
	// maybe i shouldve used a set or list->collection.sort instead? but w/e this is done and it works
	public static boolean isDuplicate(String s) {
		char c ;
		if(s.length() == STATIONARY_DISK.length()) {
			for(int i = 0; i < s.length(); i++) {
				c = s.charAt(i);
				for(int j = 1; j < s.length() && j!= i; j++) {
					if(c == s.charAt(j)) {
						//System.out.println(c + " " + s.charAt(j)); // for testing
						return true;
					}
				}
			}
		} else return false;
		return false;
	}
	
	
}


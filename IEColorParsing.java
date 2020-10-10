import java.util.Scanner;

// https://stackoverflow.com/questions/8318911/why-does-html-think-chucknorris-is-a-color/8333464#8333464
// https://stackoverflow.com/questions/8318911/why-does-html-think-chucknorris-is-a-color/52857795#52857795
// used this a lot to check: https://mothereff.in/bgcolor

public class IEColorParsing {

	// should've used separate methods, but screw it, im finally done !!!!!!!!!!!!!!!!!!

	// tests:
	// AoCooooooAoCooooooAoCooooooCCDAOHJPUNK 00a000
	// ooovooooFvoFvFF 00 00 f0
	// oooofooooffFvoFvFF 00 0f f0
	// ooofooooffvofvf 0f 0f f0
	// ooooofoooooffFvooFvFF 00 0f f0
	// ooovFooovFooovF
	// mmmbcoofooomlkombcoofoooo 0b 00 f0
	// note to self- cute colors:
	// iouworiejkhgcsdlllagjdriw 00 c0 d0
	// asdfjsdfksjd;lakfvnmxvce a0 00 f0
	// kldsklsdmmmffsdcnnvjjceiuowe d0 f0 e0
	// kjsalfdkjsadlkfjslkfajdfks 00 ad fa
	// louise belcher				00 e0 c0

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter anything. Enter *** to quit: ");
		String putIn = scan.nextLine();

		while (!putIn.equalsIgnoreCase("***")) { // ignore case? idk

			// String putIn = "chucknorris".toLowerCase(); // for testing purposes
			char input[] = putIn.toLowerCase().toCharArray(); // .toCharArray();
			char invalid[] = "ghijklmnopqrstuvwxyz".toCharArray();
			String in = "";
			System.out.println("original: \t   " + putIn);

			// Replace all nonvalid hexadecimal characters with 0’s:
			// chucknorris becomes c00c0000000
			for (int i = 0; i < input.length; i++) {
				for (int j = 0; j < invalid.length - 1; j++) {
					if (input[i] == invalid[j])
						input[i] = '0';
					else if(input[i] == ' ') // didn't even realize this was needed until "louise belcher"
						input[i] = '0';
				}
				in += input[i];
			}
			System.out.println("replaced: \t   " + in); // test

			// Pad out to the next total number of characters divisible by 3 (11 → 12):
			// c00c 0000 0000
			int padout = 0;
			if (in.length() % 3 != 0) {
				int temp1 = in.length() / 3;
				int temp2 = temp1 * 3;
				padout = temp2 + 3;
			}
			int amtZeroes = padout - in.length();
			for (int i = 0; i < amtZeroes; i++)
				in += 0;
			System.out.println("pad  out: \t   " + in); // test

			// chunk
			String[] inSplit = new String[3]; // [c00c, 0000, 0000]
			String inStr = "";
			int numPerGrp = in.length() / 3;

			if (input.length > 3) {
				if (in.length() >= 24) { // no leading 0's ; truncate to 8 characters from left
					inSplit[0] = in.substring(numPerGrp - 8, numPerGrp);
					inSplit[1] = in.substring((numPerGrp * 2) - 8, numPerGrp * 2);
					inSplit[2] = in.substring((in.length()) - 8, in.length());
					// for(String s : inSplit) System.out.print(s + " "); // for ttesting
					
					// truncate to 8 chars first, then truncate leading 0's ?
					// if 0's: leading zeros truncated
					if (inSplit[0].charAt(0) == '0' && inSplit[1].charAt(0) == '0' && inSplit[2].charAt(0) == '0') {
						for (int i = 0; i < (inSplit[0].length() - 5); i++) {
							// idk what -5 does, but it works. im tired and have been working on this for
							// too long
							while (inSplit[0].charAt(i) == '0' && inSplit[1].charAt(i) == '0'
									&& inSplit[2].charAt(i) == '0') {
								i++;
							}
							inSplit[0] = inSplit[0].substring(i);
							inSplit[1] = inSplit[1].substring(i);
							inSplit[2] = inSplit[2].substring(i);
						}
						// System.out.println(inSplit[0].length() + " "+ inSplit[1].length() + " "+
						// inSplit[2].length()); // testing
					}
					truncateTo2(inSplit); // truncate to 2 characters from right

				} else { // entire string < 24, just truncate to 2 in each group && no leading 0's ; done
					inSplit[0] = in.substring(0, numPerGrp);
					inSplit[1] = in.substring((numPerGrp), numPerGrp * 2);
					inSplit[2] = in.substring((numPerGrp * 2), in.length());
					if (inSplit[0].charAt(0) == '0' && inSplit[1].charAt(0) == '0' && inSplit[2].charAt(0) == '0') {
						for (int i = 0; i < (in.length() / 3) - 3; i++) {
							while (inSplit[0].charAt(i) == '0' && inSplit[1].charAt(i) == '0'
									&& inSplit[2].charAt(i) == '0') { // while -> if
								i++;
							}
							inSplit[0] = inSplit[0].substring(i);
							inSplit[1] = inSplit[1].substring(i);
							inSplit[2] = inSplit[2].substring(i);
						}
					}
					truncateTo2(inSplit);
				}
			}

			for (int j = 0; j < 3; j++) {
				if (inSplit[j] == null)
					inSplit[j] = '0' + "";
				inStr += inSplit[j] + ' '; // space for easy viewing
			}
			System.out.println("split + truncated: " + inStr);
			
			
			System.out.print("\nEnter anything. Enter *** to quit: ");
			putIn = scan.nextLine();
		}

	}

	public static void truncateTo2(String[] ins) {
		if (ins[0].length() < 2) { // b/c oooFoooFoooF -> f f f
			ins[0] = "0" + ins[0];
			ins[1] = "0" + ins[1];
			ins[2] = "0" + ins[2];
		} else {
			ins[0] = ins[0].substring(0, 2); // to truncate to first 2 chars of each group
			ins[1] = ins[1].substring(0, 2);
			ins[2] = ins[2].substring(0, 2);
		}

	}

}

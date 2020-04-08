import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordShuffle {
	public static void main(String[] args) throws IOException {
		Random rand = new Random();
		int r;
		
    // replace __ with file names
		String newFile = "__"; // write shit into this file
		String originalFile = "__"; // get shit from this file
		
		PrintWriter fileOut = null;
		fileOut = new PrintWriter(new BufferedWriter(new FileWriter(newFile))); //write
		
		// danke: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
		List<String> lines = Collections.emptyList();
		lines = Files.readAllLines(Paths.get(originalFile), StandardCharsets.UTF_8);
		Iterator<String> itr = lines.iterator();
		
		ArrayList<Integer> nums = new ArrayList<>(lines.size());
		int i = 0;
		while(i < lines.size()) {
			itr.hasNext();
			r = rand.nextInt(lines.size());
			if(!nums.contains(r)) {
				fileOut.print(lines.get(r) + "\n");
				nums.add(i, r);
				i++;	
			}
			
		}
		fileOut.close();
	}
}

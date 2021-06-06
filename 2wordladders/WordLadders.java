import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;

public class WordLadders {
	HashMap<String, HashSet<String>> graph;

	public WordLadders() {

	}

	public static void main(String[] args) throws Exception {
		WordLadders w = new WordLadders();
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int q = sc.nextInt();

		w.readgraph(n, sc);
		sc.nextLine();
		ArrayList<Integer> distances = w.readInputfile(q, sc);
		for(int i = 0; i < distances.size(); i++) {
			int ans = distances.get(i);
			if (ans == -1) {
				System.out.println("Impossible");
			} else {
				System.out.println(ans);
			}
		}
	}

	public void readgraph(int n, Scanner scanner) throws Exception {
		graph = new HashMap<String, HashSet<String>>();

		for(int j = 0; j < n; j++) {
			String word = scanner.next();
			//System.out.println(word);
			for (int i = 0; i < 5; i++) {
				String subWord;
				subWord = word.replaceFirst(word.substring(i, i + 1), "");
				char[] characters = subWord.toCharArray();
				Arrays.sort(characters);
				subWord = String.valueOf(characters);
				if (graph.get(subWord) != null) {
					graph.get(subWord).add(word);
				}
					else {
					graph.put(subWord, new HashSet<String>());
					graph.get(subWord).add(word);
					}
				}
			}
		}

	public int BFS(String from, String to) {
		ArrayList<HashSet<String>> layers = new ArrayList<HashSet<String>>();
		HashSet<String> layer0 = new HashSet<String>();
		layer0.add(from);
		layers.add(layer0);
		HashSet<String> checked = new HashSet<String>();
		checked.add(from);
		int distance = 0;
		while (!layers.get(distance).isEmpty()) {
			if(layers.get(distance).contains(to)) {
				return distance;
			} else  {
				distance++;
				layers.add(new HashSet<String>());
				for (String str : layers.get(distance - 1)) {
					str = str.substring(1, 5);
					char[] characters = str.toCharArray();
					Arrays.sort(characters);
					str = String.valueOf(characters);
					for (String s : graph.get(str)) {
						if (s.equals(to)) {return distance; }
						if(!checked.contains(s)) {
							layers.get(distance).add(s);
						}
						checked.add(s);
					}
				}
			}
		}
		return -1;
	}

	public ArrayList<Integer> readInputfile(int q, Scanner scanner) throws FileNotFoundException {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		//Scanner scanner = new Scanner(new File(filePath));
		//while(scanner.hasNext()) {
		for(int i = 0; i < q; i++) {
			String line = scanner.nextLine();
			//System.out.println(line);
			String[] words = line.split(" ");
			distances.add(BFS(words[0], words[1]));
		}
		return distances;
	}
}

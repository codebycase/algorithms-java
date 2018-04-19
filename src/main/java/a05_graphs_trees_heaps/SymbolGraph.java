/******************************************************************************
 *  Compilation:  javac SymbolGraph.java
 *  Execution:    java SymbolGraph filename.txt delimiter
 *  Dependencies: ST.java Graph.java In.java StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/routes.txt
 *                https://algs4.cs.princeton.edu/41graph/movies.txt
 *                https://algs4.cs.princeton.edu/41graph/moviestiny.txt
 *                https://algs4.cs.princeton.edu/41graph/moviesG.txt
 *                https://algs4.cs.princeton.edu/41graph/moviestopGrossing.txt
 *  
 *  %  java SymbolGraph routes.txt " "
 *  JFK
 *     MCO
 *     ATL
 *     ORD
 *  LAX
 *     PHX
 *     LAS
 *
 *  % java SymbolGraph movies.txt "/"
 *  Tin Men (1987)
 *     Hershey, Barbara
 *     Geppi, Cindy
 *     Jones, Kathy (II)
 *     Herr, Marcia
 *     ...
 *     Blumenfeld, Alan
 *     DeBoy, David
 *  Bacon, Kevin
 *     Woodsman, The (2004)
 *     Wild Things (1998)
 *     Where the Truth Lies (2005)
 *     Tremors (1990)
 *     ...
 *     Apollo 13 (1995)
 *     Animal House (1978)
 *
 * 
 *  Assumes that input file is encoded using UTF-8.
 *  % iconv -f ISO-8859-1 -t UTF-8 movies-iso8859.txt > movies.txt
 *
 ******************************************************************************/

package a05_graphs_trees_heaps;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SymbolGraph {
	private final String NEW_LINE = System.getProperty("line.separator");
	private final int numVertices;
	private int numEdges;

	private Map<String, Integer> map; // key -> index
	private String[] keys; // index -> key, inverted array
	private List<List<Integer>> adjacents;

	public SymbolGraph(String filename, String delimiter) {
		map = new HashMap<String, Integer>();

		// First pass builds the index by reading strings to associate
		// distinct strings with an index
		try {
			File file = new File(filename);
			FileInputStream stream = new FileInputStream(file);
			Scanner scanner = new Scanner(new BufferedInputStream(stream));

			// first pass builds the index by reading vertex names
			while (scanner.hasNextLine()) {
				String[] a = scanner.nextLine().split(delimiter);
				for (String value : a) {
					if (!map.containsKey(value))
						map.put(value, map.size());
				}
			}

			// inverted index to get string keys in an array
			keys = new String[map.size()];
			for (String name : map.keySet()) {
				keys[map.get(name)] = name;
			}

			numVertices = map.size();

			// second pass builds the graph by connecting first vertex on each
			// line to all others
			this.numEdges = 0;
			this.adjacents = new ArrayList<>(numVertices);
			for (int v = 0; v < numVertices; v++) {
				adjacents.add(new ArrayList<Integer>());
			}

			stream = new FileInputStream(file);
			scanner = new Scanner(new BufferedInputStream(stream));
			while (scanner.hasNextLine()) {
				String[] a = scanner.nextLine().split(delimiter);
				int v = map.get(a[0]);
				for (int i = 1; i < a.length; i++) {
					int w = map.get(a[i]);
					addEdge(v, w);
				}
			}
			scanner.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not read " + filename, e);
		}
	}

	public boolean contains(String key) {
		return map.containsKey(key);
	}

	public int indexOf(String key) {
		return map.get(key);
	}

	public String nameOf(int vertex) {
		validateVertex(vertex);
		return keys[vertex];
	}

	public int numVertices() {
		return numVertices;
	}

	public int numEdges() {
		return numEdges;
	}

	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		numEdges++;
		adjacents.get(v).add(w);
		adjacents.get(w).add(v);
	}

	public Iterable<Integer> adjacents(int v) {
		validateVertex(v);
		return adjacents.get(v);
	}

	public int degree(int v) {
		validateVertex(v);
		return adjacents.get(v).size();
	}

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex >= numVertices)
			throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (numVertices - 1));
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(numVertices + " vertices, " + numEdges + " edges " + NEW_LINE);
		for (int v = 0; v < numVertices; v++) {
			s.append(v + ": ");
			for (int w : adjacents.get(v)) {
				s.append(w + " ");
			}
			s.append(NEW_LINE);
		}
		return s.toString();
	}
}

package util;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	public int id;
	public int depth;
	public State state;
	public List<Vertex> edges;

	public Vertex() {
		state = State.UNVISITED;
		edges = new ArrayList<>();
	}

	public Vertex(int id) {
		this();
		this.id = id;
	}

	public Vertex(int id, int depth) {
		this(id);
		this.depth = depth;
	}
}

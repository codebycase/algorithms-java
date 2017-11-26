package util;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	public State state;
	public List<Vertex> edges;

	public Vertex() {
		state = State.UNVISITED;
		edges = new ArrayList<>();
	}
}

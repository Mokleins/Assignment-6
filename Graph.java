import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Graph implementation About: "You may use the adjacency matrix approach found
 * in the text book, or you may use a set of Towns and a set of Roads" this
 * class will use the SETS approach
 * 
 * @author xylarco
 *
 */
public class Graph implements GraphInterface<Town, Road> {
	// contains a set V of vertices and a set E of edges
	private Set<Town> vertices;
	private Set<Road> edges;

	// use a set of Towns (vertices) for evaluation
	// unsettled vertices must be evaluated
	private Set<Town> unSettledVertices;
	// if a shortest path from the source to a vertex has been found, is moved from
	// unsettled to settled
	private Set<Town> settledVertices;
	// in order to get the path, all vertex predecessors are stored in this map
	private Map<Town, Town> vertexPredecessors;
	private Map<Town, Integer> distances;

	public Graph(Set<Town> vertices, Set<Road> edges) {
		super();
		this.vertices = vertices;
		this.edges = edges;
		this.settledVertices = new HashSet<Town>();
		this.unSettledVertices = new HashSet<Town>();
		this.distances = new HashMap<Town, Integer>();
		this.vertexPredecessors = new HashMap<Town, Town>();
		
	}

	/**
	 * @param vertices
	 * @param edges
	 */
	public Graph() {
		super();
		this.vertices = new HashSet<Town>();
		this.edges = new HashSet<Road>();
		this.settledVertices = new HashSet<Town>();
		this.unSettledVertices = new HashSet<Town>();
		this.distances = new HashMap<Town, Integer>();
		this.vertexPredecessors = new HashMap<Town, Town>();
	}

	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		Road road = null;
		for (Road edge : edges) {
			if (edge.contains(sourceVertex) && edge.contains(destinationVertex)) {
				road = edge;
			}
		}
		return road;
	}

	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road road = new Road(sourceVertex, destinationVertex, weight, description);
		vertices.add(sourceVertex);
		vertices.add(destinationVertex);
		edges.add(road);
		return road;
	}

	@Override
	public boolean addVertex(Town v) {
		return vertices.add(v);
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		return Objects.nonNull(this.getEdge(sourceVertex, destinationVertex));
	}

	@Override
	public boolean containsVertex(Town v) {
		return vertices.contains(v);
	}

	@Override
	public Set<Road> edgeSet() {
		return this.edges;
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> edgesOfAVertex = new HashSet<Road>();
		for (Road road : edges) {
			if (road.contains(vertex)) {
				edgesOfAVertex.add(road);
			}
		}
		return edgesOfAVertex;
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road roadToRemove = new Road(sourceVertex, destinationVertex, weight, description);
		if (edges.remove(roadToRemove)) {
			return roadToRemove;
		}
		return null;
	}

	@Override
	public boolean removeVertex(Town v) {
		boolean removed = false;
		/*
		 * Removes the specified vertex from this graph including all its touching edges
		 * if present. More formally, if the graph contains a vertex u such that
		 * u.equals(v), the call removes all edges that touch u and then removes u
		 * itself
		 */
		if (vertices.contains(v)) {
			// avoid concurrent modification exception
			Set<Road> removables = edgesOf(v);
			for (Road road : removables) {
				edges.remove(road);
			}
			removed = vertices.remove(v);

		}
		return removed;
	}

	@Override
	public Set<Town> vertexSet() {
		return this.vertices;
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		dijkstraShortestPath(sourceVertex);
		// use predecessors to get the path
		LinkedList<String> paths = new LinkedList<String>();
		Town predecessor = destinationVertex;
		while (vertexPredecessors.get(predecessor) != null) {
			Road edge = getEdge(vertexPredecessors.get(predecessor), predecessor);
			String source = vertexPredecessors.get(predecessor).getName();
			String destination = predecessor.getName();
			
			predecessor = vertexPredecessors.get(predecessor);
			
			paths.add(source + " via " + edge.getName() + " to " + destination
					+ " " + edge.getWeight() + " mi");
		}
		//reverse (destination-source path to source-destination path)
		Collections.reverse(paths);
		return new ArrayList<String>(paths);
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		// Assign to every vertex a tentative distance value: set it to zero for the
		// initial vertex
		// and to infinity for all other nodes (see getDistance(), currently using the
		// Integer maximum value)
		distances.put(sourceVertex, 0);
		unSettledVertices.add(sourceVertex);

		while (unSettledVertices.size() > 0) {
			Town vertex = getLowestDistanceVertex(unSettledVertices);
			settleVertex(vertex);
			findMinimalDistances(vertex);
		}

	}
	/**
	 * get the vertex with the lowest distance from the source out of the unsettled vertices
	 * @param vertices
	 * @return Town vertex
	 */
	private Town getLowestDistanceVertex(Set<Town> vertices) {
		Town lowestDistanceVertex = null;
		for (Town vertex : vertices) {
			if (lowestDistanceVertex == null) {
				lowestDistanceVertex = vertex;
			} else {
				if (getDistance(vertex) < getDistance(lowestDistanceVertex)) {
					lowestDistanceVertex = vertex;
				}
			}
		}
		return lowestDistanceVertex;
	}
	/**
	 * Get the distance stored in the distances map. 
	 * Assign to every vertex a tentative "infinity" distance value if distance is not found in the map
	 * @param destinationVertex
	 * @return distance
	 */
	private int getDistance(Town destinationVertex) {
		Integer distance = distances.get(destinationVertex);
		if (distance == null) {
			// "INFINITY"
			distance = Integer.MAX_VALUE;
		}
		return distance;
	}
	/**
	 * settle a vertex in the sets
	 * @param vertex
	 */
	private void settleVertex(Town vertex) {
		settledVertices.add(vertex);
		unSettledVertices.remove(vertex);
	}
	/**
	 * Find and put minimal distances given a vertex
	 * @param vertex
	 */
	private void findMinimalDistances(Town vertex) {
		findAndSetAdjacentVertices(vertex);
		List<Town> adjacentVertices = vertex.getAdjacentTowns();
		for (Town adjacentVertex : adjacentVertices) {
			Road edge = getEdge(vertex, adjacentVertex);
			int edgeVertexDistance = getDistance(vertex) + edge.getWeight();
			// put the minimal distance
			if (getDistance(adjacentVertex) > edgeVertexDistance) {
				distances.put(adjacentVertex, edgeVertexDistance);
				vertexPredecessors.put(adjacentVertex, vertex);
				unSettledVertices.add(adjacentVertex);
			}
		}
	}

	/**
	 * find and set the adjacent vertices of a vertex
	 * @param vertex
	 * @return adjacent vertices of the vertex
	 */
	private void findAndSetAdjacentVertices(Town vertex) {
		List<Town> adjacents = new ArrayList<Town>();
		Set<Road> edges = edgesOf(vertex);
		for (Road edge : edges) {
			if (edge.getSource().equals(vertex)) {
				adjacents.add(edge.getDestination());
			} else {
				adjacents.add(edge.getSource());
			}
		}
		vertex.setAdjacentTowns(adjacents);		
	}
}

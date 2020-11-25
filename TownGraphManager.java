import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author xylarco
 *
 */
public class TownGraphManager implements TownGraphManagerInterface {
	private Graph graph = new Graph();

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Road road = graph.addEdge(new Town(town1), new Town(town2), weight, roadName);
		return (Objects.nonNull(road));
	}

	@Override
	public String getRoad(String town1, String town2) {
		Road road = graph.getEdge(new Town(town1), new Town(town2));
		if (Objects.nonNull(road)) {
			return road.getName();
		}
		return null;
	}

	@Override
	public boolean addTown(String v) {
		return graph.addVertex(new Town(v));
	}

	@Override
	public Town getTown(String name) {
		for (Town t : graph.vertexSet()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		// TODO Auto-generated method stub
		return graph.containsEdge(new Town(town1), new Town(town2));
	}

	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> roadsNames = new ArrayList<String>();
		ArrayList<Road> orderedRoadsList = new ArrayList<Road>(graph.edgeSet());
		Collections.sort(orderedRoadsList);
		for (Road road : orderedRoadsList) {
			roadsNames.add(road.getName());
		}
		return roadsNames;
	}

	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Road removedRoad = graph.removeEdge(new Town(town1), new Town(town2), 0, road);
		return Objects.nonNull(removedRoad);
	}

	@Override
	public boolean deleteTown(String v) {
		return graph.removeVertex(new Town(v));
	}

	@Override
	public ArrayList<String> allTowns() {
		ArrayList<Town> orderedTowns = new ArrayList<Town>(graph.vertexSet());
		Collections.sort(orderedTowns);
		ArrayList<String> allTowns = new ArrayList<String>();
		for (Town town : orderedTowns) {
			allTowns.add(town.getName());
		}
		return allTowns;
	}

	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		return graph.shortestPath(new Town(town1), new Town(town2));
	}

	/**
	 * Read file with data. The road-name and miles are separated by a comma, while
	 * the road information and the two towns are separated by semi-colons. For
	 * example: I-94,282;Chicago;Detroit	
	 * @param File documentation 
	 */
	@Override
	public void populateTownGraph(File file) throws FileNotFoundException, IOException {
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
				String[] lineSplitSemiColons = scanner.nextLine().split(";");
				String source = lineSplitSemiColons[1];
				String destination = lineSplitSemiColons[2];
				String[] roadInfoSplit = lineSplitSemiColons[0].split(",");
				String roadName = roadInfoSplit[0];
				Integer weight = Integer.parseInt(roadInfoSplit[1]);
				graph.addEdge(new Town(source), new Town(destination), weight, roadName);
			}
		}

	}

}

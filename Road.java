import java.util.Objects;

/**
 * The class stores references to the two vertices(Town endpoints), the distance
 * between vertices, and a name, and the traditional methods (constructors,
 * getters/setters, toString, etc.), and a compareTo, which compares two Road
 * objects
 * 
 * @author xylarco
 *
 */
public class Road implements Comparable<Road> {
	
	private Town source;
	private Town destination;
	private int weight;
	private String name;


	/**
	 * @param source
	 * @param destination
	 * @param weight
	 * @param name
	 */
	public Road(Town source, Town destination, int weight, String name) {
		super();
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.name = name;
	}

	/**
	 * @param source
	 * @param destination
	 * @param name
	 */
	public Road(Town source, Town destination, String name) {
		super();
		this.source = source;
		this.destination = destination;
		this.name = name;
	}

	@Override
	public int compareTo(Road o) {
		return this.getName().compareTo(o.getName());
	}

	/**
	 * Returns true only if the Road contains the given town
	 * 
	 * @param town
	 * @return true only if the Road is connected to the given Town
	 */
	public boolean contains(Town town) {
		return (source.compareTo(town) == 0 || destination.compareTo(town) == 0);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the source
	 */
	public Town getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Town source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public Town getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Town destination) {
		this.destination = destination;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(destination, source);
	}

	/**
	 * Since this is a undirected graph, an edge from A to B is equal to an edge
	 * from B to A
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Road other = (Road) obj;
		//a road that goes from point A to point B is the same as a road that goes from point B to point A
		return ((Objects.equals(destination, other.destination) && Objects.equals(source, other.source))
				||
				(Objects.equals(destination, other.source) && Objects.equals(source, other.destination)));
	}

	@Override
	public String toString() {
		return "Road [source=" + source + ", destination=" + destination + ", weight=" + weight + ", name=" + name
				+ "]";
	}

}

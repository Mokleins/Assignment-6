import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Town as a node of a graph. It will implement the Comparable interface. Two
 * towns will be considered the same if their name is the same.
 * Town class holds the name of the town and a list of adjacent towns
 * @author xylarco
 *
 */
public class Town implements Comparable<Town> {
	private String name;
	private List<Town> adjacentTowns;
	/**
	 * @param name
	 */
	public Town(String name) {
		super();
		this.name = name;
		this.adjacentTowns = new ArrayList<Town>();
	}

	@Override
	public int compareTo(Town o) {
		// compare names
		return this.getName().compareTo(o.getName());
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
	 * @return the adjacentTowns
	 */
	public List<Town> getAdjacentTowns() {
		return adjacentTowns;
	}

	/**
	 * @param adjacentTowns the adjacentTowns to set
	 */
	public void setAdjacentTowns(List<Town> adjacentTowns) {
		this.adjacentTowns = adjacentTowns;
	}

	// autogenerated by eclipse using name attribute
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

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
		Town other = (Town) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Town [name=" + name + "]";
	}




}
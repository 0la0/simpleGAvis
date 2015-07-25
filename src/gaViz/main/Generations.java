package gaViz.main;

import java.util.ArrayDeque;

public class Generations {

	private int size;
	private ArrayDeque<Population> populations = new ArrayDeque<Population>();
	
	public Generations (int size) {
		this.size = size;
	}
	
	public int size () {
		return this.size;
	}
	
	public void add (Population p) {
		if (this.populations.size() >= this.size && this.populations.peekLast() != null) {
			this.populations.removeFirst();
		}
		this.populations.add(p);
	}
	
	public Population getLatestPopulation () {
		return this.populations.peekLast();
	}
	
	public ArrayDeque<Population> getPopulations () {
		return this.populations;
	}
	
}

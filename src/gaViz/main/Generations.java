package gaViz.main;

import java.util.ArrayDeque;

public class Generations {

	private int sizeLimit;
	private ArrayDeque<Population> populations = new ArrayDeque<Population>();
	
	public Generations (int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	
	public void add (Population p) {
		if (this.populations.size() >= this.sizeLimit && this.populations.peekLast() != null) {
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

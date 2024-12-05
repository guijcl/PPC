package dna;

public class Result {
	
	private int[] results;
	
	public Result(int[] results) {
		this.results = results;
	}
	
	public int[] getResults() {
		return results;
	}
	
	public boolean compare(Result o) {
		if (results.length != o.results.length) return false;
		for (int i = 0; i< results.length; i++) {
			if (results[i] != o.results[i]) return false;
		}
		return true;
	}
	
	public Result sum(Result o) {
		int[] nr = new int[results.length];
		for (int i=0; i<results.length; i++) {
			nr[i] = results[i] + o.results[i];
		}
		return new Result(nr);
	}

}

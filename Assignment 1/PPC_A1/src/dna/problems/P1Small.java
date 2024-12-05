package dna.problems;
import java.util.Arrays;
import java.util.List;

import dna.Result;

public class P1Small implements Problem {
	@Override
	public char[] getSearchSequence() {
		return new char[] {'c', 'g', 't', 'r', 'a', 'a', 'a'};
	}
	
	@Override
	public List<String> getPatterns() {
		return Arrays.asList("cg", "tr", "aa", "tat");
	}
	
	public Result getSolution() {
		return new Result(new int[] {1, 1, 2, 0});
	}
}

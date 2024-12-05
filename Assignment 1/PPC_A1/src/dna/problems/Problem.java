package dna.problems;
import java.util.List;

import dna.Result;

public interface Problem {

	char[] getSearchSequence();

	List<String> getPatterns();

	Result getSolution();

}
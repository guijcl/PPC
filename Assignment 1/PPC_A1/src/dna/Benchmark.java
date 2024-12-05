package dna;

import java.util.List;
import java.util.function.BiFunction;

import dna.problems.P1Small;
import dna.problems.P2Large;
import dna.problems.Problem;

public class Benchmark {
	
	public static Result sequential(Problem p, Integer ncores) {
		char[] searchSequence = p.getSearchSequence();
		List<String> patterns = p.getPatterns();
		
		int[] results = new int[p.getPatterns().size()]; 
		
		for(int i = 0; i < searchSequence.length; i++) {
			for(int j = 0; j < patterns.size(); j++) {
				if(isIn(searchSequence, i, patterns.get(j)))
					results[patterns.indexOf(patterns.get(j))]++;
			}
		}
		
		return new Result(results);
	}
	
	public static Result parallel(Problem p, Integer ncores) {
		char[] searchSequence = p.getSearchSequence();
		List<String> patterns = p.getPatterns();
		
		int size = searchSequence.length;
		
		int NUMBER_OF_THREADS = ncores;
		if(NUMBER_OF_THREADS > size)
			NUMBER_OF_THREADS = size;
		int[][] t_results = new int[NUMBER_OF_THREADS][p.getPatterns().size()]; 
		int chunkSize = size / NUMBER_OF_THREADS;
		Thread[] ts = new Thread[NUMBER_OF_THREADS];
		
		for(int i = 0; i < NUMBER_OF_THREADS; i++) {
			int start = i * chunkSize;
			int end = (i < NUMBER_OF_THREADS - 1) ? (i + 1) * chunkSize : size;
			
			int index = i;
			
			ts[i] = new Thread( () -> {
				for(int j = start; j < end; j++) {
					for(int k = 0; k < patterns.size(); k++) {
						if(isIn(searchSequence, j, patterns.get(k)))
							t_results[index][patterns.indexOf(patterns.get(k))]++;
					}
				}
			});
			ts[i].start();
		}
		
		for(Thread t : ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int[] results = new int[p.getPatterns().size()]; 
		for(int i = 0; i < t_results.length; i++) {
			for(int j = 0; j < t_results[i].length; j++) {
				results[j] += t_results[i][j];
			}
		}
				
		return new Result(results);
	}
		
	
	public static boolean isIn(char[] arr, int start, String pattern) {
		if ( (arr.length - start) < pattern.length()) return false;
		for (int i=0; i < pattern.length(); i++) {
			if (arr[start + i] != pattern.charAt(i)) return false;
		}
		return true;
	}
	
	public static void bench(Problem p, BiFunction<Problem, Integer, Result> f, String name) {
		
		int maxCores = Runtime.getRuntime().availableProcessors();
		for (int ncores=1; ncores<=maxCores; ncores *= 2) {

			for (int i=0; i< 30; i++) {
				long tseq = System.nanoTime();
				Result r = f.apply(p, ncores);
				tseq = System.nanoTime() - tseq;
			
				if (!r.compare(p.getSolution())) {
					System.out.println("Wrong result for " + name + ".");
					System.exit(1);
				}
				System.out.println(ncores + ";" + name + ";" + tseq);
			}
		}
	}
	
	public static void main(String[] args) {
		Problem p = (Runtime.getRuntime().availableProcessors() == 64) ? new P2Large() : new P1Small();
		Benchmark.bench(p, Benchmark::sequential, "seq");
		Benchmark.bench(p, Benchmark::parallel, "par");
	}
}

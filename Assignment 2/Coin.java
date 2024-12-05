import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("serial")
public class Coin extends RecursiveTask<Integer> {

	public static final int LIMIT = 999;
	
	private int[] coins;
	private int index;
	private int accumulator;
	//public static AtomicInteger task_count = new AtomicInteger();
	
	public Coin(int[] coins, int index, int accumulator) {
		this.coins = coins;
		this.index = index;
		this.accumulator = accumulator;
		//task_count.addAndGet(1);
	}
	
	public static int[] createRandomCoinSet(int N) {
		int[] r = new int[N];
		for (int i = 0; i < N ; i++) {
			if (i % 10 == 0) {
				r[i] = 400;
			} else {
				r[i] = 4;
			}
		}
		return r;
	}

	public static void main(String[] args) {
		int nCores = Runtime.getRuntime().availableProcessors();

		int[] coins = createRandomCoinSet(30);

		int repeats = 40;
		for (int i=0; i<repeats; i++) {
			long seqInitialTime = System.nanoTime();
			int rs = seq(coins, 0, 0);
			long seqEndTime = System.nanoTime() - seqInitialTime;
			System.out.println(nCores + ";Sequential;" + seqEndTime);
			
			long parInitialTime = System.nanoTime();
			Coin c = new Coin(coins, 0, 0);
			//c.fork();
			ForkJoinPool pool = new ForkJoinPool(10);
			pool.invoke(c);
			int rp_join = c.join();
			/** Contagem de Tasks criadas a cada iteração do programa Paralelizado (Ver atributos e construtor da Classe)
			 * System.out.println(task_count.get());
			 * task_count.set(0);
			 */
			long parEndTime = System.nanoTime() - parInitialTime;
			System.out.println(nCores + ";Parallel;" + parEndTime);
			
			if (rp_join != rs) {
				System.out.println("Wrong Result!");
				System.exit(-1);
			}
		}

	}

	private static int seq(int[] coins, int index, int accumulator) {
		if (index >= coins.length) {
			if (accumulator < LIMIT) {
				return accumulator;
			}
			return -1;
		}
		if (accumulator + coins[index] > LIMIT) {
			return -1;
		}
		
		int a = seq(coins, index+1, accumulator);
		int b = seq(coins, index+1, accumulator + coins[index]);
		
		return Math.max(a,  b);
	}

	@Override
	protected Integer compute() {
		if (index >= coins.length) {
			if (accumulator < LIMIT) {
				return accumulator;
			}
			return -1;
		}
		if (accumulator + coins[index] > LIMIT) {
			return -1;
		}
		
		
		if(RecursiveTask.getSurplusQueuedTaskCount() > 2) 
			return seq(coins, index, accumulator);
		
		
		Coin c1 = new Coin(coins, index + 1, accumulator);
		c1.fork();
		
		Coin c2 = new Coin(coins, index + 1, accumulator + coins[index]);
		c2.fork();
		
		int j1 = c1.join();
		int j2 = c2.join();
		
		return Math.max(j1, j2);
	}
}

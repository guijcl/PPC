package assignment4_v3;

public class Main {
	public static void main(String[] args) {
		
		String numToReplace = "1";
		String numResOnReplace = "999";
		
		Manager m = new Manager();
		
		Customer c = new Customer(m.getAddress(), numToReplace, numResOnReplace);
		
		c.getAddress().sendMessage(new BootstrapMessage());
		// The Customer decides when to shutdown everything.
		
	}
}

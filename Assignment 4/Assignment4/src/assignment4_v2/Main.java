package assignment4_v2;

public class Main {
	public static void main(String[] args) {
		
		String numToReplace = "1";
		String folderNumResOnReplace = "11"; // >10
		String fileNumResOnReplace = "101"; //  >100
		
		if(Integer.parseInt(folderNumResOnReplace) > 10 &&
				Integer.parseInt(fileNumResOnReplace) > 100) {
			Manager m = new Manager();
			
			Customer c = new Customer(m.getAddress(), 
					numToReplace, folderNumResOnReplace, fileNumResOnReplace);
			
			c.getAddress().sendMessage(new BootstrapMessage());
			// The Customer decides when to shutdown everything.
		} else {
			System.out.println("Number substring already exists in Folders or Files");
		}
		
	}
}

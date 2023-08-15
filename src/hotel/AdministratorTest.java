package hotel;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AdministratorTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Administrator test start");

		Administrator admin = new Administrator("ime", "prezime", "musko", "20.09.2022", "0603789714", "bulevar oslobodjenja 15, novi sad", "12", "loz");
		Administrator admin_2 = new Administrator("ime", "prezime", "musko", "20.09.2022", "0603789714", "bulevar oslobodjenja 15, novi sad", "12", "loz");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Administrator test end");
	}
}

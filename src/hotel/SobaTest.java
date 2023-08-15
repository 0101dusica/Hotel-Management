package hotel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SobaTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Soba test start");

		Soba soba_test = new Soba(1, "jednokrevetna");
		Soba soba_test2 = new Soba(2, "lux soba");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Administrator test end");
	}
	
	@Test
    public void test_citaj_zauzetost() {
		Soba soba = new Soba(1, "jednokrevetna");
	    org.junit.Assert.assertTrue( soba.citaj_zauzetost("10.10.2027.", "15.10.2027.", "1"));
		
    }
}

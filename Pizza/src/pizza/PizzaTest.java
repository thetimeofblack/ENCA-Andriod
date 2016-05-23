package pizza;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class PizzaTest
 * @author Gong Zhaowen
 * @version 1.1 23.05.2016
 * The class for testing class Pizza
 */
public class PizzaTest {

	private static Pizza pizza;
	
	/**
	 * initialize static property pizza
	 */
	@BeforeClass
	public static void initialize() {
		try {
			pizza = new Pizza();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Test 1
	 * Medium size with two toppings
	 */
	@Test
	public void testPizza1() {
		pizza.getRadioButton2().setSelected(true);
		pizza.getCheckBox1().setSelected(true);
		pizza.getCheckBox4().setSelected(true);
		pizza.getConfirmButton().doClick();
		assertEquals("Price: 7.5ву", pizza.getLabel().getText());
	}
	
	/**
	 * Test 2
	 * Small size with four toppings
	 */
	@Test
	public void testPizza2() {
		pizza.getRadioButton1().setSelected(true);
		pizza.getCheckBox1().setSelected(true);
		pizza.getCheckBox3().setSelected(true);
		pizza.getCheckBox4().setSelected(true);
		pizza.getCheckBox5().setSelected(true);
		pizza.getConfirmButton().doClick();
		assertEquals("Price: 7.0ву", pizza.getLabel().getText());
	}
}
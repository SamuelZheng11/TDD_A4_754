package acceptance;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertEquals;

public class DefaultSteps {
	
	int five;
	int ten;
	
	@Given("User has $amount dollars")
	public void givenUserHas5Dollars(int amount) {
		five = 5;
	}

	@When("User multiply it by $multiplier")
	public void whenUserMultiplyItBy2(int multiplier) throws InterruptedException {
		ten = five * multiplier;
	}

	@Then("The resultant amount is $result")
	public void thenTheResultantAmountIs10(int result) {
		assertEquals(10, ten);
	}
}

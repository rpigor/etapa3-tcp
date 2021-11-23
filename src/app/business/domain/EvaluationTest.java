package app.business.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EvaluationTest {

	private Evaluation e1, e2, e3;
	private Evaluator evaluator1, evaluator2;
	private Product product1, product2;
	
	@Before
	public void setUp() throws Exception {
		evaluator1 = new Evaluator(1, "John", "SP", null);
		evaluator2 = new Evaluator(2, "Joana", "RS", null);
		
		product1 = new Product(1, "Avon Sun A", evaluator1, null, null);
		product2 = new Product(2, "L'oreal Shampoo JJ", evaluator2, null, null);
		
		e1 = new Evaluation(evaluator1, product1);
		e2 = new Evaluation(evaluator2, product2, -343);
		e3 = new Evaluation(evaluator1, product1);
		
		e3.rate(0);
	}
	
	@Test
	public void testGetEvaluator() {
		assertTrue(e1.getEvaluator().equals(evaluator1));
		assertFalse(e1.getEvaluator().equals(evaluator2));
		assertFalse(e1.getEvaluator().equals(null));
		
		assertFalse(e2.getEvaluator().equals(evaluator1));
		assertTrue(e2.getEvaluator().equals(evaluator2));
		assertFalse(e2.getEvaluator().equals(null));
	}
	
	@Test
	public void testGetProduct() {
		assertTrue(e1.getProduct().equals(product1));
		assertFalse(e1.getProduct().equals(product2));
		assertFalse(e1.getProduct().equals(null));
		
		assertFalse(e2.getProduct().equals(product1));
		assertTrue(e2.getProduct().equals(product2));
		assertFalse(e2.getEvaluator().equals(null));
	}
	
	@Test
	public void testGetRating() {
		assertTrue(e1.getRating() == 0); // rating is not initialized, thus 0
		
		assertTrue(e2.getRating() == -343);
		
		assertTrue(e3.getRating() == 0);
	}
	
	@Test
	public void testIsCompleted() {
		assertFalse(e1.isCompleted());
		
		assertTrue(e2.isCompleted());
		
		assertTrue(e3.isCompleted());
	}
	
	@Test
	public void testIsPending() {
		assertTrue(e1.isPending());
		assertTrue(e1.isCompleted() != e1.isPending());
		
		assertFalse(e2.isPending());
		assertTrue(e2.isCompleted() != e2.isPending());
		
		assertFalse(e2.isPending());
		assertTrue(e3.isCompleted() != e3.isPending());
	}
	
	@Test
	public void testRate() {
		e1.rate(-2);
		assertTrue(e1.getRating() == -2);
		assertTrue(e1.isCompleted());
		
		e2.rate(42);
		assertTrue(e2.getRating() == 42);
		assertTrue(e2.isCompleted());
		
		e3.rate(0);
		assertTrue(e3.getRating() == 0);
		assertTrue(e3.isCompleted());
	}
	
}

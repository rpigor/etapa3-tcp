package app.business.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	
	private Product testProduct1, testProduct2, testProduct3, testProduct4, testProduct5, testProduct6;
	private Evaluator evaluator1, evaluator2, evaluator3;
	private EvaluationGroup eg1, eg2;
	
	@Before
	public void setUp() throws Exception {
		evaluator1 = new Evaluator(10, "Mike", null, null);
		evaluator2 = new Evaluator(9, "Nike", null, null);
		evaluator3 = new Evaluator(8, "Pike", null, null);
		
		eg1 = new EvaluationGroup("eg1", new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator2, evaluator3)));
		eg2 = new EvaluationGroup("eg2", new LinkedList<Evaluator>(Arrays.asList(evaluator2)));
		
		testProduct1 = new Product(0, "nome1", evaluator1, eg1, Category.BB_CREAM);
		testProduct2 = new Product(1, "nome2", evaluator1, eg2, Category.CC_CREAM);
		testProduct3 = new Product(2, "nome3", evaluator1, eg1, Category.DD_CREAM);
		testProduct4 = new Product(3, "nome4", evaluator2, eg2, Category.POWDER_SUNSCREEN);
		testProduct5 = new Product(4, "nome5", evaluator2, eg1, Category.FOUNDATION_SPF);
		testProduct6 = new Product(5, "nome6", evaluator2, eg2, Category.OIL_FREE_MATTE_SPF);
		
		evaluator1.allowProduct(testProduct1);
		
		evaluator2.allowProduct(testProduct2);
		
		evaluator1.allowProduct(testProduct3);
		evaluator2.allowProduct(testProduct3);
	}

	@Test
	public void testGetId() {
		assertTrue(testProduct1.getId() == 0);
		assertTrue(testProduct2.getId() == 1);
		assertTrue(testProduct3.getId() == 2);
		assertFalse(testProduct4.getId() == 2);
		assertFalse(testProduct5.getId() == 1);
		assertFalse(testProduct6.getId() == 0);
	}
	
	@Test
	public void testGetName() {
		assertTrue(testProduct1.getName().equals("nome1"));
		assertTrue(testProduct2.getName().equals("nome2"));
		assertTrue(testProduct3.getName().equals("nome3"));
		assertFalse(testProduct4.getName().equals("nome5"));
		assertFalse(testProduct5.getName().equals("nome6"));
		assertFalse(testProduct6.getName().equals("nome4"));
	}
	
	@Test
	public void testGetRequester() {
		assertTrue(testProduct1.getRequester().equals(evaluator1));
		assertFalse(testProduct2.getRequester().equals(evaluator2));
		assertTrue(testProduct3.getRequester().equals(evaluator1));
		assertTrue(testProduct4.getRequester().equals(evaluator2));
		assertFalse(testProduct5.getRequester().equals(evaluator1));
		assertTrue(testProduct6.getRequester().equals(evaluator2));
	}
	
	@Test
	public void testGetEvaluationGroup() {
		assertTrue(testProduct1.getEvaluationGroup().equals(eg1));
		assertFalse(testProduct2.getEvaluationGroup().equals(eg1));
		assertTrue(testProduct3.getEvaluationGroup().equals(eg1));
		assertTrue(testProduct4.getEvaluationGroup().equals(eg2));
		assertFalse(testProduct5.getEvaluationGroup().equals(eg2));
		assertTrue(testProduct6.getEvaluationGroup().equals(eg2));
	}
	
	@Test
	public void testGetCategory() {
		assertTrue(testProduct1.getCategory().equals(Category.BB_CREAM));
		assertFalse(testProduct2.getCategory().equals(Category.BB_CREAM));
		assertTrue(testProduct3.getCategory().equals(Category.DD_CREAM));
		assertTrue(testProduct4.getCategory().equals(Category.POWDER_SUNSCREEN));
		assertFalse(testProduct5.getCategory().equals(Category.CC_CREAM));
		assertTrue(testProduct6.getCategory().equals(Category.OIL_FREE_MATTE_SPF));
	}

	@Test
	public void testGetAllowedEvaluators() {
		assertTrue(testProduct1.getAllowedEvaluators().equals(Arrays.asList(evaluator1)));
		assertTrue(testProduct2.getAllowedEvaluators().equals(Arrays.asList(evaluator2)));
		assertTrue(testProduct3.getAllowedEvaluators().equals(Arrays.asList(evaluator1, evaluator2)));
		assertFalse(testProduct3.getAllowedEvaluators().equals(Arrays.asList(evaluator2, evaluator1)));
		assertTrue(testProduct4.getAllowedEvaluators().equals(Arrays.asList()));
		assertFalse(testProduct5.getAllowedEvaluators().equals(Arrays.asList(evaluator3)));
		assertFalse(testProduct6.getAllowedEvaluators().equals(Arrays.asList(evaluator2)));
	}

}
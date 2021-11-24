package app.business.domain;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class EvaluationGroupTest {
	
	private Evaluator evaluator1, evaluator2, evaluator3, evaluator4;
	private EvaluationGroup eg1, eg2, eg3, eg4;
	private Product testProduct1, testProduct2, testProduct3, testProduct4, testProduct5, testProduct6;
	
	@Before
	public void setUp() throws Exception {
		
		evaluator1 = new Evaluator(10, null, null, null);
		evaluator2 = new Evaluator(9, null, null, null);
		evaluator3 = new Evaluator(8, null, null, null);
		evaluator4 = new Evaluator(7, null, null, null);
		
		eg1 = new EvaluationGroup("eg1", new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator2, evaluator3, evaluator4)));
		eg2 = new EvaluationGroup("eg2", new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator3)));
		eg3 = new EvaluationGroup("eg3", new LinkedList<Evaluator>(Arrays.asList(evaluator2, evaluator3)));
		eg4 = new EvaluationGroup("eg4", new LinkedList<Evaluator>(Arrays.asList(evaluator2)));
		
		testProduct1 = new Product(0, "nome1", evaluator1, eg1, Category.BB_CREAM);
		testProduct2 = new Product(1, "nome2", evaluator1, eg1, Category.CC_CREAM);
		testProduct3 = new Product(2, "nome3", evaluator1, eg1, Category.DD_CREAM);
		
		testProduct4 = new Product(3, "nome4", evaluator2, eg2, Category.POWDER_SUNSCREEN);
		testProduct5 = new Product(4, "nome5", evaluator2, eg2, Category.FOUNDATION_SPF);
		
		testProduct6 = new Product(5, "nome6", evaluator2, eg4, Category.OIL_FREE_MATTE_SPF);
		
		evaluator1.allowProduct(testProduct1).rate(0);
		evaluator1.allowProduct(testProduct4).rate(2);
		evaluator1.allowProduct(testProduct3);
		
		evaluator2.allowProduct(testProduct2).rate(2);
		evaluator2.allowProduct(testProduct3).rate(1);
		evaluator2.allowProduct(testProduct6).rate(0);
		
		evaluator3.allowProduct(testProduct2);
		evaluator3.allowProduct(testProduct5);
		
		evaluator4.allowProduct(testProduct1).rate(-3);
		evaluator4.allowProduct(testProduct2).rate(1);
	}

	@Test
	public void testGetName() {
		assertTrue(eg1.getName().equals("eg1"));
		assertTrue(eg2.getName().equals("eg2"));
		assertFalse(eg3.getName().equals(null));
		assertFalse(eg4.getName().equals("eg5"));
	}
	
	@Test
	public void testGetMembers() {
		assertTrue(eg1.getMembers().equals(Arrays.asList(evaluator1, evaluator2, evaluator3, evaluator4)));
		assertFalse(eg2.getMembers().equals(Arrays.asList(evaluator1)));
		assertTrue(eg3.getMembers().equals(Arrays.asList(evaluator2, evaluator3)));
		assertFalse(eg3.getMembers().equals(Arrays.asList(evaluator3, evaluator2)));
		assertFalse(eg4.getMembers().equals(Arrays.asList(evaluator2, evaluator3)));
		assertTrue(eg4.getMembers().equals(Arrays.asList(evaluator2)));
	}
	
	@Test
	public void testAllowed() {
		assertFalse(eg1.isAllowed());
		eg1.setAllowed();
		assertTrue(eg1.isAllowed());
		eg2.setAllowed();
		assertTrue(eg2.isAllowed());
	}
	
	@Test
	public void testUnallowed() {
		eg1.setAllowed();
		eg2.setAllowed();
		assertFalse(eg1.isUnallowed());
		assertTrue(eg4.isUnallowed());
		eg1.setUnallowed();
		assertTrue(eg1.isUnallowed());
		eg3.setAllowed();
		assertFalse(eg3.isUnallowed());
	}
	
	@Test
	public void testHasPendingEvaluation() {
		assertTrue(eg1.hasPendingEvaluation());
		assertTrue(eg2.hasPendingEvaluation());
		assertFalse(eg3.hasPendingEvaluation());
		assertFalse(eg4.hasPendingEvaluation());
	}
	
	@Test
	public void testGetAcceptableAndUnacceptableProductsMean() {
		Map<Product, Double> acceptableProductsMeanEG1 = new HashMap<Product, Double>();
		acceptableProductsMeanEG1.put(testProduct2, Double.valueOf(1.5));
		acceptableProductsMeanEG1.put(testProduct3, Double.valueOf(1));
		Map<Product, Double> unacceptableProductsMeanEG1 = new HashMap<Product, Double>();
		unacceptableProductsMeanEG1.put(testProduct1, Double.valueOf(-1.5));	
		assertTrue(eg1.getAcceptableAndUnacceptableProductsMean(0.0).equals(new AbstractMap.SimpleEntry<Map<Product, Double>, Map<Product, Double>>(acceptableProductsMeanEG1, unacceptableProductsMeanEG1)));
	
		Map<Product, Double> acceptableProductsMeanEG4 = new HashMap<Product, Double>();
		acceptableProductsMeanEG4.put(testProduct6, Double.valueOf(0));
		Map<Product, Double> unacceptableProductsMeanEG4 = new HashMap<Product, Double>();
		assertTrue(eg4.getAcceptableAndUnacceptableProductsMean(0.0).equals(new AbstractMap.SimpleEntry<Map<Product, Double>, Map<Product, Double>>(acceptableProductsMeanEG4, unacceptableProductsMeanEG4)));
	}

}

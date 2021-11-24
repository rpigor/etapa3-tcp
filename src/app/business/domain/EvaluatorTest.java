package app.business.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class EvaluatorTest {

    private Evaluator evaluator1, evaluator2, evaluator3, evaluator4;
    private Product testProduct1, testProduct2, testProduct3;
    private EvaluationGroup eg1, eg2;
    private Evaluation ev1, ev2, ev3, ev4, ev5, ev6, ev7, ev8;

    @Before
    public void setUp() throws Exception {
        evaluator1 = new Evaluator(1, "Mike", "SP",
                new LinkedList<Category>(Arrays.asList()));
        evaluator2 = new Evaluator(2, "Nicolas", "RS", 
                new LinkedList<Category>(Arrays.asList(Category.DD_CREAM, Category.POWDER_SUNSCREEN)));
        evaluator3 = new Evaluator(3, "Joe", "SP", 
                new LinkedList<Category>(Arrays.asList(Category.BB_CREAM)));
        evaluator4 = new Evaluator(4, "Jane", "RS", 
                new LinkedList<Category>(Arrays.asList()));
        
        eg1 = new EvaluationGroup(null, new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator2)));
        eg2 = new EvaluationGroup(null, new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator2)));
        
        testProduct1 = new Product(1, "Produto", null, eg1, Category.DD_CREAM);
        testProduct2 = new Product(2, null, evaluator1, eg1, Category.OIL_FREE_MATTE_SPF);
        testProduct3 = new Product(3, null, evaluator3, eg2, Category.BB_CREAM);
        
        ev1 = evaluator1.allowProduct(testProduct1);
        ev2 = evaluator1.allowProduct(testProduct2);
        ev2.rate(3);
        
        ev3 = evaluator2.allowProduct(testProduct1);
        ev3.rate(-1);
        ev4 = evaluator2.allowProduct(testProduct2);
        ev4.rate(-2);
        ev5 = evaluator2.allowProduct(testProduct3);
        ev5.rate(0);
        
        ev6 = evaluator3.allowProduct(testProduct1);
        ev6.rate(3);
        ev7 = evaluator3.allowProduct(testProduct2);
        ev7.rate(0);
        ev8 = evaluator3.allowProduct(testProduct3);
    }
    
	@Test
	public void testGetId() {
		assertTrue(evaluator1.getId() == 1);
		assertFalse(evaluator2.getId() != 2);
		assertFalse(evaluator1.getId() == 0);
	}
	@Test
	public void testGetName() {
		assertTrue(evaluator1.getName().equals("Mike"));
		assertTrue(evaluator2.getName().equals("Nicolas"));
		assertFalse(evaluator1.getName().equals("Nick"));
	}

	@Test
	public void testGetState() {
		assertTrue(evaluator1.getState().equals("SP"));
		assertTrue(evaluator2.getState().equals("RS"));
		assertFalse(evaluator1.getState().equals(""));
	}

	@Test
	public void testGetInterestCategories() {
		assertTrue(evaluator1.getInterestCategories().equals(Arrays.asList()));
		assertFalse(evaluator1.getInterestCategories().equals(Arrays.asList(Category.DD_CREAM, Category.POWDER_SUNSCREEN)));
		assertTrue(evaluator2.getInterestCategories().equals(Arrays.asList(Category.DD_CREAM, Category.POWDER_SUNSCREEN)));
		assertTrue(evaluator3.getInterestCategories().equals(Arrays.asList(Category.BB_CREAM)));
		assertFalse(evaluator3.getInterestCategories().equals(Arrays.asList()));
	}

	@Test
	public void testGetEvaluations() {
		assertTrue(evaluator1.getEvaluations().equals(Arrays.asList(ev1, ev2)));
		assertFalse(evaluator1.getEvaluations().equals(Arrays.asList()));
		assertTrue(evaluator2.getEvaluations().equals(Arrays.asList(ev3, ev4, ev5)));
		assertTrue(evaluator3.getEvaluations().equals(Arrays.asList(ev6, ev7, ev8)));
		assertTrue(evaluator4.getEvaluations().equals(Arrays.asList()));
		assertFalse(evaluator4.getEvaluations().equals(Arrays.asList(ev1)));
	}
	
	@Test
	public void testAllowProduct() {
		assertTrue(evaluator1.allowProduct(testProduct1) == null);
		assertFalse(evaluator4.allowProduct(testProduct1) == null);
	}
    
    @Test
   	public void testIsProductCandidate() {
       	assertTrue(evaluator2.isProductCandidate(testProduct1));
       	
       	assertFalse(evaluator2.isProductCandidate(testProduct2)); // evaluator has no interest
       	
   		assertFalse(evaluator3.isProductCandidate(testProduct3)); // evaluator is the product's requester
   		
   		assertFalse(evaluator1.isProductCandidate(testProduct2)); // both has no interest and is the product's requester
   	}
    
    @Test
	public void testCountAllowedProductsByGroup() {
		assertTrue(evaluator1.countAllowedProductsByGroup(eg1) == 1); // already evaluated products don't count as allowed
		assertTrue(evaluator1.countAllowedProductsByGroup(eg2) == 0);
		
		assertTrue(evaluator2.countAllowedProductsByGroup(eg1) == 0);
		assertTrue(evaluator2.countAllowedProductsByGroup(eg2) == 0);
		
		assertTrue(evaluator3.countAllowedProductsByGroup(eg1) == 0);
		assertTrue(evaluator3.countAllowedProductsByGroup(eg2) == 1);
	}

    @Test
    public void testGetPendingEvaluationForProduct() {
        assertTrue(evaluator1.getPendingEvaluationForProduct(testProduct1) != null);

        assertFalse(evaluator2.getPendingEvaluationForProduct(testProduct1) != null);
        assertFalse(evaluator2.getPendingEvaluationForProduct(testProduct2) != null);
        
        assertFalse(evaluator3.getPendingEvaluationForProduct(testProduct1) != null);
        assertFalse(evaluator3.getPendingEvaluationForProduct(testProduct2) != null);
        assertTrue(evaluator3.getPendingEvaluationForProduct(testProduct3) != null);
    }
    
    @Test
    public void testHasPendingEvaluationForGroup() {
        assertTrue(evaluator1.hasPendingEvaluationForGroup(eg1));
        assertFalse(evaluator2.hasPendingEvaluationForGroup(eg2));
        
        assertFalse(evaluator2.hasPendingEvaluationForGroup(eg1));
        assertFalse(evaluator2.hasPendingEvaluationForGroup(eg2));
        
        assertTrue(evaluator3.hasPendingEvaluationForGroup(eg2));
        assertFalse(evaluator3.hasPendingEvaluationForGroup(eg1));
    }

}
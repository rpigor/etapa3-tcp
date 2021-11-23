package app.business.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class EvaluatorTest {

    private Evaluator evaluator1, evaluator2, evaluator3;
    private Product testProduct1, testProduct2, testProduct3;
    private EvaluationGroup eg1, eg2;

    @Before
    public void setUp() throws Exception {
        evaluator1 = new Evaluator(1, "Mike", "SP",
                new LinkedList<Category>(Arrays.asList()));
        evaluator2 = new Evaluator(2, "Nicolas", "RS", 
                new LinkedList<Category>(Arrays.asList(Category.DD_CREAM, Category.POWDER_SUNSCREEN)));
        evaluator3 = new Evaluator(3, "Joe", "SP", 
                new LinkedList<Category>(Arrays.asList(Category.BB_CREAM)));
        
        eg1 = new EvaluationGroup(null, new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator2)));
        eg2 = new EvaluationGroup(null, new LinkedList<Evaluator>(Arrays.asList(evaluator1, evaluator2)));
        
        testProduct1 = new Product(1, "Produto", null, eg1, Category.DD_CREAM);
        testProduct2 = new Product(2, null, evaluator1, eg1, Category.OIL_FREE_MATTE_SPF);
        testProduct3 = new Product(3, null, evaluator3, eg2, Category.BB_CREAM);
        
        evaluator1.allowProduct(testProduct1);
        evaluator1.allowProduct(testProduct2).rate(3);
        
        evaluator2.allowProduct(testProduct1).rate(-1);
        evaluator2.allowProduct(testProduct2).rate(-2);
        evaluator2.allowProduct(testProduct3).rate(0);
        
        evaluator3.allowProduct(testProduct1).rate(3);
        evaluator3.allowProduct(testProduct2).rate(0);
        evaluator3.allowProduct(testProduct3);
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
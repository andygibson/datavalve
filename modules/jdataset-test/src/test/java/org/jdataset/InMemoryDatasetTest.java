package org.jdataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jdataset.dataset.GenericProviderDataset;
import org.jdataset.dataset.ObjectDataset;
import org.jdataset.impl.InMemoryDataProvider;
import org.jdataset.testing.junit.AbstractObjectDatasetJUnitTest;

public class InMemoryDatasetTest extends AbstractObjectDatasetJUnitTest<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger(InMemoryDatasetTest.class.getName());
    
    

    public ObjectDataset<Integer> buildTestDataset() {
    	
    	return buildTestDataset(100);
    }
    
    public ObjectDataset<Integer> buildTestDataset(final int count) {
    	
        InMemoryDataProvider<Integer> ds = new InMemoryDataProvider<Integer>() {

        	private static final long serialVersionUID = 1L;
        	
            @Override
            protected List<Integer> fetchBackingData() {
                List<Integer> res = new ArrayList<Integer>();
                for (int i = 0; i < count; i++) {
                    res.add(i);
                }
                return res;
            }
        };
        
        return new GenericProviderDataset<Integer, InMemoryDataProvider<Integer>>(ds);
    }

    public void testGetResultCount() {
    	ObjectDataset<Integer> ds = buildTestDataset();
        int res = ds.getResultCount();
        assertEquals(100, res);
    }

    public void testGetResultsUnbound() {
    	ObjectDataset<Integer> ds = buildTestDataset();
        List<Integer> res = ds.getResultList();
        
        assertNotNull(res);
        assertEquals(100, res.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, res.get(i).intValue());
        }
    }

    public void testGetResultsBound() {
    	ObjectDataset<Integer> ds = buildTestDataset();
        ds.setMaxRows(12);
        List<Integer> res = ds.getResultList();

        assertNotNull(res);
        assertEquals(12, res.size());
        for (int i = 0; i < 12; i++) {
            assertEquals(i, res.get(i).intValue());
        }
    }

    public void testLast() {
    	ObjectDataset<Integer> ds = buildTestDataset();
        ds.setMaxRows(10);
        ds.last();        
        assertEquals(10, ds.getPageCount());
        int firstResult = ds.getFirstResult();
        log.fine("First result = "+firstResult);        
        assertEquals(90, firstResult);
    }
    
    public void testLastWithFractionalCount() {
    	ObjectDataset<Integer> ds = buildTestDataset(95);
        ds.setMaxRows(10);
        ds.last();
        Integer firstResult = ds.getFirstResult();        
        assertNotNull(firstResult);
        assertEquals(90, firstResult.longValue());
    }
    
    public void testLastWithNoPaging() {
    	ObjectDataset<Integer> ds = buildTestDataset(5);
        ds.setMaxRows(10);
        ds.last();
        int firstResult = ds.getFirstResult();
        log.fine("First result = "+firstResult);        
        assertEquals(0, firstResult);
    }
    
    public void testPaginationPreviousAllResults() {
    	ObjectDataset<Integer> ds = buildTestDataset(100);
        assertEquals(false, ds.isPreviousAvailable());
    }
    
    public void testPaginationNextAllResults() {
    	ObjectDataset<Integer> ds = buildTestDataset(100);
        assertEquals(false, ds.isNextAvailable());
    }
    
    public void testPaginationPreviousPaged() {
    	ObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        assertEquals(false, ds.isPreviousAvailable());
    }
    
    public void testPaginationNextPaged() {
    	ObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        assertEquals(true, ds.isNextAvailable());
    }
    
    public void testPaginationNextNoRead() {
    	ObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        ds.next();
        assertEquals(true, ds.isNextAvailable());
        assertEquals(true, ds.isPreviousAvailable());
        
    }
    
    public void testPaginationPreviousNoRead() {
        ObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        ds.previous();
        assertEquals(0,ds.getFirstResult());
        assertEquals(1, ds.getPage());
        assertEquals(true, ds.isNextAvailable());
        assertEquals(false, ds.isPreviousAvailable());        
    }
    
    public void testPaginationLastNoRead() {
    	ObjectDataset<Integer> ds = buildTestDataset(100);
        ds.setMaxRows(10);
        ds.last();
        assertEquals(false, ds.isNextAvailable());
        assertEquals(true, ds.isPreviousAvailable());        
    }
    
    public void testSmalldataset() {
    	ObjectDataset<Integer> ds = buildTestDataset(6);
        ds.setMaxRows(10);        
        assertEquals(false, ds.isNextAvailable());
        assertEquals(false, ds.isPreviousAvailable());
        ds.next();
        assertEquals(6, ds.getResultCount().intValue());
        assertEquals(false, ds.isNextAvailable());
        assertEquals(false, ds.isPreviousAvailable());
        
    }

	@Override
	public ObjectDataset<Integer> buildObjectDataset() {
		return buildTestDataset(100);
	}

	@Override
	public int getDataRowCount() {
		return 100;
	}
    
    
}

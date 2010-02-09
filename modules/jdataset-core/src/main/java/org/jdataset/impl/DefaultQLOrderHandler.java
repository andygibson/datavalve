package org.jdataset.impl;

import java.util.regex.Pattern;

import org.jdataset.Paginator;
import org.jdataset.provider.QueryDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultQLOrderHandler extends DefaultOrderHandler<String> {

	private static Pattern commaSplitter = Pattern.compile(",");
	
	private static Logger log = LoggerFactory
	.getLogger(DefaultQLOrderHandler.class);

	
	/**
	 * Determines the order by clause based on the values of
	 * {@link ObjectDataset#getOrderKey()},
	 * {@link QueryDataProvider#getOrderKeyMap()} and
	 * {@link QueryDataProvider#isOrderAscending()}. If no order is specified
	 * then <code>null</code> is returned.
	 * 
	 * @see #calculateOrderByClause()
	 * 
	 * @return The order by fields with asc/desc indicators or null of there is
	 *         no order by specified.
	 */
	public final String calculateOrderBy(Paginator paginator) {
		String orderKey = paginator.getOrderKey();

		// fail quickly if we don't have an order
		if (orderKey == null || orderKey.length() == 0) {
			return null;
		}

		// we specified an order key, but we have no values, so issue a warning
		// to the user
		if (isEmpty()) {
			log.warn("orderKey property is set but the orderKeyMap is empty.");
			return null;
		}

		String order = translateOrderKey(orderKey);
		if (order == null) {
			// if we can't find the order, then warn the user
			log.warn("orderKey value '{}' not translated successfully",
					orderKey);
			return null;
		}

		// parse out fields and add order
		String[] fields = commaSplitter.split(order);
		order = "";
		// concatenate the fields with the direction, put commas in between
		for (String field : fields) {
			if (order.length() != 0) {
				order = order + ", ";
			}
			order = order + field
					+ (paginator.isOrderAscending() ? " ASC " : " DESC ");
		}
		log.debug("Order key = {}, order by = {}", paginator.getOrderKey(),
				order);
		return order;
	}
	
	protected String translateOrderKey(String key) {
		return getOrderValue(key);
	}
}

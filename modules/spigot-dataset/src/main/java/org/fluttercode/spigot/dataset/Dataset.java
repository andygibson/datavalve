/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fluttercode.spigot.dataset;

import java.util.List;

import org.fluttercode.spigot.DataProvider;
import org.fluttercode.spigot.Paginator;

/**
 * A Generic version of the provider dataset that extends the
 * {@link AbstractDataset} and holds a typed {@link DataProvider} reference
 * which can be accessed from the client.
 * 
 * <pre>
 * MyCustomProvider&lt;Person&gt; provider = new MyCustomProvider&lt;Person&gt;();
 * ds = new GenericDataset&lt;Person, MyCustomProvider&gt;();
 * ds.getProvider().someCustomMethod();
 * </pre>
 * 
 * We have 'helper' types for this with specific implementations for
 * parameterized,statement,and query provider types. These are currently
 * implemented as {@link ParameterizedDataset}, {@link StatementDataset}, and
 * {@link QueryDataset}.
 * <p/>
 * If you want a straight {@link ObjectDataset} implementation without a typed
 * provider, just use the {@link DataProviderDataset} which accepts any
 * {@link DataProvider} implementation.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            The type of object this dataset will end up returning
 * @param <P>
 *            The type of data provider used to fetch the data
 */
public class Dataset<T, P extends DataProvider<T>> extends AbstractDataset<T> {

	private static final long serialVersionUID = 1L;

	private P provider;

	public Dataset() {
		super();
	}

	public Dataset(P provider) {
		super();
		this.provider = provider;
	}

	public Integer loadResultCount() {
		return provider.fetchResultCount();
	}

	@Override
	protected List<T> loadResults(Paginator paginator) {

		return provider.fetchResults(paginator);
	}

	public List<T> fetchResults(Paginator paginator) {
		return provider.fetchResults(paginator);
	}

	public P getProvider() {
		return provider;
	}
}

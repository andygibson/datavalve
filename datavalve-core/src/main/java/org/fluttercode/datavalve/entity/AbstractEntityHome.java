package org.fluttercode.datavalve.entity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.fluttercode.datavalve.EntityHome;

public abstract class AbstractEntityHome<T> implements EntityHome<T> {

	private T entity;
	private Object id;
	private boolean managed;
	private Class<T> entityClass;

	public T getEntity() {
		if (entity == null) {
			entity = fetchEntity();
		}
		return entity;
	}

	/**
	 * Fetches the entity by loading a new one or creating one
	 * 
	 * @return Fetched entity
	 */
	private T fetchEntity() {
		if (isIdSet()) {
			return internalLoadEntity();
		} else {
			return internalCreateEntity();
		}
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;

	}

	/**
	 * Internal method used to create the entity. Do not override, override the
	 * {@link AbstractEntityHome#doCreateEntity()} method to implement entity
	 * creation. Override the <code>doPreCreateEntity</code> and
	 * <code>doPostCreateEntity</code> methods to add additional processing when
	 * creating an entity.
	 * 
	 * @return The loaded entity
	 */
	protected T internalCreateEntity() {
		doPreCreateEntity();
		T result = doCreateEntity();
		managed = false;
		doPostCreateEntity(result);
		return result;
	}

	/**
	 * Internal method used to load the entity. Do not override, override the
	 * {@link AbstractEntityHome#doLoadEntity()} method to implement entity
	 * loading. Override the <code>doPreLoadEntity</code> and
	 * <code>doPostLoadEntity</code> methods to add additional processing when
	 * loading an entity.
	 * 
	 * @return The loaded entity
	 */
	protected T internalLoadEntity() {
		doPreLoadEntity();
		T result = doLoadEntity();
		managed = true;
		doPostLoadEntity(result);
		return result;
	}

	/**
	 * Override this method to provide additional processing prior to creating
	 * an entity.
	 */
	protected void doPreCreateEntity() {
		// do nothing
	}

	/**
	 * Override this method to construct the object
	 * 
	 * @return Newly created instance of T
	 */
	protected T doCreateEntity() {
		Class<T> type = getEntityClass();
		T entity = null;
		try {
			entity = type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	/**
	 * Override this method to provide additional handling post to the newly
	 * created entity
	 * 
	 * @param entity
	 *            The newly created entity
	 */
	protected void doPostCreateEntity(T entity) {
		// do nothing
	}

	/**
	 * Override to add additional processing prior to loading an entity
	 */
	protected void doPreLoadEntity() {
		// do nothing
	}

	/**
	 * Override to implement entity loading for the specific data source
	 * 
	 * @return The loaded entity
	 */
	protected abstract T doLoadEntity();

	/**
	 * Override this method to add additional handling to set up the loaded
	 * entity.
	 * 
	 * @param entity
	 *            The entity loaded
	 */
	protected void doPostLoadEntity(T entity) {
		// do nothing
	}

	public void insert() {
		doPreInsert();
		doInsert();
		managed = true;
		internalPostInsert();
		doPostInsert();
	}

	/**
	 * Override to add addition handling once the entity has been inserted
	 */
	protected void doPostInsert() {
		// do nothing
	}

	/**
	 * Used internally for post-insert processing, to add your own addition
	 * handling, override the {@link AbstractEntityHome#doPostInsert()} method.
	 */
	protected void internalPostInsert() {
		// do nothing
	}

	/**
	 * Perform the actual insert operation
	 */
	protected abstract void doInsert();

	/**
	 * Override this method to add additional pre insert handling on the entity.
	 */
	protected void doPreInsert() {
		// Do Nothing

	}

	public void cancel() {
		if (isManaged()) {
			doPreCancel();
			doCancel();
			internalPostCancel();
			doPostCancel();
		} else {
			// since this isn't managed, just create a new one
			setEntity(internalCreateEntity());
		}
	}

	/**
	 * Override to provide additional handling prior to cancelling changes to
	 * the entity.
	 */
	protected void doPreCancel() {
		// do nothing
	}

	/**
	 * Implements the actual entity change cancellation
	 */
	protected abstract void doCancel();

	/**
	 * Internal method to provide additional post cancellation handling.
	 */
	protected void internalPostCancel() {
		// do nothing
	}

	/**
	 * Override this method to provide additional handling post cancellation
	 */
	protected void doPostCancel() {
		// do nothing
	}

	public void update() {
		doPreUpdate();
		doUpdate();
		internalPostUpdate();
		doPostUpdate();
	}

	/**
	 * Override to provide additional handling prior to updating the entity.
	 */
	protected void doPreUpdate() {
		// do nothing
	}

	/**
	 * Perform the actual update on the entity instance
	 */
	protected void doUpdate() {
		// do nothing
	}

	/**
	 * Internal method to provide additional post update processing. Do not
	 * override on the client, override the
	 * {@link AbstractEntityHome#doPostUpdate()} method instead to add your own
	 * post update handling.
	 */
	protected void internalPostUpdate() {
		// do nothing
	}

	/**
	 * Override to provide additional handling after updating the entity.
	 */
	protected void doPostUpdate() {
		// do nothing
	}

	public boolean isIdSet() {
		return id != null;
	}

	public boolean isManaged() {
		return managed;
	}

	public Class<T> getEntityClass() {
		if (entityClass == null) {
			ParameterizedType parameterizedType = (ParameterizedType) getClass()
					.getGenericSuperclass();

			Object value = parameterizedType.getActualTypeArguments()[0];
			if (value instanceof Class) {
				return (Class<T>) value;
			} else {
				throw new IllegalArgumentException(
						"Unable to extract generic type information, please set manually using setEntityClass()");
			}
		}
		return entityClass;
	}

}

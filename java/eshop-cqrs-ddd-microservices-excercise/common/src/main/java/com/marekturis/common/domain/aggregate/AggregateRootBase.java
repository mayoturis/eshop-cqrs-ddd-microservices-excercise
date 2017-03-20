package com.marekturis.common.domain.aggregate;

import com.marekturis.common.domain.event.AggregateEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class AggregateRootBase implements AggregateRoot {

	private static final long serialVersionUID = 94894654687986465l;

	private List<AggregateEvent> changes = new ArrayList<>();

	private Integer identity;

	private int currentVersion;

	private int versionWhenLoaded;

	public AggregateRootBase(Integer identity) {
		this(identity, 0);
	}

	public AggregateRootBase(Integer identity, int version) {
		this.identity = identity;
		currentVersion = version;
		versionWhenLoaded = version;
	}

	@Override
	public Integer identity() {
		return this.identity;
	}

	@Override
	public void replayEvent(AggregateEvent event) {
		applyEvent(event);
	}

	@Override
	public List<AggregateEvent> pullChanges() {
	 	List<AggregateEvent> changes = this.changes;
		this.changes = new ArrayList<>();
		return changes;
	}

	@Override
	public int currentVersion() {
		return currentVersion;
	}

	@Override
	public int versionWhenLoaded() {
		return versionWhenLoaded;
	}

	@Override
	public void setVersions(int version) {
		versionWhenLoaded = version;
		currentVersion = version;
	}

	protected void fire(AggregateEvent event) {
		changes.add(event);
		currentVersion++;
		event.setAggregateIdentity(identity);
		event.setAggregateVersion(currentVersion);
		applyEvent(event);
	}

	private void applyEvent(AggregateEvent event) {
		try {
			Method method = this.getClass().getDeclaredMethod("apply", event.getClass());
			method.setAccessible(true);
			method.invoke(this, event);
		} catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException exception) {
			throw new UnappliableEventException("Event '" + event.getClass().getName() + "' " +
					"cannot be applied to aggregate '" + this.getClass().getName() + "'", exception);
		}
	}
}

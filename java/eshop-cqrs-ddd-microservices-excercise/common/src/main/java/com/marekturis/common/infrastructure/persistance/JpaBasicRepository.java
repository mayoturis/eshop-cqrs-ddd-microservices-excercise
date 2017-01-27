package com.marekturis.common.infrastructure.persistance;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.repository.BasicRepository;

/**
 * @author Marek Turis
 */
abstract public class JpaBasicRepository<TAggregateRoot extends AggregateRoot> implements BasicRepository<TAggregateRoot> {

	abstract protected String aggregateClass();

	@Override
	public TAggregateRoot getById(Integer id) {
		return null;
	}

	@Override
	public void add(TAggregateRoot aggregateRoot) {

	}

	private class EventRow {
		private Integer aggregateId;
		private String data;
		private int version;

		public EventRow(Integer aggregateId, String data, int version) {
			this.aggregateId = aggregateId;
			this.data = data;
			this.version = version;
		}

		public EventRow() {
		}

		public Integer getAggregateId() {
			return aggregateId;
		}

		public void setAggregateId(Integer aggregateId) {
			this.aggregateId = aggregateId;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
	}

	private class AggregateRow {
		private Integer aggregateId;
		private String type;
		private int version;

		public AggregateRow(Integer aggregateId, String type, int version) {
			this.aggregateId = aggregateId;
			this.type = type;
			this.version = version;
		}

		public AggregateRow() {

		}

		public Integer getAggregateId() {
			return aggregateId;
		}

		public void setAggregateId(Integer aggregateId) {
			this.aggregateId = aggregateId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
	}

	// todo snapshots
}

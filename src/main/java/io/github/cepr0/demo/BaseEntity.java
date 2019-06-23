package io.github.cepr0.demo;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Version;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Long version;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{id=" + getId() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!getClass().isInstance(o)) return false;
		return getId() != null && getId().equals(((BaseEntity) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}

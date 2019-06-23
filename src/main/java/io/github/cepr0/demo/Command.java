package io.github.cepr0.demo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NodeEntity
public class Command extends BaseEntity {

	@Relationship(type = "PARENT")
	private Command parent;

	private int order;

	@NotBlank private String value;
	@NotBlank private String response;

	private boolean isError;

	public String name() {
		return value.trim().split("\\s")[0];
	}
}

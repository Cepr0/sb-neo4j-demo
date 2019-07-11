package io.github.cepr0.demo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

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

	@Builder.Default
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdOn = LocalDate.now();

	public String name() {
		return value.trim().split("\\s")[0];
	}
}

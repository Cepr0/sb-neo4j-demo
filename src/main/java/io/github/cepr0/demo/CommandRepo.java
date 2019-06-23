package io.github.cepr0.demo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CommandRepo extends Neo4jRepository<Command, Long> {

	@Query("match (c:Command) where not (c)-[:PARENT]->() return c order by c.order")
	List<Command> findRootCommands();

	@Query("match (p:Command)<-[:PARENT]-(c:Command) where id(p) = {parentId} return c order by c.order")
	List<Command> findByParentId(Long parentId);
}

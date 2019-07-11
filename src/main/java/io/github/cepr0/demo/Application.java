package io.github.cepr0.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

@Slf4j
@EnableTransactionManagement
@EnableNeo4jRepositories
@SpringBootApplication
public class Application {

	private final CommandRepo commandRepo;

	public Application(CommandRepo commandRepo) {
		this.commandRepo = commandRepo;
	}

	@Bean
	public FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean() {
		FormattingConversionServiceFactoryBean service = new FormattingConversionServiceFactoryBean();
		Set<Formatter> formatters = new HashSet<>();
		formatters.add(new LocalDateFormatter());
		service.setFormatters(formatters);
		return service;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Order(1)
	@EventListener
	public void populate(ApplicationReadyEvent e) {
		commandRepo.deleteAll();
		log.info("[i] Nodes count: {}", commandRepo.count());

		Command root = commandRepo.save(Command.builder().value("configure").response("Ok").order(1).build());

		Iterable<Command> rootCommands = commandRepo.saveAll(asList(
				Command.builder().parent(root).value("command1").response("Ok").order(1).build(),
				Command.builder().parent(root).value("command2").response("Ok").order(2).build(),
				Command.builder().parent(root).value("command3").response("Ok").order(3).build(),
				Command.builder().parent(root).value("command4").response("Ok").order(4).build()
		));

		List<Command> commands = StreamSupport.stream(rootCommands.spliterator(), false)
				.sorted(comparing(Command::getOrder))
				.collect(Collectors.toList());

		Command command1 = commands.get(0);
		commandRepo.saveAll(asList(
						Command.builder().parent(command1).value("command5").response("Ok").order(1).build(),
						Command.builder().parent(command1).value("command6").response("Ok").order(2).build(),
						Command.builder().parent(command1).value("command7").response("Ok").order(3).build(),
						Command.builder().parent(command1).value("command8").response("Ok").order(4).build()
				));

		List<Command> roots = commandRepo.findRootCommands();
		roots.forEach(command -> log.info("[i] Root command: {}", command.name()));

		Iterable<Command> rootSequence = commandRepo.findByParentId(root.getId());
		rootSequence.forEach(command -> log.info("[i] Root's command: {}", command.name()));

		Iterable<Command> command1Sequence = commandRepo.findByParentId(command1.getId());
		rootSequence.forEach(command -> log.info("[i] Command1's command: {}", command.name()));
	}
}

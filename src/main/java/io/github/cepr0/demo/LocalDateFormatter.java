package io.github.cepr0.demo;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// @Component
public class LocalDateFormatter implements Formatter<LocalDate> {
	@Override
	public LocalDate parse(final String text, final Locale locale) {
		return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Override
	public String print(final LocalDate object, final Locale locale) {
		return DateTimeFormatter.ISO_LOCAL_DATE.format(object);
	}
}

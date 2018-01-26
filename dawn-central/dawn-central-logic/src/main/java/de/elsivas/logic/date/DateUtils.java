package de.elsivas.logic.date;

import java.time.LocalDate;

public class DateUtils {

	public static long minus(final LocalDate a, final LocalDate b) {
		return Math.abs(a.toEpochDay() - b.toEpochDay());
	}

}

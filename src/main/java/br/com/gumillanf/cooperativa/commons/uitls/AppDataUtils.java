package br.com.gumillanf.cooperativa.commons.uitls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public abstract class AppDataUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static final DateFormat[] DATE_FORMAT = Stream.of("dd/MM/yyyy HH:mm", "dd/MM/yyyy", "yyyy-MM-dd HH:mm:ss",
            "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd", "DDMMYYYY").map(SimpleDateFormat::new)
            .toArray(SimpleDateFormat[]::new);

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, "dd/MM/yyyy");
    }

    public static Date parseDate(final String value) {
        for (DateFormat formatter : DATE_FORMAT) {
            try {
                return formatter.parse(value);
            }
            catch (Exception dtpe) {
                // ignore, try next format
            }
        }
        throw new IllegalArgumentException("Could not parse " + value);
    }

    public static Date truncateDate(final Date date) {
        return parseDate(formatDate(date));
    }

}

package pl.praktycznajava.module3.valueobjects.challenge1;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateOfBirth {

    public static final int ADULT_AGE = 18;

    LocalDate date;

    public boolean isToday() {
        LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        return currentDate.getMonth() == date.getMonth() && currentDate.getDayOfMonth() == date.getDayOfMonth();
    }

    public boolean isAdult() {
        LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        return currentDate.getYear() - date.getYear() > ADULT_AGE;
    }
}

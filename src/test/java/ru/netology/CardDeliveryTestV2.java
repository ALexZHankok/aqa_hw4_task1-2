package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTestV2 {

    private String generateDate(int plusDays) {
        LocalDate today = LocalDate.now();
        LocalDate afterQuantityDays = today.plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formatDate = formatter.format(afterQuantityDays);
        return formatDate;
    }

    @Test
    public void shouldBePositiveV2() {
        open("http://localhost:9999");
        String city = "Санкт-Петербург";
        int dayToAdd = 7;
        int defaultAddedDays = 3;
        $("[data-test-id='city']input").setValue(city.substring(0, 2));
        $$(".menu-item__control").findBy(text(city)).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").click();
        if (!generateDate(defaultAddedDays).equals(generateDate(dayToAdd))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(generateDate(dayToAdd))).click();
        $("[data-test-id='name']input").setValue("Алексей Зверев");
        $("[data-test-id='phone']input").setValue("+79119294414");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + generateDate(dayToAdd)));
    }
}
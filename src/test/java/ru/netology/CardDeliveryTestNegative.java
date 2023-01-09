package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTestNegative {
    private String getDate(int plusDays) {
        LocalDate today = LocalDate.now();
        LocalDate afterQuantityDays = today.plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formatDate = formatter.format(afterQuantityDays);
        return formatDate;
    }

    @Test
    void shouldBeNegative() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(getDate(3));
        $("[data-test-id=phone] input").setValue("+79119294414");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
}

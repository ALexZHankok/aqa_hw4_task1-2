package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardPositiveTests {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompletedOne(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Алексей Зверев");
        $("[data-test-id='phone'] input").setValue("+79119294414");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldBeSuccessfullyCompletedTwo(){
        open("http://localhost:9999");
        String city = "Санкт-Петербург";
        int dayToAdd = 7;
        int defaultAddDays = 3;
        $("[data-test-id='city'] input").setValue(city.substring(0, 2));
        $$(".menu-item__control").findBy(text(city)).click();
        $("[data-test-id='date'] input").click();
        if (!generateDate(defaultAddDays, "MM").equals(generateDate(dayToAdd, "MM"))){
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(generateDate(dayToAdd, "d"))).click();
        $("[data-test-id='name'] input").setValue("Зверев Алексей");
        $("[data-test-id='phone'] input").setValue("+79119294414");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + generateDate(dayToAdd, "dd.MM.yyyy")));
    }
}
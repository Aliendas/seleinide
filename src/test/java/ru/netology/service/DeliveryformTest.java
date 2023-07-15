package ru.netology.service;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryformTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @BeforeEach
    public void beforeEach() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestFormPositive() {
        $("[data-test-id=city] input").setValue("Мос");
        $$("menu-item").find(text("Москва")).click();
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("button.icon-button").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Иванова Маша");
        $("[data-test-id=phone] input").setValue("+79264464646");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

}





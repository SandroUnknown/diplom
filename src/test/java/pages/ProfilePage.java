package pages;

import api.BooksApi;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.books.BookModel;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfilePage {

    private final SelenideElement
            firstBookSelector = $$(".rt-tr-group").first(),
            deleteConfirmSelector = $("#closeSmallModal-ok");

    @Step("Открыть страницу с профилем.")
    public ProfilePage openPage() {

        open("/profile");

        return this;
    }

    @Step("Удалить книгу.")
    public ProfilePage deleteBook(String isbn) {

        firstBookSelector.$("a[href='/profile?book=" + isbn + "']").shouldBe(exist);
        firstBookSelector.$("#delete-record-undefined").click();
        deleteConfirmSelector.click();

        return this;
    }

    @Step("Проверить результат на UI-слое.")
    public ProfilePage checkResultOnUi(String isbn) {

        firstBookSelector.$("a[href='/profile?book=" + isbn + "']").shouldNot(exist);

        return this;
    }

    @Step("Проверить результат на API-слое.")
    public void checkResultOnApi() {

        BooksApi booksApi = new BooksApi();
        List<BookModel> books = booksApi.getUserBooks();

        assertTrue(books.isEmpty());
    }
}
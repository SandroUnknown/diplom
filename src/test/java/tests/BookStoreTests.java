package tests;

import api.BooksApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static io.qameta.allure.Allure.step;

@DisplayName("Тесты для Book Store")
public class BookStoreTests extends TestBase {

    @DisplayName("Удалить книгу (UI)")
    @Test
    @WithLogin
    void deleteBook() {

        BooksApi booksApi = new BooksApi();

        step("Очистить всю корзину (через API).", () -> {
                    booksApi.deleteAllBooks();
        });

        String isbn = step("Получить случайный ISBN книги.", () ->
                booksApi.getRandomIsbn());

        step("Добавить книгу в корзину (через API).", () -> {
                    booksApi.addBook(isbn);
        });

        step("Удаляем книгу из корзины (через UI).", () -> {
            ProfilePage profilePage = new ProfilePage();
            profilePage
                    .openPage()
                    .deleteBook(isbn)
                    .checkResultOnUi(isbn)
                    .checkResultOnApi();
        });
    }
}
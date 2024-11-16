package tests;

import api.BooksApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

@DisplayName("Тесты для Book Store")
public class BookStoreTests extends TestBase {

    @DisplayName("Удалить книгу (UI)")
    @Test
    @WithLogin
    void deleteBook() {

        BooksApi booksApi = new BooksApi();
        booksApi.deleteAllBooks();

        String isbn = booksApi.getRandomIsbn();
        booksApi.addBook(isbn);

        ProfilePage profilePage = new ProfilePage();
        profilePage
                .openPage()
                .deleteBook(isbn)
                .checkResultOnUi(isbn);
        booksApi.checkResultOnApi();

    }
}
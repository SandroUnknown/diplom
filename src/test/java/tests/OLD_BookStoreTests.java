package tests;

import api.OLD_BooksApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static data.AuthData.USER_NAME;

@DisplayName("Тесты для Book Store")
public class OLD_BookStoreTests extends OLD_TestBaseForDemoqa {

    @DisplayName("Удалить книгу (UI)")
    @Test
    @WithLogin
    void deleteBook() {

        OLD_BooksApi booksApi = new OLD_BooksApi();
        booksApi.deleteAllBooks();

        String isbn = booksApi.getRandomIsbn();
        booksApi.addBook(isbn);

        ProfilePage profilePage = new ProfilePage();
        profilePage
                .openPage()
                .checkAuthData(USER_NAME)
                .checkBookInProfile(isbn)
                .deleteBook(isbn)
                .checkResultOnUi(isbn);

        booksApi.checkResultOnApi();
    }
}
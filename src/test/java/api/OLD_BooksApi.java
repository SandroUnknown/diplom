package api;

import io.qameta.allure.Step;
import models.OLD_books.*;

import java.util.List;
import java.util.Random;

import static data.AuthData.USER_ID;
import static data.AuthData.USER_TOKEN;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specs.Specification.*;

public class OLD_BooksApi {

    @Step("Удалить все книги из корзины.")
    public OLD_BooksApi deleteAllBooks() {

        given(OLD_requestSpec)
                .header("Authorization", "Bearer " + USER_TOKEN)
                .queryParam("UserId", USER_ID)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec204);

        return this;
    }

    @Step("Добавить книгу в корзину.")
    public OLD_BooksApi addBook(String isbn) {

        IsbnModel book = new IsbnModel();
        book.setIsbn(isbn);

        AddBooksCollectionRequestModel booksCollection = new AddBooksCollectionRequestModel();
        booksCollection.setUserId(USER_ID);
        booksCollection.setCollectionOfIsbns(List.of(book));

        given(OLD_requestSpec)
                .header("Authorization", "Bearer " + USER_TOKEN)
                .body(booksCollection)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec201);
        return this;
    }

    @Step("Получить список всех книг пользователя.")
    public List<BookModel> getUserBooks() {

        AllBooksFromProfileResponseModel response =
                given(OLD_requestSpec)
                        .when()
                        .header("Authorization", "Bearer " + USER_TOKEN)
                        .get("/Account/v1/User/" + USER_ID)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(AllBooksFromProfileResponseModel.class);

        return response.getBooks();
    }

    @Step("Получить список всех книг магазина.")
    public AllBooksFromStoreResponseModel getAllBooks() {

        return given(OLD_requestSpec)
                .when()
                .header("Authorization", "Bearer " + USER_TOKEN)
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec200)
                .extract().as(AllBooksFromStoreResponseModel.class);
    }

    @Step("Получить случайный ISBN.")
    public String getRandomIsbn() {

        AllBooksFromStoreResponseModel booksFromStore = getAllBooks();

        Random random = new Random();
        int randomIndex = random.nextInt(booksFromStore.getBooks().size());

        return booksFromStore.getBooks().get(randomIndex).getIsbn();
    }

    @Step("Проверить, что в корзине пусто (API).")
    public void checkResultOnApi() {

        OLD_BooksApi booksApi = new OLD_BooksApi();
        List<BookModel> books = booksApi.getUserBooks();

        assertTrue(books.isEmpty());
    }
}
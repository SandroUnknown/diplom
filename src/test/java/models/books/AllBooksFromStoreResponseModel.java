package models.books;

import lombok.Data;

import java.util.List;

@Data
public class AllBooksFromStoreResponseModel {
    List<BookFromStoreResponseModel> books;

    @Data
    public static class BookFromStoreResponseModel {
        String isbn, title, subTitle, author, publish_date, publisher, description, website;
        Integer pages;
    }
}
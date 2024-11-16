package models.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllBooksFromStoreResponseModel {
    private List<BookFromStoreResponseModel> books;

    @Data
    public static class BookFromStoreResponseModel {

        private String isbn, title, subTitle, author, publisher, description, website;

        @JsonProperty("publish_date")
        private String publishDate;

        private Integer pages;
    }
}
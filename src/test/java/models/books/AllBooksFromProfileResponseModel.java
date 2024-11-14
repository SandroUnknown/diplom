package models.books;

import lombok.Data;

import java.util.List;

@Data
public class AllBooksFromProfileResponseModel {
    String userId, username;
    List<BookModel> books;
}
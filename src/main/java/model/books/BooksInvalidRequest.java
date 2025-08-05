package model.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BooksInvalidRequest {

    private Object id;
    private Object title;
    private Object description;
    private Object pageCount;
    private Object excerpt;
    private Object publishDate;
}

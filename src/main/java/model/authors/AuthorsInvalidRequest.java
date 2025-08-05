package model.authors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorsInvalidRequest {

    private Object id;
    private Object idBook;
    private Object firstName;
    private Object lastName;
}

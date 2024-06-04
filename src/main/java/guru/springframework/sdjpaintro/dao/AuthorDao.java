package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;

public interface AuthorDao {

    Author getById(Long id);

    Author findAuthorByName(String firstName, String LastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);
    
    void deleteAuthorById(Long id);

}

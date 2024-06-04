package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(Long id);

    Author findAuthorByName(String firstName, String LastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> listAuthorByLastNameLike(String lastName);

    Long countAuthorsWithGivenLastname(String lastName);

    Author findAuthorByNameCriteria(String firstName, String LastName);
}

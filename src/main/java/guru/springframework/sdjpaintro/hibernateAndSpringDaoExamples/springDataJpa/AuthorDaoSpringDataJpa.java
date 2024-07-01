package guru.springframework.sdjpaintro.hibernateAndSpringDaoExamples.springDataJpa;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.Author;
import guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository.AuthorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
public class AuthorDaoSpringDataJpa {

    private final AuthorJpaRepository repository;

    @Autowired
    public AuthorDaoSpringDataJpa(AuthorJpaRepository repository) {
        this.repository = repository;
    }

    public Author getById(Long id) {
        return repository.getById(id);
    }

    public Author findAuthorByName(String firstName, String lastName) {
        return repository.findAuthorByFirstNameAndLastName(firstName, lastName);
    }

    public Author saveNewAuthor(Author author) {
        return repository.save(author);
    }

    public Author updateAuthor(Author author) {
        Author fetched = repository.getById(author.getId());
        fetched.setFirstName(author.getFirstName());
        fetched.setLastName(author.getLastName());
        return repository.save(fetched);
    }

    public void deleteAuthorById(Long id) {
        repository.deleteById(id);
    }

    public List<Author> listAuthorByLastNameLike(String lastName) {
        return repository.listAuthorByLastNameLike(lastName);
    }

    public Future<List<Author>> listAuthorByFirstNameLike(String firstName) {
        return repository.listAuthorByFirstNameLike(firstName);
    }

    public Long countAuthorsWithGivenLastname(String lastName) {
        return repository.countAuthorsWithGivenLastname(lastName);
    }

    public Author findAuthorByNameCriteria(String firstName, String LastName) {
        return null;
    }
}

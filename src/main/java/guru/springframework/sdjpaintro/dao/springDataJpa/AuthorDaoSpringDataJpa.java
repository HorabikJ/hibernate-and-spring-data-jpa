package guru.springframework.sdjpaintro.dao.springDataJpa;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.repositories.AuthorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
public class AuthorDaoSpringDataJpa implements AuthorDao {

    private final AuthorJpaRepository repository;

    @Autowired
    public AuthorDaoSpringDataJpa(AuthorJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return repository.findAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return repository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        Author fetched = repository.getById(author.getId());
        fetched.setFirstName(author.getFirstName());
        fetched.setLastName(author.getLastName());
        return repository.save(fetched);
    }

    @Override
    public void deleteAuthorById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        return repository.listAuthorByLastNameLike(lastName);
    }

    public Future<List<Author>> listAuthorByFirstNameLike(String firstName) {
        return repository.listAuthorByFirstNameLike(firstName);
    }

    @Override
    public Long countAuthorsWithGivenLastname(String lastName) {
        return repository.countAuthorsWithGivenLastname(lastName);
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String LastName) {
        return null;
    }
}

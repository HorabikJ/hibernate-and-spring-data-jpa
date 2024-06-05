package guru.springframework.sdjpaintro.dao.hibernate;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AuthorDaoHibernate.class})
class AuthorDaoHibernateTest {

    private final AuthorDaoHibernate authorDao;

    @Autowired
    public AuthorDaoHibernateTest(AuthorDaoHibernate authorDao) {
        this.authorDao = authorDao;
    }

    @Test
    void getById() {
        Author author = new Author();
        author.setFirstName("AuthorFirstName-1");
        author.setLastName("AuthorLastName-1");

        Author savedAuthor = authorDao.saveNewAuthor(author);

        Author fetchedAuthor = authorDao.getById(savedAuthor.getId());

        assertThat(fetchedAuthor).isNotNull();
    }

    @Test
    void findAuthorByName() {
        String firstName = "AuthorFirstName-2";
        String lastName = "AuthorLastName-2";

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        authorDao.saveNewAuthor(author);

        Author authorByName = authorDao.findAuthorByName(firstName, lastName);
        assertThat(authorByName).isNotNull();
        assertThat(authorByName.getFirstName()).isEqualTo(firstName);
        assertThat(authorByName.getLastName()).isEqualTo(lastName);
    }

    @Test
    void saveNewAuthor() {
        Author author = new Author();
        author.setFirstName("NewAuthorFirstName-3");
        author.setLastName("NewAuthorLastName-3");
        Author persistedAuthor = authorDao.saveNewAuthor(author);

        assertThat(persistedAuthor).isNotNull();
        assertThat(persistedAuthor.getId()).isNotNull();
    }

    @Test
    void updateAuthor() {
        Author author = new Author();
        author.setFirstName("NewAuthorFirstName-4");
        author.setLastName("NewAuthorLastName-4");
        Author persistedAuthor = authorDao.saveNewAuthor(author);

        persistedAuthor.setFirstName("NewAuthorFirstName-5");
        Author updatedAuthor = authorDao.updateAuthor(persistedAuthor);

        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getId()).isEqualTo(persistedAuthor.getId());
        assertThat(updatedAuthor.getFirstName()).isEqualTo("NewAuthorFirstName-5");
        assertThat(updatedAuthor.getLastName()).isEqualTo("NewAuthorLastName-4");
    }

    @Test
    void deleteAuthorById() {
        Author author = new Author();
        author.setFirstName("NewAuthorFirstName-6");
        author.setLastName("NewAuthorLastName-6");
        Author persistedAuthor = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(persistedAuthor.getId());

        Author deleted = authorDao.getById(persistedAuthor.getId());
        assertThat(deleted).isNull();
    }

    @Test
    void listAuthorByLastNameLike() {
        Author author1 = new Author();
        author1.setFirstName("Eduardo");
        author1.setLastName("Tralala");
        authorDao.saveNewAuthor(author1);

        Author author2 = new Author();
        author2.setFirstName("Wallie");
        author2.setLastName("Blablala");
        authorDao.saveNewAuthor(author2);

        Author author3 = new Author();
        author3.setFirstName("Dominic");
        author3.setLastName("Barboza");
        authorDao.saveNewAuthor(author3);

        List<Author> authors = authorDao.listAuthorByLastNameLike("ala");
        assertThat(authors).hasSize(2);
    }

    @Test
    void countAuthorsWithGivenLastname() {
        Author author1 = new Author();
        author1.setFirstName("Eduardo");
        author1.setLastName("Smith");
        authorDao.saveNewAuthor(author1);

        Author author2 = new Author();
        author2.setFirstName("Wallie");
        author2.setLastName("Kowalsky");
        authorDao.saveNewAuthor(author2);

        Author author3 = new Author();
        author3.setFirstName("Dominic");
        author3.setLastName("Kowalsky");
        authorDao.saveNewAuthor(author3);

        Long count = authorDao.countAuthorsWithGivenLastname("Kowalsky");

        assertThat(count).isEqualTo(2);
    }

    @Test
    void findAuthorByNameCriteria() {
        Author author = new Author();
        author.setFirstName("Eduardo");
        author.setLastName("Saragado");
        authorDao.saveNewAuthor(author);

        Author fetchedAuthor = authorDao.findAuthorByNameCriteria("Eduardo", "Saragado");

        assertThat(fetchedAuthor).isNotNull();
        assertThat(fetchedAuthor.getLastName()).isEqualTo("Saragado");
        assertThat(fetchedAuthor.getFirstName()).isEqualTo("Eduardo");
    }

    @Test
    void firstLevelCacheDemo() {
        authorDao.firstLevelCacheDemo();
    }

}

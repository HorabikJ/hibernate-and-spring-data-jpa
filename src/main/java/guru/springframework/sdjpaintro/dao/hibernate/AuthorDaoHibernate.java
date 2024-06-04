package guru.springframework.sdjpaintro.dao.hibernate;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoHibernate implements AuthorDao {

    private final EntityManager entityManager;

    public AuthorDaoHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author getById(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name",
                Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
//        Below commented lines can be useful when we want to use this method outside the transaction.
//        Or when Hibernate transaction manager is not in sync with SpringBoot transaction manager.
//        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
//        entityManager.getTransaction().commit();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        entityManager.joinTransaction();
        entityManager.merge(author); // Merge function can merge the entity to hibernate transaction context but not 
        // always have to perform the operations on the db after merging, so that is why we have to use below method 
        // calls to make sure that changes we made to the author entity in hibernate context will be performed on the
        // db. 
        entityManager.flush(); // Flushing the db operations from current hibernate transaction to db.
        entityManager.clear(); // Clearing 1st level cache.
        return entityManager.find(Author.class, author.getId()); // fetching author directly form db, as 1st 
        // level cache was cleared line above. 
    }

    @Override
    public void deleteAuthorById(Long id) {
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
    }

}

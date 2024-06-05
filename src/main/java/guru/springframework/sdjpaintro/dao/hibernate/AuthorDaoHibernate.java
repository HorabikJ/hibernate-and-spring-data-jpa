package guru.springframework.sdjpaintro.dao.hibernate;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * For more info how to properly use entity manager see: https://www.geeksforgeeks.org/jpa-hibernate-entity-manager/
 * It describes how to get the instance of EntityManager, manage its transactions and flushing, clearing cache.
 * I am not doing this over here as today nobody really uses standalone Hibernate, it is always used with Spring Data
 * JPA and I want to focus on that more in this course. Also, I think that creating a new transaction for each query
 * in the Dao layer is not a good practice, as it is shown in the above link. Transaction management should be
 * implemented a layer higher, in service layer, not in the Dao layer. Dao layer should be responsible only for
 * saving, deleting or fetching the data. Service layer should manage transactions as in service we may have several
 * Dao methods grouped in one service method that is transactional.
 */
@Component
public class AuthorDaoHibernate {

    private final EntityManager entityManager;

    public AuthorDaoHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Author getById(Long id) {
        return entityManager.find(Author.class, id);
    }

    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name",
                Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }

    public Author saveNewAuthor(Author author) {
        entityManager.joinTransaction();
        entityManager.persist(author);
        entityManager.flush();
        return author;
    }

    public Author updateAuthor(Author author) {
        entityManager.joinTransaction();

        // first way of updating an entity ->>>
        Author dbAuthor = entityManager.find(Author.class, author.getId());
        dbAuthor.setFirstName(author.getFirstName());
        dbAuthor.setLastName(author.getLastName());

        // second way of updating an entity ->>>
//        entityManager.merge(author); // Merge function can merge the entity to hibernate transaction context but not 
        // always have to perform the operations on the db after merging, so that is why we have to use below method 
        // calls to make sure that changes we made to the author entity in hibernate context will be performed on the
        // db. 

        // We chose the first way because, we do not want to use merge() function. And because of that any chenges 
        // that are done to persisted entity (persisted means attached to Hibernate session) will be reflected in db 
        // when the Hibernate session is flushed or committed. According to: 
        // https://stackoverflow.com/questions/49604134/update-vs-merge-method-in-hibernate
        
        entityManager.flush(); // Flushing the db operations from current hibernate transaction to db.
        entityManager.clear(); // Clearing 1st level cache.
        return entityManager.find(Author.class, author.getId()); // fetching author directly form db, as 1st 
        // level cache was cleared line above. 
    }

    public void deleteAuthorById(Long id) {
        entityManager.joinTransaction();
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
    }

    public List<Author> listAuthorByLastNameLike(String lastName) {
        TypedQuery<Author> query = entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.lastName LIKE :last_name",
                Author.class);
        query.setParameter("last_name", "%" + lastName + "%");
        return query.getResultList();
    }

    public Long countAuthorsWithGivenLastname(String lastName) {
        TypedQuery<Long> query = entityManager.createNamedQuery(
                "count_authors_with_given_lastname",
                Long.class);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);

        Root<Author> root = query.from(Author.class);

        ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
        ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

        Predicate firstNamePredicate = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
        Predicate lastNamePredicate = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

        query.select(root).where(criteriaBuilder.and(firstNamePredicate, lastNamePredicate));

        TypedQuery<Author> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter(firstNameParam, firstName);
        typedQuery.setParameter(lastNameParam, lastName);

        return typedQuery.getSingleResult();
    }

    public void firstLevelCacheDemo() {
        // comment out one of or all entityManager.clear() lines and observe the logs 
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        entityManager.persist(author);
        System.out.println("---");
        entityManager.clear();
        entityManager.find(Author.class, author.getId());
        System.out.println("---");
        entityManager.clear();
        entityManager.find(Author.class, author.getId());
    }


}

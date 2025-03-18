package library.repositories;

import library.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public BookRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("library");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Book save(Book book) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        return null;
    }

    public Book find(Long id) {
        return entityManager.find(Book.class, id);
    }

    public void delete(Long id) {
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
        entityManager.getTransaction().commit();
    }

    public void update(Book book) {
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }

    public Book findByTitle(String title) {
        return entityManager.find(Book.class, title);
    }

    public Book findByIsbn(String isbn) {
        String jpql = "SELECT b FROM Book b WHERE b.isbn = :isbn";
        TypedQuery<Book> query = entityManager.createQuery(jpql, Book.class);
        query.setParameter("isbn", isbn);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public Book findByPublisher(String publisher) {
        return entityManager.find(Book.class, publisher);
    }

    public List<Book> searchBooks(String title, String authors, String publicationDate, String isbn, String publisher, String similarBooks) {
        // Inicia a base da consulta JPQL
        StringBuilder jpql = new StringBuilder("SELECT b FROM Book b WHERE 1=1");

        // Lista para armazenar os parâmetros de consulta
        int paramIndex = 1;

        // Adiciona os filtros dinamicamente com base nos parâmetros
        if (title != null && !title.isEmpty()) {
            jpql.append(" AND b.title LIKE :title");
        }
        if (authors != null && !authors.isEmpty()) {
            jpql.append(" AND b.authors LIKE :authors");
        }
        if (publicationDate != null && !publicationDate.isEmpty()) {
            jpql.append(" AND b.publicationDate LIKE :publicationDate");
        }
        if (isbn != null && !isbn.isEmpty()) {
            jpql.append(" AND b.isbn LIKE :isbn");
        }
        if (publisher != null && !publisher.isEmpty()) {
            jpql.append(" AND b.publisher LIKE :publisher");
        }
        if (similarBooks != null && !similarBooks.isEmpty()) {
            jpql.append(" AND b.similarBooks LIKE :similarBooks");
        }

        // Cria a query e define os parâmetros
        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (authors != null && !authors.isEmpty()) {
            query.setParameter("authors", "%" + authors + "%");
        }
        if (publicationDate != null && !publicationDate.isEmpty()) {
            query.setParameter("publicationDate", "%" + publicationDate + "%");
        }
        if (isbn != null && !isbn.isEmpty()) {
            query.setParameter("isbn", "%" + isbn + "%");
        }
        if (publisher != null && !publisher.isEmpty()) {
            query.setParameter("publisher", "%" + publisher + "%");
        }
        if (similarBooks != null && !similarBooks.isEmpty()) {
            query.setParameter("similarBooks", "%" + similarBooks + "%");
        }

        return query.getResultList();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}

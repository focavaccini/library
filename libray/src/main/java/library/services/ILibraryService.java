package library.services;

import library.entities.Book;

import java.io.File;
import java.util.List;

public interface ILibraryService {

    Book findById(Long id);

    Book findByTitle(String title);

    Book findByIsbn(String isbn);

    Book findByPublisher(String publisher);

    Book save(Book book);

    void update(Long idBook, Book book);

    void delete(Long id);

    void processFile(File file);

    List<Book> searchBooks(String title, String authors, String publicationDate, String isbn, String publisher, String similarBooks);

    void findBookFromUrl(String url);

}

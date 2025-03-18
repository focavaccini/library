package library.forms;

import library.entities.Book;
import library.services.LibraryService;

import java.awt.event.ActionEvent;
import java.util.List;

public class SearchBooks extends SearchBooksForm {

    private LibraryService libraryService;

    public SearchBooks(Library library) {
        super(library);
        this.libraryService = new LibraryService();
    }

    public SearchBooks() {
        super();
    }

    @Override
    protected void btnSearchClick(ActionEvent event) {
        String title = txtTitle.getText();
        String authors = txtAuthors.getText();
        String publishDate = txtPublishDate.getText();
        String isbn = txtIsbn.getText();
        String publishers = txtPublishers.getText();
        String similarBooks = txtSimilarBooks.getText();

        LibraryService libraryService = new LibraryService();
        List<Book> books = libraryService.searchBooks(title, authors, publishDate, isbn, publishers, similarBooks);

        ListBookForm listBookForm = new ListBooks(this, books);
        listBookForm.setVisible(true);

        this.dispose();
    }

    @Override
    protected void btnBackClick(ActionEvent event) {
        this.dispose();
        Library library = new Library();
        library.setVisible(true);
    }


}

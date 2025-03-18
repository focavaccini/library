package library.forms;

import library.entities.Book;
import library.services.LibraryService;

import java.awt.event.ActionEvent;
import java.util.List;

public class ListBooks extends ListBookForm {

    protected LibraryService libraryService;

    public ListBooks(SearchBooks previousWindow, List<Book> books) {
        super(previousWindow, books);
        this.libraryService = new LibraryService();
        this.listBooks = books;
    }

    @Override
    protected void btnCloseClick(ActionEvent event) {
        this.dispose();
        if (previousWindow != null) {
            previousWindow.setVisible(true);
        }
    }
}

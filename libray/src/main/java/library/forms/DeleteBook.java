package library.forms;

import library.entities.Book;
import library.services.LibraryService;
import library.utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteBook extends DeleteBookForm {

    private LibraryService libraryService;

    public DeleteBook(Library library) {
        super(library);
        this.libraryService = new LibraryService();
    }

    @Override
    protected void btnDeleteClick(ActionEvent event) {
        try {
            String isbnBook = txtIsbn.getText();
            Book book = libraryService.findByIsbn(isbnBook);

            if (Utils.isEmpty(book)) {
                JOptionPane.showMessageDialog(this, "Book not found", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                libraryService.delete(book.getId());
                JOptionPane.showMessageDialog(this, "Livro deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                int option = JOptionPane.showOptionDialog(this, "Livro deletado com sucesso! Deseja voltar à tela inicial?", "Sucesso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Sim", "Não"}, "Sim");

                if (option == JOptionPane.YES_OPTION) {
                    this.dispose();
                    Library library = new Library();
                    library.setVisible(true);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O valor inserido não é um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    protected void btnBackClick(ActionEvent event) {
        this.dispose();
        Library library = new Library();
        library.setVisible(true);
    }
}

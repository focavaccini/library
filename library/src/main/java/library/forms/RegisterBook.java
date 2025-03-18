package library.forms;

import library.entities.Book;
import library.services.LibraryService;
import library.utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegisterBook extends RegisterBookForm {

    private LibraryService libraryService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public RegisterBook(Library library) {
        super(library);
        this.libraryService = new LibraryService();
    }

    @Override
    protected void btnSaveClick(ActionEvent event) {

        if (Utils.isEmpty(txtTitle.getText()) || Utils.isEmpty(txtIsbn.getText()) ||
                Utils.isEmpty(txtPublisher.getText()) || Utils.isEmpty(txtSimilarBooks.getText()) ||
                Utils.isEmpty(txtAuthors.getText()) || Utils.isEmpty(txtPublicationDate.getText())) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = new Book();
        book.setTitle(txtTitle.getText());
        book.setIsbn(txtIsbn.getText());

        String authors = txtAuthors.getText();
        String publishers = txtPublisher.getText();
        String similarBooks = txtSimilarBooks.getText();

        book.setAuthors(authors);
        book.setSimilarBooks(similarBooks);
        book.setPublishers(publishers);

        try {
            book.setPublishDate(dateFormat.parse(txtPublicationDate.getText()));
        } catch (ParseException e) {
            // Se a data for inválida
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            libraryService.save(book);

            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            int option = JOptionPane.showOptionDialog(this, "Livro cadastrado com sucesso! Deseja voltar à tela inicial?", "Sucesso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Sim", "Não"}, "Sim");

            if (option == JOptionPane.YES_OPTION) {
                this.dispose();
                Library library = new Library();
                library.setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar o livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void btnBackClick(ActionEvent event) {
        this.dispose();
        Library library = new Library();
        library.setVisible(true);
    }
}

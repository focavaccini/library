package library.forms;

import library.services.LibraryService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RegisterBookByIsbn extends RegisterBookByIsbnForm {

    private LibraryService libraryService;

    public RegisterBookByIsbn(Library library) {
        super(library);
        this.libraryService = new LibraryService();
    }

    @Override
    protected void btnSearchClick(ActionEvent event) {
        try {
            String isbnBook = txtIsbn.getText();
            libraryService.findBookFromUrl(isbnBook);

            JOptionPane.showMessageDialog(this, "Livro Cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            int option = JOptionPane.showOptionDialog(this, "Livro Cadastrado com sucesso! Deseja voltar à tela inicial?", "Sucesso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Sim", "Não"}, "Sim");

            if (option == JOptionPane.YES_OPTION) {
                this.dispose();
                Library library = new Library();
                library.setVisible(true);
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

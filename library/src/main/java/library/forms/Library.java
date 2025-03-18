package library.forms;

import library.services.LibraryService;
import library.utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Library extends LibraryForm {

    private LibraryService libraryService;

    public Library() {
        this.libraryService = new LibraryService();
    }

    @Override
    protected void btnFileChooserClick(ActionEvent event) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = jFileChooser.showOpenDialog(this);

        if (result == JFileChooser.CANCEL_OPTION) {
            jFileChooser.cancelSelection();
        } else {

            File file = jFileChooser.getSelectedFile();
            String fileName = file.getName();
            String fileExtension = libraryService.getFileExtension(fileName);

            if (!fileExtension.equals("csv") && !fileExtension.equals("xml")) {
                JOptionPane.showMessageDialog(this, "Tipo de documento inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            libraryService.processFile(file);
        }
    }

    @Override
    protected void btnRegisterClick(ActionEvent event) {
        RegisterBookForm registerBookForm = new RegisterBook(this);
        registerBookForm.setVisible(true);

        this.dispose();
    }

    @Override
    protected void btnDeleteClick(ActionEvent event) {
        DeleteBookForm deleteBookForm = new DeleteBook(this);
        deleteBookForm.setVisible(true);

        this.dispose();
    }

    @Override
    protected void btnSearchBookIsbn(ActionEvent event) {
        RegisterBookByIsbnForm registerBookForm = new RegisterBookByIsbn(this);
        registerBookForm.setVisible(true);

        this.dispose();
    }

    @Override
    protected void btnSearchBookClick(ActionEvent event) {
        SearchBooksForm listBookForm = new SearchBooks(this);
        listBookForm.setVisible(true);
        this.dispose();
    }

}

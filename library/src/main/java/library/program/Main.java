package library.program;

import library.forms.Library;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Library library = new Library();
            library.setVisible(true);
        });
    }
}

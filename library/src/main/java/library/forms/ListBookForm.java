package library.forms;

import library.entities.Book;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class ListBookForm extends JFrame {

    protected SearchBooks previousWindow;
    protected JButton btnClose;
    protected JPanel pnlForm;
    protected JPanel pnlFoot;
    protected JTable table;
    protected List<Book> listBooks;

    public ListBookForm(SearchBooks previousWindow, List<Book> listBooks) {
        this.previousWindow = previousWindow;
        this.listBooks = listBooks != null ? listBooks : new ArrayList<>();
        this.inicializar();
        this.events();
    }

    private void inicializar() {
        this.setTitle("Listar livros");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
        this.getContentPane().add(getPnlFoot(), BorderLayout.PAGE_END);

        this.setVisible(true);

        this.pack();
    }

    protected abstract void btnCloseClick(ActionEvent event);

    private void events() {
        btnClose.addActionListener(this::btnCloseClick);
    }

    public JPanel getPnlForm() {
        if (pnlForm == null) {
            pnlForm = new JPanel();
            pnlForm.setLayout(new BorderLayout());  // Definindo um layout simples

            if (listBooks.isEmpty()) {
                JLabel label = new JLabel("Nenhum livro encontrado.");
                pnlForm.add(label, BorderLayout.CENTER);
            } else {
                // Definindo as colunas da tabela
                String[] colunas = {"Título", "Autor", "Isbn", "Data de Publicação", "Livros Similares", "Editoras"};

                DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

                for (Book book : listBooks) {
                    Object[] linha = {book.getTitle(), book.getAuthors(), book.getIsbn(), book.getPublishDate(), book.getSimilarBooks(), book.getPublishers()};
                    modelo.addRow(linha);
                }

                table = new JTable(modelo);
                JScrollPane scrollPane = new JScrollPane(table);
                pnlForm.add(scrollPane, BorderLayout.CENTER);
            }
        }
        return pnlForm;
    }

    public JPanel getPnlFoot() {
        if (pnlFoot == null) {
            pnlFoot = new JPanel(new GridLayout(1, 1));
            this.add(pnlFoot, BorderLayout.PAGE_END);
            btnClose = new JButton("Voltar");
            pnlFoot.add(btnClose);
        }
        return pnlFoot;
    }

}

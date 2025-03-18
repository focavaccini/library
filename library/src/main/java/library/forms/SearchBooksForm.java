package library.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class SearchBooksForm extends JFrame {

    private static final int SIZE_FIELD_TEXT = 20;

    protected Library previousWindow;

    protected JLabel lblTitle;
    protected JLabel lblAuthors;
    protected JLabel lblPublishDate;
    protected JLabel lblIsbn;
    protected JLabel lblPublishers;
    protected JLabel lblSimilarBooks;

    protected JTextField txtTitle;
    protected JTextField txtAuthors;
    protected JTextField txtPublishDate;
    protected JTextField txtIsbn;
    protected JTextField txtPublishers;
    protected JTextField txtSimilarBooks;

    protected JButton btnSearch;
    protected JButton btnBack;

    protected JPanel pnlForm;
    protected JPanel pnlFoot;

    public SearchBooksForm(Library previousWindow) {
        this.previousWindow = previousWindow;
        this.inicializar();
        this.events();
    }

    public SearchBooksForm() {

    }

    private void inicializar() {
        this.setTitle("Buscar Livro");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
        this.getContentPane().add(getPnlFoot(), BorderLayout.PAGE_END);

        this.setVisible(true);

        this.pack();
    }

    protected abstract void btnSearchClick(ActionEvent event);
    protected abstract void btnBackClick(ActionEvent event);

    private void events() {
        btnSearch.addActionListener(this::btnSearchClick);
        btnBack.addActionListener(this::btnBackClick);
    }

    public JPanel getPnlForm() {
        if (pnlForm == null) {
            pnlForm = new JPanel(new GridLayout(6, 1));
            this.add(pnlForm, BorderLayout.CENTER);

            lblTitle = new JLabel("Título");
            txtTitle = new JTextField(SIZE_FIELD_TEXT);

            lblAuthors = new JLabel("Autores");
            txtAuthors = new JTextField(SIZE_FIELD_TEXT);

            lblPublishDate = new JLabel("Data de Publicação");
            txtPublishDate = new JTextField(SIZE_FIELD_TEXT);

            lblIsbn = new JLabel("ISBN");
            txtIsbn = new JTextField(SIZE_FIELD_TEXT);

            lblPublishers = new JLabel("Editora");
            txtPublishers = new JTextField(SIZE_FIELD_TEXT);

            lblSimilarBooks = new JLabel("Livros Semelhantes");
            txtSimilarBooks = new JTextField(SIZE_FIELD_TEXT);

            pnlForm.add(lblTitle);
            pnlForm.add(txtTitle);

            pnlForm.add(lblAuthors);
            pnlForm.add(txtAuthors);

            pnlForm.add(lblPublishDate);
            pnlForm.add(txtPublishDate);

            pnlForm.add(lblIsbn);
            pnlForm.add(txtIsbn);

            pnlForm.add(lblPublishers);
            pnlForm.add(txtPublishers);

            pnlForm.add(lblSimilarBooks);
            pnlForm.add(txtSimilarBooks);
        }
        return pnlForm;
    }

    public JPanel getPnlFoot() {
        if (pnlFoot == null) {
            pnlFoot = new JPanel();
            this.add(pnlFoot, BorderLayout.PAGE_END);

            btnSearch = new JButton("Buscar Livro");
            btnBack = new JButton("Voltar");

            pnlFoot.add(btnSearch);
            pnlFoot.add(btnBack);
        }
        return pnlFoot;
    }

}

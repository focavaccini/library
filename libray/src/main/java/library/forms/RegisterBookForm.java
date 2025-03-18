package library.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class RegisterBookForm extends JFrame {

    protected Library previousWindow;

    protected JTextField txtTitle;
    protected JTextField txtAuthors;
    protected JTextField txtPublicationDate;
    protected JTextField txtIsbn;
    protected JTextField txtPublisher;
    protected JTextField txtSimilarBooks;

    protected JButton btnSave;
    protected JButton btnBack;

    protected JPanel pnlForm;

    public RegisterBookForm(Library previousWindow) {
        this.previousWindow = previousWindow;
        this.inicializar();
        this.events();
    }

    private void inicializar() {
        this.setTitle("Registrar Livro");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
        this.setVisible(true);

        this.pack();
    }

    protected abstract void btnSaveClick(ActionEvent event);
    protected abstract void btnBackClick(ActionEvent event);

    private void events() {
        btnSave.addActionListener(this::btnSaveClick);
        btnBack.addActionListener(this::btnBackClick);
    }

    public JPanel getPnlForm() {
        if (pnlForm == null) {
            pnlForm = new JPanel(new GridLayout(7, 2, 10, 10));
            this.add(pnlForm, BorderLayout.CENTER);

            JLabel lblTitle = new JLabel("Título:");
            txtTitle = new JTextField(20);

            JLabel lblAuthors = new JLabel("Autores:");
            txtAuthors = new JTextField(20);

            JLabel lblPublicationDate = new JLabel("Data de Publicação:");
            txtPublicationDate = new JTextField(20);

            JLabel lblIsbn = new JLabel("ISBN:");
            txtIsbn = new JTextField(20);

            JLabel lblPublisher = new JLabel("Editora:");
            txtPublisher = new JTextField(20);

            JLabel lblSimilarBooks = new JLabel("Livros Semelhantes:");
            txtSimilarBooks = new JTextField(20);

            pnlForm.add(lblTitle);
            pnlForm.add(txtTitle);

            pnlForm.add(lblAuthors);
            pnlForm.add(txtAuthors);

            pnlForm.add(lblPublicationDate);
            pnlForm.add(txtPublicationDate);

            pnlForm.add(lblIsbn);
            pnlForm.add(txtIsbn);

            pnlForm.add(lblPublisher);
            pnlForm.add(txtPublisher);

            pnlForm.add(lblSimilarBooks);
            pnlForm.add(txtSimilarBooks);

            btnSave = new JButton("Salvar");
            btnBack = new JButton("Voltar");
            pnlForm.add(btnSave);
            pnlForm.add(btnBack);
        }
        return pnlForm;
    }

}

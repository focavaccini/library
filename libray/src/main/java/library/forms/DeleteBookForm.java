package library.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class DeleteBookForm extends JFrame {

    protected Library previousWindow;

    protected JTextField txtIsbn;

    protected JButton btnDelete;
    protected JButton btnBack;

    protected JPanel pnlForm;

    public DeleteBookForm(Library previousWindow) {
        this.previousWindow = previousWindow;
        this.inicializar();
        this.events();
    }

    private void inicializar() {
        this.setTitle("Deletar Livro");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);
        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
        this.setVisible(true);

        this.pack();
    }

    protected abstract void btnDeleteClick(ActionEvent event);
    protected abstract void btnBackClick(ActionEvent event);

    private void events() {
        btnDelete.addActionListener(this::btnDeleteClick);
        btnBack.addActionListener(this::btnBackClick);
    }

    public JPanel getPnlForm() {
        if (pnlForm == null) {
            pnlForm = new JPanel(new GridLayout(1, 1));
            this.add(pnlForm, BorderLayout.CENTER);

            JLabel lblIsbnBook = new JLabel("Isbn do Livro:");
            txtIsbn = new JTextField(20);

            pnlForm.add(lblIsbnBook);
            pnlForm.add(txtIsbn);

            btnDelete = new JButton("Apagar");
            btnBack = new JButton("Voltar");
            pnlForm.add(btnDelete);
            pnlForm.add(btnBack);
        }
        return pnlForm;
    }
}

package library.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class RegisterBookByIsbnForm extends JFrame {

    protected Library previousWindow;

    protected JTextField txtIsbn;

    protected JButton btnSearch;
    protected JButton btnBack;

    protected JPanel pnlForm;

    public RegisterBookByIsbnForm(Library previousWindow) {
        this.previousWindow = previousWindow;
        this.inicializar();
        this.events();
    }

    private void inicializar() {
        this.setTitle("Buscar Livro");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
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
            pnlForm = new JPanel(new GridLayout(1, 1));
            this.add(pnlForm, BorderLayout.CENTER);

            JLabel lblIsbnBook = new JLabel("Isbn do Livro:");
            txtIsbn = new JTextField(20);

            pnlForm.add(lblIsbnBook);
            pnlForm.add(txtIsbn);

            btnSearch = new JButton("Buscar");
            btnBack = new JButton("Voltar");
            pnlForm.add(btnSearch);
            pnlForm.add(btnBack);
        }
        return pnlForm;
    }

}

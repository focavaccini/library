package library.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class LibraryForm extends JFrame {

    protected JPanel pnlForm;
    protected JPanel pnlFoot;

    protected JButton btnRegister;
    protected JButton btnDelete;
    protected JButton btnFileChooser;
    protected JButton btnSearchBook;
    protected JButton btnSearchBookIsbn;

    public LibraryForm() {
        this.inicializar();
        this.events();
    }

    private void inicializar() {
        this.setTitle("Book Register");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
        this.getContentPane().add(getPnlFoot(), BorderLayout.PAGE_END);
        this.setSize(600, 100);
//        this.pack();
    }

    protected abstract void btnFileChooserClick(ActionEvent event);

    protected abstract void btnSearchBookClick(ActionEvent event);

    protected abstract void btnRegisterClick(ActionEvent event);

    protected abstract void btnDeleteClick(ActionEvent event);

    protected abstract void btnSearchBookIsbn(ActionEvent event);

    private void events() {
        btnFileChooser.addActionListener(this::btnFileChooserClick);
        btnSearchBook.addActionListener(this::btnSearchBookClick);
        btnRegister.addActionListener(this::btnRegisterClick);
        btnDelete.addActionListener(this::btnDeleteClick);
        btnSearchBookIsbn.addActionListener(this::btnSearchBookIsbn);
    }

    public JPanel getPnlFoot() {
        if (pnlFoot == null) {
            pnlFoot = new JPanel(new FlowLayout(FlowLayout.CENTER));

            btnRegister = new JButton("Registrar");
            btnDelete = new JButton("Deletar");
            btnFileChooser = new JButton("Selecionar Arquivo");
            btnSearchBook = new JButton("Buscar Livros");
            btnSearchBookIsbn = new JButton("Buscar por Isbn");

            pnlFoot.add(btnRegister);
            pnlFoot.add(btnDelete);
            pnlFoot.add(btnSearchBookIsbn);
            pnlFoot.add(btnFileChooser);
            pnlFoot.add(btnSearchBook);
        }

        return pnlFoot;
    }

    public JPanel getPnlForm() {
        if (pnlForm == null) {
            pnlForm = new JPanel(new GridLayout(7, 2));
        }

        return pnlForm;
    }

}

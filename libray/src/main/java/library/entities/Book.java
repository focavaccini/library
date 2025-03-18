package library.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_book")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;


    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "authors")
    private String authors;

    @Column(name = "publishers")
    private String publishers;

    @Column(name = "similar_books")
    private String similarBooks;

}

package library.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.entities.Book;
import library.entities.DateParser;
import library.repositories.BookRepository;
import library.utils.Utils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class LibraryService implements ILibraryService {

    BookRepository bookRepository = new BookRepository();

    @Override
    public Book findById(Long id) {
        return bookRepository.find(id);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Book findByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> searchBooks(String title, String authors, String publicationDate, String isbn, String publisher, String similarBooks) {
        return bookRepository.searchBooks(title, authors, publicationDate, isbn, publisher, similarBooks);
    }

    @Override
    public void update(Long idBook, Book book) {
        Book bookManaged = findById(idBook);

        bookManaged.setTitle(Utils.nvl(book.getTitle(), bookManaged.getTitle()));
        bookManaged.setIsbn(Utils.nvl(book.getIsbn(), bookManaged.getIsbn()));
        bookManaged.setPublishDate(Utils.nvl(book.getPublishDate(), bookManaged.getPublishDate()));
        bookManaged.setPublishers(Utils.nvl(book.getPublishers(), bookManaged.getPublishers()));
        bookManaged.setSimilarBooks(Utils.nvl(book.getSimilarBooks(), bookManaged.getSimilarBooks()));
        bookManaged.setAuthors(Utils.nvl(book.getAuthors(), bookManaged.getAuthors()));

        bookRepository.update(bookManaged);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public void findBookFromUrl(String isbnBook) {
        Map<String, Object> dados = new HashMap<>();
        try {
            //deixado fico, mas podendo ser utilizado da forma a baixo se fosse o caso
//            String urlGet = "https://openlibrary.org/isbn/" + isbnBook;
            String urlGet = "https://openlibrary.org/isbn/9780140328721.json";
            URL objUrl = new URL(urlGet);
            HttpURLConnection con = (HttpURLConnection) objUrl.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String descricao = br.readLine();
                ObjectMapper mapper = new ObjectMapper();
                dados = mapper.readValue(descricao, Map.class);
                processBook(dados);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void processFile(File file) {
        if (Utils.isEmpty(file)) {
            throw new RuntimeException("Falha ao buscar o arquivo");
        }

        String fileName = file.getName();
        String fileExtension = getFileExtension(fileName);

        if (fileExtension.equals("csv") || fileExtension.equals("xls")) {
            processFileCsv(file);
        }  else if (fileExtension.equals("xml")) {
            processFileXml(file);
        }
    }

    public void processFileCsv(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Lê a primeira linha com os cabeçalhos
            String headerLine = br.readLine();

            if (headerLine != null) {
                String[] columnNames = headerLine.split(";");

                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");

                    // Mapeia o nome da coluna para o respectivo valor
                    Map<String, Object> mapOfData = new HashMap<>();

                    for (int i = 0; i < columnNames.length; i++) {
                        if (i < values.length) {
                            mapOfData.put(columnNames[i].trim(), values[i].trim());
                        }
                    }

                    // Mapeia os dados para a entidade Book
                    processBook(mapOfData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processFileXml(File file) {
        String xmlContent = null;
        try {
            xmlContent = Utils.readFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = XML.toJSONObject(xmlContent);
        Map<String, Object> json = Utils.convertXmlToJson(jsonObject);

        Map<String, Object> books = (Map<String, Object>) json.get("books");
        Object bookData = books != null ? books.get("book") : json.get("book");

        if (Utils.isEmpty(bookData)) {
            throw new RuntimeException("Falha ao buscar o arquivo");
        }

        if (bookData instanceof Map) {
            Map<String, Object> mapOfData = (Map<String, Object>) bookData;
            processBook(mapOfData);
        } else if (bookData instanceof JSONArray) {
            JSONArray bookArray = (JSONArray) bookData;
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject bookObject = bookArray.getJSONObject(i);
                processBook(bookObject.toMap());
            }
        } else {
            throw new RuntimeException("Formato inesperado de dados no arquivo XML");
        }
    }

    private void processBook(Map<String, Object> mapOfData) {
        Book bookManaged = findByIsbn(mapEntityFields(mapOfData).getIsbn());

        if (!Utils.isEmpty(bookManaged)) {
            update(bookManaged.getId(), mapEntityFields(mapOfData));
        } else {
            save(mapEntityFields(mapOfData));
        }
    }

    public Book mapEntityFields(Map<String, Object> dados) {
        try {
            Class<?> c = Class.forName("library.entities.Book");
            Field[] atributos = c.getDeclaredFields();
            Book book = new Book();

            for (Map.Entry<String, Object> entry : dados.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String normalizedKey = normalizeColumnName(key);

                Optional<Field> matchingField = Arrays.stream(atributos)
                        .filter(field -> field.getName().equalsIgnoreCase(normalizedKey))
                        .findFirst();

                if (matchingField.isPresent()) {
                    Field field = matchingField.get();
                    field.setAccessible(true);

                    if (field.getType().equals(Date.class) && value instanceof String) {
                        String dateValue = (String) value;
                        Date parsedDate = DateParser.parseDate(dateValue);
                        if (parsedDate != null) {
                            field.set(book, parsedDate);
                        } else {
                            System.err.println("Erro ao analisar data: " + dateValue);
                        }
                    } else if (field.getName().equals("isbn") && value instanceof List) {
                        List<String> isbnList = (List<String>) value;

                        if (!isbnList.isEmpty()) {
                            String isbn = isbnList.get(0);
                            field.set(book, isbn);
                        }
                    } else if (field.getName().equals("authors") && value instanceof List) {
                        List<Map<String, Object>> authorsList = (List<Map<String, Object>>) value;
                        List<String> authors = new ArrayList<>();

                        for (Map<String, Object> authorData : authorsList) {
                            String authorName = (String) authorData.get("key");
                            if (authorName != null) {
                                authors.add(authorName);
                            }
                        }

                        String authorsString = String.join(", ", authors);
                        field.set(book, authorsString);
                    } else if (field.getName().equals("publishers") && value instanceof List) {
                        // Se o valor for uma lista de publishers
                        List<String> publishersList = (List<String>) value;

                        String publishersString = String.join(", ", publishersList);
                        field.set(book, publishersString);
                    } else if (field.getName().equals("similarBooks") && value instanceof List) {
                        List<?> similarBooksList = (List<?>) value;
                        List<String> stringSimilarBooksList = new ArrayList<>();

                        for (Object similarBook : similarBooksList) {
                            // Converte cada item para string (caso seja Integer ou outro tipo)
                            stringSimilarBooksList.add(String.valueOf(similarBook));
                        }

                        String similarBooksString = String.join(", ", stringSimilarBooksList);
                        field.set(book, similarBooksString);
                    } else {
                        field.set(book, value);
                    }
                }
            }

            return book;

        } catch (Throwable e) {
            return null;
        }
    }


    private String normalizeColumnName(String columnName) {
        // Remove qualquer caractere especial (underscore ou múltiplos espaços) e converte para um único espaço
        columnName = columnName.replaceAll("(_|\\s)+", " ").trim();

        // Remove o BOM (Byte Order Mark) se presente
        if (columnName.startsWith("\uFEFF")) {
            columnName = columnName.substring(1).trim();
        }

        // Caso o nome da coluna seja "ISBN" ou "Covers", retornamos diretamente os nomes ajustados
        if (columnName.toLowerCase().startsWith("isbn")) {
            return "isbn";
        }

        if (columnName.toLowerCase().startsWith("covers")) {
            return "similarBooks";
        }

        // Divide as palavras baseadas em espaços
        String[] words = columnName.split("\\s+");
        StringBuilder camelCaseColumnName = new StringBuilder();

        // Converte para camelCase
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                // Primeira palavra, coloca tudo em minúsculo
                camelCaseColumnName.append(words[i].toLowerCase());
            } else {
                // Palavras subsequentes, coloca a primeira letra em maiúsculo
                camelCaseColumnName.append(capitalize(words[i]));
            }
        }

        return camelCaseColumnName.toString();
    }

    private String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }
}

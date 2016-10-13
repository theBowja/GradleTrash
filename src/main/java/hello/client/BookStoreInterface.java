package hello.client;

import java.util.Collection;

public interface BookStoreInterface {
	public Book addNewBook(Book book) throws IllegalArgumentException;
	public Book updateBook(String bookId, Book modified) throws IllegalArgumentException;
	public Collection<Book> listAll();
	public Book lookup(String bookId);
	public void deleteBook(Book book);
}

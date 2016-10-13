package hello.controller;

import org.springframework.web.bind.annotation.RestController;

import hello.client.Book;
import hello.client.BookStoreInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api(value = "Book", description = "Operations with book", produces = "application/json")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
	@Autowired
	BookStoreInterface bookStore;
	
    @RequestMapping(value = "/books", method = RequestMethod.GET,
    		produces={MediaType.APPLICATION_JSON_VALUE})
    public Collection<Book> list() {
    	Collection<Book> books = bookStore.listAll();
        
        return books;
    }
   
    @SuppressWarnings("unused")
	@RequestMapping(value = "/books", method = RequestMethod.POST,
    		consumes={MediaType.APPLICATION_JSON_VALUE},
    		produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add a book", notes = "Add a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "Added")
    })
    public ResponseEntity addBook(@RequestBody Book book) {
    	try {
    		Book newbook = bookStore.addNewBook(book);
    	} catch (IllegalArgumentException e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST);
    	}

        return new ResponseEntity(book, HttpStatus.CREATED);

    }
    
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET,
    		produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get book info", notes = "Get the book info")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found the book"),
            @ApiResponse(code = 200, message = "Found the book")
    })
    public ResponseEntity getBook(@PathVariable("bookId")String id) {
        Book book = bookStore.lookup(id);

        if (book == null)
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
        else
        	return new ResponseEntity(book, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT,
    		consumes={MediaType.APPLICATION_JSON_VALUE},
    		produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Modify book info", notes = "Modify the book info")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found the book"),
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 200, message = "Update the book info")
    })
    public ResponseEntity changeBook(@PathVariable("bookId")String id, @RequestBody Book modified) {
    	try {
    		Book book = bookStore.updateBook(id, modified);
    		if (book == null)
    			return new ResponseEntity(HttpStatus.NOT_FOUND);
    		else
    			return new ResponseEntity(book, HttpStatus.OK);
    	} catch (IllegalArgumentException e) {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete the book", notes = "Remove the book from repository")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found the book"),
            @ApiResponse(code = 200, message = "Removed the book")
    })
    public ResponseEntity deleteBook(@PathVariable("bookId")String id) {
        Book book = bookStore.lookup(id);

        if (book == null)
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        bookStore.deleteBook(book);
        return new ResponseEntity(HttpStatus.OK);  // some people prefer to use HttpStatus.NO_CONTENT
    }
}
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Book {
  private String title;
  private String author;
  private String genre;
  private String serialNumber;
  private boolean isRented;
  private Member renter;
  private List<Member> rentHistory;

  /*
  Constructor
  */
  public Book(String title, String author, String genre, String serialNumber) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.serialNumber = serialNumber;
    
    this.isRented = false;
    this.renter = null;
    this.rentHistory = new ArrayList<Member>();
  }

  /*
  Purpose: Returns the title (String) of the book.
  */
  public String getTitle(){
    return this.title;
  }

  /*
  Purpose: Returns the author (String) of the book.
  */
  public String getAuthor(){
    return this.author;
  }

  /*
  Purpose: Returns the genre (String) of the book.
  */
  public String getGenre(){
    return this.genre;
  }

  /*
  Purpose: Returns the title (String) of the book.
  */
  public String getSerialNumber(){
    return this.serialNumber;
  }
  
  /*
  Purpose: Returns if the books is currenlty rented.
  Return: this.isRented (boolean)
  */
  public boolean isRented(){
    return this.isRented;
  }

  /*
  Purpose: Returns the member currently renting the book. If the book is currently unrented, the renter will be null.
  Return: this.renter (Member)
  */
  public Member getRenter() {
    return this.renter;
  }

  /* 
  Purpose: Formats the Book object to create the long form of its toString().
    If the book is rented: [serialNumber]: [title] ([author], [genre])\nRented by [renter number].
    If the book is available: [serialNumber]: [title] ([author], [genre])\nCurrently available. 
  Returns: Current renter (Member)
  */
  public String longString() {
    if (this == null) {
      return null;
    }
    String retString = String.format("%s: %s (%s, %s)", this.serialNumber, this.title,this.author,this.genre);

    if (this.isRented) {
      retString += String.format("%nRented by: %s.", renter.getMemberNumber());
    } else {
      retString += String.format("%nCurrently available.");
    }
    return retString;
  }

  /* 
  Purpose: Formats the Book object to create the short form of its toString().
  Format: [title] ([author])
  Returns: String
  */
  public String shortString() {
    if (this == null) {
      return null;
    }

    String retString = String.format("%s (%s)",this.title,this.author);
    return retString;
  }

  /*
  Purpose: Returns the renter history, in chronological order.
  Returns: The list of members who have rented the book. (List<Member>)
  */
  public List<Member> renterHistory() {
    return this.rentHistory;
  }

  /* 
  Purpose: Helper Method for reading files - takes in the file, confirms it exists and returns a scanner.
  Arguments: filename (String)
  Returns: Scanner either of the file, or null if the file doesn't exist
  */
  private static Scanner openFileRead (String filename) {
    try {
        File myFile = new File(filename);
        Scanner scan = new Scanner(myFile); 
        return scan;

    } catch (FileNotFoundException e) {
      return null;
    }
  }

  /* Purpose: Helper Method for writing to files - takes in the file, and returns a Printwriter.
  Arguments: filename (String)
  Returns: Printwriter of the file, or null if the file doesn't exist
  */
  private static PrintWriter openFileWrite(String filename) {
    try {
      File myFile = new File(filename);
      PrintWriter writer = new PrintWriter(myFile); 
      return writer;

    } catch (FileNotFoundException e) {
        return null;
    }
  }

  /*
  Purpose: Retrieves the book from the given csv file based on its serial number. 
  Arguments: filename - The csv file containing a book collection, serialNumber - The serial number for the book.
  Returns: Book object with the information read in from the csv
  */
  public static Book readBook(String filename, String serialNumber) {
    if (filename == null || serialNumber == null) {
      return null;
    }
    Scanner scan = openFileRead(filename);
    if (scan == null) {
      return null;
    }
    scan.nextLine(); //Skips the title line.

    while (scan.hasNextLine()) {
      String[] line = scan.nextLine().split(",");

      if (line[0].equals(serialNumber)) {
        return new Book(line[1], line[2], line[3], line[0]);
      }

    }
    return null;
  }
  
  /*
  Purpose: Reads in the collection of books from the given csv file. 
  Arguments: filename - The csv file containing a book collection.
  Returns: A list of Book objects
  */
  public static List<Book> readBookCollection​(String filename) {
    if (filename == null) {
      return null;
    }
    List<Book> ls = new ArrayList<Book>();

    Scanner scan = openFileRead(filename);
    if (scan == null) {
      return null;
    }

    scan.nextLine(); // Skips the title line.

    while (scan.hasNextLine()) {
      String[] line = scan.nextLine().split(",");
        ls.add(new Book(line[1], line[2], line[3], line[0]));
    }
    return ls;
  }

  /*
  Purpose: Save the collection of books to the given file.
  Arguments: filename - The csv file to write to, books - The collection of books to write to file.
  Returns: A list of Book objects
  */
  public static void saveBookCollection​(String filename, Collection<Book> books) {
    if (filename == null || books == null) {
      return;
    }
    PrintWriter writer = openFileWrite(filename);
    writer.println("serialNumber,title,author,genre");

    for (Book b: books) {
      String formattedString = String.format("%s,%s,%s,%s", b.serialNumber, b.title, b.author, b.genre);
      writer.println(formattedString);
    }
    writer.close();
  }

  /* 
  Purpose: Sets the current renter to be the given member.
    If the member does not exist, or the book is already being rented, do nothing and return false.
    If the book is able to be rented, return true. 
  Arguments: member - The new person renting the book.
  Returns: Boolean, whether the renting was successful
  */
  public boolean rent(Member member) {
    if (member == null) {
      return false;
    }
    if (this.isRented) {
      return false;
    }

    this.renter = member;
    this.isRented = true;
    //this.rentHistory.add(0,member); //swap order of history if need be
    
    return true;
  }

  /* 
  Purpose: Sets the current renter to null.
    If the member does not exist or isn't the current renter, do nothing and return false.
    If the book is rented by the member, change the current renter and return true.
  Arguments: member - The member returning the book.
  Returns: Boolean, whether the relinquishing was successful
  */
  public boolean relinquish(Member member) {
    if (member == null || member != this.renter) {
      return false;
    }

    if (!this.isRented) {
      return false;
    }

    this.renter = null;
    this.isRented = false;
    this.rentHistory.add(member);
    return true;
  }

  /* 
  Purpose:  A helper method that implements an insertion sort that will 
            insert a book object into an array based on its serial number. 
  Arguments:  books - The list of books the book is to be inserted into
              book - The book to be inserted
  */
  private static void addSorted(List<Book> books, Book book) {
    // Loops through each book in the list, and adds book immediately before the first 
    // book it finds with a bigger serial number
    for (int i = 0; i < books.size(); i ++) {
      if (Integer.parseInt(book.serialNumber) < Integer.parseInt(books.get(i).serialNumber)) {
        books.add(i,book);
        return;
      }
    }
    books.add(book);
  }

  /*
  Purpose: Creates a new list containing books by the specified author.
    If the list or author does not exist, return null.
    If they do exist, create a new list with all the books written by the given author, 
    sort by serial number, and return the result.
  Arguments:  books - the list of books to filter
              author - the author to filter by
  Returns:  The filtered list of books (List<Book>)
  */
  public static List<Book> filterAuthor​(List<Book> books, String author) {
    if (books == null || author == null) {
      return null;
    }

    List<Book> matchingBooks = new ArrayList<Book>();

    for (Book book: books) {
      if (book == null) {
        continue;
      }

      if (book.author.equals(author)) {
        
        // Adds the first matching book without any sorting necessary
        if (matchingBooks.size() == 0) {
          matchingBooks.add(book);
        
        // Adds the rest of the books using the helper method.
        } else {
          Book.addSorted(matchingBooks, book);
        }
      }
    }

  return matchingBooks;
  }

  /*
  Purpose: Creates a new list containing books by the specified genre.
    If the list or genre does not exist, return null.
    If they do exist, create a new list with all the books in the specified genre, 
    sort by serial number, and return the result.
  Arguments:  books - the list of books to filter
              author - the genre to filter by
  Returns:  The filtered list of books (List<Book>)
  */
  public static List<Book> filterGenre​(List<Book> books, String genre) {
    if (books == null || genre == null) {
      return null;
    }

    List<Book> matchingBooks = new ArrayList<Book>();

    for (Book book: books) {
      if (book == null) {
        continue;
      }

      if (book.genre.equals(genre)) {

        // Adds the first matching book without any sorting necessary
        if (matchingBooks.size() == 0) {
          matchingBooks.add(book);

        // Adds the rest of the books using the helper method.
        } else {
          Book.addSorted(matchingBooks, book);
        }
      }
    }
    return matchingBooks;
  }

}
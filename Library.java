import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.File;



public class Library {
  public static final String HELP_STRING = "EXIT ends the library process\nCOMMANDS outputs this " + 
    "help string\n\nLIST ALL [LONG] outputs either the short or long string for all books\nLIST " + 
    "AVAILABLE [LONG] outputs either the short of long string for all available books\nNUMBER " + 
    "COPIES outputs the number of copies of each book\nLIST GENRES outputs the name of every " + 
    "genre in the system\nLIST AUTHORS outputs the name of every author in the system\n\nGENRE " + 
    "<genre> outputs the short string of every book with the specified genre\nAUTHOR <author> " + 
    "outputs the short string of every book by the specified author\n\nBOOK <serialNumber> [LONG] " + 
    "outputs either the short or long string for the specified book\nBOOK HISTORY <serialNumber> " + 
    "outputs the rental history of the specified book\n\nMEMBER <memberNumber> outputs the information " + 
    "of the specified member\nMEMBER BOOKS <memberNumber> outputs the books currently rented by the " + 
    "specified member\nMEMBER HISTORY <memberNumber> outputs the rental history of the specified member" + 
    "\n\nRENT <memberNumber> <serialNumber> loans out the specified book to the given member\nRELINQUISH " + 
    "<memberNumber> <serialNumber> returns the specified book from the member\nRELINQUISH ALL " + 
    "<memberNumber> returns all books rented by the specified member\n\nADD MEMBER <name> adds a member " + 
    "to the system\nADD BOOK <filename> <serialNumber> adds a book to the system\n\nADD COLLECTION " + 
    "<filename> adds a collection of books to the system\nSAVE COLLECTION <filename> saves the " + 
    "system to a csv file\n\nCOMMON <memberNumber1> <memberNumber2> ... outputs the common books " +
    "in members\' history";
  
  private List<Book> books;
  private List<Member> members;
  private static Integer nextMemberNumber;
  
  /*
  Constructor
  */
  public Library() {
    this.books = new ArrayList<Book>();
    this.members = new ArrayList<Member>();
    nextMemberNumber = 100000;
  }
  
  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints out the formatted strings for all books in the system.
           If there are no books in the system, output "No books in system."
          
           Invoked by "LIST ALL", this method prints out either the short string or long string of all books 
           (based on the LONG flag), seperated by new lines.
  Arguments: fullString (boolean) - Whether to print short or long strings
  */
  public void getAllBooks​(boolean fullString) {
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    List<String> BookStrings = new ArrayList<String>();

    for (Book b: this.books) {

      if (b == null) {
        continue;
      }

      // Adds the short or long string based on the flag fullString
      if (fullString) {
        BookStrings.add(b.longString());

      } else {
        BookStrings.add(b.shortString());
      }
    }

    // Ensures correct whitespace for the printing
    if (fullString) {
      System.out.println(String.join("\n\n", BookStrings));
    } else {
      System.out.println(String.join("\n", BookStrings));
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints out the formatted strings for all available books in the system.
           If there are no books in the system, output "No books in system."
           If there are no available books, output "No books available."

           Invoked by "LIST AVAILABLE", this method prints out either the short string or long string 
           of all available books (based on LONG flag), seperated by new lines. 
  Arguments: fullString (boolean) - Whether to print short or long strings
  */
  public void getAvailableBooks​(boolean fullString) {
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    List<String> availableBookStrings = new ArrayList<String>();

    int count = 0;

    for (Book b: this.books) {

      if (b == null) {
        continue;
      }

      // Skips if the book is unavailable
      if (b.isRented()) {
        continue;
      }

      // Adds the short or long string based on the flag fullString
      if (fullString) {
        availableBookStrings.add(b.longString());

      } else {
        availableBookStrings.add(b.shortString());
      }
      count++;
    }
    
    if (count < 1) {
      System.out.println("No books available.");
      return;
    }

    if (fullString) {
      System.out.println(String.join("\n\n", availableBookStrings));
    } else {
      System.out.println(String.join("\n", availableBookStrings));
    }

  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints out the number of copies of each book in the system.
           Books are considered copies if they have the same short string.
           If there are no books in the system, output "No books in system."

           Invoked by "NUMBER COPIES", this method prints out the number of copies present of each book, 
           separated by new lines, sorted lexicographically. 
           
  Format: "[short string]: [number]"
  */
  public void getCopies() {

    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    Map<String,Integer> copies = new HashMap<String,Integer>();

    // Loops through this.books and counts the copies for each book in a hashmap
    // This is based on matching short strings
    for (Book b: this.books) {
      if (copies.containsKey(b.shortString())) {
        copies.put(b.shortString(), copies.get(b.shortString())+1);
      } else {
        copies.put(b.shortString(), 1);
      }
    }

    // Iterates through the hashmap and adds each book and its amount of copies as a string to an array.
    ArrayList<String> outputLines = new ArrayList<String>();

    Iterator it = copies.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      outputLines.add(pair.getKey() + ": " + pair.getValue());
    }

    // Sorts the array of strings lexicographically
    Collections.sort(outputLines);

    // Prints out the sorted strings
    for (String line: outputLines) {
      System.out.println(line);
    }

  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints out all the genres in the system.

           Invoked by "LIST GENRES", this method prints out the list of genres stored in the system.
           Each genre is only printed once, in alphabetical order.
           If there are no books, output "No books in system."

  */
  public void getGenres() {

    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    // Loops through this.books, and whenever a genre not already on the 
    // genres list is found, it is added to the genres list.
    List<String> genres = new ArrayList<String>();
    for (Book b: this.books) {
      // Checks the genre is not already on the list
      if (!genres.contains(b.getGenre())) {
        genres.add(b.getGenre());
      }
    }

    // Sorts the genres lexicographically.
    Collections.sort(genres);

    // Prints the genres.
    for (String g: genres) {
      System.out.println(g);
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints out all the authors in the system.

           Invoked by "LIST AUTHORS", this method prints out the list of authors in the system. 

           Each genre should only be printed once, and they should be printed in alphabetical order.
           If there are no books, output "No books in system."
  */
  public void getAuthors() {
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    List<String> authors = new ArrayList<String>();

    // Loops through this.books, and whenever an author not already on the 
    // authors list is found, it is added to the authors list.
    for (Book b: this.books) {
      if (!authors.contains(b.getAuthor())) {
        authors.add(b.getAuthor());
      }
    }

    // Sorts the authors lexicographically.
    Collections.sort(authors);

    // Prints the authors.
    for (String a: authors) {
      System.out.println(a);
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints all books in the system with the specified genre using the book filterAuthor method

           Invoked by "GENRE [genre]", this method outputs all the books in the system with the specified genre. 
           Each book has its short string printed on a new line, and books ordered by serial number.

           If there are no books, output "No books in system."
           If there are no books of the specified genre, output "No books with genre [genre]."

  Arguments: genre (String)- The genre to filter by
  */
  public void getBooksByGenre​(String genre) {
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    List<Book> filteredBooks = Book.filterGenre​(this.books, genre);

    if (filteredBooks.size()<1) {
      System.out.printf("No books with genre %s.%n", genre);
      return;
    }

    for (Book b: filteredBooks) {
      System.out.println(b.shortString());
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints all books in the system by the specified author.
           Invoked by "AUTHOR [author]", this method outputs all 
           the books in the system written by the specified author. 
           Each book has its short string printed on a new line, and books ordered by serial number.

           If there are no books, output "No books in system."
           If there are no books by the specified author, output "No books by [author]."

  Arguments: author (String) - The author to filter by
  */
  public void getBooksByAuthor​(String author) {
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    List<Book> filteredBooks = Book.filterAuthor​(this.books, author);

    if (filteredBooks.size()<1) {
      System.out.printf("No books by %s.%n", author);
      return;
    }

    for (Book b: filteredBooks) {
      System.out.println(b.shortString());
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints either the short or long string of the specified book.
           Invoked by the command "BOOK [serialNumber] [LONG]", 
           this method prints out the details for the specified book.
           
           If there are no books, output "No books in system."
           If the book does not exist, output "No such book in system."
           If fullString is true, output the long string of the book.
           If fullString is false, output the short string of the book.

  Arguments: serialNumber (String) - The serial number of the book, fullString - Whether to print short or long string
  */
  public void getBook​(String serialNumber, boolean fullString) {

    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    // Loops through this.books until it finds a matching serial number, then prints the long/short string and returns
    for (Book b: this.books) {

      if (b.getSerialNumber().equals(serialNumber)) {
        if (fullString) {
          System.out.println(b.longString());
        } else {
          System.out.println(b.shortString());
        }
        return;
      }
    }

    // If no book matched, the return in the if statement was not triggered and instead prints here.
    System.out.println("No such book in system.");
  }

  // ----------------------------------------------------------------------------------------------
  
  /*
  Purpose: Prints out all the member numbers of members who have previously rented a book.

           Invoked by the command "BOOK HISTORY [serialNumber]", this method outputs every member 
           that has rented the given book, in the order of rents.

           If the book does not exist in the system, output "No such book in system."
           If the book has not been rented, output "No rental history."

  Arguments: serialNumber (String) - The serial number of the book
  */
  public void bookHistory​(String serialNumber) {
    if (serialNumber == null) {
      System.out.println("No such book in system.");
      return;
    }

    boolean found = false;

    // Loops until it finds a matchin serial number
    for (Book b: this.books) {
      
      if (b.getSerialNumber().equals(serialNumber)) {
        found = true;

        if (b.renterHistory().size() < 1) {
          System.out.println("No rental history.");
          return;
        }

        for (Member m: b.renterHistory()) {
          System.out.println(m.getMemberNumber());
        }

        break;
      }
    }

    if (!found) {
      System.out.println("No such book in system.");
      return;
    }

    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Adds a book to the system by reading it from a csv file using the Book readBook() method.
           Invoked by the command "ADD BOOK [file] [serialNumber]", this method reads the given file, 
           searches for the serial number, and then adds the book to the system.

           If the file does not exist, output "No such file."
           If the book does not exist within the file, output "No such book in file."
           If the book's serial number is already present in the system, output "Book already exists in system."
           If the book is successfully added, output "Successfully added: [shortstring]."

  Arguments: bookFile (String) - The csv file to read, serialNumber (String) - The serial number of the book
  */
  public void addBook(String bookFile, String serialNumber){
    if (serialNumber == null) {
      System.out.println("No such book in file.");
      return;
    }
    if (bookFile == null){
      System.out.println("No such file.");
      return;
    }
    //Checking if the book is already in the system
    for (Book book: this.books) {
      if (book.getSerialNumber().equals(serialNumber)) {
        System.out.println("Book already exists in system.");
        return;
      }
    }

    Book b = Book.readBook(bookFile, serialNumber);

    if (b == null) {
      // Checks if the null return was due to the file or book not being found.
      try {
        File myFile = new File(bookFile);
        Scanner scan = new Scanner(myFile); 
      } catch (FileNotFoundException e) {
        System.out.println("No such file.");
        return;
      } 
      System.out.println("No such book in file.");
      return;

    }
    
    this.books.add(b);

    System.out.println("Successfully added: "+ b.shortString()+".");
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: A helper method that takes in a string member number, 
           and returns the index in this.members of the corresponding member.
           If the member does not exist, returns -1.

  Arguments: memberNumber (String) - the member number of the member that's index needs finding.

  Returns: an integer index of the corresponding member in this.members
  */
  private int getMemberIndex(String memberNumber) {
    if (memberNumber == null) {
      return -1;
    }

    // Loops through this.books unitl the member Number is found, then returns it's index.
    for (int i = 0; i < this.members.size(); i ++) {
      if (this.members.get(i).getMemberNumber().equals(memberNumber)) {
        return i;
      }
    }
    return -1;
  }

  /*
  Purpose: A helper method that takes in a string serial number, 
           and returns the index in this.books of the corresponding book.
           If the book does not exist, returns -1.

  Arguments: serialNumber (String) - the serial number of the book that's index needs finding.

  Returns: an integer index of the corresponding book in this.books
  */
  private int getBookIndex(String serialNumber) {
    if (serialNumber == null) {
      return -1;
    }

    int bookIndex = -1;
    
    for (int i = 0; i < this.books.size(); i ++) {
      if (this.books.get(i).getSerialNumber().equals(serialNumber)) {
        bookIndex = i;
        break;
      }
    }

    return bookIndex;
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Loans out a book to a member within the system.

           Invoked by the command "RENT [memberNumber] [serialNumber]", this method loans out the specified book to the given member.
           
           If there are no members in the system, output "No members in system."
           If there are no books in the system, output "No books in system."
           If the member does not exist, output "No such member in system."
           If the book does not exist, output "No such book in system."
           If the book is already being loaned out, output "Book is currently unavailable."
           If the book is successfully loaned out, output "Success."

  Arguments: memberNumber (String) - The members' member number, serialNumber (String) - The book's serial number
  */
  public void rentBook(String memberNumber, String serialNumber) {

    // Checks (see method purpose for explanation)
    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    // Getting the indexed in this.books and this.members for the given member and serial number.
    int memberIndex = this.getMemberIndex(memberNumber);
    int bookIndex = this.getBookIndex(serialNumber);

    if (bookIndex == -1) {
      System.out.println("No such book in system.");
      return;
    }

    if (memberIndex == -1) {
      System.out.println("No such member in system.");
      return;
    }

    if (this.books.get(bookIndex).isRented()) {
      System.out.println("Book is currently unavailable.");
      return;
    }

    // Renting out the book
    this.members.get(memberIndex).rent(this.books.get(bookIndex));

    System.out.println("Success.");
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Returns a book to the system.

           Invoked by the command "RELINQUISH [memberNumber] [serialNumber]", this method gets 
           the specified member to return the given book back to the system.
           
           If there are no members in the system, output "No members in system."
           If there are no books in the system, output "No books in system."
           If the member does not exist, output "No such member in system."
           If the book does not exist, output "No such book in system."
           If the book is not being loaned out by the member, output "Unable to return book."
           If the book is successfully return, output "Success."

  Arguments: memberNumber (String) - The members' member number, serialNumber (String) - The book's serial number
  */
  public void relinquishBook(String memberNumber, String serialNumber) {

    // Checks (see method purpose for description)
    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }
    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    // Getting the indexed in this.books and this.members for the given member and serial number.
    int memberIndex = this.getMemberIndex(memberNumber);
    int bookIndex = this.getBookIndex(serialNumber);

    if (bookIndex == -1) {
      System.out.println("No such book in system.");
      return;
    }

    if (memberIndex == -1) {
      System.out.println("No such member in system.");
      return;
    }
    Book b = this.books.get(bookIndex);
    Member m = this.members.get(memberIndex);

    if (!b.isRented() || b.getRenter() != m) {
      System.out.println("Unable to return book.");
      return;
    }

    // Relinquishing the book
    m.relinquish(b);

    System.out.println("Success.");
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Makes a member return all books they are currently renting.

           Invoked by the command "RELINQUISH ALL [memberNumber]", this method causes the 
           specified member to return all books they are currently renting.

           If there are no members in the system, output "No members in system."
           If the member does not exist, output "No such member in system."
           If the member does exist, return all books and output "Success."

  Arguments: memberNumber (String) - The member's member number
  */
  public void relinquishAll(String memberNumber) {

    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }

    // Gets the index of the corresponding member in this.members
    int memberIndex = this.getMemberIndex(memberNumber);

    if (memberIndex == -1) {
      System.out.println("No such member in system.");
      return;
    }

    // uses the member relinquish all method.
    this.members.get(memberIndex).relinquishAll();
    System.out.println("Success.");
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints the details of the specified member.

           Invoked by the command "MEMBER [memberNumber]", this method outputs the details of the specified member.

           If there are no members in the system, output "No members in system."
           If the member does not exist, output "No such member in system."

  Format: [memberNumber]: [member name]

  Arguments: memberNumber (String) - The member's member number
  */
  public void getMember(String memberNumber) {

    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }

    if (memberNumber == null) {
      System.out.println("No such member in system.");
      return;
    }

    int memberIndex = this.getMemberIndex(memberNumber);

    if (memberIndex == -1) {
      System.out.println("No such member in system.");
      return;
    }

    Member mem = this.members.get(memberIndex);

    System.out.printf("%s: %s%n", mem.getMemberNumber(),mem.getName());

  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints a list of all the books a member is currently renting.

           Invoked by the command "MEMBER BOOKS [memberNumber]", this method prints out all the books the 
            specified member is currently renting, in the order that they were rented. 
           Each book has its short string printed on a new line.

           If there are no members in the system, output "No members in system."
           If the member does not exist, output "No such member in system."
           If the member is not renting any books, output "Member not currently renting."


  Arguments: memberNumber (String) - The member's member number
  */
  public void getMemberBooks(String memberNumber) {
    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }

    int memberIndex = this.getMemberIndex(memberNumber);

    if (memberIndex == -1) {
      System.out.println("No such member in system.");
      return;
    }

    if (this.members.get(memberIndex).renting().size() < 1) {
      System.out.println("Member not currently renting.");
      return;
    }

    for (Book b: this.members.get(memberIndex).renting()) {
      System.out.println(b.shortString());
    }

  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints a list of all the books a member has previously rented.

           Invoked by the command "MEMBER HISTORY [memberNumber]", this method prints out all the books 
            the specified member has previously rented, in the order that they were returned. 
          Each book should have its short string printed on a new line.

           If there are no members in the system, output "No members in system."
           If the member does not exist, output "No such member in system."
           If the member has not rented any books, output "No rental history for member."

  Arguments: memberNumber (String) - The member's member number
  */
  public void memberRentalHistory(String memberNumber) {
    if (memberNumber == null) {
      return;
    }
    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }

    int memberIndex = this.getMemberIndex(memberNumber);

    if (memberIndex == -1) {
      System.out.println("No such member in system.");
      return;
    }

    if (this.members.get(memberIndex).history().size() < 1) {
      System.out.println("No rental history for member.");
      return;
    }

    for (Book b: this.members.get(memberIndex).history()) {
      System.out.println(b.shortString());
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Adds the collection of books stored in a csv file to the system.

           Invoked by the command "ADD COLLECTION [filename]", this method reads 
            all the books from a csv file and adds them to the system.

           If the file does not exist, output "No such collection."
           If no books can be added (e.g. all serial numbers already present in system), 
            output "No books have been added to the system."
           If at least one book has been added to the system, output "[number] books successfully added."

  Arguments: filename (String) - The csv file storing the collection of books
  */
  public void addCollection​(String filename) {
    if (filename == null) {
      System.out.println("No such collection.");
      return;
    }
    List<Book> booksToAdd = Book.readBookCollection​(filename);
    int count = 0;

    if (booksToAdd == null) {
      System.out.println("No such collection.");
      return;
    }

    // Loops through each book to be added, and compares it to all the 
    //  books in this.books. If there is a match, it is skipped.
    for (Book b: booksToAdd) {

      boolean found = false;

      for (Book compare: this.books) {
        if (compare.getSerialNumber().equals(b.getSerialNumber())) {
          found = true;
          break;
        }
      }

      if (!found) {
        count ++;
        this.books.add(b);
      }
    }

    if (count == 0) {
      System.out.println("No books have been added to the system.");
    } else {
      System.out.printf("%d books successfully added.%n", count);
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Saves the current collection of books in the system to a csv file using 
            the Book saveBookCollection method.

           Invoked by the command "SAVE COLLECTION [filename]", this method saves all 
            the books stored in the system to a csv file (in the same format as can be 
            read with "ADD COLLECTION [filename").

           If there are no books in the system, output "No books in system."
           If there are books, write them to the file and output "Success."
  Arguments: filename (String) - The csv file to write the collection to
  */
  public void saveCollection(String filename) {

    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    Book.saveBookCollection​(filename, this.books);

    System.out.println("Success.");
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Adds a member to the system, taking in a name to give the Member object.

           Invoked by the command "ADD MEMBER [name]", this method creates a new member 
            with the specified name and adds them to the system. 

           The first member will always have member number 100000, and subsequent members 
            will increment from there (so the second member has number 100001).
           Once the member is successfully added to the system, output "Success."

  Arguments: name (String) - The name of the member
  */
  public void addMember​(String name) {
    // Converts the int nextMemberNumber to a string.
    String memberNumber = String.valueOf(nextMemberNumber);

    // Uses the static class variable nextMemberNumber to store the next available member number.
    nextMemberNumber++;

    Member m = new Member(name, memberNumber);

    members.add(m);
    System.out.println("Success.");
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Prints out all the books that all members provided have previously 
            rented using Member method commonBooks().

           Invoked by the command "COMMON [member1] [member2] ...", this method lists 
            out the short strings of all the books that have been rented by listed members, 
            ordered by serial number.

           If there are no members in the system, output "No members in system."
           If there are no books in the system, output "No books in system."
           If at least one of the members does not exist, output "No such member in system."
           If there are duplicate members provided, output "Duplicate members provided."
           If there are no common books, output "No common books."

  Arguments: memberNumbers (String[]) - The array of member numbers
  */
  public void common(String[] memberNumbers) {
    if (this.members.size() < 1) {
      System.out.println("No members in system.");
      return;
    }

    if (this.books.size() < 1) {
      System.out.println("No books in system.");
      return;
    }

    // Converts the array of member numbers to an array of the corresponding Members.
    Member[] mems = new Member[memberNumbers.length];
    int index = 0;

    //Checking for duplicates
    for (String n: memberNumbers) {

      int matches = 0;

      for (String l: memberNumbers) {
        if (n.equals(l)) {
          matches ++;
        }
      }

      if (matches > 1) {
        System.out.println("Duplicate members provided.");
        return;
      }
    }

    // Takes the checked member numbers and retrieves the corresponding members.
    for (String n: memberNumbers) {
      int memberIndex = getMemberIndex(n);
      if (memberIndex == -1) {
        System.out.println("No such member in system.");
        return;
      }
      mems[index] = this.members.get(index);
      index ++;
    }
    

    List<Book> commonBooks = Member.commonBooks​(mems);

    if (commonBooks.size() < 1) {
      System.out.println("No common books.");
      return;
    }

    for (Book b: commonBooks) {
      System.out.println(b.shortString());
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: The main method of the entire program.
  Arguments: args (String[]) - Command line arguments
  */
  public static void main(String[] args) {
    Library lib = new Library();
    lib.run();
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Takes user input and converts it to a String array, which is passed to runCommand
  */
  public void run() {
    Scanner keyboard = new Scanner(System.in);
    System.out.print("user: ");

    while (keyboard.hasNextLine()) {
      
      String commandString = keyboard.nextLine();
      String[] command = commandString.toUpperCase().split(" ");

      // commandLower is used for filenames, member names, authors and genres where capitalisation matters
      String[] commandLower = commandString.split(" ");

      if (command[0].equals("EXIT")) {
        System.out.println("Ending Library process.");
        return;
      }
      
      this.runCommand(command, commandLower);
      System.out.println();
      System.out.print("user: ");
    }
  }

  // ----------------------------------------------------------------------------------------------

  /*
  Purpose: Takes in a String array of words in the commad, and calls the corresponding methods.
  */
  public void runCommand(String[] command, String[] commandLower) {
    String c1 = command[0];
    boolean longStringFormat = false;

    // Processes single word commands first
    if (c1.equals("EXIT")) {
      return;
    } else if (c1.equals("COMMANDS")) {
      System.out.println(Library.HELP_STRING);
      return;
    }

    String c2 = command[1];
    
    if (c1.equals("LIST")) {

      if (command.length == 3) {
        if (command[2].equals("LONG")) {
          longStringFormat = true;
        }
      }

      if (c2.equals("ALL")) {
        this.getAllBooks​(longStringFormat);
        return;

      } else if (c2.equals("AVAILABLE")) {
        this.getAvailableBooks​(longStringFormat);
        return;

      } else if (c2.equals("GENRES")) {
        this.getGenres();
        return;

      } else if (c2.equals("AUTHORS")) {
        this.getAuthors();
        return;
      }

    } else if (c1.equals("NUMBER")) {
      
      if (c2.equals("COPIES")) {
        this.getCopies();
        return;
      }

    } else if (c1.equals("GENRE")) {
      String genre = "";

      // Allows for multi word genres, takes all words after GENRE and combines them into a string
      for (int i = 1; i < commandLower.length; i ++) {
        genre += commandLower[i];
        if (i < commandLower.length-1) {
          genre += " ";
        }
      }
      this.getBooksByGenre​(genre);
      return;
      
    } else if (c1.equals("AUTHOR")) {
      String author = "";
      // Allows for multi word names, takes all words after AUTHOR and combines them into a string
      for (int i = 1; i < commandLower.length; i ++) {
        author += commandLower[i];
        if (i < commandLower.length-1) {
          author += " ";
        }
      }
      this.getBooksByAuthor​(author);
      return;
      
    } else if (c1.equals("BOOK")) {
      if (c2.equals("HISTORY")) {
        String serial = command[2];
        this.bookHistory​(serial);
        return;
      }

      if (command.length == 3) {
        if (command[2].equals("LONG")) {
          longStringFormat = true;
        }
      }
      this.getBook(c2, longStringFormat);
      return;
      
      
    } else if (c1.equals("MEMBER")) {
      if (c2.equals("BOOKS")) {
        this.getMemberBooks(command[2]);
        return;
      } 
      if (c2.equals("HISTORY")){
        this.memberRentalHistory(command[2]);
        return;
      }

      this.getMember(c2);
      return;
      
    } else if (c1.equals("RENT")) {
      this.rentBook(c2, command[2]);
      
    } else if (c1.equals("RELINQUISH")) {
      if (c2.equals("ALL")) {
        this.relinquishAll(command[2]);
        return;
      }
      this.relinquishBook(c2, command[2]);
      return;
      
    } else if (c1.equals("ADD")) {

      if (c2.equals("MEMBER")) {
        // Allows for multi word names, takes all words after Member and combines them into a string
        String name = "";
        int len = command.length;
        String[] nameParts = Arrays.copyOfRange(commandLower, 2, len);
        
        for (String p: nameParts) {
          name += p;
          name += " ";
        }
        name  = name.substring(0, name.length()-1);
        this.addMember​(name);
        return;

      } else if (c2.equals("BOOK")) {
        this.addBook(commandLower[2], commandLower[3]);
        return;
      } else if (c2.equals("COLLECTION")) {
        this.addCollection​(commandLower[2]);
        return;
      }
      
    } else if (c1.equals("SAVE")) {
      this.saveCollection(commandLower[2]);
      return;
      
    } else if (c1.equals("COMMON")) {
      int len = command.length;
      String[] members = Arrays.copyOfRange(command, 1, len);
      

      this.common(members);
      
    }
  }
}
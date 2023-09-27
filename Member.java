import java.util.List;
import java.util.ArrayList;

public class Member {
  private String name;
  private String memberNumber;
  private List<Book> history;
  private List<Book> currentlyRented;
  private List<String> historySerial;

  /*
  Constructor
  */
  public Member(String name, String memberNumber) {
    this.name = name;
    this.memberNumber = memberNumber;
    this.history = new ArrayList<Book>();
    this.currentlyRented = new ArrayList<Book>();
    this.historySerial = new ArrayList<String>();
  }

  /*
  Purpose: Returns the name of the member.
  Returns: The name of the member (String).
  */
  public String getName() {
    return this.name;
  }

  /*
  Purpose: Returns the member number of the member.
  Returns: The member number of the member (String).
  */
  public String getMemberNumber() {
    return this.memberNumber;
  }

  /*
  Purpose:  Returns the history of books rented, in the order they were returned (oldest first).
            Books currently being rented are not included in this list.
  Returns:  The list of books that have been rented (List<Book>).
  */
  public List<Book> history() {
    return this.history;
  }

  /*
  Purpose: Returns the list of books currently being rented, in the order they were rented.
  Returns: The list of books currently being rented (List<Book>).
  */
  public List<Book> renting() {
    return this.currentlyRented;
  }

  /*
  Purpose: Rents the given book by calling the book rent method and adding the book to the member 
           current rentals list.
            If the book does not exist or it is already being rented, do nothing and return false.
            Otherwise, set the renter of the book to this instance of member and return true.
  Arguments: book - The book to rent.
  Returns: The outcome of the rental transaction (boolean).
  */
  public boolean rent(Book book) {
    if (book == null) {
      return false;
    }
    // checks if the book is already being rented by the member
    if (this.currentlyRented.contains(book)) {
      return false;
    }

    this.currentlyRented.add(book);
    return book.rent(this);
  }

  /*
  Purpose: Returns the book to the library by calling the book relinquish method, 
           removing it from current rentals and adding it to history.
              If the book doesn't exist, or the member isn't renting the book, return false.
              Otherwise, set the renter of the book to null, add it to the rental history, and return true.
  Arguments: book - The book to return.
  Returns: The outcome of the rental transaction (boolean).
  */
  public boolean relinquish(Book book) {
    if (book == null) {
      return false;
    }

    if (!this.currentlyRented.contains(book)) {
      return false;
    }

    this.history.add(book);
    this.historySerial.add(book.getSerialNumber());
    this.currentlyRented.remove(book);

    return book.relinquish(this);
  }

  /*
  Purpose: Returns all books rented by the member. Loops through the currently 
           rented list and calls relinquish on each one.
  */
  public void relinquishAll() {

    List<Book> temp = new ArrayList<Book>();

    for (Book b: this.currentlyRented) {
      temp.add(b);
    }

    for (Book b: temp) {
      this.relinquish(b);
    }
    
  }

  /* 
  Purpose:  A helper method that implements an insertion sort that will 
            insert a book object into an array based on its serial number. 
  Arguments:  books - The list of books the book is to be inserted into
              book - The book to be inserted
  */
  private static void addSorted(List<Book> books, Book book) {
    for (int i = 0; i < books.size(); i ++) {
      if (Integer.parseInt(book.getSerialNumber()) == Integer.parseInt(books.get(i).getSerialNumber())) {
        return;
      }
      if (Integer.parseInt(book.getSerialNumber()) < Integer.parseInt(books.get(i).getSerialNumber())) {
        books.add(i,book);
        return;
      }
    }
    books.add(book);
  }

  /*
  Purpose: Returns the intersection of the members' histories, ordered by serial number.
            If members is invalid, return null.
            Otherwise, return the sorted intersection of books.
            Each book will appear a maxiumu of one times in the intersection.
            If only 1 member is given, their history is returned.
  Arguments: members - The array of members (Member[]).
  Returns: The sorted intersection of books.
  */
  public static List<Book> commonBooks​(Member[] members) {

    if (members == null || members.length == 0) {
      return null;
    }
    if (members[0] == null) {
      return null;
    }

    // All books in the intersection must be in the first member's history, 
    // so this is taken as the books to check.
    List<Book> booksToCheck = members[0].history();

    ArrayList<Book> common = new ArrayList<Book>();

    boolean found = true;

    // Checks for any null members in the array.
    for (Member m: members) {
      if (m == null) {
        return null;
      }
    }

    // For each book, the members are looped through. If the book is not found in a member's 
    // history, the code moves onto checking the next book. If the book is not not found in any 
    // of the members (it occurs in all), it is added to the common books list using the addsorted method.
    for (Book b: booksToCheck) {
      found = true;

      for (Member m: members) {
        if (m == null) {
          return null;
        }
        
        if (!m.historySerial.contains(b.getSerialNumber())) {
          found = false;
          break;
        }
      }

      if (found) {
        addSorted(common, b);
      }
    }
    return common;
  }
}
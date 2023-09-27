# Librarian
This was my first exercise in Java OOP. The task was to take [the given javadoc](/javadoc/index.html), implement and test it. 

## How to run
To run the code, run the follwing commands
```
javac Library.java
java Library
```

## Commands
Once the program is running, you will be prompted with
```
user:
```
at which point you can enter any of the following commands...
|Command|Function|
|-|-|
|`EXIT`|ends the library process|
|`COMMANDS`|outputs the help string|
|`LIST ALL [LONG]`|outputs either the short or long string for all books|
|`LIST AVAILABLE [LONG]`|outputs either the short of long string for all available books|
|`NUMBER COPIES`|outputs the number of copies of each book|
|`LIST GENRES`|outputs the name of every genre in the system|
|`LIST AUTHORS`|outputs the name of every author in the system|
|`GENRE <genre>`|outputs the short string of every book with the specified genre|
|`AUTHOR <author>`|outputs the short string of every book by the specified author|
|`BOOK <serialNumber> [LONG]`|outputs either the short or long string for the specified book|
|`BOOK HISTORY <serialNumber>`|outputs the rental history of the specified book|
|`MEMBER <memberNumber>`|outputs the information of the specified member|
|`MEMBER BOOKS <memberNumber>`|outputs the books currently rented by the specified member|
|`MEMBER HISTORY <memberNumber>`|outputs the rental history of the specified member|
|`RENT <memberNumber> <serialNumber>`|loans out the specified book to the given member|
|`RELINQUISH <memberNumber> <serialNumber>`|returns the specified book from the member|
|`RELINQUISH ALL <memberNumber>`|returns all books rented by the specified member|
|`ADD MEMBER <name>`|adds a member to the system|
|`ADD BOOK <filename> <serialNumber>`|adds a book to the system|
|`ADD COLLECTION <filename>`|adds a collection of books to the system|
|`SAVE COLLECTION <filename>`|saves the system to a csv file|
|`COMMON <memberNumber1> <memberNumber2> ...`| outputs the common books in members’ history|



## Testing Explanations

To run tests, use `bash runTests.sh`

`addMember`:
Adding and retrieving members:
Tests adding both single and multi word member names
Tests calling member with existing and non-existing member numbers, and with our any members in the system.

`addBook`:
This test looks at the processes of adding and retrieving books.
Tests calling BOOK on a book that doesn’t exist, and then with and without the LONG flag
Tests adding individual books, with an existing and non-existing file, and with an existing and non-existing serial number
Tests adding a collection from an existing and non-existing file, as well as adding a collection where some or all of the books are already in the system.

`genreAndAuthor`:
This test focuses on the genre and author functions.
The author command is tested with:
- No books in the system
- A single word author name
- A multi word author name
- An author not in the system

Similarly, the genre command is tested with
- No books in the system
- A single word genre
- A multi word genre
- A genre not in the system

`Copies`:
Tests number copies with and without books in the system.

`listAuthorsGenres`:
List authors and list genres are tested with:
- No books in the system
- Books in the system 

`listAll`:
List all is tested with:
- No books in the system
- With the long flag
- Without the long flag

`memberBooks`:
Test 7 focuses on the member books command it tests the command with:
- No members in the system
- No such member in system
- Member not currently renting
- Member renting books.

`listAvailable`:
Tests the list available command with:
- No books in system
- No available books
- Available books exist, with and without the long flag.

`Rent`:
Tests the rent function with:
- No members in system
- No books in system
- No such member
- No such book
- Book not available
- Success

`Relinquish`:
Tests the relinquish command with:
- No members in system
- No books in system
- No such member
- No such book
- Unable to return (not being loaned out/not being loaned out by member)
- Success

`relinquishAll`:
Tests the relinquish all command with:
- No members in system
- No such member
- A member currently renting multiple books
- A member currently renting 1 book

`memberHistory`:
Tests the member history functionality with:
- No members in system
- No such member
- No rental history
- Rental history from the relinquish command
- Rental history from the relish all command

`bookHistory`:
Tests the book history command, with:
- No books in system
- No rental history for the books
- A book with rental history

`Common`:
Tests the common command with:
- No members in system
- No books in system
- No such member(s)
- Duplicate members
- 1 member
- 2 members
- 3+ members
- No common books

`Commands`:
- Checks the COMMANDS command (lower and uppercase), and the exit command

`saveCollection`:
Tests the save collection command with:
- no books in system
- books in system
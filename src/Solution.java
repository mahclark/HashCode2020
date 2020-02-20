import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Book implements Comparable<Book> {
    int index;
    int score;

    public Book(int index, int score) {
        this.index = index;
        this.score = score;
    }

    @Override
    public int compareTo(Book o) {
        return Integer.compare(o.score, score);
    }
}

class Library {
    int index;
    int numBooks;
    int signup;
    int booksPerDay;
    TreeSet<Book> books;

    public Library(int index, int numBooks, int signup, int booksPerDay) {
        this.index = index;
        this.numBooks = numBooks;
        this.signup = signup;
        this.booksPerDay = booksPerDay;
    }

    void setBooks(TreeSet<Book> books) {
        this.books = books;
    }
}

public class Solution {

    public static void main(String[] args) throws IOException {
        List<String> paths = List.of(
                "a_example",
                "b_read_on",
                "c_incunabula",
                "d_tough_choices",
                "e_so_many_books",
                "f_libraries_of_the_world"
        );

        for (String path : paths) {
            File file = new File("src\\input\\" + path + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String[] line1 = br.readLine().split(" ");
            int numBooks = Integer.parseInt(line1[0]);
            int numLibs = Integer.parseInt(line1[1]);
            int numDays =  Integer.parseInt(line1[2]);

            List<Integer> bookScores = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

            for (int libIndex = 0; libIndex < numLibs; libIndex++) {
                String[] firstLine = br.readLine().split(" ");
                Library library = new Library(libIndex, Integer.parseInt(firstLine[0]), Integer.parseInt(firstLine[1]), Integer.parseInt(firstLine[2]));
                List<Integer> books = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                TreeSet<Book> bookSet = new TreeSet<>();
                for (int book : books) {
                    bookSet.add(new Book(book, bookScores.get(book)));
                }

                library.setBooks(bookSet);
            }
        }
    }
}

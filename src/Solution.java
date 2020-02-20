import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

public class Solution {

    static HashSet<Book> taken = new HashSet<>();

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

        int maxScore(int daysLeft) {
            daysLeft -= signup;
            int bookOutput = Math.min(Math.max(daysLeft*booksPerDay, 0), numBooks);

            int i = 0;
            int score = 0;
            for (Book book : books) {
                if (taken.contains(book)) {
                    continue;
                }

                i++;
                if (i > bookOutput) {
                    break;
                }
                score += book.score;
            }

            return score;
        }

        int priority(int totalDays) {
            int p = 0;
            p += numBooks;
//            p += 1.01   *   maxScore(totalDays);
//            p -= 100    *   signup;
//            p += 0      *   booksPerDay;

            return p;
        }

        List<Book> getBooks(int daysLeft) {
            daysLeft -= signup;
            int bookOutput = Math.min(Math.max(daysLeft*booksPerDay, 0), numBooks);

            int i = 0;
            List<Book> chosenBooks = new ArrayList<>();
            for (Book book : books) {
                if (taken.contains(book)) {
                    continue;
                }

                i++;
                if (i > bookOutput) {
                    break;
                }
                chosenBooks.add(book);
                taken.add(book);
            }

            return chosenBooks;
        }
    }


    public static List<String> solve(Set<Library> libs, int daysLeft) {
        List<String> lines = new ArrayList<>();
        int numLibs = 0;

        while (daysLeft > 0) {
            Library bestLib = null;
            int bestScore = 0;
            for (Library lib : libs) {
                int score = lib.priority(daysLeft);
                if (score > bestScore) {
                    bestScore = score;
                    bestLib = lib;
                }
            }

            if (bestLib == null) break;
            numLibs += 1;

            List<Book> books = bestLib.getBooks(daysLeft);
            daysLeft -= bestLib.signup;
            libs.remove(bestLib);
            if (books.size() == 0) {
                numLibs -= 1;
            } else {
                lines.add(bestLib.index + " " + books.size());

                StringBuilder ans = new StringBuilder();
                for (Book x : books) {
                    ans.append(x.index).append(" ");
                }
                lines.add(ans.toString().trim());
            }
        }

        lines.add(0, String.valueOf(numLibs));

        return lines;
    }

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
            Set<Library> libraries = new HashSet<>();

            for (int libIndex = 0; libIndex < numLibs; libIndex++) {
                String[] firstLine = br.readLine().split(" ");
                Library library = new Solution().new Library(libIndex, Integer.parseInt(firstLine[0]), Integer.parseInt(firstLine[1]), Integer.parseInt(firstLine[2]));
                List<Integer> books = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                TreeSet<Book> bookSet = new TreeSet<>();
                for (int book : books) {
                    bookSet.add(new Book(book, bookScores.get(book)));
                }

                library.setBooks(bookSet);

                libraries.add(library);
            }

            Path outFile = Paths.get("src\\output\\" + path + ".out");
            Files.write(outFile, Solution.solve(libraries, numDays));
            System.out.println(path + " is done");
        }
    }
}

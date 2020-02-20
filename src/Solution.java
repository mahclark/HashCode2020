import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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

            System.out.println(br.readLine());
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        URL url = new URL("https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj pierwsze trzy cyfry numeru konta: ");
        String userInput = scanner.nextLine();

        if (userInput.length() != 3 || !userInput.matches("\\d{3}")) {
            System.out.println("Podano nieprawidłowy numer. Upewnij się, że wprowadziłeś trzy cyfry.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(userInput)) {
                    String[] parts = line.split("\\t");
                    if (parts.length >= 2) {
                        String bankCode = parts[0];
                        String bankName = parts[1];
                        System.out.println("Your account is in the bank: " + bankName + " (code: " + bankCode + ")");
                        found = true;
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("No bank was found for the provided account number digits.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while fetching the data: " + e.getMessage());
        }
    }
}
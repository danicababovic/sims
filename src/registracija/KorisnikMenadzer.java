package registracija;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KorisnikMenadzer {
    private final List<Korisnik> korisnici = new ArrayList<>();
    private static final String FILE_NAME = "korisnici.csv";

    public KorisnikMenadzer() { loadUsersFromFile(); }

    public void dodajKorisnika(Korisnik korisnik) {
        korisnici.add(korisnik);
        saveUsersToFile();
    }

    public List<Korisnik> getAllUsers() {
        return new ArrayList<>(korisnici);
    }

    public boolean deleteUserByEmail(String email) {
        boolean removed = korisnici.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        if (removed) saveUsersToFile();
        return removed;
    }

    private void saveUsersToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Korisnik u : korisnici) {
                out.println(u.getImeIPrezime() + "," + u.getEmail() + "," + u.getPassword());
            }
        } catch (IOException e) {
            System.err.println("Greška pri snimanju korisnika: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    korisnici.add(new Korisnik(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Greška pri učitavanju korisnika: " + e.getMessage());
        }
    }
}
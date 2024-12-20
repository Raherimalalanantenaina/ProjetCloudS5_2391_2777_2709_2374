package web.projet.fournisseurIdentite.mail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EmailContentLoader {
    public static String loadHTMLFromFile(String filePath) throws IOException {
        Path path = Path.of(filePath); 
        if (!Files.exists(path)) {
            throw new IOException("Le fichier spécifié est introuvable : " + filePath);
        }
        return new String(Files.readAllBytes(path)); 
    }
}

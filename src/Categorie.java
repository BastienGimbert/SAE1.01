import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique; //le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
        this.lexique = new ArrayList<>();
    }


    public String getNom() {
        return nom;
    }


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }


    public void initLexique(String nomFichier) {
        // { nomFichier est le nom d'un fichier texte contenant des mots et des entiers séparés par ':' }
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                String[] ligne = scanner.nextLine().split(":");
                String mot = ligne[0];
                int force = Integer.parseInt(ligne[1]);
                this.lexique.add(new PaireChaineEntier(mot, force));
                i++;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        // { d est une dépêche }
        // => { score de d pour la catégorie }

        ArrayList<String> dep = d.getMots();
        int total = 0;
        for (int i = 0; i < dep.size(); i++) {
            total += UtilitairePaireChaineEntier.entierPourChaine(this.lexique, dep.get(i));
        }
        return total;
    }

}

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {
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
    public ArrayList<Integer> score(Depeche d, ArrayList<Integer> paireEntier) {
        ArrayList<String> dep = d.getMots();
        int total = 0;
        int nbComparaisons = 0;
        for (int i = 0; i < dep.size(); i++) {
            total += UtilitairePaireChaineEntier.entierPourChaine(this.lexique, dep.get(i), new ArrayList<>(Arrays.asList(0,0))).get(0);
            nbComparaisons++;
        }
        paireEntier.set(0, total);
        paireEntier.set(1, paireEntier.get(1)+nbComparaisons);
        return paireEntier;
    }

}

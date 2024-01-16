import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classification {


    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        ArrayList<PaireChaineEntier> scoreParCat;
        int envs = 0;
        int culture = 0;
        int politique = 0;
        int sport = 0;
        int eco = 0;

        try {
            FileWriter file = new FileWriter(nomFichier);

            for (int i = 0; i < depeches.size(); i++) {
                scoreParCat = new ArrayList<>();
                for (Categorie uneCategorie: categories) {
                    scoreParCat.add(new PaireChaineEntier(uneCategorie.getNom(), uneCategorie.score(depeches.get(i))));
                }
                String catCourante = UtilitairePaireChaineEntier.chaineMax(scoreParCat);

                if (i>=0 && i<=100) {
                    if (catCourante.equals("Environnement-Sciences")) {
                        envs++;
                    }
                } else if (i>100 && i<=200) {
                    if (catCourante.equals("Culture")) {
                        culture++;
                    }
                } else if (i>200 && i<=300) {
                    if (catCourante.equals("Economie")) {
                        eco++;
                    }
                } else if (i>300 && i<=400) {
                    if (catCourante.equals("Politique")) {
                        politique++;
                    }
                } else {
                    if (catCourante.equals("Sport")) {
                        sport++;
                    }
                }


                file.write(depeches.get(i).getId()+" : "+catCourante+"\n");
            }

            file.write("ENVIRONNEMENT-SCIENCE: "+envs+"%\n");
            file.write("CULTURE: "+culture+"%\n");
            file.write("ECONOMIE: "+eco+"%\n");
            file.write("POLITIQUE: "+politique+"%\n");
            file.write("SPORTS: "+sport+"%\n");


            file.write("MOYENNE: "+(envs+culture+eco+politique+sport)/5+"%");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();

        for (Depeche uneDep:
             depeches) {
            if (uneDep.getCategorie().equals(categorie)) {
                ArrayList<String> mots = uneDep.getMots();
                for (String mot:
                     mots) {
                    int indCour = UtilitairePaireChaineEntier.entierPourChaine(resultat, mot);
                    if (indCour==0) {
                        resultat.add(new PaireChaineEntier(mot, 0));
                    } else {
                        resultat.get(indCour).setEntier(+1);
                    }
                }
            }
        }
        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
    }

    public static int poidsPourScore(int score) {
        return 0;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

    }

    public static void main(String[] args) {
        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        // Variables
        Scanner lecteur = new Scanner(System.in);

        // Catégorie
        Categorie culture = new Categorie("Culture");
        culture.initLexique("./culture.txt");
        Categorie economie = new Categorie("Economie");
        economie.initLexique("./economie.txt");
        Categorie politique = new Categorie("Politique");
        politique.initLexique("./politique.txt");
        Categorie environnementScience = new Categorie("Environnement-Sciences");
        environnementScience.initLexique("./environnement-sciences.txt");
        Categorie sport = new Categorie("Sport");
        sport.initLexique("./sports.txt");

//        System.out.println(culture.getLexique());
//        for (int i = 0; i < depeches.size(); i++) {
//            depeches.get(i).afficher();
//        }
//        System.out.println("Donnez un mot : ");
//        String saisie = lecteur.nextLine();
//        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(culture.getLexique(), saisie));

        System.out.println(depeches.get(403-1).getContenu());
        System.out.println("score dans culture : "+culture.score(depeches.get(403-1)));
        System.out.println("score dans economie : "+economie.score(depeches.get(403-1)));
        System.out.println("score dans politique : "+politique.score(depeches.get(403-1)));
        System.out.println("score dans environnementScience : "+environnementScience.score(depeches.get(403-1)));
        System.out.println("score dans sport : "+sport.score(depeches.get(403-1)));


        ArrayList<Categorie> vCategorie = new ArrayList<>();
        vCategorie.add(culture);
        vCategorie.add(economie);
        vCategorie.add(politique);
        vCategorie.add(environnementScience);
        vCategorie.add(sport);

        ArrayList<PaireChaineEntier> score = new ArrayList<>();
        for(Categorie uneCategorie : vCategorie){
            score.add(new PaireChaineEntier(uneCategorie.getNom(), uneCategorie.score(depeches.get(403-1))));
        }
        System.out.println(UtilitairePaireChaineEntier.chaineMax(score));

        Classification.classementDepeches(depeches, vCategorie, "./resultats.txt");

        // System.out.println(initDico(depeches, "SPORTS").get(450).getEntier());

        for (PaireChaineEntier unePaire:
                initDico(depeches, "SPORTS")) {
            System.out.println(unePaire.getEntier());
        }
    }


}


import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification {


    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        // { nomFichier non vide }
        // => { un tableau de dépêches contenant les dépêches du fichier nomFichier }

        long startTime = System.currentTimeMillis();
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
                while (scanner.hasNextLine() && !ligne.equalsIgnoreCase("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equalsIgnoreCase("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
            long longtime = System.currentTimeMillis();
            System.out.println("la lecture des dépêches a été réalisée en : " + (longtime-startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        // { depeches non vide, categories non vide, nomFichier non vide }
        // => { un fichier nomFichier est créé et contient le classement des depeches selon les categories,
        // et le pourcentage de depeches bien classées pour chaque catégorie avec une moyenne générale de précision du système }

        long startTime = System.currentTimeMillis();
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
                String catReelle = depeches.get(i).getCategorie();
                switch (catCourante) {
                    case "Environnement-Sciences":
                        if (catReelle.equalsIgnoreCase("Environnement-Sciences")) {
                            envs++;
                        }
                        break;
                    case "Culture":
                        if (catReelle.equalsIgnoreCase("Culture")) {
                            culture++;
                        }
                        break;
                    case "Economie":
                        if (catReelle.equalsIgnoreCase("Economie")) {
                            eco++;
                        }
                        break;
                    case "Politique":
                        if (catReelle.equalsIgnoreCase("Politique")) {
                            politique++;
                        }
                        break;
                    case "Sport":
                        if (catReelle.equalsIgnoreCase("Sports")) {
                            sport++;
                        }
                        break;
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
            long longtime = System.currentTimeMillis();
            System.out.println("le classement des dépêches a été réalisé en : " + (longtime-startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        // { depeches non vide, categorie non vide }
        // => { un tableau de PaireChaineEntier contenant les mots de la catégorie et leur fréquence d'apparition dans la catégorie }
        long startTime = System.currentTimeMillis();
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        int i = 0;
        while (!depeches.get(i).getCategorie().equalsIgnoreCase(categorie)) {
            i++;
        }
        while (i < depeches.size() && depeches.get(i).getCategorie().equalsIgnoreCase(categorie)) {
            ArrayList<String> mots = depeches.get(i).getMots();
            for (String mot: mots) {
                int indCour = UtilitairePaireChaineEntier.indicePourChaine(resultat, mot);
                if (indCour==-1) {
                    resultat.add(new PaireChaineEntier(mot, 0));
                } else {
                    resultat.get(indCour).setEntier(resultat.get(indCour).getEntier()+1);
                }
            }
            i++;
        }
        long longtime = System.currentTimeMillis();
        System.out.println("l'initialisation du dictionnaire pour la catégorie "+categorie+" a été réalisée en : " + (longtime-startTime) + "ms");
        return resultat;

    }



    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
        // { depeches non vide, categorie non vide, dictionnaire non vide }
        // => { le dictionnaire contient les mots de la catégorie et leur score }
        long startTime = System.currentTimeMillis();
        UtilitairePaireChaineEntier.tri_fusion(dictionnaire, 0, dictionnaire.size()-1);
        for (Depeche uneDep:
             depeches) {
            ArrayList<String> mots = uneDep.getMots();
            for (String mot:
                    mots) {
                int indCour = UtilitairePaireChaineEntier.indicePourChaine(dictionnaire, mot);
                if (indCour>-1) {
                    if (!uneDep.getCategorie().equalsIgnoreCase(categorie)) {
                        dictionnaire.get(indCour).setEntier(dictionnaire.get(indCour).getEntier()-1);
                    } else {
                        dictionnaire.get(indCour).setEntier(dictionnaire.get(indCour).getEntier()+1);
                    }
                }
            }
        }
        long longtime = System.currentTimeMillis();
        System.out.println("le calcul des scores pour la catégorie "+categorie+" a été réalisé en : " + (longtime-startTime) + "ms");
    }


    public static int poidsPourScore(int score) {
        // { score >= 0 }
        // => { le poids correspondant au score }
        if (score<=0) {
            return 0;
        } else if (score<4) {
            return 1;
        } else if (score<7) {
            return 2;
        } else {
            return 3;
        }
    }


    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        // { depeches non vide, categorie non vide, nomFichier non vide }
        // => { un fichier nomFichier est créé et contient le lexique de la catégorie trié par ordre croissant de chaines }
        long startTime = System.currentTimeMillis();
        ArrayList<PaireChaineEntier> dico = initDico(depeches, categorie);
        calculScores(depeches, categorie, dico);

        int i = 0;

        try {
            FileWriter file = new FileWriter(nomFichier);
            for (PaireChaineEntier unePaire : dico) {
                if (unePaire.getEntier() > 0) {
                    String chaine = unePaire.getChaine();

                    if (i < dico.size() - 1) {
                        file.write(chaine + ":" + poidsPourScore(unePaire.getEntier()) + "\n");
                    } else {
                        file.write(chaine + ":" + poidsPourScore(unePaire.getEntier()));
                    }

                }
                i++;
            }

            file.close();
            long longtime = System.currentTimeMillis();
            System.out.println("la génération du lexique pour la catégorie "+categorie+" a été réalisée en : " + (longtime-startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // programme principal
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // Début du chrono
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");
        // Lexique
        generationLexique(depeches, "ENVIRONNEMENT-SCIENCES", "./envsResult.txt");
        generationLexique(depeches, "CULTURE", "./cultureResult.txt");
        generationLexique(depeches, "ECONOMIE", "./ecoResult.txt");
        generationLexique(depeches, "POLITIQUE", "./politiqueResult.txt");
        generationLexique(depeches, "SPORTS", "./sportResult.txt");

        // Catégorie
        Categorie culture = new Categorie("Culture");
        culture.initLexique("./cultureResult.txt");
        Categorie economie = new Categorie("Economie");
        economie.initLexique("./ecoResult.txt");
        Categorie politique = new Categorie("Politique");
        politique.initLexique("./politiqueResult.txt");
        Categorie environnementScience = new Categorie("Environnement-Sciences");
        environnementScience.initLexique("./envsResult.txt");
        Categorie sport = new Categorie("Sport");
        sport.initLexique("./sportResult.txt");

        // Résultats
        ArrayList<Categorie> vCategorie = new ArrayList<>(Arrays.asList(culture, economie, politique, environnementScience, sport));
        Classification.classementDepeches(depeches, vCategorie, "./resultats.txt");


        long endTime = System.currentTimeMillis(); // Fin du chrono
        System.out.println("\n\nla classification automatique avec le tri fusion a été réalisée en : " + (endTime-startTime) + "ms");


    }


}


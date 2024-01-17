import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification {

    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                if (!ligne.startsWith(".N")) continue;
                String id = ligne.substring(3);

                ligne = scanner.nextLine();
                String date = ligne.substring(3);

                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);

                StringBuilder lignes = new StringBuilder();
                while (scanner.hasNextLine()) {
                    ligne = scanner.nextLine();
                    if (ligne.startsWith(".N")) {
                        scanner.nextLine();
                        break;
                    }
                    lignes.append(ligne).append('\n');
                }

                Depeche uneDepeche = new Depeche(id, date, categorie, lignes.toString());
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
        ArrayList<Integer> paireEntier = new ArrayList<>(Arrays.asList(0,0));
        try {
            FileWriter file = new FileWriter(nomFichier);

            for (int i = 0; i < depeches.size(); i++) {
                scoreParCat = new ArrayList<>();
                for (Categorie uneCategorie: categories) {
                    scoreParCat.add(new PaireChaineEntier(uneCategorie.getNom(), uneCategorie.score(depeches.get(i), paireEntier).get(0)));
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

            System.out.println("Number of score comparisons with merge sort : "+paireEntier.get(1));

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
        int i = 0;
        while (!depeches.get(i).getCategorie().equalsIgnoreCase(categorie)) {
            i++;
        }
        while (i < depeches.size() && depeches.get(i).getCategorie().equalsIgnoreCase(categorie)) {
            ArrayList<String> mots = depeches.get(i).getMots();
            for (String mot: mots) {
                int indCour = UtilitairePaireChaineEntier.indicePourChaine(resultat, mot, new ArrayList<>(Arrays.asList(0,0))).get(0);
                if (indCour==-1) {
                    resultat.add(new PaireChaineEntier(mot, 0));
                } else {
                    resultat.get(indCour).setEntier(resultat.get(indCour).getEntier()+1);
                }
            }
            i++;
        }
        return resultat;

    }


    public static int calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {

        int nbComparaisons = UtilitairePaireChaineEntier.tri_fusion(dictionnaire, new ArrayList<>(Arrays.asList(0,0))).get(0);

        for (Depeche uneDep:
             depeches) {
            ArrayList<String> mots = uneDep.getMots();
            for (String mot:
                    mots) {
                ArrayList<Integer> res = UtilitairePaireChaineEntier.indicePourChaine(dictionnaire, mot, new ArrayList<>(Arrays.asList(0,0)));
                nbComparaisons+=res.get(1);
                int indCour = res.get(0);
                if (indCour>-1) {
                    if (!uneDep.getCategorie().equalsIgnoreCase(categorie)) {
                        dictionnaire.get(indCour).setEntier(dictionnaire.get(indCour).getEntier()-1);
                    } else {
                        dictionnaire.get(indCour).setEntier(dictionnaire.get(indCour).getEntier()+1);
                    }
                }
                nbComparaisons++;
            }
        }
        return nbComparaisons;
    }

    public static int poidsPourScore(int score) {
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

    public static int generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        ArrayList<PaireChaineEntier> dico = initDico(depeches, categorie);
        int nbComparaisons = calculScores(depeches, categorie, dico);

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nbComparaisons;
    }

    public static void main(String[] args) {
        int totalComparaisons = 0;
        long startTime = System.currentTimeMillis(); // Début du chrono
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");
        // Lexique
        totalComparaisons+=generationLexique(depeches, "ENVIRONNEMENT-SCIENCES", "./envsResult.txt");
        totalComparaisons+=generationLexique(depeches, "CULTURE", "./cultureResult.txt");
        totalComparaisons+=generationLexique(depeches, "ECONOMIE", "./ecoResult.txt");
        totalComparaisons+=generationLexique(depeches, "POLITIQUE", "./politiqueResult.txt");
        totalComparaisons+=generationLexique(depeches, "SPORTS", "./sportResult.txt");


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

        System.out.println("Number of calculScore comparisons with merge sort : "+totalComparaisons);
        // Résultats
        ArrayList<Categorie> vCategorie = new ArrayList<>(Arrays.asList(culture, economie, politique, environnementScience, sport));
        Classification.classementDepeches(depeches, vCategorie, "./resultats.txt");

        long endTime = System.currentTimeMillis(); // Fin du chrono
        System.out.println("Near-linear automatic classification was carried out in : " + (endTime-startTime) + "ms");

    }


}


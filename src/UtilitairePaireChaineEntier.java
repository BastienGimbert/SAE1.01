import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;

        while (i < listePaires.size() && !listePaires.get(i).getChaine().equals(chaine)) {
            i++;
        }
        if (i == listePaires.size()) {
            return -1;
        } else {
            return i;
        }

    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        while (i < listePaires.size() && !listePaires.get(i).getChaine().equals(chaine)) {
            i++;
        }
        if (i < listePaires.size()) {
            return i;
        } else {
            return 0;
        }
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        int max = 0;

        for(int i=1; i<listePaires.size(); i++){
            if(listePaires.get(i).getEntier()>listePaires.get(max).getEntier()){
                max=i;
            }
        }
        return listePaires.get(max).getChaine();
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        float moyenne = 0.0f;
        int i = 0;

        while (i < listePaires.size()) {
            moyenne+=listePaires.get(i).getEntier();
            i++;
        }

        return moyenne / listePaires.size();
    }

    public static void tri_bulle_ameliore(ArrayList<PaireChaineEntier> listePaires) {
        int n = listePaires.size();
        boolean ech = true;
        while (ech) {
            ech = false;
            for (int i = 0; i < n - 1; i++) {
                if (listePaires.get(i).getEntier() < listePaires.get(i + 1).getEntier()) {
                    ech = true;
                    PaireChaineEntier tmp = listePaires.get(i);
                    listePaires.set(i, listePaires.get(i + 1));
                    listePaires.set(i + 1, tmp);
                }
            }
            n--;
        }
    }

}

import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


//    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
//        int i = 0;
//
//        while (i < listePaires.size() && !listePaires.get(i).getChaine().equalsIgnoreCase(chaine)) {
//            i++;
//        }
//        if (i == listePaires.size()) {
//            return -1;
//        } else {
//            return i;
//        }
//
//    }

    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {

        if (listePaires.isEmpty() || listePaires.get(listePaires.size()-1).getChaine().compareTo(chaine)<0) {
            return -1;
        } else {
            int inf = 0;
            int sup = listePaires.size()-1;
            int m;

            while (inf < sup) {
                m = (inf+sup) / 2;
                if (listePaires.get(m).getChaine().compareTo(chaine)>=0) {
                    sup = m;
                } else {
                    inf = m + 1;
                }
            }

            if (listePaires.get(inf).getChaine().equals(chaine)) {
                return inf;
            } else {
                return -1;
            }

        }
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        while (i < listePaires.size() && !listePaires.get(i).getChaine().equalsIgnoreCase(chaine)) {
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

    public static void tri_fusion(ArrayList<PaireChaineEntier> listePaires) {
        if (listePaires.size() > 1) {
            int m = listePaires.size() / 2;
            ArrayList<PaireChaineEntier> liste1 = new ArrayList<PaireChaineEntier>();
            ArrayList<PaireChaineEntier> liste2 = new ArrayList<PaireChaineEntier>();
            for (int i = 0; i < m; i++) {
                liste1.add(listePaires.get(i));
            }
            for (int i = m; i < listePaires.size(); i++) {
                liste2.add(listePaires.get(i));
            }
            tri_fusion(liste1);
            tri_fusion(liste2);
            fusion(liste1, liste2, listePaires);
        }
    }

    public static void fusion(ArrayList<PaireChaineEntier> liste1, ArrayList<PaireChaineEntier> liste2, ArrayList<PaireChaineEntier> listePaires) {
        int i1 = 0;
        int i2 = 0;
        int i = 0;
        while (i1 < liste1.size() && i2 < liste2.size()) {
            if (liste1.get(i1).getChaine().compareTo(liste2.get(i2).getChaine())<0) {
                listePaires.set(i, liste1.get(i1));
                i1++;
            } else {
                listePaires.set(i, liste2.get(i2));
                i2++;
            }
            i++;
        }
        while (i1 < liste1.size()) {
            listePaires.set(i, liste1.get(i1));
            i1++;
            i++;
        }
        while (i2 < liste2.size()) {
            listePaires.set(i, liste2.get(i2));
            i2++;
            i++;
        }
    }

}

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

    // Retourne l'indice de la chaine dans la liste via un algorithme de recherche dichotomique
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
    //  Assosie à chaque chaine de la liste le nombre d'occurences de cette chaine dans la liste
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
    // Retourne la chaine qui a le plus d'occurences dans la liste
    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        int max = 0;

        for(int i=1; i<listePaires.size(); i++){
            if(listePaires.get(i).getEntier()>listePaires.get(max).getEntier()){
                max=i;
            }
        }
        return listePaires.get(max).getChaine();
    }

    // Calcul la moyenne des entiers de la liste
    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        float moyenne = 0.0f;
        int i = 0;

        while (i < listePaires.size()) {
            moyenne+=listePaires.get(i).getEntier();
            i++;
        }

        return moyenne / listePaires.size();
    }

    public static void tri_fusion(ArrayList<PaireChaineEntier> listePaires, int inf, int sup) {
        if (inf < sup) {
            int m = (inf+sup)/2;
            tri_fusion(listePaires, inf, m);
            tri_fusion(listePaires, m+1 , sup);
            fusion(listePaires, inf, m, sup);
        }
    }

    public static void fusion(ArrayList<PaireChaineEntier> listePaires, int inf, int m, int sup) {
        // { inf <= sup, m = (inf+sup)/2, listePaires[inf..m] trié, listePaires[m+1..sup] trié }
        // => { listePaires[inf..sup] trié }
        ArrayList<String> vTemp = new ArrayList<>();
        int i1 = inf;
        int i2 = m+1;

        while (i1<=m && i2<=sup) {

            if (listePaires.get(i1).getChaine().compareTo(listePaires.get(i2).getChaine())<=0) {
                vTemp.add(listePaires.get(i1).getChaine());
                i1++;


            } else {
                vTemp.add(listePaires.get(i2).getChaine());
                i2++;
            }
        }

        while (i2<=sup) {
            vTemp.add(listePaires.get(i2).getChaine());
            i2++;
        }
        while (i1<=m) {
            vTemp.add(listePaires.get(i1).getChaine());
            i1++;
        }

        int i = 0;
        while (i < vTemp.size() && inf<=sup) {

            listePaires.get(inf).setChaine(vTemp.get(i));
            i++;
            inf++;
        }

    }

}

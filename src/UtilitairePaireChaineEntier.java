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

    public static ArrayList<Integer> indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine, ArrayList<Integer> paireEntier) {
        int nbComparaisons = 0;
        if (listePaires.isEmpty() || listePaires.get(listePaires.size()-1).getChaine().compareTo(chaine)<0) {
            nbComparaisons++;
            paireEntier.set(0, -1);
            paireEntier.set(1, nbComparaisons);
            return paireEntier;
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
                nbComparaisons++;
            }
            nbComparaisons++;
            paireEntier.set(1, nbComparaisons);
            if (listePaires.get(inf).getChaine().equals(chaine)) {
                paireEntier.set(0, inf);
                return paireEntier;
            } else {
                paireEntier.set(0, -1);
                return paireEntier;
            }

        }
    }

    public static ArrayList<Integer> entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine, ArrayList<Integer> paireEntier) {
        int i = 0;
        while (i < listePaires.size() && !listePaires.get(i).getChaine().equalsIgnoreCase(chaine)) {
            i++;
        }
        paireEntier.set(1, i);
        if (i < listePaires.size()) {
            paireEntier.set(0, i);
            return paireEntier;
        } else {
            paireEntier.set(0, -1);
            return paireEntier;
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

    public static int tri_bulle_ameliore(ArrayList<PaireChaineEntier> listePaires) {
        int nbComparaisons = 0;
        int n = listePaires.size();
        boolean ech = true;
        while (ech) {
            ech = false;
            for (int i = 0; i < n - 1; i++) {
                if (listePaires.get(i).getChaine().compareTo(listePaires.get(i + 1).getChaine())>0) {
                    ech = true;
                    PaireChaineEntier tmp = listePaires.get(i);
                    listePaires.set(i, listePaires.get(i + 1));
                    listePaires.set(i + 1, tmp);
                }
                nbComparaisons++;
            }
            n--;
        }
        return nbComparaisons;
    }

//    public static ArrayList<Integer> tri_fusion(ArrayList<PaireChaineEntier> listePaires, ArrayList<Integer> paireEntier) {
//        if (listePaires.size() > 1) {
//            int m = listePaires.size() / 2;
//            ArrayList<PaireChaineEntier> liste1 = new ArrayList<>();
//            ArrayList<PaireChaineEntier> liste2 = new ArrayList<>();
//
//            for (int i = 0; i < m; i++) {
//                liste1.add(listePaires.get(i));
//            }
//            for (int i = m; i < listePaires.size(); i++) {
//                liste2.add(listePaires.get(i));
//            }
//
//            tri_fusion(liste1, paireEntier);
//            tri_fusion(liste2, paireEntier);
//            paireEntier.set(0, paireEntier.get(0) + fusion(liste1, liste2, listePaires));
//        }
//
//        return paireEntier;
//    }
//
//    public static int fusion(ArrayList<PaireChaineEntier> liste1, ArrayList<PaireChaineEntier> liste2, ArrayList<PaireChaineEntier> listePaires) {
//        int i1 = 0, i2 = 0, i = 0, nbComparaisons = 0;
//
//        while (i1 < liste1.size() && i2 < liste2.size()) {
//            nbComparaisons++;
//            if (liste1.get(i1).getChaine().compareTo(liste2.get(i2).getChaine()) < 0) {
//                listePaires.set(i, liste1.get(i1++));
//            } else {
//                listePaires.set(i, liste2.get(i2++));
//            }
//            i++;
//        }
//
//        while (i1 < liste1.size()) {
//            listePaires.set(i++, liste1.get(i1++));
//        }
//        while (i2 < liste2.size()) {
//            listePaires.set(i++, liste2.get(i2++));
//        }
//
//        return nbComparaisons;
//
//    }

    public static int tri_fusion(ArrayList<PaireChaineEntier> listePaires, int inf, int sup) {
        int nbComparaisons = 0;
        if (inf < sup) {
            int m = (inf+sup)/2;
            tri_fusion(listePaires, inf, m);
            tri_fusion(listePaires, m+1 , sup);
            nbComparaisons += fusion(listePaires, inf, m, sup);
        }

        return nbComparaisons;
    }

    public static int fusion(ArrayList<PaireChaineEntier> listePaires, int inf, int m, int sup) {
        // { inf <= sup, m = (inf+sup)/2, listePaires[inf..m] trié, listePaires[m+1..sup] trié }
        // => { listePaires[inf..sup] trié }
        ArrayList<String> vTemp = new ArrayList<>();
        int i1 = inf;
        int i2 = m+1;
        int nbComparaisons = 0;

        while (i1<=m && i2<=sup) {

            if (listePaires.get(i1).getChaine().compareTo(listePaires.get(i2).getChaine())<=0) {
                vTemp.add(listePaires.get(i1).getChaine());
                i1++;


            } else {
                vTemp.add(listePaires.get(i2).getChaine());
                i2++;
            }
            nbComparaisons++;
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
        return nbComparaisons;
    }

}

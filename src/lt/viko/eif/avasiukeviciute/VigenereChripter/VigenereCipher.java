package lt.viko.eif.avasiukeviciute.VigenereChripter;

import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class VigenereCipher {
    private static Scanner in;
    private static String text;
    private static String mapkey;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nPasirinkite norima varianta\n1.Uzkriptuoti\n2.Atkriptuoti\n0.Iseiti\nPasirinkite(1,2): ");
            choice = in.nextInt();
            in.nextLine();
            if (choice == 1) {
                System.out.println("---Kriptavimas---");
                msgAndKey();
                cipherEncription(text, mapkey);

            } else if (choice == 2) {
                System.out.println("---Atkriptuoti teksta---");
                msgAndKey();
                cipherDecription(text, mapkey);

            } else {
                System.out.println("Neteisingas ivedimas iveskite 1 arba 2");
            }
        }while(choice!= 0);
    }

    private static void cipherDecription(String text, String mapkey) {
        int[][] table = createVigenereTable();
        String decryptedText = "";
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == (char)32 && mapkey.charAt(i) == (char)32){
                decryptedText += " ";
            } else{
                decryptedText += (char)(65 + itrCount((int)mapkey.charAt(i), (int)text.charAt(i)));
            }
        }
        System.out.println("Atkoduotas tekstas: " + decryptedText);
    }

    private static int itrCount(int key, int msg) {
    //funkcija kuri skaiciuoja kiek iteraciju pasiekti nuo rakto raides iki teksto raides
        int counter = 0;
        String result = "";
        for (int i = 0; i < 26; i++) {
            if(key+i > 90){
                // 90 yra ASCII of z
                result += (char) (key+(i-26));
            }else{
                result += (char)(key+i);
            }
        }
        // skaiciavimas nuo rakto raides iki teksto kriptavimo lenteleje
        for (int i = 0; i < result.length(); i++) {
            if(result.charAt(i) == msg){
                break;
            }else{
                counter++;
            }
        }
        return counter;
    }


    private static void cipherEncription(String text, String mapkey) {
        int[][] table = createVigenereTable();
        String encriptedText = " ";
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == (char) 32 && mapkey.charAt(i) == (char) 32){
                encriptedText += " ";
            }else {
                // surasti pozicijas radziu lenteleje
                encriptedText += (char)table[(int)text.charAt(i)-65][(int)mapkey.charAt(i)-65];
            }
        }
        System.out.println("Kriptuotas teksas: " + encriptedText);
    }


    private static int[][] createVigenereTable() {
       // sukuriama lentele 26x26 laikanti abeceles reiksmes
       int[][] tableArr = new int[26][26];
       for(int i = 0; i < 26; i++){
           for(int j = 0; j < 26; j++){
               int temp;
               if((i+65)+j > 90){
                   temp = ((i+65)+j) -26;
                   tableArr[i][j] = temp;
               }else {
                   temp = (i+65)+j;
                   tableArr[i][j] = temp;
               }
           }
       }
      /* for(int i = 0; i < 26; i++){
            for(int j = 0; j < 26; j++) {
                System.out.print((char) tableArr[i][j] + " ");
            }
            System.out.println();
        }*/
       return tableArr;
    }

    private static void msgAndKey() {
        System.out.println("--Zinute ir raktas turi buti tik raides--");
        // žinutės įvedimui
        System.out.print("Iveskite norima teksta: ");
        String msg = in.nextLine();
        msg = msg.toUpperCase();
        //Rakto įvedimui
        System.out.print("Iveskie rakta:");
        String key = in.nextLine();
        //in.nextLine();
        key = key.toUpperCase();
        // Raktui sulyginti su žinute
        String keyMap = "";
        for (int i = 0, j = 0; i < msg.length(); i++){
            if(msg.charAt(i)==(char) 32) {
                // tarpo tekste ignoravimui
                keyMap += (char)32;
            }else{
               //sulyginti teksto ir rakto ilgius
               if(j < key.length()){
                   keyMap += key.charAt(j);
                   j++;
               }else{
                   //kad raktas nebegalėtu  toliau kartotis kai tekstas pasibaiks
                   j = 0;
                   keyMap += key.charAt(j);
                   j++; // kad nepasikartotu rakto pirma raidė du kartus
               }
            }//if-else

        }//for
        text = msg;
        mapkey = keyMap;
 //       System.out.println("Tekstas: " + text);
 //     System.out.println("Raktas: " + mapkey);
    }
}

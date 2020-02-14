/*J'ai divisé les differents classs en differents packages et apres j'ai implementé un nouvelle classe
 * MultiGame pour jouer avec une autre personne: en particulier elle est une fonction temporisée aue permet de jouer
 * a deux joueurs sans aue un regarde l'ecran quand l'autre est en train de jouer
*/

package Menu;

import java.util.Scanner;
//On va creer une classe Bonus1 pour faire marcher le menu 

public class Bonus1 {
	private static int choice=0;
    public static int menu() {
        Scanner reader = new Scanner(System.in);
        while(choice!=1 && choice != 2 && choice != 3){
        System.out.println("Write 1 to play against the computer\n" );       
        System.out.println("Write 2 to play against your friend\n" );       
        System.out.println("Write 3 to play against the computer and load a previous game\n" );
        choice = reader.nextInt();
        if(choice != 1 && choice != 2 && choice != 3) System.out.println("Not correct input!\n");
       
        }
        return choice;
    }


public static void main(String args[]) {
    int choice;
    choice = menu();   

        if(choice == 1) new Game().init().run();		//on joue avec l'ordinateur
        if(choice == 2) new GameMulti().init().run();	//on joue avec un ami
        if(choice == 3) new Bonus2().init().run();	//on joue avec l'ordinateur avec le sauvegarde des choses
  
}
}

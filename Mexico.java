
//This program simulates an elimination dice game called Mexigo and we have two players, Giulia and David.

/*Name: Yuzhou Guo

 *StudentID: 260715042*/


//The method "Mexico" willbe called from the main method.
public class Mexico {

   public static void main (String[] args){
     
     //Have to change string from user to double.
     double buyIn = Double.parseDouble(args[0]);
     double bet = Double.parseDouble(args[1]);
     
     playMexico(buyIn, bet);
  
   }


   /*The method diceRoll simulates the situation of one dice. 
    *The Math.random provided in the computer allow us to get
     one random number from 0 to 1.
    *We set the minimum number as 1 and the maximum number as 6.*/  
   public static int diceRoll(){

     int minDice = 1;
     int maxDice = 6;
     int randomDice = (int)((double)((Math.random())*(maxDice) + minDice));
     return randomDice;

   }
   
   
   /*The method getScore simulates the two-digit number player gets in the game.
    *According to the rule, the larger number will be on the tens-column.*/
   public static int getScore(int d1, int d2){
   
     int score; 
       if (d1>=d2){
         score = (10*d1)+d2;
         return score;
     }else{
         score = (10*d2)+d1;
         return score;  
     }
     
   }
   
   
   /*The method playRound is kind of the conbination of the two previous
     methods we've written.
    *Also, this method prints player's name and the corresponding score, 
     which makes it a very important method in the last method "playMexico".
    *Since this method can only simulate one round, it should be called
     twice in "playMexico"*/
   public static int playOneRound (String name){
   
     int d1 = diceRoll(); 
     int d2 = diceRoll();  
     int score = getScore(d1,d2);
     System.out.print(name + " rolled:  " + d1 + " " + d2 + "\n");
     System.out.println(name +"'s score is:  " + score);
     
     //return the type of the method, should be integer here.
     return score;
    
   } 
   
   
   /*The method getWinner is basically a two-digit number priority system.
    *How we works here is to set the priority according to the rules
     in the game "Mexico", which is different than how we noramlly compare numbers.*/ 
   public static String getWinner (int s1, int s2){
   
     //Not a necessary step but makes coding neat.
     String G = "Giulia";
     String D = "David";
     
     //All boolean variabls here make the conditional statements very clear to read.
     boolean Gwins = (s1 == 21);
     boolean Dwins = (s2 == 21);
   
     boolean Gdoubles = ((s1 %11)==0);
     boolean GnotDouble = ((s1 %11)!=0);
     boolean Ddoubles = ((s2 %11)==0);
     boolean DnotDouble = ((s2 %11)!=0);
     
     boolean A = (Gdoubles && DnotDouble);
     boolean B = (Ddoubles && GnotDouble);
     
     //First we recognize the "tie" situation.
     //If two numbers are the same, automatically returns "tie".
     if (s1 == s2){
         return "tie";
         
     }else if (Gwins){
         return G;
     
     }else if (Dwins){
         return D;
         //Except for the "roll again" situation, whoever gets "21" immediately wins this round.
       
     }else if (A){
         return G;
       
     }else if (B){
         return D;
         //The priority keeps going, multiples of 11 is right after "21".
         /*If a number is a multiple of 11, then it beats anybody else 
           who doesn't have the same number on tens-column and one-column.
          *For instance, 44 beats 65.*/ 
         
     }else if (s1>s2){
          return G;
          
         }else if (s2>s1){
          return D;
            /*In other cases, 
              we can use the normal larger or smaller comparision.*/
         }
      return"";   
   }

   
   /*This method decides whether we are going to keep running the program or not.
    *The buy in amount has to be a positive integer of the bet amount.*/
   public static boolean canPlay (double buy, double bet) {
   
      /*This method ensures that the first number after decimal point is 0,
        which means it is an integer (in the integer form).*/
      int divisor = (int)((buy/bet)*10);
      boolean multiple = ((buy/bet)>0.0 && divisor%10 == 0);
      
      //Simple comparing to make sure buy in is larger than or equal to bet.
      boolean available = (buy>=bet);
      
      if (multiple && available){
        return true;
      }else{
        return false;
      }

   }

   //Finally, the most important method including everything!
   public static void playMexico(double buy, double bet){
     
     boolean play = canPlay(buy,bet);
     
     if(play == false){
           System.out.println("The fund is insufficient in order to play.");
           return;
           //First we test if this game can be played, if not then there's no need to continue.
     }
     
     double GiuliaBuyIn = buy;
     double DavidBuyIn = buy;
     /*Here we provide two purses for Giulia and David.
      *After each round, the money they bet before are going to be taken
       from the loser's purse.*/

     int x = 1;
     
     while(x>=1){
      
        boolean gContinue = canPlay(GiuliaBuyIn, bet);
        boolean dContinue = canPlay(DavidBuyIn, bet);
        //These two booleans have to be inside the loop so that we can check everytime.

        if(dContinue==false){
           System.out.print("Giulia won the game!");
           break;
        
        }else if(gContinue==false){
           System.out.print("David won the game!");
           break;
           /*Here we check is anybody's purse is already empty,
             If so, then we can claim the winner and end the loop.*/
        
        }else{
     
        System.out.println("Round " + x + "\n");
        //We need to count rounds, that's why we set x=1 at the beginning.
        
        int s1 = playOneRound("Giulia");
        int s2 = playOneRound("David");
        
        String winner = getWinner(s1, s2);
        
          if(winner == "tie"){
             System.out.println("It's a tie, roll again!\n");
             x++;
             continue;
             //No need to find the winner in a tie situation, we go back to the beginning of the loop.
             
          }else if (winner == "Giulia"){
             DavidBuyIn = DavidBuyIn - bet;
             System.out.println("Giulia wins this round.\n");
          }else{
             GiuliaBuyIn = GiuliaBuyIn - bet;
             System.out.println("David wins this round.\n");
          }
          x++;
        }
     }
   }
}
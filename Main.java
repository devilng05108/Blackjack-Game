import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        /*initially the player and dealer score is set to be 0 */
        int  playerScore=0;
        int  dealerScore=0;
        /* int round is just a variable that enables the game looping never stop until user decides to end the game by entering "end" as user input*/
        int round =1;
        /*String starter is user input for the user to decided start or end the game */
        String starter;
        /*difficultyGame allows player to choose the difficulty of blackJack */
        String difficultyGame;
        /*exit: is just a label for loop */
        exit:
        for(int k=1;round==1;k++){
            int dealerFirstHand=0;
            int dealerSecondHand=0;
            do{
                System.out.println("Enter \"start\" to start the round or  \"end \" to stop the game");
                Scanner start = new Scanner(System.in);
                starter = start.next();
            }while(!starter.equals("start")&&!starter.equals("end"));//this loop will only stop when user input is start or end
    
        if(starter.equals("start")){
            do{
            System.out.println("choose the difficulty :easy normal hard");
            Scanner difficulty = new Scanner(System.in);
            difficultyGame=difficulty.next();
            }while(!difficultyGame.equals("easy")&&!difficultyGame.equals("normal")&&!difficultyGame.equals("hard"));//this loop will only stop when user input is easy , normal or hard
            
        System.out.println("Round "+ k + " of BlackJack starts");
        int [] cards = new int[104];//this array keeps 104 cards consisting of 2 decks cards
        char [] suits= {'\u2660','\u2665','\u2663','\u2666','\u2660','\u2665','\u2663','\u2666'};//this array keeps the suits of the cards
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King"};//this array keeps the ranks of the cards
        
        //initializing 104 cards and showing all the cards with suits and ranks to make sure there are 2 decks of cards
        //this loop can be deleted or commented when the program is embedded into actual code.
        //this is currenly enabled to allow program debugging and lecturers determining the completeness of the code
        for(int i=0;i<104;i++){
            cards[i]=i;
            char suit = suits[i/13];
            String rank =ranks[i%13];
            System.out.print(suit + rank +" ");
            if(i!=0&&i%10==0){
                System.out.println("");
            }
        }
        
        System.out.println("");
        System.out.println("Player  and dealer have entered the game");
        System.out.println("Shuffling the cards");
        Random shuffle = new Random();
        //by this loop we shuffle all the cards randomly
        for(int i=0;i<104;i++){
            int index = shuffle.nextInt(104);
            int temp = cards[i];
            cards[i] = index;
            cards[index] =temp;
        }
        //random 2 cards for dealer and player
        System.out.print("Cards of the dealer are");
        int[]hands1Dealer= new int[15]; //we given a maximum quota of 15 cards into each hand of dealer since the minimum possible value of having 15 cards is 22 which has exceeded 21
        int[]hands2Dealer = new int [15];
        int blackJackTotal=0;  //this variable is to determine whether blackjack is obtained by player or dealer
        for(int i=0;i<2;i++){
            int twoCards = shuffle.nextInt(104);
            while(cards[twoCards]==500){ //cards with value of 500(taken) will be ignored and get a new random cards again
                twoCards = shuffle.nextInt(104);
            }
            hands1Dealer[i]=cards[twoCards];
            cards[twoCards]=500;//cards that are taken will be given a value of 500 so that we don't take the same card anymore
            char suit = suits[hands1Dealer[i]/13];//we have 13 cards of the same suit in each deck
            String rank =ranks[hands1Dealer[i]%13];//we have 4 cards of the same rank in each deck
            System.out.print(suit + rank +" ");
            //check if blackJack is obtained by dealer
            blackJackTotal+=blackJack(hands1Dealer[i]);
        }
        System.out.println("");
        //check if blackJack is obtained by dealer
        int dealerMightWin;
        if(blackJackTotal==21){
            dealerMightWin=1;
        }else{
            dealerMightWin=0;
        }
        
        System.out.print("Cards of the player are");
        int[]hands1Player = new int[15];
        int[]hands2Player = new int[15];
        blackJackTotal=0;
        for(int i=0;i<2;i++){
            int twoCards = shuffle.nextInt(104);
            while(cards[twoCards]==500){
                twoCards = shuffle.nextInt(104);
            }
            hands1Player[i]=cards[twoCards];
            //took cards recorded
            cards[twoCards]=500;
            char suit = suits[hands1Player[i]/13];
            String rank =ranks[hands1Player[i]%13];
            System.out.print(suit + rank +" ");
            //check if bllackjack
            blackJackTotal+=blackJack(hands1Player[i]);
        }
        System.out.println("");
        //check if blackJack is obtained by player
        int playerMightWin;
         if(blackJackTotal==21){
            playerMightWin=1;
        }else{
            playerMightWin=0;
        }
         
        if(dealerMightWin>playerMightWin){
            System.out.println("RESULT:Dealer got a blackJack and dealer wins");
            dealerScore++;
            continue exit;//we will exit the loop by continue keyword if blackjack obtained by dealer by not player
        }
        else if(playerMightWin>dealerMightWin){
            System.out.println("RESULT:Player got a blackJack and player wins");
            playerScore++;
            continue exit;
        }
        else if(playerMightWin==dealerMightWin&&playerMightWin==0){
            System.out.println("No one got a black jack,the game continues");
        }
        else{
            System.out.println("both dealer and player got a blackjack,no one wins this round");
            continue exit;
        }
        // player's decision to either hit or stand or split
        System.out.println("Player's turn to decide: Press 1 for hit,2 for stand,3 for split");
        Scanner decision = new Scanner(System.in);
        
        int hitCountPlayer =0;//this variable is to store the number of new cards taken
        int playerDecision=0;//this variable is to determine the decision of player
        
        if(hands1Player[0]%13!=hands1Player[1]%13){ // this determines that whether the player has the first two cards of same value to allow his to split his cards
            do{
                playerDecision = decision.nextInt();
                switch(playerDecision){
                    case 1:
                        for(int i=0;i<104;i++){
                            if(cards[i]!=500){
                                hands1Player[(2+hitCountPlayer)]=cards[i];
                                cards[i]=500;
                                System.out.println("The new card is "+suits[hands1Player[(2+hitCountPlayer)]/13] +ranks[hands1Player[(2+hitCountPlayer)]%13]);
                                hitCountPlayer++;
                                break;
                                }
                        }
                        break;
                    case 2:
                        System.out.println("You decided to stand");
                        break;
                    case 3:
                        System.out.println("Your cards have different values, you cannot split the cards,please decide again");
                        break;
                    default:
                        System.out.println("Enter 1 or 2 or 3");
                }
            }while( playerDecision!=2 );//once the player has decided to stand, his turn ends
            
            System.out.print("After round 1 the cards of player are");
            for(int i=0;i<2+hitCountPlayer;i++){
            System.out.print(suits[hands1Player[i]/13] + ranks[hands1Player[i]%13] + " ");
        }
            System.out.println("");
        }
        boolean twoHands =false;//this variable records whether the player splitted his cards or not
//        int hitCountSame=0; // this variable records the number of cards hit 
        boolean newCardTaken=false;//this variable determines whether a new card has been taken
        if(hands1Player[0]%13==hands1Player[1]%13){
            two://two is a label for do while loop
            do{
                playerDecision = decision.nextInt();
                switch(playerDecision){
                    case 1:
                        
                        for(int i=0;i<104;i++){
                            if(cards[i]!=500){
                                hands1Player[(2+hitCountPlayer)]=cards[i];
                                cards[i]=500;
                                System.out.println("The new card is "+suits[hands1Player[(2+hitCountPlayer)]/13] +ranks[hands1Player[(2+hitCountPlayer)]%13]);
                                hitCountPlayer++;
                                break;
                                }
                        }
                        newCardTaken=true;
                        
                        break;
                    case 2:
                        System.out.println("You decided to stand");
                        break two;
                    case 3:
                        if(newCardTaken==false){ //check whether a new card has been taken or not
                                System.out.println("You have decided to split your cards into two hands");
                                hands2Player[0]=hands1Player[1];
                                hands1Player[1]=500;
                                twoHands =true;
                                break two;//once the player split, we exit loop with label 'two' 
                        }
                        else{
                                System.out.println("You have more than 2 cards,you cannot split,decide again");
                        }
                        break;
                    default:
                        System.out.println("Enter 1 or 2 or 3");

                }
            }while( playerDecision!=2 && twoHands!=true);
            
            if(twoHands==false){ 
            System.out.print("After round 1 the cards of player are");
            for(int i=0;i<2+hitCountPlayer;i++){
                System.out.print(suits[hands1Player[i]/13] + ranks[hands1Player[i]%13] + " ");
             }
             System.out.println("");
            }
        }
        
        int hitCountSplitFirst =0; //records number of new cards taken in first hand of splitted hands
        int hitCountSplitSecond =0;//records number of new cards taken in second hand of splitted hands
        if(twoHands==true){
            System.out.println("Decide for your first hand,Press 1 for hit,2 for stand");
             do{
                playerDecision = decision.nextInt();
                switch(playerDecision){
                    case 1:
                        for(int i=0;i<104;i++){
                            if(cards[i]!=500){
                                hands1Player[(1+hitCountSplitFirst)]=cards[i];
                                cards[i]=500;
                                System.out.println("The new card is "+suits[hands1Player[(1+hitCountSplitFirst)]/13] +ranks[hands1Player[(1+hitCountSplitFirst)]%13]);
                                hitCountSplitFirst++;
                                break;
                                }
                        }
                        break;
                    case 2:
                        System.out.println("You decided to stand");
                        break;

                    default:
                        System.out.println("Enter 1 or 2 ");

                }
            }while( playerDecision!=2 );//we will exit the loop when player decides to stand
             
             
             System.out.println("Decide for your second hand,Press 1 for hit,2 for stand");
             do{
                playerDecision = decision.nextInt();
                switch(playerDecision){
                    case 1:
                        for(int i=0;i<104;i++){
                            if(cards[i]!=500){
                                hands2Player[(1+hitCountSplitSecond)]=cards[i];
                                cards[i]=500;
                                System.out.println("The new card is "+suits[hands2Player[(1+hitCountSplitSecond)]/13] +ranks[hands2Player[(1+hitCountSplitSecond)]%13]);
                                hitCountSplitSecond++;
                                break;
                                }
                        }
                        break;
                    case 2:
                        System.out.println("You decided to stand");
                        break;

                    default:
                        System.out.println("Enter 1 or 2 ");

                }
            }while( playerDecision!=2 );
             
             System.out.print("The first hand of player are ");
             for(int i=0;i<1+hitCountSplitFirst;i++){
            System.out.print(suits[hands1Player[i]/13] + ranks[hands1Player[i]%13] + " ");
                }
             System.out.println("");
             System.out.print("The secondhand of player are ");
             for(int i=0;i<1+hitCountSplitSecond;i++){
            System.out.print(suits[hands2Player[i]/13] + ranks[hands2Player[i]%13] + " ");
                }
             System.out.println("");
             
        }
            //easy game mode 
            int easyDealerFirstTwoCards =easyMode(hands1Dealer[0],hands1Dealer[1]);
            System.out.println("Dealer's turn");
            if(difficultyGame.equals("easy")){
                int easyDealerDecision=0;
                
                System.out.println("Easy game mode of blackJack");
                System.out.println("value of dealer is "+easyMode(hands1Dealer[0],hands1Dealer[1]));//we display the dealer value to proof that our code is working fine
                //dealer card value should be commented when this code is embedded into an actual game
                //since we are in demo currently,we will leave it here
                int easyHitCount=0;//records the number of new cards taken in easy mode
                easyMode:
                do{    
                    if(easyDealerFirstTwoCards<=18){ //in easy mode,dealer will take a new card when the card value is <=18
                        easyDealerDecision=1;
                    }
                    else{
                        easyDealerDecision=2;
                    }
                           switch(easyDealerDecision){
                               case 1:
                                   for(int i=0;i<104;i++){
                                       if(cards[i]!=500){
                                           hands1Dealer[(2+ easyHitCount)]=cards[i];
                                           cards[i]=500;
                                           System.out.println("The new card is "+suits[ hands1Dealer[(2+ easyHitCount)]/13] +ranks[ hands1Dealer[(2+ easyHitCount)]%13]);//we display the dealer value to proof that our code is working fine
                                           easyHitCount++;
                                           break;
                                           }
                                   }
                                   break;
                               case 2:
                                   System.out.println("Dealer decided to stand");
                                   break easyMode;

                               default:
                                   System.out.println("Error on easy mode ");

                            }//closed switch statement
                        int easyHitValue =hands1Dealer[(2+ easyHitCount-1)]%13;//calculate the value of newly taken cards
                        switch(easyHitValue){
                            case 0:
                                    if(easyDealerFirstTwoCards+11>21){ // if the total card value exceeds 21 if Ace is considered 11
                                        easyDealerFirstTwoCards+=1;
                                    }
                                    else{
                                        easyDealerFirstTwoCards+=11; // if the total card value dont excced 21 if Ace is considered 11
                                    }
                                    break;
                            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                    easyDealerFirstTwoCards+=easyHitValue+1;
                                    break;
                            case 9:case 10:case 11: case 12:
                                    easyDealerFirstTwoCards+=10;
                                    break;
                            default:
                                    System.out.println("error");
                                    break; 
                            
                        }
                        System.out.println("The value of dealer cards are " + easyDealerFirstTwoCards);
                }while( easyDealerDecision!=2 );
                
        }//closed if game mode = easy
            
            //normal game mode
            boolean dealerTwoHands =false;
            int dealerFirstHitCount=0;
            int dealerSecondHitCount=0;
            int normalDealerFirstTwoCards =normalMode(hands1Dealer[0],hands1Dealer[1]);
             if(difficultyGame.equals("normal")){
                
                int normalDealerDecision=0;
               
                System.out.println("normal game mode of blackJack");
                System.out.println("value of dealer is "+normalMode(hands1Dealer[0],hands1Dealer[1]));
                int normalHitCount=0;
                int split=0;
                
                normalMode:
                do{    
                    if(normalDealerFirstTwoCards<=17){ //in normal game mode,dealer will take a new card when card value is <=17
                        normalDealerDecision=1;
                    }
                    else if(normalDealerFirstTwoCards>17){
                        normalDealerDecision=2;
                    }
                    if(hands1Dealer[0]%13==hands1Dealer[1]%13&&split==0){
                        normalDealerDecision=3;
                        split=1;
                    }
                    
                           switch(normalDealerDecision){
                               case 1:
                                   for(int i=0;i<104;i++){
                                       if(cards[i]!=500){
                                           hands1Dealer[(2+ normalHitCount)]=cards[i];
                                           cards[i]=500;
                                           System.out.println("The new card is "+suits[ hands1Dealer[(2+ normalHitCount)]/13] +ranks[ hands1Dealer[(2+ normalHitCount)]%13]);
                                           normalHitCount++;
                                           break;
                                           }
                                   }
                                   break;
                               case 2:
                                   System.out.println("Dealer decided to stand");
                                   break normalMode;

                               case 3:                              
                                    System.out.println("Dealer have decided to split his cards into two hands");
                                    hands2Dealer[0]=hands1Dealer[1];
                                    hands1Dealer[1]=500;
                                    dealerTwoHands =true;
                                    break ;
                                default:
                                    System.out.println("Error");
                                    break;
                            }//closed switch statement
                           
                            if(dealerTwoHands==false){
                                     int normalHitValue =hands1Dealer[(2+ normalHitCount-1)]%13;
                        //                        System.out.println("easy hit value is  " + hands1Dealer[(2+ easyHitCount)]);
                                                switch(normalHitValue){
                                                    case 0:
                                                            if(normalDealerFirstTwoCards+11>21){
                                                                normalDealerFirstTwoCards+=1;
                                                            }
                                                            else{
                                                               normalDealerFirstTwoCards+=11;
                                                            }
                                                            break;
                                                    case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                                            normalDealerFirstTwoCards+=normalHitValue+1;
                                                            break;
                                                    case 9:case 10:case 11: case 12:
                                                            normalDealerFirstTwoCards+=10;
                                                            break;
                                                    default:
                                                            System.out.println("error");
                                                            break; 

                                                }
                                                System.out.println("The value of dealer cards are " + normalDealerFirstTwoCards);
                            }//closed daelerTwohands==false
                            
                            else{//dealer two hands == true
                                    
                                    int dealerDecision=0;
                                    
                                    System.out.println("First hand of dealer");
                                    normalFirstHands:
                                    for(int j=0;j>-1;j++){
                                        
                                        if(dealerFirstHand<=17){
                                            dealerDecision=1;
                                        }
                                        else {
                                            dealerDecision=2;
                                        }
                                    switch(dealerDecision){
                                            case 1:
                                                for(int i=0;i<104;i++){
                                                    if(cards[i]!=500){
                                                        
                                                        hands1Dealer[(1+dealerFirstHitCount)]=cards[i];
                                                        cards[i]=500;
                                                        System.out.println("Dealer has decided to hit,taking a new card");
                                                        System.out.println("The new card is "+suits[hands1Dealer[(1+dealerFirstHitCount)]/13] +ranks[hands1Dealer[(1+dealerFirstHitCount)]%13]);
                                                        dealerFirstHitCount++;
                                                        break;
                                                        }
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Dealer decided to stand");
                                                break normalFirstHands;

                                            default:
                                                System.out.println("Normal dealer split mode error");
                                     }
                                    if(j==0){
                                        dealerFirstHand=normalMode(hands1Dealer[0],hands1Dealer[1]);
                                        System.out.println("Card value of dealer first hand are " +dealerFirstHand);
                                    }//calculate the value of first 2 cards
                                    if(j!=0){ //exclude the calculation of second card
                                    int normalHitValue =hands1Dealer[(dealerFirstHitCount)]%13; //calculate value of new cards taken
                                                switch(normalHitValue){
                                                    case 0:
                                                            if(dealerFirstHand+11>21){
                                                                dealerFirstHand+=1;
                                                            }
                                                            else{
                                                               dealerFirstHand+=11;
                                                            }
                                                            break;
                                                    case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                                            dealerFirstHand+=normalHitValue+1;
                                                            break;
                                                    case 9:case 10:case 11: case 12:
                                                            dealerFirstHand+=10;
                                                            break;
                                                    default:
                                                            System.out.println("error");
                                                            break; 

                                                }
                                                System.out.println("The card value of dealer first hand are " + dealerFirstHand);
                                            }//closed if j!=0
                                    }//closed for loop dealerSplit1
                                    
                                    dealerDecision=0;
                                    System.out.println("Second hand of dealer");
                                    normalSecondHands:
                                    for(int j=0;j>-1;j++){
                                        if(dealerSecondHand<=17){
                                            dealerDecision=1;
                                        }
                                        else {
                                            dealerDecision=2;
                                            normalDealerDecision=2;
                                        }
                                    switch(dealerDecision){
                                            case 1:
                                                for(int i=0;i<104;i++){
                                                    if(cards[i]!=500){
                                                        
                                                        hands2Dealer[(1+dealerSecondHitCount)]=cards[i];
                                                        cards[i]=500;
                                                        System.out.println("Dealer has decided to hit,taking a new card");
                                                        System.out.println("The new card is "+suits[hands2Dealer[(1+dealerSecondHitCount)]/13] +ranks[hands2Dealer[(1+dealerSecondHitCount)]%13]);
                                                        dealerSecondHitCount++;
                                                        break;
                                                        }
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Dealer decided to stand");
                                                break normalSecondHands;

                                            default:
                                                System.out.println("Normal dealer split mode error");
                                     }
                                    if(j==0){
                                        dealerSecondHand=normalMode(hands2Dealer[0],hands2Dealer[1]);
                                        System.out.println("Card value of dealer second hand are " +dealerSecondHand);
                                    }
                                    if(j!=0){
                                    int normalHitValue =hands2Dealer[(dealerSecondHitCount)]%13;
                                                switch(normalHitValue){
                                                    case 0:
                                                            if(dealerSecondHand+11>21){
                                                               dealerSecondHand+=1;
                                                            }
                                                            else{
                                                               dealerSecondHand+=11;
                                                            }
                                                            break;
                                                    case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                                            dealerSecondHand+=normalHitValue+1;
                                                            break;
                                                    case 9:case 10:case 11: case 12:
                                                            dealerSecondHand+=10;
                                                            break;
                                                    default:
                                                            System.out.println("error");
                                                            break; 

                                                }
                                                System.out.println("The card value of dealer second hand are " +dealerSecondHand);
                                            }//closed if j!=0
                                    }//closed for loop dealerSplit2
                            }        
                }while( normalDealerDecision!=2 );
         
        }//closed if game mode = normal
         
          //hard game mode
            int hardDealerFirstTwoCards =hardMode(hands1Dealer[0],hands1Dealer[1]);
             if(difficultyGame.equals("hard")){
                
                int hardDealerDecision=0;
               
                System.out.println("hard mode");
                System.out.println("value of dealer is "+hardDealerFirstTwoCards);
                int hardHitCount=0;
                int split=0;
                
                hardMode:
                do{    
                    if(hardDealerFirstTwoCards<=16){ //in hard game mode,dealer will take a new card when card value is <=16
                        hardDealerDecision=1;
                    }
                    else if(hardDealerFirstTwoCards>16){
                        hardDealerDecision=2;
                    }
                    if(((hands1Dealer[0]%13==0&&hands1Dealer[1]%13==0)&&split==0)||((hands1Dealer[0]%13==7&&hands1Dealer[1]%13==7)&&split==0)){
                        hardDealerDecision=3;
                        split=1;
                    }
                    
                           switch(hardDealerDecision){
                               case 1:
                                   for(int i=0;i<104;i++){
                                       if(cards[i]!=500){
                                           hands1Dealer[(2+ hardHitCount)]=cards[i];
                                           cards[i]=500;
                                           System.out.println("The new card is "+suits[ hands1Dealer[(2+ hardHitCount)]/13] +ranks[ hands1Dealer[(2+ hardHitCount)]%13]);
                                           hardHitCount++;
                                           break;
                                           }
                                   }
                                   break;
                               case 2:
                                   System.out.println("Dealer decided to stand");
                                   break hardMode;

                               case 3:                              
                                    System.out.println("Dealer have decided to split his cards into two hands");
                                    hands2Dealer[0]=hands1Dealer[1];
                                    hands1Dealer[1]=500;
                                    dealerTwoHands =true;
                                    break ;
                                default:
                                    System.out.println("Error");
                                    break;
                            }//closed switch statement
                           
                            if(dealerTwoHands==false){
                                     int hardHitValue =hands1Dealer[(2+ hardHitCount-1)]%13;
                                                switch(hardHitValue){
                                                    case 0:
                                                            if(hardDealerFirstTwoCards+11>21){
                                                                hardDealerFirstTwoCards+=1;
                                                            }
                                                            else{
                                                               hardDealerFirstTwoCards+=11;
                                                            }
                                                            break;
                                                    case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                                            hardDealerFirstTwoCards+=hardHitValue+1;
                                                            break;
                                                    case 9:case 10:case 11: case 12:
                                                            hardDealerFirstTwoCards+=10;
                                                            break;
                                                    default:
                                                            System.out.println("error");
                                                            break; 

                                                }
                                                System.out.println("The value of dealer cards are " + hardDealerFirstTwoCards);
                            }//closed hard daelerTwohands==false
                            
                            else{//hard mode dealer two hands == true
                                    
                                    int dealerDecision=0;
                                    
                                    System.out.println("First hand of dealer");
                                    hardFirstHands:
                                    for(int j=0;j>-1;j++){
                                        
                                        if(dealerFirstHand<=16){
                                            dealerDecision=1;
                                        }
                                        else {
                                            dealerDecision=2;
                                        }
                                    switch(dealerDecision){
                                            case 1:
                                                for(int i=0;i<104;i++){
                                                    if(cards[i]!=500){
                                                        
                                                        hands1Dealer[(1+dealerFirstHitCount)]=cards[i];
                                                        cards[i]=500;
                                                        System.out.println("The new card is "+suits[hands1Dealer[(1+dealerFirstHitCount)]/13] +ranks[hands1Dealer[(1+dealerFirstHitCount)]%13]);
                                                        dealerFirstHitCount++;
                                                        break;
                                                        }
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Dealer decided to stand");
                                                break hardFirstHands;

                                            default:
                                                System.out.println("Hard dealer split mode error");
                                     }
                                    if(j==0){
                                        dealerFirstHand=hardMode(hands1Dealer[0],hands1Dealer[1]);
                                        System.out.println("The card value of dealer first hand is " + dealerFirstHand);
                                    }
                                    if(j!=0){
                                    int hardHitValue =hands1Dealer[(dealerFirstHitCount)]%13;
                        //                        System.out.println("easy hit value is  " + hands1Dealer[(2+ easyHitCount)]);
                                                switch(hardHitValue){
                                                    case 0:
                                                            if(dealerFirstHand+11>21){
                                                                dealerFirstHand+=1;
                                                            }
                                                            else{
                                                               dealerFirstHand+=11;
                                                            }
                                                            break;
                                                    case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                                            dealerFirstHand+=hardHitValue+1;
                                                            break;
                                                    case 9:case 10:case 11: case 12:
                                                            dealerFirstHand+=10;
                                                            break;
                                                    default:
                                                            System.out.println("error");
                                                            break; 

                                                }
                                                System.out.println("The card value of dealer first hand are " + dealerFirstHand);
                                            }//closed if j!=0
                                    }//closed for loop dealerSplit1
                                    
                                    dealerDecision=0;
                                    System.out.println("Second hand of dealer");
                                    hardSecondHands:
                                    for(int j=0;j>-1;j++){
                                        if(dealerSecondHand<=17){
                                            dealerDecision=1;
                                        }
                                        else {
                                            dealerDecision=2;
                                            hardDealerDecision=2;
                                        }
                                    switch(dealerDecision){
                                            case 1:
                                                for(int i=0;i<104;i++){
                                                    if(cards[i]!=500){
                                                        
                                                        hands2Dealer[(1+dealerSecondHitCount)]=cards[i];
                                                        cards[i]=500;
                                                        System.out.println("The new card is "+suits[hands2Dealer[(1+dealerSecondHitCount)]/13] +ranks[hands2Dealer[(1+dealerSecondHitCount)]%13]);
                                                        dealerSecondHitCount++;
                                                        break;
                                                        }
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Dealer decided to stand");
                                                break hardSecondHands;

                                            default:
                                                System.out.println("Hard dealer split mode error");
                                     }
                                    if(j==0){
                                        dealerSecondHand=hardMode(hands2Dealer[0],hands2Dealer[1]);
                                        System.out.println("The card value of dealer second hand is " +dealerSecondHand);
                                    }
                                    if(j!=0){
                                    int hardHitValue =hands2Dealer[(dealerSecondHitCount)]%13;
                                                switch(hardHitValue){
                                                    case 0:
                                                            if(dealerSecondHand+11>21){  // value of ace is 1 if card value +11 >21
                                                               dealerSecondHand+=1;
                                                            }
                                                            else{
                                                               dealerSecondHand+=11; //value of ace is 11 if card value +11 <=21
                                                            }
                                                            break;
                                                    case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                                                            dealerSecondHand+=hardHitValue+1;
                                                            break;
                                                    case 9:case 10:case 11: case 12:
                                                            dealerSecondHand+=10;
                                                            break;
                                                    default:
                                                            System.out.println("error");
                                                            break; 

                                                }
                                                System.out.println("The card value of dealer second hand is " +dealerSecondHand);
                                            }//closed if j!=0
                                    }//closed for loop dealerSplit2
                            }        
                }while( hardDealerDecision!=2 );
         
        }//closed if game mode = hard

                //calculate final card values for easy game mode for hands1Player with no split
                
                if(twoHands==false&&difficultyGame.equals("easy")&&dealerTwoHands==false){
                    int noSplitSum=0;// calculates the summation of card value
                    for(int i=0; i<2+hitCountPlayer;i++){
                        noSplitSum+=valueCards(hands1Player[i]%13);
                    }
                    System.out.println("Player 1 has cards of value " +noSplitSum);
                    if(easyDealerFirstTwoCards<=21&&noSplitSum<=21){// comparison of dealer and player card value
                                if(easyDealerFirstTwoCards>noSplitSum){
                                    System.out.println("RESULT:Dealer wins this round");
                                    dealerScore++;
                                }
                                else if(noSplitSum>easyDealerFirstTwoCards){
                                    System.out.println("RESULT:Player wins this round");
                                    playerScore++;
                                }
                                else{
                                    System.out.println("RESULT:no one wins");
                                }
                    }
                    else if(easyDealerFirstTwoCards>21&&noSplitSum<=21){
                                System.out.println("RESULT:Player wins this round since dealer bursts with value more than 21");
                                playerScore++;
                    }
                    else if(noSplitSum>21&&easyDealerFirstTwoCards<=21){
                                 System.out.println("RESULT:Dealer wins this round since player bursts with value more than 21");
                                 dealerScore++;
                    }
                    else{
                                 System.out.println("RESULT:both player and dealer burst,no one wins");
                    }
                }//closed easy mode final card values for hands1Player with no split
                
                //calculate easy mode and normal mode and hard mode with player split but dealer no split
                if(twoHands==true&&dealerTwoHands==false){
                    int splitSum1=0;
                    int dealerValue=0;
                    for(int i=0;i<1+hitCountSplitFirst;i++){
                        splitSum1+=valueCards(hands1Player[i]%13);
                    }
                    System.out.println("Hands 1 of player has cards of value " + splitSum1);
                    int splitSum2=0;
                    for(int i=0;i<1+hitCountSplitSecond;i++){
                        splitSum2+=valueCards(hands2Player[i]%13);
                    }
                    System.out.println("Hands 2 of player has cards of value " + splitSum2);
                    if(difficultyGame.equals("easy")){
                        dealerValue=easyDealerFirstTwoCards;
                    }
                    else if(difficultyGame.equals("normal")){
                        dealerValue=normalDealerFirstTwoCards;
                    }
                    else if(difficultyGame.equals("hard")){
                        dealerValue=hardDealerFirstTwoCards;
                    }
                    if(dealerValue<=21&&splitSum1<=21&&splitSum2<=21){
                            if(dealerValue>splitSum1&&dealerValue>splitSum2){
                                System.out.println("Dealer wins");
                                dealerScore++;
                            }
                            else if(dealerValue==splitSum1&&dealerValue>splitSum2){
                                System.out.println("Dealer wins");
                                dealerScore++;
                            }
                            else if(dealerValue==splitSum2&&dealerValue>splitSum1){
                                System.out.println("Dealer wins");
                                dealerScore++;
                            }
                            else if(dealerValue==splitSum1&&dealerValue==splitSum2){
                                System.out.println("No one wins");
                            }
                            else if(splitSum1>dealerValue||splitSum2>dealerValue){
                                System.out.println("Player wins");
                                playerScore++;
                            }
                    }
                    else if(dealerValue>21&&(splitSum1<=21||splitSum2<=21)){
                        System.out.println("Player wins");
                        playerScore++;
                    }
                    else if(dealerValue>21&&splitSum1>21&&splitSum2>21){
                        System.out.println("No one wins");
                    }
                    else if(dealerValue<=21&&splitSum1>21&&splitSum2<=21){
                        if(dealerValue>splitSum2){
                            System.out.println("Dealer wins");
                            dealerScore++;
                        }
                        else if(dealerValue==splitSum2){
                            System.out.println("No one wins");
                        }
                        else if(splitSum2>dealerValue){
                            System.out.println("Player wins");
                            playerScore++;
                        }
                    }
                    else if(dealerValue<=21&&splitSum2>21&&splitSum1<=21){
                        if(dealerValue>splitSum1){
                            System.out.println("Dealer wins");
                            dealerScore++;
                        }
                        else if(dealerValue==splitSum1){
                            System.out.println("No one wins");
                        }
                        else if(splitSum1>dealerValue){
                            System.out.println("Player wins");
                            playerScore++;
                        }
                    }
                }//closed easy mode and normal mode and hard mode with player split
                
                //calculate normal game mode for hands1Player with no split
                if(twoHands==false&&difficultyGame.equals("normal")&&dealerTwoHands==false){
                    int noSplitSum=0;
                    for(int i=0; i<2+hitCountPlayer;i++){
                        noSplitSum+=valueCards(hands1Player[i]%13);
                    }
                    System.out.println("Player 1 has cards of value " +noSplitSum);
                    if(normalDealerFirstTwoCards<=21&&noSplitSum<=21){
                                if(normalDealerFirstTwoCards>noSplitSum){
                                    System.out.println("RESULT:Dealer wins this round");
                                    dealerScore++;
                                }
                                else if(noSplitSum>normalDealerFirstTwoCards){
                                    System.out.println("RESULT:Player wins this round");
                                    playerScore++;
                                }
                                else{
                                    System.out.println("RESULT:no one wins");
                                }
                    }
                    else if(normalDealerFirstTwoCards>21&&noSplitSum<=21){
                                System.out.println("RESULT:Player wins this round since dealer bursts with value more than 21");
                                playerScore++;
                    }
                    else if(noSplitSum>21&&normalDealerFirstTwoCards<=21){
                                 System.out.println("RESULT:Dealer wins this round since player bursts with value more than 21");
                                 dealerScore++;
                    }
                    else{
                                 System.out.println("RESULT:both player and dealer burst,no one wins");
                    }
                }//closed normal mode for final values for hands1Player with no split
                
            //calculate all game  mode for dealer split but player dont
            if(twoHands==false&&dealerTwoHands==true){
                int noSplitSum=0;
                    for(int i=0; i<2+hitCountPlayer;i++){
                        noSplitSum+=valueCards(hands1Player[i]%13);
                    }
                    System.out.println("Player  has cards of value " +noSplitSum);

                if(noSplitSum<=21&&dealerFirstHand<=21&&dealerSecondHand<=21){
                            if(noSplitSum>dealerFirstHand&&noSplitSum>dealerSecondHand){
                                System.out.println("Player wins");
                                playerScore++;
                            }
                            else if(noSplitSum==dealerFirstHand&&noSplitSum>dealerSecondHand){
                                System.out.println("player wins");
                                playerScore++;
                            }
                            else if(noSplitSum==dealerSecondHand&&noSplitSum>dealerFirstHand){
                                System.out.println("player wins");
                                playerScore++;
                            }
                            else if(noSplitSum==dealerFirstHand&&noSplitSum==dealerSecondHand){
                                System.out.println("No one wins");
                            }
                            else if(dealerFirstHand>noSplitSum||dealerSecondHand>noSplitSum){
                                System.out.println("dealer wins");
                                dealerScore++;
                            }
                }
                     else if(noSplitSum>21&&(dealerFirstHand<=21||dealerSecondHand<=21)){
                        System.out.println("Dealer wins");
                        dealerScore++;
                    }
                    else if(noSplitSum>21&&dealerFirstHand>21&&dealerSecondHand>21){
                        System.out.println("No one wins");
                    }
                    else if(noSplitSum<=21&&dealerFirstHand>21&&dealerSecondHand>21){
                        System.out.println("Player wins");
                        playerScore++;
                    }
                    else if(noSplitSum<=21&&dealerFirstHand>21&&dealerSecondHand<=21){
                        if(noSplitSum>dealerSecondHand){
                            System.out.println("Player wins");
                            playerScore++;
                        }
                        else if(noSplitSum==dealerSecondHand){
                            System.out.println("No one wins");
                        }
                        else if(dealerSecondHand>noSplitSum){
                            System.out.println("Dealer wins");
                            dealerScore++;
                        }
                    }
                    else if(noSplitSum<=21&&dealerSecondHand>21&&dealerFirstHand<=21){
                        if(noSplitSum>dealerFirstHand){
                            System.out.println("Player wins");
                            playerScore++;
                        }
                        else if(noSplitSum==dealerFirstHand){
                            System.out.println("No one wins");
                        }
                        else if(dealerFirstHand>noSplitSum){
                            System.out.println("Dealer wins");
                            dealerScore++;
                        }
                    }
            }//closed game for dealer split but player dont
            
            //calculate hard game mode for player and dealer with no split
                if(twoHands==false&&difficultyGame.equals("hard")&&dealerTwoHands==false){
                    int noSplitSum=0;
                    for(int i=0; i<2+hitCountPlayer;i++){
                        noSplitSum+=valueCards(hands1Player[i]%13);
                    }
                    System.out.println("Player 1 has cards of value " +noSplitSum);
                    if(hardDealerFirstTwoCards<=21&&noSplitSum<=21){
                                if(hardDealerFirstTwoCards>noSplitSum){
                                    System.out.println("RESULT:Dealer wins this round");
                                    dealerScore++;
                                }
                                else if(noSplitSum>hardDealerFirstTwoCards){
                                    System.out.println("RESULT:Player wins this round");
                                    playerScore++;
                                }
                                else{
                                    System.out.println("RESULT:no one wins");
                                }
                    }
                    else if(hardDealerFirstTwoCards>21&&noSplitSum<=21){
                                System.out.println("RESULT:Player wins this round since dealer bursts with value more than 21");
                                playerScore++;
                    }
                    else if(noSplitSum>21&&hardDealerFirstTwoCards<=21){
                                 System.out.println("RESULT:Dealer wins this round since player bursts with value more than 21");
                                 dealerScore++;
                    }
                    else{
                                 System.out.println("RESULT:both player and dealer burst,no one wins");
                    }
                }//closed hard mode for player and dealer with no split
                
            //calculate for both dealer and player split
            if(twoHands==true&&dealerTwoHands==true){
                
                int splitSum1=0;
                int splitSum2=0;   
                    for(int i=0;i<1+hitCountSplitFirst;i++){
                        splitSum1+=valueCards(hands1Player[i]%13);
                    }
                    System.out.println("Hands 1 of player has cards of value " + splitSum1);
                    
                    for(int i=0;i<1+hitCountSplitSecond;i++){
                        splitSum2+=valueCards(hands2Player[i]%13);
                    }
                    System.out.println("Hands 2 of player has cards of value " + splitSum2);
                    System.out.println("Hand 1 of dealer has cards of value " + dealerFirstHand);
                    System.out.println("Hand 2 of dealer has cards of value "+ dealerSecondHand);
                    if(splitSum1<=21&&splitSum2<=21&&dealerFirstHand<=21&&dealerSecondHand<=21){
                            if(splitSum1>dealerFirstHand&&splitSum2>dealerSecondHand){
                                System.out.println("Result:Player wins");
                                playerScore++;
                            }
                            else if(splitSum1>dealerFirstHand&&splitSum2<dealerSecondHand){
                                System.out.println("Result:No one wins");
                            }
                            else if(splitSum1<dealerFirstHand&&splitSum2>dealerSecondHand){
                                System.out.println("Result:No one wins");
                            }
                            else if(dealerFirstHand>splitSum1&&dealerSecondHand>splitSum2){
                                System.out.println("Result: Dealer wins");
                                dealerScore++;
                            }
                            else if(dealerFirstHand==splitSum1&&dealerSecondHand>splitSum2){
                                System.out.println("Dealer wins");
                                dealerScore++;
                            }
                            else if(dealerFirstHand>splitSum1&&dealerSecondHand==splitSum2){
                                System.out.println("Dealer wins");
                                dealerScore++;
                            }
                            else if(splitSum1==dealerFirstHand&&splitSum2>dealerSecondHand){
                                System.out.println("Player wins");
                                playerScore++;
                            }
                            else if(splitSum1>dealerFirstHand&&splitSum2==dealerSecondHand){
                                System.out.println("Player wins");
                                playerScore++;
                            }
                            else if(splitSum1==dealerFirstHand&&splitSum2==dealerSecondHand){
                                System.out.println("No one wins");
                            }
                    }
                    if(splitSum1>21&&dealerFirstHand<=21&&splitSum2>21&&dealerSecondHand<=21){
                        System.out.println("Dealer wins since player bursts");
                        dealerScore++;
                    }
                    if(splitSum1>21&&dealerFirstHand<=21&&splitSum2>21&&dealerSecondHand>21){
                        System.out.println("Dealer wins since player bursts");
                        dealerScore++;
                    }
                    if(splitSum1>21&&dealerFirstHand>21&&splitSum2>21&&dealerSecondHand<=21){
                        System.out.println("Dealer wins since player bursts");
                        dealerScore++;
                    }
                    if(splitSum1<=21&&dealerFirstHand<=21&&splitSum2>21&&dealerSecondHand<=21){
                                if(splitSum1>dealerFirstHand){
                                    System.out.println("No one wins");
                                }
                                else if(splitSum1<dealerFirstHand){
                                    System.out.println("Dealer wins");
                                    dealerScore++;
                                }
                                else{
                                    System.out.println("Dealer wins");
                                    dealerScore++;
                                }
                    }
                    if(splitSum1>21&&dealerFirstHand<=21&&splitSum2<=21&&dealerSecondHand<=21){
                                if(splitSum2>dealerSecondHand){
                                    System.out.println("No one wins");
                                }
                                else if(splitSum2<dealerSecondHand){
                                    System.out.println("Dealer wins");
                                    dealerScore++;
                                }
                                else{
                                    System.out.println("Dealer wins");
                                    dealerScore++;
                                }
                    }
                    if(splitSum1<=21&&dealerFirstHand<=21&&splitSum2<=21&&dealerSecondHand>21){
                                if(splitSum1>dealerFirstHand){
                                    System.out.println("Player wins");
                                    playerScore++;
                                }
                                else if(splitSum1<dealerFirstHand){
                                    System.out.println("No one wins");
                                }
                                else{
                                    System.out.println("Player wins");
                                    playerScore++;
                                }
                    }
                    if(splitSum1<=21&&dealerFirstHand<=21&&splitSum2>21&&dealerSecondHand>21){
                                if(splitSum1>dealerFirstHand){
                                    System.out.println("Player wins");
                                    playerScore++;
                                }
                                else if(splitSum1<dealerFirstHand){
                                    System.out.println("Dealer wins");
                                    dealerScore++;
                                }
                                else{
                                    System.out.println("No one wins");
                                }
                    }
                    if(splitSum1<=21&&dealerFirstHand>21&&splitSum2<=21&&dealerSecondHand<=21){
                                if(splitSum2>dealerSecondHand){
                                    System.out.println("Player wins");
                                    playerScore++;
                                }
                                else if(splitSum2<dealerSecondHand){
                                    System.out.println("No one wins");
                                }
                                else{
                                    System.out.println("Player wins");
                                    playerScore++;
                                }
                    }
                    if(splitSum1<=21&&dealerFirstHand>21&&splitSum2>21&&dealerSecondHand<=21){
                        System.out.println("No one wins");
                    }
                    if(splitSum1>21&&dealerFirstHand>21&&splitSum2<=21&&dealerSecondHand<=21){
                                if(splitSum2>dealerSecondHand){
                                    System.out.println("Player wins");
                                    playerScore++;
                                }
                                else if(splitSum2<dealerSecondHand){
                                    System.out.println("Dealer wins");
                                    dealerScore++;
                                }
                                else{
                                    System.out.println("No one wins");
                                }
                    }
                    if(splitSum1<=21&&dealerFirstHand>21&&splitSum2<=21&&dealerSecondHand>21){
                                System.out.println("Player wins");
                                playerScore++;
                    }
                    if(splitSum1<=21&&dealerFirstHand>21&&splitSum2>21&&dealerSecondHand>21){
                                System.out.println("Player wins");
                                playerScore++;
                    }
                    if(splitSum1>21&&dealerFirstHand>21&&splitSum2<=21&&dealerSecondHand>21){
                                System.out.println("Player wins");
                                playerScore++;
                    }
                    if(splitSum1>21&&dealerFirstHand>21&&splitSum2>21&&dealerSecondHand>21){
                                System.out.println("No one wins");
                    }
                    if(splitSum1>21&&dealerFirstHand<=21&&splitSum2<=21&&dealerSecondHand>21){
                                System.out.println("No one wins");
                    }
            }//closed both dealer and player splits
        }//closed if start statement
        
        else if(starter.equals("end")){
            System.out.println("You have quited the game");
            break;
        }
        }//closed for statement
        System.out.println("The final score of player is " + playerScore);
        System.out.println("The final score of dealer is " + dealerScore);
    }
   
    
    
    
    public static int easyMode(int x,int y){ //this method is to calculate the card value of first two cards obtained by dealer in easy mode
        
        int ranks = x%13;
        int ranks2 =y%13;
        int sum=0;
        switch(ranks){
            case 0:
                    if(ranks2<5){ //when card value of the next card is has value of ace,2,3 or 4,5 first card with ace is considered 1(since 0 represents Ace,1 represents value of 2)
                        sum+=1;
                    }
                    else if(ranks2>=5){// else the first ace card will have value of 11
                        sum+=11;
                    }
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
         switch(ranks2){
            case 0:
                    if(ranks<5){
                        sum+=1;
                    }
                    else if(ranks>=5){
                        sum+=11;
                    }
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks2+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
        return sum;
    }
    
        public static int normalMode(int x,int y){ //this method is to calculate the card value of first two cards obtained by dealer in normal mode
        
        int ranks = x%13;
        int ranks2 =y%13;
        int sum=0;
        switch(ranks){
            case 0:
                    if(ranks2<5){
                        sum+=1;
                    }
                    else if(ranks2>=5){
                        sum+=11;
                    }
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
         switch(ranks2){
            case 0:
                    if(ranks<5){
                        sum+=1;
                    }
                    else if(ranks>=5){
                        sum+=11;
                    }
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks2+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
        return sum;
    }
        
        public static int hardMode(int x,int y){ //this method is to calculate the card value of first two cards obtained by dealer in hard mode
        
        int ranks = x%13;
        int ranks2 =y%13;
        int sum=0;
        switch(ranks){
            case 0:
                    if(ranks2<5){
                        sum+=1;
                    }
                    else if(ranks2>=5){
                        sum+=11;
                    }
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
         switch(ranks2){
            case 0:
                    if(ranks<5){
                        sum+=1;
                    }
                    else if(ranks>=5){
                        sum+=11;
                    }
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks2+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
        return sum;
    }
    
    public static int valueCards(int x){ // this method calculate the player card value
        int ranks = x%13;
        int sum=0;
        int aceValue=0;
        switch(ranks){
            case 0:
                    Scanner oneEleven = new Scanner(System.in);
                    do{
                        System.out.println("Player please decide Ace to be 1 or 11");
                        aceValue = oneEleven.nextInt();
                    }while(aceValue!=1&&aceValue!=11);
                    switch(aceValue){
                        case 1:
                            sum+=1;
                            break;
                        case 11:
                            sum+=11;
                            break;
                        default :
                            System.out.println("error");
                            break;
                    }
                       
                    break;
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                    sum+=ranks+1;
                    break;
            case 9:case 10:case 11: case 12:
                    sum+=10;
                    break;
            default:
                    System.out.println("error");
                    break; 
        }
        
        return sum;
    }
    
    
    public static int blackJack(int x){ //this method determines whether blackJack is obtained or not
        int sum =0;
        int ranks =x%13;
        switch(ranks){
            case 0:
                sum+=11;
                break;
            case 9: case 10: case 11: case 12:
                sum+=10;
                break;
            default:
                    break;
        }
        return sum;
    }
    
    
}

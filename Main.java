
/*
ISU - Othello ICS3U1
Amir Ahmed
Ms Karasinska 
Due June 16 2023
Program Description - This program will simulate a game of othello between two players until one player wins or it is a draw. The player will then be asked if they want to play again or not.
Data Dictionary:
boardState - holds the position of every piece in play
currentPlayer - holds which player is currently making a move
xCounter - holds how many pieces player x/RED has
oCounter - holds how many pieces player o/BLUE has
red - holds the string that would print RED in color red
blue - holds the string that would print BLUE in color blue
num1 - holds the y position of player move
num2 - holds the x position of player move
valid - holds whether the current operation is valid or not
i,j - holds the direction that the program is checking if valid or not
tempValid - used to hold whether user input is valid or not
count - holds how many times the while loop has ran
move - an int array that represents what move the player has made
y - holds the y value of player move
xString - holds the x value of the player move as a string

IMPORTANT
to test if win checking works for if one piece has no piece of their color, enter the following moves 
f5,d6,c5,f4,e3,f6,g5,e6,e7
*/

public class Main {
  static char[][] boardState = setBoard(); //declares and initializes variable
  static char currentPlayer = 'X';//declares and initializes variable
  static int xCounter = 2;//declares and initializes variable
  static int oCounter = 2;//declares and initializes variable
  public static String red = "\u001B[31mRED\u001B[37m";//declares and initializes variable
  public static String blue = "\u001B[34mBLUE\u001B[37m";//declares and initializes variable

  public static boolean validSquare(int num1, int num2) //method for checking if a square is valid
  { 
    boolean valid = false; //declares and initializes variable
    if (num1 < 0 || num1 > 7 || num2 > 7 || num2 < 0) { //if number is out of the range return false
      return false;
    }

    else if (boardState[num1][num2] != '.') { //if square is occupied, return false
      return false;
    } 
    else 
    {

      for (int i = -1; i < 2; i++) { // checking in every direction
        for (int j = -1; j < 2; j++) {
          if (i == 0 && j == 0) //if i and j is zero, move on to the next direction
            j++;

          valid = valid || validSquare(num1, num2, i, j); //checks if all 8 directions are valid. if one is valid, then the entire statement is true
        }
      }
    }
    return valid; //returns valid
  }

  public static boolean validSquare(int num1, int num2, int i, int j) { //checking valid for one direction
    boolean tempValid = false; //declares and initializes variable
    while (!tempValid) 
    { //while there is no error 
      boolean stop = false; //declares and initializes variable
      try 
        {

        if (boardState[num1 + (i)][num2 + (j)] != '.' && boardState[num1 + (i)][num2 + (j)] != currentPlayer) //checks if the piece 1 square away from the current piece in the current direction is opposite 
        {
          int count = 1; //declares and initializes variable

          while (((num1 + (i * count)) >= 0 && (num1 + (i * count)) < 8 && (num2 + (j * count)) >= 0
              && (num2 + (j * count)) < 8) && !stop)  //if an adjacent piece is oppoiste, checks in that direction unit it sees a same piece or an empty square
          {
            if (boardState[num1 + (i * count)][num2 + (j * count)] == '.') 
            {
              stop = true; //if empty piece, stops the while loop and returns false
            }
            if (boardState[num1 + (i * count)][num2 + (j * count)] == currentPlayer) 
            {
              return true; //if there is a same piece, that means that the square is valid
            }
            count++; //adds 1 to the count so the program can check the next direction
          }
        }
        tempValid = true; //sets tempValid to true so the while loop does not trigger
      } 
      catch (Exception e) //checks if the direction goes outside of the array. if it does, then move on to the next direction
      {
        tempValid = false;
        j++; //moves on to the next direction
        if (j == 2) { //when moving on to the next direction, if j is 2, then adds 1 to i and makes j zero
          i++; 
          j = 0;
          if (i == 2) {
            return false; //if i is 2(all direction have been checked), return false
          }
        }
      }
    }
    return false; //reutrns false if no direction is true
  }

  public static String printPlayer() 
  { //prints out the current playing in their respective colors
    if (currentPlayer == 'X')
      return red;
    else
      return blue;
  }

  public static void printStats() 
  {
    System.out.println(red + " has \u001B[32m" + xCounter + " pieces. \u001B[37m" + blue + " has \u001B[32m" + oCounter
        + " pieces.\u001B[37m"); //prints out how many pieces each player has
  }

  public static void placePiece(int num1, int num2) //this simulates placing a piece and changing other pieces on the board
  {
    boardState[num1][num2] = currentPlayer; //sets the piece that the player chose
    for (int i = -1; i < 2; i++) 
    { 
      for (int j = -1; j < 2; j++) //checking in every direction
      {
        if (validSquare(num1, num2, i, j))  //if the current direction is valid, starts placing pieces
        {
          int count = 1; //declares and initializes variable
          boolean stop = false; //declares and initializes variable
          while (((num1 + (i * count)) >= 0 && (num1 + (i * count)) < 8 && (num2 + (j * count)) >= 0
              && (num2 + (j * count)) < 8) && !stop) //while the squares being checked are in the array. if stop is true, then the while loop is stopped 
          {
            if (boardState[num1 + (i * count)][num2 + (j * count)] == '.'
                || boardState[num1 + (i * count)][num2 + (j * count)] == currentPlayer) //if the program hits an empty square or own piece, stop the while loop
            {
              stop = true;
            } 
            else 
            {
              boardState[num1 + (i * count)][num2 + (j * count)] = currentPlayer; //otherwise, change the current square into current player's piece
            }
            count++; //increases count
          }
        }
      }
    }
  }

  public static void printBigLine() //prints a big line 
  {
    System.out.println(
        "____________________________________________________________________________________________________________________________");
  }

  public static void reCount() //counts all the red and blue pieces 
  {
    xCounter = 0; //resets count
    oCounter = 0;//resets count

    for (int i = 0; i < 8; i++) 
    {
      for (int j = 0; j < 8; j++) //checks ever square on the board
        {
        if (boardState[i][j] == 'X')  //if the square is x, then adds one to the xCounter
        {
          xCounter++;
        }
        if (boardState[i][j] == 'O') //if the square is o, then adds one to the oCounter
        {
          oCounter++;
        }
      }
    }
  }

  public static boolean gameFinished() //checks if game is finished
  { 
    if (xCounter == 0 || oCounter == 0) //if one player has no piece on the board, then the game is finished
    { 
      return true;
    }
    if (xCounter + oCounter == 64) //if the entire board is full, then the game is finished
    {
      return true;
    }

    return false;
  }

  public static boolean skipTurn() //checks if there is no possible moves for a player and skips their turn
  { 
    boolean valid = true;
    for (int i = 0; i < 8; i++) 
    {
      for (int j = 0; j < 8; j++)
        {
        valid = valid && !validSquare(i, j); //checks every square on the board if it is valid or not
      }
    }
    return valid;
  }

  public static char[][] setBoard() //sets the starting board
  {
    char[][] boardState = new char[8][8];
    for (int i = 0; i < 8; i++) 
    {
      for (int j = 0; j < 8; j++) 
      {
        boardState[i][j] = '.';// sets every square on the board to empty
      }
    }
    boardState[3][3] = 'O';
    boardState[4][4] = 'O';
    boardState[3][4] = 'X';
    boardState[4][3] = 'X';//changes the select squares to their respective pieces
    return boardState; //returns the board
  }

  public static void printTitle() { //prints the title
    System.out.println(
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
    System.out.println(
        "@@@@@@@@@@@@@@@@@@&@&@@&#//(#@@&&@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          ");
    System.out.println(
        "@@@@@@@@@@@@@@&@%                   &&&@@@@@@@@@@@@@@@@@@@&@@@@@@@@@@@@@@&&&&&&&&&&&@@@@@@@@@@@@@@@@@@@@@@                                                                                              ");
    System.out.println(
        "@@@@@@@@@@@@&,                         %&&@@@@@@@@@@&&         #&&@@@@@@@&           @@@@@@@@@@@@@@@@@@@@@                                                                                              ");
    System.out.println(
        "@@@@@@@@@@&                              *@@@@@@@@@@&%          (&@@@@@@&&           &@@@@@@@@@@@@@@@@@@@@                                   @@@@@@@@@@     @@@@@@@@@@                                 ");
    System.out.println(
        "@@@@@@@@@*                                 @&@@@@@@@&#          #&@@@@@@&&           &&@@@@@@@@@@@@@@@@@@@                                   @@@@@@@@@@     @@@@@@@@@@                                 ");
    System.out.println(
        "@@@@@@@&               .&&&@@.              &&@@@@@&&(          %&&@@@@@&&           &@@@@@@@@@@@@@@@@@@@@                                   @@@@@@@@@@     @@@@@@@@@@                                 ");
    System.out.println(
        "@@@@@@@              &@@@@@@@@&&             &@@@@@@@*          &&&&&&@@&&           &&@@&@&&&&&@@@@@@@@@@                                   @@@@@@@@@@     @@@@@@@@@@                                  ");
    System.out.println(
        "@@@@@@#             @@@@@@@@@@@@@             &&&.                   #@@&&           &&#         .@@@@@@@@             /%%%%(.               @@@@@@@@@@     @@@@@@@@@@                                  ");
    System.out.println(
        "@@@@@@             &@@@@@@@@@@@@&*            @&&                    %&&&&                          &@@@@@        @&&&@@@@@@@@@@@@           @@@@@@@@@@     @@@@@@@@@@              /&@@@@/             ");
    System.out.println(
        "@@@@@&             &@@@@@@@@@@@@&&            (&&                    &@@&&                           (@@@@     /@&@@@@@@@@@@@@@@@@@&(        @@@@@@@@@@     @@@@@@@@@@         *&@@@@@@@@@@@@@@@*       ");
    System.out.println(
        "@@@@@%            .@@@@@@@@@@@@@&&            /&@@@@@           &&&&&@@@&%                            @@@@    &@@@@@@@@&&@@@&@@@@@@@@@       @@@@@@@@@@     @@@@@@@@@@       %@@@@@@@@@@@@@@@@@@@@&     ");
    System.out.println(
        "@@@@@&            .@@@@@@@@@@@@@&#            &&@@@&&           &@@@@@@@@(           .@&&&/           %@@@   &@@@@@@@@(       &@@@@@@@@      @@@@@@@@@@     @@@@@@@@@@     @@@@@@@@@@@@@@@@@@@@@@@@    ");
    System.out.println(
        "@@@@@@             @@@@@@@@@@@@&&.            @@@@@&%          ,&@@@@@@@&*          *&@@@@&#          %@@@  &@@@@@@@@&         @@@@@@@@%     @@@@@@@@@@     @@@@@@@@@@     @@@@@@@@@@      @@@@@@@@@@   ");
    System.out.println(
        "@@@@@&             &@@@@@@@@@@@&&            *&@@@@&*          *&@@@@@@@&,          @@@@@@@&          @@@@ *&@@@@@@@@@@@@@@@@@@@@@@@@@@@     @@@@@@@@@@     @@@@@@@@@@    %@@@@@@@@@        @@@@@@@@@%  ");
    System.out.println(
        "@@@@@@@             @@@@@@@@@@&%             @@@@@@&.          #&@@@@@@@&           &@@@@@@#          @@@@ %@@@@@@@@@@@@@@@@@@@@@@@@@@@&     @@@@@@@@@@     @@@@@@@@@@    @@@@@@@@@@        @@@@@@@@@@ ");
    System.out.println(
        "@@@@@@@(              @&@@@&&%              &@@@@@@&           &&@@@@@@@&           &@@@@@&/          @@@@ %@@@@@@@@@@@@@@@&@&&@&&&&@&&&     @@@@@@@@@@     @@@@@@@@@@    @@@@@@@@@%        @@@@@@@@@@ ");
    System.out.println(
        "@@@@@@@@%                                  &@@@@@@@&            &&&@&@@&&          .&@@@@@&,          &&@@ *&@@@@@@@@,                       @@@@@@@@@@     @@@@@@@@@@    @@@@@@@@@@        @@@@@@@@@@ ");
    System.out.println(
        "@@@@@@@@@@                               %@@@@@@@@@&                @@@@@          *@@@@@@&           &@@@  @@@@@@@@@@.      #&&&@@@@&@      @@@@@@@@@@     @@@@@@@@@@    #@@@@@@@@@/      %@@@@@@@@@(  ");
    System.out.println(
        "@@@@@@@@@@@@                           &@@@@@@@@@@@&.               &@@&&          (@@@@@@&          ,@@@@   @&@@@@@@@@&&@@&@@@@@@@@@@       @@@@@@@@@@     @@@@@@@@@@      @@@@@@@@@@@  (&@@@@@@@@@@   ");
    System.out.println(
        "@@@@@@@@@@@@@@&                     &&@@@@@@@@@@@@@@&               &@@&(          %@@@@@@&          /@@@@    *&@@@@@@@@@@@@@@@@@@@&(        @@@@@@@@@@     @@@@@@@@@@       &@@@@@@@@@@@@@@@@@@@@@@&    ");
    System.out.println(
        "@@@@@@@@@@@@@@@@@@@%,         ,%@@@@@@@@@@@@@@@@@@@@@@@&/          .@@@@,          @@@@@@@@          %@@@@       &&@@@@@@@@@@@@&&&           @@@@@@@@@@     @@@@@@@@@@         &@@@@@@@@@@@@@@@@@@@      ");
    System.out.println(
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            ,/(#(*.                @@@@@@@@@@     @@@@@@@@@@            *@@@@@@@@@@@@#         ");
    System.out.println("");
    System.out.println("");
    System.out.println("");
    System.out.println("");
  }

  public static void printEnding() { //prints thanks for playing
    printBigLine();
    System.out.println(
        "ooooooooooooo ooooo   ooooo       .o.       ooooo      ooo oooo    oooo  .oooooo..o           oooooooooooo   .oooooo.   ooooooooo.             ooooooooo.   ooooo              .o.       oooooo   oooo ooooo ooooo      ooo   .oooooo.    ");
    System.out.println(
        "8'   888   `8 `888'   `888'      .888.      `888b.     `8' `888   .8P'  d8P'    `Y8           `888'     `8  d8P'  `Y8b  `888   `Y88.           `888   `Y88. `888'             .888.       `888.   .8'  `888' `888b.     `8'  d8P'  `Y8b   ");
    System.out.println(
        "     888       888     888      .8^888.      8 `88b.    8   888  d8'    Y88bo.                 888         888      888  888   .d88'            888   .d88'  888             .8'888.       `888. .8'    888   8 `88b.    8  888           ");
    System.out.println(
        "     888       888ooooo888     .8' `888.     8   `88b.  8   88888[       `'Y8888o.             888oooo8    888      888  888ooo88P'             888ooo88P'   888            .8' `888.       `888.8'     888   8   `88b.  8  888           ");
    System.out.println(
        "     888       888     888    .88ooo8888.    8     `88b.8   888`88b.         `'Y88b            888    '    888      888  888`88b.               888          888           .88ooo8888.       `888'      888   8     `88b.8  888     ooooo ");
    System.out.println(
        "     888       888     888   .8'     `888.   8       `888   888  `88b.  oo     .d8P            888         `88b    d88'  888  `88b.             888          888       o  .8'     `888.       888       888   8       `888  `88.    .88'  ");
    System.out.println(
        "    o888o     o888o   o888o o88o     o8888o o8o        `8  o888o  o888o 8''88888P'            o888o         `Y8bood8P'  o888o  o888o           o888o        o888ooooood8 o88o     o8888o     o888o     o888o o8o        `8   `Y8bood8P'   ");

    printBigLine();
  }

  public static void printBoard() {

    System.out.println("  A B C D E F G H");
    for (int i = 0; i < 8; i++) 
    {
      System.out.print((i + 1) + " "); //checks every square on the board
      for (int j = 0; j < 8; j++) 
      {
        if (boardState[i][j] == 'X') 
        {
          System.out.print("\u001B[31m\u25CF\u001B[37m "); //if x, then prints red square
        } 
        else if (boardState[i][j] == 'O') 
        {
          System.out.print("\u001B[34m\u25CF\u001B[37m "); //if o, then print blue square
        } 
        else 
        {
          if (validSquare(i, j)) {
            System.out.print("\u26F6 "); //if empty square is valid, then prints the marker 
          } else
            System.out.print(". "); //if empty squaer is not valid then prints .
        }
      }
      System.out.println(); //prints a line
    }
  }

  public static int[] getUserMove() //gets what move the user wants to play
  {
    int[] move = new int[2]; //intializes and declares variable
    boolean tempValid = false;//intializes and declares variable
    int y = 0;//intializes and declares variable
    String xString;//intializes variable

    printBigLine();
    System.out.println("It is player " + printPlayer() + "'s turn"); //prints whose turn it is
    printStats();
    System.out.println("Enter the x coordinate for your move(A,B,C,D,E,F,G,H)"); //asks for the coordinates of the x move
    xString = In.getString().toUpperCase();//gets a string form the user 
    printBigLine();
    while (xString.length() != 1 || (xString.charAt(0) < 'A' || xString.charAt(0) > 'H')) //while the string is greater than length one or not within the range, asks again
    {
      System.out
          .println("The Letter you have input is not valid. \nEnter the x coordinate for your move(A,B,C,D,E,F,G,H)");
      xString = In.getString().toUpperCase();
      printBigLine();
    }
    move[0] = xString.charAt(0) - 65; //sets the first element of the move array

    while (!tempValid) //while invalid repeat
    {
      try {
        System.out.println("Enter the y coordinate for your move(1,2,3,4,5,6,7,8)");
        y = Integer.parseInt(In.getString()); //asks the user to enter an int. If the user enters a letter, then the catch statement triggers
        if (y > 8 || y < 1) {
          throw new ArithmeticException(); //if the number is not within the range, triggers the catch statement
        }
        tempValid = true; //sets valid to true to stop the while loop if input is valid
      } catch (Exception e) {
        System.out.println("The number you have entered is not valid.");
        tempValid = false; //tells the user the input is invalid 
        printBigLine();
      }
    }
    move[1] = y - 1; //sets the second element of the move array 

    return move; //returns move 

  }

  public static void switchPlayer() // switches the current player
  {
    if (currentPlayer == 'X')
      currentPlayer = 'O';
    else
      currentPlayer = 'X';
  }

  public static void printWinner()  //prints who won the game
  {
    if (xCounter > oCounter) {
      System.out.println("Player " + red + " Wins"); //if red has more pieces, then says red won 
    } else if (oCounter > xCounter) {
      System.out.println("Player " + blue + " Wins"); //if blue has more pieces, then says blue won
    } else {
      System.out.println("Tie Game"); //else says its a tie 
    }
  }

  public static String getStartStop() //asks the user if they want to play the game
  {
    String startStop = In.getString();
    while (!startStop.equalsIgnoreCase("stop") && !startStop.equalsIgnoreCase("play")) //if the user enters invalid input, then ask again 
    {
      printBigLine();
      System.out.println("Your input is invalid. Please enter a valid input");
      startStop = In.getString();
    }
    return startStop;
  }

  public static void printRules() //prints out the rules of the game 
  {
    printBigLine();
    System.out
        .println("Othello is a very simple game to learn but very hard to master. \nThe board starts out like this \n");
    printBoard();
    System.out.println("\nThe " + red + " and " + blue
        + " circles represent player pieces. the \u26F6 symbol represents the valid moves a player can make in a certain turn. \nTwo players take turns placing pieces until either one player has no piece of their color left or the board is entirely filled. \nThen all the pieces are counted to see who has the most pieces on the board. The player with the most pieces of the board wins. \nTo play a piece, you must place your piece so that it sandwiches the opponent pieces with your own color. \nThen all the pieces that are sandwiched between your pieces are switched to be your color.\n(This works horizontally, vertically and diagonally. only opponent pieces sandwiched between your pieces and the piece you just played will be switched)\nYou must capture a piece every turn \nIf there is no possible move for one player, their turn is skipped and the next player moves again. \nNow that you know the rules, I hope you have fun playing Othello! \nWhen you are done reading, press enter");
    In.getInt();
    printBigLine();
  }

  public static void main(String[] args) //main
  {
    printTitle();
    printBigLine();

    System.out.println("WELCOME TO OTHELLO! \n Type 'play' to enter the game. type 'stop' to exit.");
    String startStop = getStartStop(); //asks the user if they want to play the game 

    while (startStop.equalsIgnoreCase("play")) //while the user wants to play the game, run the game 
    { 
      boardState = setBoard(); 
      currentPlayer = 'X';
      xCounter = 2;
      oCounter = 2;
      //sets all the variables 

      System.out.println(
          "Do you want to review the rules of how to play? type 'yes' to see the rules. type 'no' to start playing");
      String rules = In.getString(); //asks the user if they want to see the rules of the game 
      while (!rules.equalsIgnoreCase("yes") && !rules.equalsIgnoreCase("no")) //while invlaid, asks again
      {
        System.out.println("Invalid input. Please enter a valud input");
        rules = In.getString();
      }

      if (rules.equalsIgnoreCase("yes"))
        printRules(); //if the user wants to see the rules, then prints out the rules 


      printBoard();
      int[] playerMove = new int[2]; //sets the length of the move array 
      boolean staleMate = false;
      while (!gameFinished() && !staleMate) { //while the game is not finished, the players take turns making moves 
        if (skipTurn()) //if a player cannot go, skips their turn and switches players 
        {
          System.out.println(printPlayer() + " cannot move. Turn is skipped");
          switchPlayer();
          printBoard();
        }
        if(skipTurn())
        {
          staleMate = true; //if there is no valid move again, that means that no player has a valid move and the game is finished 
        }
        else 
        {
        playerMove = getUserMove(); //asks the user for their move 
        while (!validSquare(playerMove[1], playerMove[0]) && !skipTurn()) //if the user does not enter a valid move, then asks again
        {
          System.out.println("The move you entered is invalid. Please try again");
          playerMove = getUserMove();
        }
        placePiece(playerMove[1], playerMove[0]); //places a piece 
        reCount();//counts all the pieces 
        switchPlayer(); //switches the player
        printBoard(); //prints the board 
        }
      }
      System.out.println("Game has finished!"); //once the while loop is done, then prints out who won the game 
      printBigLine();
      printStats();
      printWinner();
      System.out.println("Do you want to play again? type 'play' to play again. type 'stop' to exit");
      startStop = getStartStop(); //asks the user if they want to play again

    }
    printEnding(); //once the user is done player, prints out thanks for playing 
  }
}

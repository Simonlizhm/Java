import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Predicate;
import javalib.impworld.*;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import java.awt.Color;

//to represent a game of FloodIt
class FloodItWorld extends World {
  Random rand;
  // All the cells of the game
  ArrayList<ArrayList<Cell>> cells;
  WorldScene scene;
  int movesSoFar; //number of moves user has made so far
  int givenMoves; //number of moves user has in total
  int currentTick;
  int boardSize;
  static int BOARD_SIZE = 10;

  FloodItWorld() {
    this.scene = new WorldScene(750, 750);
    this.rand = new Random(10);
    this.cells = new ArrayList<ArrayList<Cell>>();
    this.movesSoFar = 0;
    this.givenMoves = 20;
    this.currentTick  = 1;
    this.boardSize = 10;

    this.generateCells();
    this.connectCells();
  }

  //constructor for testing
  FloodItWorld(int size) {
    this.scene = new WorldScene(750, 750);
    this.rand = new Random(10);
    this.cells = new ArrayList<ArrayList<Cell>>();
    this.movesSoFar = 0;

    this.currentTick  = 1;
    this.boardSize = size;

    FloodItWorld.BOARD_SIZE = size;  //set board size to what user input
    this.givenMoves = size * 2;      //adjust number of given moves according to board size
  }

  //EFFECT: adds the necessary number of randomly colored cells to this game's cells
  public void generateCells() { 

    ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(
        Color.red, Color.orange, Color.green, Color.pink, Color.blue));

    for (int i = 0; i < this.boardSize; i++) {
      ArrayList<Cell> row = new ArrayList<Cell>(); 

      for (int j = 0; j < this.boardSize; j++) {
        //adds a cell with a position, random color, not flooded, and no neighbors
        row.add(new Cell((j + 1) * 40 + 20, (i + 1) * 40 + 40, colors.get(rand.nextInt(5)), 
            false, null, null, null, null));
      }
      cells.add(row);
    }
  }

  //EFFECT: connects this FloodItWorld's cells
  public void connectCells() {
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        //does this cell have a cell above it?
        if (i != 0) {
          Cell currentCell = this.cells.get(i).get(j);
          Cell aboveCurrentCell = this.cells.get(i - 1).get(j);
          currentCell.topHelper(aboveCurrentCell);
          aboveCurrentCell.bottomHelper(currentCell);
        }
        //does this cell have a cell to its left?
        if (j != 0) {
          Cell currentCell = this.cells.get(i).get(j);
          Cell leftCurrentCell = this.cells.get(i).get(j - 1);
          currentCell.leftHelper(leftCurrentCell);
          leftCurrentCell.rightHelper(currentCell);
        }
      }
    }
    this.cells.get(0).get(0).flooded = true;
  }

  //EFFECT: places all the cells on the cell on this game's scene
  public void placeAll() {
    for (ArrayList<Cell> row : this.cells) {
      for (Cell cell : row) {
        this.scene.placeImageXY(cell.draw(), cell.x, cell.y);
      }
    }
  }

  //are all the cells in this game's board the same color?
  public boolean isDone() {
    //Color color = this.cells.get(0).get(0).color;
    for (ArrayList<Cell> row : this.cells) {
      for (Cell cell : row) {
        if (! cell.sameColor(this.cells.get(0).get(0))) {
          return false;
        }
      }
    }
    return true;
  }

  //displays the number of moves the player has and if they won or lost the game
  public void displayScore() {
    this.scene.placeImageXY(new TextImage("Moves: " + this.movesSoFar + "/" 
        + this.givenMoves, 20, Color.black), 
        105, 30);

    if (this.movesSoFar >= this.givenMoves && ! this.isDone() && !this.isProgress()) {
      this.scene.placeImageXY(new TextImage("You lose", 20, Color.black), 
          300, 30);
    }
    else if (this.movesSoFar <= this.givenMoves && this.isDone() && this.cells.size() > 0 
        && !this.isProgress()) {
      this.scene.placeImageXY(new TextImage("You win", 20, Color.black), 
          300, 30);
    }
  }

  //EFFECT: determines the color the user chooses and updates the game accordingly
  public void onMouseClicked(Posn pos) {
    int row = ((pos.x ) / 40) - 1;
    int col = ((pos.y - 20) / 40) - 1;

    //is the mouse click on the board?
    if (row <= this.cells.size() && col <= this.cells.size()) {
      Color color = this.cells.get(col).get(row).color;
      //is the board's colors changing right now?
      //user isn't selecting the same color again
      if (! this.isProgress() && color != this.cells.get(0).get(0).color) {
        this.movesSoFar = this.movesSoFar + 1;  //increase num of moves by one
        this.cells.get(0).get(0).color = color;  //change top left cell's color to selected color
      }  
    } 
  }

  //are this board's cell's colors in the process of changing right now?
  public boolean isProgress() {
  
    for (ArrayList<Cell> row : this.cells) {
      for (Cell cell : row) {
        //is a cell flooded but not the right color?
        if (! cell.sameColor(this.cells.get(0).get(0)) && cell.flooded) {
          return true;
        }
      }
    }
    return false;
  }

  //EFFECT: makes this game's cell's flooded to be true if their neighbors are flooded 
  //and they're the same color
  public void flood() {
    ArrayList<Cell> output = new ArrayList<Cell>();

    for (ArrayList<Cell> row : this.cells) {
      for (Cell cell : row) {
        output.addAll(cell.floodHelper(new Negate(), new Identity()));   
      }
    }
    this.changeToFlooded(output);
  }

  //EFFECT: changes this game's cell's colors if a cell and a neighboring cell are flooded 
  //but different colors
  public void floodColor() {
    ArrayList<Cell> output = new ArrayList<Cell>();

    for (ArrayList<Cell> row : this.cells) {
      for (Cell cell : row) {
        output.addAll(cell.floodHelper(new Identity(), new Negate()));     
      }
    }
    this.changeColor(output);
  }


  //EFFECT: changes all the cells in the given list to be the top left cell's color
  public void changeColor(ArrayList<Cell> loc) {
    for (Cell cell : loc) {
      cell.color = this.cells.get(0).get(0).color;
    }
  }

  //EFFECT: changes all the cells in the given list to flooded
  public void changeToFlooded(ArrayList<Cell> loc) {
    for (Cell cell : loc) {
      cell.flooded = true;
    }
  }

  @Override 
  //EFFECT: update the cell's flooded field and color on each tick
  public void onTick() {
    this.flood();
    this.floodColor();
    this.currentTick = this.currentTick + 1;
  }

  //EFFECT: restart the game when r is pressed
  @Override
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.cells = new ArrayList<ArrayList<Cell>>();
      this.movesSoFar = 0;
      this.generateCells();
      this.connectCells();
    }
  }

  @Override
  //draws the cells and displays the score for this game
  public WorldScene makeScene() {
    this.scene = new WorldScene(750, 750);
    this.placeAll();
    this.displayScore();
    return this.scene;
  }
}


//to represent a predicate that determines whether a Cell is flooded
class Negate implements Predicate<Boolean> {
  //is this cell flooded?
  public boolean test(Boolean bool) {
    return ! bool;
  }
}

class Identity implements Predicate<Boolean> {
  //is this cell flooded?
  public boolean test(Boolean bool) {
    return bool;
  }
}


import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Predicate;
import javalib.worldimages.*;


// Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;
  
  Cell(int x, int y, Color color, boolean flooded, Cell left, Cell top, Cell right, Cell bottom) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  //EFFECT: sets this cell's left as the given node
  public void leftHelper(Cell other) {
    this.left = other;
  }

  //EFFECT: sets this cell's top as the given node
  public void topHelper(Cell other) {
    this.top = other;
  }

  //EFFECT: sets this cell's right as the given node
  public void rightHelper(Cell other) {
    this.right = other;
  }
  
  //EFFECT: sets this cell's bottom as the given node
  public void bottomHelper(Cell other) {
    this.bottom = other;
  }

  //draws this cell as an image
  public WorldImage draw() {
    return new RectangleImage(40, 40, OutlineMode.SOLID, this.color);
  }

  //does the given cell and this cell have the same color?
  public boolean sameColor(Cell other) {
    return this.color == other.color;
  }

  //returns a list of cells that meet a criteria:
  //is it flooded? is its neighbor flooded? does it have the same color as its neighbor?
  public ArrayList<Cell> floodHelper(Predicate<Boolean> isFlooded, Predicate<Boolean> sameColor) {
    ArrayList<Cell> output = new ArrayList<Cell>();

    if (isFlooded.test(this.flooded)) {
      if ((this.left != null && this.left.flooded && sameColor.test(this.left.sameColor(this)))
          || (this.top != null && this.top.flooded && sameColor.test(this.top.sameColor(this)))
          || (this.right != null && this.right.flooded 
          && sameColor.test(this.right.sameColor(this)))
          || (this.bottom != null && this.bottom.flooded 
          && sameColor.test(this.bottom.sameColor(this)))) {
        output.add(this);
      }
    }
    return output;
  }
}


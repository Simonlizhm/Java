import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javalib.impworld.*;

import java.awt.Color;
import java.util.ArrayDeque;

//to represent a Maze 
class Game extends World {
  ArrayList<ArrayList<Vertex>> vertices;
  ArrayList<Edge> edges;
  ArrayDeque<Vertex>  worklist;
  HashMap<Vertex, Vertex> cameFromEdge;
  int currentTick;
  boolean breadth;
  boolean depth;
  boolean isDone;

  Vertex target;

  //constructor for testing
  Game() {

    Graph graph = new Graph(4, 4); //set maze size to 4 by 4 squares

    this.edges = graph.edgesInTree; 
    this.vertices = graph.vertices;
    this.worklist = new ArrayDeque<Vertex>(Arrays.asList(this.vertices.get(0).get(0)));
    this.cameFromEdge = new HashMap<Vertex, Vertex>();
    this.currentTick = 1;
    this.breadth = false;
    this.depth = false;
    this.isDone = false;

    this.target = this.vertices.get(this.vertices.size() - 1).get(this.vertices.get(0).size() - 1);

  }

  //constructor for playing
  Game(int xSize, int ySize) {

    Graph graph = new Graph(xSize, ySize); //set maze size to user input

    this.edges = graph.edgesInTree; 
    this.vertices = graph.vertices;
    this.worklist = new ArrayDeque<Vertex>(Arrays.asList(this.vertices.get(0).get(0)));
    this.cameFromEdge = new HashMap<Vertex, Vertex>();
    this.currentTick = 1;
    this.breadth = false;
    this.depth = false; 
    this.isDone = false;

    this.target = this.vertices.get(this.vertices.size() - 1).get(this.vertices.get(0).size() - 1);

  }

  public void search() {
    if (this.breadth || this.depth) {

      if (this.worklist.size() > 0 && !this.isDone) {
        Vertex next = this.worklist.removeFirst();

        //have we already seen this vertex?
        if (next.color.equals(Color.pink)) {
          //next is already discarded when .removeFirst() is called

        } //is this the vertex we're looking for?
        else if (next.equals(this.target)) {
          this.target.color = Color.pink;
          this.isDone = true;
        } 
        else {
          next.color = Color.pink;
          for (Edge e : next.outEdges) {
            Vertex nextVertex = next.vertexAtOtherEnd(e);
            //cameFromEdge doesn't have a copy 
            if (! this.cameFromEdge.containsKey(nextVertex)) {
              if (this.breadth) {
                this.worklist.add(nextVertex);
              }
              else {
                this.worklist.push(nextVertex);
              }
              this.cameFromEdge.put(nextVertex, next);
            }
          } 
        }
      }
    }
  }

  //EFFECT: traces the path back in red
  public void reconstruct() {
    //the vertex this.target came from
    Vertex targetValue = this.cameFromEdge.get(this.target); 

    if (this.vertices.get(0).get(0).color != Color.red) {
      this.target.color = Color.red;
      this.target = targetValue;    
    } 
  }

  //EFFECT: starts breadth search if the b key is pressed or depth if 
  //the d key is pressed
  public void onKeyEvent(String key) {
    if (key.equals("b")) {
      this.breadth = true;
    }
    else if (key.equals("d")) {
      this.depth = true;
    }
  }

  @Override
  //Searches the maze or reconstruct the patch one square every tick
  public void onTick() {
    this.search();
    if (this.isDone) {
      this.reconstruct();
    }
    this.currentTick = this.currentTick + 1;
  }

  //EFFECT: draw all of the vertices in this game
  public void drawMaze(WorldScene scene) {
    for (int i = 0; i < this.vertices.size(); i++) {
      for (int j = 0; j < this.vertices.get(0).size(); j++) {
        scene.placeImageXY(
            this.vertices.get(i).get(j).draw(),
            4 + 8 * j, 4 + 8 * i);  
      }
    } 
  }

  @Override
  //draws this game
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(800, 480);
    this.drawMaze(scene);
    return scene;
  }
}
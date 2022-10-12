import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.awt.Color;
import javalib.worldimages.*;

//to represent an edge in a maze
class Edge {
  Vertex from;
  Vertex to;
  int weight;

  Edge(int randWeight) {
    this.from = null;
    this.to = null;
    this.weight = randWeight;
  }

  Edge(int randWeight, Vertex from, Vertex to) {
    this.from = from;
    this.to = to;
    this.weight = randWeight;
  }

  //EFFECT: is the given vertex equal to this edge's from vertex?
  public boolean fromEqual(Vertex v) {
    return this.from.equals(v);
  }

  //EFFECT: is the given vertex equal to this edge's to vertex?
  public boolean toEqual(Vertex v) {
    return this.to.equals(v);
  }

  //EFFECT: sets this Edge's to to the given vVrtex
  void toHelper(Vertex v) {
    this.to = v;
  }

  //EFFECT: sets this Edges's from to the given Vertex and the
  //given Vertex's right to this Edge
  void fromRightHelper(Vertex v) {
    this.from = v;
    v.right = this;
  }

  //EFFECT: sets this Edges's from to the given Vertex and the
  //given Vertex's bottom to this Edge
  void fromBotHelper(Vertex v) {
    this.from = v;
    v.bottomHelper(this);
  }
}

//to represent a Vertex that is connected to an edge on its top, left,
//right, and bottom. Also has a boolean representing whether there is a 
//wall in that direction
class Vertex {
  ArrayList<Edge> outEdges;
  Edge top;
  Edge left;
  Edge right;
  Edge bottom;
  //does this square have a wall on this side?
  Boolean topWall;
  Boolean leftWall;
  Boolean rightWall;
  Boolean bottomWall;

  Color color;

  int x; //coordinates for debugging
  int y;

  Vertex(int x, int y) {
    this.outEdges = new ArrayList<Edge>();
    this.top = null;
    this.left = null;
    this.right = null;
    this.bottom = null;
    this.topWall = true;
    this.leftWall = true;
    this.rightWall = true;
    this.bottomWall = true;
    this.color = Color.lightGray;

    this.x = x;
    this.y = y;
  }

  Vertex(int x, int y, Edge top, Edge left, Edge right, Edge bottom, 
      boolean topWall, boolean leftWall, boolean rightWall, boolean bottomWall, Color color) {
    this.outEdges = new ArrayList<Edge>();
    this.top = top;
    this.left = left;
    this.right = right;
    this.bottom = bottom;
    this.topWall = topWall;
    this.leftWall = leftWall;
    this.rightWall = rightWall;
    this.bottomWall = bottomWall;
    this.color = color;

    this.x = x;
    this.y = y;
  }

  //returns the vertex this vertex is connected to via the given edge
  public Vertex vertexAtOtherEnd(Edge e) {
    if (e.to.equals(this)) {
      return e.from;
    }
    else {
      return e.to;
    }
  }

  //EFFECT: sets this Vertex's bottom Edge's to to the given Vertex
  void bottomToHelper(Vertex v) {
    this.bottom.toHelper(v);
  }

  //EFFECT: sets this Vertex's right Edge's to to the given Vertex
  void rightToHelper(Vertex v) {
    this.right.toHelper(v);
  }

  //EFFECT: sets this Vertex's bottom to the given Edge
  void bottomHelper(Edge e) {
    this.bottom = e;
  }

  //EFFECT: sets this Vertex's top to the given Vertex's bottom
  void topBotHelper(Vertex v) {
    this.top = v.bottom;
  }

  //EFFECT: sets this Vertex's left to the given Vertex's right
  void leftRightHelper(Vertex v) {
    this.left = v.right;
  }

  //represent this Vertex as a rectangle with walls
  public WorldImage draw() {

    WorldImage output = new RectangleImage(8, 8, OutlineMode.SOLID, this.color);

    WorldImage wallV = new LineImage(new Posn(0, 8), Color.gray);
    WorldImage wallH = new LineImage(new Posn(8, 0), Color.gray);

    if (this.topWall) {
      output = new OverlayOffsetImage(wallH, 0, 4, output);
    } 
    if (this.leftWall) {
      output = new OverlayOffsetImage(wallV, 4, 0, output);
    }
    if (this.rightWall) {
      output = new OverlayOffsetImage(wallV, -4, 0, output);
    }
    if (this.bottomWall) {
      output = new OverlayOffsetImage(wallH, 0, -4, output);
    }

    return output;
  }

  //EFFECT: Sets one of this vertex's wall fields to false if it is connected to the given
  //vertex in that direction
  void setWallHelper(Vertex v) {
    if (this.top != null && (this.top.fromEqual(v))) {
      this.topWall = false;
    }
    else if (this.left != null && (this.left.fromEqual(v) )) {
      this.leftWall = false;
    }
    else if (this.right != null && (this.right.toEqual(v))) {
      this.rightWall = false;
    }
    else if (this.bottom != null && (this.bottom.toEqual(v))) {
      this.bottomWall = false;
    }
  }
}

//to represent a Graph of Vertices and Edges 
class Graph {
  ArrayList<ArrayList<Vertex>> vertices;
  ArrayList<Edge> edges;

  //size of the graph
  int xSize;
  int ySize;
  Random rand;

  HashMap<Vertex, Vertex> representatives;
  //list of edges and vertices in the minimum spanning tree
  ArrayList<Edge> edgesInTree;
  ArrayList<ArrayList<Vertex>> verticesInTree;


  //Constructor for testing
  Graph() {
    this.vertices = new ArrayList<ArrayList<Vertex>>();
    this.edges = new ArrayList<Edge>();
    this.xSize = 4;
    this.ySize = 4;
    this.rand = new Random(10);

    this.representatives = new HashMap<Vertex, Vertex>();
    this.verticesInTree = new ArrayList<ArrayList<Vertex>>();
    this.edgesInTree = new ArrayList<Edge>();
  }


  //Constructor for testing
  Graph(int xSize, int ySize) {
    this.vertices = new ArrayList<ArrayList<Vertex>>();
    this.edges = new ArrayList<Edge>();
    this.xSize = xSize;
    this.ySize = ySize;
    this.rand = new Random(10);

    this.representatives = new HashMap<Vertex, Vertex>();
    this.verticesInTree = new ArrayList<ArrayList<Vertex>>();
    this.edgesInTree = new ArrayList<Edge>();

    this.generateVertices();
    this.connectVertices();
    this.edges.sort(new EdgeComparator()); 
    this.initializeHashMap();
    this.kruskal();
    this.setWalls();
    this.setOutEdges();
  }
  
  //Constructor for playing
  Graph(int xSize, int ySize, Boolean bool) {
    this.vertices = new ArrayList<ArrayList<Vertex>>();
    this.edges = new ArrayList<Edge>();
    this.xSize = xSize;
    this.ySize = ySize;
    this.rand = new Random();

    this.representatives = new HashMap<Vertex, Vertex>();
    this.verticesInTree = new ArrayList<ArrayList<Vertex>>();
    this.edgesInTree = new ArrayList<Edge>();

    this.generateVertices();
    this.connectVertices();
    this.edges.sort(new EdgeComparator()); 
    this.initializeHashMap();
    this.kruskal();
    this.setWalls();
    this.setOutEdges();
  }

  //EFFECT: creates vertices for this Graph
  void generateVertices() {
    for (int i = 0; i < this.ySize; i++) {
      ArrayList<Vertex> row = new ArrayList<Vertex>();
      for (int j = 0; j < this.xSize; j++) {
        Vertex newVertex = new Vertex(j, i);
        if (i == 0 && j == 0) {
          newVertex.color = Color.green; //first square is green
        }
        else if (i == this.ySize - 1 && j == this.xSize - 1) {
          newVertex.color = Color.magenta; //last square is magenta
        }
        row.add(newVertex);
      }
      this.vertices.add(row);
    }
  }

  //EFFECT: add all the edges in the minimum spanning tree to their respective vertex
  void setOutEdges() {
    for (Edge e: this.edgesInTree) {
      e.to.outEdges.add(e);
      e.from.outEdges.add(e);
    }
  }

  //EFFECT: creates edges and connects them to this graph's vertices
  void connectVertices() {
    for (int i = 0; i < this.ySize; i++) {

      for (int j = 0; j < this.xSize; j++) {

        Vertex currVer = this.vertices.get(i).get(j);
        //if this vertex isn't on the last row
        if (i != this.ySize - 1) {
          //create this vertex's bottom edge
          Edge bottom = new Edge(rand.nextInt(30));
          //set bottom edge's from to this vertex  
          bottom.fromBotHelper(currVer);
          this.edges.add(bottom);
        }

        //if this vertex isn't in the last column
        if (j != this.xSize - 1) {
          //create this vertex's right edge
          Edge right = new Edge(rand.nextInt(30));
          //set right edge's from to this vertex        
          right.fromRightHelper(currVer); 
          this.edges.add(right);
        }

        //if this vertex isn't in the first row 
        if (i != 0) {
          //set the vertex above this one's bottom edge's to this vertex        
          this.vertices.get(i - 1).get(j).bottomToHelper(currVer);
          currVer.topBotHelper(this.vertices.get(i - 1).get(j));
        }

        //if this vertex isn't in the first column
        if (j != 0) {
          //set the vertex to its left's right to this vertex
          this.vertices.get(i).get(j - 1).rightToHelper(currVer);     
          currVer.leftRightHelper(this.vertices.get(i).get(j - 1));
        }
      }
    }
  }


  //EFFECT: initialize the hash map by setting every's vertex's representative to itself
  void initializeHashMap() {
    for (ArrayList<Vertex> row : this.vertices) {
      for (Vertex vertex : row) {
        this.representatives.put(vertex, vertex);
      }
    }
  }

  //determines the representative of the given vertex
  Vertex find(Vertex v) {

    Vertex vRepr = this.representatives.get(v); //given vertex's representative

    //if vertex maps to itself return itself
    if (vRepr.equals(v)) {
      return v;
    }
    else {
      return find(vRepr); //recursively find vertex's representative
    }
  }

  //EFFECT: sets v1's representative to the other
  void union(Vertex v1, Vertex v2) {
    this.representatives.put(v1, find(v2));
  }

  //EFFECT: adds a minimum spanning tree's edges to this graph's edgesInTree field
  void kruskal() {
    for (int i = 0; i < this.edges.size(); i++) {
      Vertex y = this.edges.get(i).from;
      Vertex x = this.edges.get(i).to;
      //if both vertex's representatives are the same, discard this edge    
      if (find(x).equals(find(y))) {
        continue;
      }
      else {
        this.edgesInTree.add(edges.get(i));
        union(find(x), find(y));
      }  
    }
  }

  //EFFECT: goes through edges in trees and sets vertice's leftWall, topWall, rightWall,
  //bottomWall to be false if it is connected to another vertex in that direction
  void setWalls() {
    for (Edge e : this.edgesInTree) {
      e.to.setWallHelper(e.from);
      e.from.setWallHelper(e.to);
    }
  }
}


//to represent a comparator that compares Edges
class EdgeComparator implements Comparator<Edge> {
  @Override
  //returns the difference of the second given edge subtracted from the first
  //given edge
  public int compare(Edge e1, Edge e2) {
    return e1.weight - e2.weight;
  }
}












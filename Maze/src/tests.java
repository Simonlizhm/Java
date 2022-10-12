import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javalib.impworld.WorldScene;
import javalib.worldimages.LineImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

class ExamplesGame {
  Graph graph1;
  Game game1;
  Edge edge1;

  WorldScene game1Scene;
  ArrayList<Edge> allEdges;
  ArrayList<ArrayList<Vertex>> game1Vertices;

  Vertex v1;
  Vertex v2;

  void initConditions() {
    this.graph1 = new Graph();
    this.game1 = new Game();
    this.edge1 = new Edge(10);
    this.game1Scene = new WorldScene(750, 750);

    this.v1 = new Vertex(0, 0);
    this.v2 = new Vertex(1, 0);
  }

  //========METHODS IN EDGE==========
  void testFromEqual(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();

    Edge e1 = this.graph1.edges.get(0);

    t.checkExpect(e1.fromEqual(this.graph1.vertices.get(0).get(0)), true);
    t.checkExpect(e1.fromEqual(this.graph1.vertices.get(0).get(3)), false);
  }

  void testToEqual(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();

    Edge e1 = this.graph1.edges.get(0);

    t.checkExpect(e1.toEqual(this.graph1.vertices.get(1).get(0)), true);
    t.checkExpect(e1.toEqual(this.graph1.vertices.get(0).get(3)), false);
  }


  void testToHelper(Tester t) {
    initConditions();
    t.checkExpect(this.edge1.to, null);
    this.edge1.toHelper(v1);
    t.checkExpect(this.edge1.to, v1);
  }

  void testFromRightHelper(Tester t) {
    initConditions();
    t.checkExpect(this.edge1.from, null);
    t.checkExpect(this.v1.right, null);
    this.edge1.fromRightHelper(v1);
    t.checkExpect(this.edge1.from, this.v1);
    t.checkExpect(this.v1.right, this.edge1);
  }

  void testFromBotHelper(Tester t) {
    initConditions();
    t.checkExpect(this.edge1.from, null);
    t.checkExpect(this.v1.bottom, null);
    this.edge1.fromBotHelper(v1);
    t.checkExpect(this.edge1.from, this.v1);
    t.checkExpect(this.v1.bottom, this.edge1);
  }

  //=========METHODS IN VERTEX========
  void testVertexAtOtherEnd(Tester t) {
    initConditions();
    t.checkExpect(this.game1.vertices.get(1).get(2)
        .vertexAtOtherEnd(this.game1.vertices.get(1).get(2).right), 
        this.game1.vertices.get(1).get(3));

    t.checkExpect(this.game1.vertices.get(1).get(2)
        .vertexAtOtherEnd(this.game1.vertices.get(1).get(2).top), 
        this.game1.vertices.get(0).get(2));

    t.checkExpect(this.game1.vertices.get(1).get(2)
        .vertexAtOtherEnd(this.game1.vertices.get(1).get(2).left), 
        this.game1.vertices.get(1).get(1));

    t.checkExpect(this.game1.vertices.get(1).get(2)
        .vertexAtOtherEnd(this.game1.vertices.get(1).get(2).bottom), 
        this.game1.vertices.get(2).get(2));
  }

  void testBottomToHelper(Tester t) {
    initConditions();  //this.bottom.toHelper(v);
    graph1.generateVertices();
    Vertex v11 = this.graph1.vertices.get(1).get(1);
    Vertex v01 = this.graph1.vertices.get(0).get(1);

    v01.bottom = new Edge(10);
    t.checkExpect(v01.bottom.to, null);

    v01.bottomToHelper(v11);
    t.checkExpect(v01.bottom.to, v11);
  }

  void testRightToHelper(Tester t) {
    initConditions();

    graph1.generateVertices();
    Vertex v11 = this.graph1.vertices.get(1).get(1);
    Vertex v10 = this.graph1.vertices.get(1).get(0);

    v10.right = new Edge(10);
    t.checkExpect(v10.right.to, null);

    v10.rightToHelper(v11);
    t.checkExpect(v10.right.to, v11);
  }

  void testBottomHelper(Tester t) {
    initConditions();
    t.checkExpect(this.v1.bottom, null);
    this.v1.bottomHelper(this.edge1);
    t.checkExpect(this.v1.bottom, this.edge1);  
  }

  void testTopBotHelper(Tester t) {
    initConditions();
    this.v2.bottom = new Edge(10);

    t.checkExpect(this.v1.top, null);
    this.v1.topBotHelper(v2);
    t.checkExpect(this.v1.top, this.v2.bottom);
  }

  void testLeftRightHelper(Tester t) {
    initConditions();
    this.v2.right = new Edge(10);

    t.checkExpect(this.v1.top, null);
    this.v1.leftRightHelper(v2);
    t.checkExpect(this.v1.left, this.v2.right);
  }

  void testDraw(Tester t) {
    initConditions();
    WorldImage wallV = new LineImage(new Posn(0, 8), Color.gray);
    WorldImage wallH = new LineImage(new Posn(8, 0), Color.gray);
    //Vertex with walls on all sides
    t.checkExpect(this.v1.draw(), 
        new OverlayOffsetImage(wallH, 0, -4, 
            new OverlayOffsetImage(wallV, -4, 0, 
                new OverlayOffsetImage(wallV, 4, 0, 
                    new OverlayOffsetImage(wallH, 0, 4, 
                        new RectangleImage(8, 8, OutlineMode.SOLID, Color.lightGray))))));
    //Vertex with no walls
    this.v1.leftWall = false;
    this.v1.topWall = false;
    this.v1.rightWall = false;
    this.v1.bottomWall = false;
    t.checkExpect(this.v1.draw(),  new RectangleImage(8, 8, OutlineMode.SOLID, 
        Color.lightGray));
  }

  void testSetWallHelper(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();
    this.graph1.initializeHashMap();
    this.graph1.kruskal();

    //v00 and v01 connected horizontally 
    //v00 v10 connected vertically
    Vertex v00 = this.graph1.vertices.get(0).get(0); //row0 col0
    Vertex v01 = this.graph1.vertices.get(0).get(1); //row1 col0
    Vertex v10 = this.graph1.vertices.get(1).get(0); //row0 col1

    t.checkExpect(v00.bottomWall, true);
    t.checkExpect(v00.rightWall, true);
    t.checkExpect(v10.leftWall, true);
    t.checkExpect(v01.topWall, true);

    v00.setWallHelper(v01);
    t.checkExpect(v00.rightWall, false);
    v01.setWallHelper(v00);
    t.checkExpect(v01.leftWall, false);
    v10.setWallHelper(v00);
    t.checkExpect(v10.topWall, false);
    v00.setWallHelper(v10);
    t.checkExpect(v00.bottomWall, false);
  }

  //=========METHODS IN GRAPH================
  void testGenerateVertices(Tester t) {
    initConditions();
    t.checkExpect(this.graph1.vertices.size(), 0);
    this.graph1.generateVertices();
    t.checkExpect(this.graph1.vertices.size(), 4);
    t.checkExpect(this.graph1.vertices.get(0).size(), 4);

    t.checkExpect(this.graph1.vertices.get(0).get(0).color, Color.green);
    t.checkExpect(graph1.vertices.get(0).get(0).top, null);
    t.checkExpect(graph1.vertices.get(0).get(0).left, null);
    t.checkExpect(graph1.vertices.get(0).get(0).right, null);
    t.checkExpect(graph1.vertices.get(0).get(0).bottom, null);

    t.checkExpect(this.graph1.vertices.get(2).get(2), new Vertex(2,2));
    t.checkExpect(graph1.vertices.get(2).get(2).top, null);
    t.checkExpect(graph1.vertices.get(2).get(2).left, null);
    t.checkExpect(graph1.vertices.get(2).get(2).right, null);
    t.checkExpect(graph1.vertices.get(2).get(2).bottom, null);

    t.checkExpect(graph1.vertices.get(3).get(3).color, Color.magenta);
  }

  void testConnectVertices(Tester t) {
    initConditions();
    t.checkExpect(graph1.edgesInTree.size(), 0);
    this.graph1.generateVertices();
    this.graph1.connectVertices();

    //top left vertex
    t.checkExpect(graph1.vertices.get(0).get(0).top, null);
    t.checkExpect(graph1.vertices.get(0).get(0).left, null);
    t.checkExpect(graph1.vertices.get(0).get(0).right.to, graph1.vertices.get(0).get(1));
    t.checkExpect(graph1.vertices.get(0).get(0).bottom.to, graph1.vertices.get(1).get(0));

    //vertex at 1,1
    t.checkExpect(graph1.vertices.get(1).get(1).top.from, graph1.vertices.get(0).get(1));
    t.checkExpect(graph1.vertices.get(1).get(1).left.from, graph1.vertices.get(1).get(0));
    t.checkExpect(graph1.vertices.get(1).get(1).right.to, graph1.vertices.get(1).get(2));
    t.checkExpect(graph1.vertices.get(1).get(1).bottom.to, graph1.vertices.get(2).get(1));

    //bottom right vertex
    t.checkExpect(graph1.vertices.get(3).get(3).top.from, graph1.vertices.get(2).get(3));
    t.checkExpect(graph1.vertices.get(3).get(3).left.from, graph1.vertices.get(3).get(2));
    t.checkExpect(graph1.vertices.get(3).get(3).right, null);
    t.checkExpect(graph1.vertices.get(3).get(3).bottom, null);

    //vertex at row1 col3
    t.checkExpect(graph1.vertices.get(1).get(3).top.from, graph1.vertices.get(0).get(3));
    t.checkExpect(graph1.vertices.get(1).get(3).left.from, graph1.vertices.get(1).get(2));
    t.checkExpect(graph1.vertices.get(1).get(3).right, null);
    t.checkExpect(graph1.vertices.get(1).get(3).bottom.to, graph1.vertices.get(2).get(3));

    //vertex at row3 col1
    t.checkExpect(graph1.vertices.get(3).get(1).left.to, graph1.vertices.get(3).get(1));
    t.checkExpect(graph1.vertices.get(3).get(1).right.to, graph1.vertices.get(3).get(2));
    t.checkExpect(graph1.vertices.get(3).get(1).top.from, graph1.vertices.get(2).get(1));
    t.checkExpect(graph1.vertices.get(3).get(1).bottom, null);
  }

  void testInitializeHashMap(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();
    t.checkExpect(graph1.representatives.size(), 0);

    this.graph1.initializeHashMap();
    t.checkExpect(graph1.representatives.size(), 16);

    Vertex vertHashCode = graph1.vertices.get(2).get(2);
    t.checkExpect(graph1.representatives.get(vertHashCode), vertHashCode);

    Vertex vertHashCode2 = graph1.vertices.get(3).get(2);
    t.checkExpect(graph1.representatives.get(vertHashCode2), vertHashCode2);
  }

  void testEdges(Tester t) {
    initConditions();
    graph1.generateVertices();
    graph1.connectVertices();
    t.checkExpect(graph1.edges.get(0).weight, 3);
    t.checkExpect(graph1.edges.get(5).weight, 16);
    t.checkExpect(graph1.edges.get(10).weight, 13);
    t.checkExpect(graph1.edges.get(23).weight, 8);

    graph1.edges.sort(new EdgeComparator());
    //make sure graph.edges is sorted in ascending order by weights
    t.checkExpect(graph1.edges.get(0).weight, 0);
    t.checkExpect(graph1.edges.get(5).weight, 4);
    t.checkExpect(graph1.edges.get(10).weight, 13);
    t.checkExpect(graph1.edges.get(23).weight, 29);
  }

  void testFind(Tester t) {
    initConditions();
    graph1.generateVertices();
    graph1.connectVertices();
    this.graph1.initializeHashMap();

    Vertex v23 = this.graph1.vertices.get(2).get(3);
    Vertex v00 = this.graph1.vertices.get(0).get(0);
    //v23's representative is itself
    t.checkExpect(this.graph1.find(v23), v23);
    //set v23's representative to v00
    this.graph1.representatives.put(v23, v00);
    t.checkExpect(this.graph1.find(v23), v00);
  }

  void testUnion(Tester t) {
    initConditions();
    graph1.generateVertices();
    graph1.connectVertices();
    this.graph1.initializeHashMap();

    Vertex v23 = this.graph1.vertices.get(2).get(3);
    Vertex v11 = this.graph1.vertices.get(1).get(1);
    Vertex v00 = this.graph1.vertices.get(0).get(0);

    t.checkExpect(this.graph1.representatives.get(v23), v23);
    this.graph1.union(v23, v00); //v23's repr is v00
    t.checkExpect(this.graph1.representatives.get(v23), v00);
    this.graph1.union(v11, v23); //v11's repr is v00 because v23's repr is v00
    t.checkExpect(this.graph1.representatives.get(v11), v00);
  }

  void testKruskal(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();
    this.graph1.edges.sort(new EdgeComparator()); 
    this.graph1.initializeHashMap();

    t.checkExpect(this.graph1.edgesInTree.size(), 0);
    this.graph1.kruskal();
    t.checkExpect(this.graph1.edgesInTree.size(), 15);

    //check number of edges is number of vertices - 1
    Graph graph3 = new Graph(3,3);
    t.checkExpect(graph3.edgesInTree.size(), 8);

    Graph graph99 = new Graph(99,99);
    t.checkExpect(graph99.edgesInTree.size(), 99 * 99 - 1);
  }

  void testSetWalls(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();
    this.graph1.edges.sort(new EdgeComparator()); 
    this.graph1.initializeHashMap();
    this.graph1.kruskal();

    Vertex v12 = this.graph1.vertices.get(1).get(2); //row1 col2
    Vertex v00 = this.graph1.vertices.get(0).get(0); //row0 col0
    Vertex v21 = this.graph1.vertices.get(2).get(1); //row2 col1

    t.checkExpect(v12.topWall, true);
    t.checkExpect(v12.leftWall, true);
    t.checkExpect(v12.rightWall, true);
    t.checkExpect(v12.bottomWall, true);

    t.checkExpect(v00.topWall, true);
    t.checkExpect(v00.leftWall, true);
    t.checkExpect(v00.rightWall, true);
    t.checkExpect(v00.bottomWall, true);

    t.checkExpect(v21.topWall, true);
    t.checkExpect(v21.leftWall, true);
    t.checkExpect(v21.rightWall, true);
    t.checkExpect(v21.bottomWall, true);

    this.graph1.setWalls();

    t.checkExpect(v12.topWall, true);
    t.checkExpect(v12.leftWall, false);
    t.checkExpect(v12.rightWall, true);
    t.checkExpect(v12.bottomWall, true);

    t.checkExpect(v00.topWall, true);
    t.checkExpect(v00.leftWall, true);
    t.checkExpect(v00.rightWall, false);
    t.checkExpect(v00.bottomWall, false);

    t.checkExpect(v21.topWall, false);
    t.checkExpect(v21.leftWall, false);
    t.checkExpect(v21.rightWall, false);
    t.checkExpect(v21.bottomWall, true);
  }

  void testSetOutEdges(Tester t) {
    initConditions();
    this.graph1.generateVertices();
    this.graph1.connectVertices();
    this.graph1.edges.sort(new EdgeComparator()); 
    this.graph1.initializeHashMap();
    this.graph1.kruskal();
    this.graph1.setWalls();

    t.checkExpect(graph1.vertices.get(0).get(0).outEdges, new ArrayList<Edge>());
    t.checkExpect(graph1.vertices.get(2).get(1).outEdges, new ArrayList<Edge>());

    this.graph1.setOutEdges();

    t.checkExpect(graph1.vertices.get(0).get(0).outEdges, 
        new ArrayList<Edge>(Arrays.asList(graph1.vertices.get(0).get(0).right,
            graph1.vertices.get(0).get(0).bottom))); 

    t.checkExpect(graph1.vertices.get(2).get(3).outEdges, 
        new ArrayList<Edge>(Arrays.asList(graph1.vertices.get(2).get(3).top))); 

    t.checkExpect(graph1.vertices.get(3).get(2).outEdges.size(), 3);
    t.checkExpect(graph1.vertices.get(3).get(2).outEdges
        .contains(graph1.vertices.get(3).get(2).right), true);
    t.checkExpect(graph1.vertices.get(3).get(2).outEdges
        .contains(graph1.vertices.get(3).get(2).top), true);
    t.checkExpect(graph1.vertices.get(3).get(2).outEdges
        .contains(graph1.vertices.get(3).get(2).left), true);

    t.checkExpect(graph1.vertices.get(3).get(3).outEdges, 
        new ArrayList<Edge>(Arrays.asList(graph1.vertices.get(3).get(3).left))); 
  }

  //==========METHODS IN EDGE COMPARATOR============
  void compare(Tester t) {
    initConditions();
    t.checkExpect(new EdgeComparator().compare(new Edge(10), new Edge(6)), 4);
    t.checkExpect(new EdgeComparator().compare(new Edge(10), new Edge(10)), 0);
    t.checkExpect(new EdgeComparator().compare(new Edge(3), new Edge(29)), -26);
  }

  //==========METHODS IN MAZE============
  void testMakeScene(Tester t) {
    initConditions();

    WorldScene checkScene = new WorldScene(800,480);
    checkScene.placeImageXY(this.game1.vertices.get(0).get(0).draw(), 4, 4);
    checkScene.placeImageXY(this.game1.vertices.get(0).get(1).draw(), 12, 4);
    checkScene.placeImageXY(this.game1.vertices.get(0).get(2).draw(), 20, 4);
    checkScene.placeImageXY(this.game1.vertices.get(0).get(3).draw(), 28, 4);

    checkScene.placeImageXY(this.game1.vertices.get(1).get(0).draw(), 4, 12);
    checkScene.placeImageXY(this.game1.vertices.get(1).get(1).draw(), 12, 12);
    checkScene.placeImageXY(this.game1.vertices.get(1).get(2).draw(), 20, 12);
    checkScene.placeImageXY(this.game1.vertices.get(1).get(3).draw(), 28, 12);

    checkScene.placeImageXY(this.game1.vertices.get(2).get(0).draw(), 4, 20);
    checkScene.placeImageXY(this.game1.vertices.get(2).get(1).draw(), 12, 20);
    checkScene.placeImageXY(this.game1.vertices.get(2).get(2).draw(), 20, 20);
    checkScene.placeImageXY(this.game1.vertices.get(2).get(3).draw(), 28, 20);

    checkScene.placeImageXY(this.game1.vertices.get(3).get(0).draw(), 4, 28);
    checkScene.placeImageXY(this.game1.vertices.get(3).get(1).draw(), 12, 28);
    checkScene.placeImageXY(this.game1.vertices.get(3).get(2).draw(), 20, 28);
    checkScene.placeImageXY(this.game1.vertices.get(3).get(3).draw(), 28, 28);

    t.checkExpect(game1.makeScene(), checkScene);
  }

  void testSearch(Tester t) {
    initConditions();
    this.game1.depth = true;
    t.checkExpect(game1.cameFromEdge.size(), 0);
    t.checkExpect(game1.vertices.get(0).get(0).color, Color.green);
    t.checkExpect(game1.worklist.size(), 1);
    this.game1.search();
    t.checkExpect(game1.cameFromEdge.size(), 2);
    t.checkExpect(game1.vertices.get(0).get(0).color, Color.pink);
    t.checkExpect(game1.worklist.size(), 2);
    t.checkExpect(game1.worklist.contains(game1.vertices.get(0).get(0)), false);
  }

  void testReconstruct(Tester t) {
    initConditions();
    this.game1.depth = true;
    for (int i = 0;i < 40; i++) {
      this.game1.search();
    }
    this.game1.reconstruct();
    t.checkExpect(game1.vertices.get(3).get(3).color, Color.red);
    t.checkExpect(game1.target, game1.vertices.get(3).get(2));
  }

  void testOnTick(Tester t) {
    initConditions();
    this.game1.depth = true;
    this.game1.onTick();
    t.checkExpect(this.game1.currentTick, 2);
    //from testSearch
    t.checkExpect(game1.cameFromEdge.size(), 2);
    t.checkExpect(game1.vertices.get(0).get(0).color, Color.pink);
    t.checkExpect(game1.worklist.size(), 2);
    t.checkExpect(game1.worklist.contains(game1.vertices.get(0).get(0)), false);

    //solve the maze
    for (int i = 0;i < 10; i++) {
      game1.onTick();
    }

    t.checkExpect(game1.vertices.get(3).get(3).color, Color.red);
    t.checkExpect(game1.target, game1.vertices.get(3).get(2));
  }

  void testOnKeyEvent(Tester t) {
    initConditions();
    t.checkExpect(this.game1.breadth, false);
    t.checkExpect(this.game1.depth, false);
    game1.onKeyEvent("f");
    t.checkExpect(this.game1.breadth, false);
    t.checkExpect(this.game1.depth, false);
    game1.onKeyEvent("d");
    t.checkExpect(this.game1.depth, true);
    game1.onKeyEvent("b");
    t.checkExpect(this.game1.breadth, true);
  }

  void testGame(Tester t) {
    Game g = new Game(100, 60); //seeded with Random(10)
    g.bigBang(800, 480, 0.001);
  }
}


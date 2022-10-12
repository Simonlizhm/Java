import java.util.ArrayList;
import java.util.Arrays;

import tester.*;
import javalib.funworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class ExamplesFloodItWorld {

  Cell cell11;
  Cell cell21;
  Cell cell12;
  Cell cell31;
  Cell cell22;
  Cell cell13;
  Cell cell41;
  Cell cell32;
  Cell cell23;
  Cell cell14;
  Cell cell42;
  Cell cell33;
  Cell cell24;
  Cell cell43;
  Cell cell34;
  Cell cell44;

  Cell cell11game2;
  Cell cell21game2;
  Cell cell12game2;
  Cell cell31game2;
  Cell cell22game2;
  Cell cell13game2;
  Cell cell32game2;
  Cell cell23game2;
  Cell cell33game2;

  Cell cell11connected;
  Cell cell21connected;
  Cell cell12connected;
  Cell cell31connected;
  Cell cell22connected;
  Cell cell13connected;
  Cell cell41connected;
  Cell cell32connected;
  Cell cell23connected;
  Cell cell14connected;
  Cell cell42connected;
  Cell cell33connected;
  Cell cell24connected;
  Cell cell43connected;
  Cell cell34connected;
  Cell cell44connected;

  ArrayList<ArrayList<Cell>> game1Cells;
  ArrayList<ArrayList<Cell>> game1CellsConnected;
  ArrayList<ArrayList<Cell>> game2Cells;

  FloodItWorld game1;
  FloodItWorld game2;

  WorldScene game1InitScene;

  void initConditions() {
    this.game1 = new FloodItWorld(4);
    this.game2 = new FloodItWorld(3);

    this.cell11game2 = new Cell(60,80, Color.pink, false, null, null, null, null); 
    this.cell21game2 = new Cell(100,80, Color.red, false, null, null, null, null);
    this.cell31game2 = new Cell(140,80, Color.pink, false, null, null, null, null);

    this.cell12game2 = new Cell(60,120, Color.red, false, null, null, null, null);
    this.cell22game2 = new Cell(100,120, Color.orange, false, null, null, null, null); 
    this.cell32game2 = new Cell(140,120, Color.orange, false, null, null, null, null);

    this.cell13game2 = new Cell(60,160, Color.green, false, null, null, null, null); 
    this.cell23game2 = new Cell(100,160, Color.pink, false, null, null, null, null);
    this.cell33game2 = new Cell(140,160, Color.orange, false, null, null, null, null);


    this.cell11 = new Cell(60, 80, Color.pink, false, null, null, null, null); 
    this.cell21 = new Cell(100, 80, Color.red, false, null, null, null, null);
    this.cell31 = new Cell(140,80, Color.pink, false, null, null, null, null);
    this.cell41 = new Cell(180,80, Color.red, false, null, null, null, null);

    this.cell12 = new Cell(60, 120, Color.orange, false, null, null, null, null);
    this.cell22 = new Cell(100, 120, Color.orange, false, null, null, null, null); 
    this.cell32 = new Cell(140, 120, Color.green, false, null, null, null, null);
    this.cell42 = new Cell(180, 120, Color.pink, false, null, null, null, null);

    this.cell13 = new Cell(60, 160, Color.orange, false, null, null, null, null); 
    this.cell23 = new Cell(100, 160, Color.blue, false, null, null, null, null);
    this.cell33 = new Cell(140, 160, Color.pink, false, null, null, null, null);
    this.cell43 = new Cell(180, 160, Color.blue, false, null, null, null, null);

    this.cell14 = new Cell(60, 200, Color.orange, false, null, null, null, null);
    this.cell24 = new Cell(100, 200, Color.pink, false, null, null, null, null);
    this.cell34 = new Cell(140, 200, Color.red, false, null, null, null, null);
    this.cell44 = new Cell(180, 200, Color.red, false, null, null, null, null);

    this.game1Cells = new ArrayList<ArrayList<Cell>>(
        new ArrayList<ArrayList<Cell>>(Arrays.asList(
            new ArrayList<Cell>(Arrays.asList(
                this.cell11, this.cell21, this.cell31, this.cell41)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell12, this.cell22, this.cell32, this.cell42)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell13, this.cell23, this.cell33, this.cell43)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell14, this.cell24, this.cell34, this.cell44)))));

    this.game2Cells = new ArrayList<ArrayList<Cell>>(
        new ArrayList<ArrayList<Cell>>(Arrays.asList(
            new ArrayList<Cell>(Arrays.asList(
                this.cell11game2, this.cell21game2, this.cell31game2)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell12game2, this.cell22game2, this.cell32game2)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell13game2, this.cell23game2, this.cell33game2)))));

    this.game1InitScene = new WorldScene(750, 750)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.pink), 60, 80)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.red), 100, 80)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.pink), 140, 80)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.red), 180, 80)

        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.orange), 60, 120)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.orange), 100, 120)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.green), 140, 120)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.pink), 180, 120)

        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.orange), 60, 160)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.blue), 100, 160)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.pink), 140, 160)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.blue), 180, 160)

        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.orange), 60, 200)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.pink), 100, 200)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.red), 140, 200)
        .placeImageXY(new RectangleImage(40, 40, OutlineMode.SOLID, Color.red), 180, 200);

    this.cell11connected = new Cell(60,80, Color.pink, true, 
        null, null, cell21connected, cell12connected);
    this.cell21connected = new Cell(100,80, Color.red, false, 
        cell11connected, null, cell31connected, cell22connected);
    this.cell31connected = new Cell(140,80, Color.pink, false, 
        cell21connected, null, cell41connected, cell32connected); 
    this.cell41connected = new Cell(180,80, Color.red, false, 
        cell31connected, null, null, cell42connected);

    this.cell12connected = new Cell(60,120, Color.orange, false, 
        null, cell11connected, cell22connected, cell13connected);
    this.cell22connected = new Cell(100,120, Color.orange, false, 
        cell12connected, cell21connected, cell32connected, cell23connected);
    this.cell32connected = new Cell(140,120, Color.green, false, 
        cell22connected, cell31connected, cell42connected, cell33connected);
    this.cell42connected = new Cell(180,120, Color.pink, false, 
        cell32connected, cell41connected, null, cell43connected);

    this.cell13connected = new Cell(60,160, Color.orange, false, 
        null, cell12connected, cell23connected, cell14connected); 
    this.cell23connected = new Cell(100,160, Color.blue, false, 
        cell13connected, cell22connected, cell33connected, cell24connected);
    this.cell33connected = new Cell(140,160, Color.pink, false, 
        cell23connected, cell32connected, cell43connected, cell34connected);
    this.cell43connected = new Cell(180,160, Color.blue, false, 
        cell33connected, cell42connected, null, cell44connected);

    this.cell14connected = new Cell(60,200, Color.orange, false, 
        null, cell13connected, cell24connected, null);
    this.cell24connected = new Cell(100,200, Color.pink, false, 
        cell14connected, cell23connected, cell34connected, null);
    this.cell34connected = new Cell(140,200, Color.red, false, 
        cell24connected, cell33connected, cell44connected, null);
    this.cell44connected = new Cell(180,200, Color.red, false, 
        cell34connected, cell43connected, null, null);


    this.game1CellsConnected = new ArrayList<ArrayList<Cell>>(
        new ArrayList<ArrayList<Cell>>(Arrays.asList(
            new ArrayList<Cell>(Arrays.asList(
                this.cell11connected, this.cell21connected, this.cell31connected, 
                this.cell41connected)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell12connected, this.cell22connected, this.cell32connected, 
                this.cell42connected)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell13connected, this.cell23connected, this.cell33connected, 
                this.cell43connected)),
            new ArrayList<Cell>(Arrays.asList(
                this.cell14connected, this.cell24connected, this.cell34connected, 
                this.cell44connected)))));
  }

  //=========================Methods in Cell=========================

  void testDraw(Tester t) {
    initConditions();
    t.checkExpect(this.cell11.draw(), new RectangleImage(40, 40, OutlineMode.SOLID, Color.pink));
    t.checkExpect(this.cell21.draw(), new RectangleImage(40, 40, OutlineMode.SOLID, Color.red));
  }

  void testSameColor(Tester t) {
    initConditions();
    t.checkExpect(this.cell11.sameColor(this.cell42), true);
    t.checkExpect(this.cell11.sameColor(this.cell44), false);
  }

  void testLeftHelper(Tester t) {
    initConditions();
    t.checkExpect(this.cell22.left, null);
    this.cell22.leftHelper(this.cell12);
    t.checkExpect(this.cell22.left, this.cell12);
  }

  void testTopHelper(Tester t) {
    initConditions();
    t.checkExpect(this.cell22.top, null);
    this.cell22.topHelper(this.cell21);
    t.checkExpect(this.cell22.top, this.cell21);
  }

  void testRightHelper(Tester t) {
    initConditions();
    t.checkExpect(this.cell22.right, null);
    this.cell22.rightHelper(this.cell12);
    t.checkExpect(this.cell22.right, this.cell12);
  }

  void testBottomHelper(Tester t) {
    initConditions();
    t.checkExpect(this.cell22.bottom, null);
    this.cell22.bottomHelper(this.cell23);
    t.checkExpect(this.cell22.bottom, this.cell23);
  }

  void testFloodHelper(Tester t) {
    initConditions();

    //to change a cell's flooded
    t.checkExpect(this.cell11connected.floodHelper(new Negate(), new Identity()), 
        new ArrayList<Cell>());
    this.cell11connected.color = Color.orange;
    t.checkExpect(this.cell12connected.floodHelper(new Negate(), new Identity()), 
        new ArrayList<Cell>(Arrays.asList(this.cell12connected)));
    //to change a cell's color
    t.checkExpect(this.cell11connected.floodHelper(new Identity(), new Negate()), 
        new ArrayList<Cell>());
    this.cell21connected.flooded = true;
    t.checkExpect(this.cell21connected.floodHelper(new Identity(), new Negate()), 
        new ArrayList<Cell>(Arrays.asList(this.cell21connected)));
  }

  //=====================METHODS IN FLOODITWORLD=====================

  void testGenerateCells(Tester t) {
    initConditions();
    this.game1.generateCells();
    t.checkExpect(this.game1.cells, this.game1Cells);
    this.game2.generateCells();
    t.checkExpect(this.game2.cells, this.game2Cells);
  }

  void testConnectCells(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    //t.checkExpect(game1.cells, this.game1CellsConnected);

    //top left cell
    t.checkExpect(this.game1.cells.get(0).get(0).right, this.game1.cells.get(0).get(1));
    t.checkExpect(this.game1.cells.get(0).get(0).bottom, this.game1.cells.get(1).get(0));
    t.checkExpect(this.game1.cells.get(0).get(0).left, null);
    t.checkExpect(this.game1.cells.get(0).get(0).top, null);

    //top right cell
    t.checkExpect(this.game1.cells.get(0).get(3).right, null);
    t.checkExpect(this.game1.cells.get(0).get(3).bottom, this.game1.cells.get(1).get(3));
    t.checkExpect(this.game1.cells.get(0).get(3).left, this.game1.cells.get(0).get(2));
    t.checkExpect(this.game1.cells.get(0).get(3).top, null);

    //bottom left cell
    t.checkExpect(this.game1.cells.get(3).get(0).right, this.game1.cells.get(3).get(1));
    t.checkExpect(this.game1.cells.get(3).get(0).bottom, null);
    t.checkExpect(this.game1.cells.get(3).get(0).left, null);
    t.checkExpect(this.game1.cells.get(3).get(0).top, this.game1.cells.get(2).get(0));

    //middle cell
    t.checkExpect(this.game1.cells.get(1).get(1).right, this.game1.cells.get(1).get(2));
    t.checkExpect(this.game1.cells.get(1).get(1).bottom, this.game1.cells.get(2).get(1));
    t.checkExpect(this.game1.cells.get(1).get(1).left, this.game1.cells.get(1).get(0));
    t.checkExpect(this.game1.cells.get(1).get(1).top, this.game1.cells.get(0).get(1));

    //bottom right cell
    t.checkExpect(this.game1.cells.get(3).get(3).left, this.game1.cells.get(3).get(2));
    t.checkExpect(this.game1.cells.get(3).get(3).top, this.game1.cells.get(2).get(3));
    t.checkExpect(this.game1.cells.get(3).get(3).right, null);
    t.checkExpect(this.game1.cells.get(3).get(3).bottom, null);

  }

  void testPlaceAll(Tester t) {
    initConditions();
    t.checkExpect(this.game1.scene, new WorldScene(750, 750));
    this.game1.generateCells();
    this.game1.connectCells();
    this.game1.placeAll();
    t.checkExpect(this.game1.scene, this.game1InitScene);
  }

  void testIsDone(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    t.checkExpect(game1.isDone(), false);
    this.game1.cells = 
        new ArrayList<ArrayList<Cell>>(Arrays.asList(
            new ArrayList<Cell>(Arrays.asList(
                new Cell(40, 80, Color.orange, false, null, null, null, null))),
            new ArrayList<Cell>(Arrays.asList(
                new Cell(60, 100, Color.orange, false, null, null, null, null))),
            new ArrayList<Cell>(Arrays.asList(
                new Cell(800, 140, Color.orange, false, null, null, null, null)))));
    t.checkExpect(this.game1.isDone(), true);
  }

  void testDisplayScore(Tester t) {
    initConditions();
    t.checkExpect(this.game1.scene, new WorldScene(750, 750));
    this.game1.generateCells();
    this.game1.connectCells();

    this.game1.movesSoFar = 99;
    this.game1.displayScore();
    t.checkExpect(game1.scene, 
        new WorldScene(750, 750).placeImageXY(new TextImage("Moves: " + "99" + "/" 
            + "8", 20, Color.black), 
            105, 30).placeImageXY(new TextImage("You lose", 20, Color.black), 
                300, 30));

    initConditions();
    this.game1.movesSoFar = 5;
    this.game1.cells = 
        new ArrayList<ArrayList<Cell>>(Arrays.asList(
            new ArrayList<Cell>(Arrays.asList(
                new Cell(40, 80, Color.orange, false, null, null, null, null))),
            new ArrayList<Cell>(Arrays.asList(
                new Cell(60, 100, Color.orange, false, null, null, null, null))),
            new ArrayList<Cell>(Arrays.asList(
                new Cell(800, 140, Color.orange, false, null, null, null, null)))));
    this.game1.displayScore();
    t.checkExpect(this.game1.scene, new WorldScene(750, 750).placeImageXY(
        new TextImage("Moves: " + "5" + "/" 
            + "8", 20, Color.black), 
        105, 30).placeImageXY(new TextImage("You win", 20, Color.black), 
            300, 30));
  }

  void testOnMouseClicked(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    //a valid color is selected
    t.checkExpect(this.game1.movesSoFar, 0);
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.pink);
    this.game1.onMouseClicked(new Posn(161, 181));
    t.checkExpect(this.game1.movesSoFar, 1);
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.red);

    //a color is the board is that color right now  
    this.game1.onMouseClicked(new Posn(100, 80));
    t.checkExpect(this.game1.movesSoFar, 1);
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.red);

    //user didn't click on a cell
    this.game1.onMouseClicked(new Posn(500, 530));
    t.checkExpect(this.game1.movesSoFar, 1);
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.red);
  }

  void testIsProgress(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    t.checkExpect(this.game1.isProgress(), false);

    //board is in the progress of turning pink
    this.game1.cells.get(0).get(0).color = Color.red;
    this.game1.cells.get(0).get(1).flooded = true;
    this.game1.cells.get(0).get(0).color = Color.pink;
    t.checkExpect(this.game1.isProgress(), true);
  }

  void testFlood(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    t.checkExpect(game1.cells.get(0).get(1).flooded, false);
    this.game1.cells.get(0).get(0).color = Color.orange;
    this.game1.flood();
    t.checkExpect(this.game1.cells.get(1).get(0).flooded, true);
    t.checkExpect(this.game1.cells.get(2).get(0).flooded, false);
    this.game1.flood();
    t.checkExpect(this.game1.cells.get(2).get(0).flooded, true);
  }

  //=====STOP=======
  void testFloodColor(Tester t) {
    initConditions();
    
    this.game1.generateCells();
    this.game1.connectCells();
    
    //user selects red then green
    this.game1.cells.get(0).get(0).color = Color.red;
    this.game1.cells.get(0).get(1).flooded = true;
    this.game1.cells.get(1).get(0).flooded = true;
    this.game1.cells.get(0).get(0).color = Color.green;
    
    t.checkExpect(this.game1.cells.get(0).get(1).color, Color.red);
    t.checkExpect(this.game1.cells.get(1).get(0).color, Color.orange);
    t.checkExpect(this.game1.cells.get(2).get(0).color, Color.orange);
    
    this.game1.floodColor();
    
    t.checkExpect(this.game1.cells.get(0).get(1).color, Color.green);
    t.checkExpect(this.game1.cells.get(1).get(0).color, Color.green);
    t.checkExpect(this.game1.cells.get(2).get(0).color, Color.orange);
  }

  void testChangeColor(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    ArrayList<Cell> empty = new ArrayList<Cell>();
    this.game1.changeColor(empty);
    t.checkExpect(empty, new ArrayList<Cell>());
    
    ArrayList<Cell> notEmpty = new ArrayList<Cell>(Arrays.asList(
        new Cell(60, 80, Color.pink, true, null, null, null, null),
        new Cell(100, 80, Color.red, true, null, null, null, null),
        new Cell(60, 120, Color.orange, false, null, null, null, null),
        new Cell(100, 120, Color.orange, true, null, null, null, null)));
    this.game1.changeColor(notEmpty);
    t.checkExpect(notEmpty, new ArrayList<Cell>(Arrays.asList(
        new Cell(60,80, Color.pink, true, null, null, null, null), 
        new Cell(100, 80, Color.pink, true, null, null, null, null), 
        new Cell(60, 120, Color.pink, false, null, null, null, null), 
        new Cell(100, 120, Color.pink, true, null, null, null, null))));
  }

  void testChangeToFlooded(Tester t) {
    initConditions();
    
    this.game1.generateCells();
    this.game1.connectCells();
    ArrayList<Cell> empty = new ArrayList<Cell>();
    this.game1.changeToFlooded(empty);
    t.checkExpect(empty, new ArrayList<Cell>());
    
    ArrayList<Cell> notEmpty = new ArrayList<Cell>(Arrays.asList(
        new Cell(60, 80, Color.pink, false, null, null, null, null),
        new Cell(100, 80, Color.red, false, null, null, null, null),
        new Cell(60, 120, Color.orange, true, null, null, null, null),
        new Cell(100, 120, Color.orange, false, null, null, null, null)));
    this.game1.changeToFlooded(notEmpty);
    t.checkExpect(notEmpty, new ArrayList<Cell>(Arrays.asList(
        new Cell(60,80, Color.pink, true, null, null, null, null), 
        new Cell(100, 80, Color.red, true, null, null, null, null), 
        new Cell(60, 120, Color.orange, true, null, null, null, null), 
        new Cell(100, 120, Color.orange, true, null, null, null, null))));
  }

  void testOnTick(Tester t) {
    initConditions();
    
    this.game1.generateCells();
    this.game1.connectCells();
    
    t.checkExpect(this.game1.currentTick, 1);
    t.checkExpect(this.game1.cells.get(0).get(1).flooded, false);
    
    //color red is selected
    this.game1.cells.get(0).get(0).color = Color.red;
    this.game1.onTick();
    t.checkExpect(this.game1.currentTick , 2);
    t.checkExpect(this.game1.cells.get(0).get(1).flooded, true);
    
    //color orange is selected
    this.game1.cells.get(0).get(0).color = Color.orange;
    t.checkExpect(this.game1.cells.get(1).get(0).flooded, false);
    this.game1.onTick();
    t.checkExpect(this.game1.cells.get(1).get(0).flooded, true);
    t.checkExpect(this.game1.cells.get(0).get(1).color, Color.orange);
  }

  void testOnKeyEvent(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    
    this.game1.movesSoFar = 9;
    t.checkExpect(this.game1.movesSoFar, 9);
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.pink);
    t.checkExpect(this.game1.cells.get(2).get(3).color, Color.blue);
    t.checkExpect(this.game1.cells.get(1).get(2).color, Color.green);
    
    //invalid key press
    this.game1.onKeyEvent("o");
    t.checkExpect(this.game1.movesSoFar, 9);
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.pink);
    t.checkExpect(this.game1.cells.get(2).get(3).color, Color.blue);
    t.checkExpect(this.game1.cells.get(1).get(2).color, Color.green);
    
    this.game1.onKeyEvent("r");
    t.checkExpect(this.game1.movesSoFar, 0);
    //new cells are different
    t.checkExpect(this.game1.cells.get(0).get(0).color, Color.orange);
    t.checkExpect(this.game1.cells.get(2).get(3).color, Color.red);
    t.checkExpect(this.game1.cells.get(1).get(2).color, Color.red);
  }

  void testMakeScene(Tester t) {
    initConditions();
    this.game1.generateCells();
    this.game1.connectCells();
    
    //make a FloodItGame exactly like game1
    FloodItWorld game1Mirror = new FloodItWorld(4);
    game1Mirror.generateCells();
    game1Mirror.connectCells();
    
    t.checkExpect(this.game1.scene, new WorldScene(750, 750));
    t.checkExpect(game1Mirror.scene, new WorldScene(750, 750));
    t.checkExpect(this.game1.cells, game1Mirror.cells);
    
    this.game1.makeScene();
    game1Mirror.placeAll();
    game1Mirror.displayScore();
    //check if game1's scene is the same as just calling
    //placeAll() and displayScore()
    t.checkExpect(this.game1.scene, game1Mirror.scene);
  }

  //================METHOD IN NEGATE=================
  void testNegate(Tester t) {
    t.checkExpect(new Negate().test(true), false);
    t.checkExpect(new Negate().test(false), true);
  }

  //================METHOD IN IDENTITY=================
  void testIdentity(Tester t) {
    t.checkExpect(new Identity().test(true), true);
    t.checkExpect(new Identity().test(false), false);
  }

  void testConstructor1(Tester t) {
    //FloodItWorld g = new FloodItWorld();
    FloodItWorld g = new FloodItWorld(15);
    g.generateCells();
    g.connectCells();
    g.bigBang(750, 750, 1.0 / 28.0); 
  }

}



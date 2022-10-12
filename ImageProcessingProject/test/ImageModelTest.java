import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import model.ImageModel;
import model.ImageProcessor;

import static org.junit.Assert.assertEquals;

/**
 * To test the image model and every method works correctly.
 */
public class ImageModelTest {
  ImageProcessor image;
  Color[][] color;

  {
    color = new Color[4][4];
  }


  private Color[][] createPixel() {
    int r;
    int g;
    int b;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        r = i * j;
        g = i * j * 10;
        b = i * j * j;
        color[i][j] = new Color(r, g, b);
      }
    }
    return color;
  }

  /**
   * Initializes the image model.
   */
  @Before
  public void init() {
    this.image = new ImageModel(createPixel(), 255, "test");
  }

  /**
   * Test brighten or darken.
   */
  @Test
  public void testExposure() {
    // brighten by 10
    init();
    assertEquals("10 10 10\n" +
            "10 10 10\n" +
            "10 10 10\n" +
            "10 10 10\n" +
            "10 10 10\n" +
            "11 20 11\n" +
            "12 30 14\n" +
            "13 40 19\n" +
            "10 10 10\n" +
            "12 30 12\n" +
            "14 50 18\n" +
            "16 70 28\n" +
            "10 10 10\n" +
            "13 40 13\n" +
            "16 70 22\n" +
            "19 100 37\n", image.exposure(10, "test-brighter").toString());

    // brighten by 1000
    init();
    assertEquals("255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n", image.exposure(1000, "test-even-brighter").toString());

    // darken by 30
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 10 0\n" +
            "0 30 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 30 0\n" +
            "0 60 0\n", image.exposure(-30, "test-darker").toString());

    // darken by 300
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n", image.exposure(-300, "test-even-darker").toString());

    // brighten 10 then darken 30
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 10 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 20 0\n" +
            "0 40 0\n" +
            "0 0 0\n" +
            "0 10 0\n" +
            "0 40 0\n" +
            "0 70 7\n", image.exposure(10, "test-brighten")
            .exposure(-30, "test-bd").toString());
  }

  /**
   * Test vertical and horizontal flip.
   */
  @Test
  public void testFlip() {
    // horizontal flip
    init();
    assertEquals("0 0 0\n" +
            "3 30 3\n" +
            "6 60 12\n" +
            "9 90 27\n" +
            "0 0 0\n" +
            "2 20 2\n" +
            "4 40 8\n" +
            "6 60 18\n" +
            "0 0 0\n" +
            "1 10 1\n" +
            "2 20 4\n" +
            "3 30 9\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n", image.flipping(true, "test-h").toString());

    // vertical flip
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "3 30 9\n" +
            "2 20 4\n" +
            "1 10 1\n" +
            "0 0 0\n" +
            "6 60 18\n" +
            "4 40 8\n" +
            "2 20 2\n" +
            "0 0 0\n" +
            "9 90 27\n" +
            "6 60 12\n" +
            "3 30 3\n" +
            "0 0 0\n", image.flipping(false, "test-v").toString());

    // vertical then horizontal flip
    init();
    assertEquals("9 90 27\n" +
            "6 60 12\n" +
            "3 30 3\n" +
            "0 0 0\n" +
            "6 60 18\n" +
            "4 40 8\n" +
            "2 20 2\n" +
            "0 0 0\n" +
            "3 30 9\n" +
            "2 20 4\n" +
            "1 10 1\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n", image.flipping(false, "test-v")
            .flipping(true, "test-both").toString());
  }

  /**
   * Test convert to various grayscale.
   */
  @Test
  public void testConvertTo() {
    // value-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "10 10 10\n" +
            "20 20 20\n" +
            "30 30 30\n" +
            "0 0 0\n" +
            "20 20 20\n" +
            "40 40 40\n" +
            "60 60 60\n" +
            "0 0 0\n" +
            "30 30 30\n" +
            "60 60 60\n" +
            "90 90 90\n", image.convertTo("value-component", "value").toString());

    // intensity-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "4 4 4\n" +
            "8 8 8\n" +
            "14 14 14\n" +
            "0 0 0\n" +
            "8 8 8\n" +
            "17 17 17\n" +
            "28 28 28\n" +
            "0 0 0\n" +
            "12 12 12\n" +
            "26 26 26\n" +
            "42 42 42\n", image.convertTo("intensity-component", "intensity")
            .toString());

    // luma-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "7 7 7\n" +
            "15 15 15\n" +
            "22 22 22\n" +
            "0 0 0\n" +
            "14 14 14\n" +
            "30 30 30\n" +
            "45 45 45\n" +
            "0 0 0\n" +
            "22 22 22\n" +
            "45 45 45\n" +
            "68 68 68\n", image.convertTo("luma-component", "luma").toString());

    //greyscale
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "7 7 7\n" +
            "15 15 15\n" +
            "22 22 22\n" +
            "0 0 0\n" +
            "14 14 14\n" +
            "30 30 30\n" +
            "45 45 45\n" +
            "0 0 0\n" +
            "22 22 22\n" +
            "45 45 45\n" +
            "68 68 68\n", image.convertTo("greyscale", "gs").toString());


    // red-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "1 1 1\n" +
            "2 2 2\n" +
            "3 3 3\n" +
            "0 0 0\n" +
            "2 2 2\n" +
            "4 4 4\n" +
            "6 6 6\n" +
            "0 0 0\n" +
            "3 3 3\n" +
            "6 6 6\n" +
            "9 9 9\n", image.convertTo("red-component", "red").toString());

    // green-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "10 10 10\n" +
            "20 20 20\n" +
            "30 30 30\n" +
            "0 0 0\n" +
            "20 20 20\n" +
            "40 40 40\n" +
            "60 60 60\n" +
            "0 0 0\n" +
            "30 30 30\n" +
            "60 60 60\n" +
            "90 90 90\n", image.convertTo("green-component", "green").toString());

    // blue-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "1 1 1\n" +
            "4 4 4\n" +
            "9 9 9\n" +
            "0 0 0\n" +
            "2 2 2\n" +
            "8 8 8\n" +
            "18 18 18\n" +
            "0 0 0\n" +
            "3 3 3\n" +
            "12 12 12\n" +
            "27 27 27\n", image.convertTo("blue-component", "blue").toString());

    // blue-luma-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "1 1 1\n" +
            "4 4 4\n" +
            "9 9 9\n" +
            "0 0 0\n" +
            "2 2 2\n" +
            "8 8 8\n" +
            "18 18 18\n" +
            "0 0 0\n" +
            "3 3 3\n" +
            "12 12 12\n" +
            "27 27 27\n", image.convertTo("blue-component", "blue")
            .convertTo("luma-component", "blue-luma").toString());
  }

  /**
   * Test load image.
   */
  @Test
  public void testLoad() {
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "1 10 1\n" +
            "2 20 4\n" +
            "3 30 9\n" +
            "0 0 0\n" +
            "2 20 2\n" +
            "4 40 8\n" +
            "6 60 18\n" +
            "0 0 0\n" +
            "3 30 3\n" +
            "6 60 12\n" +
            "9 90 27\n", image.load("F:\\IntelliJ_IDEA\\CS_3500\\image_process" +
            "ing_assignment_4\\src\\test.ppm", "test").toString());
  }

  @Test
  public void testMultipleOperation() {
    // brighten flip red
    init();
    assertEquals("20 20 20\n" +
            "23 23 23\n" +
            "26 26 26\n" +
            "29 29 29\n" +
            "20 20 20\n" +
            "22 22 22\n" +
            "24 24 24\n" +
            "26 26 26\n" +
            "20 20 20\n" +
            "21 21 21\n" +
            "22 22 22\n" +
            "23 23 23\n" +
            "20 20 20\n" +
            "20 20 20\n" +
            "20 20 20\n" +
            "20 20 20\n", image.exposure(20, "b")
            .flipping(true, "bh")
            .convertTo("red-component", "bhr").toString());

    // flip darken luma-component
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "14 14 14\n" +
            "7 7 7\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "36 36 36\n" +
            "21 21 21\n" +
            "7 7 7\n" +
            "0 0 0\n" +
            "58 58 58\n" +
            "35 35 35\n" +
            "14 14 14\n" +
            "0 0 0\n", image.flipping(false, "f")
            .exposure(-10, "fd")
            .convertTo("luma-component", "fdl").toString());
  }

  @Test
  public void testSharpening() {
    // test sharpening
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "2 30 4\n" +
            "2 20 4\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "2 20 2\n" +
            "4 40 8\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n", image.manipulateImage("sharpening", "s").toString());

    // test sharpening x2
    init();
    assertEquals("0 0 0\n" +
            "0 5 1\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 6 0\n" +
            "3 52 7\n" +
            "2 20 4\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "2 20 2\n" +
            "4 40 8\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n", image.manipulateImage("sharpening", "s")
            .manipulateImage("sharpening", "ss").toString());
  }

  @Test
  public void testBlur() {
    // test blur
    init();
    assertEquals("0 0 0\n" +
            "0 2 0\n" +
            "0 3 0\n" +
            "0 0 0\n" +
            "0 2 0\n" +
            "0 8 0\n" +
            "0 18 4\n" +
            "3 30 9\n" +
            "0 3 0\n" +
            "0 18 1\n" +
            "1 35 6\n" +
            "6 60 18\n" +
            "0 0 0\n" +
            "3 30 3\n" +
            "6 60 12\n" +
            "9 90 27\n", image.manipulateImage("blur", "b").toString());

    // test blur x2
    init();
    assertEquals("0 0 0\n" +
            "0 2 0\n" +
            "0 3 0\n" +
            "0 0 0\n" +
            "0 2 0\n" +
            "0 8 0\n" +
            "0 16 3\n" +
            "3 30 9\n" +
            "0 3 0\n" +
            "0 16 0\n" +
            "0 33 5\n" +
            "6 60 18\n" +
            "0 0 0\n" +
            "3 30 3\n" +
            "6 60 12\n" +
            "9 90 27\n", image.manipulateImage("blur", "b")
            .manipulateImage("blur", "b").toString());
  }

  @Test
  public void testSepia() {
    // test sepia
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "8 7 5\n" +
            "16 15 11\n" +
            "25 23 18\n" +
            "0 0 0\n" +
            "16 14 11\n" +
            "33 30 23\n" +
            "51 46 36\n" +
            "0 0 0\n" +
            "24 22 17\n" +
            "50 45 35\n" +
            "77 69 54\n", image.manipulateImage("sepia", "s").toString());

    // test sepia x2
    init();
    assertEquals("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "9 8 6\n" +
            "19 17 13\n" +
            "30 27 21\n" +
            "0 0 0\n" +
            "19 17 13\n" +
            "40 35 28\n" +
            "62 55 43\n" +
            "0 0 0\n" +
            "29 26 20\n" +
            "60 54 42\n" +
            "93 83 64\n", image.manipulateImage("sepia", "s")
            .manipulateImage("sepia", "ss").toString());
  }

  @Test
  public void testNewMultipleEditions() {
    // brighten, sepia
    init();
    assertEquals("6 6 4\n" +
            "6 6 4\n" +
            "6 6 4\n" +
            "6 6 4\n" +
            "6 6 4\n" +
            "15 13 10\n" +
            "23 21 16\n" +
            "32 29 22\n" +
            "6 6 4\n" +
            "23 20 16\n" +
            "40 36 28\n" +
            "58 52 40\n" +
            "6 6 4\n" +
            "31 28 21\n" +
            "57 51 39\n" +
            "84 75 58\n", image.exposure(5, "b")
            .manipulateImage("sepia", "bs").toString());

    // sepia, blur, sharpen
    init();
    assertEquals("0 0 0\n" +
            "3 0 0\n" +
            "3 2 2\n" +
            "0 0 0\n" +
            "3 0 0\n" +
            "23 14 13\n" +
            "16 10 8\n" +
            "0 0 0\n" +
            "3 2 2\n" +
            "16 10 8\n" +
            "30 25 20\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "0 0 0\n", image.manipulateImage("sepia", "s")
            .manipulateImage("blur", "sb")
            .manipulateImage("sharpening", "sbs").toString());
  }

  @Test
  public void undo() {
    init();
    ImageProcessor imageFlipping = image.flipping(true, "test");
    image.getModelCreated().add(imageFlipping);
    assertEquals(2, image.getModelCreated().size());
    image.undo();
    assertEquals(1, image.getModelCreated().size());
    image.undo();
    assertEquals(1, image.getModelCreated().size());
  }
}
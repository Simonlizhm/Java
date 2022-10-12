
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageButtonController;
import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageProcessor;
import view.EditorView;
import view.IEditorView;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 */
public class ImageUtil {
  static ImageProcessor model;
  static Color[][] pixels;
  static int maxValue;
  static Scanner sc = new Scanner(System.in);

  //#####################################################################################//

  /**
   * The entrance of the program.
   *
   * @param args arguments of command lines
   */
  public static void main(String[] args) {
    Color[][] pixel = new Color[1][1];
    pixel[0][0] = Color.BLACK;
    model = new ImageModel(pixel, 255, "model");

    if (args.length == 0) {
      IEditorView view = new EditorView(model);
      ImageController control = new ImageButtonController(model, view);
      control.changeImage();
    } else {
      for (String a : args) {
        if (a.contains("-text")) {
          Readable rd = new InputStreamReader(System.in);
          ImageController controller = new ImageControllerImpl(model, rd);
          controller.changeImage();
        } else if (a.contains("-file")) {
          String filename = args[4];
          try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            String inputSum = "";

            while ((line = br.readLine()) != null) {
              inputSum = inputSum.concat(line) + "\n";
            }

            Reader in = new StringReader(inputSum);
            ImageController controller = new ImageControllerImpl(model, in);
            controller.changeImage();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }
}
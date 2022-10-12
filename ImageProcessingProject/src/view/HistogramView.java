package view;



import java.awt.Graphics;
import java.awt.Color;


import javax.swing.JPanel;
import model.ImageProcessor;

/**
 * This class calculates the frequency for each pixel and plot the histogram line by line.
 */
public class HistogramView extends JPanel {
  private ImageProcessor model;

  /**
   * This constructor will take in a model and process to paint lines.
   * @param model this is the model that we will pass in.
   */
  public HistogramView(ImageProcessor model) {
    this.model = model;
  }

  //#####################################################################################//

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.repaint();

    int[] arrRed = makeHistogram("red");
    int[] arrBlue = makeHistogram("blue");
    int[] arrGreen = makeHistogram("green");
    int[] arrIntensity = makeHistogram("intensity");

    int max = findMax(arrRed, arrBlue, arrGreen, arrIntensity);
    int min = findMin(arrRed, arrBlue, arrGreen, arrIntensity);
    float difference = max - min;

    for (int a = 0; a < arrRed.length; a++) {
      g.setColor(Color.RED);
      double red = (arrRed[a] - min) / difference;
      g.drawLine(a + 5, 350, a + 5, 350 - (int)(red * 350));
    }

    for (int a = 0; a < arrBlue.length; a++) {
      g.setColor(Color.BLUE);
      double blue = (arrBlue[a] - min) / difference;
      g.drawLine((a + 400) + 5, 350, (a + 400) + 5, 350 - (int)(blue * 350));
    }

    for (int a = 0; a < arrGreen.length; a++) {
      g.setColor(Color.GREEN);
      double green = (arrGreen[a] - min) / difference;
      g.drawLine((a + 800) + 5, 350, (a + 800) + 5, 350 - (int) (green * 350));
    }

    for (int a = 0; a < arrIntensity.length; a++) {
      g.setColor(Color.DARK_GRAY);
      double intensity = (arrIntensity[a] - min) / difference;
      g.drawLine((a + 1200) + 5, 350, (a + 1200) + 5, 350 - (int) (intensity * 350));
    }
  }

  //********************************************//

  private int findMax(int[] arrRed, int[] arrBLue, int[] arrGreen, int[] arrIntensity) {
    int max = arrRed[0];

    max = getMax(arrRed, arrBLue, max);
    max = getMax(arrGreen, arrIntensity, max);

    return max;
  }

  private int getMax(int[] arrRed, int[] arrBLue, int max) {
    for (int i : arrRed) {
      if (i > max) {
        max = i;
      }
    }
    for (int i : arrBLue) {
      if (i > max) {
        max = i;
      }
    }
    return max;
  }

  private int findMin(int[] arrRed, int[] arrBLue, int[] arrGreen, int[] arrIntensity) {
    int min = arrRed[0];

    min = getMin(arrRed, arrBLue, min);
    min = getMin(arrGreen, arrIntensity, min);

    return min;
  }

  private int getMin(int[] arrRed, int[] arrBLue, int min) {
    for (int i : arrRed) {
      if (i < min) {
        min = i;
      }
    }
    for (int i : arrBLue) {
      if (i < min) {
        min = i;
      }
    }
    return min;
  }

  //********************************************//

  /**
   * This makes the histograms by getting the pixel values and getting the frequencies.
   * @param color array of which color we are returning.
   * @return a list of integer arrays of frequencies of a color
   */
  public int[] makeHistogram(String color) {
    int[] returnArr = new int[256];
    ImageProcessor lastModel = model.getModelCreated().get(model.getModelCreated().size() - 1);

    for (int i = 0; i < lastModel.getPixelColor().length; i++) {
      for (int j = 0; j < lastModel.getPixelColor()[0].length; j++) {
        if (color.equalsIgnoreCase("blue")) {
          returnArr[lastModel.getPixelColor()[i][j].getBlue()]++;
        } else if (color.equalsIgnoreCase("red")) {
          returnArr[lastModel.getPixelColor()[i][j].getRed()]++;
        } else if (color.equalsIgnoreCase("green")) {
          returnArr[lastModel.getPixelColor()[i][j].getGreen()]++;
        } else if (color.equalsIgnoreCase("intensity")) {
          returnArr[(lastModel.getPixelColor()[i][j].getBlue()
                  + lastModel.getPixelColor()[i][j].getRed()
                  + lastModel.getPixelColor()[i][j].getGreen()) / 3]++;
        }
      }
    }
    return returnArr;
  }
}

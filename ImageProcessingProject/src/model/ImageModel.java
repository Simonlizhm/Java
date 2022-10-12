package model;

import java.awt.Component;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;

import javax.imageio.ImageIO;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is the ImageProcessor class where we implement operations of getting value, intensity,
 * luma, flipping, changing the brightness, etc.
 */
public class ImageModel extends Component implements ImageProcessor {
  private final String name;
  private Color[][] pixelColor;
  private int maxValue;
  private ArrayList<ImageProcessor> modelCreated;

  /**
   * The default constructor of the model.
   *
   * @param pixelColor the color information of each pixel
   * @param maxValue   the max color value of the PPm file
   * @param name       the name of the file
   */
  public ImageModel(Color[][] pixelColor, int maxValue, String name) {
    if (pixelColor == null) {
      throw new IllegalArgumentException("No image found");
    } else if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Please name the image");
    }

    this.pixelColor = pixelColor;
    this.maxValue = maxValue;
    this.name = name;
    this.modelCreated = new ArrayList<>();
  }

  //#####################################################################################//

  /**
   * Converts the image to visualize a color, value, intensity, or luma grayscale of the image.
   *
   * @param userInput the type of grayscale image the user wants to convert to
   * @return a 2d array of integer, each integer represents a specific image attribute
   */
  @Override
  public ImageProcessor convertTo(String userInput, String filename) {
    Color[][] pixel = new Color[pixelColor.length][pixelColor[0].length];

    for (int i = 0; i < this.pixelColor.length; i++) {
      for (int j = 0; j < this.pixelColor[i].length; j++) {
        int value = 0;

        int r = pixelColor[i][j].getRed();
        int g = pixelColor[i][j].getGreen();
        int b = pixelColor[i][j].getBlue();

        switch (userInput) {
          case "red-component":
            value = r;
            break;
          case "blue-component":
            value = b;
            break;
          case "green-component":
            value = g;
            break;
          case "luma-component":
          case "greyscale":
            value = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
            break;
          case "intensity-component":
            value = (r + g + b) / 3;
            break;
          case "value-component":
            if (r > value) {
              value = r;
            }
            if (g > value) {
              value = g;
            }
            if (b > value) {
              value = b;
            }
            break;
          default:
            throw new IllegalArgumentException("Cannot convert to this type");
        }

        pixel[i][j] = new Color(value, value, value);
      }
    }

    return new ImageModel(pixel, maxValue, filename);
  }


  //********************************************//

  @Override
  public ImageProcessor manipulateImage(String userInput, String filename) {
    double[][] kernel = new double[0][];
    Color[][] holder = new Color[pixelColor.length][pixelColor[0].length];
    int r;
    int g;
    int blue;
    Color[][] image;


    if (userInput.equalsIgnoreCase("sepia")) {
      for (int i = 0; i < pixelColor.length; i++) {
        for (int j = 0; j < pixelColor[0].length; j++) {
          r = (int) (pixelColor[i][j].getRed() * 0.393 + 0.769 * pixelColor[i][j].getGreen()
                  + pixelColor[i][j].getBlue() * 0.189);
          g = (int) (pixelColor[i][j].getRed() * 0.349 + 0.686 * pixelColor[i][j].getGreen()
                  + pixelColor[i][j].getBlue() * 0.168);
          blue = (int) (pixelColor[i][j].getRed() * 0.272 + 0.534 * pixelColor[i][j].getGreen()
                  + pixelColor[i][j].getBlue() * 0.131);
          if (r > maxValue) {
            r = maxValue;
          }
          if (g > maxValue) {
            g = maxValue;
          }
          if (blue > maxValue) {
            blue = maxValue;
          }
          holder[i][j] = new Color(r, g, blue);
        }
      }
      return new ImageModel(holder, maxValue, filename);

    } else if (userInput.equals("blur")) {
      kernel = new double[3][3];
      for (int a = 0; a < kernel.length; a++) {
        for (int b = 0; b < kernel[a].length; b++) {
          if (a == 1 && b == 1) {
            kernel[a][b] = 0.25;
          } else if ((a % 2 == 0 && b % 2 == 0)) {
            kernel[a][b] = 0.0625;
          } else {
            kernel[a][b] = 0.125;
          }
        }
      }

    } else if (userInput.equals("sharpening")) {
      kernel = new double[5][5];
      for (int a = 0; a < kernel.length; a++) {
        for (int b = 0; b < kernel[a].length; b++) {
          if (a == 2 && b == 2) {
            kernel[a][b] = 1;
          } else if (a == 0 || b == 0 || a == kernel.length - 1 || b == kernel.length - 1) {
            kernel[a][b] = -0.125;
          } else {
            kernel[a][b] = 0.25;
          }
        }
      }
    }

    image = changePixel(kernel);
    return new ImageModel(image, maxValue, filename);
  }

  // get center of a kernel
  private Color[][] changePixel(double[][] kernel) {
    int center = kernel.length / 2;
    int redTotal = 0;
    int greenTotal = 0;
    int blueTotal = 0;
    // create an array with edge cases
    Color[][] holder = new Color[pixelColor.length + center][pixelColor[0].length + center];
    Color[][] newHolder = new Color[pixelColor.length][pixelColor[0].length];

    for (int i = 0; i < holder.length; i++) {
      for (int j = 0; j < holder[i].length; j++) {
        if (i < center || j < center || i > pixelColor.length || j > pixelColor[0].length) {
          holder[i][j] = new Color(0, 0, 0);
        } else {
          holder[i][j] = pixelColor[i - center][j - center];
        }
      }
    }
    for (int a = center; a < holder.length - center; a++) {
      for (int b = center; b < holder[a].length - center; b++) {
        for (int c = 0; c < kernel.length; c++) {
          for (int d = 0; d < kernel[c].length; d++) {
            blueTotal += (int) (holder[a - center + c][b - center + d].getBlue() * kernel[c][d]);
            redTotal += (int) (holder[a - center + c][b - center + d].getRed() * kernel[c][d]);
            greenTotal += (int) (holder[a - center + c][b - center + d].getGreen() * kernel[c][d]);
          }
        }
        if (blueTotal < 0) {
          blueTotal = 0;
        }
        if (blueTotal > maxValue) {
          blueTotal = maxValue;
        }
        if (greenTotal < 0) {
          greenTotal = 0;
        }
        if (greenTotal > maxValue) {
          greenTotal = maxValue;
        }
        if (redTotal < 0) {
          redTotal = 0;
        }
        if (redTotal > maxValue) {
          redTotal = maxValue;
        }

        holder[a][b] = new Color(redTotal, greenTotal, blueTotal);
        blueTotal = 0;
        redTotal = 0;
        greenTotal = 0;
      }
    }
    for (int a = 0; a < newHolder.length; a++) {
      System.arraycopy(holder[a + center], center, newHolder[a], 0, newHolder[0].length);
    }
    return newHolder;
  }

  //********************************************//

  /**
   * Flip an image horizontally or vertically.
   *
   * @param direction true to flip horizontally, false to flip vertically
   */
  @Override
  public ImageProcessor flipping(boolean direction, String filename) {
    Color[][] holder = new Color[pixelColor.length][pixelColor[0].length];
    int count = 0;
    // horizontal
    if (direction) {
      for (int i = pixelColor.length - 1; i >= 0; i--) {
        System.arraycopy(pixelColor[i], 0, holder[count], 0, pixelColor[i].length);
        count++;
      }
    } else {
      for (int i = 0; i < pixelColor.length; i++) {
        for (int j = pixelColor[i].length - 1; j >= 0; j--) {
          holder[i][count] = pixelColor[i][j];
          count++;
        }
        count = 0;
      }
    }


    return new ImageModel(holder, maxValue, filename);
  }

  /**
   * Get the name of the image.
   *
   * @return the name of the image
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Get the pixel of the image.
   *
   * @return all the pixels in the image
   */
  @Override
  public Color[][] getPixelColor() {
    return this.pixelColor;
  }

  //********************************************//

  /**
   * Apply brightening or darkening to the image.
   *
   * @param value the amount to apply the changes.
   */
  @Override
  public ImageProcessor exposure(int value, String filename) {
    Color[][] holder = new Color[pixelColor.length][pixelColor[0].length];
    for (int i = 0; i < pixelColor.length; i++) {
      for (int j = 0; j < pixelColor[i].length; j++) {
        int r = pixelColor[i][j].getRed();
        int g = pixelColor[i][j].getGreen();
        int b = pixelColor[i][j].getBlue();

        // check if + - value is out of range
        if (r + value > maxValue) {
          r = maxValue - value;
        } else if (r + value < 0) {
          r = -value;
        }

        if (g + value > maxValue) {
          g = maxValue - value;
        } else if (g + value < 0) {
          g = -value;
        }

        if (b + value > maxValue) {
          b = maxValue - value;
        } else if (b + value < 0) {
          b = -value;
        }

        holder[i][j] = new Color(r + value, g + value, b + value);
      }
    }

    return new ImageModel(holder, maxValue, filename);
  }

  //********************************************//

  /**
   * Load the image from system.
   *
   * @param filePath the path of the file to be saved.
   * @param filename the name of the file being loaded
   * @return an ImageProcessor interface
   */
  @Override
  public ImageProcessor load(String filePath, String filename) {
    this.read(filePath);
    return new ImageModel(this.pixelColor, maxValue, filename);
  }

  //********************************************//

  private void read(String filePath) {
    Scanner sc;

    if (filePath.toLowerCase().contains(".ppm")) {
      try {
        sc = new Scanner(new FileInputStream(filePath));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException(e);
      }

      readPPM(sc);
    } else if (filePath.toLowerCase().contains(".jpg")
            || filePath.toLowerCase().contains(".png")
            || filePath.toLowerCase().contains(".bmp")) {
      try {
        BufferedImage img = ImageIO.read(new File(filePath));
        saveColorAssignment(img);
      } catch (IOException e) {
        throw new IllegalArgumentException("Invalid file path");
      }
      maxValue = 255;

    } else {
      throw new IllegalArgumentException("File type not supported");
    }
  }

  //********************************************//

  /**
   * Save the file into the system explorer.
   *
   * @param filePath the path of the file to be saved.
   */
  @Override
  public void save(String filePath) {
    if (filePath.toLowerCase().contains(".ppm")) {
      try {
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write("P3\n");
        fileWriter.write(this.pixelColor[0].length + " " + this.pixelColor.length + "\n");
        fileWriter.write(maxValue + "\n");

        for (Color[] colors : this.pixelColor) {
          for (int j = 0; j < this.pixelColor[0].length; j++) {
            fileWriter.write(colors[j].getRed() + "\n");
            fileWriter.write(colors[j].getGreen() + "\n");
            fileWriter.write(colors[j].getBlue() + "\n");
          }
        }
        fileWriter.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      saveHelper(filePath);
    }
  }

  //********************************************//

  @Override
  public void saveToSystem() {
    if (modelCreated.size() != 0) {
      BufferedImage saveImg = this.convertToImage();
      final JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG, PNG, BMP & PPM Images", "jpg", "png", "bmp", "ppm");
      fileChooser.setFileFilter(filter);
      int retrival = fileChooser.showSaveDialog(ImageModel.this);
      if (retrival == JFileChooser.APPROVE_OPTION) {
        try {
          File f = fileChooser.getSelectedFile();
          ImageIO.write(saveImg, "png", new File(f.getAbsolutePath()));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  //********************************************//

  @Override
  public String toString() {
    String show = "";
    for (Color[] colors : this.pixelColor) {
      for (int j = 0; j < this.pixelColor[0].length; j++) {
        show = show + colors[j].getRed() + " ";
        show = show + colors[j].getGreen() + " ";
        show = show + colors[j].getBlue() + "\n";
      }
    }
    return show;
  }

  //********************************************//

  private void saveHelper(String filePath) {
    File saveImage = new File(filePath);
    BufferedImage img = new BufferedImage(pixelColor[1].length,
            pixelColor.length, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < pixelColor.length; i++) {
      for (int j = 0; j < pixelColor[0].length; j++) {
        img.setRGB(j, i, pixelColor[i][j].getRGB());
      }
    }

    if (filePath.toLowerCase().contains(".jpg")) {
      try {
        ImageIO.write(img, "jpg", saveImage);
      } catch (IOException e) {
        throw new IllegalArgumentException("Save failed");
      }
    } else if (filePath.toLowerCase().contains(".png")) {
      try {
        ImageIO.write(img, "png", saveImage);
      } catch (IOException e) {
        throw new IllegalArgumentException("Save failed");
      }
    } else if (filePath.toLowerCase().contains(".bmp")) {
      try {
        ImageIO.write(img, "bmp", saveImage);
      } catch (IOException e) {
        throw new IllegalArgumentException("Save failed");
      }
    } else {
      throw new IllegalArgumentException("Unsupported file type");
    }
  }

  //********************************************//

  @Override
  public ArrayList<ImageProcessor> getModelCreated() {
    if (modelCreated.size() == 0) {
      Color[][] dummyColor = new Color[1][1];
      dummyColor[0][0] = Color.WHITE;
      modelCreated.add(new ImageModel(dummyColor, 255, "dummyName"));
    }

    return this.modelCreated;
  }

  //********************************************//

  @Override
  public ImageProcessor loadFromSystem() {
    BufferedImage img;

    final JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG, BMP & PPM Images", "jpg", "png", "bmp", "ppm");
    fileChooser.setFileFilter(filter);
    int retvalue = fileChooser.showOpenDialog(ImageModel.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      if (!f.getName().contains(".ppm")) {
        try {
          img = ImageIO.read(f);
        } catch (IOException e) {
          throw new IllegalArgumentException("Not an Image");
        }
        saveColorAssignment(img);
      } else if (f.getName().contains(".ppm")) {
        try {
          Scanner sc = new Scanner(new FileInputStream(f));
          readPPM(sc);
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException(e);
        }
      }
    }

    return new ImageModel(this.pixelColor, 255, this.name);
  }

  //********************************************//

  private void readPPM(Scanner sc) {
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    pixelColor = new Color[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelColor[i][j] = new Color(r, g, b);
      }
    }
  }

  //********************************************//

  @Override
  public BufferedImage convertToImage() {
    ImageProcessor model = this.modelCreated.get(this.modelCreated.size() - 1);
    Color[][] pixel = model.getPixelColor();
    BufferedImage img = new BufferedImage(pixel[0].length,
            pixel.length, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < pixel.length; i++) {
      for (int j = 0; j < pixel[0].length; j++) {
        img.setRGB(j, i, pixel[i][j].getRGB());
      }
    }

    return img;
  }

  //********************************************//

  @Override
  public void undo() {
    if (modelCreated.size() > 1) {
      modelCreated.remove(modelCreated.size() - 1);
    }
  }

  //********************************************//

  private void saveColorAssignment(BufferedImage img) {
    int red;
    int green;
    int blue;

    pixelColor = new Color[img.getHeight()][img.getWidth()];
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int assign = img.getRGB(x, y);
        Color color = new Color(assign, true);
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        pixelColor[y][x] = new Color(red, green, blue);
      }
    }
  }
}

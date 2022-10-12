package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This is the ImageProcessor Interface where we implement operations of getting value, intensity,
 * luminosity, flipping, changing the brightness, etc.
 */
public interface ImageProcessor {
  /**
   * Converts the image to visualize a color, value, intensity, or luma grayscale of the image.
   *
   * @param userInput the type of grayscale image the user wants to convert to
   * @return a grayscale version of the original copy
   */
  ImageProcessor convertTo(String userInput, String filename);

  /**
   * Flip an image horizontally or vertically.
   *
   * @param direction true to flip horizontally, false to flip vertically
   * @return returns a new ImageProcessor as a copy of the edited image
   */
  ImageProcessor flipping(boolean direction, String filename);

  /**
   * Apply brightening or darkening to the image.
   *
   * @param value the amount to apply the changes.
   * @return returns a new ImageProcessor as a copy of the edited image
   */
  ImageProcessor exposure(int value, String filename);

  /**
   * Save the file into the system explorer.
   *
   * @param filePath the path of the file to be saved.
   */
  void save(String filePath);

  /**
   * Load the image from system.
   *
   * @param filePath the path of the file to be saved.
   * @param filename the name of the file being loaded
   * @return an ImageProcessor interface
   */
  ImageProcessor load(String filePath, String filename);

  /**
   * Get the name of the image.
   *
   * @return the name of the image
   */
  String getName();

  /**
   * Set the image to sepia tone, blur the image, or sharpen the image based on userInput.
   *
   * @param userInput user choose to sepia, blur, or sharpen
   * @param filename  name of the file being edited
   * @return an ImageProcessor interface
   */
  ImageProcessor manipulateImage(String userInput, String filename);

  /**
   * Get the pixel of the image.
   *
   * @return all the pixels in the image
   */
  Color[][] getPixelColor();

  /**
   * Gets the arrayList that stores all the previous editions of the image.
   *
   * @return an arrayList that stores ImageProcessor interfaces
   */
  ArrayList<ImageProcessor> getModelCreated();

  /**
   * Load an image by choosing a file from system explorer and convert the file to
   * a 2d array of colors.
   *
   * @return an ImageProcessor interface
   */
  ImageProcessor loadFromSystem();

  /**
   * Saves the last edition to the system by file chooser.
   */
  void saveToSystem();

  /**
   * Converts the 2d array of colors into an image.
   *
   * @return an image being edited
   */
  BufferedImage convertToImage();

  /**
   * Return to the last step of operation that the user did.
   */
  void undo();
}


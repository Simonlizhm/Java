import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import model.ImageProcessor;

/**
 * Confirm correct inputs of the controller.
 */
public class ConfirmInputImage implements ImageProcessor {
  final StringBuilder log;

  /**
   * The default controller of the ConfirmInputImage class.
   *
   * @param log the log to be appended.
   */
  public ConfirmInputImage(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public ImageProcessor convertTo(String userInput, String filename) {
    log.append(
            "convertTo being called");
    return null;
  }

  @Override
  public ImageProcessor flipping(boolean direction, String filename) {
    log.append("flip");
    return null;
  }

  @Override
  public ImageProcessor exposure(int value, String filename) {
    log.append("exposure");
    return this;
  }

  @Override
  public void save(String filePath) {
    log.append("save");
  }

  @Override
  public ImageProcessor load(String filePath, String filename) {
    log.append(
            "load being called");
    return null;
  }

  @Override
  public String getName() {
    log.append("getName");
    return "YinuoSb";
  }

  @Override
  public ImageProcessor manipulateImage(String userInput, String filename) {
    log.append("manipulateImage being called");
    return this;
  }

  @Override
  public Color[][] getPixelColor() {
    log.append("getPixelColor");
    return new Color[0][];
  }

  @Override
  public ArrayList<ImageProcessor> getModelCreated() {

    log.append("getPixelColor");
    return null;
  }

  @Override
  public ImageProcessor loadFromSystem() {
    log.append("load being called");
    return null;
  }

  @Override
  public void saveToSystem() {
    log.append("save being called");
  }

  @Override
  public BufferedImage convertToImage() {
    log.append("convertToImage being called");
    return null;
  }

  @Override
  public void undo() {
    log.append("undo being called");
  }
}


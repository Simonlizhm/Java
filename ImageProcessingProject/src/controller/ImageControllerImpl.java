package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.ImageProcessor;

/**
 * Represents a controller for the image editor, it is responsible for taking in user inputs
 * and transmit them into the model.
 */
public class ImageControllerImpl implements ImageController {
  private ImageProcessor model;
  private ArrayList<ImageProcessor> modelCreated;
  private Readable readable;


  /**
   * The default constructor of the controller.
   *
   * @param model    an image processor model
   * @param readable inputs from user
   * @throws IllegalArgumentException the model cannot be null
   */
  public ImageControllerImpl(ImageProcessor model, Readable readable)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    modelCreated = new ArrayList<>();
    this.readable = readable;
    this.model = model;

    modelCreated.add(model);
  }

  /**
   * The main method for the controller that initializes the editor and
   * takes input to transmit to the model.
   */

  @Override
  public void changeImage() throws IllegalStateException {
    boolean quit = false;
    Scanner sc = new Scanner(readable);
    ImageProcessor image;

    while (!quit) {
      String userInput = sc.next();
      String valueStr;
      int value = 0;
      String filePath;
      String filename;
      String editedName = null;
      boolean sameName = true;
      boolean isInList = false;
      ImageProcessor imageEdited = null;
      ImageProcessor overwriteImage;

      switch (userInput) {
        case "brighten":
        case "darken":
          boolean isNumber = false;

          while (!isNumber) {
            valueStr = sc.next();
            if (isNumberHelper(valueStr)) {
              isNumber = true;
              value = Integer.parseInt(valueStr);
            } else {
              System.out.println("Please input an integer again");
            }
          }

          filename = sc.next();

          while (sameName) {
            editedName = sc.next();
            if (isSameName(editedName)) {
              System.out.println("The name has been entered, please enter another name again ");
            } else {
              sameName = false;
            }
          }

          for (ImageProcessor processor : modelCreated) {
            if (processor.getName().equals(filename)) {
              isInList = true;
              image = processor;
              if (userInput.equals("brighten")) {
                imageEdited = image.exposure(Math.abs(value), editedName);
              } else {
                imageEdited = image.exposure(-1 * Math.abs(value), editedName);
              }
            }
          }

          modelCreated.add(imageEdited);

          if (!isInList) {
            System.out.println("Filename not found ");
          } else {
            System.out.println("Image edited ");
          }
          break;

        case "vertical-flip":
        case "horizontal-flip":
        case "value-component":
        case "intensity-component":
        case "luma-component":
        case "red-component":
        case "green-component":
        case "blue-component":
        case "greyscale":
          filename = sc.next();

          while (sameName) {
            editedName = sc.next();
            if (this.isSameName(editedName)) {
              System.out.println("The name has been entered, please enter another name again ");
            } else {
              sameName = false;
            }
          }

          for (ImageProcessor processor : modelCreated) {
            if (processor.getName().equals(filename)) {
              isInList = true;
              image = processor;
              if (userInput.equals("vertical-flip")) {
                imageEdited = image.flipping(false, editedName);
              } else if (userInput.equals("horizontal-flip")) {
                imageEdited = image.flipping(true, editedName);
              } else {
                imageEdited = image.convertTo(userInput, editedName);
              }
            }
          }
          modelCreated.add(imageEdited);

          if (!isInList) {
            System.out.println("Filename not found ");
          } else {
            System.out.println("Image edited ");
          }
          break;

        case "sepia":
        case "blur":
        case "sharpening":
          filename = sc.next();

          while (sameName) {
            editedName = sc.next();
            if (this.isSameName(editedName)) {
              System.out.println("The name has been entered, please enter another name again ");
            } else {
              sameName = false;
            }
          }

          for (ImageProcessor processor : modelCreated) {
            if (processor.getName().equals(filename)) {
              isInList = true;
              image = processor;
              imageEdited = image.manipulateImage(userInput, editedName);
            }
          }
          modelCreated.add(imageEdited);

          if (!isInList) {
            System.out.println("Filename not found ");
          } else {
            System.out.println("Image edited ");
          }
          break;

        case "load":
          filePath = sc.next();
          filename = sc.next();
          overwriteImage = model.load(filePath, filename);
          modelCreated.set(0, overwriteImage);
          break;

        case "save":
          filePath = sc.next();
          filename = sc.next();
          for (ImageProcessor imageProcessor : modelCreated) {
            if (imageProcessor.getName().equals(filename)) {
              isInList = true;
              image = imageProcessor;
              image.save(filePath);
            }
          }

          if (!isInList) {
            System.out.println("Filename not found ");
          } else {
            System.out.println("Successfully saved! ");
          }
          break;

        case "q":
        case "quit":
          quit = true;
          System.out.println("Program quit! ");
          break;

        default:
          System.out.println("No such instruction ");
      }
    }
  }

  //********************************************//

  private boolean isSameName(String editedName) {
    boolean returnValue = false;
    for (ImageProcessor imageProcessor : this.modelCreated) {
      if (imageProcessor.getName().equals(editedName)) {
        returnValue = true;
      }
    }

    return returnValue;
  }

  //********************************************//

  private boolean isNumberHelper(String input) {
    try {
      int num = Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}

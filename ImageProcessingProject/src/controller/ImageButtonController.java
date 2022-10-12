package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import javax.swing.ImageIcon;

import model.ImageProcessor;
import view.IEditorView;

/**
 * The image button controller connects between the model and the GUI. This controller takes in
 * action events of those buttons and respond with models to them.
 */
public class ImageButtonController implements ImageController, ActionListener {
  private ImageProcessor model;
  private IEditorView view;

  /**
   * The default constructor of this class that takes in model and view to communicate between them.
   * @param model the model of the image program
   * @param view the GUI of the image program
   * @throws IllegalArgumentException the model or the view cannot be null
   */
  public ImageButtonController(ImageProcessor model, IEditorView view)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    } else if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }

    this.model = model;
    this.view = view;
  }

  //#####################################################################################//

  @Override
  public void changeImage() {
    this.view.setCommandButtonListener(this);
    this.view.makeVisible();
  }

  //********************************************//

  @Override
  public void actionPerformed(ActionEvent command) {
    ImageProcessor imageEdited;

    switch (command.getActionCommand()) {
      case "horizontal":
        imageEdited = model.getModelCreated().get(model.getModelCreated().size() - 1)
                .flipping(true, model.getName());
        addImage(imageEdited);
        break;
      case "vertical":
        imageEdited = model.getModelCreated().get(model.getModelCreated().size() - 1)
                .flipping(false, model.getName());
        addImage(imageEdited);
        break;
      case "brighten":
        imageEdited = model.getModelCreated().get(model.getModelCreated().size() - 1)
                .exposure(5, model.getName());
        addImage(imageEdited);
        break;
      case "darken":
        imageEdited = model.getModelCreated().get(model.getModelCreated().size() - 1)
                .exposure(-5, model.getName());
        addImage(imageEdited);
        break;
      case "sepia":
      case "sharpening":
      case "blur":
        imageEdited = model.getModelCreated().get(model.getModelCreated().size() - 1)
                .manipulateImage(command.getActionCommand(), model.getName());
        addImage(imageEdited);
        break;
      case "vertical-flip":
      case "horizontal-flip":
      case "value":
      case "intensity":
      case "luma":
      case "red":
      case "green":
      case "blue":
        imageEdited = model.getModelCreated().get(model.getModelCreated().size() - 1)
                .convertTo(command.getActionCommand() + "-component", model.getName());
        addImage(imageEdited);
        break;
      case "downSizeConfirm":
        break;
      case "undo":
        model.undo();
        BufferedImage image = model.convertToImage();
        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(new ImageIcon(image));
        view.addImagePanel(image);
        break;
      case "load":
        imageEdited = model.loadFromSystem();
        addImage(imageEdited);
        break;
      case "save":
        model.saveToSystem();
        break;
      default:
        System.out.println("No such instruction");
    }
  }

  private void addImage(ImageProcessor imageEdited) {
    model.getModelCreated().add(imageEdited);
    BufferedImage image = model.convertToImage();
    JLabel imgLabel = new JLabel();
    imgLabel.setIcon(new ImageIcon(image));

    view.showHistogram();
    view.addImagePanel(image);
  }
}

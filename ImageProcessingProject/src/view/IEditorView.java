package view;

import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

/**
 * An interface for the GUI of this program. This interface is responsible for storing any method
 * the GUI might need.
 */
public interface IEditorView {
  /**
   * Sets command button listeners for all the buttons created for the program.
   *
   * @param actionEvent action event.
   */
  void setCommandButtonListener(ActionListener actionEvent);

  /**
   * Makes the whole GUI visible.
   */
  void makeVisible();

  /**
   * Refreshes the image after each edition operation.
   *
   * @param img The image being edited
   */
  void addImagePanel(BufferedImage img) ;

  /**
   * Refreshes the histogram of the image after each edition operation.
   */
  void showHistogram();

  int getWidth();

  int getHeight();
}

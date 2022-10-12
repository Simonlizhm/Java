import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.JButton;

import controller.ImageButtonController;
import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageProcessor;
import view.EditorView;
import view.IEditorView;

import static org.junit.Assert.assertEquals;

/**
 * This test will test the controller's operations with different inputs, including load, save,
 * horizontal-flip, etc.
 */
public class TestController {
  ImageController controller;
  Reader in;
  StringBuilder log;
  ConfirmInputImage mock;

  /**
   * Create a log StringBuilder object that will keep track of what functions we've called so far.
   */
  @Before
  public void init() {
    this.log = new StringBuilder();
  }


  /**
   * This test will test the controller's operations with different inputs, including load, save,
   * horizontal-flip, etc.
   */
  @Test
  public void testChangeImage() {
    // test brighten in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("brighten 10 YinuoSb test-brighten q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameexposure", log.toString());

    init();
    // test darken in controller
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("darken 10 YinuoSb test-darken q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameexposure", log.toString());

    // test horizontal-flip in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("horizontal-flip YinuoSb true test-horizontal q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameflip", log.toString());

    // test vertical-flip in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("vertical-flip YinuoSb false test-vertical q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameflip", log.toString());

    // test intensity in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("intensity-component YinuoSb test-intensity q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameintensity-componenttest-intensity", log.toString());

    // test luma in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("luma-component YinuoSb test-luma-component q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameluma-componenttest-luma-component", log.toString());

    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("greyscale YinuoSb test-greyscale q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamegreyscaletest-greyscale", log.toString());

    // test value in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("value-component YinuoSb test-value q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamevalue-componenttest-value", log.toString());

    // test red-component in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("red-component YinuoSb test-red q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamered-componenttest-red", log.toString());

    // test green in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("green-component YinuoSb test-green q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamegreen-componenttest-green", log.toString());

    // test blue in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("blue-component YinuoSb test-blue q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNameblue-componenttest-blue", log.toString());

    // test load in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("load F:\\IntelliJ_IDEA\\CS_3500" +
            "\\image_processing_assignment_4\\src\\test.ppm YinuoSb test-load q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("load being called", log.toString());

    // test save in controller
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("save F:\\IntelliJ_IDEA\\CS_3500" +
            "\\image_processing_assignment_4\\test\\TestController.java YinuoSb test-save q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamesave", log.toString());

    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("sepia YinuoSb test-sepia q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamemanipulateImage being called", log.toString());

    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("blur YinuoSb test-blur q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamemanipulateImage being called", log.toString());

    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("sharpening YinuoSb test-sharpening q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamegetNamemanipulateImage being called", log.toString());

    // test jpg save
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("save F:\\IntelliJ_IDEA\\CS_3500" +
            "\\image_processing_assignment_4\\src\\simonSb.jpg YinuoSb q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamesave", log.toString());

    // test png save
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("save F:\\IntelliJ_IDEA\\CS_3500" +
            "\\image_processing_assignment_4\\src\\simonSb.png YinuoSb q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamesave", log.toString());

    // test bmp save
    init();
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("save F:\\IntelliJ_IDEA\\CS_3500" +
            "\\image_processing_assignment_4\\src\\simonSb.bmp YinuoSb q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamesave", log.toString());

    // test txt file
    mock = new ConfirmInputImage(log);
    this.in = new StringReader("load F:\\IntelliJ_IDEA\\CS_3500" +
            "\\image_processing_assignment_4\\src\\testText.txt YinuoSb q");
    this.controller = new ImageControllerImpl(mock, in);
    this.controller.changeImage();
    assertEquals("getNamesaveload being called", log.toString());
  }


  @Test
  public void testButtonController() {
    // test sepia
    init();
    mock = new ConfirmInputImage(log);
    Color[][] pixelColor = new Color[1][1];
    ImageProcessor model = new ImageModel(pixelColor, 255, "s");
    IEditorView view = new EditorView(model);
    JButton sepia = new JButton("sepia");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("manipulateImage being called", log.toString());

    // test blur
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton blur = new JButton("blur");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("blur being called", log.toString());

    // test sharpen
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton sharpen = new JButton("sharpen");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("manipulateImage being called", log.toString());

    // test exposure
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton brighten = new JButton("+5");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("exposure being called", log.toString());

    // test exposure
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton darken = new JButton("-5");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("exposure being called", log.toString());

    // test vflip
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton vFlip = new JButton("vertical");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("flipping being called", log.toString());

    // test hflip
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton v = new JButton("horizontal");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("flipping being called", log.toString());

    // test hflip
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton h = new JButton("horizontal");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("flipping being called", log.toString());

    // test greyscale
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton l = new JButton("luma");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("luma being called", log.toString());

    // test greyscale
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton r = new JButton("red-component");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("convertTo being called", log.toString());

    // test greyscale
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton g = new JButton("green-component");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("convertTo being called", log.toString());

    // test greyscale
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton b = new JButton("blue-component");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("convertTo being called", log.toString());

    // test greyscale
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton i = new JButton("intensity-component");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("convertTo being called", log.toString());

    // test undo
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton undo = new JButton("undo");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("undo being called", log.toString());

    // test load
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton load = new JButton("load");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("load being called", log.toString());

    // test save
    init();
    mock = new ConfirmInputImage(log);
    pixelColor = new Color[1][1];
    model = new ImageModel(pixelColor, 255, "s");
    view = new EditorView(model);
    JButton save = new JButton("save");
    this.controller = new ImageButtonController(mock, view);
    this.controller.changeImage();
    assertEquals("save being called", log.toString());
  }
}

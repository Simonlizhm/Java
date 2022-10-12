package view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

import model.ImageProcessor;

/**
 * This is a GUI class that adds all the panels and buttons to the JFrame and sets actions to
 * these buttons so that the controller and model could respond to them.
 */
public class EditorView extends JFrame implements IEditorView {
  private final ImageProcessor model;
  private final JPanel toolPanel;
  private final JPanel greyscalePanel;
  private HistogramView hisView;
  private JLabel imageLabel = new JLabel();
  private final JButton confirm;
  private final JButton maskingPath;
  private final JButton clearMasking;
  private final JButton hFlip;
  private final JButton vFlip;
  private final JButton sepia;
  private final JButton blur;
  private final JButton sharpen;
  private final JButton load;
  private final JButton save;
  private final JButton componentRed;
  private final JButton componentGreen;
  private final JButton componentBlue;
  private final JButton luma;
  private final JButton intensity;
  private final JButton value;
  private final JButton undo;
  private final JButton brighten;
  private final JButton darken;

  //********************************************//

  /**
   * The constructor of the class that takes in a model and add panels and buttons to the frame.
   * @param model the model of the image that stores the pixels
   */
  public EditorView(ImageProcessor model) {
    super();
    this.model = model;

    setTitle("Image Editor");
    setSize(1600, 1200);


    // uses a border layout
    this.setLayout(new BorderLayout());

    // add panels
    Border border = new LineBorder(Color.GRAY, 2, true);
    JPanel histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(1600, 350));
    this.add(histogramPanel, BorderLayout.PAGE_START);
    histogramPanel.setBorder(border);

    this.greyscalePanel = new JPanel();
    greyscalePanel.setPreferredSize(new Dimension(200, 800));
    this.add(greyscalePanel, BorderLayout.LINE_START);
    greyscalePanel.setBorder(border);

    // pass the image to the image panel
    JScrollPane imagePanel = new JScrollPane(imageLabel);
    imagePanel.setPreferredSize(new Dimension(1100, 800));
    this.add(imagePanel, BorderLayout.CENTER);
    imagePanel.setBorder(border);

    this.toolPanel = new JPanel();
    toolPanel.setPreferredSize(new Dimension(300, 800));
    this.add(toolPanel, BorderLayout.LINE_END);
    toolPanel.setBorder(border);

    //********************************************//

    // application of the toolbar
    // exposure sidebar
    addBlank(10);
    JLabel exposure = new JLabel("Exposure");
    exposure.setPreferredSize(new Dimension(255, 15));
    this.brighten = new JButton("+5");
    this.darken = new JButton("-5");
    brighten.setPreferredSize(new Dimension(100, 30));
    darken.setPreferredSize(new Dimension(100, 30));
    this.toolPanel.add(exposure);
    this.toolPanel.add(darken);
    this.toolPanel.add(brighten);

    this.addBlank(8);

    // flip buttons
    JLabel flip = new JLabel("Flip");
    flip.setPreferredSize(new Dimension(255, 15));
    this.toolPanel.add(flip);
    this.hFlip = new JButton("horizontal");
    hFlip.setPreferredSize(new Dimension(100, 30));
    this.toolPanel.add(hFlip);
    this.vFlip = new JButton("vertical");
    vFlip.setPreferredSize(new Dimension(100, 30));
    this.toolPanel.add(vFlip);

    this.addBlank(20);

    // blur or sharpen button
    JLabel blurSharpen = new JLabel("Blur/Sharpen");
    blurSharpen.setPreferredSize(new Dimension(255, 15));
    this.toolPanel.add(blurSharpen);
    this.blur = new JButton("blur");
    this.sharpen = new JButton("sharpen");
    blur.setPreferredSize(new Dimension(100, 30));
    sharpen.setPreferredSize(new Dimension(100, 30));
    this.toolPanel.add(blur);
    this.toolPanel.add(sharpen);

    this.addBlank(20);

    // sepia-tone button
    JLabel sepiaTone = new JLabel("Sepia-tone Filter");
    sepiaTone.setPreferredSize(new Dimension(255, 15));
    this.sepia = new JButton("sepia");
    sepia.setPreferredSize(new Dimension(100, 30));
    toolPanel.add(sepiaTone);
    toolPanel.add(sepia);

    this.addBlank(15);

    // down-scaling button
    JLabel downScaling = new JLabel("Down Scaling");
    downScaling.setPreferredSize(new Dimension(255, 15));
    JTextArea width = new JTextArea("");
    width.setPreferredSize(new Dimension(100, 30));
    JTextArea height = new JTextArea("");
    height.setPreferredSize(new Dimension(100, 30));
    confirm = new JButton("confirm");
    confirm.setPreferredSize(new Dimension(155, 30));
    toolPanel.add(downScaling);
    toolPanel.add(width);
    toolPanel.add(height);
    toolPanel.add(confirm);

    this.addBlank(20);

    // masking
    JLabel masking = new JLabel("Partial Editing");
    masking.setPreferredSize(new Dimension(255, 15));
    this.maskingPath = new JButton("mask image file path");
    maskingPath.setPreferredSize(new Dimension(155, 30));
    this.clearMasking = new JButton("clear mask");
    clearMasking.setPreferredSize(new Dimension(100, 30));
    toolPanel.add(masking);
    toolPanel.add(maskingPath);
    this.addBlank(0);
    toolPanel.add(clearMasking);

    this.addBlank(20);

    // load and save
    JLabel importExport = new JLabel("Import/Export Image");
    load = new JButton("load");
    save = new JButton("save");
    importExport.setPreferredSize(new Dimension(255, 15));
    load.setPreferredSize(new Dimension(100, 30));
    save.setPreferredSize(new Dimension(100, 30));
    toolPanel.add(importExport);
    toolPanel.add(load);
    toolPanel.add(save);

    //********************************************//

    // application of the greyscale panel
    enter();
    componentRed = new JButton("red greyscale");
    componentRed.setPreferredSize(new Dimension(140, 40));
    componentGreen = new JButton("green greyscale");
    componentGreen.setPreferredSize(new Dimension(140, 40));
    componentBlue = new JButton("blue greyscale");
    componentBlue.setPreferredSize(new Dimension(140, 40));
    value = new JButton("value greyscale");
    value.setPreferredSize(new Dimension(140, 40));
    intensity = new JButton("intensity greyscale");
    intensity.setPreferredSize(new Dimension(140, 40));
    luma = new JButton("luma greyscale");
    luma.setPreferredSize(new Dimension(140, 40));
    undo = new JButton("undo");
    undo.setPreferredSize(new Dimension(130, 55));
    greyscalePanel.add(componentRed);
    enter();
    greyscalePanel.add(componentGreen);
    enter();
    greyscalePanel.add(componentBlue);
    enter();
    greyscalePanel.add(value);
    enter();
    greyscalePanel.add(intensity);
    enter();
    greyscalePanel.add(luma);
    enter();
    enter();
    enter();
    greyscalePanel.add(undo);
    hisView = new HistogramView(model);
    histogramPanel.add(hisView);

    this.makeVisible();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  //#####################################################################################//

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    for (JButton jButton : Arrays.asList(brighten, darken, confirm, maskingPath,
            clearMasking, hFlip, vFlip, sepia, blur, sharpen, load, save,
            componentRed, componentGreen, componentBlue, intensity, value, luma, undo)) {
      jButton.addActionListener(actionEvent);

      brighten.setActionCommand("brighten");
      darken.setActionCommand("darken");
      hFlip.setActionCommand("horizontal");
      vFlip.setActionCommand("vertical");
      sepia.setActionCommand("sepia");
      blur.setActionCommand("blur");
      sharpen.setActionCommand("sharpening");
      load.setActionCommand("load");
      save.setActionCommand("save");
      undo.setActionCommand("undo");
      componentRed.setActionCommand("red");
      componentGreen.setActionCommand("green");
      componentBlue.setActionCommand("blue");
      intensity.setActionCommand("intensity");
      value.setActionCommand("value");
      luma.setActionCommand("luma");
      confirm.setActionCommand("downSizeConfirm");
      maskingPath.setActionCommand("masking path");
      clearMasking.setActionCommand("clear masking");
    }
  }

  //********************************************//

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  //********************************************//

  @Override
  public void showHistogram() {
    HistogramView h = new HistogramView(model);
    int[] arr = h.makeHistogram("red");
    hisView.removeAll();
    Border border = new LineBorder(Color.RED, 0, false);

    for (int i : arr) {
      JPanel line = new JPanel();
      line.setPreferredSize(new Dimension(1, i));
      hisView.add(line);
      line.setBorder(border);
    }
  }

  //********************************************//

  @Override
  public void addImagePanel(BufferedImage img) {
    ImageIcon icon = new ImageIcon(img);
    imageLabel.setIcon(icon);
  }

  //********************************************//

  private void addBlank(int height) {
    JLabel blank = new JLabel("");
    blank.setPreferredSize(new Dimension(255, height));
    this.toolPanel.add(blank);
  }

  //********************************************//

  private void enter() {
    JLabel blank = new JLabel("");
    blank.setPreferredSize(new Dimension(255, 20));
    this.greyscalePanel.add(blank);
  }
}


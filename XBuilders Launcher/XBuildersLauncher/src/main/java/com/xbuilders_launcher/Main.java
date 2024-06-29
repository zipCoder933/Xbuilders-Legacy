package com.xbuilders_launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame {


    public static void main(String[] args) throws IOException, FontFormatException {


        File currentDir = new File(System.getProperty("user.dir"));
        System.out.println("Current dir: " + currentDir.getAbsolutePath());

        new Main(
                "XBuildersUI4J.jar",
                "Xbuilders3.jar",
                new File(currentDir.getParentFile(), "XBuilders-2"),
                new File(currentDir.getParentFile(), "XBuilders"),
                "");
    }


    private JPanel topPanel;
    private JButton XB2;
    private JButton XB3;
    private JLabel titleLabel;
    private JButton exitButton;


    Font font;

    final Color BUTTON_DEFAULT_BACKGROUND = new Color(50, 50, 50);
    final Color BUTTON_DEFAULT_FOREGROUND = new Color(200, 200, 200);
    final Color BUTTON_HOVERED = new Color(50, 50, 200);
    final int BUTTON_SIZE = 48;

    public Main(String xb2command, String xb3command,
                File xb2dir, File xb3dir, String args) throws IOException, FontFormatException {
        super("XBuilders Launcher");


        setSize(450, 310);
        //Disable maximize button
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(topPanel);
        //Disable the top bar
        setUndecorated(true);
        topPanel.setBorder(new EmptyBorder(35, 35, 35, 35));

        //Set some padding at the bottom of titleLabel
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        //Set default windows look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream inputStream = loadResource("icon.png");

        //Load image
        //Set icon
        Image image = ImageIO.read(inputStream);
        setIconImage(image);

        //Set image icons for buttons
        Image xb3 = ImageIO.read(loadResource("XB3 icon.png")).getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        XB3.setIcon(new ImageIcon(xb3));
        Image xb2 = ImageIO.read(loadResource("XB2 icon.png")).getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        XB2.setIcon(new ImageIcon(xb2));

        //Load font
        InputStream is = loadResource("Press_Start_2P/PressStart2P-Regular.ttf");
        font = Font.createFont(Font.TRUETYPE_FONT, is);

        //Apply font
        Font largeFont = font.deriveFont(12f);
        Font smallFont = font.deriveFont(10f);
        titleLabel.setFont(largeFont);
        //Set font to buttons
        XB2.setFont(smallFont);
        XB3.setFont(smallFont);
//        exitButton.setFont(smallFont);

        exitButton.addActionListener(e -> dispose());

        styleButton(XB2, () -> {
            runProgram("java -jar \"" + xb2dir.getAbsolutePath() + "\\" + xb2command + "\" " + args, xb2dir);
        });
        styleButton(XB3, () -> {
            runProgram("java -jar \"" + xb3dir.getAbsolutePath() + "\\" + xb3command + "\" " + args, xb3dir);
        });

        setVisible(true);
    }


    public void styleButton(JButton button, Runnable clickAction) {
        button.setFocusPainted(false);
        button.setBackground(BUTTON_DEFAULT_BACKGROUND);
        button.setForeground(BUTTON_DEFAULT_FOREGROUND);

        //Padding
        button.setBorder(new EmptyBorder(15, 15, 15, 15));

        button.setHorizontalAlignment(SwingConstants.LEFT); // Align icon and text to the left

        button.addActionListener(e -> {
            clickAction.run();
            dispose();
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVERED); // Set blue background on hover
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_DEFAULT_BACKGROUND);
                button.setForeground(BUTTON_DEFAULT_FOREGROUND);
            }
        });
    }

    public void runProgram(String command, File workingDir) {
        System.out.println("command: " + command + "\n\n" + "workingDir: \"" + workingDir.getAbsolutePath() + "\"");
        try {
            Runtime.getRuntime().exec(command, null, workingDir);
        } catch (IOException e) {
            createPopupWindow("Error",
                    "command: " + command + "\n\n" +
                            "workingDir: \"" + workingDir.getAbsolutePath() + "\"\n\n" + e.toString());
        }
    }

    public static void createPopupWindow(String title, String str) {
        final JFrame parent = new JFrame();
        JLabel label = new JLabel("");
        label.setText("<html><body style='padding:5px;'>" + str.replace("\n", "<br>") + "</body></html>");
        label.setVerticalAlignment(JLabel.TOP);
        label.setFont(label.getFont().deriveFont(12f));
        parent.add(label);
        parent.pack();
        parent.getContentPane().setBackground(Color.white);
        parent.setVisible(true);
        parent.pack();
        parent.setTitle(title);
        parent.setLocationRelativeTo(null);
        parent.setAlwaysOnTop(true);
        parent.setVisible(true);
        parent.setSize(380, 200);
    }

    private InputStream loadResource(String path) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}

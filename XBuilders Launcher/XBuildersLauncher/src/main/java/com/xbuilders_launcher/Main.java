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
import java.nio.file.Files;

public class Main extends JFrame {


    private static File getRelativePath(String relativePath) {
        File currentDir = new File(System.getProperty("user.dir"));
        //If relativePath starts with ../ set currentDir up one level
        while (relativePath.startsWith("../") || relativePath.startsWith("..\\")) {
            currentDir = currentDir.getParentFile();
            relativePath = relativePath.substring(3);
        }
        return new File(currentDir, relativePath);
    }

    public static void main(String[] args) {
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            System.out.println("Current dir: " + currentDir.getAbsolutePath());
            File config = new File(currentDir, "config.txt");

            if (!config.exists()) {
                //Create the config.txt file
                String str = "XB2\t" +
                        "XBuilders-2-main\\XBuilders-2-main\t" +
                        "XBuildersUI4J.jar\n" +
                        //---------------------------
                        "XB3\t" +
                        "XBuilders-main\\XBuilders-main\t" +
                        "XBuilders3.jar";

                Files.write(config.toPath(), str.getBytes());
            }

            //Read the config.txt file
            String[] lines = new String(Files.readAllBytes(config.toPath())).split("\n");

            File xbuilders2File = null;
            File xbuilder3File = null;
            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].trim();
                String[] parts = lines[i].split("\t");

                if (parts[0].equals("XB2")) {
                    xbuilders2File = getRelativePath(parts[1]);
                } else if (parts[0].equals("XB3")) {
                    xbuilder3File = getRelativePath(parts[1]);
                }
            }

            System.out.println("XBuilders 2 path: " + xbuilders2File.getAbsolutePath());
            System.out.println("XBuilders 3 path: " + xbuilder3File.getAbsolutePath());
            if (!xbuilders2File.exists()) {
                createPopupWindow("Error", "XBuilders 2 not found:\n \"" + xbuilders2File.getAbsolutePath() + "\"");
            }

            if (!xbuilder3File.exists()) {
                createPopupWindow("Error", "XBuilders 3 not found:\n \"" + xbuilder3File.getAbsolutePath() + "\"");
            }


            //Remove the filenamef rom the path and  put it in the name
            String xbuilders2Name = xbuilders2File.getName();
            File xbuilders2Dir = xbuilders2File.getParentFile();
            String xbuilder3Name = xbuilder3File.getName();
            File xbuilder3Dir = xbuilder3File.getParentFile();
            System.out.println("\nXBuilders 2 name: " + xbuilders2Name);
            System.out.println("XBuilders 3 name: " + xbuilder3Name);
            System.out.println("XBuilders 2 dir: " + xbuilders2Dir.getAbsolutePath());
            System.out.println("XBuilders 3 dir: " + xbuilder3Dir.getAbsolutePath());


            new Main(
                    xbuilders2Name,
                    xbuilder3Name,
                    xbuilders2Dir,
                    xbuilder3Dir,
                    "");
        } catch (Exception e) {
            e.printStackTrace();
            createPopupWindow("Error", e.toString());
        }
    }


    private JPanel topPanel;
    private JButton XB2;
    private JButton XB3;
    private JLabel titleLabel;
    private JButton exitButton;
    private JCheckBox runAsTerminal;


    Font font;

    final Color BUTTON_DEFAULT_BACKGROUND = new Color(50, 50, 50);
    final Color BUTTON_DEFAULT_FOREGROUND = new Color(200, 200, 200);
    final Color BUTTON_HOVERED = new Color(50, 50, 200);
    final int BUTTON_SIZE = 48;


    public Main(String xb2jarfile, String xb3jarfile,
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
            runProgram(xb2dir, xb2jarfile, args,
                    runAsTerminal.isSelected());
        });
        styleButton(XB3, () -> {
            runProgram(xb3dir, xb3jarfile, args,
                    runAsTerminal.isSelected());
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

    public void runProgram(File workingDir, String jarfile, String args, boolean runAsTerminal) {
        try {
            if (runAsTerminal) {
                String command = "java -jar \\\"" + jarfile + "\\\" " + args;
                System.out.println(command);
                // Create a ProcessBuilder to run PowerShell in a new window
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start",
                        "powershell.exe", "-NoExit", "-Command", command);
                pb.directory(workingDir);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                // Wait for the process to complete (It will still close because we are just starting a new window)
                process.waitFor();
            } else {
                String command = "java -jar \"" + jarfile + "\" " + args;
                System.out.println(command);
                Runtime.getRuntime().exec(command, null, workingDir);
            }
        } catch (IOException | InterruptedException e) {
            createPopupWindow("Error",
                    "Jarfile: \"" + jarfile + "\"\n\n" +
                            "args: \"" + args + "\"\n\n" +
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

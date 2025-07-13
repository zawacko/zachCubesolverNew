package org.cubexell.cubesolver.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

import static org.cubexell.cubesolver.core.CubeConstants.*;

public class RaspberryPiCamera implements CubeColorInspector{
    public RaspberryPiCamera(Robot robot) {
        this.robot = robot;
    }

    String outputImage = "cubeColors.jpg";

    int imageWidth = 3280;
    int imageHeight = 2464;

    Robot robot;

    public void startup() {

    }

    public char[][][] inspect() {
        captureImage();
        drawPositioningGrid(500);
        char[][][] cubeColors = new char[6][3][3];

        char[][] backFace = inspectBackFace('B');

        char[][] leftFace = inspectLeftFace('O');

        char[][] downFace = inspectDownFace('Y');

        robot.executeMoves(SEE_OPPOSITE_FACE_FRONT);
        outputImage = "cubeColorsF.jpg";
        captureImage();
        char[][] frontFace = inspectBackFace('G');
        robot.executeMoves(SEE_OPPOSITE_FACE_FRONT);

        robot.executeMoves(SEE_OPPOSITE_FACE_RIGHT);
        outputImage = "cubeColorsR.jpg";
        captureImage();
        char[][] rightFace = inspectLeftFace('R');
        robot.executeMoves(SEE_OPPOSITE_FACE_RIGHT);

        robot.executeMoves(SEE_OPPOSITE_FACE_UP);
        outputImage = "cubeColorsU.jpg";
        captureImage();
        char[][] upFace = inspectDownFace('W');
        robot.executeMoves(SEE_OPPOSITE_FACE_UP);

        cubeColors[BACK_FACE_INDEX] = backFace;
        cubeColors[LEFT_FACE_INDEX] = leftFace;
        cubeColors[DOWN_FACE_INDEX] = downFace;
        cubeColors[FRONT_FACE_INDEX] = frontFace;
        cubeColors[RIGHT_FACE_INDEX] = rightFace;
        cubeColors[UP_FACE_INDEX] = upFace;
    return cubeColors;
    }

    public char[][] inspectBackFace(char center) {
        return new char[][]{
                {
                        findColorHSV(1000,615,95,85),
                        findColorHSV(1160,490,240,90),
                        findColorHSV(1490,140,290,90),
                },
                {
                        findColorHSV(850,1050,150,250),
                        center,
                        findColorHSV(1500,515,300,215)
                },
                {
                        findColorHSV(755,1500,175,250),
                        findColorHSV(1020,1375,290,250),
                        findColorHSV(1400,1080,400,290)
                },
        };
    }

    public char[][] inspectLeftFace(char center) {
        return new char[][]{
                {
                        findColorHSV(1950,75,225,125),
                        findColorHSV(2320,450,200,125),
                        findColorHSV(2600,640,90,80),
                },
                {
                        findColorHSV(1925,500,325,215),
                        center,
                        findColorHSV(2715,1060,125,275)
                },
                {
                        findColorHSV(1910,1040,375,315),
                        findColorHSV(2425,1325,200,275),
                        findColorHSV(2800,1515,150,300)
                },
        };
    }

    public char[][] inspectDownFace(char center) {
        return new char[][]{
                {
                        findColorHSV(2490,1990,375,95),
                        findColorHSV(2025,2070,350,75),
                        findColorHSV(1900,2130,80,50),
                },
                {
                        findColorHSV(2175,1800,375,125),
                        center,
                        findColorHSV(1400,2020,150,90)
                },
                {
                        findColorHSV(1600,1550,500,180),
                        findColorHSV(1250,1740,250,190),
                        findColorHSV(800,1900,280,100)
                },
        };
    }

    public void captureImage() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("libcamera-jpeg", "-o", outputImage, "--width", Integer.toString(imageWidth), "--height", Integer.toString(imageHeight), "--timeout", "1000");

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Image captured successfully: " + outputImage);
            } else {
                System.out.println("Error capturing image, exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public char findColorRGB (int squareX, int squareY, int squareWidth, int squareHeight) {
        Color averageColor = new Color(0,0,0);
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        try {
            BufferedImage image = ImageIO.read(new File(outputImage));
            long sumRed = 0, sumGreen = 0, sumBlue = 0;
            int pixelCount = 0;

            for (int x = squareX; x < squareX + squareWidth; x++) {
                for (int y = squareY; y < squareY + squareHeight; y++) {
                    Color color = new Color(image.getRGB(x, y));

                    sumRed += color.getRed();
                    sumGreen += color.getGreen();
                    sumBlue += color.getBlue();
                    pixelCount++;
                }
            }

            avgRed = (int) (sumRed / pixelCount);
            avgGreen = (int)  (sumGreen / pixelCount);
            avgBlue = (int)  (sumBlue / pixelCount);

            averageColor = new Color(avgRed, avgGreen, avgBlue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        char finalColor = classifyColorRGB(averageColor);

//        if (finalColor == 'U') {
//            System.out.println("no matching color. exiting program. rgb: "+averageColor);
//            System.exit(1);
//        }

        drawSquare(squareX, squareY, squareWidth, squareHeight);

        System.out.println(Integer.toString(avgRed) + " " + Integer.toString(avgGreen) + " " + Integer.toString(avgBlue));

        return finalColor;
    }

    public char findColorHSV (int squareX, int squareY, int squareWidth, int squareHeight) {
        float avgHue = 0, avgSaturation = 0, avgValue = 0;
        try {
            BufferedImage image = ImageIO.read(new File(outputImage));
            float totalHue = 0, totalSaturation = 0, totalValue = 0;
            int pixelCount = 0;

            for (int x = squareX; x < squareX + squareWidth; x++) {
                for (int y = squareY; y < squareY + squareHeight; y++) {
                    Color color = new Color(image.getRGB(x, y));

                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();

                    float[] hsv = Color.RGBtoHSB(red,green,blue,null);
                    hsv[0] *= 360;

                    totalHue += hsv[0];
                    totalSaturation += hsv[1];
                    totalValue += hsv[2];

                    pixelCount++;
                }
            }

            avgHue = totalHue/pixelCount;
            avgSaturation = totalSaturation/pixelCount;
            avgValue = totalValue/pixelCount;

        } catch (Exception e) {
            e.printStackTrace();
        }

        char finalColor = classifyColorHSV(avgHue, avgSaturation, avgValue);

//        if (finalColor == 'U') {
//            System.out.println("no matching color. exiting program. rgb: "+averageColor);
//            System.exit(1);
//        }

        drawSquare(squareX, squareY, squareWidth, squareHeight);

        System.out.println(avgHue + " " + avgSaturation + " " + avgValue);

        return finalColor;
    }

    public char classifyColorRGB(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        if (red > 120 && green > 120 && blue > 100) {
            return 'W';
        }

        if (red > 150 && green > 150 && blue < 50) {
            return 'Y';
        }

        if (red > 150 && green > 50 && green < 150 && blue < 100 && green-blue > 15) {
            return 'O';
        }

        if (red > 120 && green < 90 && blue < 80) {
            return 'R';
        }

        if (red < 100 && green > 100 && blue < 100) {
            return 'G';
        }

        if (red < 120 && green < 130 && blue > 70) {
            return 'B';
        }
        //if unknown
        return 'U';
    }

    public char classifyColorHSV(float hue, float saturation, float value) {

        if (saturation < 0.2 && value > 0.5) {
            return 'W';
        } else if ((hue > 240 && value > 0.8)||((((hue < 58 && hue > 45)|| hue > 300 || (hue >= 150 && hue < 200)) && value < 0.9) || (hue > 100 && hue < 150 && value > 0.8))) {
            return 'R';
        } else if ((hue < 58) /*|| (hue >= 300 && hue < 345)*/) {
            return 'O';
        } else if (hue >= 58 && hue < 85) {
            return 'Y';
        } else if (hue >= 85 && hue < 150) {
            return 'G';
        } else if (hue >= 200 && hue < 220) {
            return 'B';
        } else {
            return 'U';
        }
    }

    public void drawSquare(int squareX, int squareY, int squareWidth, int squareHeight) {
        try {
            BufferedImage image = ImageIO.read(new File(outputImage));

            Graphics2D g2d = image.createGraphics();

            g2d.setColor(Color.RED);

            g2d.drawRect(squareX, squareY, squareWidth, squareHeight);

            g2d.dispose();

            ImageIO.write(image, "jpg", new File(outputImage));
            //System.out.println("squares drawn and image saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPositioningGrid(int squareSize) {
        try {
            BufferedImage image = ImageIO.read(new File(outputImage));

            Graphics2D g2d = image.createGraphics();

            g2d.setColor(Color.RED);

            for(int x = 0; x < imageWidth; x += squareSize) {
                for(int y = 0; y < imageHeight; y += squareSize) {
                    g2d.drawRect(x, y, squareSize, squareSize);
                }
            }

            g2d.dispose();

            ImageIO.write(image, "jpg", new File(outputImage));
            System.out.println("square drawn and image saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("finished drawing grid");
    }

    public void shutdown() {

    }
}

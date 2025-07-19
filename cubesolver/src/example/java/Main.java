import org.cubexell.cubesolver.core.*;

import java.util.Scanner;

import com.pi4j.Pi4J;

import com.pi4j.io.gpio.*;

import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;




public class Main {

	public static void main(String[] args) {
        int BEGINNER_METHOD = 1;
        int OP_METHOD = 2;
        int KOCIEMBA_METHOD = 3;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Scramble Cube? (Y or N): ");
        while (!scanner.hasNextLine()){
                LockSupport.parkNanos(10000);
        }
        String isScramblingCube = scanner.nextLine();

        System.out.println("Use OP, Beginner, or Kociemba method (1 for OP, 2 for beginner, 3 for Kociemba): ");
        while (!scanner.hasNextLine()){
                LockSupport.parkNanos(10000);
        }
        int method = scanner.nextInt();

		scanner.close();

        String cubeSolvingMethod;
        if (method == 1) {
            cubeSolvingMethod = "Old Pochmann";
        } else if (method == 2) {
            cubeSolvingMethod = "Beginner";
        } else {
            cubeSolvingMethod = "Kociemba";
        }


        if (args.length > 0 && args[0] != null) {
            cubeSolvingMethod = args[0];
        }

        Motor upMotor = new RohsStepperMotor(24, 25, 8, 7);
        Motor downMotor = new RohsStepperMotor(1, 12, 16, 20);
        Motor rightMotor = new RohsStepperMotor(21, 26, 19, 13);
        Motor leftMotor = new RohsStepperMotor(2, 3, 4, 17);
        Motor frontMotor = new RohsStepperMotor(6, 5, 0, 11);
        Motor backMotor = new RohsStepperMotor(9, 10, 22, 27);

        Robot robot = new RaspberryPiRobot(upMotor, downMotor, rightMotor, leftMotor, frontMotor, backMotor);


        System.out.println("Cube solving method: " + cubeSolvingMethod);


        CubeScrambler scrambler = new CubeScrambler(robot);

		Cube cube;

        CubeColorInspector inspector = new OpenCvRaspberryPiCamera(robot);
        char[][][] cubeColors;

		//String[] scrambleMoves = {"Ri","Bi","Ri","F2","L","F2","B","R","Ui","R","Fi","U","Li","B","L2","U2","B2","F2","L2","F"};


        if ("Y".equalsIgnoreCase(isScramblingCube)) {
			String[] scrambleMoves = scrambler.getScramble(20);
			System.out.println("------Scramble-------------");
			for (String scrambleMove : scrambleMoves) {
				System.out.print(scrambleMove + ",");
			}
            cubeColors = scrambler.scramble(scrambleMoves);
        }
		else {
            cubeColors = inspector.inspect();
        }

        cube = new Cube(cubeColors);
        System.out.println("------After scramble------------");
        System.out.println(cube.toString());

        CubeSolvingMethod solver;

        if ("Beginner".equalsIgnoreCase(cubeSolvingMethod)) {
            solver = new BeginnerMethod();
        }
		else if ("Kociemba".equalsIgnoreCase(cubeSolvingMethod)) {
            solver = new KociembaAlgorithm();
        }
		else {
            solver = new OldPochmannMethod();
        }

		String[] solution = solver.solve(cubeColors);



        System.out.println("------solution num moves: " + solution.length + "------------");
        for (String s : solution) {
            System.out.print(s + ",");
        }
        System.out.println();
        String[] simplifiedSolution = cube.simplifySolution(solution);
        System.out.println("------simplified solution num moves: " + simplifiedSolution.length + "------------");
        for (String s : simplifiedSolution) {
            System.out.print(s + ",");
        }

        cube.simulateMoves(simplifiedSolution);
        System.out.println("------AfterSolving------------");
        System.out.println(cube.toString());

        robot.executeMoves(simplifiedSolution);
        robot.resetMotors();

        System.out.println("finished");

    }

}

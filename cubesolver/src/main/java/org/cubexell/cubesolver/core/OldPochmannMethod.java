package org.cubexell.cubesolver.core;

import java.util.ArrayList;
import java.util.HashSet;

import static org.cubexell.cubesolver.core.CubeConstants.*;

public class OldPochmannMethod  
	implements CubeSolvingMethod {
	
	Cube cube;
    ArrayList<String> solution;

	public OldPochmannMethod () {
		//super (cubeColors);
	}
	
	public static final String[] TPERM = {"R","U","Ri","Ui","Ri","F","R2","Ui","Ri","Ui","R","U","Ri","Fi"};
    public static final String[] CORNER_SWAP_ALGORITHM = {"R","Ui","Ri","Ui","R","U","Ri","Fi","R","U","Ri","Ui","Ri","F","R"};
    public static final String[] WB = {"R2","Ui","R2"};
    public static final String[] REV_WB = {"R2","U","R2"};
    public static final String[] WG = {"R2","U","R2"};
    public static final String[] REV_WG = {"R2","Ui","R2"};
    public static final String[] WO = {" "};
    public static final String[] REV_WO = {" "};
    public static final String[] OW = {"Li","U","Bi","Ui"};
    public static final String[] REV_OW = {"U","B","Ui","L"};
    public static final String[] OG = {"Ui","F","U"};
    public static final String[] REV_OG = {"Ui","Fi","U"};
    public static final String[] OY = {"Li","Ui","F","U"};
    public static final String[] REV_OY = {"Ui","Fi","U","L"};
    public static final String[] OB = {"U","Bi","Ui"};
    public static final String[] REV_OB = {"U","B","Ui"};
    public static final String[] GW = {"R","Fi","Ri","Li"};
    public static final String[] REV_GW = {"L","R","F","Ri"};
    public static final String[] GR = {"U2","R","U2"};
    public static final String[] REV_GR = {"U2","Ri","U2"};
    public static final String[] GY = {"R","F","Ri","Li"};
    public static final String[] REV_GY = {"L","R","Fi","Ri"};
    public static final String[] GO = {"Li"};
    public static final String[] REV_GO = {"L"};
    public static final String[] RB = {"U","B","Ui"};
    public static final String[] REV_RB = {"U","Bi","Ui"};
    public static final String[] RY = {"D2","Li","Ui","F","U"};
    public static final String[] REV_RY = {"Ui","Fi","U","L","D2"};
    public static final String[] RG = {"Ui","Fi","U"};
    public static final String[] REV_RG = {"Ui","F","U"};
    public static final String[] BW = {"Ri","B","R","L"};
    public static final String[] REV_BW = {"Li","Ri","Bi","R"};
    public static final String[] BO = {"L"};
    public static final String[] REV_BO = {"Li"};
    public static final String[] BY = {"Ri","Bi","R","L"};
    public static final String[] REV_BY = {"Li","Ri","B","R"};
    public static final String[] BR = {"U2","Ri","U2"};
    public static final String[] REV_BR = {"U2","R","U2"};
    public static final String[] YG = {"Di","L2"};
    public static final String[] REV_YG = {"L2","D"};
    public static final String[] YR = {"D2","L2"};
    public static final String[] REV_YR = {"L2","D2"};
    public static final String[] YB = {"D","L2"};
    public static final String[] REV_YB = {"L2","Di"};
    public static final String[] YO = {"L2"};
    public static final String[] REV_YO = {"L2"};
    //Check letter position a, then r, and then e(with corner in blue)

    public static final String[] WRB = {"R","Di"};
    public static final String[] REV_WRB = {"D","Ri"};
    public static final String[] WGR = {"F"};
    public static final String[] REV_WGR = {"Fi"};
    public static final String[] WOG = {"F","Ri"};
    public static final String[] REV_WOG = {"R","Fi"};
    public static final String[] OGW = {"F2"};
    public static final String[] REV_OGW = {"F2"};
    public static final String[] OYG = {"D2","R"};
    public static final String[] REV_OYG = {"Ri","D2"};
    public static final String[] OBY = {"D2"};
    public static final String[] REV_OBY = {"D2"};
    public static final String[] GWO = {"Fi","D"};
    public static final String[] REV_GWO = {"Di","F"};
    public static final String[] GRW = {"F2","D"};
    public static final String[] REV_GRW = {"Di","F2"};
    public static final String[] GYR= {"F","D"};
    public static final String[] REV_GYR = {"Di","Fi"};
    public static final String[] GOY = {"D"};
    public static final String[] REV_GOY = {"Di"};
    public static final String[] RWG = {"Ri"};
    public static final String[] REV_RWG = {"R"};
    public static final String[] RBW = {"R2"};
    public static final String[] REV_RBW = {"R2"};
    public static final String[] RYB = {"R"};
    public static final String[] REV_RYB = {"Ri"};
    public static final String[] RGY = {" "};
    public static final String[] REV_RGY = {" "};
    public static final String[] BWR = {"Ri","F"};
    public static final String[] REV_BWR = {"Fi","R"};
    public static final String[] BYO = {"Di","R"};
    public static final String[] REV_BYO = {"Ri","D"};
    public static final String[] BRY = {"Di"};
    public static final String[] REV_BRY = {"D"};
    public static final String[] YGO = {"Fi"};
    public static final String[] REV_YGO = {"F"};
    public static final String[] YRG = {"Ri","Di"};
    public static final String[] REV_YRG = {"D","R"};
    public static final String[] YBR = {"D2","Fi"};
    public static final String[] REV_YBR = {"F","D2"};
    public static final String[]  YOB = {"D","Fi"};
    public static final String[] REV_YOB = {"F","Di"};
    
    public void executeEdgeSwap(String faceColor, ArrayList<String> solution) {
        
        if ("WB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, WB);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_WB);
        }
        else if ("WG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, WG);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_WG);
        }
        else if ("WO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, WO);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_WO);
        }
        else if ("OW".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OW);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_OW);
        }
        else if ("OG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OG);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_OG);
        }
        else if ("OY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OY);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_OY);
        }
        else if ("OB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OB);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_OB);
        }
        else if ("GW".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GW);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_GW);
        }
        else if ("GR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GR);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_GR);
        }
        else if ("GY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GY);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_GY);
        }
        else if ("GO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GO);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_GO);
        }
        else if ("RB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RB);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_RB);
        }
        else if ("RY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RY);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_RY);
        }
        else if ("RG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RG);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_RG);
        }
        else if ("BW".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BW);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_BW);
        }
        else if ("BO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BO);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_BO);
        }
        else if ("BY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BY);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_BY);
        }
        else if ("BR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BR);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_BR);
        }
        else if ("YG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YG);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_YG);
        }
        else if ("YR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YR);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_YR);
        }
        else if ("YB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YB);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_YB);
        }
        else if ("YO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YO);
            cube.recordMoves(solution, TPERM);
            cube.recordMoves(solution, REV_YO);
        }
        
    }

    public void executeCornerSwap(String faceColor, ArrayList<String> solution) {
        
        if ("WRB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, WRB);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_WRB);
        }
        else if ("WGR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, WGR);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_WGR);
        }
        else if ("WOG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, WOG);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_WOG);
        }
        else if ("OGW".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OGW);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_OGW);
        }
        else if ("OYG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OYG);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_OYG);
        }
        else if ("OBY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, OBY);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_OBY);
        }
        else if ("GWO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GWO);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_GWO);
        }
        else if ("GRW".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GRW);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_GRW);
        }
        else if ("GYR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GYR);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_GYR);
        }
        else if ("GOY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, GOY);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_GOY);
        }
        else if ("RWG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RWG);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_RWG);
        }
        else if ("RBW".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RBW);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_RBW);
        }
        else if ("RYB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RYB);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_RYB);
        }
        else if ("RGY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, RGY);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_RGY);
        }
        else if ("BWR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BWR);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_BWR);
        }
        else if ("BYO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BYO);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_BYO);
        }
        else if ("BRY".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, BRY);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_BRY);
        }
        else if ("YGO".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YGO);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_YGO);
        }
        else if ("YRG".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YRG);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_YRG);
        }
        else if ("YBR".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YBR);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_YBR);
        }
        else if ("YOB".equalsIgnoreCase(faceColor)) {
            cube.recordMoves(solution, YOB);
            cube.recordMoves(solution, CORNER_SWAP_ALGORITHM);
            cube.recordMoves(solution, REV_YOB);
        }
    }
    

    private void addUnsolvedEdge(HashSet<String> unsolvedEdges, String edge){
        if(!cube.isEdgeSolved(edge))
            unsolvedEdges.add(edge);
    }

    private HashSet<String> createUnsolvedEdges() {
        HashSet<String> unsolvedEdges = new HashSet<>(22);
        addUnsolvedEdge(unsolvedEdges, "WB");
        addUnsolvedEdge(unsolvedEdges, "WG");
        addUnsolvedEdge(unsolvedEdges, "WO");

        addUnsolvedEdge(unsolvedEdges, "OW");
        addUnsolvedEdge(unsolvedEdges, "OG");
        addUnsolvedEdge(unsolvedEdges, "OY");
        addUnsolvedEdge(unsolvedEdges, "OB");

        addUnsolvedEdge(unsolvedEdges, "GW");
        addUnsolvedEdge(unsolvedEdges, "GR");
        addUnsolvedEdge(unsolvedEdges, "GY");
        addUnsolvedEdge(unsolvedEdges, "GO");

        addUnsolvedEdge(unsolvedEdges, "RB");
        addUnsolvedEdge(unsolvedEdges, "RY");
        addUnsolvedEdge(unsolvedEdges, "RG");

        addUnsolvedEdge(unsolvedEdges, "BW");
        addUnsolvedEdge(unsolvedEdges, "BO");
        addUnsolvedEdge(unsolvedEdges, "BY");
        addUnsolvedEdge(unsolvedEdges, "BR");

        addUnsolvedEdge(unsolvedEdges, "YG");
        addUnsolvedEdge(unsolvedEdges, "YR");
        addUnsolvedEdge(unsolvedEdges, "YB");
        addUnsolvedEdge(unsolvedEdges, "YO");
        return unsolvedEdges;
    }

    private void addUnsolvedCorner(HashSet<String> unsolvedCorners, String corner){
        if(!cube.isCornerSolved(corner))
            unsolvedCorners.add(corner);
    }

    private HashSet<String> createUnsolvedCorners() {
        HashSet<String> unsolvedCorners = new HashSet<>(22);
        addUnsolvedCorner(unsolvedCorners, "WRB");
        addUnsolvedCorner(unsolvedCorners, "WGR");
        addUnsolvedCorner(unsolvedCorners, "WOG");

        addUnsolvedCorner(unsolvedCorners, "OGW");
        addUnsolvedCorner(unsolvedCorners, "OYG");
        addUnsolvedCorner(unsolvedCorners, "OBY");

        addUnsolvedCorner(unsolvedCorners, "GWO");
        addUnsolvedCorner(unsolvedCorners, "GRW");
        addUnsolvedCorner(unsolvedCorners, "GYR");
        addUnsolvedCorner(unsolvedCorners, "GOY");

        addUnsolvedCorner(unsolvedCorners, "RBW");
        addUnsolvedCorner(unsolvedCorners, "RYB");
        addUnsolvedCorner(unsolvedCorners, "RWG");
        addUnsolvedCorner(unsolvedCorners, "RGY");

        addUnsolvedCorner(unsolvedCorners, "BWR");
        addUnsolvedCorner(unsolvedCorners, "BYO");
        addUnsolvedCorner(unsolvedCorners, "BRY");

        addUnsolvedCorner(unsolvedCorners, "YGO");
        addUnsolvedCorner(unsolvedCorners, "YRG");
        addUnsolvedCorner(unsolvedCorners, "YBR");
        addUnsolvedCorner(unsolvedCorners, "YOB");
        return unsolvedCorners;
    }
    
    String getEdgeToBeSolved(){
        char b = cube.cubeColors[UP_FACE_INDEX][1][2];
        char m = cube.cubeColors[RIGHT_FACE_INDEX][0][1];
        return String.valueOf(b) + String.valueOf(m);
    }
    
    public void solveEdges() {
        HashSet<String> unsolvedEdges = createUnsolvedEdges();
        while (unsolvedEdges.size() > 0 && !cube.areEdgesSolved()) {
            System.out.println("num unsolved edges: " + unsolvedEdges.size());
            String faceColor = getEdgeToBeSolved();
 
            System.out.println("faceColor: " + faceColor);
            while (!"WR".equalsIgnoreCase(faceColor) &&
                    !"RW".equalsIgnoreCase(faceColor)){
                executeEdgeSwap(faceColor, solution);
                if (unsolvedEdges.contains(faceColor)){
                    unsolvedEdges.remove(faceColor);
                    unsolvedEdges.remove(cube.getReverseOfString(faceColor));
                }
                faceColor = getEdgeToBeSolved();
               
                System.out.println("faceColor: " + faceColor);
                System.out.println("num unsolved edges: " + unsolvedEdges.size());
            }

            if (unsolvedEdges.size() > 0) {
                System.out.println("new cycle, swapping with: " + unsolvedEdges.iterator().next());
                System.out.println("number of unsolved edges: " + unsolvedEdges.size());
                executeEdgeSwap(unsolvedEdges.iterator().next(), solution);
            }
        }
        System.out.println("Done with edges..., unsolved edges: " + unsolvedEdges.size());
    }
    
    public String getCornerToBeSolved(){
        char a = cube.cubeColors[UP_FACE_INDEX][0][0];
        char r = cube.cubeColors[BACK_FACE_INDEX][0][2];
        char e = cube.cubeColors[LEFT_FACE_INDEX][0][0];
        String faceColor = String.valueOf(a) + String.valueOf(r) + String.valueOf(e);
        return faceColor;
    }
    public void solveCorners() {
        HashSet<String> unsolvedCorners = createUnsolvedCorners();
        while (unsolvedCorners.size() > 0  && !cube.areCornersSolved()) {
            System.out.println("num unsolved corners: " + unsolvedCorners.size());
            char a = cube.cubeColors[UP_FACE_INDEX][0][0];
            char r = cube.cubeColors[BACK_FACE_INDEX][0][2];
            char e = cube.cubeColors[LEFT_FACE_INDEX][0][0];
            String faceColor = String.valueOf(a) + String.valueOf(r) + String.valueOf(e);
            while (!"WBO".equalsIgnoreCase(faceColor) &&
                    !"OWB".equalsIgnoreCase(faceColor) &&
                    !"BOW".equalsIgnoreCase(faceColor)){
                executeCornerSwap(faceColor, solution);
                if (unsolvedCorners.contains(faceColor)){
                    unsolvedCorners.remove(faceColor);
                    unsolvedCorners.remove(cube.otherCorner1(a,r,e));
                    unsolvedCorners.remove(cube.otherCorner2(a,r,e));
                }
                a = cube.cubeColors[UP_FACE_INDEX][0][0];
                r = cube.cubeColors[BACK_FACE_INDEX][0][2];
                e = cube.cubeColors[LEFT_FACE_INDEX][0][0];
                faceColor = String.valueOf(a) + String.valueOf(r) + String.valueOf(e);
                System.out.println("faceColor: " + faceColor);
                System.out.println("num unsolved corners: " + unsolvedCorners.size());
            }
            if (unsolvedCorners.size() > 0) {
                executeCornerSwap(unsolvedCorners.iterator().next(), solution);
                System.out.println("new cycle, swapping with: " + unsolvedCorners.iterator().next());
                System.out.println("number of unsolved corners: " + unsolvedCorners.size());
            }
        }
        System.out.println("Done with corners..., unsolved corners: " + unsolvedCorners.size());
        
    }
    
    public String[] solve (char[][][] cubeColors) {
    	char[][][] cubeColorsCopy = new char[6][3][3];
    	Helper.copy3dArray(cubeColors, cubeColorsCopy);
    	this.cube = new Cube(cubeColorsCopy);
    	this.solution = new ArrayList<String>();
    	solveEdges();
    	solveCorners();
    	return solution.toArray(new String[0]);
    }


}

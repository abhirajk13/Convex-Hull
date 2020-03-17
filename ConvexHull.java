import java.util.Scanner;
import java.util.*;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;



public class ConvexHull extends Application {


   static List<Double> xvalues = new ArrayList<Double>();
   static List<Double> yvalues = new ArrayList<Double>();
   
   static List<Double> allx = new ArrayList<Double>();
   static List<Double> ally = new ArrayList<Double>();
   

   @Override 
   public void start(Stage stage) { 
   
        stage.setTitle("Convex Hull of Hedgehogs Path");
        Group root = new Group();
        Canvas canvas = new Canvas(1500, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
        
   } 
   
     
       
     private void drawShapes(GraphicsContext gc) {
     
        for(int x=0; x<xvalues.size(); x+=2) {
      
            gc.strokeLine(xvalues.get(x)*50, yvalues.get(x)*50,xvalues.get(x+1)*50, yvalues.get(x+1)*50);
      
        }
      
        for(int i = 0; i<allx.size(); i++) {
        
            gc.setStroke(Color.BLUE);
            gc.strokeOval(allx.get(i)*50-5, ally.get(i)*50-5, 9, 9);
            gc.fillText("("+allx.get(i)+","+ally.get(i)+")", allx.get(i)*50+8, ally.get(i)*50+15);
      
       }
        
       for(int j = 0; j<xvalues.size(); j++) {
       
            gc.setFill(Color.RED);
            gc.fillOval(xvalues.get(j)*50-5, yvalues.get(j)*50-5, 10, 10);
    
        }
    }
   
   

    public static void main(String args[]) {
    
        int maxPoints = 70;
       
        double[] xVal = new double[maxPoints+1];
        double[] yVal = new double[maxPoints+1];
        
        int pointCount = loadPoints(maxPoints, xVal, yVal);
        
        System.out.println("The Total Number of Points: ");
        System.out.println(pointCount);
        
        System.out.println("All Points: ");
        for(int x=0; x< pointCount; x++) {
            System.out.println((x+1) + ". " + "(" + xVal[x] + "," + yVal[x] + ")");
            allx.add(xVal[x]);
            ally.add(yVal[x]);
        }
        
        
        
        if(checkDuplicates(pointCount, xVal, yVal)) return;
        
        computeConvexHull(pointCount, xVal, yVal);
        

        
        launch(args); 
    }

    static int loadPoints(int maxPoints, double[] xVal, double[] yVal) {
       
       Boolean readNegative = false;
       Boolean maxReached = false;
       int myCounter = 0;
       double myInput = 0;
       int oddevencounter = 0;
       int yValcounter = 0;
       int xValcounter = 0;
       
       Scanner sc = new Scanner(System.in);
       System.out.println("Enter an x point: ");
       while(readNegative == false && maxReached == false) {
        
        myInput = sc.nextDouble();
        
        if(myCounter >= 2*maxPoints) {
            maxReached = true;
            System.out.println("Maximum Capacity Of Array Has Been Reached!");
            
        }
        else if(myInput < 0) {
            readNegative = true;
        }
        else {
        
             if(oddevencounter % 2 == 0) {
                xVal[xValcounter] = myInput;
                xValcounter++;
                System.out.println("Enter an y point: ");
             }
             else {
                yVal[yValcounter] = myInput;
                yValcounter++;
                System.out.println("Enter an x point: ");
             }
             oddevencounter++;
        }
        myCounter++;
       }
        if(myCounter%2 != 0) {
            xValcounter--;
            myCounter--;
        }
        return yValcounter;
    }
    
    
    
    static boolean checkDuplicates(int pointCount, double xVal[], double yVal[]) {

        for(int i = pointCount; i>=1; i--) {
            for(int j=0; j<i; j++) {
            
                if(xVal[i] == xVal[j] && yVal[i] == yVal[j] && i!=j) {
                
                    System.out.println("Duplicate points found!");
                    return true;
            
                }
            }
        }
    
        return false;
    
    }
    
    
    
    static void computeConvexHull(int pointCount, double xVal[], double yVal[]) {
        double m, c;
        
        int above = 0;
        int below = 0;
        
        System.out.println("The Following Edges Make Up The Convex Hull: ");
        
        for(int i = pointCount-1; i>=0; i--) {
            for(int j=0; j<i; j++) {
                
                m = (yVal[i]-yVal[j])/(xVal[i]-xVal[j]);
                c = yVal[i] - (m)*(xVal[i]);
                
                above = 0;
                below = 0;
                
                for(int w = 0; w<=pointCount-1; w++) {
                
                    if((m == Double.POSITIVE_INFINITY) || (m == Double.NEGATIVE_INFINITY)){
                    
                        if(xVal[w] < xVal[i]) {
                            below +=1;
                        }
                        else if(xVal[w] > xVal[i]) {
                            above +=1;
                        }
                    }
                    
                    else{
                        if(yVal[w] < m*(xVal[w]) + c) {
                            below +=1;
                        }
                        else if(yVal[w] > m*(xVal[w]) + c) {
                            above +=1;
                        }
                    }
                }
                if((above > 0 && below == 0) || (below >0 && above == 0)) {
                    System.out.println("Edge (" + xVal[i] + "," + yVal[i] + ")" + " --" + " (" + xVal[j] + "," + yVal[j] + ")");
                    xvalues.add(xVal[i]);
                    yvalues.add(yVal[i]);
                    xvalues.add(xVal[j]);
                    yvalues.add(yVal[j]);
                }
            }
        }
        
        
        
        
    }
    
}

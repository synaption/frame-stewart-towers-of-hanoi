import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

class HanoiObj {

    private int start;
    private int end;
    private int towersNum;
    private int diskNum;
    private Stack<Integer> towersStack;

    public HanoiObj ( int start, int end, int diskNum ) {
        this.start = start;
        this.end = end;
        this.diskNum = diskNum;

        init ();
    }

    private void init () {
        towersNum = end;
        towersStack = new Stack<> ();

        //make all towers with push
        for(int i=towersNum;i>=1;i--)
        if(i!=start && i!=end)
        towersStack.push(i);
        //make start tower be on top
        //and then destination tower
        towersStack.push(end);
        towersStack.push(start);
    }
    /**A recursive implementation of the the Frame-Stewart algorithm
    *
    * @param S is the stack containing the towers labels (integer values)
    *          where source tower is TOS and destination towers are the element below TOS
    * @param n is the number of disks to be moved
    * @param r is the number of towers to be used in the movement
    * @return
    */
    private ArrayList<Point> hanoi ( Stack<Integer> S, int n, int r ) {

        //Base call to move a single disk
        //peek is a function that gets the value from the top of stack
        //pop is a function that deletes a value from the top of the stack
        if(n == 1){
            ArrayList<Point> v= new ArrayList<Point>();
            Point P=new Point();
            int s1 =S.peek();S.pop();
            int s2 =S.peek();S.pop();
            P.x=s1;P.y=s2;
            v.add(P);
	     return v;//print out here for moves
        }

        int k;//choose k disks to be moved at first
        if(r<4)
            k=n-1;
        else
        	  //k= (int)((int)n/2);
            k= (int)((float)n/2.0);

        ///////////////////////////////////////
        /*Transfer k disks to a tower other than source or destination
        This done by modifying the satck such that the tower on the top
        is not src or destination */
        ArrayList<Point> firstStepMovements ;
        Stack <Integer>sTowersFirstStep =  (Stack <Integer>) S.clone();
        int srcTower = sTowersFirstStep.peek();sTowersFirstStep.pop();//get the source
        int destTower= sTowersFirstStep.peek();sTowersFirstStep.pop();//get the destination
        int otherTower = sTowersFirstStep.peek();sTowersFirstStep.pop();//get the other tower to be new destination
        sTowersFirstStep.push(destTower);sTowersFirstStep.push(otherTower);sTowersFirstStep.push(srcTower);
        firstStepMovements = (ArrayList<Point>) (hanoi(sTowersFirstStep,k,r)).clone();
        //////////////////////////////////////////////////////
        /*Transfer n-k disks from the source to destination
        without disturbing the tower that is already contains k */
        ArrayList<Point> secondStepMovements ;
        Stack <Integer>sTowersSecondStep =  (Stack <Integer>) S.clone();
        srcTower = sTowersSecondStep.peek();sTowersSecondStep.pop();
        destTower = sTowersSecondStep.peek();sTowersSecondStep.pop();sTowersSecondStep.pop();
        sTowersSecondStep.push(destTower);sTowersSecondStep.push(srcTower);
        secondStepMovements = (ArrayList<Point>) (hanoi(sTowersSecondStep,n-k,r-1)).clone();
        //////////////////////////////////////////////////////
        /*Transfer k disks to the destination by making it the TOS in the otherTower [the new source[
        and making the tower under the TOS is the destination */
        ArrayList<Point> thirdStepMovements ;
        Stack <Integer>sTowersThirdStep = (Stack <Integer>) S.clone();
        srcTower = sTowersThirdStep.peek();sTowersThirdStep.pop();
        destTower = sTowersThirdStep.peek();sTowersThirdStep.pop();
        otherTower  = sTowersThirdStep.peek();sTowersThirdStep.pop();
        sTowersThirdStep.push(srcTower);sTowersThirdStep.push(destTower);sTowersThirdStep.push(otherTower);
        thirdStepMovements = (ArrayList<Point>) (hanoi(sTowersThirdStep,k,r)).clone();
        //////////////////////////////////////////////////////
        //Update the firstStepMovements top include movements in the second and third steps
        for (Point pointsInCenter : secondStepMovements) {
            firstStepMovements.add(pointsInCenter);
        }
         for(Point pointsInRight : thirdStepMovements)
            firstStepMovements.add(pointsInRight);

        return firstStepMovements;//Return all movements
}

    public void display () {
        //Call the recursive function
        ArrayList<Point> hanoiSteps = hanoi(towersStack,diskNum,towersNum);
        //display the all movements needed to solve the towers of hanoi
        for(Point hanoiPoint : hanoiSteps)
            System.out.println(hanoiPoint.x + "->"+hanoiPoint.y);
        }
}

public class TowerOfHanoiStewart {
    public static void main ( String[] args ) {
    											   //change values here
        HanoiObj obj = new HanoiObj ( 1, 5, 21 ); //start, end, diskNum
        obj.display ();

    }
}

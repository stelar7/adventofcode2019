package day25;

import utils.*;

import java.util.Scanner;

public class One
{
    
    /*
        https://csacademy.com/app/graph_editor/
        
        hullbreach stables east
        stables storage south
        storage arcade south
        hullbreach observatory south
        observatory engineering east
        observatory sickbay west
        hullbreach sciencelab west
        sciencelab hotchocofountain north
        hotchocofountain giftwrappingcenter north
        giftwrappingcenter hallway west
        hotchocofountain passages west
        passages corridor north
        passages crewquarters south
        sciencelab holodeck west
        holodeck warpdrive west
        warpdrive navigation south
        navigation securitycheckpoint south
        holodeck kitchen south
     */
    
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day25.input");
        Scanner        s       = new Scanner(System.in);
        
        while (true)
        {
            machine.queueInput();
            System.out.println(Utils.fromASCII(machine.outputList()));
            machine.input(s.nextLine());
            machine.queueOutput();
        }
    }
}

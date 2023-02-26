package model;

import tools.GamerAccount;
import tools.GamerMenager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class GameMap {
    public int[][] distancemap;
    Random rand;

    public int maprows =0;
    public int mapcolumns =0;
    private char[][] charmap;
    int number;
    Possition[][] nearestpossition;

    public ArrayList<Treassure> treasures = new ArrayList<>();
    public GameMap() throws FileNotFoundException {
        scanfromfile();
        rand = new Random();
        number=0;
    }

    public void emptyMap(int mapcolumns,int maprows){
       this.mapcolumns =mapcolumns;
        this.maprows =maprows;

        charmap = new char[mapcolumns][maprows];
        for(int row = 0; row<maprows; row++){
            putElemenet(0,row,'W');
            putElemenet(mapcolumns-1,row,'W');
        }
        for(int column = 0; column<mapcolumns; column++){
            putElemenet(column,0,'W');
            putElemenet(column,maprows-1,'W');
        }
        for(int row=1; row<maprows-1;row++){
            for(int column=1; column<mapcolumns-1;column++){
                putElemenet(column,row,'U');
            }
        }
    }

    public void lightmap(int column, int row, String surround){
        putElemenet(column-1,row-1,surround.charAt(1));
        putElemenet(column,row-1,surround.charAt(2));
        putElemenet(column+1,row-1,surround.charAt(3));
        putElemenet(column-1,row,surround.charAt(4));
        putElemenet(column,row,surround.charAt(5));
        putElemenet(column+1,row,surround.charAt(6));
        putElemenet(column-1,row+1,surround.charAt(7));
        putElemenet(column,row+1,surround.charAt(8));
        putElemenet(column+1,row+1,surround.charAt(9));
    }

    public ArrayList<String> read(URL url) {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader ReadF = null;
            ReadF = new BufferedReader(new InputStreamReader(url.openStream()));
            String numstring = ReadF.readLine();
            try {
                while (numstring != null) {
                    list.add(numstring);
                    numstring = ReadF.readLine();
                }
            } catch (Exception er) {
                return null;
            }
            ReadF.close();
        }catch (Exception x){
            return null;
        }
        System.out.println(list.size());
        return list;
    }
    private void scanfromfile() throws FileNotFoundException {
        URL url = GameMap.class.getResource("map.txt");
        ArrayList<String> data = read(url);
        maprows = data.size();
        mapcolumns =data.get(0).length();
        charmap=new char[mapcolumns][maprows];
        for(int row = 0; row< maprows; row++){
            for(int column = 0; column< mapcolumns; column++){
                charmap[column][row]=data.get(row).charAt(column);
            }
        }
        rand = new Random();
        for(int i =0; i<20; i++){
            Possition possition = putgamer();
            putElemenet(possition.column,possition.row,'S');
            Treassure treassure = new Treassure(possition, rand.nextInt(1,5),rand.nextInt(1,5) );
            treasures.add(treassure);
        }
    }

    public Treassure getTreassuresAtPossition(int column, int row){
        for(Treassure t: treasures){
            if(t.possition.column==column&&t.possition.row==row)
                return t;
        }
        return null;
    }
    public char getElement(int column, int row){
        return charmap[column][row];
    }
    public void putElemenet(int column, int row, char element){
        charmap[column][row]=element;
    }
    public Possition putgamer(){
        int row;
        int column;
        do {
            row = rand.nextInt(1, maprows);
            column = rand.nextInt(1, mapcolumns);
        } while(getElement(column,row)!=' ');
        return new Possition(column,row);
    }

    public String explore(Possition actualpossition, GamerMenager gamerMenager){
        int column = actualpossition.column;
        int row = actualpossition.row;
        ArrayList<Possition> neighbours = createNeighbourswithcenter(column, row);
        String possitionstosend="";
        ArrayList<String> TreassureStrings = new ArrayList<>();
        for(int i=0;i <=8; i++){
            int col = neighbours.get(i).column;
            int ro = neighbours.get(i).row;
            char x = getElement(col,ro);

            for(String s: gamerMenager.LoginBase.keySet()){
                GamerAccount gamerAccounttemp = gamerMenager.LoginBase.get(s);
                if(col==gamerAccounttemp.possition.column&&ro==gamerAccounttemp.possition.row){
                    x = 'P';
                }
            }
            if(getElement(col, ro)=='S'){
                Treassure treassure = getTreassuresAtPossition(col,ro);
                if(treassure!=null){
                    String treassurestr = "treassure;"+col+";"+ro+";"+treassure.weight+";"+treassure.value;

                    TreassureStrings.add(treassurestr);
                }
            }
            possitionstosend = possitionstosend + x;

        }
        possitionstosend="\""+possitionstosend+"\"";
        for(String s: TreassureStrings){
            possitionstosend=possitionstosend+"~"+s;
        }
        return possitionstosend;
    }
    public ArrayList<Possition> createNeighbourswithcenter(int column, int row){
        ArrayList<Possition> neighbours = new ArrayList<>();
        neighbours.add(new Possition(column-1,row-1));
        neighbours.add(new Possition(column,row-1));
        neighbours.add(new Possition(column+1,row-1));
        neighbours.add(new Possition(column-1,row));
        neighbours.add(new Possition(column,row));
        neighbours.add(new Possition(column+1,row));
        neighbours.add(new Possition(column-1,row+1));
        neighbours.add(new Possition(column,row+1));
        neighbours.add(new Possition(column+1,row+1));
        return  neighbours;
    }

    public void shortestpath(Possition startPossition){
        distancemap = new int[mapcolumns][maprows];
        nearestpossition = new Possition[mapcolumns][maprows];

        for(int row =0; row<maprows;row++){
            for(int column=0; column<mapcolumns;column++){
                distancemap[column][row]=10000;
                nearestpossition[column][row]=new Possition(-1,-1);
            }
        }

        distancemap[startPossition.column][startPossition.row]=0;
        ArrayList<Possition> queue = new ArrayList<>();
        queue.add(startPossition);
        while(queue.size()!=0){
            Possition temppossition = queue.get(0);
            queue.remove(0);
            ArrayList<Possition> neighbours = createNeighbours(temppossition.column, temppossition.row);
            for(Possition pos: neighbours){
                if(getElement(pos.column, pos.row)=='W')continue;
                if(getElement(pos.column, pos.row)=='P')continue;
                if(distancemap[pos.column][pos.row]>distancemap[temppossition.column][temppossition.row]+1){
                    distancemap[pos.column][pos.row]=distancemap[temppossition.column][temppossition.row]+1;
                    nearestpossition[pos.column][pos.row]=temppossition;
                    if(getElement(pos.column, pos.row)!='U')
                        queue.add(pos);
                }
            }
        }
    }

    public ArrayList<Possition> createNeighbours(int column, int row){
        ArrayList<Possition> neighbours = new ArrayList<>();
        neighbours.add(new Possition(column-1,row+1));
        neighbours.add(new Possition(column,row+1));
        neighbours.add(new Possition(column+1,row+1));
        neighbours.add(new Possition(column-1,row));
        neighbours.add(new Possition(column+1,row));
        neighbours.add(new Possition(column-1,row-1));
        neighbours.add(new Possition(column,row-1));
        neighbours.add(new Possition(column+1,row-1));
        return  neighbours;
    }

    public void gamerCleaner(){
        for(int column = 0 ; column<mapcolumns; column++){
            for(int row = 0; row<maprows; row++){
                if(getElement(column,row)=='P') putElemenet(column,row,' ');
            }
        }
    }

    public Possition findnearest(Possition gamerPossition, char target){
        Possition startPossition = new Possition(gamerPossition.column,gamerPossition.row);
        ArrayList<Possition> possibilities = new ArrayList<>();
        int min = 10000;
        Possition minpossition=null;
        for(int row = 0; row<maprows; row++){
            for(int column = 0; column<mapcolumns;column++){
                if(getElement(column,row)==target)
                    if(distancemap[column][row]!=-1){
                        if(distancemap[column][row]<min){
                            min=distancemap[column][row];
                            minpossition=new Possition(column,row);
                        }
                    }
            }
        }
        if(minpossition==null){
            return null;
        }
        Possition toreturn=minpossition;
        while((minpossition.column!=startPossition.column)||(minpossition.row!=startPossition.row)){
            toreturn=minpossition;
            minpossition=nearestpossition[minpossition.column][minpossition.row];

        }
        return toreturn;
    }


    public Possition findnetreassures(Possition gamerPossition){
        Possition startPossition = new Possition(gamerPossition.column,gamerPossition.row);
        float minration = 10000;
        Possition minpossition=null;
        for(int row = 0; row<maprows; row++){
            for(int column = 0; column<mapcolumns;column++){
                if(getElement(column,row)=='S')
                    if(distancemap[column][row]!=-1){
                        for(Treassure t :treasures){
                            System.out.println(t.possition.column+" "+t.possition.row+" "+t.weight+" "+t.value);
                        }
                        Treassure treassure = getTreassuresAtPossition(column,row);
                        float ratio = treassure.value/(treassure.weight+distancemap[column][row]);
                        if(ratio<minration){
                            minration=ratio;
                            minpossition=new Possition(column,row);
                        }
                    }
            }
        }
        if(minpossition==null){
            return null;
        }
        Possition toreturn=minpossition;
        while((minpossition.column!=startPossition.column)||(minpossition.row!=startPossition.row)){
            toreturn=minpossition;
            minpossition=nearestpossition[minpossition.column][minpossition.row];
        }
        return toreturn;
    }

    public void addTreassure(Treassure treassure){
        if(getTreassuresAtPossition(treassure.possition.column,treassure.possition.row)!=null){
            return;
        }

        treasures.add(treassure);
    }
}

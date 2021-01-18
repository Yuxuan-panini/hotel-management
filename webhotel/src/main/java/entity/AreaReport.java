package entity;

import java.util.ArrayList;
import java.util.List;

public class AreaReport {
    private class Each{
        public String area;
        public float income;

        public Each(String s,float f){
            area=s;income=f;
        }
    }

    List<Each> areareportList;

    public AreaReport(){
        areareportList=new ArrayList<>();
    }

    public void add(String ar,float f){
        Each each=new Each(ar,f);
        areareportList.add(each);
    }
}

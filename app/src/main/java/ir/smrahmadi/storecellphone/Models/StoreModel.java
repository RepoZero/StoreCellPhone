package ir.smrahmadi.storecellphone.Models;

/**
 * Created by cloner on 12/23/17.
 */

public class StoreModel {

    String name;
    int number;

    public StoreModel(String name,int number){
        this.name = name;
        this.number = number;

    }
    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {

        return number;
    }
}

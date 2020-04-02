/*
 * File: HashTable.java
 * Author: David Hui
 * Description: Allows a user to add to, remove from, get from, check the presence of an element in, get the load of, set the load of, and set the max load of a hash table.
 */
import java.util.*;
public class HashTable<T> {
    private int numElements; // stores the number of elements
    private ArrayList<LinkedList<T>> table; // the table that holds the
    private double maxLoad = 0.75; // keeps tracks of the max load

    public HashTable(){
        // set num elements to zero and empty the table
        numElements = 0;
        emptyTable(10);
    }

    /**
     * Gets the spot in the table where the val should be
     * @param val the value to get the spot of
     * @return the spot in the table
     */
    private int getSpot(T val){
        return Math.abs(val.hashCode() % table.size());
    }

    /**
     * Create a new table with num spots
     * @param num the number of spots in the new table
     */
    public void emptyTable(int num){
        // create a new table and fill it
        table = new ArrayList<LinkedList<T>>();
        for(int i = 0; i < num; i++){
            table.add(null);
        }
    }

    /**
     * Add a value to the table
     * @param val the value to add to the table
     */
    public void add(T val){
        int spot = getSpot(val); // get the table index
        if(table.get(spot) == null){ // create a LList if there isn't one there already
            table.set(spot, new LinkedList<T>());
        }
        table.get(spot).add(val); // add the value to the LList
        numElements++; // increase the numElements
        // resize if necessary
        if(getLoad() >= maxLoad){
            resize();
        }
    }

    /**
     * Resizes the table
     * @see HashTable#resize(int) resize
     */
    public void resize(){
        // resizes a default of 10x bigger
        resize(table.size() * 10);
	}

    /**
     * Resizes the table
     * @param tableSize the new size of the table
     */
	public void resize(int tableSize){
	    // keep a reference to the current table
        ArrayList<LinkedList<T>> tmp = table;

        // reset the table
        numElements = 0;
        emptyTable(tableSize);

        // fill the table again
        for(LinkedList<T> list : tmp){
            if(list != null){
                for(T val : list){
                    add(val);
                }
            }
        }
    }

    /**
     * Remove the value from the table
     * @param val the value to remove
     */
	public void remove(T val){
        int spot = getSpot(val); // get the spot
        // make sure it's valid
        if(table.get(spot) == null){
            return;
        }

        // if remove was successful (changed LList), decrease numElements
        if(table.get(spot).remove(val)){
            this.numElements--;
        }
        // set spot to null if it's the last one in the LList
        if(table.get(spot).size() == 0){
            table.set(spot, null);
        }
    }

    /**
     * Returns whether val is present in the table
     * @param val the value to check
     * @return whether val is present in the table
     */
    public boolean contains(T val){
        int spot = getSpot(val);
        // check if spot is not null and value is in the LList at that position
        return table.get(spot) != null && table.get(spot).contains(val);
    }

    /**
     * Returns the element that is equal to value
     * @param value the value to check for
     * @return the element that is stored in the table
     */
    public T get(T value){
        int spot = getSpot(value);
        // check spot exists
        if(table.get(spot) != null){
            // Check within LList for the match of the key/value
            for(T possible : table.get(spot)){
                if(value.equals(possible)){
                    return possible;
                }
            }
        }
        return null;
    }

    /**
     * Returns the load of the table
     * @return the load of the table
     */
    public double getLoad(){
        return (double)this.numElements/this.table.size();
    }

    /**
     * Sets the maximum load of the table
     * @param percent the maximum load of the table, between 0.1 and 0.8
     */
    public void setMaxLoad(double percent){
        // make sure it is within bounds
        if(percent < 0.1 || percent > 0.8){
            return;
        }
        this.maxLoad = percent;
        // check resize
        if(this.getLoad() >= this.maxLoad){
            resize();
        }
    }

    /**
     * Forces the table to be the given load
     * @param percent the desired load of the table, between 0.1 and 0.8, and not greater than the maximum load
     */
    public void setLoad(double percent){
        // check if percent is within bounds and also if the table has anything in it
        if(percent < 0.1 || percent > 0.8 || percent > this.maxLoad || numElements == 0){
            return;
        }
        // get the new table size
        int newTableSize = (int)Math.ceil(numElements/percent);
        resize(newTableSize);

    }

    /**
     * Returns the HashTable as an ArrayList
     * @return the HashTable as an ArrayList
     */
    public ArrayList<T> toArray(){
        // create the return list
        ArrayList<T> ret = new ArrayList<>();

        // flatten the table
        for (LinkedList<T> l : this.table) {
            if (l != null) {
                for (T ele : l) {
                    if (ele != null) {
                        ret.add(ele);
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public String toString(){
        String ans = "";
        // go through the table and flatten it to a string
        for(LinkedList<T> list : table){ // lucky charms
            if(list != null){
                for(T val : list){
                    ans+= val + ", ";
                }
            }
        }
        if(numElements > 0){
            ans = ans.substring(0, ans.length() - 2);
        }
        return "<" + ans + ">";
    }
}

// Load: #elements/#spots
// Efficient as long as load < 70%
import java.util.*;
class HashTable<T> {
    private int numElements;
    private ArrayList<LinkedList<T>> table;
    private double maxLoad = 0.75;
    public HashTable(){
        numElements = 0;
        emptyTable(10);
    }
    private int getSpot(T val){
        return Math.abs(val.hashCode() % table.size());
    }
    public void emptyTable(int num){
        table = new ArrayList<LinkedList<T>>();
        for(int i = 0; i < num; i++){
            table.add(null);
        }
    }
    public void add(T val){
        int spot = getSpot(val);
        if(table.get(spot) == null){
            table.set(spot, new LinkedList<T>());
        }
        table.get(spot).add(val);
        numElements++;
        if((double)numElements/table.size() >= maxLoad){
            resize();
        }
    }
    public void resize(){
        resize(table.size() * 10);
	}
	public void resize(int tableSize){
        ArrayList<LinkedList<T>> tmp = table;
        numElements = 0;
        emptyTable(tableSize);
        for(LinkedList<T> list : tmp){
            if(list != null){
                for(T val : list){
                    add(val);
                }
            }
        }
    }
	public void remove(T val){
        int spot = getSpot(val);
        if(table.get(spot) == null){
            return;
        }
        if(table.get(spot).remove(val)){
            this.numElements -= 1;
        }
        if(table.get(spot).size() == 0){
            table.set(spot, null);
        }
    }
    public boolean contains(T val){
        int spot = getSpot(val);
        return table.get(spot) != null && table.get(spot).contains(val);
    }
    public double getLoad(){
        return (double)this.numElements/this.table.size();
    }
    public void setMaxLoad(double percent){
        if(percent < 0.1 || percent > 0.8){
            return;
        }
        this.maxLoad = percent;
        if(this.getLoad() >= this.maxLoad){
            resize();
        }
    }
    public void setLoad(double percent){
        if(percent < 0.1 || percent > 0.8 || percent > this.maxLoad){
            return;
        }
        int newTableSize = (int)Math.ceil(numElements/percent);
        resize(newTableSize);

    }
    public ArrayList<T> toArray(){
        ArrayList<T> ret = new ArrayList<>();
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
        for(LinkedList<T> list : table){
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

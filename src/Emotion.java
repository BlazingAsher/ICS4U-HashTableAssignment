/*
 * File: Emotion.java
 * Author: David Hui
 * Description: Stores emotion data for a specific point
 */
public class Emotion {
    public final int x,y; // location
    private int lh,hs,eb; // emotion values
    private int counter = 1; // number of emotion sets at this location
    public Emotion(int x, int y, int lh, int hs, int eb){
        this.x = x;
        this.y = y;
        this.lh = lh;
        this.hs = hs;
        this.eb = eb;
    }

    /**
     * Scales emotion data to RGB value
     * @param val the emotion value
     * @return the RGB value
     */
    public int mapToColorScale(double val){
        return (int) Math.round(((val + 100)/200) * 255);
    }

    /**
     * Add an emotion entry to this location
     * @param lh the love/hate value
     * @param hs the happiness/sadness value
     * @param eb the excitement/boredom value
     */
    public void addEntry(int lh, int hs, int eb){
        this.lh += lh;
        this.hs += hs;
        this.eb += eb;
        counter++;
    }

    /**
     * Returns the R value at this location
     * @return the R value at this location
     */
    public int getR(){
        return mapToColorScale((double) lh/counter);
    }

    /**
     * Returns the G value at this location
     * @return the G value at this location
     */
    public int getG(){
        return mapToColorScale((double) hs/counter);
    }

    /**
     * Returns the B value at this location
     * @return the B value at this location
     */
    public int getB(){
        return mapToColorScale((double) eb/counter);
    }

    @Override
    public boolean equals(Object o){
        if(o == this){ // instance of itself
            return true;
        }

        // null or not of the same class
        if(o == null || o.getClass() != this.getClass()){
            return false;
        }

        // cast the Object to an Emotion so that we have access to its fields
        Emotion other = (Emotion) o;

        // Determining equality based on location
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode(){
        return this.x*647 + this.y;
    }
}

package sample;


public class Cloth {

    public Cloth(int clothID, String clothType) {

        this.clothID = clothID;
        this.clothType = clothType;
    }

    public Cloth(String clothType) {
        this.clothType = clothType;
    }

    private String clothType;




    public Cloth(int clothID) {
        this.clothID = clothID;
    }

    public String getClothType() {
        return clothType;
    }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }

    public int getClothID() {
        return clothID;
    }

    public void setClothID(int clothID) {
        this.clothID = clothID;
    }

    int clothID;


    @Override
    public String toString() {
        return "Cloth{" +
                "clothType='" + clothType + '\'' +
                ", clothID=" + clothID +
                '}';
    }
}

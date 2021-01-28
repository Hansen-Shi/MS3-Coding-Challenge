package csvParser;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;

/**
 * A Java bean class to use in conjunction with opencsv to process data.
 */
public class Records implements Serializable {
    //Private fields matching the csv column headers
    @CsvBindByName
    private String A;
    @CsvBindByName
    private String B;
    @CsvBindByName
    private String C;
    @CsvBindByName
    private String D;
    @CsvBindByName
    private String E;
    @CsvBindByName
    private String F;
    @CsvBindByName
    private String G;
    @CsvBindByName
    private String H;
    @CsvBindByName
    private String I;
    @CsvBindByName
    private String J;

    //All the setters and getters
    public void setA(String s){ this.A = s; }
    public String getA(){ return this.A; }

    public void setB(String s){ this.B = s; }
    public String getB(){ return this.B; }

    public void setC(String s){ this.C = s; }
    public String getC(){ return this.C; }

    public void setD(String s){ this.D = s; }
    public String getD(){ return this.D; }

    public void setE(String s){ this.E = s; }
    public String getE(){ return this.E; }

    public void setF(String s){ this.F = s; }
    public String getF(){ return this.F; }

    public void setG(String s){ this.G = s; }
    public String getG(){ return this.G; }

    public void setH(String s){ this.H = s; }
    public String getH(){ return this.H; }

    public void setI(String s){ this.I = s; }
    public String getI(){ return this.I; }

    public void setJ(String s){ this.J = s; }
    public String getJ(){ return this.J; }

    //Overwritten toString to easily verify the data
    @Override
    public String toString() {
        return "Column A=" + A + ", Column B=" +
                D + ", Column E=" + E + ", Column F=" +
                F + ", Column G=" + G + ", Column H=" +
                B + ", Column C=" + C + ", Column D=" +
                H + ", Column I=" + I + ", Column J=" + J;
    }

    /**
     * Method checks if any of the fields are set to a blank space.
     * A blank would imply that the row is missing a value and the record is invalid.
     * @return - returns true if the record has no blank spaces for any field, false otherwise.
     */
    public boolean validRecord() {
        return !this.A.equals("") && !this.B.equals("") && !this.C.equals("")
                && !this.D.equals("") && !this.E.equals("") && !this.F.equals("")
                && !this.G.equals("") && !this.H.equals("") && !this.I.equals("")
                && !this.J.equals("");
    }
}

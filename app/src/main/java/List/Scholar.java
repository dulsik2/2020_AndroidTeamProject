package List;

public class Scholar {
    private String title;
    private String tuitionFee;
    private String income;
    private String region;

    public Scholar(){}
    public Scholar(String t, String fee, String in, String re){
        title = t;
        tuitionFee = fee;
        income = in;
        region = re;
    }

    public String getTitle(){return title;}
    public String getTuitionFee(){return tuitionFee;}
    public String getIncome(){return income;}
    public String getRegion(){return region;}
}

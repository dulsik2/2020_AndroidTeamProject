package List;

;

public class Scholar {
    private String title;
    private String tuitionFee;
    private String income;
    private String region;
    private String startDate;
    private String endDate;

    public Scholar(){}
    public Scholar(String t, String fee, String in, String re, String s, String e){
        title = t;
        tuitionFee = fee;
        income = in;
        region = re;
        startDate = s;
        endDate = e;
    }

    public String getTitle(){return title;}
    public String getTuitionFee(){return tuitionFee;}
    public String getIncome(){return income;}
    public String getRegion(){return region;}
    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
}

package List;

// ***** 전체_이소영 *****
public class Scholar {
    private String title;
    private String tuitionFee;
    private String income;
    private String region;
    private String startDate;
    private String endDate;
    private String link;

    public Scholar(){}   // 문서를 클래스로 변환하기위해 기본 생성자 필요
    public Scholar(String t, String fee, String in, String re, String s, String e, String l){
        title = t;
        tuitionFee = fee;
        income = in;
        region = re;
        startDate = s;
        endDate = e;
        link = l;
    }

    // 게터 메소드
    public String getTitle(){return title;}
    public String getTuitionFee(){return tuitionFee;}
    public String getIncome(){return income;}
    public String getRegion(){return region;}
    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
    public String getLink(){return link;}
}

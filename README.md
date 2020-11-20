# Mobile Programming Team Project

### 20191644 이연주
---

**목차**

- 프로젝트 설명
   - 목표
   - 초기 주제
      - 주제 설명
      - 변경한 이유

- 코드 구현
  - 전체 코드 요약
  - AndroidManifest.xml
  - filterLst.class
  - Main
    - activity_main.xml
    - button_state.xml
    - MainActivity.class
  - Popup(Filt)
    - activity_popup.xml
    - Filter.class
  - 그 외(다른 팀원 구현)

- 코드 실행
    - 간단한 기능
    - 검색조건 선택하기
    - 검색조건 띄우기
    - 캘린더 앱 연동하기
    - 경고창 확인

 - 마무리

---

## 1. 프로젝트 설명

해당 프로젝트는 **메인화면, 장학금 조건 선택화면, 장학금 필터링** 이렇게 세가지 액티비티로 구현되어 있다.

첫번째 액티비티인 **메인 액티비티**에선, 장학금 조건을 선택하는 액티비티와 조건에 따라 필터링 하는 액티비티로 넘어가는 버튼을 클릭하게 되어있다. 또한 장학금 조건을 선택하면 조건을 화면에 띄워주도록 한다.

두번째 액티비티인 **장학금 조건 선택 액티비티**에선, 장학금의 조건을 **등록금 내/외/내 + 외, 소득분위(장학재단 기준), 지역, 기간** 에 따라 선택하도록 되어있다.

마지막 세번째 액티비티인 **장학금 필터링 액티비티**에선, 두번째 액티비티에서 선택한 조건에 따라 DB를 이용하여 정보를 가져오도록 하고 그 중에서 선택한 장학금에 대한 인터넷 링크 연결과, 캘린더 앱에 일정을 추가하도록 하였다.

#### - 목표

프로젝트 설명해서 언급했던 것과 같이 이 앱의 주요기능은 다음과 같다.
  * 자신이 궁금한 조건에 대해 장학금을 필터링 한다.
  * 자세히 보기 위해 인터넷 링크 연결로 확인할 수 있게 한다.
  * 기본 캘린더앱과 연동하여 일정을 추가할 수 있도록 한다.

#### - 초기주제

###### 주제 설명

초기에 선택했던 주제에는 `국민대학교 e-campus`와 연동하여 자신의 과제를 가져와서 `TodoList`처럼 보여주고 `달력형식`으로 깔끔하게 정리하여 보여주는 프로젝트를 구상하였다.

현재 존재하는 홈페이지와 앱에서는 `동영상, 과제`등이 결합되어 시각적으로 과제를 확인하기에는 큰 어려움이 있고 기간에 대해서 명확하게 알기 어려웠다.

따라서 과제를 가져와서 `TodoList, 캘린더, 푸쉬알림`등을 통해 알리는 앱을 만들고자 하였다.

###### 변경한 이유

변경한 이유는 다음과 같다

    - 국민대학교 e-campus에 대한 공공 API가 제공되지 않아 로그인에 대해 접근하기 어렵다.
    - 웹포털이 새로 개편되고 있어, 구현하기에 적합하지 못하다.

## 2. 코드 구현

 전체적인 소스파일에는 `AndroidManifest.xml`를

 메인화면을 이루는 `MainActivity.class`와 `activity_main.xml`,
 장학금의 조건을 선택하는 `Filter.class`와 `activity_popup.xml`,
 장학금을 조건에 따라 필터링하는 `FilteredList.class`와 `activity_filteredlist.xml`가 있다.

 또한, 장학금의 검색 조건 정보들을 담는 `filterLst`라는 객체의 `filterLst.class`와  소득분위와 지역은 Spinner로 구현하였기 때문에 그 String array정보를 담은 `arrays.xml`파일이 포함된다.

 그 외에도 전체적인 레이아웃 스타일을 변경하였기 때문에 `styles.xml`을 수정하였으며, 버튼의 스타일을 변경하기 위해 `button_state.xml`파일을 구현하였다.

 장학금을 필터링하고 보여줄 `ListView`를 위해 `ListViewAdaptor.clss`와 DB에서 필요한 장학금의 정보를 담은 객체인 `Scholar`에 대한 `Scholar.clss`도 함께 구현되었는데, 이 부분에 대해서는 같이한 팀원이 구현하였다.

 ##### - AndroidManifest.xml

 ###### *(부분생략)*

  ```xml
  .
  .

    <uses-permission android:name="android.permission.READ_CALENDAR"/> //(1)
    <uses-permission android:name="android.permission.WRITE_CALENDAR"/>
.
.

  <activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
  </activity>
  <activity android:name=".Filter" android:theme="@style/Theme.MyDialog"/> <!-- 팝업창을 띄우는 Theme로 설정 -->
  <activity
    android:name=".FilteredList"
    tools:ignore="DuplicateActivity" />
 ```

(1)로 표시된 부분은 후에 기본캘린더와의 연동을 위해 추가한 허가 구문이다.

 AndroidManifest.xml파일에 3개의 액티비티가 생성되어있음을 확인할 수 있다.
 `Filter.class`에 대한 액티비티는 팝업창을 띄우는 Theme로 설정하였다.
 세번째 액티비티인 `FilteredList.class`에 대한 액티비티는 `ignore`을 사용하여
 ()()

 ##### - filterLst.class

 이 클래스는 두번째 액티비티와 세번째 액티비티에서 사용되며 **장학금 선택 조건**에 대한 정보를 담고있다.

 두번째 액티비티(Filter.class, activity_popup)에서 선택한 조건들을 filterLst 객체에 담아서 여러 액티비티에서 사용하도록 한다.

 ###### *#filterLst.class*

```java
public class filterLst implements Serializable {
    private ArrayList<String> tuitionFeeLst; //등록금 내, 외, 내+외
    private String income; //소득분위
    private String region; //지역
    private ArrayList<String> spanLst; //시작기간, 종료기간

    //default
    public filterLst(){
        tuitionFeeLst = new ArrayList<String>();
        income = "";
        region = "";
        spanLst = new ArrayList<String>();
    }

    //get Method
    public ArrayList<String> getTuitionFeeLst(){return tuitionFeeLst;}
    public String getIncome(){return income;}
    public String getRegion(){return region;}
    public ArrayList<String> getSpanLst(){return spanLst;}

    //set Method
    public void setTuitionFeeLst(ArrayList<String> ttL){tuitionFeeLst = ttL;}
    public void setIncome(String icL){income = icL;}
    public void setRegion(String rgL){region = rgL;}
    public void setSpanLst(ArrayList<String> spL){spanLst = spL;}
}
```

`filterLst`는 생성될 때, 일단 모든 변수들을 초기화 한다.

이 객체에 생성되어 있는 변수들은 `tuitionFeeLst, income, region, spanLst`가 있다.

**tuitionFeeLst**  : (등록금 내, 등록금 외, 등록금 내 + 외) 중 해당하는 것들이 담긴다. 등록금에 관련한 Lst로 한개 이상이 조건에 해당될 수 있으므로 ArrayLst 타입으로 구현

**income** : 소득분위에 관련한 것. Spinner에서 선택된 원소가 할당된다.

**region** : 지역에 관련한 것. Spinner에서 선택된 원소가 할당된다.

**spanLSt** : (시작일, 종료일) 이 담긴다. 형태는 String으로 `yyyy-MM-dd`로 담기도록 하였다. DatePicker로 선택된 날짜가 담기게 된다.

`filterLst`객체는 먼저 `MainActivity`에서 생성되며 생성된 `filterLst`객체가 두번째 액티비티(장학금 조건 선택)과 세번째 액티비티(장학금 필터링)에서 양방향으로 데이터가 오가도록 되어있다.

 또한 선택된 검색조건이 담긴 객체는 ***동일한*** 객체로 여러 액티비티에 사용되어야 한다.

##### - MAIN

메인에서는 액티비티를 전환하고, 선택된 조건들을 화면에 띄우도록 하였다.

 ###### *activity_main.xml (부분생략)*

 ```xml
  .
  .
  .
  <!-- 검색조건 선택하는 버튼 -->
<Button
   android:id="@+id/filterBT"
   android:layout_width="400dp"
   android:layout_height="60dp"
   android:layout_gravity="center_horizontal"
   android:layout_marginBottom="20dp"
   android:text="나에게 맞는 장학금 조건 선택하기"
   android:background="@drawable/button_state"
   android:textColor="#016fa2"
   android:textSize="20dp" />

   <!-- 필터링 한 결과를 보기위한 버튼 -->
<Button
   android:id="@+id/searchBT"
   android:layout_width="400dp"
   android:layout_height="60dp"
   android:layout_gravity="center_horizontal"
   android:text="나에게 맞는 장학금 검색하기"
   android:background="@drawable/button_state"
   android:textColor="#016fa2"
   android:textSize="20dp" />
  .
  .
 ```
 메인 화면에 띄우는 버튼을 두개 생성하였고 Button의 모양을 직접 커스텀 하기 위해 button_state.xml에서 디자인하고 연결해 주었다.

 ```xml
 .
 .
 .
 <!-- 검색조건을 확인하기 위한 TextView들 -->
    <TextView
        android:layout_marginTop="30dp"
        android:background="#cbddf5"
        android:id="@+id/tuitionSetFilterTxt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        android:textColor="#1263ce"
        android:textSize="15dp"
        android:layout_gravity="center_horizontal"
        android:text=""/>
.
.
.
 ```

`tuitionFee`뿐만 아니라 다른 조건들 또한 위와 동일하게 TextView를 설정하여 값이 들어있는 경우에만 `visibility`를 `VISIBLE`로 설정하도록 하였다.

###### *button_state.class*

```xml
  <!-- 버튼을 둥글게 만들기 위하여 button의 state를 설정함 -->
    <item android:state_focused="false" android:state_pressed="false">
        <shape>
            <solid android:color="#b2ddef" />
            <corners android:radius="15dp" />
            <stroke android:width="2dp"
                android:color="#ffffff" />
        </shape>
    </item>
    <!-- 버튼을 클릭하였을때 버튼 변화 -->
    <item android:state_focused="false" android:state_pressed="true">
        <shape>
            <solid android:color="#b2ddff" />
            <corners android:radius="15dp" />
            <stroke android:width="2dp"
                android:color="#006caa" />
        </shape>
    </item>
```

버튼이 클릭되었을 때와 클릭되지 않았을 때를 구분하여 구현하였다.
또한 모서리를 둥글게 하고, 겉에 윤곽선도 만들어 주었다.

###### *MainActivity.class (부분생략)*

```java
Button filterBt = (Button)findViewById(R.id.filterBT); //검색조건 찾는 액티비티로 넘어가는 버튼
Button searchBt = (Button)findViewById(R.id.searchBT); //필터링하여 검색하는 액티비티로 넘어가는 버튼

filterBt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent filterIntent = new Intent(getApplicationContext(), Filter.class);
        filterIntent.putExtra("filterLst", FL);
        startActivityForResult(filterIntent, 0);//액티비티로 넘어감
    }
});

// 검색 버튼 클릭에 대한 이벤트 리스너
searchBt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent selectedIntent = new Intent(getApplicationContext(), FilteredList.class);
        selectedIntent.putExtra("selectedLst", FL);
        startActivityForResult(selectedIntent, 1);
    }
});
```
메인에서의 주요한 기능은 일단 다른 액티비티로의 전환이다.
따라서 `Button Click Event`가 주요 기능이 되었다.
해당 버튼을 누르면 Intent를 만들어 `filterLst`객체를 담아 넘기고, 각각 해당하는 `requestCode`를 `0, 1`로 설정해 주었다.

**0** : 검색조건 선택 액티비티
**1** : 장학금 필터링 액티비티

```java
if (requestCode == 0) { //filterIntent에서의 결과를 처리
  if (resultCode == RESULT_OK) {
      String tmp = ""; //Txt를 설정하기 위한 temp변수

      FL = (filterLst)data.getSerializableExtra("backFilterLst"); //FL을 다시 할당
      ArrayList<String> tuitionfeeLst = FL.getTuitionFeeLst();
      String income = FL.getIncome();
      String region = FL.getRegion();
      ArrayList<String> spanLst = FL.getSpanLst(); //tuition, income, region, span을 담은 데이터를 get

      //tuition에 대한 정보를 TextView 로 띄움
      if (!tuitionfeeLst.isEmpty()) {
          tmp = "[ 등록금 내 / 외 / 내 + 외 : ";
          for (String str:tuitionfeeLst){
              tmp += str + ", ";
          }
          tmp += "]";
          ttSetFilt.setText(tmp);
          ttSetFilt.setVisibility(View.VISIBLE);
      }
      else ttSetFilt.setVisibility(View.GONE);

      //income에 대한 정보를 TextView 로 띄움
      if (!income.equals("")) {
          tmp = "[ 소득분위 구분 / 미구분 : " + income + "]";
          icSetFilt.setText(tmp);
          icSetFilt.setVisibility(View.VISIBLE);
      }
      else icSetFilt.setVisibility(View.GONE);

      //region에 대한 정보를 TextView 로 띄움
      if (!region.equals("")) {
          tmp = "[ 지역 : " + region + "]";
          rgSetFilt.setText(tmp);
          rgSetFilt.setVisibility(View.VISIBLE);
      }
      else rgSetFilt.setVisibility(View.GONE);

      //span에 대한 정보를 TextView 로 띄움
      if (!spanLst.isEmpty()) {
          Log.d("mainSpan", spanLst.get(0));
          tmp = "[ 기간 : " + spanLst.get(0) + " ~ " + spanLst.get(1) + "]";
          spSetFilt.setText(tmp);
          spSetFilt.setVisibility(View.VISIBLE);
      }
      else spSetFilt.setVisibility(View.GONE);
  }
}
```
`requestCode`가 0 이므로 검색조건을 선택한 액티비티에서 받은 신호이다.
검색조건에 대한 정보를 초기화 된 `ArrayList, String` 객체에 담고, 담겨진 객체들을 TextView에 set해주었다.

그 후에 `Visible`이 `gone`이었던 것들을 `Visible`로 바꾸어 주었다.

```java
else if (requestCode == 1){
    if (resultCode == RESULT_OK){
        FL = new filterLst();
        ttSetFilt.setVisibility(View.GONE);
        icSetFilt.setVisibility(View.GONE);
        rgSetFilt.setVisibility(View.GONE);
        spSetFilt.setVisibility(View.GONE);
    }
}
```
`requestCode`가 1 이기 때문에 장학금을 필터링 한 액티비티에서 받은 신호이다.
장학금을 필터링 한 액티비티에서 신호를 받게되면 초기 메인화면을 초기화 하도록 구현하였기 때문에, 다시 TextView를 `Gone`으로 수정하고 `filterLst`의 객체를 새로 만들어 주었다.

##### - Popup(Filt)

두번째 액티비티에서는 `장학금의 검색 조건`을 선택하는 액티비티이다.
또한 이 부분은 팝업창으로 띄우도록 설정하였다.

###### *activity_popup.xml (부분생략)*

```xml
.
.
<LinearLayout
    android:layout_marginTop="15dp"
    android:layout_width="match_parent"
    android:layout_height="25dp"
    android:orientation="horizontal">
    <CheckBox
        android:id="@+id/tuitionInCk"
        android:layout_width="30dp"
        android:layout_height="20dp"/>
    <TextView
        android:id="@+id/tuitionInTxt"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="등록금 내"
        android:textSize="20dp"/>
</LinearLayout>
.
.
<LinearLayout
    android:layout_marginTop="15dp"
    android:layout_width="match_parent"
    android:layout_height="25dp"
    android:orientation="horizontal">

    <CheckBox
        android:id="@+id/incomeCk"
        android:layout_width="30dp"
        android:layout_height="20dp"/>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:textSize="20dp"
        android:layout_marginRight="8dp"
        android:text="소득분위"/>
    <TextView
        android:id="@+id/incomeTxt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18dp"
        android:textColor="#1263ce"
        android:text="선택안함"/>
</LinearLayout>
<Spinner
    android:id="@+id/incomeSpin"
    android:entries="@array/incomeArray"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
.
.
```
장학금의 조건은 위와 같이 구현되어있다.
`tuitionFee`같은 경우에는 체크박스 + 텍스트뷰로,
`income, region`같은 경우에는 체크박스 + 스피너,
`span`같은 경우에는 체크박스 + EditText(Calendar)
로 이루어져 있다.

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <Button
        android:id="@+id/allCkBoxBT"
        android:layout_width="63dp"
        android:layout_height="40dp"
        android:background="#b2ddff"
        android:text="전체선택"
        android:textColor="#016fa2" />

    <Button
        android:id="@+id/cancelBT"
        android:layout_width="116dp"
        android:layout_height="40dp"
        android:background="#b2ddff"
        android:text="취소"
        android:textColor="#016fa2" />

    <Button
        android:id="@+id/setFilteringBT"
        android:layout_width="122dp"
        android:layout_height="40dp"
        android:background="#b2ddff"
        android:text="검색 조건 선택하기"
        android:textColor="#016fa2" />
</LinearLayout>
```
또한 검색조건 아래에는 버튼을 3가지 구현해 두었다.

**취소버튼** : 메인으로 돌아가도록 구현
**전체선택** : 모든 조건의 체크박스를 선택하도록 구현
**검색조건 선택하기** : 선택한 검색조건들을 선택하도록 구현

전체적인 레이아웃은 `LinearLayout`으로 이루어져 있다.

###### *Filter.class (부분생략)*

`Filter.class` 에서는, `MainActivity`에서 넘어와서, 해당 액티비티에서 검색조건을 선택하여 `filterLst`객체로 다시 반환하는 역할을 한다.

등록금에 관한 조건은 체크박스 선택으로 해결할 수 있으므로 Spinner에 대한 부분은 다음과 같다.

```java
.
.
//income Spinner에 대한 이벤트 처리 _ 이연주
incomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        incomeTxtView.setText("" + parent.getItemAtPosition(position)); //선택한 String으로 Text를 설정 _ 이연주
        //String을 선택하지 않으면 checkbox는 false로, 선택하면 true로 _ 이연주
        if (incomeTxtView.getText().equals("선택안함")) {incomeCkBox.setChecked(false);}
        else incomeCkBox.setChecked(true);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent){ //선택하지 않은 default
        incomeTxtView.setText("선택안함");
    }
});
.
.
```
`소득분위`와 `지역`에 대한 조건은 `Spinner`를 통해 이루어진다. 두 조건을 선택한 값으로 TextView를 set해주고, 만약 그 선택한 값이 "선택안함"이 아니라면 자동으로 체크박스를 체크하도록 설정하였다.

```java
spanStartTxt.setInputType(0);
spanTillTxt.setInputType(0);
//시작일 TextEdit을 클릭하였을 때 _ 이연주
spanStartTxt.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
       flag = 1; //flag 를 1 로 설정 _ 이연주
       new DatePickerDialog(Filter.this, R.style.DatePickerTheme, datePick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(); //달력을 띄우고 Text설정 _ 이연주
   }
});
//종료일 TextEdit을 클릭하였을 때 _ 이연주
spanTillTxt.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
       flag = 2; //flag 를 2 로 설정 _ 이연주
       new DatePickerDialog(Filter.this, R.style.DatePickerTheme, datePick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(); //달력을 띄우고 Text설정 _ 이연주
   }

});
}
```
위의 코드는 `기간`에 대해 처리한 부분이다. `기간`은 EditText를 클릭하면 Calendar Dialog가 뜨도록 구현하였다.

`setInputType(0)`으로 설정함으로, EditText를 클릭하더라도 키보드가 뜨지 않도록 설정하였다.

그리고 각 EditText를 클릭하면 DatePickerDialog를 선택하게 되고, 현재 calendar객체에 담겨있는 시간을 가져와 보여주도록 하였다.

현재 봐야하는 달력이 두개기 때문에 flag변수를 통해서 구분지었다.
(flag를 두지 않으면 해당하는 EditText를 setText하기 힘들다.)

```java
int flag = 0;

Calendar calendar = Calendar.getInstance(); //calendar _ 이연주

DatePickerDialog.OnDateSetListener datePick = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth); //년, 월, 일 set
        update(); //Text set
    }
};
```

Edit Text를 클릭하고 나서 DatePicker를 실행할 때에 설정하게 되는 Listener이다.
사용자가 클릭하여 설정한 날짜를 `calendar`객체에 담고, `update()` 함수를 호출하여 EditText의 Text를 설정하여 준다.

```java
private void update(){ //TextEdit의 Text를 set해주는 Method _ 이연주
    String myFormat = "yyyy-MM-dd";    // 출력형식   2018-11-28 _ 이연주
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

    if (flag == 1){ //시작일 _ 이연주
        EditText tmp = (EditText)findViewById(R.id.startDate);
        tmp.setText(sdf.format(calendar.getTime()));
    }
    else if (flag == 2){ //종료일 _ 이연주
        EditText tmp = (EditText)findViewById(R.id.tillDate);
        tmp.setText(sdf.format(calendar.getTime()));
    }
  }
```
`update()`가 호출되면 일정에 대해 `format`을 정해주고, flag에 따라 EditText를 set해주도록 한다.

```java
//전체선택 버튼에 대한 이벤트 처리 _ 이연주
allCkButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //체크박스가 true이면 false로, false이면 true로
        if (tuitionInCkBox.isChecked() && tuitionOutCkBox.isChecked() && tuitionInandOutCkBox.isChecked() && incomeCkBox.isChecked() && regionCkBox.isChecked() && spanCkBox.isChecked()) {
            tuitionInCkBox.setChecked(false);
            tuitionOutCkBox.setChecked(false);
            tuitionInandOutCkBox.setChecked(false);
            incomeCkBox.setChecked(false);
            regionCkBox.setChecked(false);
            spanCkBox.setChecked(false);
        }
        else {
            tuitionInCkBox.setChecked(true);
            tuitionOutCkBox.setChecked(true);
            tuitionInandOutCkBox.setChecked(true);
            incomeCkBox.setChecked(true);
            regionCkBox.setChecked(true);
            spanCkBox.setChecked(true);
        }
    }
});
```
**전체선택**버튼을 클릭하게 되면 모든 체크박스를 `True`로 만들어 준다.
만약 모든 체크박스가 선택되었을 경우 모든 체크박스를 `False`로 만들어 주도록 구현하였다.

```java
final filterLst finalFL = FL;
    //취소 버튼을 클릭하였을 때 _ 이연주
    cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            backIntent.putExtra("backFilterLst", finalFL);
            setResult(RESULT_OK, backIntent);
            finish(); //FL을 그대로 돌려주고 액티비티 종료 _ 이연주
        }
    });
```
**취소** 버튼을 클릭하게 되면 메인화면으로 돌아가서 초기화 시키도록 하기 위해서 `setResult`를 이용하도록 하였다.
`Intent`를 호출한 후에는 액티비티를 종료하도록 하였다.

```java
tFLst = new ArrayList<String>();
      //Ck된 조건들만 담아서 FL에 설정한다. _ 이연주
      if (tuitionInCkBox.isChecked()) {tFLst.add(tuitioninTxtView.getText().toString());} //tuition in _ 이연주
      if (tuitionOutCkBox.isChecked()) {tFLst.add(tuitionOutTxtView.getText().toString());} //tuition out _ 이연주
      if (tuitionInandOutCkBox.isChecked()) {tFLst.add(tuitionInandOutTxtView.getText().toString());} //tuition in + out _ 이연주
      if (incomeCkBox.isChecked()) { //income _ 이연주
          if (incomeTxtView.getText().toString().equals("선택안함")){ //CkBox는 되어있지만 선택되지 않았을 때_ 이연주
              AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
              noOpBand.setMessage("소득분위를 선택해주세요!");
              noOpBand.setPositiveButton("확인", null);
              noOpBand.create().show();
              return;
          }
          iC[0] = incomeTxtView.getText().toString();}
```
**검색조건 선택하기** 버튼을 클릭하였을 때에는 체크박스의 여부에 따라 검색조건을 담도록 하였다.
체크박스가 체크되어있는데 속성이 선택되어있지 않으면 경고창을 띄우도록 하였다.

`income`부분을 보면, 체크박스는 선택되었으나, `income`에서 선택된 항목이 "선택안함" 이므로 속성이 선택되어있지 않은 경우를 의미한다.
이런경우는 경고창을 띄우도록 하였고 , `region`도 위와 마찬가지로 구현하였다.

```java
if (spanCkBox.isChecked()) { //span _ 이연주
      if (spanStartTxt.getText().toString().substring(0, 2).equals("ex") || spanTillTxt.getText().toString().substring(0, 2).equals("ex")){ //기간이 설정되어 있지 않으면 경고문을 띄움 _ 이연주
          AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
          noOpBand.setMessage("기간을 선택해주세요!");
          noOpBand.setPositiveButton("확인", null);
          noOpBand.create().show();
          return;
      }
      try {
          //시작일과 종료일이 잘못되어있으면 경고문 띄우기 _ 이연주
          if (ckSpanisOK(spanStartTxt.getText().toString(), spanTillTxt.getText().toString())){
              AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
              noOpBand.setMessage("기간을 다시 설정해주세요!");
              noOpBand.setPositiveButton("확인", null);
              noOpBand.create().show();
              return;
          }
      } catch (ParseException e) {
          e.printStackTrace();
      }
      sPLst.add(spanStartTxt.getText().toString());
      sPLst.add(spanTillTxt.getText().toString());}
```
기간에 대한 경우에는 두가지 경고처리를 해주었다.
  1. 체크박스가 선택되었는데 기간을 정하지 않은 경우
  2. 시작일과 종료일이 올바르지 않은경우

***체크박스가 선택되었는데 기간을 정하지 않은경우***
시작과 종료일이 `String`으로 되어있기 때문에 `substring`을 통해 비교하였다.
default에 `ex)`값이 들어있기 때문에 해당 문구가 하나라도 들어간경우 경고창을 띄우도록 하였다.

***시작일과 종료일이 올바르지 않은경우***

```java
//시작일과 종료일에 대해서 체크하는 함수 _ 이연주
public boolean ckSpanisOK(String start, String till) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date stDate = sdf.parse(start);
    Date tiDate = sdf.parse(till);

    if (stDate.after(tiDate)) return true; //시작일이 종료일보다 이후에 있으면 true 반환 _ 이연주
    return false;
}
```
`ckSpanisOK`라는 함수를 호출하여 각각의 시작일과 종료일을 비교하도록 하였다.
만약 시작일이 종료일보다 이후에 일어나도록 설정하였다면 `true`를 반환한다.

따라서 `ckSpanisOK`가 true인 경우 잘못 기간을 선택했다는 경고창이 뜨도록 하였다.

##### - 그 외(다른 팀원 구현)

그 외에는 **장학금 필터링** 기능에 대한 부분으로 이루어져 있다.
해당 기능은 선택한 검색조건을 세번째 액티비티로 전달하고, 그 조건과 `DB`에 올라가 있는 장학금들의 조건을 비교하여 필터링 하도록 하였다.

**장학금 필터링** 액티비티에서도 **돌아가기, 링크연결하기(자세히보기), 캘린더에 일정추가하기** 기능이 포함되어 있으며 그 중 본인이 구현한

***캘린더에 일정추가하기*** 부분에 대해서만 기술하려 한다.

###### *FilteredList.class*

이 기능은 간단하다.
먼저 *AndroidManifest.xml* 파일에 다음 두줄을 추가하도록 한다.
```xml
<uses-permission android:name="android.permission.READ_CALENDAR"/>
<uses-permission android:name="android.permission.WRITE_CALENDAR"/>
```
위의 두줄은 캘린더의 연동에 대한 허가부분이다.


```java
//일정추가 버튼 클릭시
calBt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //DB에서 년/월/일 받아서 할당
        int year = Integer.parseInt(toSaveDate.substring(0, 4));
        int month = Integer.parseInt(toSaveDate.substring(5, 7)) - 1;
        int day = Integer.parseInt(toSaveDate.substring(8, 10));

        //Calendar 가져오기
        Calendar calAppointment = Calendar.getInstance();

        //Calendar 에 년/월/일 set
        calAppointment.set(Calendar.YEAR, year);
        calAppointment.set(Calendar.MONTH, month);
        calAppointment.set(Calendar.DAY_OF_MONTH, day);
        calAppointment.set(Calendar.HOUR_OF_DAY, 12);
        calAppointment.set(Calendar.MINUTE, 30);

        //구글 캘린더 연동
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calAppointment.getTime().getTime()); //시간 설정
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, "Your Timezone");
        intent.putExtra(CalendarContract.Events.TITLE, toSaveTitle); //타이틀 == 장학금 명
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Your description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Your location");
        if (intent.resolveActivityInfo(getApplicationContext().getPackageManager(), 0) != null) {
            startActivity(intent);
        }
    }
});
```
그 이후에 버튼을 선택하면 선택되어있는 장학금(리스트 뷰)의 `기간`과 `장학금 명`을 가져오도록 한다.
`기간`값을 가져와서 `String`에 포함되어있는 `-`의 값을 제외하고 받기 위해 `substring`을 이용하였다.

가져온 값들로 calendar의 일정과 시간을 set한 후에 주석이 남겨져 있는 두 부분 (`시간`, `장학금 명`)에 할당해 줌으로써 기본 캘린더 앱과 연동하여 일정을 바로 추가하도록 하였다.

일정을 추가하게 되면 장학금 명으로 일정명이 설정되고, 장학금기간의 마지막날로 일정 날짜가 설정된다.

이 이외의 장학금을 `DB`를 통해서 읽어오는 부분과, 리스트 뷰를 이용해 화면에 띄우고, 링크를 연결하는 부분에 대해서는 팀원이 구현한 내용으로 생략하도록 한다.

## 3. 코드 실행

###### 간단한 기능
    - 검색조건 선택하기
    - 검색조건 띄우기
    - 캘린더 앱 연동하기
    - 경고창 확인

1. 검색조건 선택하기

![1.main](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/1.main.PNG)
![2.filterTabOpen](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/2.filterTabOpen.PNG)

메인화면에서 `나에게 맞는 장학금 조건 선택하기` 버튼을 클릭하게 되면 두번째 사진처럼 팝업창이 뜨게 된다.

![3.allSelect](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/3.allSelect.PNG)

그다음은 전체선택을 통해 모든 조건을 선택해 본 상태이다.

![3-1.IncomeSpinnerNotSel](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/3-1.IncomeSpinnerNotSel.PNG)
![3-1.IncomeSpinnerSel](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/3-1.IncomeSpinnerSel.PNG)

`소득분위`에 대한 `Spinner`를 선택하고 나면 선택한 항목으로 TextView가 바뀌는 것을 볼 수 있다.

![3-2.RegionSpinnerNotSel](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/3-2.RegionSpinnerNotSel.PNG)
![3-2.RegionSpinnerSel](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/3-2.RegionSpinnerSel.PNG)

`지역`에 대한 `Spinner`를 선택하고 나면 선택한 항목으로 TextView가 바뀌는 것을 볼 수 있다.

![7.calendarOpen](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/7.calendarOpen.PNG)
![10.allOptionSelect](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/10.allOptionSelect.PNG)

`일정`에 관한 EditText를 클릭하면 위의 사진처럼 캘린더가 뜨도록 되어있다. 따라서 클릭하게 되면 두번째 사진처럼 Text가 set된것을 볼 수 있다.

2. 검색조건 띄우기

![11.TextView](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/11.TextView.PNG)

검색 조건은 위에서 선택했던 조건 대로 메인화면에 띄워진 것을 볼 수 있다.

3. 캘린더 앱 연동하기
![12.selectSholar%20before%20update%20Calendar](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/12.selectSholar%20before%20update%20Calendar.PNG)
![13.googleCalendar](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/13.googleCalendar.PNG)

선택한 장학금을 아래에 `캘린더에 추가`버튼을 눌러서 사진과 같이 `구글캘린더 혹은 기본캘린더`에 추가하도록 할 수 있다.
일정명은 장학금 명과 같고 일정기간은 종료일과 같으므로, 둘을 비교해보면 일치하는 것을 알 수 있다.

###### 경고창 확인

1. 체크박스가 선택되었는데 소득분위 항목이 선택되지 않았을 경우

![4.noIncome](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/4.noIncome.PNG)

2. 체크박스가 선택되었는데 지역 항목이 선택되지 않았을 경우
![5.noRegion](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/5.noRegion.PNG)

3. 체크박스가 선택되었는데 일정이 선택되지 않았을 경우
![6.noSpan](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/6.noSpan.PNG)

4. 지정한 일정이 잘못된 경우
![8.setWrongDate](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/8.setWrongDate.PNG)
![9.WrongDateAlert](https://github.com/yannJu/AndroidStudioProject/blob/master/teamProjectCapture/9.WrongDateAlert.PNG)

모두 제대로 경고창이 뜨고있음을 확인할 수 있다.

## 4. 마무리

해당 프로젝트를 구현하면서, 레이아웃에 관한 부분에 대해서는 이전 개인프로젝트를 포함해 실습을 통해 쉽게 구현할 수 있었다.

무리없이 레이아웃을 구현하고, 간단한 버튼 이벤트나 `Intent`전환등에서 막히지 않는 부분에서 많이 익숙해 진 것 같다고 느꼈다.

하지만 모든 구현이 쉬운 것은 아니었고, 전체적인 틀을 만들고 아이디어를 구한다는 것이 조금 까다로웠다.

특히 **액티비티 팝업으로 띄우기**, **기간선택을 위해 캘린더 띄우기**와 **기본앱과 연동** 하는 부분에서 시간을 많이 투자했던 것 같다.

이 이후에는 팀원이 구현했던 부분에 대해서 스스로 구현해보고 데이터베이스를 구축해보고 싶다는 생각을 했다.

또한 처음에 선택했던 주제에 대해서 구현할 수 있는 방법을 모색하고, 데이터 베이스와 웹크롤링을 연결시켜서 직접 데이터 베이스를 구축하는 것이아니라 **웹에 있는 데이터를 가져오는 방법**, **로그인을 통해 개인 데이터를 만드는 등**에 대해서 고민함으로써 좀 더 ***상용적인 앱*** 으로 확장시키고 싶다.

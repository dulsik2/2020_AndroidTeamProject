package List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.teamproject.R;

import java.util.ArrayList;

// ***** 전체_이소영 *****
public class ListViewAdaptor extends BaseAdapter {
    private TextView title;
    private TextView fee;
    private TextView income;
    private TextView region;
    private TextView span;

    private Context mContext = null;
    public ArrayList<Scholar> arr = new ArrayList<>();
    private LayoutInflater mInflater;

    public ListViewAdaptor(Context context){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    // 리스트뷰에 이용하는 ArrayList에 아이템 추가
    public void addItem(Scholar s){
        arr.add(s);
        notifyDataSetChanged();
    }

    // ArrayList 데이터의 개수 리턴
    @Override
    public int getCount() {return arr.size();}

    // ArrayList에서 pos 인덱스에 위치한 아이템 반환
    @Override
    public Scholar getItem(int pos) {return arr.get(pos);}

    @Override
    public long getItemId(int i) {return i;}

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        title = (TextView) convertView.findViewById(R.id.titleView);
        fee = (TextView) convertView.findViewById(R.id.tuitionFeeView);
        income = (TextView) convertView.findViewById(R.id.incomeView);
        region = (TextView) convertView.findViewById(R.id.regionView);
        span = (TextView) convertView.findViewById(R.id.spanView);

        // 리스트뷰 아이템의 텍스트뷰 Text 설정
        Scholar listViewItem = arr.get(pos);
        title.setText(listViewItem.getTitle());
        fee.setText("등록금 내/외: " + listViewItem.getTuitionFee());
        income.setText("소득분위: " + listViewItem.getIncome());
        region.setText("지역: " + listViewItem.getRegion());
        span.setText(listViewItem.getStartDate() + " ~ " + listViewItem.getEndDate());
        return convertView;
    }
}

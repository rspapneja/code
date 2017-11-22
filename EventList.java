package clue.highmountain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adpter.EventAdpter;
import Adpter.MyBucketListAdpter;
import extra.Global;
import extra.NewProgressBar;
import extra.commonDialog;
import extra.userOnlineInfo;
import vollyclass.OnApihit;
import vollyclass.VolleyBase;

public class EventList extends AppCompatActivity  implements OnApihit,View.OnClickListener{

    RecyclerView event_recycle;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    NewProgressBar dialog;
    ArrayList<HashMap<String, String>> event_list = new ArrayList<HashMap<String, String>>();

    TextView title;
    ImageView left_image,search,female,no_data_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        initializeVariable();

        eventListAPI();
    }

    private void eventListAPI() {

        userOnlineInfo user = new userOnlineInfo();

        if (user.isOnline(this))
        {
            dialog = new NewProgressBar(this);
            dialog.show();
            Map<String,String> params = new HashMap<>();
            String link = Global.URL+"get_all_events";
            new VolleyBase(this).main(params, link, 0);
        }
        else
        {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(this);
        }
    }

    private void initializeVariable()
    {
        event_recycle=(RecyclerView)findViewById(R.id.events_layout);


        title=(TextView)findViewById(R.id.title);
        title.setText("Be A Traveller!");

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/georgiab.ttf");
        // title.setTypeface(tf);
        title.setTextColor(Color.parseColor("#3d3b3b"));

        left_image=(ImageView)findViewById(R.id.left_image);
        no_data_found=(ImageView)findViewById(R.id.no_data_found);
        left_image.setImageResource(R.drawable.back_black_icon);

        female=(ImageView) findViewById(R.id.female);
        search=(ImageView)findViewById(R.id.search);

        female.setOnClickListener(this);
        search.setOnClickListener(this);
        left_image.setOnClickListener(this);

    }

    @Override
    public void success(String Response, int index) {
      try
         {
             Log.e("response",Response);
            JSONObject jobj = new JSONObject(Response);
            String status = jobj.getString("status");

            if (status.equalsIgnoreCase("true"))
            {
                no_data_found.setVisibility(View.GONE);
                event_recycle.setVisibility(View.VISIBLE);
                HashMap<String, String> list;
                JSONArray results1 = jobj.getJSONArray("events");

                for (int i = 0; i < results1.length(); i++) {
                    JSONObject c = results1.getJSONObject(i);
                    list = new HashMap<String, String>();

                    list.put("event_id", c.getString("event_id"));
                    list.put("event_name", c.getString("event_name"));
                    list.put("location", c.getString("location"));
                    list.put("image", c.getString("image"));
                    event_list.add(list);
                }

                recylerViewLayoutManager = new LinearLayoutManager(EventList.this,LinearLayoutManager.VERTICAL,false);
                event_recycle.setLayoutManager(recylerViewLayoutManager);

                EventAdpter ad = new EventAdpter(EventList.this, event_list);
                event_recycle.setAdapter(ad);
            }
            else
            {
                no_data_found.setVisibility(View.VISIBLE);
                event_recycle.setVisibility(View.GONE);
                dialog.dismiss();
            }
        } catch (Exception e)
      {
            dialog.dismiss();
            System.out.println(e);
        }
        dialog.dismiss();
    }

    @Override
    public void error(VolleyError error, int index)
    {
        Toast.makeText(EventList.this,error.toString(),Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.female:

                extra.Contactus contact_in=new extra.Contactus();
                contact_in.contactUs(this);
                break;

            case R.id.search:

                Intent search_in=new Intent(this,SearchFilter.class);
                this.startActivity(search_in);
                break;
            case R.id.left_image:

                onBackPressed();
                break;


        }
    }
}

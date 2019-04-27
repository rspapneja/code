package kite.appzorro.kiteApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.ReviewProfessionalListModel;
import adpter.ReviewListAdpter;
import apiInterface.ApiClient;
import apiInterface.ApiInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.App;
import extra.ErrorDialog;
import extra.Global;
import extra.ProgressBarHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewListActivty extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.back_im)
    ImageView backIm;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.options)
    ImageView options;
    @BindView(R.id.review_recycleview)
    RecyclerView reviewRecycleview;

    ProgressBarHandler progressBarHandler;

    SharedPreferences sh;
    @BindView(R.id.no_record)
    TextView noRecord;

    String professional_id;
    List<ReviewProfessionalListModel.ResponseBean.DataBean> review_list;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    boolean loadmore=false;
    int pageNo=1,lastPageNo=1;
    ReviewListAdpter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        ButterKnife.bind(this);

        progressBarHandler = new ProgressBarHandler(this);
        sh = getSharedPreferences(Global.PREF_NAME, MODE_PRIVATE);

        setTitle();

        backIm.setImageResource(R.drawable.ic_icon_backarrow);

        LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(ReviewListActivty.this);
        reviewRecycleview.setLayoutManager(recylerViewLayoutManager);

        review_list=new ArrayList<>();

         sa = new ReviewListAdpter(ReviewListActivty.this, review_list);
        reviewRecycleview.setAdapter(sa);

        swipeRefreshLayout.setOnRefreshListener(this);

        Intent i=getIntent();

        professional_id= i.getStringExtra("professional_id");

        getProfessionalReviewList();

        reviewRecycleview.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        int lastCompletelyVisibleItemPosition = 0;

                        lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                        if (lastCompletelyVisibleItemPosition == review_list.size() - 1)
                        {
                            if(loadmore)
                            {
                                swipeRefreshLayout.setRefreshing(true);
                                pageNo += 1;
                                getProfessionalReviewList();

                            }
                        }
                    }
                });


    }
    private void setTitle() {

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/myriad_pro-bold.otf");
        title.setText("All Review");
        title.setTextColor(Color.parseColor("#585858"));
        title.setTypeface(tf);
    }
    private void getProfessionalReviewList() {

        if(App.getInstance().isConnected())
        {
            progressBarHandler.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ReviewProfessionalListModel> call = apiService.getProfessionalReviewList(professional_id,""+pageNo);
            call.enqueue(new Callback<ReviewProfessionalListModel>() {
                @Override
                public void onResponse(Call<ReviewProfessionalListModel> call, Response<ReviewProfessionalListModel>
                        response)
                {

                    String success = response.body().getResponse().getStatus();

                    if (success.equals("1"))
                    {
                        lastPageNo = Integer.parseInt(response.body().getResponse().getLast_page());

                        Log.e("lastPageNo", "" + lastPageNo);
                        Log.e("curentpage", "" + pageNo);

                        if (pageNo <= lastPageNo)
                        {
                            review_list.addAll(response.body().getResponse().getData());
                            sa.notifyDataSetChanged();
                        }
                        if (pageNo == lastPageNo)
                        {
                            progressBarHandler.hide();
                            loadmore = false;
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        progressBarHandler.hide();
                    } else
                    {
                        if (pageNo == 1)
                        {
                            progressBarHandler.hide();

                            try
                            {
                                progressBarHandler.hide();
                                reviewRecycleview.setVisibility(View.GONE);
                                noRecord.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setRefreshing(false);
                                loadmore = false;

                            }
                            catch (Exception e)
                            {
                                loadmore = false;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewProfessionalListModel> call, Throwable t) {

                    progressBarHandler.hide();

                    Toast.makeText(ReviewListActivty.this, " Server Error, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            ErrorDialog.getInstance().showAlert(ReviewListActivty.this,getString(R.string.no_internetconnection));
        }
    }
    @OnClick(R.id.back_im)
    public void onViewClicked()
    {
        onBackPressed();
    }


    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(true);

        pageNo = 1;
        lastPageNo=1;
        review_list.clear();
        sa.notifyDataSetChanged();

        refreshList();

    }

    private void refreshList()
    {  getProfessionalReviewList();
        swipeRefreshLayout.setRefreshing(false);
    }
}

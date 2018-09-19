package com.amarnath.deliverytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amarnath.deliverytest.adapters.DeliveryListAdapter;
import com.amarnath.deliverytest.utils.models.Delivery;
import com.amarnath.deliverytest.utils.webservice.DeliveryAPIManager;
import com.amarnath.deliverytest.utils.widgets.RecyclerItemClickListener;
import com.google.gson.JsonElement;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DeliveryListAdapter adapter;
    private ProgressBar progressBar;

    private int offset = 0, limit = 20;
    private boolean isLoading = false;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_nav_title);
        mTitle.setText(getString(R.string.things_to_deliver));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.pb_delivery_list);
        recyclerView = (RecyclerView) findViewById(R.id.rv_delivery_list);
        adapter = new DeliveryListAdapter(MainActivity.this, Realm.getDefaultInstance().where(Delivery.class).findAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Delivery delivery = adapter.getItem(position);
                        Intent intent = new Intent(MainActivity.this, DeliveryDetailActivity.class);
                        intent.putExtra("id", delivery.getId());
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                {
                    visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            System.out.println("last item");
                            getDeliveries();
                        }
                    }
                }
            }
        });

        if (App.getInstance().isNetworkAvailable())
            getDeliveries();
        else
            App.getInstance().showNoNetworkAlert(this);
    }

    private void getDeliveries() {

        if (isLoading) return;

        showProgressView();
        isLoading = true;
        offset = adapter.getItemCount();
        DeliveryAPIManager.getInstance().getDeliveries(offset, limit, new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, final Response<JsonElement> response) {

                if (response.isSuccessful()) {

                    Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.createOrUpdateAllFromJson(Delivery.class, response.body().toString());
                        }
                    });
                }
                hideProgressView();
                isLoading = false;
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideProgressView();
                isLoading = false;
                getDeliveries();
            }
        });
    }

    private void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressView() {
        progressBar.setVisibility(View.GONE);
    }

}

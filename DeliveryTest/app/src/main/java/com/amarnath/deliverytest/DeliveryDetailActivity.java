package com.amarnath.deliverytest;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnath.deliverytest.utils.models.Delivery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.realm.Realm;

public class DeliveryDetailActivity extends AppCompatActivity/**/ implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private TextView desc;
    private ImageView logo;

    private int deliveryId;
    private Delivery deliveryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_delivery_detail);
        ViewCompat.setTransitionName(findViewById(R.id.toolbar), DeliveryDetailActivity.class.getSimpleName());
        supportPostponeEnterTransition();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_nav_title);
        mTitle.setText(getString(R.string.delivery_details));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_delivery_list);
        mapFragment.getMapAsync(this);

        desc = (TextView) findViewById(R.id.tv_delivery_desc);
        logo = (ImageView) findViewById(R.id.iv_delivery_logo);
        logo.setClipToOutline(true);

        deliveryId = getIntent().getIntExtra("id", -1);
        deliveryData = getDeliveryDataFromId(deliveryId);

        desc.setText(deliveryData.getDescription());
        Glide.with(this)
                .load(deliveryData.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(logo);

    }

    private Delivery getDeliveryDataFromId(int id) {

        if (id == -1) {
            finish();
        }
        deliveryData = Realm.getDefaultInstance().where(Delivery.class).equalTo("id", id).findFirst();
        if (deliveryData == null) {
            finish();
        }

        return deliveryData;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        System.out.println(deliveryData.getDescription());
        System.out.println(deliveryData.getLocation());
        System.out.println(deliveryData.getLocation().getLat());
        System.out.println(deliveryData.getLocation().getLng());

        if (deliveryData == null)
            deliveryData = getDeliveryDataFromId(deliveryId);

        double lat = deliveryData.getLocation().getLat();
        double lng = deliveryData.getLocation().getLng();

        LatLng geo = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(geo).title(deliveryData.getLocation().getAddress()));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geo, 15));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }
}

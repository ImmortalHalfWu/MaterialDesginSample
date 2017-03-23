package com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.R;
import com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.adapters.BaseRecyclerViewAdapter;

import java.util.ArrayList;

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);

        
        initToolbar();
        initRecyclerView();
        initCTL();

    }

    private void initCTL() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingTollbarLayout);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new BaseRecyclerViewAdapter(new ArrayList<String>()));

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}

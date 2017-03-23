package com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.activitys;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.R;

import java.util.List;

/**   
 * 
 * Name:    StatusBarTestActivity
 * 
 * User:    WuImmortalHalf
 * Data:    2017/3/16 14:46
 *
 * <p>Todo:    ( 兼容4.4，实现状态栏透明，并将布局从屏幕左上角开始绘制（状态栏覆盖在布局之上） )
 * <p>1，创建style-v19 和 style-v21 对应4.4与5.0+版本</p>
 * <p>2，添加Flag，android:windowTranslcentStatus = true，android:windowTranslcentNavigation = true，这两个Flag意味着布局从左上角开始绘制，右下角结束。5.0加一条设置statueBarColor = 0x00000000，透明</p>
 * <p>3，根布局不添加fitsSystemWindow = true，则整个布局上移至状态栏下，并被状态栏覆盖</p>
 * <p>4，设置paddingTop，或者marginTop，实现布局下移。基于责任最小化，建议4.4设置Toolbar的paddingTop为statuBarHeight，titleMarginTop为statuBarHeight/2。5.0只需设置paddingTop为statueBarHeight</p>
 * <p>5，修改控件颜色，如toolbar backgroupColor，状态栏透明，显示toolbar颜色</p>
*/
public class StatusBarTestActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_test);
        ViewGroup contentLayout = (ViewGroup) findViewById(R.id.activity_status_bar_test);
        contentLayout.setFitsSystemWindows(false);
        //等于4.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
//
//            showToast("4.0");
//
//            // 生成一个状态栏大小的矩形
//            // 添加 statusBarView 到布局中
//
//            View statusBarView = createStatusBarView(getResources().getColor(R.color.colorAccent));
//            contentLayout.addView(statusBarView, 0);
//            // 内容布局不是 LinearLayout 时,设置padding top
//            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//                contentLayout.getChildAt(1)
//                        .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight() + contentLayout.getPaddingTop(),
//                                contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
//            }
//            // 设置属性
//            contentLayout.setFitsSystemWindows(false);
//            contentLayout.setClipToPadding(true);
//
//
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            showToast(">=5.0");
////            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            ViewGroup rootViewGroup = (ViewGroup) findViewById(android.R.id.content);
//            rootViewGroup.setFitsSystemWindows(false);
//            if (rootViewGroup.getChildCount() > 1) {
////                rootViewGroup.getChildAt(1).setBackgroundColor(Color.argb(100, 0, 0, 0));
//            } else {
////                rootViewGroup.addView(createStatusBarView(getResources().getColor(R.color.colorAccent)));
////                rootViewGroup.addView(createStatusBarView(Color.BLUE));
//            }
//        }


        initButton();
        initToolBar();

    }

    private void initButton() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPalette();
            }
        });
    }


    private void initToolBar() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);
        toolbar.setFitsSystemWindows(false);
        setSupportActionBar(toolbar);
        toolbar.measure(0,0);
        toolbar.getLayoutParams().height = toolbar.getMeasuredHeight()+getStatusBarHeight();
        //5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setPadding(0,getStatusBarHeight(),0,0);
        }else
        //4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            toolbar.setTitleMarginTop(getStatusBarHeight()/2);
            toolbar.setPadding(0,getStatusBarHeight(),0,0);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.go_back);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }


    private void initPalette() {
        /*
        Vibrant （有活力的）
        Vibrant dark（有活力的 暗色）
        Vibrant light（有活力的 亮色）
        Muted （柔和的）
        Muted dark（柔和的 暗色）
        Muted light（柔和的 亮色）
         */
        Palette.from(BitmapFactory.decodeResource(getResources(),R.mipmap.timg2))
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette == null) return;
                        List<Palette.Swatch> swatches = palette.getSwatches();
                        for (Palette.Swatch sw:
                             swatches) {
                            if (sw != null) {
                                toolbar.setBackgroundColor(sw.getRgb());
                                toolbar.setTitleTextColor(sw.getTitleTextColor());
                            }
                        }
//                        Palette.Swatch swatch = palette.getLightMutedSwatch();
//                        palette.getVibrantSwatch();
//                        palette.getDarkVibrantSwatch();
//                        palette.getLightVibrantSwatch();
//                        palette.getMutedSwatch();
//                        palette.getDarkMutedSwatch();
//                        palette.getLightMutedSwatch();
//                        if (swatch != null) {
//                            toolbar.setBackgroundColor(swatch.getRgb());
//                            toolbar.setTitleTextColor(swatch.getTitleTextColor());
//                        }
                    }
                });
    }


    private View createStatusBarView(@ColorInt int color) {

        View view = new View(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight());
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(color);

        return view;

    }

    private @Dimension int getStatusBarHeight(){
        return getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height","dimen","android"));
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}

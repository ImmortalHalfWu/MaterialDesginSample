package com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.activitys;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.R;
import com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.glide.GlideHelp;

import java.io.File;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.R.attr.id;
import static com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.R.id.imageView;
import static com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.R.id.toolbar;

/**   
 * 
 * Name:    ScrollingActivity2
 * 
 * User:    WuImmortalHalf
 * Data:    2017/3/16 17:12
 *
 * Todo:    ( ActionbarLayout + collapsingToolbar + Toolbar 实现状态栏与Toolbar同色，包括collapsingToolbarlayout折叠时的渐变效果 )
 * <p>1，在style-v19 与 style-v21 中，添加android:WindowTrancslucentStatue = true。在style-v21中，添加statuebarColor = 0x00000000</p>
 * <p>2，动态更改toolBar的高度，要先调用measure(0,0)，Toolbar.getLayoutparames().height = toolbar.getmeasureHeight() + statueBarHeight;加上状态栏高度</p>
 * <p>3，动态修改toolBar paddingTop 为状态栏高度， 4.4中，再设置titleMarginTop += statuBarHeight / 2 </p>
 *
 * <p>PS:</p>
 * <p>1，CollapsingToolbar 的折叠与展开有BUG，在展开状态下锁屏，再解锁，折叠，toolBar title向下偏移，一半处于toolbar之下无法看到</p>
 * <P>2，虽然实现了类网易云音乐的详情界面，但，滑动体验很差，因为使用的是collapsingToolBar实现头布局的上移，错了。</P>
*/
public class ScrollingActivity2 extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);

        initToolbar();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        loadImage();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadImage();
            }
        },1000);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbar() {

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        collapsingToolbarLayout.setPadding(0,getStatueBarHeight(),0,0);
        collapsingToolbarLayout.setTitleEnabled(false);


        android.support.design.widget.AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        CollapsingToolbarListener listener = new CollapsingToolbarListener() {
            private final int ALPHA_MAX = 255,ALPHA_MIN = 0;
            private float alphaOffset = -1.0f;
            /*
            toolbar背景透明度问题
            1，如果通过CollapsingToolbarLayout.setContentScrim(Drawable) 设置，虽然不用考虑透明度的改变，但bitmap被转为Color
            2，如果通过CollapsingToolbarLayout的滚动监听更改Drawable透明度，运算次数太多，卡顿。
             */
            @Override
            protected void onStateChange(AppBarLayout appBarLayout, int verticalOffset, CollapsingToolbarState state) {
                //  0 ~ 255
                //  verticalOffset == 0 ~ appBarLayout.getTotalScrollRange()
                if (alphaOffset == -1){
                    alphaOffset = ALPHA_MAX * 1f / Math.abs(appBarLayout.getTotalScrollRange());
                }
                if (toolbar.getBackground() == null) return;
                int alpha = Math.round(Math.abs(verticalOffset)*alphaOffset);
                switch (state){
                    case OPEN:
                        toolbar.getBackground().setAlpha(ALPHA_MIN);
                        break;
                    case CLOSE:
                        toolbar.getBackground().setAlpha(ALPHA_MAX);
                        break;
                    case OPENING:

//                        toolbar.getBackground().setAlpha(
//                                Math.round(Math.abs(verticalOffset)*alphaOffset)
//                        );
//                        break;
                    case CLOSEING:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if (alpha != toolbar.getBackground().getAlpha()){
                                toolbar.getBackground().setAlpha(alpha);
                            }
                        }
//                        toolbar.getBackground().setAlpha(
//                                Math.round(Math.abs(verticalOffset)*alphaOffset)
//                        );
                        break;
                }
            }
        };
        appBarLayout.addOnOffsetChangedListener(listener);
//        appBarLayout.setPadding(0,getStatueBarHeight(),0,0);
//        ViewGroup.LayoutParams layoutParams = appBarLayout.getLayoutParams();
//        layoutParams.height += getStatueBarHeight();
//        appBarLayout.setLayoutParams(layoutParams);
//        appBarLayout.setMinimumHeight(appBarLayout.getMinimumHeight()+getStatueBarHeight());



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.measure(0,0);
        toolbar.getLayoutParams().height = toolbar.getMeasuredHeight()+getStatueBarHeight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setPadding(0,getStatueBarHeight(),0,0);
//            collapsingToolbarLayout.setPadding(0,getStatueBarHeight(),0,0);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            toolbar.setPadding(0,getStatueBarHeight(),0,0);
            toolbar.setTitleMarginTop(Math.round(getStatueBarHeight()/2.0f));
//            appBarLayout.setPadding(0,getStatueBarHeight(),0,0);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private int getStatueBarHeight(){

        return getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height","dimen","android"));

    }

    public enum CollapsingToolbarState{

        /**
         * 打开状态
         */
        OPEN,
        /**
         * 关闭
         */
        CLOSE,
        /**
         * 打开中
         */
        OPENING,
        /**
         * 关闭中
         */
        CLOSEING

    }

    private static abstract class CollapsingToolbarListener implements AppBarLayout.OnOffsetChangedListener{
        private static final String TAG = "CollapsingToolbarListen";
        private CollapsingToolbarState state;
        private int lastOffset = 1;
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset == 0){
                //展开
                if (state!=CollapsingToolbarState.OPEN){
                    state = CollapsingToolbarState.OPEN;
                    onStateChange(appBarLayout,verticalOffset,state);
                }

            }else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                //关闭
                if (state!=CollapsingToolbarState.CLOSE){
                    state = CollapsingToolbarState.CLOSE;
                    onStateChange(appBarLayout,verticalOffset,state);
                }
            }else {
                //中间
                state = verticalOffset > lastOffset ? CollapsingToolbarState.OPENING : CollapsingToolbarState.CLOSEING;
                onStateChange(appBarLayout,verticalOffset,state);
            }
            lastOffset = verticalOffset;
        }

        protected abstract void onStateChange(AppBarLayout appBarLayout, int verticalOffset,CollapsingToolbarState state);

    }


    private void loadImage(){

        String url =
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489982573613&di=2aa5a3694f324e0508c7345f6624b9ba&imgtype=0&src=http%3A%2F%2Fbizhi.zhuoku.com%2F2014%2F01%2F07%2F2%2Fzhuoku083.jpg"
                ;

        Glide.with(this)
                .load(url)
                .bitmapTransform(new BlurTransformation(this,25,30))
                .crossFade(3000)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        resource.setAlpha(0);
                        toolbar.setBackgroundDrawable(resource);
//                        collapsingToolbarLayout.setContentScrim(resource);
                        return false;
                    }
                })
                .into(toolbar.getMeasuredWidth(),toolbar.getMeasuredHeight());

//
        GlideHelp.newInstance().loadImageFading(
                Glide.with(this),
                url,
                (ImageView) findViewById(R.id.imageView3),
                R.mipmap.timg2,
                R.mipmap.timg,
                2000,
                new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        changColorForPalette((ImageView) findViewById(R.id.imageView3));
                        return false;
                    }
                }
        );

        GlideHelp.newInstance().loadImageBlur(
                Glide.with(this),
                url,
                (ImageView) findViewById(R.id.imageView4),
                R.mipmap.timg,
                R.mipmap.timg2,
                new BlurTransformation(this,25,15*2),
                new  RequestListener<String, GlideDrawable>(){

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        changColorForPalette((ImageView) findViewById(R.id.imageView4));
//                        toolbar.setBackgroundDrawable(resource);
                        return false;
                    }
                }
                );
    }

    private void changColorForPalette(ImageView imageView){
//        toolbar.setBackground(imageView.getDrawable());

        Palette.from(((BitmapDrawable)imageView.getDrawable()).getBitmap())
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {

                        Palette.Swatch swatch = null;
                        if (palette.getDarkVibrantSwatch() != null){
                            swatch = palette.getDarkVibrantSwatch();
                        }
                        else if (palette.getDarkMutedSwatch() != null){
                            swatch = palette.getDarkMutedSwatch();
                        }
                        else if (palette.getVibrantSwatch() != null){
                            swatch = palette.getVibrantSwatch();
                        }
                        else if (palette.getMutedSwatch() != null){
                            swatch = palette.getMutedSwatch();
                        }
                        else if (palette.getLightMutedSwatch() != null){
                            swatch = palette.getLightMutedSwatch();
                        }
                        else if (palette.getLightVibrantSwatch() != null){
                            swatch = palette.getLightVibrantSwatch();
                        }
                        else{
                            Palette.Swatch maxPopulationSw = null;
                            List<Palette.Swatch> swatches = palette.getSwatches();
                            for (Palette.Swatch sw :
                                    swatches) {
                                if (sw != null) {
                                    if (maxPopulationSw == null){
                                        maxPopulationSw = sw;
                                    }else {
                                        maxPopulationSw = maxPopulationSw.getPopulation() > sw.getPopulation() ? maxPopulationSw : sw;
                                    }
                                }
                            }
                            swatch = maxPopulationSw;

                        }

//                        Palette.Swatch s1 = palette.getVibrantSwatch();       //获取到充满活力的这种色调
//                        Palette.Swatch s4 = palette.getMutedSwatch();           //获取柔和的色调
//                        Palette.Swatch s2 = palette.getDarkVibrantSwatch();    //获取充满活力的黑
//                        Palette.Swatch s5 = palette.getDarkMutedSwatch();      //获取柔和的黑
//                        Palette.Swatch s3 = palette.getLightVibrantSwatch();   //获取充满活力的亮
//                        Palette.Swatch s6 = palette.getLightMutedSwatch();  //获取柔和的亮

                        if (swatch == null) return;
                        TextView contentTextView = (TextView) findViewById(R.id.content_textview);
                        contentTextView.setBackgroundColor(swatch.getRgb());
                        contentTextView.setTextColor(swatch.getBodyTextColor());
//                        collapsingToolbarLayout.setContentScrimColor(Color.TRANSPARENT);
//                        collapsingToolbarLayout.setContentScrimColor(swatch.getRgb());
//                        collapsingToolbarLayout.setCollapsedTitleTextColor(swatch.getTitleTextColor());
//                        collapsingToolbarLayout.setExpandedTitleColor(swatch.getBodyTextColor());
                    }
                });
    }


}

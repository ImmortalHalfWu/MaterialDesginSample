package com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.glide;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/3/17.
 */

public class GlideHelp {

    private static GlideHelp mGlideHelp;

    public static GlideHelp newInstance(){
        return  mGlideHelp == null ? mGlideHelp = new GlideHelp() : mGlideHelp;
    }

    private GlideHelp() {}

    /**
     *<p> User:    WuImmortalHalf
     *<p> Data:    2017/3/17 10:35
     *<p> Todo:    ( 只加载 )
    */
    public GlideHelp loadImage(@NonNull RequestManager glide, @NonNull String url, @NonNull ImageView imageView){
        glide.load(url).into(imageView);
        return this;
    }

    /**
     *<p> User:    WuImmortalHalf
     *<p> Data:    2017/3/17 10:35
     *<p> Todo:    ( 正常加载，以及占位与成功失败图 )
    */
    public GlideHelp loadImageOrErro(@NonNull RequestManager glide, @NonNull String url, @NonNull ImageView imageView, @DrawableRes int holdRes, @DrawableRes int erroRes){
        glide.load(url).placeholder(holdRes).error(erroRes).into(imageView);
        return this;
    }

    /**
     *<p> User:    WuImmortalHalf
     *<p> Data:    2017/3/17 10:34
     *<p> Todo:    ( 加载gif )
    */
    public GlideHelp loadImageGif(@NonNull RequestManager glide, @NonNull String url, @NonNull ImageView imageView){
        glide.load(url).asGif().into(imageView);
        return this;
    }
    /**
     *<p> User:    WuImmortalHalf
     *<p> Data:    2017/3/17 10:34
     *<p> Todo:    ( 渐入效果 )
    */
    public GlideHelp loadImageFading(@NonNull RequestManager glide, @NonNull String url, @NonNull ImageView imageView,@DrawableRes int holdRes, @DrawableRes int erroRes,int fadeingTime,@Nullable RequestListener listener){
        glide.load(url).error(erroRes).placeholder(holdRes).crossFade(fadeingTime).listener(listener).into(imageView);
        return this;
    }

    /**
     *<p> User:    WuImmortalHalf
     *<p> Data:    2017/3/17 10:34
     *<p> Todo:    ( 高斯模糊效果 radius 模糊度1-25,sampling取样范围，越大，则取样范围越小)
    */
    public GlideHelp loadImageBlur(@NonNull RequestManager glide, @NonNull String url, @NonNull ImageView imageView, @DrawableRes int holdRes, @DrawableRes int erroRes, @NonNull BlurTransformation blurTransformation,@Nullable RequestListener<String, GlideDrawable> listener){
        glide.load(url).error(erroRes).placeholder(holdRes)
                .crossFade(1000)
                .listener(listener)
                .bitmapTransform(blurTransformation).into(imageView);
        return this;
    }

    /**
     *<p> User:    WuImmortalHalf
     *<p> Data:    2017/3/17 10:38
     *<p> Todo:    ( 圆角图 )
    */
    public GlideHelp loadImageCircle(@NonNull RequestManager glide, @NonNull String url, @NonNull ImageView imageView, @DrawableRes int holdRes, @DrawableRes int erroRes, int blurValue, @NonNull RoundedCornersTransformation roundedCornersTransformation){
        glide.load(url).error(erroRes).placeholder(holdRes)
                .bitmapTransform(roundedCornersTransformation).into(imageView);
        return this;
    }

}

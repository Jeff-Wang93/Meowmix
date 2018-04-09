package com.example.jeff.meowmix;

import android.content.Context;
import android.util.AttributeSet;

/** A ImageView subclass used to custom fit images. Used in album_tabxml. Can see final result
 * in the Albums tab.
 *
 */
public class SquareImage extends android.support.v7.widget.AppCompatImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasure, int heightMeasure) {
        super.onMeasure(widthMeasure, heightMeasure);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
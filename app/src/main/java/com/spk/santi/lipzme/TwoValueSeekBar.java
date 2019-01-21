package com.spk.santi.lipzme;

/**
 * Two Values SeekBar
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class TwoValueSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    private Rect rect;
    private Paint paint ;
    private int seekbar_height;

    public TwoValueSeekBar(Context context) {
        super(context);

    }

    public TwoValueSeekBar(Context context, AttributeSet attrs) {

        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        seekbar_height = 6;
    }

    public TwoValueSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        rect.set(5 + getThumbOffset(),
                (getHeight() / 2) - (seekbar_height/2),
                (getWidth()-5)- getThumbOffset(),
                (getHeight() / 2) + (seekbar_height/2));

        paint.setColor(Color.LTGRAY);
        canvas.drawRect(rect, paint);


        if (this.getProgress() > 8) {


            rect.set(getWidth() / 2,
                    (getHeight() / 2) - (seekbar_height/2),
                    getWidth() / 2 + (getWidth() / 17) * (getProgress() - 8),
                    getHeight() / 2 + (seekbar_height/2));

            paint.setColor(Color.parseColor("#FF0099CC"));
            canvas.drawRect(rect, paint);

        }

        if (this.getProgress() < 8) {

            rect.set(getWidth() / 2 - ((getWidth() / 17) * (8 - getProgress())),
                    (getHeight() / 2) - (seekbar_height/2),
                    getWidth() / 2,
                    getHeight() / 2 + (seekbar_height/2));

            paint.setColor(Color.parseColor("#FF0099CC"));
            canvas.drawRect(rect, paint);

        }

        super.onDraw(canvas);
    }
}

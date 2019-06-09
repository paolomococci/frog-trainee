/**
 *
 * Copyright 2019 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.frog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static local.example.frog.R.dimen.positions_default;
import static local.example.frog.R.styleable;

public class KnobView
        extends View {

    private final float[] tempResultFloats = new float[2];
    private final StringBuffer tempLabelStringBuffer = new StringBuffer(8);

    private int defaultNumberOfPositions = positions_default;
    private int activePosition;
    private int knobColorOn;
    private int knobColorOff;

    private float customViewWidth;
    private float customViewHeight;
    private float radius;

    private Paint textViewPaint;
    private Paint circleViewPaint;

    public KnobView(Context context) {
        super(context);
    }

    public KnobView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setUp(attributeSet);
    }

    public KnobView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        setUp(attributeSet);
    }

    public void setPositionCount(int count) {
        this.defaultNumberOfPositions = count;
        this.activePosition = 0;
        circleViewPaint.setColor(knobColorOff);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(
                customViewWidth / 2,
                customViewHeight / 2,
                radius,
                circleViewPaint
        );

        final float labelRadius = radius + 20;
        StringBuffer label = tempLabelStringBuffer;
        for (int i = 0; i < defaultNumberOfPositions; i++) {
            float[] coordinates = arrange(i, labelRadius, true);
            float x = coordinates[0];
            float y = coordinates[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(
                    label,
                    0,
                    label.length(),
                    x,
                    y,
                    textViewPaint
            );
        }

        final float marker = radius - 35;
        float[] coordinates = arrange(
                activePosition,
                marker,
                false
        );
        float x = coordinates[0];
        float y = coordinates[1];
        canvas.drawCircle(
                x,
                y,
                20,
                textViewPaint
        );
    }

    @Override
    protected void onSizeChanged(
            int width,
            int height,
            int oldWidth,
            int oldHeight
    ) {
        customViewWidth = width;
        customViewHeight = height;
        radius = (float) (Math.min(customViewWidth, customViewHeight) / 2 * 0.8);
    }

    private float[] arrange(
            final int position,
            final float radius,
            boolean isLabel
    ) {
        float[] results;
        results = tempResultFloats;
        double startAngle;
        double angle;
        if (defaultNumberOfPositions > 4) {
            startAngle = Math.PI * (3 / 2d);
            angle = startAngle + (position * (Math.PI / defaultNumberOfPositions));
            results[0] = (float) (radius * Math.cos(angle * 2)) + (customViewWidth / 2);
            results[1] = (float) (radius * Math.sin(angle * 2)) + (customViewHeight / 2);
            if ((angle > Math.toRadians(360)) && isLabel) {
                results[1] += 20;
            }
        } else {
            startAngle = Math.PI * (9 / 8D);
            angle = startAngle + (position * (Math.PI / defaultNumberOfPositions));
            results[0] = (float) (radius * Math.cos(angle)) + (customViewWidth / 2);
            results[1] = (float) (radius * Math.sin(angle)) + (customViewHeight / 2);
        }
        return results;
    }

    private void setUp(
            AttributeSet attrs
    ) {
        knobColorOn = Color.GREEN;
        knobColorOff = Color.DKGRAY;

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(
                    attrs,
                    styleable.KnobView,
                    0,
                    0
            );

            knobColorOn = typedArray
                    .getColor(
                            styleable.KnobView_knob_color_on,
                            knobColorOn);
            knobColorOff = typedArray
                    .getColor(
                            styleable.KnobView_knob_color_off,
                            knobColorOff);
            defaultNumberOfPositions = typedArray
                    .getInt(
                            styleable.KnobView_knob_indicators,
                            defaultNumberOfPositions);
            typedArray.recycle();
        }

        textViewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textViewPaint.setColor(Color.BLACK);
        textViewPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textViewPaint.setTextAlign(Paint.Align.CENTER);
        textViewPaint.setTextSize(40F);
        circleViewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleViewPaint.setColor(knobColorOff);
        activePosition = 0;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activePosition = (activePosition + 1) % defaultNumberOfPositions;
                if (activePosition >= 1) {
                    circleViewPaint.setColor(knobColorOn);
                } else {
                    circleViewPaint.setColor(knobColorOff);
                }
                invalidate();
            }
        });
    }
}

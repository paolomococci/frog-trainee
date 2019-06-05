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

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import java.util.Objects;

public class WaterCircleAnimationView
        extends View {

    private static final int DURATION = 2000;
    private static final int ADJUST = 7;
    private static final long DELAY = 500L;

    private final Paint paint = new Paint();

    private float abscissaAxis;
    private float ordinateAxis;
    private float radius;

    private AnimatorSet animatorSet = new AnimatorSet();

    public WaterCircleAnimationView(Context context) {
        super(context);
    }

    public WaterCircleAnimationView(
            Context context,
            @Nullable AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public WaterCircleAnimationView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(abscissaAxis, ordinateAxis, radius, paint);
    }

    @Override
    public void onSizeChanged(
            int width,
            int height,
            int oldWidth,
            int oldHeight
    ) {
        /* expansion */
        ObjectAnimator expansionObjectAnimator = ObjectAnimator
                .ofFloat(
                        this,
                        "radius",
                        0,
                        getWidth()
                );
        expansionObjectAnimator.setDuration(DURATION);
        expansionObjectAnimator.setInterpolator(new LinearInterpolator());

        /* contraction */
        ObjectAnimator contractionObjectAnimator = ObjectAnimator
                .ofFloat(
                        this,
                        "radius",
                        getWidth(),
                        0
                );
        contractionObjectAnimator.setDuration(DURATION);
        contractionObjectAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        contractionObjectAnimator.setStartDelay(DELAY);

        /* repetition */
        ObjectAnimator repetitionObjectAnimator = ObjectAnimator
                .ofFloat(
                        this,
                        "radius",
                        0, getWidth()
                );
        repetitionObjectAnimator.setStartDelay(DELAY);
        repetitionObjectAnimator.setDuration(DURATION);
        repetitionObjectAnimator.setRepeatCount(3);
        repetitionObjectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.play(expansionObjectAnimator)
                .before(contractionObjectAnimator);
        animatorSet.play(repetitionObjectAnimator)
                .after(contractionObjectAnimator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
            abscissaAxis = motionEvent.getX();
            ordinateAxis = motionEvent.getY();
            if(animatorSet != null && animatorSet.isRunning()) {
                animatorSet.cancel();
            }
            Objects.requireNonNull(animatorSet).start();
        }
        return super.onTouchEvent(motionEvent);
    }

    private void setRadius(float radius) {
        this.radius = radius;
        paint.setColor(Color.argb(
                255,
                235,
                245,
                252
        ) + (int) radius / ADJUST);
        invalidate();
    }
}

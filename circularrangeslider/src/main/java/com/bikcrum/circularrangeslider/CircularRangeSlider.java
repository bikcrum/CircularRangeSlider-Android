package com.bikcrum.circularrangeslider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class CircularRangeSlider extends View {
    //attributes
    private int max;
    private float stepLength;
    private CharSequence[] labels;
    private int labelColor;
    private float labelSize;
    private int labelInterval;
    private boolean hideLabel;
    private boolean hideZero;

    private int circleColor;

    private int borderColor;
    private float borderWidth;

    private int sectorColor;

    private ColorStateList sliderColor;
    private float sliderWidth;
    private float sliderLength;

    private float axisRadius;
    private int axisColor;

    private int startFrom;
    private float startAngle;
    private float endAngle;

    private float startIndexStepLength;
    private float startIndexStepWidth;
    private int startIndexStepColor;

    private float progress;
    private boolean progressEnabled;
    private int progressColor;
    private float progressLength;

    private int startIndex;
    private int endIndex;

    //non user required
    private final Paint paint = new Paint();
    private float centerX;
    private float centerY;
    private float radius;
    private float stepsGap;
    private final RectF ovalBigArc = new RectF();
    private float startThumbCenterX;
    private float endThumbCenterX;
    private float endThumbCenterY;
    private float startThumbCenterY;
    private float startThumbAngle;
    private float endThumbAngle;
    private OnRangeChangeListener onRangeChangeListener = null;
    private int startIndexOld = -1;
    private int endIndexOld = -1;
    private final int[] mTempStates = new int[2];
    private float transformAngle;
    private final Rect bounds = new Rect();

    private final String TAG = "demo";

    private int getCircleColor() {
        return circleColor;
    }

    public void setAxisColor(int axisColor) {
        this.axisColor = axisColor;
        invalidate();
    }


    public void setLabelVisibility(int visibility) {
        hideLabel = visibility != View.VISIBLE;
        invalidate();
    }


    private static class Gravity {
        private static final int TOP = 1;
        private static final int BOTTOM = 3;
        private static final int RIGHT = 2;
        private static final int LEFT = 4;
    }

    public interface OnRangeChangeListener {
        void onRangePress(int startIndex, int endIndex);
        void onRangeChange(int startIndex, int endIndex);
        void onRangeRelease(int startIndex, int endIndex);
    }

    public void setProgressEnabled(boolean progressEnabled) {
        this.progressEnabled = progressEnabled;
    }

    public void setOnRangeChangeListener(OnRangeChangeListener onRangeChangeListener) {
        this.onRangeChangeListener = onRangeChangeListener;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public CircularRangeSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setMax(int max) {
        if (max < 3) {
            max = 3;
        }
        this.max = max;
        stepsGap = (endAngle - startAngle) / max;

        if (endIndex >= max) {
            endIndex = max - 2;
        }

        startThumbAngle = startIndex * stepsGap;
        endThumbAngle = endIndex * stepsGap;

        if (onRangeChangeListener != null) {
            onRangeChangeListener.onRangeChange(startIndex, endIndex);
        }
        setStartIndex(startIndex);
        setEndIndex(endIndex);
        invalidate();
    }


    public void setStepLength(float stepLength) {
        this.stepLength = stepLength;
        invalidate();
    }

    public void setlabels(String[] labels) {
        this.labels = labels;
        invalidate();
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidate();
    }

    private int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    private int getSectorColor() {
        return sectorColor;
    }

    public void setSectorColor(int sectorColor) {
        this.sectorColor = sectorColor;
        invalidate();
    }


    public void setSliderColor(ColorStateList sliderColor) {
        this.sliderColor = sliderColor;
        invalidate();
    }

    public void setSliderWidth(float sliderWidth) {
        this.sliderWidth = sliderWidth;
        invalidate();
    }

    public void setSliderLength(float sliderLength) {
        this.sliderLength = sliderLength;
        invalidate();
    }

    public void setAxisRadius(float axisRadius) {
        this.axisRadius = axisRadius;
        invalidate();
    }

    public void setStartFrom(int startFrom) {
        this.startFrom = startFrom;
        switch (startFrom) {
            case Gravity.TOP:
                transformAngle = 270;
                break;
            case Gravity.BOTTOM:
                transformAngle = 90;
                break;
            case Gravity.LEFT:
                transformAngle = 180;
                break;
            case Gravity.RIGHT:
                transformAngle = 0;
        }
        invalidate();
    }

    public void setStartIndexStepLength(float startIndexStepLength) {
        this.startIndexStepLength = startIndexStepLength;
        invalidate();
    }

    public void setStartIndexStepWidth(float startIndexStepWidth) {
        this.startIndexStepWidth = startIndexStepWidth;
        invalidate();
    }

    private int getStartIndexStepColor() {
        return startIndexStepColor;
    }

    public void setStartIndexStepColor(int startIndexStepColor) {
        this.startIndexStepColor = startIndexStepColor;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }

    public boolean isProgressEnabled() {
        return progressEnabled;
    }

    private int getProgressColor() {
        return progressColor;
    }

    private void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }


    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        startIndex = Math.min(Math.max(startIndex, 0), max);
        this.startIndex = startIndex;
        startThumbAngle = startAngle + (startIndex * stepsGap);
        endThumbAngle = startAngle + (endIndex * stepsGap);
        invalidate();
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        endIndex = Math.min(Math.max(endIndex, 0), max);
        this.endIndex = endIndex;
        startThumbAngle = startAngle + (startIndex * stepsGap);
        endThumbAngle = startAngle + (endIndex * stepsGap);
        invalidate();
    }
    public int getLabelInterval() {
        return labelInterval;
    }
    public void setLabelInterval(int labelInterval) {
        this.labelInterval = labelInterval;
        invalidate();
    }
    public void setLabelColor(int color) {
        labelColor = color;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularRangeSlider, 0, 0);

        max = a.getInteger(R.styleable.CircularRangeSlider_max, 12);
        stepLength = a.getDimension(R.styleable.CircularRangeSlider_stepLength, 10);
        labels = a.getTextArray(R.styleable.CircularRangeSlider_labels);
        labelColor = a.getColor(R.styleable.CircularRangeSlider_labelColor, Color.parseColor("#ffff00"));
        labelSize = a.getDimension(R.styleable.CircularRangeSlider_labelSize, 30);
        labelInterval = a.getInteger(R.styleable.CircularRangeSlider_labelInterval, 1);
        hideLabel = a.getBoolean(R.styleable.CircularRangeSlider_hideLabel, false);
        hideZero = a.getBoolean(R.styleable.CircularRangeSlider_hideZero, false);

        circleColor = a.getColor(R.styleable.CircularRangeSlider_circleColor, Color.parseColor("#4db6ac"));

        borderColor = a.getColor(R.styleable.CircularRangeSlider_borderColor, Color.parseColor("#e0e0e0"));
        borderWidth = a.getDimension(R.styleable.CircularRangeSlider_borderWidth, 5);

        sectorColor = a.getColor(R.styleable.CircularRangeSlider_sectorColor, Color.parseColor("#45000000"));

        sliderColor = a.getColorStateList(R.styleable.CircularRangeSlider_sliderColor);

        sliderWidth = a.getDimension(R.styleable.CircularRangeSlider_sliderWidth, 5);
        sliderLength = a.getDimension(R.styleable.CircularRangeSlider_sliderLength, 20);

        axisRadius = a.getDimension(R.styleable.CircularRangeSlider_axisRadius, 7.5f);
        axisColor = a.getColor(R.styleable.CircularRangeSlider_axisColor, Color.WHITE);

        startFrom = a.getInteger(R.styleable.CircularRangeSlider_startFrom, Gravity.TOP);
        startAngle = a.getFloat(R.styleable.CircularRangeSlider_startAngle, 0f);
        endAngle = a.getFloat(R.styleable.CircularRangeSlider_endAngle, 360f);

        startIndexStepLength = a.getDimension(R.styleable.CircularRangeSlider_startIndexStepLength, stepLength);
        startIndexStepWidth = a.getDimension(R.styleable.CircularRangeSlider_startIndexStepWidth, borderWidth * 1.5f);
        startIndexStepColor = a.getColor(R.styleable.CircularRangeSlider_startIndexStepColor, Color.WHITE);

        progress = a.getFloat(R.styleable.CircularRangeSlider_progress, 0);
        progressEnabled = a.getBoolean(R.styleable.CircularRangeSlider_progressEnabled, false);
        progressColor = a.getColor(R.styleable.CircularRangeSlider_progressColor, Color.WHITE);
        progressLength = a.getFloat(R.styleable.CircularRangeSlider_progressLength, 1);

        startIndex = a.getInt(R.styleable.CircularRangeSlider_startIndex, 0);
        endIndex = a.getInt(R.styleable.CircularRangeSlider_endIndex, 1);

        boolean enabled = a.getBoolean(R.styleable.CircularRangeSlider_enabled, true);

        setEnabled(enabled);

        if (max < 3) {
            max = 3;
        }

        if (endIndex >= max) {
            endIndex = max - 1;
        }

        stepsGap = (endAngle - startAngle) / max;

        switch (startFrom) {
            case Gravity.BOTTOM:
                transformAngle = 90;
                break;
            case Gravity.LEFT:
                transformAngle = 180;
                break;
            case Gravity.RIGHT:
                transformAngle = 0;
                break;
            default:
                transformAngle = 270;
                break;
        }

        startThumbAngle = startAngle + (startIndex * stepsGap);
        endThumbAngle = startAngle + (endIndex * stepsGap);

        a.recycle();

    }

    private Path generateStepsPath() {
        Path path = new Path();

        for (int i = 0; i < max; i++) {
            double angRad = Math.toRadians(startAngle + (i * stepsGap));
            float startX = centerX + (float) (radius * Math.cos(angRad));
            float startY = centerY + (float) (radius * Math.sin(angRad));
            float stopX = centerX + (float) ((radius - (i == 0 ? startIndexStepLength : stepLength)) * Math.cos(angRad));
            float stopY = centerY + (float) ((radius - (i == 0 ? startIndexStepLength : stepLength)) * Math.sin(angRad));
            //     canvas.drawLine(startX, startY, stopX, stopY, paint);

            path.moveTo(startX, startY);
            path.lineTo(stopX, stopY);

        }

        return path;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(transformAngle, centerX, centerY);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        //background
        paint.setColor(getCircleColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);

        //sector
        float sweepAngle;
        if (startThumbAngle >= endThumbAngle) {
            sweepAngle = 360 - startThumbAngle + endThumbAngle;
        } else {
            sweepAngle = endThumbAngle - startThumbAngle;
        }

        paint.setColor(getSectorColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(ovalBigArc,
                startThumbAngle,
                sweepAngle,
                true,
                paint);

        //steps
        paint.setColor(getBorderColor());
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i <= max; i++) {
            double angRad = Math.toRadians(startAngle + (i * stepsGap));
            if (i == 0) {
                paint.setColor(getStartIndexStepColor());
                paint.setStrokeWidth(startIndexStepWidth);
            } else {
                paint.setColor(getBorderColor());
                paint.setStrokeWidth(borderWidth);
            }
            float startX = centerX + (float) (radius * Math.cos(angRad));
            float startY = centerY + (float) (radius * Math.sin(angRad));
            float stopX = centerX + (float) ((radius - (i == 0 ? startIndexStepLength : stepLength)) * Math.cos(angRad));
            float stopY = centerY + (float) ((radius - (i == 0 ? startIndexStepLength : stepLength)) * Math.sin(angRad));
            canvas.drawLine(startX, startY, stopX, stopY, paint);

            //labels
            boolean drawLabel = (i % labelInterval == 0);
            drawLabel &= !((i == 0) && hideZero);
            drawLabel &= !hideLabel;

            if (drawLabel) {
                String label;

                if (labels != null) {
                    if (i >= 0 && i < labels.length) {
                        label = labels[i].toString();
                    } else {
                        label = "";
                    }
                } else {
                    label = String.valueOf(i);
                }

                paint.setColor(labelColor);
                paint.setTextSize(labelSize);

                paint.getTextBounds(label, 0, label.length(), bounds);

                double boundRadius = Math.sqrt(bounds.width() * bounds.width() + bounds.height() * bounds.height()) / 2;
                float padding;
                if (i == 0) {
                    padding = startIndexStepLength;
                } else {
                    padding = stepLength;
                }
                int x = (int) (centerX + Math.cos(angRad + Math.toRadians(transformAngle)) * (radius - padding - boundRadius));
                int y = (int) (centerY + Math.sin(angRad + Math.toRadians(transformAngle)) * (radius - padding - boundRadius));
                canvas.rotate(-transformAngle, centerX, centerY);
                canvas.drawText(label, x - (float) bounds.width() / 2, y + (float) bounds.height() / 2, paint);
                canvas.rotate(transformAngle, centerX, centerY);
            }
        }

        //border
        paint.setColor(getBorderColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);

        //start thumb
        float cosineOfStartThumbAngle = (float) Math.cos(Math.toRadians(startThumbAngle));
        float sineOfStartThumbAngle = (float) Math.sin(Math.toRadians(startThumbAngle));
        float cosineOfEndThumbAngle = (float) Math.cos(Math.toRadians(endThumbAngle));
        float sineOfEndThumbAngle = (float) Math.sin(Math.toRadians(endThumbAngle));

        //start thumb
        startThumbCenterX = centerX + radius * cosineOfStartThumbAngle;
        startThumbCenterY = centerY + radius * sineOfStartThumbAngle;
        paint.setColor(getSliderColor(touchedOnStartThumb));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(sliderWidth);
        canvas.drawLine(
                startThumbCenterX,
                startThumbCenterY,
                centerX + (radius + sliderLength) * cosineOfStartThumbAngle,
                centerY + (radius + sliderLength) * sineOfStartThumbAngle,
                paint);


        //end thumb
        endThumbCenterX = centerX + radius * cosineOfEndThumbAngle;
        endThumbCenterY = centerY + radius * sineOfEndThumbAngle;
        paint.setColor(getSliderColor(touchedOnEndThumb));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(sliderWidth);
        canvas.drawLine(
                endThumbCenterX,
                endThumbCenterY,
                centerX + (radius + sliderLength) * cosineOfEndThumbAngle,
                centerY + (radius + sliderLength) * sineOfEndThumbAngle,
                paint);


        //progress
        paint.setStyle(Paint.Style.FILL);
        if (progressEnabled) {
            float angRad = (float) Math.toRadians(startAngle + (progress * stepsGap));
            float stopX = centerX + (float) ((radius * progressLength) * Math.cos(angRad));
            float stopY = centerY + (float) ((radius * progressLength) * Math.sin(angRad));
            paint.setColor(getProgressColor());
            paint.setStrokeWidth(sliderWidth);
            canvas.drawLine(centerX, centerY, stopX, stopY, paint);
        }

        //center axis
        paint.setColor(getAxisColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, axisRadius, paint);
    }

    private int getAxisColor() {
        return axisColor;
    }

    private boolean touchedOnStartThumb;
    private boolean touchedOnEndThumb;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return super.onTouchEvent(event);
        }
        float x = event.getX();
        float y = event.getY();

        float x1 = x - centerX;
        float y1 = y - centerY;

        float x2 = (float) (x1 * Math.cos(Math.toRadians(-transformAngle)) - y1 * Math.sin(Math.toRadians(-transformAngle)));
        float y2 = (float) (x1 * Math.sin(Math.toRadians(-transformAngle)) + y1 * Math.cos(Math.toRadians(-transformAngle)));

        x = x2 + centerX;
        y = y2 + centerY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!registerNearestThumb(x, y)) {
                    return super.onTouchEvent(event);
                }
                moveThumb(x, y);
                if (onRangeChangeListener != null) {
                    onRangeChangeListener.onRangePress(startIndex, endIndex);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveThumb(x, y);
                break;
            case MotionEvent.ACTION_UP:
                moveThumb(x, y);
                if (onRangeChangeListener != null) {
                    onRangeChangeListener.onRangeRelease(startIndex, endIndex);
                }
                touchedOnStartThumb = false;
                touchedOnEndThumb = false;
                break;
            default:
                return super.onTouchEvent(event);
        }
        invalidate();
        return true;

    }

    private boolean registerNearestThumb(float x, float y) {
        double distanceToStartThumb = distance(x, y, startThumbCenterX, startThumbCenterY);
        double distanceToEndThumb = distance(x, y, endThumbCenterX, endThumbCenterY);
        if (distanceToStartThumb < distanceToEndThumb) {
            touchedOnStartThumb = true;
        } else {
            touchedOnEndThumb = true;
        }
        return true;
    }

    private void moveThumb(float x, float y) {
        if (touchedOnStartThumb) {
            startThumbAngle = getNearestAngle(x, y);
        } else if (touchedOnEndThumb) {
            endThumbAngle = getNearestAngle(x, y);
        }
        int maxIndex = Math.round((endAngle - startAngle) / stepsGap);
        startIndex = Math.round((startThumbAngle - startAngle) / stepsGap);
        endIndex = Math.round((endThumbAngle - startAngle) / stepsGap);

        if (startIndex >= endIndex)
        {
            if (touchedOnStartThumb)
            {
                if (endIndex < maxIndex)
                {
                    endIndex = startIndex + 1;
                    endThumbAngle = startAngle + (endIndex * stepsGap);
                }
                else
                {
                    startIndex = endIndex - 1;
                    startThumbAngle = startAngle + (startIndex * stepsGap);
                }
            }
            else if (touchedOnEndThumb)
            {
                if (startIndex > 0)
                {
                    startIndex = endIndex - 1;
                    startThumbAngle = startAngle + (startIndex * stepsGap);
                }
                else
                {
                    endIndex = startIndex + 1;
                    endThumbAngle = startAngle + (endIndex * stepsGap);
                }
            }
        }

        if ((startIndex < 0))
        {
            startIndex = 0;
            startThumbAngle = startAngle;
        }

        if (endIndex > maxIndex)
        {
            endIndex = maxIndex;
            endThumbAngle = endAngle;
        }

        if (startIndex != startIndexOld || endIndex != endIndexOld) {
            if (onRangeChangeListener != null) {
                onRangeChangeListener.onRangeChange(startIndex, endIndex);
            }
        }
        startIndexOld = startIndex;
        endIndexOld = endIndex;
    }

    private float getNearestAngle(float x, float y) {

        float angle = (float)(Math.toDegrees(Math.atan2(y - centerY, x - centerX)));
        if (angle < 0) angle += 360;

        float angleCeil = ((float)Math.ceil((angle - startAngle) / stepsGap) * stepsGap) + startAngle;
        float angleFloor = ((float)Math.floor((angle - startAngle) / stepsGap) * stepsGap) + startAngle;

        if (Math.abs(angle - angleCeil) < Math.abs(angle - angleFloor)) {
            return angleCeil < 0 ? angleCeil + 360 : angleCeil;
        } else {
            return angleFloor < 0 ? angleFloor + 360 : angleFloor;
        }

    }


    private double distance(float x1, float y1, float x2, float y2) {
        float delx = x1 - x2;
        float dely = y1 - y2;
        return Math.sqrt(delx * delx + dely * dely);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w < h) {
            radius = (float) w / 2;
            centerX = radius;
            centerY = (float) h / 2;
        } else {
            radius = (float) h / 2;
            centerX = (float) w / 2;
            centerY = radius;
        }
        ovalBigArc.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        //providing padding to move slider
        radius -= sliderLength;
    }

    void debug(String[] names, String... values) {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                a.append("\n");
            }
            a.append(names[i]).append(" = ").append(values[i]);
        }
        // Log.i(TAG, a);
    }

    void debug(String[] names, Integer... values) {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                a.append("\n");
            }
            a.append(names[i]).append(" = ").append(values[i]);
        }
        // Log.i(TAG, a);
    }

    void debug(String[] names, Float... values) {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                a.append("\n");
            }
            a.append(names[i]).append(" = ").append(values[i]);
        }
        // Log.i(TAG, a);
    }

    private int getSliderColor(boolean pressed) {
        if (sliderColor == null) {
            if (pressed) {
                return Color.parseColor("#e3fae6ab");
            }
            return Color.parseColor("#e3ffca28");
        } else {
            mTempStates[0] = android.R.attr.state_enabled;
            mTempStates[1] = pressed ? android.R.attr.state_pressed : -android.R.attr.state_pressed;
            return sliderColor.getColorForState(mTempStates, 0);
        }
    }

    public boolean isProgressInsideRange() {
        if (!progressEnabled) {
            return false;
        }

        if (startIndex == 0) {
            return progress < endIndex;
        } else if (endIndex == 0) {
            return progress >= startIndex;
        } else if (startIndex < endIndex) {
            return progress >= startIndex && progress < endIndex;
        } else if (startIndex > endIndex) {
            return progress >= startIndex || progress < endIndex;
        } else return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth = Math.min(widthSize, heightSize);
        int desiredHeight = Math.min(widthSize, heightSize);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = desiredHeight;
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }
}

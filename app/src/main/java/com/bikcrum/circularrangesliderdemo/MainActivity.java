
package com.bikcrum.circularrangesliderdemo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bikcrum.circularrangeslider.CircularRangeSlider;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CircularRangeSlider mCircularRangeSlider;
    private TextView mStartIndex;
    private TextView mEndIndex;
    private TextView mProgress;
    private TextView mInsideRange;
    private TextView mMax;
    private SeekBar mSeekbarMax;
    private CheckBox mCheckboxProgress;
    private CheckBox mCheckboxLabel;
    private SeekBar mSeekbarStepLength;

    private final String TAG = "demo";

    private float i = 0;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircularRangeSlider = findViewById(R.id.circular_range_slider);
        mStartIndex = findViewById(R.id.start_index);
        mEndIndex = findViewById(R.id.end_index);
        mProgress = findViewById(R.id.progress);
        mInsideRange = findViewById(R.id.in_range);
        mMax = findViewById(R.id.max);
        mSeekbarMax = findViewById(R.id.seekbar);
        mCheckboxProgress = findViewById(R.id.checkbox_progress);
        mCheckboxLabel = findViewById(R.id.checkbox_label);
        mSeekbarStepLength = findViewById(R.id.step_length);

        mCircularRangeSlider.setOnRangeChangeListener(new CircularRangeSlider.OnRangeChangeListener() {
            @Override
            public void onRangePress(int startIndex, int endIndex) {

            }

            @Override
            public void onRangeChange(int startIndex, int endIndex) {
                Log.i(TAG, "start index = " + startIndex + " , " + "end index = " + endIndex);
                mStartIndex.setText("Start index: " + startIndex);
                mEndIndex.setText("End index: " + endIndex);
            }

            @Override
            public void onRangeRelease(int startIndex, int endIndex) {

            }
        });

        mSeekbarMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //minimum number of supported step is 3
                if (progress < 3) {
                    progress = 3;
                }
                mCircularRangeSlider.setMax(progress);
                mMax.setText("Max steps (" + progress + ")");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mCircularRangeSlider.setProgressEnabled(true);

        runnable = new Runnable() {
            @Override
            public void run() {
                mCircularRangeSlider.setProgress(i);
                mProgress.setText(String.format(Locale.ENGLISH, "Progress: %.1f\n(Customizable)", i));
                i = (i + 0.05f) % mCircularRangeSlider.getMax();

                if (mCircularRangeSlider.isProgressInsideRange()) {
                    mInsideRange.setText("Inside Range: True");
                } else {
                    mInsideRange.setText("Inside Range: False");
                }

                handler.postDelayed(this, 10);
            }
        };
        handler.post(runnable);

        mCheckboxProgress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mCircularRangeSlider.setProgressEnabled(checked);
                if (checked) {
                    handler.post(runnable);
                } else {
                    handler.removeCallbacks(runnable);
                }
            }
        });

        mCheckboxLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mCircularRangeSlider.setLabelVisibility(View.VISIBLE);
                } else {
                    mCircularRangeSlider.setLabelVisibility(View.GONE);
                }
            }
        });

        mSeekbarStepLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircularRangeSlider.setStepLength(progress);
                mCircularRangeSlider.setStartIndexStepLength(progress * 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int getTextColor(int selectedColor) {
        double L = 0.2126 * Color.red(selectedColor) + 0.7152 * Color.green(selectedColor) + 0.0722 * Color.blue(selectedColor);
        if (L > 179) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    public void changeCircleColor(final View view) {
        ColorPickerDialogBuilder
                .with(MainActivity.this)
                .setTitle("Choose color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        mCircularRangeSlider.setCircleColor(selectedColor);
                        view.setBackgroundColor(selectedColor);
                        ((Button) view).setTextColor(getTextColor(selectedColor));
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    public void changeLabelColor(final View view) {
        ColorPickerDialogBuilder
                .with(MainActivity.this)
                .setTitle("Choose color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        mCircularRangeSlider.setLabelColor(selectedColor);
                        view.setBackgroundColor(getTextColor(selectedColor));
                        ((Button) view).setTextColor(selectedColor);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
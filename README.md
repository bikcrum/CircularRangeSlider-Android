# CircularRangeSliderDemo

★ Contains two thumbs that slides in circle to provide the range.
★ You can add as many as steps as per your need.
★ Add running progress to it
★ Use custom label for each step
★ Know whether the progress is inside the range or not just from build-in functions
★ Change color of circle, progress, steps, axis etc
★ Change width and length of border, steps, axis etc

<a href="https://thumbs.gfycat.com/PoisedSlimyHyracotherium-size_restricted"><img src="https://thumbs.gfycat.com/PoisedSlimyHyracotherium-size_restricted.gif" title="Circular Range Slider Demo"/></a>

## Attributes

| Attr  | format | decription |
| ------------- | ------------- | ------------- |
| max  | integer  | Maximum number of steps, 3 to above |
| stepLength  | dimension  | Change length of step  |
| labels  | reference  | An array entries for label in each step, Default is indices  |
| label_color  | color  | Default is "#ffff00" |
| labelSize  | dimension  | Default is 30 px |
| hideLabel  | boolean  | Default is false |
| circleColor  | color  | Default is "#4db6ac" (greenish) |
| borderColor  | color  | Default is "#e0e0e0"  |
| borderWidth | dimension  | 5px  |
| sectorColor  | color  | Sector is formed between sliders region, Default color is "#45000000" (Note: Provide non-opaque color)  |
| sliderColor  | color, colorState  | Default is yellowish |
| sliderWidth  | dimension  | Default is 5 px  |
|  sliderLength | dimension  | Default is 20 px |
|  axisRadius | dimension  | Center, Default is 8 px  |
|  axisColor | color  | Center, Default is white |
| startFrom  | integer  | Where startIndex should locate. Default is top among (top,bottom,left,right)  |
| startIndexStepLength | dimension  | Default is twice stepLength  |
| startIndexStepWidth  | dimension  | Default is 1.5 times borderWidth  |
| progress | float  | Default is 0  |
| progressEnabled  | boolean  | Default is false |
| progressColor  | color  | Default is "#d50000" (reddish)  |
| startIndex | integer  | Default is 0 |
| endIndex  | integer  | Default is 1 |

# How to use

### Add root build.gradle
```
repositories {
     maven {
         url "https://jitpack.io"
     }
}
```

### Add build.gradle
```
dependencies {
    compile 'com.github.giliy:CircularTimerClock-Android:0.1.1'
}
```

### To use it in your code
Simply add the View to your layout xml

```
 <giliy.com.circulartimerclock.CircularTimerClock
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/circular_clock"
        android:layout_gravity="center"
        timer:arc_dash_size="40dp"
        timer:start_hour="5"
        timer:start_minutes="20"
        timer:end_hour="9"
        timer:end_minutes="30"
        timer:clock_time_interval="5"
        timer:clock_tick_interval="15"
        timer:clock_hour_color="#FFF"
        timer:clock_tick_color="#FFF"
        timer:hours_size="18sp"
        timer:start_time_is_am="true"
        timer:end_time_is_am="false"
        timer:is_clock_inside="true"
        timer:border_thickness="40dp"
        timer:border_color="#e7e7e7"
        timer:arc_gradient_color_start="#78f8b8"
        timer:arc_gradient_color_end="#00964b"
        timer:clock_background_image="@drawable/sunrise"
        timer:start_thumb_image="@drawable/circle_toggle_shape"
        timer:end_thumb_image="@drawable/circle_toggle_shape"
        timer:thumb_size="35dp" >

    </giliy.com.circulartimerclock.CircularTimerClock>
```
To retrieve the time simply call the view from your activity like this
```java
CircularTimerClock clock = (CircularTimerClock) findViewById(R.id.circular_clock);
        clock.setOnTimeChangedListener(new CircularTimerClock.ontTimeChanged() {
            @Override
            public void onStartTimeChange(String time, int hour, int minutes,boolean isAM) {
                Log.d("time: ",""+time);

            }

            @Override
            public void onEndTimeChange(String time, int hour, int minutes, boolean isAM) {
                Log.d("time: ",""+time);
            }
        });
```

# CircularRangeSliderDemo

★ Contains two thumbs that slides in circle to provide the range</br>
★ You can add as many as steps as per your need</br>
★ Add running progress to it</br>
★ Use custom label for each step</br>
★ Know whether the progress is inside the range or not just from build-in functions</br>
★ Change color of circle, progress, steps, axis etc</br>
★ Change width and length of border, steps, axis etc</br>

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
   
}
```

### To use it in your code
Simply add the View to your layout xml

```
<com.bikcrum.circularrangeslider.CircularRangeSlider
     android:id="@+id/circular_range_slider"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content">
        
</com.bikcrum.circularrangeslider.CircularRangeSlider>
```

### Add listener
```java

CircularRangeSlider mCircularRangeSlider.setOnRangeChangeListener(new CircularRangeSlider.OnRangeChangeListener() {
     @Override 
     public void onRangePress(int startIndex, int endIndex) {
          //called when user starts changing slider
     }

     @Override
     public void onRangeChange(int startIndex, int endIndex) {
          //called while user is changing slider
     }

     @Override
     public void onRangeRelease(int startIndex, int endIndex) {
          //called when user releases slider
     }
});
```
###Enable Progress
```
<com.bikcrum.circularrangeslider.CircularRangeSlider
     android:id="@+id/circular_range_slider"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     app:progressEnabled="true">
        
</com.bikcrum.circularrangeslider.CircularRangeSlider>
```
### Change Progress Example
```java
float i = 0.0f; /* Globally declared */

//This will increment progress by 0.5 every 1 second.
Handler handler = new Handler();
handler.post(new Runnable() {
     @Override
     public void run() {
          //set progress
          mCircularRangeSlider.setProgress(i);
               
          //progress can't go beyond maximum steps however go in a loop
          i = (i + 0.5f) % mCircularRangeSlider.getMax();

          //repeat above after 1 second
          handler.postDelayed(this, 1000);
     }
});
```

### Example of all the attributes

```
  <com.bikcrum.circularrangeslider.CircularRangeSlider
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:axisColor="#00ffef"
        app:axisRadius="10dp"
        app:borderColor="#00ffef"
        app:borderWidth="5dp"
        app:circleColor="#4b4b4b"
        app:enabled="true"
        app:endIndex="3"
        app:hideLabel="false"
        app:labelColor="#00ffef"
        app:labelSize="20sp"
        app:labels="@array/labels"
        app:max="7"
        app:progress="8.5"
        app:progressColor="#ffffff"
        app:progressEnabled="true"
        app:sectorColor="#54fc888c"
        app:sliderColor="#ff0008"
        app:sliderLength="20dp"
        app:sliderWidth="8dp"
        app:startFrom="top"
        app:startIndex="1"
        app:startIndexStepColor="#00ffef"
        app:startIndexStepLength="20dp"
        app:startIndexStepWidth="8dp"
        app:stepLength="8dp">

    </com.bikcrum.circularrangeslider.CircularRangeSlider>
```
### which looks like this 
<a href="https://imgbb.com/"><img src="https://image.ibb.co/iVMJ2m/lookslikethis.png" alt="lookslikethis" border="0"></a>

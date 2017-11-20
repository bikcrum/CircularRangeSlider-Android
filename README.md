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

### Add listeners
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

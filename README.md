[![](https://jitpack.io/v/vansikrishna/Multimager.svg)](https://jitpack.io/#vansikrishna/Multimager) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Multimager-orange.svg?style=flat-square)](https://android-arsenal.com/details/1/5241)

# Multimager
Multi Image Picker and Multi Image Capture Demo app
This is a sample demonstration for multiple images capture as well as multiple image picker. UX/UI can be styled with any color with relativity to Material Design. The demo shows tinting multiple views based on theme color!!

# Library
Add the jitpack repo to your repositories section in root level build.gradle

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

Add the dependencies to the app level build.gradle

    dependencies {
        compile 'com.github.vansikrishna:Multimager:1.0.3'
    }

# Screenshots
![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/demo.gif)
![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/3 - small.png)
![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/9 - small.png)
![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/11 - small.png)

# Create intent for multi-selection
	Intent intent = new Intent(this, GalleryActivity.class);
	Params params = new Params();
	params.setCaptureLimit(10);
	params.setPickerLimit(10);
	params.setToolbarColor(selectedColor);
	params.setActionButtonColor(selectedColor);
	params.setButtonTextColor(selectedColor);
	intent.putExtra(Constants.KEY_PARAMS, params);
	startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
	
# Create intent for multi-capture
	Intent intent = new Intent(this, MultiCameraActivity.class);
	Params params = new Params();
	params.setCaptureLimit(10);
	params.setToolbarColor(selectedColor);
	params.setActionButtonColor(selectedColor);
	params.setButtonTextColor(selectedColor);
	intent.putExtra(Constants.KEY_PARAMS, params);
	startActivityForResult(intent, Constants.TYPE_MULTI_CAPTURE);

# Get the result in the `onActivityResult()` callback

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_CAPTURE:
                ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
                break;
            case Constants.TYPE_MULTI_PICKER:
                ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
                break;
        }
    }

# Default style
The default style is green, but it can be modified with any set colors. One for normal state and another for pressed state.
```setViewsColorStateList()``` in Utils class will do the job.
	

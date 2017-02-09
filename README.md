# Multimager
Multi Image Picker and Multi Image Capture Demo app
This is a sample demonstration for multiple images capture as well as multiple image picker. UX/UI can be styled with any color with relativity to Material Design. The demo shows tinting multiple views based on theme color!!

# Screenshots
![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/3.png) ![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/4.png) ![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/5.png) ![](https://github.com/vansikrishna/Multimager/blob/master/screenshots/6.png)

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

# Default style
The default style is green, but it can be modified with any set colors. One for normal state and another for pressed state.
```setViewsColorStateList()``` in Utils class will do the job.
	

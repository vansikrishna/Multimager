package com.vlk.multimager.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.vlk.multimager.R;
import com.vlk.multimager.adapters.GalleryImagesAdapter;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;
import com.vlk.multimager.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vansikrishna on 08/06/2016.
 */
public class SampleActivity extends BaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.parentLayout)
    LinearLayout parentLayout;
    int selectedColor;
    int darkenedColor;
    @Bind(R.id.multiCaptureButton)
    Button multiCaptureButton;
    @Bind(R.id.multiPickerButton)
    Button multiPickerButton;
    @Bind(R.id.customThemeButton)
    Button customThemeButton;
    @Bind(R.id.call)
    ImageView callImageView;
    @Bind(R.id.message)
    ImageView messageImageView;
    @Bind(R.id.contact_us)
    TextView contactUsTextView;
    @Bind(R.id.app_name)
    TextView appNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
        selectedColor = fetchAccentColor();
        darkenedColor = Utils.getDarkColor(selectedColor);
    }

    private int fetchAccentColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_CAPTURE:
                handleResponseIntent(intent);
                break;
            case Constants.TYPE_MULTI_PICKER:
                handleResponseIntent(intent);
                break;
        }
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float thumbnailDpWidth = getResources().getDimension(R.dimen.thumbnail_width) / displayMetrics.density;
        return (int) (dpWidth / thumbnailDpWidth);
    }

    private void handleResponseIntent(Intent intent) {
        ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getColumnCount(), GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(mLayoutManager);
        GalleryImagesAdapter imageAdapter = new GalleryImagesAdapter(this, imagesList, getColumnCount(), new Params());
        recyclerView.setAdapter(imageAdapter);
    }

    @OnClick({R.id.multiCaptureButton, R.id.multiPickerButton, R.id.customThemeButton, R.id.call, R.id.message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.multiCaptureButton:
                if (Utils.hasCameraHardware(this))
                    initiateMultiCapture();
                else
                    Utils.showLongSnack(parentLayout, "Sorry. Your device does not have a camera.");
                break;
            case R.id.multiPickerButton:
                initiateMultiPicker();
                break;
            case R.id.customThemeButton:
                setCustomTheme();
                break;
            case R.id.call:
                initiateCall();
                break;
            case R.id.message:
                sendMessage();
                break;
            case R.id.github:
                navigateToUrl();
                break;
        }
    }

    private void navigateToUrl() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://Your URL")));
    }

    private void sendMessage() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "+918106064137", null)));
    }

    private void initiateCall() {
        if (hasCallPermission(this)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "+918106064137"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
        else
            requestCallPermissions(this, Constants.REQUEST_CALL_PERMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_CALL_PERMS:
                if (validateGrantedPermissions(grantResults)) {
                    initiateCall();
                } else {
                    Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_LONG).show();
                }
                break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    private void setCustomTheme() {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(selectedColor)
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
                        changeColor(selectedColor);
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

    private void changeColor(int selectedColor) {
        this.selectedColor = selectedColor;
        this.darkenedColor = Utils.getDarkColor(selectedColor);
        Utils.showShortSnack(parentLayout, "New color selected" + Integer.toHexString(selectedColor));
//        Utils.setButtonColorStateList(customThemeButton, selectedColor, darkenedColor);
//        Utils.setButtonColorStateList(multiCaptureButton, selectedColor, darkenedColor);
//        Utils.setButtonColorStateList(multiPickerButton, selectedColor, darkenedColor);

        Utils.setButtonColorStateList(selectedColor, darkenedColor,
                customThemeButton,
                multiCaptureButton,
                multiPickerButton,
                callImageView,
                messageImageView,
                contactUsTextView,
                appNameTextView);
    }

    private void initiateMultiCapture() {
        Intent intent = new Intent(this, MultiCameraActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_CAPTURE);
    }

    private void initiateMultiPicker() {
        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
        params.setPickerLimit(10);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }


}

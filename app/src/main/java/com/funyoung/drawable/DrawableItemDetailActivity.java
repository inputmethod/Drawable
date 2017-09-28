package com.funyoung.drawable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.funyoung.views.FixCrashSeekBar;
import com.funyoung.views.SeekBarBackground;
import com.funyoung.views.SeekBarProgress;
import com.funyoung.views.SeekBarUtils;

/**
 * An activity representing a single DrawableItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DrawableItemListActivity}.
 */
public class DrawableItemDetailActivity extends AppCompatActivity {

    private static final String TAG = DrawableItemDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawableitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DrawableItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(DrawableItemDetailFragment.ARG_ITEM_ID));
            DrawableItemDetailFragment fragment = new DrawableItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.drawableitem_detail_container, fragment)
                    .commit();
        }

        setup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, DrawableItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private FixCrashSeekBar soundSeek;
    private void setup() {
        soundSeek = (FixCrashSeekBar) findViewById(R.id.seekbar);
        initSeekBar(getApplicationContext());
        soundSeek.setOnProgressChangeListener(mSeekBarChangeListener);

        initValues();
    }

    private void initValues() {
        setValue();
    }


    private void setSoundClickable(boolean value){
//        soundSelect.setEnabled(value);
//        soundSelect2.setEnabled(value);
//        soundSelect.setClickable(value);
//        soundSelect2.setClickable(value);
    }

    private void setValue() {
//        mSoundVolume = Integer.parseInt(initMgr().getValue(SettingField.TYPING_SOUND_VOLUME));
        boolean mSoundVolume = getSharedPreferences(getPackageName(), MODE_PRIVATE).getBoolean("TYPING_SOUND_VOLUME", false);
        Log.d(TAG, "setValue sound " + mSoundVolume);
        soundSeek.setProgress(mSoundVolume);
    }

//    private void soundSet(boolean value) {
//        if (value != soundSeek.isEnabled()) {
//            soundSeek.setEnabled(value);
//            soundSeek.invalidate();
//        }
//    }

    private void saveValue(int id, boolean value) {
        Log.d(TAG, "saveValue id " + id + " value " + value);
        if (id == R.id.seekbar) {
            getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().putBoolean("TYPING_SOUND_VOLUME", value).apply();
//            initMgr().setValue(SettingField.TYPING_SOUND_VOLUME, String.valueOf(value));
        } else {
            // should not be here
        }
    }

    FixCrashSeekBar.OnProgressChangeListener mSeekBarChangeListener = new FixCrashSeekBar.OnProgressChangeListener() {
        @Override
        public void onProgressChanged(FixCrashSeekBar seekBar, boolean progress) {
            int id = seekBar.getId();
            saveValue(id, progress);
        }
    };


    public void refreshUi() {
        initValues();
    }

    public void applyTheme(int normalColor, int highlightColor) {
        refreshUi();
        applySeekThumbColor(normalColor, highlightColor, highlightColor);
        applyProgressColor(normalColor, highlightColor);
    }

    private SeekBarProgress seekBarProgress;
    private SeekBarBackground seekBarBackground;
    void initSeekBar(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        float radius = density * 7f;

        seekBarProgress = new SeekBarProgress(context);
//        GradientDrawable pressed = new GradientDrawable();
//        pressed.setSize(size + 4, size + 4);
//        pressed.setCornerRadius(size / 2 + 2);
//        pressed.setColor(pressedColor);
//        seekBarProgress = pressed

        seekBarBackground = new SeekBarBackground(context);
//        soundSeek.setMaxAndProgress(10, 0);
        soundSeek.setDrawableProgress(seekBarProgress);
        soundSeek.setDrawableBkg(seekBarBackground);

        int progressColor = ContextCompat.getColor(context, R.color.colorAccent);
        int highlightColor = ContextCompat.getColor(context, R.color.colorPrimary);
        applySeekThumbColor(Color.WHITE, Color.WHITE, Color.WHITE);
        applyProgressColor(progressColor, highlightColor);
    }

    private void applySeekThumbColor(int normalColor, int highlightStartColor, int highlightEndColor) {
        float density = getResources().getDisplayMetrics().density;
        int size = Math.round(20 * density);

        soundSeek.setThumb(SeekBarUtils.getVolumeThumbDrawable(normalColor, highlightStartColor, highlightEndColor, size));
    }

    private void applyProgressColor(int normalColor, int highlightColor) {
        seekBarBackground.setColor(normalColor);
        seekBarProgress.setColor(highlightColor);
    }
}

package com.funyoung.drawing;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.funyoung.drawable.R;

/**
 * Created by yangfeng on 2017/10/8.
 */

public class DrawingActivity extends AppCompatActivity implements MyCanvas.PathsChangedListener {
    private Config config;

    private MyCanvas my_canvas;

    private ImageView eraser;
    private ImageView undo;
    private ImageView reset;

    private View back;
    private View send;

    private int color = 0;
    private float strokeWidth = 0f;
    private boolean isEraserOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawing);

        my_canvas = (MyCanvas) findViewById(R.id.my_canvas);
        my_canvas.setListener(this);

//        stroke_width_bar.progress = strokeWidth.toInt();
//        color_picker.setOnClickListener { pickColor(); }

        undo = (ImageView) findViewById(R.id.btn_undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_canvas.undo();
            }
        });

        reset = (ImageView) findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_canvas.clearCanvas();
            }
        });

        eraser = (ImageView) findViewById(R.id.eraser);
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eraserClicked();
            }
        });

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(mBackClickListener);

        send = findViewById(R.id.btn_send);
        send.setOnClickListener(sendDrawingListener);
        /*
        storeStoragePaths()

        if (intent?.action == Intent.ACTION_VIEW && intent.data != null) {
            val path = intent.data!!.path
            if (hasWriteStoragePermission()) {
                openPath(path)
            } else {
                openFileIntentPath = path
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), OPEN_FILE_INTENT)
            }
        }
        checkWhatsNewDialog()
         */

        config = new Config(getApplicationContext());

        strokeWidth = config.getBrushSize();
        my_canvas.setStrokeWidth(strokeWidth);

        setBackgroundColor(config.getCanvasBackgroundColor());
        setColor(config.getBrushColor());

        pathsChanged(0);
    }

    private void setColor(int pickedColor) {
        color = pickedColor;
//        color_picker.setBackgroundColor(color);
        my_canvas.setColor(color);
        isEraserOn = false;
        updateEraserState();
    }

    private void eraserClicked() {
        isEraserOn = !isEraserOn;
        updateEraserState();
    }

    private void updateEraserState() {
        eraser.setImageDrawable(getResources().getDrawable(isEraserOn ? R.drawable.ic_eraser_on : R.drawable.ic_eraser_off));
        my_canvas.toggleEraser(isEraserOn);
    }

    private void setBackgroundColor(int pickedColor) {
        int contrastColor = MyCanvas.getContrastColor(pickedColor);
        undo.setColorFilter(contrastColor, PorterDuff.Mode.SRC_IN);
        reset.setColorFilter(contrastColor, PorterDuff.Mode.SRC_IN);
        eraser.setColorFilter(contrastColor, PorterDuff.Mode.SRC_IN);
        my_canvas.updateBackgroundColor(pickedColor);
//        suggestedFileExtension = PNG;
    }

    @Override
    public void pathsChanged(int cnt) {
        if (cnt > 0) {
            undo.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
            eraser.setVisibility(View.VISIBLE);
            send.setEnabled(true);
        } else {
            undo.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);
            eraser.setVisibility(View.GONE);
            send.setEnabled(false);
        }
    }

    private View.OnClickListener sendDrawingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendDrawing();
        }
    };

    private void sendDrawing() {
        // todo:
        Snackbar.make(my_canvas, "Generating image to send ...", Snackbar.LENGTH_SHORT).show();
    }

    private View.OnClickListener mBackClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            hide();
        }
    };

    private void hide() {
        // do nothing but show toast
        Snackbar.make(my_canvas, "Back to origin ...", Snackbar.LENGTH_SHORT).show();
    }
}

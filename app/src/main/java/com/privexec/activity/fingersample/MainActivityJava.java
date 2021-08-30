package com.privexec.activity.fingersample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import com.privexec.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.adorsys.android.finger.Finger;
import de.adorsys.android.finger.FingerListener;

import static android.net.wifi.WifiConfiguration.Status.strings;

@SuppressLint("Registered") // Only exits for java documentation purposes
public class MainActivityJava extends AppCompatActivity implements FingerListener {
    private Finger finger;
    private ImageView fingerprintIcon;

    private Drawable iconFingerprintEnabled;
    private Drawable iconFingerprintError;
    Bitmap  bitmap;
    String fileUrl = strings[0];
    String fileName = strings[1];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iconFingerprintEnabled = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_fingerprint_on, getTheme());
        iconFingerprintError = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_fingerprint_off, getTheme());
        // You can also assign a map of error strings for the errors defined in the lib as second parameter
        finger = new Finger(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        finger.subscribe(this);
        boolean fingerprintsEnabled = finger.hasFingerprintEnrolled();

        fingerprintIcon = findViewById(R.id.login_fingerprint_icon);
        fingerprintIcon.setImageDrawable(fingerprintsEnabled ? iconFingerprintEnabled : iconFingerprintError);

        Button showDialogButton = findViewById(R.id.show_dialog_button);
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        if (!fingerprintsEnabled) {
            Toast.makeText(this, R.string.error_override_hw_unavailable, Toast.LENGTH_LONG).show();
        } else {
            showDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finger.unSubscribe();
    }

    @Override
    public void onFingerprintAuthenticationSuccess() {
        Toast.makeText(this, R.string.message_success, Toast.LENGTH_SHORT).show();
        fingerprintIcon.setImageDrawable(iconFingerprintEnabled);
        finger.subscribe(this);
    }

    @Override
    public void onFingerprintAuthenticationFailure(@NonNull String errorMessage, int errorCode) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        fingerprintIcon.setImageDrawable(iconFingerprintError);
        finger.subscribe(this);
    }

//    private void createPdf(){
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        //  Display display = wm.getDefaultDisplay();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        float hight = displaymetrics.heightPixels ;
//        float width = displaymetrics.widthPixels ;
//
//        int convertHighet = (int) hight, convertWidth = (int) width;
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//
//        PdfDocument document = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            document = new PdfDocument();
//
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//
//        Paint paint = new Paint();
//        canvas.drawPaint(paint);
//
//          bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, 0, 0 , null);
//        document.finishPage(page);
//
//        // write the document content
//        String targetPdf = "/sdcard/pdffromlayout.pdf";
//        File filePath;
//        filePath = new File(targetPdf);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//        // close the document
//        document.close();
//        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();
//        }
//        /* openGeneratedPDF(); */
//
//    }

    private void showDialog() {
       /* finger.showDialog(
                this,
                new Finger.DialogStrings(
                        getString(R.string.text_fingerprint)
                )
        );*/
       // finger.showDialog(this, Triple(getString(R.string.text_fingerprint),null,null)," login with password")

      /*  int downloadId = PRDownloader.download("url", "dirPath", fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                    }

                    @Override
                    public void onError(Error error) {

                    }


                });
*/
    }


}

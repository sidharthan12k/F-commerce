package com.example.f_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class homePage extends AppCompatActivity {
    GridView gridView;
    String[] veg = {"ONION", "CARROT", "BEETROOT", "CABBAGE", "CALIFLOWER"};
    int[] img = {R.drawable.onion, R.drawable.carrot, R.drawable.beetroot, R.drawable.cabbage, R.drawable.califlower};
    boolean b1;
    int PICK_IMAGE = 101;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        gridView = (GridView) findViewById(R.id.gridView);
        CustomeAdapter customeadapter = new CustomeAdapter();
        Intent intentt = getIntent();

        b1 = intentt.getBooleanExtra("type", false);
        gridView.setAdapter(customeadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), cart.class);
                intent.putExtra("name", veg[position]);
                intent.putExtra("img", img[position]);
                intent.putExtra("type", b1);
                //startActivity(intent);
                //speechToText();
                getImage();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                String str = Objects.requireNonNull(result).get(0);

                // use str for searching
                //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            }

            }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Log.d("hello","inside result");
            Uri uri = data.getData();
            imageFromPath(getApplicationContext(),uri);
        }
        }

    private void imageFromPath(Context context, Uri uri) {
        // [START image_from_path]
        InputImage image;
        try {
            Log.d("hello","inside imgpath try");
            image = InputImage.fromFilePath(context, uri);
            labelImages(image);
        } catch (IOException e) {
            Log.d("hello","inside imgpath error");
            e.printStackTrace();
        }
        // [END image_from_path]
    }



    public void getImage(){
        Log.d("hello","inside getimg");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void speechToText() {
        Intent intent
                = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(homePage.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


private class CustomeAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view1 = getLayoutInflater().inflate(R.layout.row_data, null);
        TextView txt1 = view1.findViewById(R.id.rowdatatext);
        ImageView img1 = view1.findViewById(R.id.rowdataimg);
        txt1.setText(veg[i]);
        img1.setImageResource(img[i]);
        return view1;
    }
}


    private void labelImages(InputImage image) {
        Log.d("hello","inside labeler");
        ImageLabelerOptions options =
                new ImageLabelerOptions.Builder()
                        .setConfidenceThreshold(0.8f)
                        .build();

        // [START get_detector_options]
        ImageLabeler labeler = ImageLabeling.getClient(options);
        // [END get_detector_options]

        /*
        // [START get_detector_default]
        // Or use the default options:
        ImageLabeler detector = imglabel.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        // [END get_detector_default]
        */

        // [START run_detector]
        Task<List<ImageLabel>> result =
                labeler.process(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<ImageLabel>>() {
                                    @Override
                                    public void onSuccess(List<ImageLabel> labels) {
                                        Log.d("hello","inside successcomplete");
                                        Log.d("hello","inside"+labels);

                                        // Task completed successfully
                                        // [START_EXCLUDE]
                                        // [START get_labels]
                                        for (ImageLabel label : labels) {
                                            Log.d("hello","inside loop");
                                            String text = label.getText();
                                            float confidence = label.getConfidence();
                                            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                                        }
                                        // [END get_labels]
                                        // [END_EXCLUDE]
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
        // [END run_detector]
    }
}
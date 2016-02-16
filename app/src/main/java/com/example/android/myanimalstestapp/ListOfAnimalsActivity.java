package com.example.android.myanimalstestapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ListOfAnimalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_animals);

        AnimalCollector animals = new AnimalCollector();
        animals.createAnimals();

        long startTime = System.currentTimeMillis();
        for (Animal animal : animals.getAnimals()) {
            generateCard(animal);
        }
        long executionTime = System.currentTimeMillis() - startTime;
        Log.i("TEST:generateCardLoop", Long.toString(executionTime));

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //options.inSampleSize = 4;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }


        //Log.i("TESTING inSampleSize:::::", Integer.toString(inSampleSize));
        return inSampleSize;
    }

    //Instansmetoder

    //Vad som händer när man klickar på Share knappen
    public void shareInformation(View view) {

        String animalName = view.getResources().getResourceEntryName(view.getId());
        //Log.i("TESTING::::::: animalName", "hiya");
        //Visa upp en Toast efter att Share knappen har blivit klickad
        Toast.makeText(ListOfAnimalsActivity.this, animalName, Toast.LENGTH_SHORT).show();

    }

    public void exploreInformation(View view) {
        String animalName = view.getResources().getResourceEntryName(view.getId());
        String url = "https://en.wikipedia.org/wiki/"+animalName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void generateCard(Animal a) {
        long startTime = System.currentTimeMillis();
        ViewGroup inclusionViewGroup = (ViewGroup)findViewById(R.id.screen);

        LayoutInflater layoutInflater = getLayoutInflater();
        View animal = layoutInflater.inflate(R.layout.cardlayout, null);

        //Set Image as scaled down Bitmap
        ImageView mImageView = (ImageView)animal.findViewById(R.id.image);
        mImageView.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), a.getImageId(), 100, 100));

        //Set title
        TextView title = (TextView)animal.findViewById(R.id.title);
        title.setText(a.getName());

        //Set description
        TextView description = (TextView)animal.findViewById(R.id.description);
        description.setText(a.getDescription());

        //Set exploreButton with correct animalname (ID)
        Button exploreButton = (Button) animal.findViewById(R.id.exploreButton);
        exploreButton.setId(a.getImageId());

        //Set shareButton with correct animalname (ID)
        Button shareButton = (Button) animal.findViewById(R.id.shareButton);
        shareButton.setId(a.getImageId());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(30, 40, 30, 50);
        animal.setLayoutParams(lp);

        inclusionViewGroup.addView(animal);

        long executionTime = System.currentTimeMillis() - startTime;

        Log.i("TEST:generateCard executionTime", Long.toString(executionTime));

    }
}

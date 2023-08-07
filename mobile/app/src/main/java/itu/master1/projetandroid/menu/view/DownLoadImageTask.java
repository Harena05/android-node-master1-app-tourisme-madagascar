package itu.master1.projetandroid.menu.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import itu.master1.projetandroid.global.MyApplication;
import itu.master1.projetandroid.menu.model.Content;

public class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
    ImageView imageView;
    Content content;

    public DownLoadImageTask(ImageView imageView){
        this.imageView = imageView;
    }


    public DownLoadImageTask(Content content){
        this.content = content;
    }


    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{

            InputStream is = new URL(MyApplication.IMAGE_URL + urlOfImage).openStream();
            logo = BitmapFactory.decodeStream(is);

        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    protected void onPostExecute(Bitmap result){
        if(imageView != null)imageView.setImageBitmap(result);
        if(content != null) content.setBitmap(result);
    }
}

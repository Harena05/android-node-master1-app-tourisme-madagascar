package itu.master1.projetandroid.menu.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import itu.master1.projetandroid.global.APIClient;
import itu.master1.projetandroid.global.MyApplication;
import itu.master1.projetandroid.menu.model.Content;
import itu.master1.projetandroid.menu.model.MenuInterface;
import itu.master1.projetandroid.menu.view.DownLoadImageTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MadaViewModel extends AndroidViewModel {
    private MutableLiveData<List<Content>> contents = new MutableLiveData<>();
    public MadaViewModel(@NonNull Application application) {
        super(application);
        this.findContentList();
    }

    public MutableLiveData<List<Content>> getContentsLive() {
        return contents;
    }

    public List<Content> getContents() {
        return this.contents.getValue();
    }


    private void findContentList() {
        if(contents.getValue() != null && contents.getValue().size() > 0) return;
        contents.setValue(new ArrayList<>());
        MyApplication app = getApplication();
        MenuInterface menuInterface = APIClient.getClient(app).create(MenuInterface.class);
        Call<List<Content>> contentList = menuInterface.doGetContentList();
        contentList.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                List<Content> result = response.body();
                for(Content ct: result) {
                    if(ct.getImages() != null && ct.getImages().length > 0)
                    new DownLoadImageTask(ct).execute(ct.getImages()[0]);
                }
                contents.setValue(result);
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}

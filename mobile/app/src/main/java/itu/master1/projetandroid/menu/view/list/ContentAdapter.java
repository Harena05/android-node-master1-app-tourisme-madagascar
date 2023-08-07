package itu.master1.projetandroid.menu.view.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import itu.master1.projetandroid.R;
import itu.master1.projetandroid.menu.model.Content;
import itu.master1.projetandroid.menu.view.DownLoadImageTask;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private List<Content> contentList;
    private Listener listener;
    interface Listener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_content, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CardView cardView = holder.cardView;


        TextView txtTitle = (TextView)cardView.findViewById(R.id.id_card_title);
        TextView txtDescription = (TextView)cardView.findViewById(R.id.id_card_description);
        String title = contentList.get(position).getTitle();
        if(title != null) title = title.substring(0, 1).toUpperCase() + title.substring(1);
        txtTitle.setText(title);
        String desc = contentList.get(position).getDescription();
        if(desc != null) {
            desc = desc.substring(0, Math.min(desc.length(), 35));
            desc = desc.substring(0, 1).toUpperCase(Locale.ROOT) + desc.substring(1);
        }
        txtDescription.setText(desc);
        ImageView img = (ImageView)cardView.findViewById(R.id.id_thumbnail);

        /*


         */
        if(contentList.get(position).getBitmap() == null) {
            if(contentList.get(position).getImages() != null && contentList.get(position).getImages().length > 0) {
                new DownLoadImageTask(img).execute(contentList.get(position).getImages()[0]);
            }
        }
        if ((contentList.get(position).getBitmap() != null)) {
            img.setImageBitmap(contentList.get(position).getBitmap());
        }
        else img.setImageResource(R.drawable.bg_cardview);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(position);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public void setContentList(List<Content> contents) {
        this.contentList = contents;
    }

    public List<Content> getContentList() {
        return this.contentList;
    }

    public ContentAdapter(List<Content> _content) {
        this.contentList = _content;
    }
}

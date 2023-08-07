package itu.master1.projetandroid.menu.view.list;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import itu.master1.projetandroid.R;
import itu.master1.projetandroid.menu.model.Content;
import itu.master1.projetandroid.menu.view.detail.MadaDetailActivity;
import itu.master1.projetandroid.menu.viewmodel.MadaViewModel;

public class MadaFragment extends Fragment {


    private RecyclerView courseRecycler;
    private EditText edtSearch;
    private MadaViewModel madaViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        ConstraintLayout mainLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_courses, parent, false);
        LinearLayout mainLayoutLinear = mainLayout.findViewById(R.id.id_linear_for_recycle);

        madaViewModel = new ViewModelProvider(this.getActivity()).get(MadaViewModel.class);
        ProgressBar spinner = (ProgressBar)mainLayout.findViewById(R.id.id_pgb_courseList);
        spinner.setVisibility(View.VISIBLE);

        courseRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_course_list, mainLayoutLinear, false);

        edtSearch = (EditText)mainLayout.findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ContentAdapter adapter = (ContentAdapter) courseRecycler.getAdapter();
                //TODO: Optimize
                for(int iC= 0; iC < adapter.getContentList().size(); iC++) adapter.notifyItemRemoved(0);

                List<Content> contents = madaViewModel.getContents();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<Content> cts = contents.stream().filter(ct -> ct.getTitle().contains(charSequence)).collect(Collectors.toList());
                    adapter.setContentList(cts);
                    for(int iC= 0; iC < adapter.getContentList().size(); iC++) adapter.notifyItemInserted(iC);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ContentAdapter adapter;   adapter =    new ContentAdapter(madaViewModel.getContents());

            courseRecycler.setAdapter(adapter);

            adapter.setListener(new ContentAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    showDetail(position);
                }
            });



        madaViewModel.getContentsLive().observe(getViewLifecycleOwner(), new Observer<List<Content>>() {
            @Override
            public void onChanged(List<Content> contents) {
                if(contents != null) {
                    ContentAdapter contentAdapter = (ContentAdapter) courseRecycler.getAdapter();
                    contentAdapter.setContentList(contents);

                    for(int iC = 0; iC < contents.size(); iC++) {
                        contentAdapter.notifyItemInserted(iC);
                    }
                    spinner.setVisibility(View.GONE);
/*
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 1000);
*/
                }

            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        courseRecycler.setLayoutManager(layoutManager);

        mainLayoutLinear.addView(courseRecycler);
        return mainLayout;
    }

    private void showDetail(int position) {
        Intent intent = new Intent(getActivity(), MadaDetailActivity.class);
        ContentAdapter adapter = (ContentAdapter) courseRecycler.getAdapter();
        intent.putExtra(MadaDetailActivity.EXTRA_CONTENT, adapter.getContentList().get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

package examples.my.android.retrofitsample;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import examples.my.android.retrofitsample.api.UmoriliApi;
import examples.my.android.retrofitsample.pojo.AnekdotModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private static UmoriliApi sUmoriliApi;

    private RecyclerView mRecyclerView;
    private List<AnekdotModel> mAnekdots = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void updateItems() {
        sUmoriliApi.getData("bash.im","bash", 50).enqueue(new Callback<List<AnekdotModel>>() {
            @Override
            public void onResponse(Call<List<AnekdotModel>> call, Response<List<AnekdotModel>> response) {
                mAnekdots.addAll(response.body());
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AnekdotModel>> call, Throwable t) {
                Log.e("Retrofit", call.toString());
                Log.e("Retrofit", call.request().toString());
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        sUmoriliApi = RetrofitController.getApi();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new AnekdotAdapter(mAnekdots));
        updateItems();

        return v;
    }

    private class AnekdotHolder extends RecyclerView.ViewHolder {

        private TextView mPost;
        private TextView mSite;

        public AnekdotHolder(View itemView) {
            super(itemView);

            mPost = (TextView) itemView.findViewById(R.id.item_post);
            mSite = (TextView) itemView.findViewById(R.id.item_site);
        }
    }

    private class AnekdotAdapter extends RecyclerView.Adapter<AnekdotHolder> {

        private List<AnekdotModel> posts;

        public AnekdotAdapter(List<AnekdotModel> posts) {
            this.posts = posts;
        }

        @NonNull
        @Override
        public AnekdotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.item, parent, false);
            return new AnekdotHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AnekdotHolder holder, int position) {
            AnekdotModel anekdotModel = posts.get(position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mPost.setText(Html.fromHtml(anekdotModel.getElementPureHtml(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.mPost.setText(Html.fromHtml(anekdotModel.getElementPureHtml()));
            }
            holder.mSite.setText(Html.fromHtml(anekdotModel.getSite()));
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }
}

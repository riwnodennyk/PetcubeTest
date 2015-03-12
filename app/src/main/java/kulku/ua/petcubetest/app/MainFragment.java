package kulku.ua.petcubetest.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kulku.ua.petcubetest.R;
import kulku.ua.petcubetest.adapter.PetPostsAdapter;
import kulku.ua.petcubetest.model.PetPost;
import kulku.ua.petcubetest.server.Backend;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_main);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_view_main);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
    }

    private void load() {
        Backend.API.listPetPosts(new Callback<List<PetPost>>() {
            @Override
            public void success(List<PetPost> petPosts, Response response) {
                mRecyclerView.setAdapter(new PetPostsAdapter(petPosts));
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                int errorMessage = Backend.getErrorMessage(error);
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}

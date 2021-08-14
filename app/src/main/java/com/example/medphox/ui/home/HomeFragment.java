package com.example.medphox.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.medphox.R;
import com.example.medphox.adapter.AdapterTest;
import com.example.medphox.adapter.AdapterTopBookedItem;
import com.example.medphox.model.TestModel;
import com.example.medphox.model.TopBookedItemModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<TopBookedItemModel> topBookedItemModels;
    private ArrayList<TestModel> testModels;
    private AdapterTopBookedItem adapterTopBookedItem;
    private AdapterTest adapterTest;
    private RequestQueue queue;
    private ShimmerFrameLayout mFrameLayout;
    private RecyclerView recyclerTopBookedItem, recyclerTest;
//    http://www.dipantan.me/api.php?case=test


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        topBookedItemModels = new ArrayList<>();
        testModels = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        queue = Volley.newRequestQueue(view.getContext());
        adapterTopBookedItem = new AdapterTopBookedItem(topBookedItemModels, view.getContext());
        adapterTest = new AdapterTest(testModels, view.getContext());
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(view.getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(view.getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerTopBookedItem.setLayoutManager(layoutManager1);
        recyclerTopBookedItem.setAdapter(adapterTopBookedItem);
        recyclerTest.setLayoutManager(layoutManager2);
        recyclerTest.setAdapter(adapterTest);
        mFrameLayout.startShimmer();
        topBookedItems(view);
        test(view);
    }

    void initView(View view) {
        mFrameLayout = view.findViewById(R.id.shimmerLayout);
        recyclerTopBookedItem = view.findViewById(R.id.recycler_top_booked_item);
        recyclerTest = view.findViewById(R.id.recycler_test);
    }

    void topBookedItems(View view) {
        String url = "https://dipantan.me/api.php?case=topbookeditems";
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    TopBookedItemModel model = new TopBookedItemModel();
                    JSONObject jsonObject = response.getJSONObject(i);
                    model.setName(jsonObject.getString("name"));
                    model.setPrice(jsonObject.getString("price"));
                    model.setDescription(jsonObject.getString("description"));
                    topBookedItemModels.add(model);
                    mFrameLayout.setVisibility(View.GONE);
                    adapterTopBookedItem.notifyDataSetChanged();

                }
            } catch (Exception e) {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(e.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        }, error -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(error.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });
        int MY_SOCKET_TIMEOUT_MS = 5000;
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(jsonArrayRequest);
    }

    void test(View view) {
        String url = "https://www.dipantan.me/api.php?case=test";
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    TestModel model = new TestModel();
                    JSONObject jsonObject = response.getJSONObject(i);
                    model.setName(jsonObject.getString("testType"));
                    model.setPrice(jsonObject.getString("testAmount"));
                    model.setDescription(jsonObject.getString("TestDescription"));
                    testModels.add(model);
                    mFrameLayout.setVisibility(View.GONE);
                    adapterTest.notifyDataSetChanged();
                }
            } catch (Exception e) {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(e.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        }, error -> {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(error.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });
        int MY_SOCKET_TIMEOUT_MS = 5000;
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(jsonArrayRequest);
    }
}
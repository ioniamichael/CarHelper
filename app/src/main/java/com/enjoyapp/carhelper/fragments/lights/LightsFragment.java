package com.enjoyapp.carhelper.fragments.lights;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.adapters.LightsAdapter;
import com.enjoyapp.carhelper.adapters.LightsHistoryAdapter;
import com.enjoyapp.carhelper.fragments.loaders.LoaderFragment;
import com.enjoyapp.carhelper.models.LightsHistory;
import com.enjoyapp.carhelper.models.Light;
import com.enjoyapp.carhelper.presenters.LightsHistoryPresenter;
import com.enjoyapp.carhelper.presenters.LightsPresenter;
import com.enjoyapp.carhelper.screens.MainActivity;
import com.enjoyapp.carhelper.utils.FragmentCommunication;
import com.enjoyapp.carhelper.views.LightsHistoryView;
import com.enjoyapp.carhelper.views.LightsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LightsFragment extends Fragment implements LightsPresenter, LightsHistoryPresenter {

    private RecyclerView mRVLights, mRVHistory;
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<Light> mLightsArray = new ArrayList<>();
    private Light mLight;
    private LightsAdapter mLightsAdapter;
    private LightsHistoryAdapter lightsHistoryAdapter;
    private LightsView mLightsView;
    private View view;
    private LightsInfoFragment mLightsInfoFragment;
    private Animation mAnimation;
    private LoaderFragment loaderFragment = new LoaderFragment();
    private LightsHistoryView lightsHistoryView;
    private LightsHistory lightsHistory;
    private ArrayList<LightsHistory> lightsHistoryArrayList = new ArrayList<>();
    private FrameLayout mHistoryContainer;

    private String TAG = "LightsFragment";
    int i = 0;

    public LightsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_lights, container, false);
            initView(view);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLightsView = new LightsView(mLightsArray, mLight, this);
        mLightsView.loadData();
        lightsHistoryView.readItemsFromDB();
    }

    //Setting adapter parameters.
    @Override
    public void setAdapter() {
        mLightsAdapter = new LightsAdapter(getActivity(), mLightsArray, communication);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRVLights.setLayoutManager(layoutManager);
        mRVLights.setAdapter(mLightsAdapter);

        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in_rv);
        mRVLights.startAnimation(mAnimation);

        mLightsAdapter.setOnLightImageTouchListener(new LightsAdapter.OnLightImageTouchListener() {
            @Override
            public void onLightImageTouch(final int position, MotionEvent event, LightsAdapter.LightsViewHolder holder) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.removeSortFragment();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    saveItemToHistory(position);
                }
            }
        });
    }

    //Clear lightsArray before loading data again.
    @Override
    public void clearRecyclerView() {
        if (mLightsArray.size() != 0) {
            mLightsArray.clear();
            mLightsAdapter.notifyDataSetChanged();
        }
    }

    //Start loading animation until the RV data is received.
    @Override
    public void startLoadAnimation() {
        assert getFragmentManager() != null;
        getFragmentManager().findFragmentById(R.id.main_fragment_container).getActivity()
                .getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, this.loaderFragment).commit();
    }

    //Stop loading animation when RV data received.
    @Override
    public void stopLoadAnimation() {
        if (getFragmentManager() != null) {
            getFragmentManager().findFragmentById(R.id.main_fragment_container).getActivity()
                    .getSupportFragmentManager().beginTransaction().remove(this.loaderFragment).commit();
        }
    }

    //Sorting adapter by warning color ( 1-4, red - blue )
    public void sortAdapter() {
        Collections.sort(mLightsArray);
        mLightsAdapter.updateData(mLightsArray);
    }


    //Initializing views.
    public void initView(View view) {
        lightsHistoryView = new LightsHistoryView(lightsHistoryArrayList, keys, lightsHistory, this);
        mRVLights = view.findViewById(R.id.RVLights);
        mRVHistory = view.findViewById(R.id.RVHistory);
        mHistoryContainer = view.findViewById(R.id.historyContainer);
    }

    //Getting light's data
    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, String lightsTitle, String lightsDesc, String imageURL) {
            mLightsInfoFragment = new LightsInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("LIGHTS_TITLE", lightsTitle);
            bundle.putString("LIGHTS_DESC", lightsDesc);
            bundle.putString("IMAGE_URL", imageURL);
            mLightsInfoFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left_light_info,
                            R.anim.exit_right_to_left_lights_info)
                    .replace(R.id.lightsInfoContainer, mLightsInfoFragment)
                    .commit();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void saveItemToHistory(int position) {
        String imageUrl = mLightsArray.get(position).getLampImageUrl();
        String title = mLightsArray.get(position).getLampTitle();
        String desc = mLightsArray.get(position).getLampDesc();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String key = null;
        Log.d(TAG, "saveItemToHistory: " + currentDate);


        lightsHistory = new LightsHistory(imageUrl, title, desc, currentDate);
        lightsHistoryView.writeItemToTB(lightsHistory);
    }


    @Override
    public void setLightsHistoryAdapter() {
        lightsHistoryAdapter = new LightsHistoryAdapter(lightsHistory, lightsHistoryArrayList, getContext(), keys);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.HORIZONTAL);
        mRVHistory.setLayoutManager(layoutManager);
        mRVHistory.setAdapter(lightsHistoryAdapter);
        lightsHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearRVBeforeLoadData() {
        if (lightsHistoryArrayList.size() != 0) {
            lightsHistoryArrayList.clear();
            lightsHistoryAdapter.notifyDataSetChanged();
        }
    }

}
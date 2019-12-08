package com.enjoyapp.carhelper.fragments.lights;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.adapters.LightsAdapter;
import com.enjoyapp.carhelper.fragments.loaders.LoaderFragment;
import com.enjoyapp.carhelper.models.Light;
import com.enjoyapp.carhelper.presenters.LightsPresenter;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.screens.MainActivity;
import com.enjoyapp.carhelper.utils.FragmentCommunication;
import com.enjoyapp.carhelper.views.LightsView;

import java.util.ArrayList;
import java.util.Collections;

public class LightsFragment extends Fragment implements LightsPresenter {

    private RecyclerView mRVLights;
    private ArrayList<Light> mLightsArray = new ArrayList<>();
    private ArrayList<String> mLightsHistory = new ArrayList<String>();
    private Light mLight;
    private LightsAdapter mLightsAdapter;
    private LightsView mLightsView;
    private TextView mEmptyHistoryTXT;
    private View view;
    private LightsInfoFragment mLightsInfoFragment;
    private Animation mAnimation;
    private ImageView mIVShadow;
    private ListView mHistoryListView;
    private ArrayAdapter<String> adapter;
    private LoaderFragment loaderFragment = new LoaderFragment();

    private String TAG = "LightsFragment";

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
                    mEmptyHistoryTXT.setVisibility(View.GONE);

                    mLightsHistory.add(0, mLightsArray.get(position).getLampTitle());
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mLightsHistory);
                    mHistoryListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Log.d(TAG, "onLightImageTouch: " + mLightsHistory.size());
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
        mRVLights = view.findViewById(R.id.RVLights);
        mIVShadow = getActivity().findViewById(R.id.IVShadow);
        mIVShadow.setVisibility(View.VISIBLE);
        mEmptyHistoryTXT = view.findViewById(R.id.emptyHistoryTXT);
        mHistoryListView = view.findViewById(R.id.historyListView);
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
        mIVShadow.setVisibility(View.GONE);
    }

}
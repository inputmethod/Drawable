package com.funyoung.drawable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.funyoung.drawable.dummy.DummyContent;
import com.funyoung.sound.SoundAdapter;
import com.funyoung.sound.GridDivider;
import com.typany.keyboard.sound.SoundHttp;
import com.typany.keyboard.sound.SoundItem;
import com.typany.keyboard.sound.SoundSkinItem;

import java.util.List;

/**
 * A fragment representing a single DrawableItem detail screen.
 * This fragment is either contained in a {@link DrawableItemListActivity}
 * in two-pane mode (on tablets) or a {@link DrawableItemDetailActivity}
 * on handsets.
 */
public class DrawableItemDetailFragment extends Fragment implements SoundHttp.OnSoundRequestListener{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DrawableItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(mItem.content));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drawableitem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.drawableitem_detail)).setText(mItem.details);
        }

        mRv = rootView.findViewById(R.id.sound_item_list);
        init();
        return rootView;
    }

    private RecyclerView mRv;
    private SoundAdapter mAdapter;
    private List<SoundItem> dataSet ;
    public void init() {
        Context context = getContext();
        int columnNum = 3;
        int pxDivider = context.getResources().getDimensionPixelOffset(R.dimen.sound_item_divider_height);
        int colorDivider = ContextCompat.getColor(context, R.color.colorAccent);
        dataSet = DummyContent.generateSoundData();
        LinearLayoutManager llMgr = new GridLayoutManager(context, columnNum);
        mRv.setLayoutManager(llMgr);
        mRv.addItemDecoration(new GridDivider(context, pxDivider, colorDivider));
        mAdapter = new SoundAdapter(getContext(), mRv, dataSet);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onLoaded(List<SoundSkinItem> itemList) {
        mAdapter.addSoundSkinList(itemList);
    }

    @Override
    public void onError(String message) {
        Snackbar.make(mRv, message, Snackbar.LENGTH_INDEFINITE).show();
    }
}

package com.funyoung.drawable;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.funyoung.drawable.dummy.DummyContent;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * An activity representing a list of DrawableItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DrawableItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DrawableItemListActivity extends AppCompatActivity implements ClickListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawableitem_list);

        startAnimation();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.drawableitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.drawableitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void startAnimation() {
        final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        final AnimationDrawable background = (AnimationDrawable) imageView.getDrawable();
        background.setOneShot(false);
        background.start();

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                background.stop();
                imageView.setVisibility(View.GONE);
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS, this));
    }

    @Override
    public void onClick(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(DrawableItemDetailFragment.ARG_ITEM_ID, id);
            DrawableItemDetailFragment fragment = new DrawableItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.drawableitem_detail_container, fragment)
                    .commit();
        } else {
            Context context = this;
            Intent intent = new Intent(context, DrawableItemDetailActivity.class);
            intent.putExtra(DrawableItemDetailFragment.ARG_ITEM_ID, id);

            context.startActivity(intent);
        }
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;
        private final WeakReference<ClickListener> clickListenerWeakReference;


        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, ClickListener listener) {
            mValues = items;
            clickListenerWeakReference = new WeakReference<>(listener);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawableitem_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.bind(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickListener listener = clickListenerWeakReference.get();
                    if (null != listener) {
                        listener.onClick(holder.mItem.id);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mTitleView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.id);
                mTitleView = view.findViewById(R.id.title);
                mContentView = view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "' " + " '" + mContentView.getText() + "'";
            }

            public void bind(DummyContent.DummyItem dummyItem) {
                Resources resources = mView.getContext().getResources();
                mItem = dummyItem;
                mIdView.setText(dummyItem.id);
                mTitleView.setText(resources.getString(dummyItem.title));
                mContentView.setText(resources.getString(dummyItem.content));
            }
        }
    }
}

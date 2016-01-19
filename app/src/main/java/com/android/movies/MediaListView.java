package com.android.movies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.movies.util.UIConstants;

/**
 *
 */
public class MediaListView extends LinearLayout {

    private Toolbar mToolbar;
    private EditText mSearchBox;
    private RecyclerView mMediaListView;
    private MediaListViewListener mListener;

    public MediaListView(Context context) {
        super(context);
    }

    public MediaListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchBox = (EditText) findViewById(R.id.search_movie_keyword);
        mMediaListView = (RecyclerView) findViewById(R.id.movie_list_container);
        mMediaListView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mMediaListView.setLayoutManager(layoutManager);
        setupToolBar();

        mSearchBox.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= mSearchBox.getRight() - mSearchBox.getTotalPaddingRight()) {
                        // your action for drawable click event
                        Log.i("MediaListView", "Left button touched");
                        mListener.onSearchButtonClick(mSearchBox.getText().toString());
                        clearTextAndKeyboard();
                        return true;
                    }
                }
                return false;
            }
        });

        mSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (mSearchBox.getText().length() > 0) {
                            mListener.onSearchButtonClick(mSearchBox.getText().toString());
                            clearTextAndKeyboard();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * This method is used to clear text and remove keyboard from screen.
     */
    private void clearTextAndKeyboard() {
        mSearchBox.setText(UIConstants.SPACE);
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mSearchBox.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setupToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_subscriptions_white_24dp);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    public RecyclerView getMediaListView() {
        return mMediaListView;
    }

    public void setMediaListViewListener(MediaListViewListener listener) {
        this.mListener = listener;
    }

    public interface MediaListViewListener {
        void onSearchButtonClick(String mediaTitle);
    }
}

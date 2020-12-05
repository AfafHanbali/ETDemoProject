package com.example.ettdemoproject.Fragments.HomeFragments.Albums;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ettdemoproject.DataModel.Album;
import com.example.ettdemoproject.R;
import com.example.ettdemoproject.Listeners.RecyclerTouchListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class AlbumsFragment extends Fragment implements AlbumsFragmentPresenter.View {

    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Albums is loading...");
    private static final CharSequence SHARE_CHOOSER_TITLE = "Share with";
    private static final String TYPE = "album";
    private static final String SUBJECT = "Album Details";


    @BindView(R.id.albums_nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rv_albums)
    RecyclerView listOfAlbums;
    @BindView(R.id.albums_progress_bar)
    ProgressBar progressBar;

    private ProgressDialog progressDialog;
    private AlbumsAdapter albumsAdapter;
    private AlbumsFragmentPresenter presenter;
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private Unbinder unbinder;
    private RecyclerTouchListener touchListener;

    public int position = -1;
    private int startIndex = 0;
    private int limit = 10;
    private boolean load = false;

    public AlbumsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_albums, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        buildProgressDialog();
        presenter = new AlbumsFragmentPresenter(this);
        presenter.loadAlbums(startIndex, limit, load);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    load = true;
                    limit += 10;
                    startIndex += 10;
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.loadAlbums(startIndex, limit, load);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disposables.clear();
    }

    @Override
    public void displayAlbums(List<Album> albumList) {
        albumsAdapter = new AlbumsAdapter(getActivity(), albumList);
        listOfAlbums.setAdapter(albumsAdapter);
        listOfAlbums.setLayoutManager(linearLayoutManager);

        touchListener = new RecyclerTouchListener(getActivity(), listOfAlbums);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.album_delete, R.id.album_share)
                .setSwipeable(R.id.album_row_FG, R.id.album_row_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID) {
                            case R.id.album_delete:
                                int tmpPosition = position;
                                Album tmpAlbum = albumList.get(position);
                                albumList.remove(position);
                                albumsAdapter.setUsersList(albumList);

                                Snackbar.make(getView(), R.string.snackBar_text, Snackbar.LENGTH_LONG).
                                        setAction(R.string.snackBar_action_text, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                albumList.add(tmpPosition, tmpAlbum);
                                                albumsAdapter.setUsersList(albumList);
                                            }

                                        }).show();

                                break;
                            case R.id.album_share:
                                Album albumObj = albumList.get(position);
                                String id = Integer.toString((albumObj.getId()));
                                String title = albumObj.getTitle();
                                String message = getContext().getString(R.string.albumShareMsg, title);
                                BranchUniversalObject buo = getBranchUniversalObject(id, title);
                                LinkProperties lp = getLinkProperties();
                                ShareSheetStyle ss = getShareSheetStyle(message);

                                buo.showShareSheet(getActivity(), lp, ss, new Branch.BranchLinkShareListener() {
                                    @Override
                                    public void onShareLinkDialogLaunched() {
                                    }

                                    @Override
                                    public void onShareLinkDialogDismissed() {
                                    }

                                    @Override
                                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                                    }

                                    @Override
                                    public void onChannelSelected(String channelName) {
                                    }
                                });
                                break;

                        }
                    }
                });
        listOfAlbums.addOnItemTouchListener(touchListener);

        if (position != -1) {
            listOfAlbums.scrollToPosition(position);
            albumsAdapter.setHighlightedRow(position);
            clearPosition();
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }


    private void clearPosition() {
        position = -1;
    }


    @Override
    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }


    private void buildProgressDialog() {
        progressDialog = new ProgressDialog(getContext(), R.style.progressDialog);
        progressDialog.setMax(100);
        progressDialog.setMessage(PROGRESS_MSG_CONTENT);
        progressDialog.setTitle(PROGRESS_MSG_TITLE);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private BranchUniversalObject getBranchUniversalObject(String id, String title){
        return new BranchUniversalObject()
                .setCanonicalIdentifier(id)
                .setTitle(title)
                .setContentDescription(TYPE)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC);
    }

    private LinkProperties getLinkProperties(){
        return  new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user");
    }

    private ShareSheetStyle getShareSheetStyle(String message) {
        return new ShareSheetStyle(getContext(), SUBJECT, message)
                .setCopyUrlStyle(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.GMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle(String.valueOf(SHARE_CHOOSER_TITLE));
    }


}
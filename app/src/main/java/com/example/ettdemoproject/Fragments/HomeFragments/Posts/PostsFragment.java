package com.example.ettdemoproject.Fragments.HomeFragments.Posts;


import android.app.ProgressDialog;
import android.os.Bundle;

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

import com.example.ettdemoproject.DataModel.Post;
import com.example.ettdemoproject.DataModel.User;
import com.example.ettdemoproject.R;
import com.example.ettdemoproject.RecyclerTouchListener;
import com.example.ettdemoproject.UI.UserInformationActivity;
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
public class PostsFragment extends Fragment implements PostsFragmentPresenter.View {
    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Posts is loading...");
    private static final CharSequence SHARE_CHOOSER_TITLE = "Share with";
    private static final String TYPE = "post";
    private static final String SUBJECT = "Post Details";


    @BindView(R.id.posts_nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rv_posts)
    RecyclerView listOfPosts;
    @BindView(R.id.posts_progress_bar)
    ProgressBar progressBar;

    private ProgressDialog progressDialog;
    private PostsAdapter postsAdapter;
    private PostsFragmentPresenter presenter;
    private Unbinder unbinder;
    private RecyclerTouchListener touchListener;

    private int position = -1;
    private int startIndex = 0;
    private int limit = 10;
    private boolean load = false;

    public PostsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        buildProgressDialog();
        postsAdapter = new PostsAdapter();
        presenter = new PostsFragmentPresenter(this);
        presenter.loadPosts(startIndex, limit, load);

        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    load = true;
                    limit += 10;
                    startIndex += 10;
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.loadPosts(startIndex, limit, load);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //  presenter.detachView();
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
    public void displayPosts(List<Post> postsList) {
        setupAdapter(postsList);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setupAdapter(List<Post> postList) {
        postsAdapter.setPostsList(postList);
        listOfPosts.setAdapter(postsAdapter);
        listOfPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        touchListener = new RecyclerTouchListener(getActivity(), listOfPosts);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.post_delete, R.id.post_share)
                .setSwipeable(R.id.post_row_FG, R.id.post_row_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID) {
                            case R.id.post_delete:
                                int tmpPosition = position;
                                Post tmpPost = postList.get(position);
                                postList.remove(position);
                                postsAdapter.setPostsList(postList);

                                Snackbar.make(getView(),R.string.snackBar_text, Snackbar.LENGTH_LONG).
                                        setAction(R.string.snackBar_action_text, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                postList.add(tmpPosition, tmpPost);
                                                postsAdapter.setPostsList(postList);
                                            }

                                        }).show();

                                break;
                            case R.id.post_share:
                                Post postObj = postList.get(position);
                                String id = Integer.toString((postObj.getId()));
                                String title = postObj.getTitle();
                                String message = getContext().getString(R.string.postShareMsg, title);
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
        listOfPosts.addOnItemTouchListener(touchListener);

        if (position != -1) {
            listOfPosts.scrollToPosition(position);
            postsAdapter.setHighlightedRow(position);
            clearPosition();
        }
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
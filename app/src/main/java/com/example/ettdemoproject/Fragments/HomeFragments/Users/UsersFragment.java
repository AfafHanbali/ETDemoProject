package com.example.ettdemoproject.Fragments.HomeFragments.Users;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.DataModel.User;
import com.example.ettdemoproject.Events.FavClickEvent;
import com.example.ettdemoproject.Events.UserClickEvent;
import com.example.ettdemoproject.R;
import com.example.ettdemoproject.RecyclerTouchListener;
import com.example.ettdemoproject.UI.UserInformationActivity;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class UsersFragment extends Fragment implements UsersFragmentPresenter.View {

    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Users is loading...");
    public static final String TYPE = "user";
    public static final String SUBJECT = "User Details";
    public static final String SHARE_CHOOSER_TITLE = "Share with";

    private int position = -1;

    @BindView(R.id.rv_users)
    RecyclerView listOfUsers;


    private ProgressDialog progressDialog;
    private UsersAdapter usersAdapter = new UsersAdapter();
    private UsersFragmentPresenter presenter;
    private Unbinder unbinder;
    private RecyclerTouchListener touchListener;

    public UsersFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buildProgressDialog();
        presenter = new UsersFragmentPresenter(this);
        presenter.loadUsers();

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        //presenter.attachView(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFavClickEvent(FavClickEvent event) {

        usersAdapter.updateItem(event.user);
        FavClickEvent stickyEvent = EventBus.getDefault().getStickyEvent(FavClickEvent.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //presenter.detachView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disposables.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void displayUsers(List<User> usersList) {
        setupAdapter(usersList);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setupAdapter(List<User> usersList) {
        usersAdapter.setUsersList(usersList);
        listOfUsers.setAdapter(usersAdapter);
        listOfUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        touchListener = new RecyclerTouchListener(getActivity(), listOfUsers);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        UserInformationActivity.startScreen(getActivity(), usersList.get(position));
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.user_delete, R.id.user_share)
                .setSwipeable(R.id.row_FG, R.id.row_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID) {
                            case R.id.user_delete:
                                int tmpPosition = position;
                                User tmpUser = usersList.get(position);
                                usersList.remove(position);
                                usersAdapter.setUsersList(usersList);

                                Snackbar.make(getView(), R.string.snackBar_text, Snackbar.LENGTH_LONG).
                                        setAction(R.string.snackBar_action_text, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                usersList.add(tmpPosition, tmpUser);
                                                usersAdapter.setUsersList(usersList);
                                            }

                                        }).show();

                                break;
                            case R.id.user_share:
                                User user = usersList.get(position);
                                String id = Integer.toString(user.getId());
                                String title = user.getName();
                                String message = getString(R.string.userShareMsg, user.getName());
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
        listOfUsers.addOnItemTouchListener(touchListener);

        if (position != -1) {
            listOfUsers.scrollToPosition(position);
            usersAdapter.setHighlightedRow(position);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserClickEvent(UserClickEvent event) {

        UserInformationActivity.startScreen(getActivity(), event.user);

    }

    private BranchUniversalObject getBranchUniversalObject(String id, String title) {
        return new BranchUniversalObject()
                .setCanonicalIdentifier(id)
                .setTitle(title)
                .setContentDescription(TYPE)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC);
    }

    private LinkProperties getLinkProperties() {
        return new LinkProperties()
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
                .setSharingTitle(SHARE_CHOOSER_TITLE);
    }

}

package com.example.ettdemoproject.Presenters;

import com.example.ettdemoproject.DataModel.User;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class UserInformationActivityPresenter {

    public static final String UNSPECIDIED = "Unspecified";
    private UserInfoView userInfoView;
    private User user;

    public UserInformationActivityPresenter(UserInfoView userInfoView, User user) {
        this.userInfoView = userInfoView;
        this.user = user;
    }

    public void getInfo() {

        String name = user.getName();
        String userName = user.getUsername();
        String phone = user.getPhone();
        String email = user.getEmail();
        String website = user.getWebsite();
        String suite = UNSPECIDIED;
        String street = UNSPECIDIED;
        String city = UNSPECIDIED;
        String zipCode = UNSPECIDIED;
        String companyName = UNSPECIDIED;
        String catchPhrase = UNSPECIDIED;
        String bs = UNSPECIDIED;
        String lat = UNSPECIDIED;
        String lng = UNSPECIDIED;
        User.Address address = user.getAddress();
        User.Company company = user.getCompany();
        User.Address.Geo geo = address.getGeo();
        if (address != null) {
            street = address.getStreet();
            suite = address.getSuite();
            city = address.getCity();
            zipCode = address.getZipcode();

            if (geo != null) {
                lat = Double.toString(geo.getLat());
                lng = Double.toString(geo.getLng());
            }
        }
        if (company != null) {
            companyName = company.getCompanyName();
            catchPhrase = company.getCatchPhrase();
            bs = company.getBs();
        }

        userInfoView.assignValues(name, userName, phone, email, street, suite, city, zipCode, companyName, catchPhrase, bs, website, lat, lng);
    }

    public boolean checkFavorite() {
        return user.isFavorite();
    }

    public void updateFavorite(boolean isFav) {
        user.setFavorite(isFav);
    }


    public interface UserInfoView {

        void showToast(String msg);
        void assignValues(String name, String userName, String phone, String email, String street, String suite, String city, String zipCode, String companyName, String catchPhrase, String bs, String website, String lat, String lng);
    }
}

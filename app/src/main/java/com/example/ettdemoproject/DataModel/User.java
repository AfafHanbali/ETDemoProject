package com.example.ettdemoproject.DataModel;

import java.io.Serializable;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class User implements Serializable {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    private boolean favorite=false;

    public static class Address implements Serializable{

        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public Address() {
        }

        public static class Geo implements Serializable{
            private double lat;
            private double lng;

            public Geo() {
            }

        }
    }

    public static class Company implements Serializable{
        private String name;
        private String catchPhrase;
        private String bs;

        public Company() {
        }

    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {

        return username;
    }

    public String getEmail() {

        return email;
    }


    public String getStreet() {

        return address.street;
    }

    public String getSuite() {

        return address.suite;
    }

    public String getCity() {

        return address.city;
    }

    public String getZipCode() {

        return address.zipcode;
    }

    public double getLatt() {
        return address.geo.lat;
    }

    public double getLng() {
        return address.geo.lng;
    }

    public String getPhone() {

        return phone;
    }

    public String getWebsite() {

        return website;
    }

    public String getCompanyName() {

        return company.name;
    }

    public String getCatchPhrase() {

        return company.catchPhrase;
    }

    public String getBs() {

        return company.bs;
    }

    public void setFavorite(boolean fovorite) {
        this.favorite = fovorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

}


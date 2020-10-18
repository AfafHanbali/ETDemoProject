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

    private boolean isFavorite = false; //TODO : in java , def value for 'boolean' member is false .

    public static class Address implements Serializable {

        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public Address() {
        }

        public static class Geo implements Serializable {
            private double lat;
            private double lng;

            public Geo() {
            }

        }
    }

    public static class Company implements Serializable {
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
        // TODO : this might produce NullPointerException
        return address.street;
    }

    public String getSuite() {
        // TODO : this might produce NullPointerException

        return address.suite;
    }

    public String getCity() {
        // TODO : this might produce NullPointerException

        return address.city;
    }

    public String getZipCode() {
        // TODO : this might produce NullPointerException

        return address.zipcode;
    }

    public double getLatt() {
        // TODO : this might produce NullPointerException

        return address.geo.lat;
    }

    public double getLng() {
        // TODO : this might produce NullPointerException

        return address.geo.lng;
    }

    public String getPhone() {

        return phone;
    }

    public String getWebsite() {

        return website;
    }

    public String getCompanyName() {
        // TODO : this might produce NullPointerException

        return company.name;
    }

    public String getCatchPhrase() {
        // TODO : this might produce NullPointerException

        return company.catchPhrase;
    }

    public String getBs() {
        // TODO : this might produce NullPointerException

        return company.bs;
    }


    public void setFavorite(boolean fovorite) {
        this.isFavorite = fovorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

}


package com.example.ettdemoproject.DataModel;

import com.google.gson.annotations.SerializedName;

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

    private boolean isFavorite;

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

            public double getLat() {
                return lat;
            }

            public double getLng() {
                return lng;
            }

        }

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public Geo getGeo() {
            return geo;
        }
    }

    public static class Company implements Serializable {
        @SerializedName("name")
        private String companyName;
        private String catchPhrase;
        private String bs;

        public Company() {
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public String getBs() {
            return bs;
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


    public String getPhone() {

        return phone;
    }

    public String getWebsite() {

        return website;
    }

    public Address getAddress() {
        return address;
    }

    public Company getCompany() {
        return company;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

}


package com.example.ettdemoproject.MainFragments.Users;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class User implements Serializable {
    public static final String UNSPECIFIED = "Unspecified";

    private int id = -1;
    private String name = UNSPECIFIED;
    private String username = UNSPECIFIED;
    private String email = UNSPECIFIED;
    private Address address;
    private String phone = UNSPECIFIED;
    private String website = UNSPECIFIED;
    private Company company;

    private boolean isFavorite;

    public void setId(int id) {
        this.id = id;
    }

    public static class Address implements Serializable {

        private String street = UNSPECIFIED;
        private String suite = UNSPECIFIED;
        private String city = UNSPECIFIED;
        private String zipcode = UNSPECIFIED;
        private Geo geo;

        public Address() {
        }

        public static class Geo implements Serializable {

            private double lat = -1;
            private double lng = -1;

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
        private String companyName = UNSPECIFIED;
        private String catchPhrase = UNSPECIFIED;
        private String bs = UNSPECIFIED;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }


}


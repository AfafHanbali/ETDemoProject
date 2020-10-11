package com.example.ettdemoproject;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

//TODO : unless you make this class implements Serializable interface (and any other referenced class ) , you cant pass the whole object to an intent .
public class User {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    //TODO : memory leak  ,if it has no reference to its enclosing class ,  always make ur inner class static .
    public class Address {

        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public Address() {
        }

        public class Geo {
            private double lat;
            private double lng;

            public Geo() {
            }

        }
    }

    public class Company {
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

}


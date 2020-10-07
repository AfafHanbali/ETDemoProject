package com.example.ettdemoproject;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class User {
    private int id;
    private String name;
    private String username;
    private String email;

    public static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;

        public Address() {
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

        public class Geo {
            private String lat;
            private String lng;

            public Geo() {
            }

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }

        }
    }

    private String phone;
    private String website;

    public class Compnay {
        private String name;
        private String catchPhrase;
        private String bs;

        public Compnay() {
        }

        public String getName() {
            return name;
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
}

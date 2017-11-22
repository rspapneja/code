package com.example.fanaticaltechnology.retrofitdemohighmountains;

import java.util.List;

/**
 * Created by FanaticalTechnology on 11/22/2017.
 */

public class PostDataClass {

    /**
     * success : 1
     * message : Restaurants found.
     * data : [{"id":"5","email":"sethiankit16@gmail.com","role":"restaurant","latitude":"","longitude":"","user_id":"5","logo":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/profile/restaurant-1510298769_pizza.jpg","license_image":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/license_images/restaurant-1510298769_SL-Laundry-Linen_3Circles_Frame3b(1).jpg","contact_name":"Ankit","contact_number":"9316216000","delivery_time":"4","distance":"97.71626125499185","restaurant_images":[]},{"id":"25","email":"sfs.abhishek17@gmail.com","role":"restaurant","latitude":"","longitude":"","user_id":"25","logo":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/profile/restaurant-1510298725_junk-food-feature.jpg","license_image":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/license_images/restaurant-1510298725_SL-Laundry-Linen_3Circles_Frame3b(1).jpg","contact_name":"Mishri","contact_number":"9316216000","delivery_time":"15","distance":"97.71626125499185","restaurant_images":[]},{"id":"29","email":"gana.for.m1@gmail.com","role":"restaurant","latitude":"","longitude":"","user_id":"29","logo":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/profile/restaurant-1510298885_Snacks.jpg","license_image":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/license_images/restaurant-1510298885_mens-undergarments-250x250.jpg","contact_name":"Gana","contact_number":"6596981322","delivery_time":"45","distance":"97.71626125499185","restaurant_images":["http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/restaurant_images/restaurant-1509884702_image.jpg"]},{"id":"4","email":"vk9869@gmail.com","role":"restaurant","latitude":"","longitude":"","user_id":"4","logo":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/profile/restaurant-1510298618_Junk-food.jpg","license_image":"http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/license_images/restaurant-1510298618_mens-undergarments-250x250.jpg","contact_name":"ajit","contact_number":"123456","delivery_time":"15","distance":"97.71626125499185","restaurant_images":["http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/restaurant_images/restaurant-1509884702_image.jpg","http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/restaurant_images/restaurant-1508238331_image.jpg"]}]
     * customer_cart : []
     */

    private int success;
    private String message;
    private List<DataBean> data;
    private List<?> customer_cart;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<?> getCustomer_cart() {
        return customer_cart;
    }

    public void setCustomer_cart(List<?> customer_cart) {
        this.customer_cart = customer_cart;
    }

    public static class DataBean {
        /**
         * id : 5
         * email : sethiankit16@gmail.com
         * role : restaurant
         * latitude :
         * longitude :
         * user_id : 5
         * logo : http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/profile/restaurant-1510298769_pizza.jpg
         * license_image : http://sunfocussolutions.com/fooddeliveryservice/assets/uploads/restaurant/license_images/restaurant-1510298769_SL-Laundry-Linen_3Circles_Frame3b(1).jpg
         * contact_name : Ankit
         * contact_number : 9316216000
         * delivery_time : 4
         * distance : 97.71626125499185
         * restaurant_images : []
         */

        private String id;
        private String email;
        private String role;
        private String latitude;
        private String longitude;
        private String user_id;
        private String logo;
        private String license_image;
        private String contact_name;
        private String contact_number;
        private String delivery_time;
        private String distance;
        private List<?> restaurant_images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLicense_image() {
            return license_image;
        }

        public void setLicense_image(String license_image) {
            this.license_image = license_image;
        }

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<?> getRestaurant_images() {
            return restaurant_images;
        }

        public void setRestaurant_images(List<?>restaurant_images) {
            this.restaurant_images = restaurant_images;
        }
    }
}

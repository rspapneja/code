package com.example.home.demoretrofit.Model;

import java.util.List;

/**
 * Created by home on 11/9/2017.
 */

public class DestinationResponse {
    /**
     * payload : {"destinations":[{"destination_id":"8","title":"123","location":"New Delhi","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/","like_count":"0","total_pack":"0","total_activity":"2","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"1","title":"Chandigrah","location":"Chandigarh","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/download.jpg","like_count":"0","total_pack":"5","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"38","title":"dfjndkjnkv","location":"","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"18","title":"highmountain1","location":"Chandigarh","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/sendaquery.jpg","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"10","title":"huyyy","location":"Polican","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/Destinationall.png","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"12","title":"KLIUF","location":"Malanville","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"4","title":"New Delhi ","location":"Amritsar","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/images.png","like_count":"0","total_pack":"3","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"5","title":"New york Package","location":"New York","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/Tulips.jpg","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"2","destination_like_status":"2","bucket":"N"},{"destination_id":"29","title":"SANDEEP","location":"Chandigarh","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/themedetail.jpg","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"37","title":"smdhfbsmfhb","location":"","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"},{"destination_id":"15","title":"vikas","location":"Bala Morghab","image":"http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/3.jpg","like_count":"0","total_pack":"0","total_activity":"0","total_rentout":"0","destination_like_status":"2","bucket":"N"}]}
     * success : Destinations are fetched.
     * status : true
     */

    private PayloadBean payload;
    private String success;
    private String status;

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class PayloadBean {
        private List<DestinationsBean> destinations;

        public List<DestinationsBean> getDestinations() {
            return destinations;
        }

        public void setDestinations(List<DestinationsBean> destinations) {
            this.destinations = destinations;
        }

        public static class DestinationsBean {
            /**
             * destination_id : 8
             * title : 123
             * location : New Delhi
             * image : http://cluecorporations.com/highmountainsindia/include/backend/img/dest_img/
             * like_count : 0
             * total_pack : 0
             * total_activity : 2
             * total_rentout : 0
             * destination_like_status : 2
             * bucket : N
             */

            private String destination_id;
            private String title;
            private String location;
            private String image;
            private String like_count;
            private String total_pack;
            private String total_activity;
            private String total_rentout;
            private String destination_like_status;
            private String bucket;

            public String getDestination_id() {
                return destination_id;
            }

            public void setDestination_id(String destination_id) {
                this.destination_id = destination_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLike_count() {
                return like_count;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
            }

            public String getTotal_pack() {
                return total_pack;
            }

            public void setTotal_pack(String total_pack) {
                this.total_pack = total_pack;
            }

            public String getTotal_activity() {
                return total_activity;
            }

            public void setTotal_activity(String total_activity) {
                this.total_activity = total_activity;
            }

            public String getTotal_rentout() {
                return total_rentout;
            }

            public void setTotal_rentout(String total_rentout) {
                this.total_rentout = total_rentout;
            }

            public String getDestination_like_status() {
                return destination_like_status;
            }

            public void setDestination_like_status(String destination_like_status) {
                this.destination_like_status = destination_like_status;
            }

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }
        }
    }
}

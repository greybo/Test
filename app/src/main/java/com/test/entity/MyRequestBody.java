package com.test.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by m on 21.09.2017.
 */

public class MyRequestBody {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        if (data==null)
            data=new Data();
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("id")
        @Expose
        private int userId;
        @SerializedName("changesCount")
        @Expose
        private String changesCount;


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getChangesCount() {
            return changesCount;
        }

        public void setChangesCount(String changesCount) {
            this.changesCount = changesCount;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
          }

}
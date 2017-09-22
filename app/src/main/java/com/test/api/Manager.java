package com.test.api;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.dao.GitDataDao;
import com.test.dao.UsersDao;
import com.test.entity.GitData;
import com.test.entity.MyRequestBody;
import com.test.entity.Users;
import com.test.utils.TestСonstants;

import java.util.List;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by m on 20.09.2017.
 */

public class Manager {

    private static String apiKey = "key=AAAAOEg4f8Q:APA91bGZwWReIoORCrJuG_eGP1yck3vNbZzrqK6ItwuesCPclWgkklRdn4jtYmor7o3g94VIhfpKPl4gGVkiLUcKu2MqT-YXnRbaqpWFh1hwKKiM5QSLkL6TNtBg3mTRGkPO7aExMIuU";

    private final static String URL_FCM = "https://fcm.googleapis.com/";
    private final static String URL_GIT = "https://api.github.com/";
    private Retrofit retrofit;
    private GitDataDao gitDataDao;
    private UsersDao usersDao;
    private Handler handler;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    public Manager(Handler handler) {
        gitDataDao = new GitDataDao(handler);
        usersDao = new UsersDao(handler);
        this.handler = handler;
    }

    private RestApi getApi(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestApi.class);
    }

    public void getDataGit(String user) {
        getApi(URL_GIT).getData(user).enqueue(new Callback<List<GitData>>() {
            @Override
            public void onResponse(Call<List<GitData>> call, Response<List<GitData>> response) {
                Log.i(TestСonstants.TAG, "response data code " + response.code() + " error " + response.errorBody());
                if (response.body() != null) {
                    gitDataDao.saveAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GitData>> call, Throwable t) {
                Log.i(TestСonstants.TAG, "onFailure " + t.getMessage());
            }
        });
    }

    public void getUsers() {
        getApi(URL_GIT).getUesrs().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                Log.i(TestСonstants.TAG, "response user code " + response.code() + " error " + response.errorBody());
                if (response.body() != null) {
                    usersDao.saveAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.i(TestСonstants.TAG, "onFailure " + t.getMessage());
            }
        });
    }

    public void send(int id) {
        MyRequestBody body = new MyRequestBody();
        body.setTo("/topics/news");
        body.getData().setUserId(id);
        body.getData().setChangesCount("23");
        String text = gson.toJson(body);
        RequestBody requestBody = okhttp3.RequestBody.create(MediaType.parse("text/plain"), text);
        getApi(URL_FCM).sendToTopic(apiKey, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i(TestСonstants.TAG_FCM, "send code: " + response.code() + " send body: " + response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TestСonstants.TAG_FCM, "send onFailure: " + t.getMessage());
            }
        });
    }

    public GitDataDao getGitDataDao() {
        return gitDataDao;
    }

    public UsersDao getUsersDao() {
        return usersDao;
    }

    public void realmClose() {
        gitDataDao.close();
        usersDao.close();
    }

}

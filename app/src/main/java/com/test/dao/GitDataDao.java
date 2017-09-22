package com.test.dao;

import android.os.Handler;
import android.util.Log;

import com.test.entity.GitData;
import com.test.utils.TestСonstants;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by m on 20.09.2017.
 */

public class GitDataDao implements Dao<GitData> {

    private Realm realm;
    private Handler handler;

    public GitDataDao(Handler handler) {
        this.handler = handler;
        realm = Realm.getDefaultInstance();
    }

    public void readAll() {
        RealmQuery<GitData> query = realm.where(GitData.class);
        RealmResults<GitData> result1 = query.findAll();
        for (GitData data : result1) {
            if (data.getOwner() != null)
                Log.i(TestСonstants.TAG, data.toString());
        }
    }

    public void save(GitData data) {
        realm.beginTransaction();
        try {
            realm.insert(data);
        } catch (RealmPrimaryKeyConstraintException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
        readAll();
    }

    @Override
    public void saveAll(List<GitData> list) {
        realm.beginTransaction();
        try {
            realm.insert(list);
        } catch (RealmPrimaryKeyConstraintException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
        readAll();
    }
    public void close(){
        realm.close();
    }
}

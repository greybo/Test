package com.test.dao;

import android.os.Handler;
import android.util.Log;

import com.test.entity.Users;
import com.test.utils.TestСonstants;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by m on 20.09.2017.
 */

public class UsersDao implements Dao<Users> {
    private Realm realm;
    private Handler handler;

    public UsersDao(Handler handler) {
        this.handler = handler;
        realm = Realm.getDefaultInstance();
    }

    public void readAll() {
        RealmQuery<Users> query = realm.where(Users.class);
        RealmResults<Users> result1 = query.findAll();
        handler.obtainMessage(TestСonstants.HANDLER_USERS_LIST, result1).sendToTarget();
        for (Users user : result1) {
            if (user != null) {
                Log.i(TestСonstants.TAG, user.toString());
            }
        }
    }

    public void save(Users user) {
        realm.beginTransaction();
        try {
            realm.insert(user);
        } catch (RealmPrimaryKeyConstraintException e) {
            e.printStackTrace();

        }
        realm.commitTransaction();
        readAll();
    }

    public void saveAll(List<Users> list) {
        realm.beginTransaction();
        try {
            realm.insertOrUpdate(list);
        } catch (RealmPrimaryKeyConstraintException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
        readAll();
    }

    public void changesCount(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Users us = realm.where(Users.class).equalTo("id", id).findFirst();
                if (us != null)
                    us.setChangesCount(us.getChangesCount() + 1);
            }
        });
    }

    public long count() {
        return realm.where(Users.class).count();

    }

    public void close() {
        realm.close();
    }
}

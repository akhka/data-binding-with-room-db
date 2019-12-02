package com.gcs.testroomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {

    private UserDao dao;
    private LiveData<List<User>> mAllUsers;
    private TestDatabase db;

    public Repository(Application application){
        db = TestDatabase.getInstance(application);
        dao = db.dao();
        mAllUsers = dao.getAllUsers();
    }

    // Get All Existing Users
    public LiveData<List<User>> getAllUsers(){
        return mAllUsers;
    }
    // ======================


    // Get Users by IDs
    public List<User> getUsersByIds(int...ids){
        return dao.getAllByIds(ids);
    }
    // ======================


    // Insert Users
    public boolean insert(User...users){
        InsertTask task = new InsertTask(users);
        try {
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean>{

        private User[] users;

        public InsertTask(User[]users){
            this.users = users;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                if (users.length != 0){
                    /*for (int i=0; i<users.length; i++){
                        dao.insertAll(users[i]);
                    }*/
                    dao.insertAll(users);
                    return true;
                }
                else {
                    return false;
                }
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
    // ======================


    // Delete User
    public boolean delete(User user){
        DeleteTask task = new DeleteTask();
        try {
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private class DeleteTask extends AsyncTask<User, Void, Boolean>{

        @Override
        protected Boolean doInBackground(User... users) {
            if (users.length != 0){
                dao.delete(users[0]);
                return true;
            }
            else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }




}

package com.gcs.testroomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<User>> allUsers;
    private List<User> usersByIds;

    public UserViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        allUsers = repository.getAllUsers();
    }

    public boolean insert(User...users){
        return repository.insert(users);
    }

    public boolean delete(User user){
        return repository.delete(user);
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }



    public List<User> getUsersByIds(int...ids){
        try {
            return new GetByIdTask(ids).execute(repository).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class GetByIdTask extends AsyncTask<Repository, Void, List<User>>{

        private int[] ids;

        public GetByIdTask(int...ids){
            this.ids = ids;
        }

        @Override
        protected List<User> doInBackground(Repository... repositories) {
            return repositories[0].getUsersByIds(ids);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
        }
    }

}

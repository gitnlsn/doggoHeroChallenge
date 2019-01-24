package br.com.doghero.dhproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.json.JSONException;

import java.util.List;

import br.com.doghero.dhproject.api.DoggoHerosRetriever;
import br.com.doghero.dhproject.datatypes.DoggoHero;

public class MyHerosViewModel extends AndroidViewModel {

    private MutableLiveData<List<DoggoHero>> doggoHeroMutableLiveData;

    public MyHerosViewModel(@NonNull Application application) {
        super(application);
        doggoHeroMutableLiveData = new MutableLiveData<>();

        update_data();
    }

    public void update_data(){
        update_doggoHeroLiveData();
        // update other data here when necessary
    }

    private void update_doggoHeroLiveData(){
        try {
            doggoHeroMutableLiveData.setValue(DoggoHerosRetriever.retrieveDoggoHeros());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<DoggoHero>> getDoggoHeroMutableLiveData() {
        return doggoHeroMutableLiveData;
    }

}

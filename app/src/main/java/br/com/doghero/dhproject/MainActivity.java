package br.com.doghero.dhproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.doghero.dhproject.datatypes.DoggoHero;

//  Activity
//
//      - Single recycler view
//          - Adapter: RecViewAdapter
//          - Manager: LinearLayoutManager
//
//      - ViewModel: MyHeroesViewModel
//          - MutableLiveData: List<DoggoHeroes>

public class MainActivity extends AppCompatActivity implements RecViewAdapterDoggoHeros.DataChangeCallbacks {

    private RecyclerView                recyclerView;
    private RecViewAdapterDoggoHeros    doggoHerosRecViewAdapter;
    private MyHerosViewModel            myHerosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.myheros_recyclerview);
        doggoHerosRecViewAdapter = new RecViewAdapterDoggoHeros(this);

        recyclerView.setAdapter(doggoHerosRecViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myHerosViewModel = ViewModelProviders.of(this).get(MyHerosViewModel.class);
        myHerosViewModel.getDoggoHeroMutableLiveData().observe(this, new Observer<List<DoggoHero>>() {
            @Override
            public void onChanged(@Nullable List<DoggoHero> doggoHeroes) {
                doggoHerosRecViewAdapter.setDoggoHeroList(doggoHeroes);
            }
        });

    } // end - onCreate

    @Override
    public int uncheckFavorite(DoggoHero doggoHero) {
//        myHerosViewModel.getDoggoHeroMutableLiveData().setValue(
//                myHerosViewModel.getDoggoHeroMutableLiveData().getValue());
        return 0;
    }
}

package br.com.doghero.dhproject.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.doghero.dhproject.api.Intents.JSON_TAG;
import br.com.doghero.dhproject.datatypes.DoggoHero;

//  DoggoHerosRetriever
//      - Util that retrieves ArrayList DoggoHeroList
//      - Implements json parsing routines
//

//  SAMPLE JSON
//
//   "recents": [
//      {
//         "is_superhero": true,
//         "user": {
//            "first_name": "Eduardo",
//            "image_url": "https://doghero-uploads.s3.amazonaws.com/uploads/photo/1433381/sq135_DH_24012018123600937_98895.png"
//         },
//         "address_neighborhood": "Parque Chacabuco",
//         "price": 55
//      },
//      {
//         "id": 53012,
//         "is_superhero": false,
//         "user": {
//            "first_name": "Maria",
//            "image_url": "https://doghero-uploads.s3.amazonaws.com/uploads/photo/764186/sq135_20170404094603_345722_1491353162913.jpg"
//         },
//         "address_neighborhood": "Aclimação",
//         "price": 60
//      }
//   ],
//   "favorites": [
//      {
//         "is_superhero": false,
//         "user": {
//            "first_name": "Cris",
//            "image_url": "https://doghero-uploads.s3.amazonaws.com/uploads/photo/1088842/sq135_20170926010555_270986_1506441955458.jpg"
//         },
//         "address_neighborhood": "Vila Gomes Cardim",
//         "price": 70
//      },
//      {
//         "is_superhero": false,
//         "user": {
//            "first_name": "Gustavo",
//            "image_url": "https://doghero-uploads.s3.amazonaws.com/uploads/photo/242781/sq135_Cris_SA-MI.jpg"
//         },
//         "address_neighborhood": "Vila Mariana",
//         "price": 45
//      },
//      {
//         "is_superhero": true,
//         "user": {
//            "first_name": "Marina",
//            "image_url": "https://doghero-uploads.s3.amazonaws.com/uploads/photo/1398933/sq135_1516145372035_517808_IMG0535JPG.jpeg"
//         },
//         "address_neighborhood": "Vila Mariana",
//         "price": 65
//      }
//   ]

public class DoggoHerosRetriever {

    public static ArrayList<DoggoHero> fakeRetrieveDoggoHeros() throws JSONException {

        ArrayList<DoggoHero> doggoHeroArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(ApiAnswer.getMyHeroes());
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()){
            // There should be two keys and two array lists: recents and favorites
            String key = keys.next();
            JSONArray possibleDoggoHeroList = jsonObject.getJSONArray(key);             // jsonARRAY from jsonOBJECT
            for (int i=0; i<possibleDoggoHeroList.length(); i++){
                JSONObject possibleDoggoHero = possibleDoggoHeroList.getJSONObject(i);  // jsonOBJECT from jsonARRAY
                doggoHeroArrayList.add( build_DoggoHeroFromJSON(possibleDoggoHero, key) );
            }
        }
        return doggoHeroArrayList;
    }

    // TODO(006): query data from network
    public static ArrayList<DoggoHero> retrieveDoggoHeros() throws JSONException {

        ArrayList<DoggoHero> doggoHeroArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(ApiAnswer.getMyHeroes());    // TODO (004): if network call -> solve asynchronously
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()){
            // There should be two keys and two array lists: recents and favorites
            String key = keys.next();
            JSONArray possibleDoggoHeroList = jsonObject.getJSONArray(key);             // jsonARRAY from jsonOBJECT
            for (int i=0; i<possibleDoggoHeroList.length(); i++){
                JSONObject possibleDoggoHero = possibleDoggoHeroList.getJSONObject(i);  // jsonOBJECT from jsonARRAY
                doggoHeroArrayList.add( build_DoggoHeroFromJSON(possibleDoggoHero, key) );
            }
        }
        return doggoHeroArrayList;
    }

    public static DoggoHero build_DoggoHeroFromJSON(JSONObject jsonObject, String identifier_key) throws JSONException {

        DoggoHero.Builder builder = new DoggoHero.Builder();

        if (identifier_key.equals(JSON_TAG.FAVORITES)) builder.set_favorite(true);
        if (identifier_key.equals(JSON_TAG.RECENTS  )) builder.set_recent  (true);

        if(jsonObject.has(JSON_TAG.IS_SUPERHERO) ) builder.set_superHero          (jsonObject.getBoolean(JSON_TAG.IS_SUPERHERO  ));
        if(jsonObject.has(JSON_TAG.ADDRESS     ) ) builder.setNeighborhood_address(jsonObject.getString (JSON_TAG.ADDRESS       ));
        if(jsonObject.has(JSON_TAG.PRICE       ) ) builder.setRevervation_price   (jsonObject.getInt    (JSON_TAG.PRICE         ));
        if(jsonObject.has(JSON_TAG.ID          ) ) builder.setId                  (jsonObject.getInt    (JSON_TAG.ID            ));

        if(jsonObject.has(JSON_TAG.USER        ) ) { // resolving user nested information
            JSONObject possibleUserInformation = jsonObject.getJSONObject(JSON_TAG.USER);
            if(possibleUserInformation.has(JSON_TAG.FIRST_NAME)) builder.setFirst_name(possibleUserInformation.getString(JSON_TAG.FIRST_NAME));
            if(possibleUserInformation.has(JSON_TAG.IMAGE_URL )) builder.setPhoto_url (possibleUserInformation.getString(JSON_TAG.IMAGE_URL ));
        }
        DoggoHero doggoHero = builder.build();
//        doggoHero.log(); // logs if necessary

        return doggoHero;
    }

}


package br.com.doghero.dhproject.datatypes;


//  DoggoHero
//      DataTransferObject that stores doggoHeros informations
//      On RecyclerView, insert several of them inside a ArrayList

import android.util.Log;

public class DoggoHero {

    private int     id;
    private Boolean superHero;
    private Boolean favorite;
    private Boolean recent;
    private String  first_name;
    private String  photo_url;
    private String  neighborhood_address;
    private int     revervation_price;
    private int     score;

    //      BUILDER
    public static class Builder{

        private int     id;
        private Boolean superHero;
        private Boolean favorite;
        private Boolean recent;
        private String  first_name;
        private String  photo_url;
        private String  neighborhood_address;
        private int     revervation_price;
        private int     score;

        public Builder() {
            id                   = 0;
            superHero            = false;
            favorite             = false;
            recent               = false;
            first_name           = null;
            photo_url            = null;
            neighborhood_address = null;
            revervation_price    = 0;
            score                = 0;
        }
        public Builder setId(int id) {
            this.id = id;
            return this;
        }
        public Builder set_superHero(Boolean is_superHero) {
            this.superHero = is_superHero;
            return this;
        }
        public Builder set_favorite(Boolean is_favorite) {
            this.favorite = is_favorite;
            return this;
        }
        public Builder set_recent(Boolean is_recent) {
            this.recent = is_recent;
            return this;
        }
        public Builder setFirst_name(String first_name) {
            this.first_name = first_name;
            return this;
        }
        public Builder setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
            return this;
        }
        public Builder setNeighborhood_address(String neighborhood_address) {
            this.neighborhood_address = neighborhood_address;
            return this;
        }
        public Builder setRevervation_price(int revervation_price) {
            this.revervation_price = revervation_price;
            return this;
        }
        public Builder setScore(int score) {
            this.score = score;
            return this;
        }
        public DoggoHero build(){
            return new DoggoHero(id, superHero, favorite, recent, first_name, photo_url, neighborhood_address, revervation_price, score);
        }

    }

    public DoggoHero(int id, Boolean superHero, Boolean favorite, Boolean recent, String first_name, String photo_url, String neighborhood_address, int revervation_price, int score) {
        this.id = id;
        this.superHero = superHero;
        this.favorite = favorite;
        this.recent = recent;
        this.first_name = first_name;
        this.photo_url = photo_url;
        this.neighborhood_address = neighborhood_address;
        this.revervation_price = revervation_price;
        this.score = score;
    }

    //    GETTERS
    public int getId() {
        return id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getPhoto_url() {
        return photo_url;
    }
    public String getNeighborhood_address() {
        return neighborhood_address;
    }
    public int getRevervation_price() {
        return revervation_price;
    }
    public int getScore() {
        return score;
    }
    public Boolean is_SuperHero() {
        return superHero;
    }
    public Boolean is_Favorite() {
        return favorite;
    }
    public Boolean is_Recent() {
        return recent;
    }

    public boolean alt_favorite(){
        favorite = !favorite;
        return favorite;
    }

    public void log(){
        Log.v("NELSON", "DoggoHero - Log");
        if (id                  != 0   ) Log.v("NELSON", "id "      + id                    );
        if (superHero           !=null ) Log.v("NELSON", superHero ? "is super"    : "not super" );
        if (favorite            !=null ) Log.v("NELSON", favorite  ? "is favorite" : "not fav"   );
        if (recent              !=null ) Log.v("NELSON", recent    ? "is recent"   : "not recent");
        if (first_name          !=null ) Log.v("NELSON", "name "    +first_name             );
        if (photo_url           !=null ) Log.v("NELSON", "photo "   +photo_url              );
        if (neighborhood_address!=null ) Log.v("NELSON", "address " +neighborhood_address   );
        if (revervation_price   !=0    ) Log.v("NELSON", "price "   +revervation_price      );
        if (score               !=0    ) Log.v("NELSON", "score "   +score                  );
    }

}

package br.com.doghero.dhproject;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.doghero.dhproject.datatypes.DoggoHero;
import br.com.doghero.dhproject.images.ImageHelper;

public class RecViewAdapterDoggoHeros extends RecyclerView.Adapter<RecViewAdapterDoggoHeros.DoggoHerosViewHolder> {

    private Context         context;
    private List<DoggoHero> doggoHeroList;

    private static final int CATEGORY_NOTFOUND = 321;
    private static final int CATEGORY_FAVORITE = 543;
    private static final int CATEGORY_RECENTS  = 7899;

    private DataChangeCallbacks dataChangeCallbacksListener;

    private ExecutorService backgroundExecutor;

    public RecViewAdapterDoggoHeros(Context context) {

        this.context                     = context.getApplicationContext();
        this.dataChangeCallbacksListener = (DataChangeCallbacks) context;

        this.backgroundExecutor          = Executors.newCachedThreadPool();

    }

    @NonNull
    @Override
    public DoggoHerosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.doggo_hero_item, viewGroup, false);
        DoggoHerosViewHolder doggoHerosViewHolder = new DoggoHerosViewHolder(view);
        return doggoHerosViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(DoggoHerosViewHolder holder, int position) {
        holder.set(doggoHeroList.get(position), is_firstOfKind(position));
    }

    @Override
    public int getItemCount() {
        if (doggoHeroList==null) return 0;
        return doggoHeroList.size();
    }

    private boolean is_firstOfKind(int position){
        if (position == 0) return true;

        int actualItem_category = CATEGORY_NOTFOUND,
            prevItem_category   = CATEGORY_NOTFOUND;

        if (doggoHeroList.get(position).is_Favorite()) actualItem_category = CATEGORY_FAVORITE;
        if (doggoHeroList.get(position).is_Recent  ()) actualItem_category = CATEGORY_RECENTS;

        if (doggoHeroList.get(position -1).is_Favorite()) prevItem_category = CATEGORY_FAVORITE;
        if (doggoHeroList.get(position -1).is_Recent  ()) prevItem_category = CATEGORY_RECENTS;

        if (actualItem_category != prevItem_category) return true;

        return false;
    }

    public void setDoggoHeroList(List<DoggoHero> doggoHeroList) {
        this.doggoHeroList = doggoHeroList;
        notifyDataSetChanged();
    }

    interface DataChangeCallbacks {
        int uncheckFavorite(DoggoHero doggoHero);
    }

    class DoggoHerosViewHolder extends RecyclerView.ViewHolder{

        // TODO (006) : move those string to Resourses folder and access through getResourses
        private static final String CATEGORY_LABEL_FAVORITE = "Heróis Favoritos";
        private static final String CATEGORY_LABEL_RECENTS  = "Heróis com quem hospedei";

        private TextView
                category_textview,
                heroName_textview,
                heroAddress_textview,
                heroPrice_textview,
                heroscore_textView;

        private ImageView
                favorite_imageview,
                superbadge_imageview,
                photo_imageView;

        private Button
                talk_button,
                book_button;

        public DoggoHerosViewHolder(View itemView) {
            super(itemView);
            category_textview    = itemView.findViewById(R.id.category_label );
            heroName_textview    = itemView.findViewById(R.id.hero_name      );
            heroAddress_textview = itemView.findViewById(R.id.hero_address   );
            heroPrice_textview   = itemView.findViewById(R.id.hero_price     );
            heroscore_textView   = itemView.findViewById(R.id.hero_score     );

            photo_imageView      = itemView.findViewById(R.id.hero_photo    );
            superbadge_imageview = itemView.findViewById(R.id.badge_super   );
            favorite_imageview   = itemView.findViewById(R.id.badge_favorite);

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void set(final DoggoHero doggoHero, boolean set_categoryLabel){
            if (set_categoryLabel){
                if (doggoHero.is_Favorite()) category_textview.setText(CATEGORY_LABEL_FAVORITE);
                if (doggoHero.is_Recent  ()) category_textview.setText(CATEGORY_LABEL_RECENTS );
                category_textview.setVisibility(View.VISIBLE);
            }else category_textview.setVisibility(View.GONE);

            // doggoHero.log(); // debugging log

            heroName_textview   .setText(""  + doggoHero.getFirst_name           ()     );
            heroAddress_textview.setText(""  + doggoHero.getNeighborhood_address ()     );
            heroPrice_textview  .setText(""  + doggoHero.getRevervation_price    ()     );
            heroscore_textView  .setText("(" + doggoHero.getScore                () +")");

            ImageHelper.loadImageBackground(context.getApplicationContext(),doggoHero.getPhoto_url(),R.drawable.ic_launcher_foreground,photo_imageView);

            if(!doggoHero.is_Recent()){
                favorite_imageview  .setVisibility(View.VISIBLE);
                if(doggoHero.is_Favorite  ())
                    favorite_imageview.setImageDrawable(context.getDrawable(R.drawable.icon_like_filled_vector_red            ));
                else
                    favorite_imageview.setImageDrawable(context.getDrawable(R.drawable.icon_like_border_vector_gray_battleship));
            }
            if(doggoHero.is_SuperHero ()) superbadge_imageview.setVisibility(View.VISIBLE);

            favorite_imageview.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    if(doggoHero.alt_favorite())
                        favorite_imageview.setImageDrawable(context.getDrawable(R.drawable.icon_like_filled_vector_red            ));
                    else
                        favorite_imageview.setImageDrawable(context.getDrawable(R.drawable.icon_like_border_vector_gray_battleship));
                    dataChangeCallbacksListener.uncheckFavorite(doggoHeroList.get(getAdapterPosition()));
                }
            });

        }

        // TODO (002) : configure buttons - talk & book

    }

}

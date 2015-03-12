package kulku.ua.petcubetest.adapter;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;

import kulku.ua.petcubetest.R;
import kulku.ua.petcubetest.model.PetPost;

/**
 * Created by aindrias on 12.03.2015.
 */
public class PetPostsAdapter extends RecyclerView.Adapter<PetPostsAdapter.ViewHolder> {
    private List<PetPost> mPetPosts;

    public PetPostsAdapter(List<PetPost> petPosts) {
        mPetPosts = petPosts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet_post, parent, false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
        int margin = parent.getContext().getResources()
                .getDimensionPixelSize(R.dimen.pet_post_list_margin);
        params.topMargin = margin;
        params.bottomMargin = margin;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PetPost petPost = mPetPosts.get(position);
        holder.nameView.setText(petPost.name);
        holder.dateView.setText(DateFormat.getDateTimeInstance().format(petPost.date));
        holder.dateView.setVisibility(View.GONE);
        Picasso.with(holder.imageView.getContext())
                .load(petPost.img_url)
                .fit().centerCrop()
                .into(holder.imageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Palette.generateAsync(
                                ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap(),
                                new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch swatch = palette.getVibrantSwatch();
                                        if (swatch == null)
                                            swatch = palette.getMutedSwatch();
                                        if (swatch != null) {
                                            holder.dateView.setVisibility(View.VISIBLE);
                                            holder.dateView.setTextColor(swatch.getBodyTextColor());
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mPetPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_pet_post);
            nameView = (TextView) itemView.findViewById(R.id.name_pet_post);
            dateView = (TextView) itemView.findViewById(R.id.date_pet_post);
        }
    }
}

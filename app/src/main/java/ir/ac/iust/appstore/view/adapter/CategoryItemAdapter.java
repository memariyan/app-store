package ir.ac.iust.appstore.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.Category;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItemHolder>
{
    public interface CategoryItemHandler
    {
        void onItemClicked(int position, Category category);
    }

    private int lastPosition = -1;
    private Integer enabledItem = -1;
    private List<Category> categories;
    private CategoryItemHandler categoryItemHandler;
    private boolean miniSize;

    public CategoryItemAdapter(List<Category> categories, CategoryItemHandler categoryItemHandler)
    {
        this.categories = categories;
        this.categoryItemHandler = categoryItemHandler;
    }

    @Override
    public CategoryItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        return new CategoryItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryItemHolder holder, final int position)
    {
        Context context = holder.itemView.getContext();
        final Category category = categories.get(position);

        //set title
        holder.title.setText(category.getName());
        holder.icon.setImageResource(category.getIcon());
       // Glide.with(context).load(iconUrlPrefix + category.getIcon()).diskCacheStrategy(DiskCacheStrategy.RESULT).placeholder(R.drawable.category_icon_white).crossFade().into(holder.icon);

/*        Drawable drawable = ((CardView) holder.topLine).getBackground().mutate();
        drawable.setColorFilter(onColor, PorterDuff.Mode.SRC_IN);
        holder.topLine.setBackground(drawable);*/

        holder.icon.setColorFilter(context.getResources().getColor(R.color.primary_dark), PorterDuff.Mode.SRC_ATOP);

        setAnimation(holder.rootView, position);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                categoryItemHandler.onItemClicked(position, categories.get(position));
                selectItem(position);
            }
        });

        if(miniSize)
        {
            holder.icon.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.category_item_image_width_mini);
            holder.icon.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.category_item_image_width_mini);
            holder.infoLayout.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.category_item_layout_width_mini);
        }
        else
        {
            holder.icon.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.category_item_image_width);
            holder.icon.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.category_item_image_width);
            holder.infoLayout.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.category_item_layout_width);
        }
    }

    public List<Category> getCategories()
    {
        return categories;
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }

    public void selectItem(int position)
    {
        int previousSelectedItem = enabledItem;
        enabledItem = position;
        notifyItemChanged(previousSelectedItem);
        notifyItemChanged(enabledItem);
    }

    public class CategoryItemHolder extends RecyclerView.ViewHolder
    {
        public CustomTextView title;
        public ImageView icon;
        public View cardView;
        public View topLine;
        public View rootView;
        public View infoLayout;

        public CategoryItemHolder(View view)
        {
            super(view);

            title = (CustomTextView) view.findViewById(R.id.root_category_item_title);
            icon = (ImageView) view.findViewById(R.id.root_category_item_icon);
            cardView = view.findViewById(R.id.root_item_card_view);
            topLine = view.findViewById(R.id.parent_item_card_view);
            infoLayout = view.findViewById(R.id.category_item_layout);
            rootView = view.findViewById(R.id.layout_guild_tree_item_root_view);
        }
    }

    public Integer getEnabledItem()
    {
        return enabledItem;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public boolean isMiniSize()
    {
        return miniSize;
    }

    public void setMiniSize(boolean miniSize)
    {
        this.miniSize = miniSize;
    }
}

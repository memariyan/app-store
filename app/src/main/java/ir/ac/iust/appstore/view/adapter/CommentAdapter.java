package ir.ac.iust.appstore.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentItemHolder>
{
    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments)
    {
        this.comments = comments;
    }

    @Override
    public CommentAdapter.CommentItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        return new CommentAdapter.CommentItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.CommentItemHolder holder, final int position)
    {
        Comment comment = comments.get(position);
        holder.author.setText(comment.getAuthor());
        holder.content.setText(comment.getText());
        holder.date.setText(comment.getDate());
        holder.rate.setRating((float) comment.getRate());
    }

    @Override
    public int getItemCount()
    {
        return comments.size();
    }

    class CommentItemHolder extends RecyclerView.ViewHolder
    {
        CustomTextView author;
        CustomTextView content;
        CustomTextView date;
        RatingBar rate;

        CommentItemHolder(View view)
        {
            super(view);

            author = (CustomTextView) view.findViewById(R.id.comment_author);
            content = (CustomTextView) view.findViewById(R.id.comment_content);
            date = (CustomTextView) view.findViewById(R.id.comment_date);
            rate = (RatingBar) view.findViewById(R.id.comment_rating_bar);

            ViewTools.setRTLSupportSettings(view);
        }
    }
}

package ir.ac.iust.appstore.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.util.JalaliCalendar;
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
        holder.author.setText(comment.getAuthor().getFirstName()+" "+comment.getAuthor().getLastName());
        holder.content.setText(comment.getText());
        holder.rate.setRating((float) comment.getRate());

        JalaliCalendar jalaliCalendar = new JalaliCalendar();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(comment.getDate().getTime());
        jalaliCalendar.fromGregorian(gregorianCalendar);

        String dateTime= jalaliCalendar.getYear()+"/"+jalaliCalendar.getMonth()+"/"+jalaliCalendar.getDay();
        holder.date.setText(dateTime);
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

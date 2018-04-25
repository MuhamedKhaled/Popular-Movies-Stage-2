package com.example.mohamed.popularmovies.adapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.databinding.ReviewListItemBinding;
import com.example.mohamed.popularmovies.model.Review;

import java.util.ArrayList;

/**
 * Created by mohamed
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private Context context;
    private OnReviewClickListener listener;
    private ArrayList<Review> reviews;

    public ReviewsAdapter(Context context, OnReviewClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ReviewListItemBinding itemBinding = ReviewListItemBinding.inflate(inflater, parent, false);
        return new ReviewViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) return 0;
        return reviews.size();
    }

    public void updateData(ArrayList<Review> newReviews) {
        this.reviews = newReviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ReviewListItemBinding itemBinding;

        public ReviewViewHolder(ReviewListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Review review) {
            SpannableString moreText = new SpannableString(context.getString(R.string.textview_ellipsize_text));
            moreText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)), 0, moreText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            itemBinding.reviewContent.setEllipsizeText(moreText, 0);
            itemBinding.setReview(review);
            itemBinding.setHandlers(this);
            itemBinding.executePendingBindings();
        }

        public void openFullReview(View v) {
            listener.onReviewClicked(reviews.get(getAdapterPosition()));
        }
    }

    public interface OnReviewClickListener {
        void onReviewClicked(Review review);
    }
}

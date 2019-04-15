package ir.ac.iust.appstore.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import ir.ac.iust.appstore.R;

public class CategoryFragment extends Fragment
{
    public CategoryFragment()
    {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance()
    {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}

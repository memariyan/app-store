package ir.ac.iust.appstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.view.ViewTools;

public class HomeFragment extends Fragment
{
    public HomeFragment()
    {
    }

    public static HomeFragment newInstance()
    {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView= inflater.inflate(R.layout.fragment_home, container, false);
        ViewTools.setLTRSupportSettings(rootView);

        return rootView;
    }
}

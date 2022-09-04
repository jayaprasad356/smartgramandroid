package com.jp.smartgram;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    public ViewPagerAdapter(
            @NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new BlankFragment();
        else if (position == 1)
            fragment = new BlankFragment();
        else if (position == 2)
            fragment = new BlankFragment();


        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Description";
        else if (position == 1)
            title = "Manufacture";

        else if (position == 2)
            title = "Details";


        return title;
    }
}
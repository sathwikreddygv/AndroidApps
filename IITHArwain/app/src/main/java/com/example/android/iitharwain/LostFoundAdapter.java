package com.example.android.iitharwain;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class LostFoundAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public LostFoundAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LostItemFragment();
        } else {
            return new FoundItemFragment();
        }
    }
    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.lost_item);
        } else  {
            return mContext.getString(R.string.found_item);
        }
    }
}

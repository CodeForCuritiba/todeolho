package br.com.todeolho.todeolho;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.titulo_pesquisar));
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_pesquisa);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_perfil);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_ranking);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_amigos);
        tabLayout.getTabAt(4).setIcon(R.drawable.icon_historico);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch (position){
            case 0:
                toolbar.setTitle(getString(R.string.titulo_pesquisar));
                break;
            case 1:
                toolbar.setTitle(getString(R.string.titulo_perfil));
                break;
            case 2:
                toolbar.setTitle(getString(R.string.titulo_ranking));
                break;
            case 3:
                toolbar.setTitle(getString(R.string.titulo_amigos));
                break;
            case 4:
                toolbar.setTitle(getString(R.string.titulo_historico));
                break;
        }

        setSupportActionBar(toolbar);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new RootPesquisarFragment();
                    break;
                case 1:
                    fragment = new PerfilFragment();
                    break;
                case 2:
                    fragment = new RankingFragment();
                    break;
                case 3:
                    fragment = new AmigosFragment();
                    break;
                case 4:
                    fragment = new HistoricoFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}

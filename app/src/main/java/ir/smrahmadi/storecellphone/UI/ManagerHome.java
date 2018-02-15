package ir.smrahmadi.storecellphone.UI;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ir.smrahmadi.storecellphone.R;
import ir.smrahmadi.storecellphone.UI.TabFragments.CategoriesFragment;
import ir.smrahmadi.storecellphone.UI.TabFragments.ManagersFragment;
import ir.smrahmadi.storecellphone.UI.TabFragments.ReportFragment;
import ir.smrahmadi.storecellphone.UI.TabFragments.StoreFragment;
import ir.smrahmadi.storecellphone.Utils.ViewPagerAdapter;

public class ManagerHome extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_group_black_24dp,
            R.drawable.ic_view_comfy_black_24dp,
            R.drawable.ic_store_black_24dp,
            R.drawable.ic_equalizer_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        this.setTitle("Manager Panel");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_store_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();





    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ManagersFragment(), "Managers");
        adapter.addFragment(new CategoriesFragment(), "Categores");
        adapter.addFragment(new StoreFragment(), "Store");
        adapter.addFragment(new ReportFragment(), "Report");
        viewPager.setAdapter(adapter);

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

}
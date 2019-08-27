package pe.com.app.unibell.appunibell.Flujo_Seguimiento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_Flujo_Seguimiento  extends AppCompatActivity {

    private static final int REQUEST_CODE_1=1;
    private ViewPager mViewPager;

    private Boolean iValor=false;
    private String searchText;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Funciones funciones= new Funciones();
    private String Fragment_detalle;
    private String Fragment_flujo;
    private String Fragment_seguimiento;

    public String getFragment_detalle() {
        return Fragment_detalle;
    }

    public void setFragment_detalle(String fragment_detalle) {
        Fragment_detalle = fragment_detalle;
    }

    public String getFragment_flujo() {
        return Fragment_flujo;
    }

    public void setFragment_flujo(String fragment_flujo) {
        Fragment_flujo = fragment_flujo;
    }

    public String getFragment_seguimiento() {
        return Fragment_seguimiento;
    }

    public void setFragment_seguimiento(String fragment_seguimiento) {
        Fragment_seguimiento = fragment_seguimiento;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flujo_seguimiento);

        setToolbar(); // Añadir la toolbar

        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Detalle(), "Detalle");
        adapter.addFragment(new Fragment_Flujo(),"Flujo");
        adapter.addFragment(new Fragment_Seguimiento(),"Seguimiento");
        viewPager.setAdapter(adapter);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Planilla-N°99-22509");
        }
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        final MenuItem refresh = menu.findItem(R.id.action_ordenfresh);
        refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
            FragDetalle();
            FragFlujo();
            FragSeguimiento();
        return false;
            }
        });
        return true;
    }

    private void FragDetalle(){
        String TabFragment = getFragment_detalle();
        Fragment_Detalle fragment_detalle = (Fragment_Detalle)getSupportFragmentManager().findFragmentByTag(TabFragment);
        if (TabFragment != null ) {
            fragment_detalle.CargarData();
        }
    }
    private void FragFlujo(){
        String TabFragment = getFragment_flujo();
        Fragment_Flujo fragment_flujo = (Fragment_Flujo) getSupportFragmentManager().findFragmentByTag(TabFragment);
        if (TabFragment != null ) {
            fragment_flujo.CargarData();
        }
    }
    private void FragSeguimiento(){
        String TabFragment = getFragment_seguimiento();
        Fragment_Seguimiento fragment_seguimiento = (Fragment_Seguimiento)getSupportFragmentManager().findFragmentByTag(TabFragment);
        if (TabFragment != null ) {
            fragment_seguimiento.CargarData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case 1:
                    FragDetalle();
                    FragFlujo();
                    FragSeguimiento();
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FragDetalle();
        FragFlujo();
        FragSeguimiento();
    }


}

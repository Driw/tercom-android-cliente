package br.com.tercom.Boundary.BoundaryUtil;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Boundary.Activity.LoginActivity;
import br.com.tercom.Boundary.Activity.ManufacturerActivity;
import br.com.tercom.Boundary.Activity.MenuActivity;
import br.com.tercom.Boundary.Activity.ProductAddActivity;
import br.com.tercom.Boundary.Activity.ProductListActivity;
import br.com.tercom.Boundary.Activity.ProviderListActivity;
import br.com.tercom.Boundary.Activity.ServiceAddActivity;
import br.com.tercom.Boundary.Activity.ServiceListActivity;
import br.com.tercom.Enum.EnumFont;
import br.com.tercom.R;
import br.com.tercom.Util.CustomTypeFace;

import static br.com.tercom.Application.AppTercom.USER_STATIC;
import static br.com.tercom.Util.CustomTypeFace.overrideFonts;
import static br.com.tercom.Util.CustomTypeFace.setFontSingleTxt;

/**
 * Created by Felipe on 14/06/2017.
 */

public abstract class AbstractAppCompatActivity extends AppCompatActivity {


    public void createToolbar() {
        Toolbar mToolbar = findViewById(R.id.include_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        TextView textTitle = mToolbar.findViewById(R.id.textTitle);
         textTitle.setTypeface(setFontSingleTxt(AppTercom.getContext(),EnumFont.FONT_RNS));
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return (true);

            default:
                return super.onOptionsItemSelected(item);
        }

    }



    public void createIntentAbs(Class classe){
        Intent intent = new Intent();
        intent.setClass(this,classe);
        startActivity(intent);
    }



    public void createToolbarWithNavigation(int index) {
        Toolbar mToolbar = findViewById(R.id.include_toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitleMarginBottom(10);
        setSupportActionBar(mToolbar);
        TextView textTitle = mToolbar.findViewById(R.id.textTitle);
//        textTitle.setTypeface(setFontSingleTxt(AppTercom.getContext(), EnumFont.FONT_RNS));
        CreateNavigationDrawer(mToolbar, index);
    }


    public void CreateNavigationDrawer( Toolbar toolbar, int identifier)
    {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withProfileImagesVisible(true)
                .withCompactStyle(true)
                .withDividerBelowHeader(true)
                .addProfiles(new ProfileDrawerItem().withEmail(USER_STATIC.getTercomEmployee().getEmail()).withName(USER_STATIC.getTercomEmployee().getName()))
                .withTextColor(Color.BLACK)
                .build();

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSliderBackgroundColor(Color.WHITE)
                .withSelectedItem(identifier)
                .addStickyDrawerItems(CreateItem(6, "Logout", Color.BLACK, Color.BLACK, getResources().getColor(R.color.colorAccent), R.drawable.ic_logout))
                .addDrawerItems(
                        CreateItem(1, "Perfil", Color.BLACK, Color.WHITE, getResources().getColor(R.color.colorAccent), R.drawable.ic_profile),
                        CreateItem(2, "Produtos", Color.BLACK, Color.WHITE, getResources().getColor(R.color.colorAccent), R.drawable.ic_box),
                        CreateItem(3, "Serviços", Color.BLACK, Color.WHITE, getResources().getColor(R.color.colorAccent), R.drawable.ic_tools),
                        CreateItem(4, "Fornecedores", Color.BLACK, Color.WHITE, getResources().getColor(R.color.colorAccent), R.drawable.ic_truck),
                        CreateItem(5, "Fabricantes", Color.BLACK, Color.WHITE, getResources().getColor(R.color.colorAccent), R.drawable.ic_industry)

                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //TODO
                        switch (position) {
                            case 1:
                                createIntentAbs(MenuActivity.class);
                                break;
                            case 2:
                                createIntentAbs(ProductListActivity.class);
                                break;
                            case 3:
                                createIntentAbs(ServiceListActivity.class);
                                break;
                            case 4:
                                createIntentAbs(ProviderListActivity.class);
                                break;
                            case 5:
                                createIntentAbs(ManufacturerActivity.class);
                                break;
                            default:
                                createIntentAbs(LoginActivity.class);
                                break;
                        }
                        return true;
                    }
                }).build();
    }

    public SecondaryDrawerItem CreateItem(int identifier, String name, int textColor, int selectedTextColor, int selectedColor, int icon)
    {
        return new SecondaryDrawerItem()
                .withIdentifier(identifier)
                .withName(name)
                .withTextColor(textColor)
                .withSelectedTextColor(selectedTextColor)
                .withSelectedColor(selectedColor)
                .withTypeface(CustomTypeFace.setFontSingleTxt(this, EnumFont.FONT_ROBOTO_REGULAR))
                .withIcon(icon);

    }


}

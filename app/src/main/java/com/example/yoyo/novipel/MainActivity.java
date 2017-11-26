package com.example.yoyo.novipel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yoyo.novipel.drawers.LeftDrawerArrayAdapter;
import com.example.yoyo.novipel.drawers.RightDrawerArrayAdapter;
import com.example.yoyo.novipel.fragments.ButtonsPage;
import com.example.yoyo.novipel.fragments.Dots;
import com.example.yoyo.novipel.fragments.InsideNovipelPage;
import com.example.yoyo.novipel.fragments.SwipeInformation;
import com.example.yoyo.novipel.fragments.ThisIsSensPage;
import com.example.yoyo.novipel.fragments.WelcomePage;
import com.example.yoyo.novipel.popWindow.ExpandableListAdapter;
import com.example.yoyo.novipel.popWindow.Pop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private int countPager = 1;
    private FrameLayout layout;
    private ViewGroup pointContainer;
    private Button nextPageButton;
    private boolean connectedToDevice = false;
    private TextView down_text;
    private String[] list_down_text;
    private Toolbar toolbar;
   // private ImageView img;
    private PopupWindow pwindo;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private int titleHeight = 0;



    // to add a new page to the list
    // that you can navigate to with the next button
    // add the fragment class

    private Class[] pages = {
            WelcomePage.class,
           // Dots.class,
            ThisIsSensPage.class
          //  InsideNovipelPage.class,
          //  SwipeInformation.class,
           // ButtonsPage.class

    };

    // this index points to the next page in the index
    private int index = 0;

    // the burger menu
//    private DrawerLayout mDrawerLayout;
//    private ListView mLeftDrawerList;
//    private ListView mRightDrawerList;

//    private String[] mListTitlesLeft;
//    private String[] mListTitlesRight;


    private void initiatePopupWindow() {
        try {

            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.window,
                    (ViewGroup) findViewById(R.id.herebabe));
            pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static View getToolbarLogoIcon(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();

        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);

        View logoIcon = null;
        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }

        if(hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ExpandableListView) findViewById(R.id.lvExp);//the accordion

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_menu);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("noviPel");
        View logoView = getToolbarLogoIcon(toolbar);//get the burger icon and attach an event to it
        logoView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //test with Toast to see if the icon responds to the event
               // Toast.makeText(getApplicationContext(), "hi ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, Pop.class));
            }
        });

        layout = (FrameLayout) findViewById(R.id.window);//find framelayout View element in activity_main.xml
        AssetManager am = getApplicationContext().getAssets();

//        typeface = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "Dubiel.ttf"));

        //initializing down_text
        down_text = (TextView)  findViewById(R.id.welcome_text); //This is the text that appears at the bottom
        //down_text.setTypeface(typeface);

        list_down_text = getResources().getStringArray(R.array.down_text);//The text which will appear at the down_text
//        mLeftDrawerList = (ListView) findViewById(R.id.left_drawer);
//        mRightDrawerList = (ListView) findViewById(R.id.right_drawer);
//        mLeftDrawerList.setOnItemClickListener(new LeftDrawerItemClickListener());
//        mRightDrawerList.setOnItemClickListener(new RightDrawerItemClickListener());
//
//        // nextPageButton = (ImageButton) findViewById(R.id.button);
//        mListTitlesLeft = getResources().getStringArray(R.array.list_array_burgermenu_left);
//        mListTitlesRight = getResources().getStringArray(R.array.list_array_burgermenu_right);
//        Log.i("RIGHT", mListTitlesRight[1]);
//        updateDrawers();

        layout = (FrameLayout) findViewById(R.id.window);//whenever we click on the Fragment will fire an event
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index++;
                if (index >= 2) {
                    index = 1;
                    
                } else {
                    Fragment fragment = null;
                    try {

//                        if(index == 1)
//                        {
//                            fragment = (Fragment) pages[index].newInstance();
//                            fragment.
//
//                        }
                        fragment = (Fragment) pages[index].newInstance();

                        Toast.makeText(MainActivity.this, "the index is "+index, Toast.LENGTH_SHORT).show();
                        if(index == 1)
                        {
                            Dots currentfragment = Dots.class.newInstance();
                            Log.i("adding new fragment", "adding new fragment");
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.window, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                           // fragment.
                           // currentfragment.receiveUpdate(1);
                        }else
                        {

                        Log.i("adding new fragment", "adding new fragment");
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.window, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        }

                        down_text.setText(list_down_text[index]);//for text


                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
                int value = getFragmentManager().getBackStackEntryCount();
                Log.i("backStack after click", "" + value);

            }
        });

      //  pagesNumbers = (Button) findViewById(R.id.pages);
      //  pagesNumbers.setTypeface(typeface);
       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //mLeftDrawerList = (ListView) findViewById(R.id.left_drawer);
        //mRightDrawerList = (ListView) findViewById(R.id.right_drawer);

        if (findViewById(R.id.window) != null) {//initialize the first fragment the first time the application fires

            if (savedInstanceState != null) {
                return;
            }
            Fragment first = null;
            try {
                first = (Fragment) pages[0].newInstance();

                down_text.setText(list_down_text[0]);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.window, first);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            int value = getFragmentManager().getBackStackEntryCount();
            Log.i("backStack after click", "" + value);
        }



        View root = (ViewGroup)findViewById(R.id.drawer_layout);
        root.post(new Runnable() {
            public void run(){
                Rect rect = new Rect();
                Window win = getWindow();
                win.getDecorView().getWindowVisibleDisplayFrame(rect);
                int statusHeight = rect.top;
                int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
                int titleHeight = contentViewTop - statusHeight;
                MainActivity.this.titleHeight = titleHeight;
                Log.w("dimens_tag", "title = " + titleHeight + " status bar = " + statusHeight);
            }
        });



    }

    //onBackPressed ovveridden because when we press back button we need down_text set correctly
//    @Override
//    public void onBackPressed()
//    {
//        int value = getFragmentManager().getBackStackEntryCount();
//        Log.i("The backstack COUNT ", " " + value);
//        if (value == 1)
//        {
//            finish();
//            System.exit(0);
//        } else if (value > 1)
//        {
//            getFragmentManager().popBackStack();
//          //  pagesNumbers.setText((index) + "/" + (pages.length-1));
//            down_text.setText(list_down_text[index-1]);
//
//        } else{
//            Log.i("This is not first time", "NOTFIRST");
//            super.onBackPressed();
//
//        }
//
//        index--;
//        if (!(index == pages.length - 2 || index == pages.length - 1))
//        {
//
//        }
//
//    }





    @Override
    public void onBackPressed() {
        int value = getFragmentManager().getBackStackEntryCount();
        Log.i("The backstack COUNT ", " " + value);
        if (value == 1) {
            finish();
            System.exit(0);
        } else if (value > 1) {
            getFragmentManager().popBackStack();
           // pagesNumbers.setText((index) + "/" + (pages.length-1));
            down_text.setText(list_down_text[index-1]);

        } else {
            Log.i("This is not first time", "NOTFIRST");
            super.onBackPressed();

        }

        index--;
        if (!(index == pages.length - 2 || index == pages.length - 1)) {
            //skipButton.setEnabled(true);
            //skipButton.setText("Skip");
        }

        if (!(index == pages.length - 1)) {
            //next.setEnabled(true);
            //next.setText("Next");
            //next.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }

        // index--;
    }


//    private void updateDrawers() {
//        Connection connection = new Connection(this);
//
//        // update the titles based on the connection
//        //mListTitlesRight = connection.isConnected() ? getResources().getStringArray(R.array.list_array_connected_right)
//        //        : getResources().getStringArray(R.array.list_array_disconnected);
//
//        // get the actual pixel dimensions
//        DisplayMetrics metrics = new DisplayMetrics();
//
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        Log.i("HEIGHT: ", metrics.heightPixels + "");
//
//
//        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mLeftDrawerList
//                .getLayoutParams();
//
//
//
//        //int drawerHeight = metrics.heightPixels - mlp.topMargin;
//
//        //Log.i("MARGINTOP: ", "" + mlp.topMargin);
//
//
//        int drawerHeight = metrics.heightPixels - MainActivity.this.titleHeight;// - mlp.topMargin;
//        int drawerWidth = (metrics.widthPixels * 2) / 3;
//        Log.i("DRAWERHEIGHT:" , metrics.heightPixels + "");
//        Log.i("MARGINTOP: " , "" + mlp.topMargin);
//        mLeftDrawerList.setMinimumWidth(drawerWidth);
//        mRightDrawerList.setMinimumWidth(drawerWidth);
//
//        // Set the adapter for the list view
//        mLeftDrawerList.setAdapter(new LeftDrawerArrayAdapter(this, R.layout.drawer_list_item_left, mListTitlesLeft, connection.isConnected(), drawerHeight));
//        mRightDrawerList.setAdapter(new RightDrawerArrayAdapter(this, R.layout.drawer_list_item_right, mListTitlesRight, connection.isConnected(), drawerHeight));
//
//
//    }


  //  private class LeftDrawerItemClickListener implements ListView.OnItemClickListener {

//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//            selectItem(position);
//        }

//        public void selectItem(int position) {
//
//            //Connection connection = new Connection(MainActivity.this);
//            Log.i("Tapped position LEFT: ", position + "");
//
//            switch (position) {
//                case 0:
//                    try {
//                        mDrawerLayout.closeDrawers();
//                        Fragment fragment = ButtonsPage.class.newInstance();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.window, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    } catch (InstantiationException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    Intent intent = new Intent(MainActivity.this, Website.class);
//                    mDrawerLayout.closeDrawers();
//                    startActivity(intent);
//                    break;
//            }
//        }
//
//    }

//    private class RightDrawerItemClickListener implements ListView.OnItemClickListener {
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//            selectItem(position);
//        }
//
//        public void selectItem(int position) {
//
//            //Connection connection = new Connection(MainActivity.this);
//
//            switch (position) {
//                case 0:
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    break;
//            }
//        }

   // }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//       // toolbar.inflateMenu(R.menu.menu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
//        {
//            @Override
//            public boolean onMenuItemClick(MenuItem item)
//            {
//                return onOptionsItemSelected(item);
//            }
//        });
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            // action with ID action_refresh was selected
//            case R.id.home:
//                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT)
//                        .show();
//                break;
//            // action with ID action_settings was selected
//            case R.id.btd:
//            Toast.makeText(this, "Buy the device", Toast.LENGTH_SHORT)
//                    .show();
//            break;
//            case R.id.inst:
//                Toast.makeText(this, "Instructions", Toast.LENGTH_SHORT)
//                        .show();
//                break;
//            case R.id.faq:
//                Toast.makeText(this, "FAQ", Toast.LENGTH_SHORT)
//                        .show();
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }

}
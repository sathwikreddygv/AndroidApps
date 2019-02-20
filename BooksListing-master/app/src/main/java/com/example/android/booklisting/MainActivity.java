package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>  {

    private static final int BOOK_LOADER_ID =1;
    private BookAdapter bookAdapter;
    private ListView bookListView;
    private  static String reuqestURLString = "https://www.googleapis.com/books/v1/volumes" ;
    private LoaderManager loaderManager;
    private TextView emptyView;
    private View loadingIndicator;
    private boolean isConnected;
    private ArrayList<Book> tempBooks;
    private EditText searchText;
    private String LOG_TAG = MainActivity.class.getName();
    private String keyWord;
    private SharedPreferences sharedPrefs;
    private FirebaseAuth mAuth;
    String filterBy;
    public static boolean isPrefChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();



       searchText = findViewById(R.id.search_editText);
       searchText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchText.setCursorVisible(true);
           }
       });


        final Button searchButton = findViewById(R.id.search_button);
        emptyView = findViewById(R.id.empty_view);
        emptyView.setText("Search!");

        // Loader manager
        loaderManager = getLoaderManager();
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = searchText.getText().toString();

                if (keyWord.equals("")){
                    Toast newToast = Toast.makeText(MainActivity.this, "Please type in a word.", Toast.LENGTH_SHORT);
                    newToast.show();

                } else {
                    if (isConnected==false){
                        emptyView.setText(R.string.no_internet);
                        return;
                    }
                    loaderStart();
                    hideKeyboard();
                    searchText.setCursorVisible(false);
                }


            }
        });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    if (isConnected==false){
                        emptyView.setText(R.string.no_internet);
                        return false;
                    }
                    keyWord = searchText.getText().toString();

                    loaderStart();
                    hideKeyboard();
                    searchText.setCursorVisible(false);                }

                return false;
            }
        });

        //create new book adapter init



        if (tempBooks==null){
            tempBooks = new ArrayList<Book>();

        } else if (tempBooks != null && savedInstanceState.getParcelableArrayList("bookList")!=null) {
                tempBooks = savedInstanceState.getParcelableArrayList("bookList");
                bookListView.setAdapter(bookAdapter);
        }


        if (bookAdapter==null){
            bookAdapter = new BookAdapter(this, tempBooks);

        }
        //assign the list view
        bookListView = findViewById(R.id.books_list_view);

        bookListView.setAdapter(bookAdapter);

        //set empty view
        bookListView.setEmptyView(emptyView);

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        filterBy = sharedPrefs.getString(getString(R.string.settings_filter_by_key), getString(R.string.settings_filter_by_default));

        Uri baseUri = Uri.parse(reuqestURLString);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", keyWord);
        uriBuilder.appendQueryParameter("maxResults","20");
        uriBuilder.appendQueryParameter("filter", filterBy);

        Log.d(LOG_TAG, "onCreateLoader: "  + uriBuilder.toString());


        if (uriBuilder==null){
            return null;
        }

        return new BookLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {

        loadingIndicator.setVisibility(View.GONE);

        if (bookAdapter!=null){
            bookAdapter.clear();
        }

        connectionChecker();

        tempBooks = (ArrayList) data;

        if (tempBooks !=null && !tempBooks.isEmpty()){
            bookAdapter.addAll(tempBooks);
        } else if (tempBooks==null){
            emptyView.setText(R.string.no_books);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
     mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(LOG_TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }

            // ...
        }
    });
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
    public void loaderStart(){

        emptyView.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);

        if (bookAdapter!=null){
            bookAdapter.clear();
        }



        if (loaderManager.getLoader(1)!=null){
            loaderManager.restartLoader(BOOK_LOADER_ID, null,this);
        } else {
            loaderManager.initLoader(BOOK_LOADER_ID, null,this);
        }
    }

    public void connectionChecker(){

        // network connectivity manager for checking status


        if (isConnected==false){
            emptyView.setText(R.string.no_internet);
        } else if (tempBooks==null){
            emptyView.setText(R.string.no_books);
        } else {
            emptyView.setText("");
        }
    }

    @Override
    protected void  onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("books", tempBooks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.getParcelableArrayList("bookList")!=null){
            tempBooks = savedInstanceState.getParcelableArrayList("bookList");
        }

        if (tempBooks != null) {
            bookListView.setAdapter(bookAdapter);
        }
        searchText.setCursorVisible(false);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isPrefChanged && keyWord!=null){
            loaderStart();
            isPrefChanged=false;
        }
    }
}




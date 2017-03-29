package xuxu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rv_contacts);
        final List<Contact> allContacts = Contact.createContactsList(10, 0);
        final ContactAdapter.ContactsAdapter adapter = new ContactAdapter.ContactsAdapter(allContacts);
        rvItems.setAdapter(adapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvItems.setLayoutManager(gridLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                List<Contact> moreContacts = Contact.createContactsList(10, page);
                final int curSize = adapter.getItemCount();

                allContacts.addAll(moreContacts);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(curSize, allContacts.size() - 1);
                        //adapter.notifyDataSetChanged();
                    }
                });
                //Toast.makeText(view.getContext(), "Loaded more", Toast.LENGTH_SHORT).show();
            }
        };
        rvItems.addOnScrollListener(scrollListener);


    }

}

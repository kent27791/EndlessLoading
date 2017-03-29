package xuxu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rv_users);
        //final List<String> allUsers = createUsersList(10, 0);
        final List<String> allUsers = new ArrayList<>();
        final UserAdapter adapter = new UserAdapter(allUsers);
        adapter.setData(createUsersList(10, 0));
        rvItems.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                List<String> moreUsers = createUsersList(10, page);
                adapter.setData(moreUsers);
                /*final int curSize = adapter.getItemCount();
                allUsers.addAll(moreUsers);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(curSize, allUsers.size() - 1);
                    }
                });*/
            }
        };
        rvItems.addOnScrollListener(scrollListener);
    }
    public static int lastUserId = 0;
    public List<String> createUsersList(int numUsers, int offset) {
        List<String> users = new ArrayList<String>();

        for (int i = 1; i <= numUsers; i++) {
            users.add("User " + ++lastUserId);
        }

        return users;
    }
}

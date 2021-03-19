package com.example.foodbank2021;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    /*private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> amountList = new ArrayList<>();
    private ArrayList<String> locationList = new ArrayList<>();

    private ListView listview;
    PopupWindow popUp;
    boolean click = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot fridgesn = snapshot.child("Fridge");

                for (DataSnapshot dataSnapshot : snapshot.child("Food").getChildren()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String amount = dataSnapshot.child("amount").getValue().toString();
                    String fridge = dataSnapshot.child("fridge").getValue().toString();
                    String location = fridgesn.child(fridge).child("location").getValue().toString();
                    nameList.add(name);
                    amountList.add(amount);
                    locationList.add(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // do nothing
            }
        });

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listview = root.findViewById(R.id.foodlist);
        popUp = new PopupWindow(getContext());

        LinearLayout layout = new LinearLayout(getContext());
        TextView tv = new TextView(getContext());

        showList();

        FloatingActionButton donate = root.findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);

        tv.setText("Please Donate Your Food.");

        layout.addView(tv, params);
        popUp.setContentView(layout);

        return root;
    }

    public void showList() {
        ArrayAdapter adapter1 = new ArrayAdapter<>(getContext(),
                R.layout.activity_listview, R.id.name_textView, nameList);
        ArrayAdapter adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.activity_listview, R.id.amount_textView, amountList);
        ArrayAdapter adapter3 = new ArrayAdapter<>(getContext(),
                R.layout.activity_listview, R.id.location_textView, locationList);

        listview.setAdapter(adapter1);
        listview.setAdapter(adapter2);
        listview.setAdapter(adapter3);
    }*/
}
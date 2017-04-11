package com.cryptochat.theguys.cryptochat.Controller.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cryptochat.theguys.cryptochat.Controller.ChatActivity;
import com.cryptochat.theguys.cryptochat.Controller.MainActivity;
import com.cryptochat.theguys.cryptochat.Model.ContactsModel;
import com.cryptochat.theguys.cryptochat.R;
import com.cryptochat.theguys.cryptochat.Utils.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static ContactsModel contactsModel;
    private ContactAdapter adapter;
    private MainActivity mainActivity;

    private OnFragmentInteractionListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Contacts main view
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView_contacts);
        mainActivity = (MainActivity) getActivity();

        contactsModel = new ContactsModel();
        contactsModel.getContacts();

        adapter = new ContactAdapter(mainActivity.getApplicationContext(),contactsModel.getContactsModels());
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) mainActivity.findViewById(R.id.fab);


        //Listens for click on contact in the contact menu
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                handleClick(view);
            }
        });

        SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_contacts);

        //Listening for pull down refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                contactsModel.getContacts();
            }
        });

        contactsModel.setVariables(adapter,mainActivity.getBaseContext(),swipeRefresh);

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onContactsFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Handles contacts click
    public void handleClick(View view){
        TextView contact = (TextView) view.findViewById(R.id.text_ContactName);
        TextView status = (TextView) view.findViewById(R.id.text_ContactOnlineStatus);

        String contactName = (String) contact.getText();
        String contactStatus = (String) status.getText();

        //If the user is online create a chat with the user
        if(contactStatus.equals("Online")){
            Utils.CURRENT_CHAT = contactName;

            //If successful go to MainActivity
            Intent intent = new Intent(mainActivity, ChatActivity.class);
            mainActivity.startActivity(intent);
        } else
            Toast.makeText(mainActivity.getBaseContext(),R.string.error_contact_offline,Toast.LENGTH_SHORT).show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onContactsFragmentInteraction(Uri uri);
    }

    //TODO besides fuck android check for update ever so often for contacts list
    public class ContactAdapter extends BaseAdapter{

        public List<ContactsModel> contactsModelList;
        private Context mContext;

        public ContactAdapter(Context context,List<ContactsModel> objects) {
            contactsModelList = objects;
            mContext = context;
        }

        @Override
        public int getCount() {
            return contactsModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return contactsModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        //Clears out current list content
        public  void clear(){
            contactsModelList.clear();
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View rowView = View.inflate(mContext, R.layout.row_contact, null);

            TextView contactName = (TextView)rowView.findViewById(R.id.text_ContactName);
            TextView contactOnlineStatus = (TextView)rowView.findViewById(R.id.text_ContactOnlineStatus);
            ImageView contactAvatar = (ImageView)rowView.findViewById(R.id.image_ContactAvatar);

            //TODO fix deprecated stuff
            contactAvatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
            contactName.setText(contactsModelList.get(position).getUsername());

            //Change color for online status
            if(contactsModelList.get(position).getOnlineStatus().equals("Online")){
                contactOnlineStatus.setTextColor(getResources().getColor(R.color.online));
            } else {
                contactOnlineStatus.setTextColor(getResources().getColor(R.color.offline));
            }
            contactOnlineStatus.setText(contactsModelList.get(position).getOnlineStatus());
            //TODO setup action listner to open up new chat with user or presexisting
            return rowView;
        }

    }
}

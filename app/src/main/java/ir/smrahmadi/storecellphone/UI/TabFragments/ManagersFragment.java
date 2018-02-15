package ir.smrahmadi.storecellphone.UI.TabFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.smrahmadi.storecellphone.R;

import static ir.smrahmadi.storecellphone.app.DB;

/**
 * Created by cloner on 12/20/17.
 */

public class ManagersFragment extends Fragment {

    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> namearray= new ArrayList<String>();

    ListView Managers_lst;
    Button Managers_add ;

    ManagersAdapter adapter ;

    ArrayList<String> Manager = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.managers, container, false);
        Managers_lst = (ListView) view.findViewById(R.id.Managers_lst);
        Managers_add = (Button) view.findViewById(R.id.Managers_add);





        id.clear();
        namearray.clear();
        getData();


        if(id!=null) {


            adapter = new ManagersAdapter(getContext(), id,namearray);
            Managers_lst.setAdapter(adapter);
        }
        else {
            Toast.makeText(getContext(), "There are no Managers", Toast.LENGTH_SHORT).show();
        }



        Managers_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.manager_add_dialog);
                // Set dialog title
                dialog.setTitle("Add Manager");

                final EditText Manager_add_dialog_EdtName = (EditText) dialog.findViewById(R.id.Manager_add_dialog_EdtName);
                final EditText Manager_add_dialog_EdtUsername = (EditText) dialog.findViewById(R.id.Manager_add_dialog_EdtUsername);
                final EditText Manager_add_dialog_EdtPassword = (EditText) dialog.findViewById(R.id.Manager_add_dialog_EdtPassword);
                Button Manager_add_dialog_BtnAdd = (Button) dialog.findViewById(R.id.Manager_add_dialog_BtnAdd);


                Manager_add_dialog_BtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(TextUtils.isEmpty(Manager_add_dialog_EdtName.getText().toString())){
                            Manager_add_dialog_EdtName.setError("is Empty !!!");
                            return;
                        }
                        if(TextUtils.isEmpty(Manager_add_dialog_EdtUsername.getText().toString())){
                            Manager_add_dialog_EdtUsername.setError("is Empty !!!");
                            return;
                        }
                        if(TextUtils.isEmpty(Manager_add_dialog_EdtPassword.getText().toString())){
                            Manager_add_dialog_EdtPassword.setError("is Empty !!!");
                            return;
                        }

                        String name ="'"+Manager_add_dialog_EdtName.getText().toString()+"',"  ;
                        String username="'"+Manager_add_dialog_EdtUsername.getText().toString()+"'," ;
                        String password = "'"+Manager_add_dialog_EdtPassword.getText().toString()+"'";

                        String Query = "INSERT INTO Managers (Name,UserName,Password) VALUES ("+name+username+password+") ";
                        Cursor cursor = DB.rawQuery(Query, null);
                        cursor.moveToFirst();


                        id.clear();
                        namearray.clear();
                        getData();


                        if(id!=null) {


                            adapter = new ManagersAdapter(getContext(), id,namearray);
                            Managers_lst.setAdapter(adapter);
                        }
                        else {
                            Toast.makeText(getContext(), "There are no Managers", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();


                    }
                });

                dialog.show();
            }
        });


        return view;
    }

    public void getData(){
        String SelectQuery = "SELECT * FROM Managers";
        Cursor cursor = DB.rawQuery(SelectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {


            if (cursor.moveToFirst()) {

                do {


                    id .add(cursor.getInt(cursor.getColumnIndex("id")));
                    namearray.add(cursor.getString(cursor.getColumnIndex("Name")));



                } while (cursor.moveToNext());
            }

            cursor.close();
        }

    }



    public class ManagersAdapter extends ArrayAdapter {

        Context context;

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();

        public ManagersAdapter(Context context,  ArrayList<Integer> id  ,  ArrayList<String> name ) {
            super(context,R.layout.manager_lst,id);
            // TODO Auto-generated constructor stub
            this.context = context;
            this.ids = id;
            this.names = name;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.manager_lst, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.manager_lst_txt);
            ImageView img = (ImageView) convertView.findViewById(R.id.manager_lst_img);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked



                                    String SelectQuery = "DELETE FROM Managers WHERE id = "+ids.get(position);
                                    Cursor cursor = DB.rawQuery(SelectQuery, null);
                                    cursor.moveToFirst();




                                    id.clear();
                                    namearray.clear();

                                    Managers_lst.setAdapter(adapter);
                                    getData();

                                    if(id!=null) {


                                        adapter = new ManagersAdapter(getContext(), id,names);
                                        Managers_lst.setAdapter(adapter);
                                    }
                                    else {
                                        Toast.makeText(getContext(), "There are no Managers", Toast.LENGTH_SHORT).show();
                                    }





                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }



            });
            name.setText(names.get(position));
            return convertView;
        }

    }
}

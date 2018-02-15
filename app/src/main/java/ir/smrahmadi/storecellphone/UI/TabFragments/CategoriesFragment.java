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

public class CategoriesFragment extends Fragment {

    ListView Categories_lst ;
    CategoriesAdapter adapter ;

    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> namearray= new ArrayList<String>();
    Button Categories_Add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.categories, container, false);
        Categories_lst = (ListView) view.findViewById(R.id.Categories_lst);
        Categories_Add = (Button) view.findViewById(R.id.Categories_Add);


        id.clear();
        namearray.clear();
        getData();



        if(id!=null) {

            adapter = new CategoriesAdapter(getContext(), id,namearray);
            Categories_lst.setAdapter(adapter);
        }
        else
            Toast.makeText(getContext(), "There are no Categories", Toast.LENGTH_SHORT).show();

        Categories_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.categorie_add_dialog);
                // Set dialog title

                final EditText Categorie_add_dialog_EdtName = (EditText) dialog.findViewById(R.id.Categorie_add_dialog_EdtName);
                Button Categorie_add_dialog_BtnAdd = (Button) dialog.findViewById(R.id.Categorie_add_dialog_BtnAdd);


                Categorie_add_dialog_BtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (TextUtils.isEmpty(Categorie_add_dialog_EdtName.getText().toString())) {
                            Categorie_add_dialog_EdtName.setError("is Empty !!!");
                            return;
                        }


                        String name = "'" + Categorie_add_dialog_EdtName.getText().toString()+"'";


                        String Query = "INSERT INTO Categories (Name) VALUES (" + name +") ";
                        Cursor cursor = DB.rawQuery(Query, null);
                        cursor.moveToFirst();

                        id.clear();
                        namearray.clear();
                        getData();



                        if(id!=null) {

                            adapter = new CategoriesAdapter(getContext(), id,namearray);
                            Categories_lst.setAdapter(adapter);
                        }
                        else
                            Toast.makeText(getContext(), "There are no Categories", Toast.LENGTH_SHORT).show();


                        dialog.dismiss();

                    }
                });


                dialog.show();



            }
        });








        return view;
    }


    public void getData(){
        String SelectQuery = "SELECT * FROM Categories";
        Cursor cursor = DB.rawQuery(SelectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {



            if (cursor.moveToFirst()) {

                do {



                    id .add(cursor.getInt(cursor.getColumnIndex("Id")));
                    namearray.add(cursor.getString(cursor.getColumnIndex("Name")));


                } while (cursor.moveToNext());
            }

            cursor.close();
        }

    }



    public class CategoriesAdapter extends ArrayAdapter {


        Context context;

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();

        public CategoriesAdapter(Context context,  ArrayList<Integer> id  ,  ArrayList<String> name ) {
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



                                    String SelectQuery = "DELETE FROM Categories WHERE id = "+ids.get(position);
                                    Cursor cursor = DB.rawQuery(SelectQuery, null);
                                    cursor.moveToFirst();




                                    id.clear();
                                    namearray.clear();

                                    Categories_lst.setAdapter(adapter);
                                    getData();

                                    if(id!=null) {


                                        adapter = new CategoriesAdapter(getContext(), id,names);
                                        Categories_lst.setAdapter(adapter);
                                    }
                                    else {
                                        Toast.makeText(getContext(), "There are no Categories", Toast.LENGTH_SHORT).show();
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

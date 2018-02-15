package ir.smrahmadi.storecellphone.UI.TabFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.smrahmadi.storecellphone.Models.StoreModel;
import ir.smrahmadi.storecellphone.R;

import static ir.smrahmadi.storecellphone.app.DB;

/**
 * Created by cloner on 12/20/17.
 */

public class StoreFragment extends Fragment {

    ListView Store_lst ;

    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> namearray= new ArrayList<String>();
    ArrayList<Integer> number = new ArrayList<Integer>();

    Button Store_Add ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.store, container, false);
        Store_lst = (ListView) view.findViewById(R.id.Store_lst);
        Store_Add = (Button) view.findViewById(R.id.Store_Add);




        id.clear();
        namearray.clear();
        number.clear();

        getData();

        if(id!=null) {
            StoreAdapter adapter = new StoreAdapter(getContext(), id, namearray, number);
            Store_lst.setAdapter(adapter);
        }else
            Toast.makeText(getContext(), "There are no Device", Toast.LENGTH_SHORT).show();


        Store_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.store_add_dialog);
                // Set dialog title




                final Spinner Store_add_dialog_SpnCategory = (Spinner) dialog.findViewById(R.id.Store_add_dialog_SpnCategory);
                final EditText Store_add_dialog_EdtName = (EditText) dialog.findViewById(R.id.Store_add_dialog_EdtName);
                final EditText Store_add_dialog_EdtNumber = (EditText) dialog.findViewById(R.id.Store_add_dialog_EdtNumber);
                Button Store_add_dialog_BtnAdd = (Button) dialog.findViewById(R.id.Store_add_dialog_BtnAdd);


                final ArrayList<Integer> idspn = new ArrayList<Integer>();
                ArrayList<String> catrgoriespn = new ArrayList<String>();

                String SelectQuery = "SELECT * FROM Categories";
                Cursor cursor = DB.rawQuery(SelectQuery, null);

                if (cursor != null && cursor.getCount() > 0) {




                    if (cursor.moveToFirst()) {

                        do {

                            idspn.add(cursor.getInt(cursor.getColumnIndex("Id")));
                            catrgoriespn.add(cursor.getString(cursor.getColumnIndex("Name")));


                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }



                ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,catrgoriespn);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                Store_add_dialog_SpnCategory.setAdapter(aa);



                Store_add_dialog_BtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(Store_add_dialog_EdtName.getText().toString())) {
                            Store_add_dialog_EdtName.setError("is Empty !!!");
                            return;
                        }

                        if (TextUtils.isEmpty(Store_add_dialog_EdtNumber.getText().toString())) {
                            Store_add_dialog_EdtNumber.setError("is Empty !!!");
                            return;
                        }


                        String catid = idspn.get(Integer.parseInt(String.valueOf(Store_add_dialog_SpnCategory.getSelectedItemId())))+",";
                        String name = "'"+Store_add_dialog_EdtName.getText().toString()+"',";
                        String numberm = Store_add_dialog_EdtNumber.getText().toString();

                        String Query = "INSERT INTO Store (CategoryId,Name,Number) VALUES ("+ catid + name + numberm +") ";
                        Cursor cursor = DB.rawQuery(Query, null);
                        cursor.moveToFirst();


                        id.clear();
                        namearray.clear();
                        number.clear();

                        getData();

                        if(id!=null) {
                            StoreAdapter adapter = new StoreAdapter(getContext(), id, namearray, number);
                            Store_lst.setAdapter(adapter);
                        }else
                            Toast.makeText(getContext(), "There are no Device", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    }
                });


                dialog.show();


            }
        });



        return view ;
    }

    public String getCategory(int id){

        String SelectQuery = "SELECT * FROM Categories WHERE Id="+id;
        Cursor cursor = DB.rawQuery(SelectQuery, null);

        String category = null;
        if (cursor.moveToFirst()) {

            do {


            category =  cursor.getString(cursor.getColumnIndex("Name"));


            } while (cursor.moveToNext());
        }

        return category;
    }


    public void getData(){
        String SelectQuery = "SELECT * FROM Store";
        Cursor cursor = DB.rawQuery(SelectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {



            if (cursor.moveToFirst()) {

                do {

                    String category = getCategory(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                    id.add(cursor.getInt(cursor.getColumnIndex("Id")));
                    namearray.add(category+" "+cursor.getString(cursor.getColumnIndex("Name")));
                    number.add(cursor.getInt(cursor.getColumnIndex("Number")));




                } while (cursor.moveToNext());
            }

            cursor.close();
        }

    }


    public class StoreAdapter extends ArrayAdapter {

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<String> names= new ArrayList<String>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        Context context;

        public StoreAdapter(Context context,ArrayList<Integer> id,ArrayList<String> name,ArrayList<Integer> number) {
            super(context,R.layout.store_lst,id);
            // TODO Auto-generated constructor stub
            this.context = context;
            this.ids = id;
            this.names = name;
            this.numbers = number;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.store_lst, parent, false);

            TextView product = (TextView) convertView.findViewById(R.id.Store_lst_product);
            TextView number = (TextView) convertView.findViewById(R.id.Store_lst_number);
            CheckBox chk = (CheckBox) convertView.findViewById(R.id.Store_lst_chk);


            product.setText(names.get(position));
            number.setText(numbers.get(position)+"");

            return convertView;
        }

    }
}

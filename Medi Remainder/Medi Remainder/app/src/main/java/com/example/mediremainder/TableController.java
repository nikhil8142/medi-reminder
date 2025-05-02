package com.example.mediremainder;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TableController extends DatabaseHandler
{
    public TableController(Context context)
    {
        super(context);
    }

    public boolean create(Medicine medicine)
    {

        ContentValues values = new ContentValues();

        values.put("medname", medicine.name);
        values.put("dosage", medicine.dosage);
        values.put("time", String.valueOf(medicine.time));
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("mediremainder", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count()
    {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM students";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<Medicine> read()
    {

        List<Medicine> recordsList = new ArrayList<Medicine>();

        String sql = "SELECT * FROM mediremainder ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do
            {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String name = cursor.getString(cursor.getColumnIndex("medname"));
                int dosage= Integer.parseInt(cursor.getString(cursor.getColumnIndex("dosage")));
                String time=cursor.getString(cursor.getColumnIndex("time"));


                Medicine med = new Medicine();
                med.id=id;
                med.dosage=dosage;
                med.name=name;
                med.time= time;
                recordsList.add(med);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

   /* public ObjectStudent readSingleRecord(int studentId)
    {

        ObjectStudent objectStudent = null;

        String sql = "SELECT * FROM students WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst())
        {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            objectStudent = new ObjectStudent();
            objectStudent.id = id;
            objectStudent.firstname = firstname;
            objectStudent.email = email;

        }

        cursor.close();
        db.close();

        return objectStudent;
    }

    public boolean update(ObjectStudent objectStudent)
    {

        ContentValues values = new ContentValues();

        values.put("firstname", objectStudent.firstname);
        values.put("email", objectStudent.email);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectStudent.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("students", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id)
    {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("students", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }*/

}



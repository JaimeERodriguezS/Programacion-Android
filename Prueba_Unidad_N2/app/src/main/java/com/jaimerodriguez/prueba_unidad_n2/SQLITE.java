package com.jaimerodriguez.prueba_unidad_n2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLITE extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_SENSORES = "CREATE TABLE Sensores(" +
            "id_sensor INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nom_sensor text unique not null," +
            "fecha_sensor text not null,"+
            "campo_observacion varchar not null,"+
            "valor_sensor text not null)";

    public SQLITE(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(CREATE_TABLE_SENSORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS Sensores");
        onCreate(DB);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}

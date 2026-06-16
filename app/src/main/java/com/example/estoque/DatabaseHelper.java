package com.example.estoque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "estoque.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COL_ID       = "id";
    public static final String COL_NOME     = "nome";
    public static final String COL_SENHA    = "senha";
    public static final String COL_PERFIL   = "perfil";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsuarios =
                "CREATE TABLE " + TABLE_USUARIOS + " (" +
                        COL_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NOME    + " TEXT NOT NULL UNIQUE, " +
                        COL_SENHA   + " TEXT NOT NULL, " +
                        COL_PERFIL  + " TEXT NOT NULL" +
                        ")";
        db.execSQL(createUsuarios);

        inserirUsuario(db, "Pedro",    "1234", "gerente");
        inserirUsuario(db, "Maria", "5678", "estoquista");
        inserirUsuario(db, "Jéssina",   "4321", "vendedor");
        inserirUsuario(db, "Henrique",  "8765", "embalador");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    private void inserirUsuario(SQLiteDatabase db, String nome, String senha, String perfil) {
        ContentValues values = new ContentValues();
        values.put(COL_NOME,   nome);
        values.put(COL_SENHA,  senha);
        values.put(COL_PERFIL, perfil);
        db.insert(TABLE_USUARIOS, null, values);
    }

    public String autenticar(String nome, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] colunas  = { COL_PERFIL };
        String   selecao  = COL_NOME + " = ? AND " + COL_SENHA + " = ?";
        String[] args     = { nome, senha };

        Cursor cursor = db.query(TABLE_USUARIOS, colunas, selecao, args, null, null, null);

        String perfil = null;
        if (cursor.moveToFirst()) {
            perfil = cursor.getString(cursor.getColumnIndexOrThrow(COL_PERFIL));
        }

        cursor.close();
        db.close();
        return perfil;
    }
}
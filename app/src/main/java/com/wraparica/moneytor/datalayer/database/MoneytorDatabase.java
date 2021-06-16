package com.wraparica.moneytor.datalayer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wraparica.moneytor.datalayer.dao.AccountsDao;
import com.wraparica.moneytor.datalayer.dao.ExpensesDao;
import com.wraparica.moneytor.datalayer.dao.IncomeDao;
import com.wraparica.moneytor.datalayer.entity.Accounts;
import com.wraparica.moneytor.datalayer.entity.Expenses;
import com.wraparica.moneytor.datalayer.entity.Income;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import static com.wraparica.moneytor.datalayer.database.MoneytorMigration.DATABASE_VERSION;

@Database(entities = {
        Accounts.class,
        Expenses.class,
        Income.class

}, version = DATABASE_VERSION, exportSchema = true)
public abstract class MoneytorDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "db-moneytor.db";
    private static final char[] DATABASE_PASSWORD = "password".toCharArray();

    private static MoneytorDatabase instance;

    public static MoneytorDatabase get(Context context) {
        if (instance == null) {
            instance = getNewInstance(context);
        }
        return instance;
    }

    private static MoneytorDatabase getNewInstance(Context context) {
//        SafeHelperFactory factory = new SafeHelperFactory(DATABASE_PASSWORD, SafeHelperFactory.POST_KEY_SQL_MIGRATE);
        SupportFactory factory = new SupportFactory(SQLiteDatabase.getBytes(DATABASE_PASSWORD));

        return Room.databaseBuilder(context, MoneytorDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MoneytorMigration.MIGRATIONS)
                .openHelperFactory(factory)
                .build();
    }

    public static void destroy() {
        if (instance != null && instance.isOpen()) {
            instance.close();
        }
        instance = null;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static char[] getDatabasePassword() {
        return DATABASE_PASSWORD;
    }

    public abstract AccountsDao accountsDao();

    public abstract ExpensesDao expensesDao();

    public abstract IncomeDao incomeDao();


}
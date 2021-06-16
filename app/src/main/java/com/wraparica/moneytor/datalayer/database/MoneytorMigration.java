package com.wraparica.moneytor.datalayer.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@SuppressWarnings("WeakerAccess")
public class MoneytorMigration {

    public static final int DATABASE_VERSION = 1;

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration[] MIGRATIONS = {
            MIGRATION_1_2
    };
}

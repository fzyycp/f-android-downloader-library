package cn.faury.android.library.downloader.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

public abstract class BaseContentDbHelper extends SQLiteOpenHelper {

    /**
     * map of dao
     */
    private Map<String, ContentDbDao> mContentDbDaoMap = new HashMap<>();

    public BaseContentDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.initContentDbDaoMap();
    }

    /**
     * init dao map
     */
    private void initContentDbDaoMap() {

        List<ContentDbDao> contentDbDaos = new ArrayList<>();

        // config daos
        onConfigContentDbDaos(contentDbDaos);

        if (contentDbDaos==null || contentDbDaos.isEmpty()) {
            return;
        }

        for (ContentDbDao contentDbDao : contentDbDaos) {
            if (contentDbDao == null) {
                continue;
            }
            String tableName = contentDbDao.getTableName();

            if (TextUtils.isEmpty(tableName)) {
                continue;
            }

            if (mContentDbDaoMap.containsKey(tableName)) {
                continue;
            }

            // put dao to map
            mContentDbDaoMap.put(tableName, contentDbDao);
        }
    }

    /**
     * config daos
     *
     * @param contentDbDaos current daos
     */
    protected abstract void onConfigContentDbDaos(List<ContentDbDao> contentDbDaos);

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        Collection<ContentDbDao> contentDbDaos = mContentDbDaoMap.values();

        if (contentDbDaos==null || contentDbDaos.isEmpty()) {
            return;
        }

        for (ContentDbDao contentDbDao : contentDbDaos) {
            if (contentDbDao == null) {
                continue;
            }
            // call dao's onCreate
            contentDbDao.onCreate(db);
        }

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Collection<ContentDbDao> contentDbDaos = mContentDbDaoMap.values();

        if (contentDbDaos==null || contentDbDaos.isEmpty()) {
            return;
        }

        for (ContentDbDao contentDbDao : contentDbDaos) {
            if (contentDbDao == null) {
                continue;
            }
            // call dao's onUpgrade
            contentDbDao.onUpgrade(db, oldVersion, newVersion);
        }
    }


    /**
     * get dao by table name
     *
     * @param tableName table name
     * @return the dao for the given table name
     */
    public ContentDbDao getContentDbDao(String tableName) {
        if (!mContentDbDaoMap.containsKey(tableName)) {
            throw new RuntimeException("unregistered database table:" + tableName + " in " + this.getClass()
                    .getSimpleName());
        }
        return mContentDbDaoMap.get(tableName);
    }
}

package com.rt.sc.tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by renmingming on 15/9/21.
 */
public class DirUtils
{

    private static final String TAG = "DirUtils";
    private static final boolean DEBUG = false;

    private DirUtils() {}

    /**
     * 获取一个缓存目录
     * 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径< /sdcard/Android/data/<application package>/cache >
     * 否则，调用getCacheDir()方法来获取缓存路径< /data/data/<application package>/cache>
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        assertContext(context);
        final String cachePath = (isUserExternal() || !isExternalStorageRemovable()) ? getExternalCacheDir(
                context).getPath() : context.getCacheDir().getPath();
        final File dir = new File(cachePath + File.separator + uniqueName);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            } else {
                return dir;
            }
        } else {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getDiskCacheDir(Context context) {
        assertContext(context);
        String cachePath = (isUserExternal() || !isExternalStorageRemovable()) ? getExternalCacheDir(
                context).getPath() : context.getCacheDir().getPath();
        if(StringUtils.isEmpty(cachePath)){
            cachePath = DeviceUtil.getSDCardPath();
        }
        File dir = new File(cachePath);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            } else {
                return dir;
            }
        } else {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取一个缓存目录
     * 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalFileDir()方法来获取缓存路径< /sdcard/Android/data/<application package>/file >
     * 否则，调用getFileDir()方法来获取缓存路径< /data/data/<application package>/file>
     */
    public static File getDiskFilesDir(Context context, String uniqueName) {
        assertContext(context);
        final String cachePath = (isUserExternal() || !isExternalStorageRemovable()) ? getExternalFilesDir(
                context).getPath() : context.getFilesDir().getPath();

        final File dir = new File(cachePath + File.separator + uniqueName);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            } else {
                return dir;
            }
        } else {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getDiskFilesDir(Context context) {
        assertContext(context);
        final String cachePath = (isUserExternal() || !isExternalStorageRemovable()) ? getExternalFilesDir(
                context).getPath() : context.getFilesDir().getPath();
        File dir = new File(cachePath);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            } else {
                return dir;
            }
        } else {
            dir.mkdirs();
        }
        return dir;
    }

    private static void assertContext(Context context) {
        if (context == null) {
            throw new NullPointerException("context can't be null");
        }

    }

    /**
     * 判断外置SD卡是否存在
     * @return
     */
    private static boolean isUserExternal() {
        String externalStorageState = Environment.getExternalStorageState();
        boolean isUserExternal = Environment.MEDIA_MOUNTED.equals(externalStorageState);
        return isUserExternal;
    }

    /**
     * 判断外置SD卡是否可以被移除
     * @return
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (CompatUtils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    private static File getExternalCacheDir(Context context) {
        if (CompatUtils.hasFroyo()) {
            File dir = context.getExternalCacheDir();
            if (dir != null) {
                return dir;
            }
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    private static File getExternalFilesDir(Context context) {
        if (CompatUtils.hasFroyo()) {
            File dir = context.getExternalFilesDir(null);
            if (dir != null) {
                return dir;
            }
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/files/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 获取文件可用空间
     */
    @TargetApi(9)
    public static long getUsableSpace(File path) {
        if (CompatUtils.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    public static File getExternalStorage(Context context, String uniqueName) {
        File file;
        if (isUserExternal()) {
            String path = "/sdcard/" + context.getPackageName();
            file = new File(path, uniqueName);
            if (file.exists()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            } else {
                file.mkdirs();
            }
        } else {
            file = getDiskFilesDir(context, uniqueName);
        }
        return file;
    }
}

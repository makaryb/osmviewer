package com.makaryb.adplaceservice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStorageState;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.getDefaultUncaughtExceptionHandler;

class ExceptionHandler implements UncaughtExceptionHandler {
    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());
    private final DateFormat fileFormatter = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
    private String versionName = "0";
    private int versionCode = 0;
    private final String stacktraceDir;
    private final UncaughtExceptionHandler previousHandler;

    private ExceptionHandler(@NotNull Context context, boolean chained) {
        PackageManager mPackManager = context.getPackageManager();
        PackageInfo mPackInfo;
        try {
            mPackInfo = mPackManager.getPackageInfo(context.getPackageName(), 0);
            versionName = mPackInfo.versionName;
            versionCode = mPackInfo.versionCode;
        } catch (NameNotFoundException e) {
            // ignore
        }
        if (chained)
            previousHandler = getDefaultUncaughtExceptionHandler();
        else
            previousHandler = null;
        stacktraceDir = String.format("/Android/data/%s/files/", context.getPackageName());
    }

    @NonNull
    static ExceptionHandler inContext(Context context) {
        return new ExceptionHandler(context, true);
    }

    @NonNull
    static ExceptionHandler reportOnlyHandler(Context context) {
        return new ExceptionHandler(context, false);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        final String state = getExternalStorageState();
        final Date dumpDate = new Date(currentTimeMillis());
        if (MEDIA_MOUNTED.equals(state)) {
            makeReport(thread, exception, dumpDate);
        }
        if (previousHandler != null)
            previousHandler.uncaughtException(thread, exception);
    }

    private void makeReport(@NotNull Thread thread, Throwable exception, Date dumpDate) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("\n\n\n")
                .append(formatter.format(dumpDate))
                .append("\n")
                .append(String.format(Locale.getDefault(), "Version: %s (%d)\n", versionName, versionCode))
                .append(thread.toString())
                .append("\n");
        processThrowable(exception, reportBuilder);
        tryToWriteStacktrace(dumpDate, reportBuilder);
    }

    private void tryToWriteStacktrace(Date dumpDate, StringBuilder reportBuilder) {
        File sd = getExternalStorageDirectory();
        File stacktrace = new File(
                sd.getPath() + stacktraceDir,
                String.format(
                        "stacktrace-%s.txt",
                        fileFormatter.format(dumpDate)));
        File dumpDir = stacktrace.getParentFile();
        boolean dirReady = dumpDir.isDirectory() || dumpDir.mkdirs();
        if (dirReady) {
            writeStacktrace(reportBuilder, stacktrace);
        }
    }

    private void writeStacktrace(@NotNull StringBuilder reportBuilder, File stacktrace) {
        try (FileWriter writer = new FileWriter(stacktrace, true)) {
            writer.write(reportBuilder.toString());
        } catch (IOException e) {
            // ignore
        }
        // ignore
    }

    private void processThrowable(Throwable exception, StringBuilder builder) {
        if (exception == null)
            return;
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        builder.append("Exception: ")
                .append(exception.getClass().getName())
                .append("\n")
                .append("Message: ")
                .append(exception.getMessage())
                .append("\nStacktrace:\n");
        for (StackTraceElement element : stackTraceElements) {
            builder.append("\t")
                    .append(element.toString())
                    .append("\n");
        }
        processThrowable(exception.getCause(), builder);
    }
}
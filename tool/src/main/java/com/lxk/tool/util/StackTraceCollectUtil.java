package com.lxk.tool.util;


/**
 * 异常堆栈详细信息收集
 */
public final class StackTraceCollectUtil {


    private static final String CAUSED_BY = "\r\nCaused by:";
    private static final String SUPPRESSED = "\r\n\tSuppressed:";

    public static String collectStackTrace(final Throwable t) {

        return collectStackTrace(t, -1);
    }

    public static String collectStackTrace(final Throwable t, final int collectLargestLines) {

        //默认初始为大小 7000
        final StringBuilder sb = new StringBuilder(7000);
        collectStackTrace(sb, t, collectLargestLines);

        return sb.toString();
    }

    private static String collectStackTrace(final StringBuilder sb, final Throwable t) {

        return collectStackTrace(sb, t, -1);
    }

    private static String collectStackTrace(final StringBuilder sb, final Throwable t, final int collectLargestLines) {

        getStackTrace(sb, t, collectLargestLines);

        final Throwable[] suppressed = t.getSuppressed();
        if (suppressed != null) {
            for (Throwable t1 : suppressed) {
                sb.append(SUPPRESSED);
                collectStackTrace(sb, t1);
            }
        }

        final Throwable cause = t.getCause();
        if (cause != null) {
            sb.append(CAUSED_BY);
            collectStackTrace(sb, cause);
        }

        return sb.toString();
    }

    private static StringBuilder getStackTrace(final StringBuilder sb, final Throwable t, int collectLargestLines) {

        final String name = t.getClass().getName();
        final String message = t.getMessage();
        sb.append(
                (t instanceof NullPointerException) ?
                        (message == null ? name : name + ":" + message) :
                        name + ":" + message
        );


        final StackTraceElement[] stackElements = t.getStackTrace();
        if (stackElements != null) {
            collectLargestLines = collectLargestLines < 0 ? stackElements.length : collectLargestLines;
            for (int i = 0; i < collectLargestLines; i++) {
                StackTraceElement ste = stackElements[i];
                sb
                        .append("\n\tat ")
                        .append(ste.getClassName())
                        .append(".")
                        .append(ste.getMethodName())
                        .append("(")
                        .append(ste.getLineNumber())
                        .append(")");
            }
        }

        return sb;
    }

}

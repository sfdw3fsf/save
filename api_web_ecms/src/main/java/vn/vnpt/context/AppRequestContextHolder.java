//package vn.vnpt.context;
//
//public class AppRequestContextHolder {
//
//    private static final ThreadLocal<AppRequestContext> contextHolder = new ThreadLocal<>();
//
//    public static void clearContext() {
//        contextHolder.remove();
//    }
//
//    public static AppRequestContext getContext() {
//
//        AppRequestContext ctx = contextHolder.get();
//
//        if (ctx == null) {
//            ctx = createEmptyContext();
//            contextHolder.set(ctx);
//        }
//
//        return ctx;
//    }
//
//    public static void setContext(AppRequestContext context) {
//        contextHolder.set(context);
//    }
//
//    public static AppRequestContext createEmptyContext() {
//        return new AppRequestContext();
//    }
//
//}

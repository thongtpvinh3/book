package com.thong.book.common;

import java.util.ArrayList;

public final class AntMatchersList {

    public static String[] getAntMatchersList() {
        return new ArrayList<>() {{
            add("/api/auth/*");
        }}.toArray(new String[0]);
    }

    public static String[] getAntMatchersAdminList() {
        return new String[]{
                "/api/book/delete/*"
        };
    }
}

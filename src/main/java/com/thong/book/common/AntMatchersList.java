package com.thong.book.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

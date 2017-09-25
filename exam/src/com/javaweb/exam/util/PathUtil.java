package com.javaweb.exam.util;

import com.javaweb.exam.AppConstants;
import com.javaweb.exam.AppContext;

public class PathUtil {

    public static String getFullPath(String path) {
        if (path == null) {
            path = "";
        }

        String urlPrefix = AppConstants.APP_URL_PREFIX;
        if (!StringUtil.isEmpty(urlPrefix)) {
            urlPrefix += "/";
        }

        return AppContext.getContextPath() + "/" + urlPrefix + path;
    }

}

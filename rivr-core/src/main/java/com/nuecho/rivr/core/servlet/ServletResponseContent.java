/*
 * Copyright (c) 2012 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.core.servlet;

import java.io.*;

/**
 * @author Nu Echo Inc.
 */
public interface ServletResponseContent {
    String getContentType();

    void writeTo(OutputStream outputStream) throws IOException;
}
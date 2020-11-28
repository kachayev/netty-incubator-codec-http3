/*
 * Copyright 2020 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.incubator.codec.http3;

import java.util.Arrays;
import java.util.List;

import io.netty.handler.codec.UnsupportedValueConverter;
import io.netty.handler.codec.http2.CharSequenceMap;
import io.netty.util.AsciiString;

final class QpackStaticTable {

    /**
     * Appendix A: Static Table
     * https://tools.ietf.org/html/draft-ietf-quic-qpack-19#appendix-A
     */
    private static final List<QpackHeaderField> STATIC_TABLE = Arrays.asList(
        newEmptyHeaderField(":authority"),
        newHeaderField(":path", "/"),
        newHeaderField("age", "0"),
        newEmptyHeaderField("content-disposition"),
        newHeaderField("content-length", "0"),
        newEmptyHeaderField("cookie"),
        newEmptyHeaderField("date"),
        newEmptyHeaderField("etag"),
        newEmptyHeaderField("if-modified-since"),
        newEmptyHeaderField("if-none-match"),
        newEmptyHeaderField("last-modified"),
        newEmptyHeaderField("link"),
        newEmptyHeaderField("location"),
        newEmptyHeaderField("referer"),
        newEmptyHeaderField("set-cookie"),
        newHeaderField(":method", "CONNECT"),
        newHeaderField(":method", "DELETE"),
        newHeaderField(":method", "GET"),
        newHeaderField(":method", "HEAD"),
        newHeaderField(":method", "OPTIONS"),
        newHeaderField(":method", "POST"),
        newHeaderField(":method", "PUT"),
        newHeaderField(":scheme", "http"),
        newHeaderField(":scheme", "https"),
        newHeaderField(":status", "103"),
        newHeaderField(":status", "200"),
        newHeaderField(":status", "304"),
        newHeaderField(":status", "404"),
        newHeaderField(":status", "503"),
        newHeaderField("accept", "*/*"),
        newHeaderField("accept", "application/dns-message"),
        newHeaderField("accept-encoding", "gzip, deflate, br"),
        newHeaderField("accept-ranges", "bytes"),
        newHeaderField("access-control-allow-headers", "cache-control"),
        newHeaderField("access-control-allow-headers", "content-type"),
        newHeaderField("access-control-allow-origin", "*"),
        newHeaderField("cache-control", "max-age=0"),
        newHeaderField("cache-control", "max-age=2592000"),
        newHeaderField("cache-control", "max-age=604800"),
        newHeaderField("cache-control", "no-cache"),
        newHeaderField("cache-control", "no-store"),
        newHeaderField("cache-control", "public, max-age=31536000"),
        newHeaderField("content-encoding", "br"),
        newHeaderField("content-encoding", "gzip"),
        newHeaderField("content-type", "application/dns-message"),
        newHeaderField("content-type", "application/javascript"),
        newHeaderField("content-type", "application/json"),
        newHeaderField("content-type", "application/x-www-form-urlencoded"),
        newHeaderField("content-type", "image/gif"),
        newHeaderField("content-type", "image/jpeg"),
        newHeaderField("content-type", "image/png"),
        newHeaderField("content-type", "text/css"),
        newHeaderField("content-type", "text/html;charset=utf-8"),
        newHeaderField("content-type", "text/plain"),
        newHeaderField("content-type", "text/plain;charset=utf-8"),
        newHeaderField("range", "bytes=0-"),
        newHeaderField("strict-transport-security", "max-age=31536000"),
        newHeaderField("strict-transport-security", "max-age=31536000;includesubdomains"),
        newHeaderField("strict-transport-security", "max-age=31536000;includesubdomains;preload"),
        newHeaderField("vary", "accept-encoding"),
        newHeaderField("vary", "origin"),
        newHeaderField("x-content-type-options", "nosniff"),
        newHeaderField("x-xss-protection", "1; mode=block"),
        newHeaderField(":status", "100"),
        newHeaderField(":status", "204"),
        newHeaderField(":status", "206"),
        newHeaderField(":status", "302"),
        newHeaderField(":status", "400"),
        newHeaderField(":status", "403"),
        newHeaderField(":status", "421"),
        newHeaderField(":status", "425"),
        newHeaderField(":status", "500"),
        newEmptyHeaderField("accept-language"),
        newHeaderField("access-control-allow-credentials", "FALSE"),
        newHeaderField("access-control-allow-credentials", "TRUE"),
        newHeaderField("access-control-allow-headers", "*"),
        newHeaderField("access-control-allow-methods", "get"),
        newHeaderField("access-control-allow-methods", "get, post, options"),
        newHeaderField("access-control-allow-methods", "options"),
        newHeaderField("access-control-expose-headers", "content-length"),
        newHeaderField("access-control-request-headers", "content-type"),
        newHeaderField("access-control-request-method", "get"),
        newHeaderField("access-control-request-method", "post"),
        newHeaderField("alt-svc", "clear"),
        newEmptyHeaderField("authorization"),
        newHeaderField("content-security-policy", "script-src 'none';object-src 'none';base-uri 'none'"),
        newHeaderField("early-data", "1"),
        newEmptyHeaderField("expect-ct"),
        newEmptyHeaderField("forwarded"),
        newEmptyHeaderField("if-range"),
        newEmptyHeaderField("origin"),
        newHeaderField("purpose", "prefetch"),
        newEmptyHeaderField("server"),
        newHeaderField("timing-allow-origin", "*"),
        newHeaderField("upgrade-insecure-requests", "1"),
        newEmptyHeaderField("user-agent"),
        newEmptyHeaderField("x-forwarded-for"),
        newHeaderField("x-frame-options", "deny"),
        newHeaderField("x-frame-options", "sameorigin"));

    private static QpackHeaderField newEmptyHeaderField(String name) {
        return new QpackHeaderField(AsciiString.cached(name), AsciiString.EMPTY_STRING);
    }

    private static QpackHeaderField newHeaderField(String name, String value) {
        return new QpackHeaderField(AsciiString.cached(name), AsciiString.cached(value));
    }

    static final int INDEX_NOT_FOUND = -1;

    private static final CharSequenceMap<Integer> STATIC_INDEX_BY_NAME = createMap();

    /**
     * The number of header fields in the static table.
     */
    static final int length = STATIC_TABLE.size();

    /**
     * Return the header field at the given index value.
     * Note that QPACK uses 0-based indexing when HPACK is using 1-based.
     */
    static QpackHeaderField getEntry(int index) {
        return STATIC_TABLE.get(index);
    }

    /**
     * Returns the lowest index value for the given header field name in the static
     * table. Returns -1 if the header field name is not in the static table.
     */
    static int getIndex(CharSequence name) {
        Integer index = STATIC_INDEX_BY_NAME.get(name);
        if (index == null) {
            return INDEX_NOT_FOUND;
        }
        return index;
    }

    /**
     * Returns the index value for the given header field in the static table.
     * Returns -1 if the header field is not in the static table.
     */
    static int getIndexInsensitive(CharSequence name, CharSequence value) {
        int index = getIndex(name);
        if (index == INDEX_NOT_FOUND) {
            return INDEX_NOT_FOUND;
        }

        // Note this assumes all entries for a given header field are sequential.
        // TODO: Is this in right order?
        while (index < length) {
            QpackHeaderField entry = getEntry(index);
            if (QpackUtil.equalsVariableTime(name, entry.name) && QpackUtil.equalsVariableTime(value, entry.value)) {
                return index;
            }
            index++;
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * Creates a map CharSequenceMap header name to index value to allow quick lookup.
     */
    @SuppressWarnings("unchecked")
    private static CharSequenceMap<Integer> createMap() {
        CharSequenceMap<Integer> mapping =
            new CharSequenceMap<Integer>(true, UnsupportedValueConverter.<Integer>instance(), length);
        // Iterate through the static table in reverse order to
        // save the smallest index for a given name in the map.
        for (int index = length - 1; index >= 0; index--) {
            QpackHeaderField entry = getEntry(index);
            CharSequence name = entry.name;
            mapping.set(name, index);
        }
        return mapping;
    }

    private QpackStaticTable() {
    }
}

/*
 * Copyright 2020 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.common.catalog;

import org.thinkit.api.catalog.BiCatalog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ブラケットを管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum Bracket implements BiCatalog<Bracket, String> {

    /**
     * 開始ブラケット
     */
    START(0, "["),

    /**
     * 終了ブラケット
     */
    END(1, "]");

    /**
     * コード値
     */
    @Getter
    private final int code;

    /**
     * タグ
     */
    @Getter
    private final String tag;

    /**
     * {@link #START} 要素の文字列表現を返却します。
     *
     * @return {@link #START} 要素の文字列表現
     *
     * @see #START
     */
    public static String start() {
        return START.getTag();
    }

    /**
     * {@link #END} 要素の文字列表現を返却します。
     *
     * @return {@link #END} 要素の文字列表現
     *
     * @see #END
     */
    public static String end() {
        return END.getTag();
    }
}
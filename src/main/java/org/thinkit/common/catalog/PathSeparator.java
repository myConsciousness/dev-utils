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
 * パスのセパレータを管理するカタログです。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum PathSeparator implements BiCatalog<PathSeparator, String> {

    /**
     * セパレータ : {@code "/"}
     */
    SLASH(0, "/"),

    /**
     * セパレータ : {@code "\"}
     */
    BACK_SLASH(1, "\\"),

    /**
     * セパレータ : {@code ":"}
     */
    COLON(2, ":");

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
     * {@link #SLASH} 要素の文字列表現を返却します。
     *
     * @return {@link #SLASH} 要素の文字列表現
     *
     * @see #SLASH
     */
    public static String slash() {
        return SLASH.getTag();
    }

    /**
     * {@link #BACK_SLASH} 要素の文字列表現を返却します。
     *
     * @return {@link #BACK_SLASH} 要素の文字列表現
     *
     * @see #BACK_SLASH
     */
    public static String backSlash() {
        return BACK_SLASH.getTag();
    }

    /**
     * {@link #COLON} 要素の文字列表現を返却します。
     *
     * @return {@link #COLON} 要素の文字列表現
     *
     * @see #SLCOLONASH
     */
    public static String colon() {
        return COLON.getTag();
    }
}
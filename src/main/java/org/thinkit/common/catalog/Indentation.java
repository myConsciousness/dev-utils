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
import org.thinkit.common.Preconditions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * インデントを管理するカタログです。<br>
 * {@link #getBrace()}を使用することでインデント要素の文字列表現を取得することができます。<br>
 * <br>
 * 以下の静的メソッドを使用することでも各要素の文字列表現を取得できます。<br>
 * {@link #space()}<br>
 * {@link #tabCode()}<br>
 * {@link #returnCode()}<br>
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public enum Indentation implements BiCatalog<Indentation, String> {

    /**
     * 半角スペース
     */
    SPACE(0, " "),

    /**
     * 水平タブ
     */
    TAB(1, "\t"),

    /**
     * 改行
     */
    RETURN(2, "\r\n");

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
     * 半角空白を返却します。
     *
     * @return 半角空白
     * @see #SPACE
     */
    public static String space() {
        return SPACE.getTag();
    }

    /**
     * タブコードを返却します。
     *
     * @return タブコード
     * @see #TAB
     */
    public static String tabCode() {
        return TAB.getTag();
    }

    /**
     * 改行コードを返却します。
     *
     * @return 改行コード
     * @see #RETURN
     */
    public static String returnCode() {
        return RETURN.getTag();
    }

    /**
     * インデント用の半角空白を返却します。<br>
     * このメソッドからは必ず半角空白4個を結合したインデント用文字列が返却されます。<br>
     * 任意の個数のインデント用文字列を取得したい場合は{@link #getIndentSpaces(int)}を使用してください。
     *
     * @return 半角空白4個のインデント用文字列
     * @see #getIndentSpaces(int)
     */
    public static String getIndentSpaces() {
        return getIndentSpaces(4);
    }

    /**
     * 引数として指定された個数分の半角空白を返却します。<br>
     * このメソッドからは引数として指定された任意の個数分のインデント用文字列が返却されます。<br>
     * 半角空白4個のインデント用文字列を取得したい場合はこのメソッドを使用しても問題はありませんが、<br>
     * 専用のメソッドを用意しているので{@link #getIndentSpaces()}を使用するようにしてください。
     *
     * @param number インデント用文字列を生成する際に使用する任意の空白数
     * @return 指定された空白数に対応したインデント用文字列
     * @see #getIndentSpaces()
     */
    public static String getIndentSpaces(int number) {

        Preconditions.requirePositive(number);

        final StringBuilder indentSpaces = new StringBuilder(number);
        final String space = space();

        for (int i = 0; i < number; i++) {
            indentSpaces.append(space);
        }

        return indentSpaces.toString();
    }

    /**
     * インデント用のタブコードを返却します。<br>
     * このメソッドからは必ずタブコード1個が返却されます。<br>
     * 任意の個数のインデント用文字列を取得したい場合は{@link #getIndentTabs(int)}を使用してください。
     *
     * @return タブコード1個のインデント用文字列
     * @see #getIndentTabs(int)
     */
    public static String getIndentTabs() {
        return getIndentTabs(1);
    }

    /**
     * 引数として指定された個数分のタブコードを返却します。<br>
     * このメソッドからは引数として指定された任意の個数分のインデント用文字列が返却されます。<br>
     * タブコード1個のインデント用文字列を取得したい場合はこのメソッドを使用しても問題はありませんが、<br>
     * 専用のメソッドを用意しているので{@link #getIndentTab()}を使用するようにしてください。
     *
     * @param number インデント用文字列を生成する際に使用する任意のタブコード数
     * @return 指定されたタブコード数に対応したインデント用文字列
     * @see #getIndentTabs()
     */
    public static String getIndentTabs(int number) {

        Preconditions.requirePositive(number);

        final StringBuilder indentTabs = new StringBuilder(number);
        final String tabCode = tabCode();

        for (int i = 0; i < number; i++) {
            indentTabs.append(tabCode);
        }

        return indentTabs.toString();
    }
}
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

package org.thinkit.common.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.NonNull;

/**
 * リフレクション処理をより簡潔に行うために複雑な手続きをラッピングしたクラスです。<br>
 * この {@link FluentReflection}
 * クラスのインスタンス生成を行う際にはリフレクション処理対象のクラスオブジェクトをコンストラクタへ渡し、
 * ジェネリクスにはリフレクション処理を行った後にリフレクション処理対象のメソッドが返却する型を指定してください。<br>
 * 例えば、リフレクション処理対象のメソッドが {@code String} 型の値を返却する場合は以下のように指定してください。
 *
 * <pre>
 * インスタンス生成例（返却値が {@code String} 型の場合）
 * <code>FluentReflection&lt;String&gt; reflection = new FluentReflection&lt;&gt;(TestClass.class);</code>
 * String result = reflection.invoke("testMethod");
 * </pre>
 *
 * リフレクション処理を実行する際にはリフレクション処理対象のメソッド名を引数として
 * {@link FluentReflection#invoke(String)} メソッドを呼び出してください。
 * <p>
 * インスタンスメソッドまたは静的メソッドを問わず {@link FluentReflection#invoke(String)}
 * メソッドを使用することでリフレクション処理が可能ですが、静的メソッドに対してリフレクション処理を行う際には静的メソッドのために最適化された
 * {@link FluentReflection#invokeStatic(String)} の使用が推奨されます。
 * <p>
 * リフレクション処理を行う際にリフレクション処理対象のメソッドが引数を必要とする場合は、
 * {@link FluentReflection#add(Class, Object)} メソッドを使用し引数情報を設定してください。
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 *
 * @see #invoke(String)
 * @see #invokeStatic(String)
 * @see #add(Class, Object)
 */
public final class FluentReflection<T> {

    /**
     * リフレクション実行時に使用する引数情報
     */
    private ReflectionParameter parameter;

    /**
     * リフレクション処理を行う対象のクラス
     */
    private Class<?> clazz;

    /**
     * デフォルトコンストラクタ
     */
    @SuppressWarnings("unused")
    private FluentReflection() {
    }

    /**
     * コンストラクタ
     *
     * @param clazz リフレクション処理を行う対象のクラス
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public FluentReflection(@NonNull final Class<?> clazz) {
        this.parameter = new ReflectionParameter();
        this.clazz = clazz;
    }

    /**
     * 引数として指定されたメソッド名に紐づく処理を実行し処理結果を返却します。<br>
     * この {@link FluentReflection#invoke(Object, String)}
     * メソッドはprivateメソッドに対して処理を行うことを想定しています。<br>
     * 引数として {@code null} が渡された場合は実行時に必ず失敗します。<br>
     * <p>
     * リフレクション実行時に引数情報が必要な場合は {@link FluentReflection#add(Class, Object)}
     * メソッドを使用してください。
     * <p>
     * このメソッドは静的メソッドをリフレクションで処理することを想定しているため、インスタンスメソッドをリフレクションで処理する場合は
     * {@link FluentReflection#invoke(String)} メソッドを使用してください。
     *
     * <pre>
     * 使用例:
     * <code>FluentReflection&lt;String&gt; reflection = new FluentReflection&lt;&gt;(ContentLoader.class);
     * String result = reflection.invokeStatic(methodName);</code>
     * </pre>
     *
     * @param methodName リフレクション処理を行う対象のメソッド名
     * @return リフレクション処理の実行結果
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     * @throws IllegalArgumentException 引数として渡された {@code methodName} の値が空文字列の場合
     */
    public T invokeStatic(@NonNull final String methodName) {
        return this.invoke(methodName, true);
    }

    /**
     * 引数として指定されたメソッド名に紐づく処理を実行し処理結果を返却します。<br>
     * この {@link FluentReflection#invoke(Object, String)}
     * メソッドはprivateメソッドに対して処理を行うことを想定しています。<br>
     * 引数として {@code null} が渡された場合は実行時に必ず失敗します。<br>
     * <p>
     * リフレクション実行時に引数情報が必要な場合は {@link FluentReflection#add(Class, Object)}
     * メソッドを使用してください。
     * <p>
     * このメソッドはインスタンスメソッドをリフレクションで処理することを想定しているため、静的メソッドをリフレクションで処理する場合は
     * {@link FluentReflection#invokeStatic(String)} メソッドを使用してください。
     *
     * <pre>
     * 使用例:
     * <code>FluentReflection&lt;String&gt; reflection = new FluentReflection&lt;&gt;(ContentLoader.class);
     * String result = reflection.invoke(methodName);</code>
     * </pre>
     *
     * @param methodName リフレクション処理を行う対象のメソッド名
     * @return リフレクション処理の実行結果
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     * @throws IllegalArgumentException 引数として渡された {@code methodName} の値が空文字列の場合
     */
    public T invoke(@NonNull final String methodName) {
        return this.invoke(methodName, false);
    }

    /**
     * 引数として指定されたメソッド名に紐づく処理を実行し処理結果を返却します。<br>
     * この {@link FluentReflection#invoke(Object, String)}
     * メソッドはprivateメソッドに対して処理を行うことを想定しています。<br>
     * 引数として {@code null} が渡された場合は実行時に必ず失敗します。<br>
     * <p>
     * リフレクション実行時に引数情報が必要な場合は {@link FluentReflection#add(Class, Object)}
     * メソッドを使用してください。
     * <p>
     * 返却値を取得する際に未確定の型に変換する必要があるためメソッド全体に {@code @SuppressWarnings("unchecked")}
     * を指定していますがこの型変換は必ず成功します。
     *
     * <pre>
     * 使用例:
     * <code>FluentReflection&lt;String&gt; reflection = new FluentReflection&lt;&gt;(ContentLoader.class);
     * String result = reflection.invoke(methodName);</code>
     * </pre>
     *
     * @param methodName リフレクション処理を行う対象のメソッド名
     * @param isStatic   静的メソッドの可否を表現するフラグ
     * @return リフレクション処理の実行結果
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     * @throws IllegalArgumentException 引数として渡された {@code methodName} の値が空文字列の場合
     */
    @SuppressWarnings("unchecked")
    private T invoke(@NonNull final String methodName, final boolean isStatic) {

        if (methodName.isEmpty()) {
            throw new IllegalArgumentException("Method name is required.");
        }

        try {
            final Object clazzInstance = isStatic ? null : this.clazz.getDeclaredConstructor().newInstance();

            if (this.parameter.isEmpty()) {
                Method method = this.clazz.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return (T) method.invoke(clazzInstance);
            }

            Method method = this.clazz.getDeclaredMethod(methodName, this.parameter.getTypes());
            method.setAccessible(true);
            return (T) method.invoke(clazzInstance, this.parameter.getValues());

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * リフレクション実行時に使用する引数情報を設定します。<br>
     * リフレクション実行時に指定する引数の型を第1引数に、引数の値を第2引数に指定してください。<br>
     * 引数として {@code null} が渡された場合は実行時に必ず失敗します。<br>
     * <p>
     * この {@link FluentReflection#add(Class, Object)}
     * メソッドは自分自身のインスタンスを返却するためメソッドチェーンでの処理が可能です。
     *
     * <pre>
     * 使用例:
     * <code>FluentReflection&lt;T&gt; reflection = new FluentReflection&lt;&gt;();
     * reflection.add(String.class, "test");
     * reflection.add(Integer.class, 123);
     * </code>
     * </pre>
     *
     * <pre>
     * 使用例（メソッドチェーン）:
     * <code>FluentReflection&lt;T&gt; reflection = new FluentReflection&lt;&gt;();
     * reflection.add(String.class, "test").add(Integer.class, 123);
     * </code>
     * </pre>
     *
     * @param argumentType  引数の型
     * @param argumentValue 引数の値
     * @return 自分自身のインスタンス
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    public FluentReflection<T> add(@NonNull Class<?> argumentType, @NonNull Object argumentValue) {
        this.parameter.add(argumentType, argumentValue);
        return this;
    }
}
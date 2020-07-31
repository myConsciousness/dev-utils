/**
 * Project Name : dev-utils<br>
 * File Name : FluentIterator.java<br>
 * Encoding : UTF-8<br>
 * Creation Date : 2020/07/25<br>
 * <p>
 * Copyright © 2020 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be<br>
 * reproduced or used in any manner whatsoever.
 */

package org.thinkit.common.util.iterator;

import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * {@link Iterator} インターフェースの汎用的な処理を定義したクラスです。
 * <p>
 * 特定のクラスでイテレート処理を実装する場合はこの {@link FluentIterator} クラスを
 * {@link Iterable#iterator()} メソッドで返却するように実装することで簡単にイテレート処理を実装することができます。
 * <p>
 * この {@link FluentIterator} クラスを使用しイテレート処理を実装する場合はイテレート対象のクラスで
 * {@link IterableNode} インターフェースを実装してください。
 *
 * <pre>
 * 使用例:
 * <code>
 * public final class TestNodes implements Iterable&lt;TestNode&gt;, IterableNode&lt;TestNode&gt; {
 *
 *   &#64;Override
 *   public Iterator<TestNode> iterator() {
 *       return FluentIterator.of(this);
 *   }
 *
 *   &#64;Override
 *   public int size() {
 *       return size;
 *   }
 *
 *   &#64;Override
 *   public List&lt;TestNode&gt; nodes() {
 *       return nodes;
 *   }
 * }
 * </code>
 * </pre>
 *
 * @author Kato Shinya
 * @since 1.0
 * @version 1.0
 *
 * @see IterableNode<T>
 */
@ToString
@EqualsAndHashCode
public final class FluentIterator<T> implements Iterator<T> {

    /**
     * イテレートするノード群
     */
    private List<T> nodes;

    /**
     * イテレートするノード群のサイズ
     */
    private int size;

    /**
     * イテレート時のカーソルインデックス
     */
    private int cursorIndex;

    /**
     * デフォルトコンストラクタ
     */
    private FluentIterator() {
    }

    /**
     * コンストラクタ
     *
     * @param iterableNode {@link IterableNode} インターフェースを実装したイテレート可能なオブジェクト
     *
     * @exception NullPointerException 引数として {@code null} が渡された場合
     */
    private FluentIterator(@NonNull IterableNode<T> iterableNode) {
        this.nodes = iterableNode.nodes();
        this.size = iterableNode.size();
        this.cursorIndex = 0;
    }

    /**
     * 引数として指定された {@code iterableNode} オブジェクトの情報をもとに {@link FluentIterator}
     * クラスの新しいインスタンスを生成して返却します。
     *
     * @param <T>          イテレート時に取得するオブジェクトの型
     * @param iterableNode {@link IterableNode} インターフェースを実装したイテレート可能なオブジェクト
     * @return {@link FluentIterator} クラスの新しいインスタンス
     */
    public static <T> FluentIterator<T> of(@NonNull IterableNode<T> iterableNode) {
        return new FluentIterator<>(iterableNode);
    }

    @Override
    public T next() {
        return nodes.get(cursorIndex++);
    }

    @Override
    public boolean hasNext() {
        return this.size > this.cursorIndex;
    }
}
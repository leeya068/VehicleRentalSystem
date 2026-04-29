package model;

/**
 * Stack implementation using custom singly linked list
 * Only exposes push(), pop(), peek(), isEmpty() - LIFO behavior
 * Head is the top of stack for O(1) push/pop
 */
public class LinkedListStack<T> {
    private StackNode<T> top;
    private int size;
    
    public LinkedListStack() {
        this.top = null;
        this.size = 0;
    }
    
    /**
     * Push item onto stack - O(1) at head
     */
    public void push(T item) {
        StackNode<T> newNode = new StackNode<>(item);
        newNode.setNext(top);
        top = newNode;
        size++;
    }
    
    /**
     * Pop item from stack - O(1) from head
     */
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }
    
    /**
     * Peek at top item without removing
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.getData();
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int getSize() {
        return size;
    }
    
    public void clear() {
        top = null;
        size = 0;
    }
}
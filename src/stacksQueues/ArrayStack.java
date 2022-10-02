package stacksQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayStack<Item> implements Iterable<Item> {

  // initial capacity of underlying resizing array
  private static final int INIT_CAPACITY = 8;

  private Item[] a;         // array of items
  private int n;            // number of elements on stack


  /**
   * Initializes an empty stack.
   */
  public ArrayStack() {
    a = (Item[]) new Object[INIT_CAPACITY];
    n = 0;
  }

  /**
   * Is this stack empty?
   *
   * @return true if this stack is empty; false otherwise
   */
  public boolean isEmpty() {
    return n == 0;
  }

  /**
   * Returns the number of items in the stack.
   *
   * @return the number of items in the stack
   */
  public int size() {
    return n;
  }


  // resize the underlying array holding the elements
  private void resize(int capacity) {
    assert capacity >= n;

    // alternative implementation
    a = java.util.Arrays.copyOf(a, capacity);
  }


  /**
   * Adds the item to this stack.
   *
   * @param item the item to add
   */
  public void push(Item item) {
    // add item
    if (n == a.length) resize(a.length * 2);
    a[n] = item;
    n += 1;
  }

  /**
   * Removes and returns the item most recently added to this stack.
   *
   * @return the item most recently added
   * @throws java.util.NoSuchElementException if this stack is empty
   */
  public Item pop() {
    if (isEmpty()) throw new NoSuchElementException();
    if (n > 0 && n == a.length / 4) resize(a.length / 2);
    Item item = a[n - 1];
    n -= 1;
    return item;
  }


  /**
   * Returns (but does not remove) the item most recently added to this stack.
   *
   * @return the item most recently added to this stack
   * @throws java.util.NoSuchElementException if this stack is empty
   */
  public Item peek() {
    return a[n - 1];
  }

  /**
   * Returns an iterator to this stack that iterates through the items in LIFO order.
   *
   * @return an iterator to this stack that iterates through the items in LIFO order.
   */
  public Iterator<Item> iterator() {
    return new ReverseArrayIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  private class ReverseArrayIterator implements Iterator<Item> {
    int i = n;

    @Override
    public boolean hasNext() {
      return i > 0;
    }

    @Override
    public Item next() {
      Item item = a[n - i];
      i -= 1;
      return item;
    }
  }


  /**
   * Unit tests the {@code Stack} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    ArrayStack<String> as = new ArrayStack<>();
    as.push("a");
    as.push("b");
    as.push("c");

    for (String a : as) {
      System.out.print(a + " ");
    }
    System.out.println();


    System.out.println("size is " + as.size());
    System.out.println("peak is " + as.peek());
    System.out.println("pop one first item is " + as.pop());
    System.out.println("pop one second item is " + as.pop());
  }
}
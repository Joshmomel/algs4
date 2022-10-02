package stacksQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<Item> implements Iterable<Item> {
  private int n;          // size of the stack
  private Node first;     // top of stack

  // helper linked list class
  private class Node {
    private Item item;
    private Node next;
  }

  /**
   * Initializes an empty stack.
   */
  public LinkedStack() {
    first = null;
    n = 0;
    assert check();
  }

  /**
   * Is this stack empty?
   *
   * @return true if this stack is empty; false otherwise
   */
  public boolean isEmpty() {
    return first == null;
  }

  /**
   * Returns the number of items in the stack.
   *
   * @return the number of items in the stack
   */
  public int size() {
    return n;
  }

  /**
   * Adds the item to this stack.
   *
   * @param item the item to add
   */
  public void push(Item item) {
    Node oldFirst = first;
    Node newFirst = new Node();
    newFirst.item = item;
    newFirst.next = oldFirst;
    first = newFirst;
    n += 1;

    assert check();
  }

  /**
   * Removes and returns the item most recently added to this stack.
   *
   * @return the item most recently added
   * @throws java.util.NoSuchElementException if this stack is empty
   */
  public Item pop() {
    if (isEmpty()) throw new NoSuchElementException();
    Node oldFirst = first;
    first = first.next;
    oldFirst.next = null;
    n -= 1;

    assert check();
    return oldFirst.item;                   // return the saved item
  }


  /**
   * Returns (but does not remove) the item most recently added to this stack.
   *
   * @return the item most recently added to this stack
   * @throws java.util.NoSuchElementException if this stack is empty
   */
  public Item peek() {
    return first.item;
  }

  /**
   * Returns a string representation of this stack.
   *
   * @return the sequence of items in the stack in LIFO order, separated by spaces
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Item item : this)
      s.append(item).append(" ");
    return s.toString();
  }

  /**
   * Returns an iterator to this stack that iterates through the items in LIFO order.
   *
   * @return an iterator to this stack that iterates through the items in LIFO order.
   */
  public Iterator<Item> iterator() {
    return new LinkedIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  private class LinkedIterator implements Iterator<Item> {
    Node fistNode = first;

    @Override
    public boolean hasNext() {
      return fistNode != null;
    }

    @Override
    public Item next() {
      Item item = fistNode.item;
      fistNode = fistNode.next;
      return item;
    }
  }


  // check internal invariants
  private boolean check() {

    // check a few properties of instance variable 'first'
    if (n < 0) {
      return false;
    }
    if (n == 0) {
      if (first != null) return false;
    } else if (n == 1) {
      if (first == null) return false;
      if (first.next != null) return false;
    } else {
      if (first == null) return false;
      if (first.next == null) return false;
    }

    // check internal consistency of instance variable n
    int numberOfNodes = 0;
    for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
      numberOfNodes++;
    }
    return numberOfNodes == n;
  }

  /**
   * Unit tests the {@code LinkedStack} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    LinkedStack<String> ls = new LinkedStack<>();
    ls.push("a");
    ls.push("b");
    ls.push("c");

    for (String l : ls) {
      System.out.print(l + " ");
    }
    System.out.println();
    System.out.println(ls);


    System.out.println("size is " + ls.size());
    System.out.println("peak is " + ls.peek());
    System.out.println("pop one first item is " + ls.pop());
    System.out.println("pop one second item is " + ls.pop());

  }
}
package stacksQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
  private Node<Item> first;    // beginning of queue
  private Node<Item> last;     // end of queue
  private int n;               // number of elements on queue

  // helper linked list class
  private static class Node<Item> {
    private Item item;
    private Node<Item> next;
  }

  /**
   * Initializes an empty queue.
   */
  public Queue() {
    first = null;
    last = null;
    n = 0;
  }

  /**
   * Returns true if this queue is empty.
   *
   * @return {@code true} if this queue is empty; {@code false} otherwise
   */
  public boolean isEmpty() {
    return first == null;
  }

  /**
   * Returns the number of items in this queue.
   *
   * @return the number of items in this queue
   */
  public int size() {
    return n;
  }

  /**
   * Returns the item least recently added to this queue.
   *
   * @return the item least recently added to this queue
   * @throws NoSuchElementException if this queue is empty
   */
  public Item peek() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    return first.item;
  }

  /**
   * Adds the item to this queue.
   *
   * @param item the item to add
   */
  public void enqueue(Item item) {
    Node<Item> oldLast = last;
    last = new Node<>();
    last.item = item;
    last.next = null;

    if (isEmpty()) first = last;
    else oldLast.next = last;

    n += 1;
  }

  /**
   * Removes and returns the item on this queue that was least recently added.
   *
   * @return the item on this queue that was least recently added
   * @throws NoSuchElementException if this queue is empty
   */
  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    Item item = first.item;
    first = first.next;
    if (isEmpty()) last = null;
    n -= 1;

    return item;
  }

  /**
   * Returns a string representation of this queue.
   *
   * @return the sequence of items in FIFO order, separated by spaces
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Item item : this) {
      s.append(item);
      s.append(' ');
    }
    return s.toString();
  }

  /**
   * Returns an iterator that iterates over the items in this queue in FIFO order.
   *
   * @return an iterator that iterates over the items in this queue in FIFO order
   */
  public Iterator<Item> iterator() {
    return new LinkedIterator(first);
  }

  // an iterator, doesn't implement remove() since it's optional
  private class LinkedIterator implements Iterator<Item> {
    private Node<Item> current;

    public LinkedIterator(Node<Item> first) {
      current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }


  /**
   * Unit tests the {@code Queue} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    Queue<String> q = new Queue<>();
    q.enqueue("a");
    q.enqueue("b");
    q.enqueue("c");


    for (String s : q) {
      System.out.print(s + " ");
    }
    System.out.println();

    System.out.println("size is " + q.size());

    System.out.println("dequeue first item " + q.dequeue());
    System.out.println("dequeue second item " + q.dequeue());
    System.out.println("dequeue third item " + q.dequeue());


  }
}

package graph;

public class FlowEdge {
  public int v, w;
  private final double capacity;
  private double flow;

  public FlowEdge(int v, int w, double capacity) {
    if (v < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
    if (w < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
    if (!(capacity >= 0.0)) throw new IllegalArgumentException("Edge capacity must be non-negative");

    this.v = v;
    this.w = w;
    this.capacity = capacity;
    this.flow = 0.0;
  }

  public FlowEdge(int v, int w, double capacity, double flow) {
    if (v < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
    if (w < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
    if (!(capacity >= 0.0)) throw new IllegalArgumentException("Edge capacity must be non-negative");

    this.v = v;
    this.w = w;
    this.capacity = capacity;
    this.flow = flow;
  }

  public FlowEdge(FlowEdge e) {
    this.v = e.v;
    this.w = e.w;
    this.capacity = e.capacity;
    this.flow = e.flow;
  }

  public int from() {
    return v;
  }

  public int to() {
    return w;
  }

  public double capacity() {
    return capacity;
  }

  public double flow() {
    return flow;
  }

  public int other(int vertex) {
    if (vertex == v) return w;
    else if (vertex == w) return v;
    else throw new IllegalArgumentException("invalid endpoint");
  }

  public double residualCapacityTo(int vertex) {
    if (vertex == v) return flow;
    else if (vertex == w) return capacity - flow;
    else throw new IllegalArgumentException();
  }

  public void addResidualFlowTo(int vertex, double delta) {
    if (vertex == v) flow -= delta;
    else if (vertex == w) flow += delta;
    else throw new IllegalArgumentException();
  }

  public String toString() {
    return v + "->" + w + " " + flow + "/" + capacity;
  }

  public static void main(String[] args) {
    FlowEdge e = new FlowEdge(12, 23, 9, 2);
    e.addResidualFlowTo(23, 1);
    System.out.println(e);
    System.out.println("residualCapacityTo v=12 is " + e.residualCapacityTo(12));
    System.out.println("residualCapacityTo v=23 is " + e.residualCapacityTo(23));
  }

}



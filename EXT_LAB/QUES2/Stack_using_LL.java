class Node {
    int value;
    Node next;
    Node(int v) {
        value = v;
        next = null;
    }
}

class Stack {
    private Node head;
    private int n;

    MyStack() {
        head = null;
        n = 0;
    }

    void push(int x) {
        Node p = new Node(x);
        p.next = head;
        head = p;
        n++;
    }

    int pop() {
        if (head == null) return -1;
        int res = head.value;
        head = head.next;
        n--;
        return res;
    }

    int peek() {
        return head == null ? -1 : head.value;
    }

    boolean empty() {
        return head == null;
    }

    int size() {
        return n;
    }

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push(10);
        s.push(20);
        s.push(30);
        System.out.println(s.pop());
        System.out.println(s.peek());
        System.out.println(s.empty());
        System.out.println(s.size());
    }
}

package service.mem;

import model.Task;
import service.HistoryManager;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Long, Node<Task>> map = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    @Override
    public void add(Task task) {
        Node<Task> node = map.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        node = linkLast(task);
        map.put(task.getId(), node);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(Long id) {
        Node<Task> node = map.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    Node<Task> linkLast(Task obj) {
        Node<Task> oldLast = last;
        Node<Task> newNode = new Node<>(oldLast, obj, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        }
        else {
            oldLast.next = newNode;
        }
        return last;
    }

    List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        if (first == null) {
            return list;
        }
        Node<Task> cur = first;
        list.add(cur.object);
        while ((cur = cur.next) != null) {
            list.add(cur.object);
        }
        return list;
    }

    void removeNode(Node<Task> node) {
        Node<Task> previous = node.previous;
        Node<Task> next = node.next;

        if (previous == null) {
            first = next;
        }
        else {
            previous.next = next;
        }

        if (next == null) {
            last = previous;
        }
        else {
            next.previous = previous;
        }
    }

    private static class Node<T> {
        private final T object;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T object, Node<T> next) {
            this.object = object;
            this.next = next;
            this.previous = previous;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public T getObject() {
            return object;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public Node<T> getNext() {
            return next;
        }
    }


}

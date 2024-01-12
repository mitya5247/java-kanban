package ru.yandex.praktikum.project.engine;

import ru.yandex.praktikum.project.store.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    public ArrayList<Task> tasksListHistory = new ArrayList<>();

    public Map<Integer, Task> tasksMapHistory = new HashMap<>();

    public Node<Task> head;
    public Node<Task> tail;
    public int size = 0;


    class Node<Task> {

        public Node<Task> prev;
        public Task data;
        public Node<Task> next;


        public Node(Task data) {
            this.data = data;
            prev = null;
            next = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data = '" + data +
                    ", next = '" + next +
                    "}";
        }


    }

    public void linkLast(Task task) {
        Node<Task> node = new Node<>(task);
        if (head != null) {
            Node<Task> oldTail = tail;
            tail = node;
            tail.prev = oldTail;
            oldTail.next = tail;
        } else {
            head = node;
            head.next = null;
            head.prev = null;
            tail = node;
        }
        size++;
    }

    public void getTasks() {
        tasksListHistory.clear();
        Node<Task> node = head;

        if (head == null) {
            return;
        } else {
            while (node != null) {
                tasksListHistory.add(node.data);
                node = node.next;
            }
        }


    }

    public Node<Task> getNode(int id) {
        Node<Task> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.data.getId() == id) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public void removeNode(Node<Task> node) {
        Node<Task> firstNode = head;
        while (firstNode != null) {
            if (firstNode.equals(node)) {
                if (firstNode == tail && firstNode == head) {
                    tail = null;
                    head = null;
                    break;
                } else if (firstNode == head) {
                    head = firstNode.next;
                    firstNode.next.prev = head;
                    break;
                } else if (firstNode == tail) {
                    tail = firstNode.prev;
                    tail.next = null;
                    break;
                } else {
                    firstNode.prev.next = firstNode.next;
                    firstNode.next.prev = firstNode.prev; // она связывает ноду предыдущую с последующей, то есть ссылка на предыдущий список, без этой строки
                    //      firstNode.next.prev ссылается также на firstNode, который пытаемся удалить!
                    break;
                }

            }
            firstNode = firstNode.next;

        }

        --size;
    }

    @Override
    public void add(Task task) {
        if (!tasksMapHistory.containsKey(task.getId())) {
            tasksMapHistory.put(task.getId(), task);
            this.linkLast(task);
        } else {
            tasksMapHistory.remove(task.getId(), task);
            this.removeNode(this.getNode(task.getId()));
            this.linkLast(task);
            tasksMapHistory.put(task.getId(), task);
        }


    }

    @Override
    public List<Task> getHistory() {
        this.getTasks();
        if (!tasksListHistory.isEmpty()) {
            if (tasksListHistory.size() > 10) {
                ArrayList<Integer> idArray = new ArrayList<>();
                for (int i = 0; i < tasksListHistory.size() - 10; i++) {
                    idArray.add(tasksListHistory.get(i).getId());
                    if (tasksListHistory.size() == 10) {
                        break;
                    }
                }
                for (int i = 0; i < idArray.size(); i++) {
                    this.removeNode(this.getNode(tasksListHistory.get(0).getId()));
                    tasksListHistory.remove(0);
                }
            }
        } else {
            System.out.println("История пуста");
        }
        return tasksListHistory;
    }

    @Override
    public void remove(int id) {
        //  if (!tasksListHistory.isEmpty()) {
        //     for (int i = 0; i < tasksListHistory.size(); i++) {
        //        if (tasksListHistory.get(i).getId() == id) {
        //            tasksListHistory.remove(i);
        //           break;
        //        }
        //   }
        //  } else {
        // System.out.println("История  пуста");
        //    return ;
        //   }
        if (head != null) {
            this.removeNode(this.getNode(id));
            this.getTasks();
        } else {
            System.out.println("История пуста");
        }
    }


    @Override
    public String toString() {
        return this.getHistory().toString();
        //  return "LinkedList{" +
        //        "head='" + head +
        //      "}";
    }

    public String toStringNew() {
        //  return this.getHistory().toString();
        return "LinkedList{" +
                "head='" + head +
                "}";
    }
}

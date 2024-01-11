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
            tail = head;
        }
        size++;
    }

    public void getTasks() {
        tasksListHistory.clear();
        Node<Task> node = head;

        if (head == null) {
            System.out.println("Истории нет");
        } else {
            while (node != null) {
                tasksListHistory.add(node.data);
                node = node.next;
            }
         //   tasksListHistory.add(tail.data);
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
        ArrayList<Task> toDelete = new ArrayList<>();
        Node<Task> firstNode = head;
        if (firstNode == null) {
            System.out.println("Список пуст");
        } else if (firstNode.equals(node) && firstNode.equals(tail)) {

            toDelete.add(firstNode.data);

            tasksMapHistory.remove(firstNode.data.getId());
            tasksListHistory.removeAll(toDelete);

            head = null;
            tail = null;

        } else if (node.equals(tail)) {

            toDelete.add(node.data);


            tasksMapHistory.remove(firstNode.data.getId());
            tasksListHistory.removeAll(toDelete);

            tail = tail.prev;
            tail.next = null;

        } else {

            while (firstNode.next != null) {
                if (firstNode.equals(node)) {
                    firstNode.prev.next = firstNode.next;
                    return;
                }
                firstNode = firstNode.next;
            }
        }

        size--;
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
        if (!tasksListHistory.isEmpty()) {
            for (int i = 0; i < tasksListHistory.size(); i++) {
                if (tasksListHistory.get(i).getId() == id) {
                    tasksListHistory.remove(i);
                }
            }
        } else {
            System.out.println("История пуста");
        }
    }

    @Override
    public String toString() {
        return this.getHistory().toString();
     //   return "LinkedList{" +
     //           "head='" + head +
     //           "}";
    }
}

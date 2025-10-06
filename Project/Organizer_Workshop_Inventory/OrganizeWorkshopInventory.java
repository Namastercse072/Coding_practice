package Coding_practice.Project.Organizer_Workshop_Inventory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizeWorkshopInventory {

    // Inner Node class
    private static class Node {
        String toolName;
        int quantity;
        Node next;

        Node(String toolName, int quantity) {
            this.toolName = toolName;
            this.quantity = quantity;
        }
    }

    private Node[] table;
    private int size;

    // Constructor
    public OrganizeWorkshopInventory(int size) {
        this.size = size;
        table = new Node[size];
    }

    // Hash function
    private int hash(String key) {
        return Math.abs(key.hashCode()) % size;
    }

    // Insert tool or update existing
    public void insertTool(String toolName, int quantity) {
        int index = hash(toolName);
        Node head = table[index];
        Node curr = head;

        while (curr != null) {
            if (curr.toolName.equals(toolName)) {
                curr.quantity += quantity;
                return;
            }
            curr = curr.next;
        }

        Node newNode = new Node(toolName, quantity);
        newNode.next = head;
        table[index] = newNode;
    }

    // Search tool by name
    public Node searchTool(String toolName) {
        int index = hash(toolName);
        Node curr = table[index];
        while (curr != null) {
            if (curr.toolName.equals(toolName)) return curr;
            curr = curr.next;
        }
        return null;
    }

    // Update tool quantity
    public boolean updateQuantity(String toolName, int newQuantity) {
        Node tool = searchTool(toolName);
        if (tool != null) {
            tool.quantity = newQuantity;
            return true;
        }
        return false;
    }

    // Remove zero or negative quantity
    public void removeZeroQuantity() {
        for (int i = 0; i < size; i++) {
            Node curr = table[i];
            Node prev = null;
            while (curr != null) {
                if (curr.quantity <= 0) {
                    if (prev == null) table[i] = curr.next;
                    else prev.next = curr.next;
                } else {
                    prev = curr;
                }
                curr = curr.next;
            }
        }
    }

    // Get inventory as sorted list
    public String[] getInventoryStatus() {
        List<String> list = new ArrayList<>();
        for (Node head : table) {
            Node curr = head;
            while (curr != null) {
                list.add(curr.toolName + " - Qty: " + curr.quantity);
                curr = curr.next;
            }
        }
        Collections.sort(list);
        return list.toArray(new String[0]);
    }

    // === REQUIRED STATIC METHOD ===
    public static String[] organizeWorkshopInventory(String[] toolNames, int[] quantities) {
        if (toolNames == null || quantities == null || toolNames.length != quantities.length) {
            return new String[]{"Invalid input"};
        }

        OrganizeWorkshopInventory inventory = new OrganizeWorkshopInventory(10);

        for (int i = 0; i < toolNames.length; i++) {
            inventory.insertTool(toolNames[i], quantities[i]);
        }

        inventory.removeZeroQuantity();
        return inventory.getInventoryStatus();
    }

    // Example main for quick testing
    public static void main(String[] args) {
        String[] tools = {"Hammer", "Wrench", "Drill", "Wrench", "Screwdriver", "Drill"};
        int[] qty = {5, 3, 0, 4, 10, 2};

        String[] result = organizeWorkshopInventory(tools, qty);

        for (String item : result) {
            System.out.println(item);
        }
    }
}



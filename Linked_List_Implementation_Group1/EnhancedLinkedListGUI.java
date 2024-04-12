import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;

public class EnhancedLinkedListGUI extends JFrame {

    private final LinkedList<String> list;
    private final DefaultListModel<String> listModel;
    private final JTextField inputField;
    private final LinkedList<LinkedList<String>> previousStates;
    private ListIterator<LinkedList<String>> iterator;

    public EnhancedLinkedListGUI() {
        super("Enhanced LinkedList GUI");

        list = new LinkedList<>();
        listModel = new DefaultListModel<>();
        inputField = new JTextField(20);
        previousStates = new LinkedList<>();
        iterator = previousStates.listIterator();

        // Setup the frame
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel for adding elements
        JPanel inputPanel = new JPanel();
        JButton addButton = new JButton("Add (Tail)");
        JButton pushButton = new JButton("Push (Head)");
        JButton popButton = new JButton("Pop (Tail)");
        JButton removeButton = new JButton("Remove");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton traverseButton = new JButton("Traverse");
        JButton popIndexButton = new JButton("Pop Index");

        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(pushButton);
        inputPanel.add(popButton);
        inputPanel.add(removeButton);
        inputPanel.add(undoButton);
        inputPanel.add(redoButton);
        inputPanel.add(traverseButton);
        inputPanel.add(popIndexButton);

        // List display
        JList<String> displayList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(displayList);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    // Store previous state
                    previousStates.addLast(new LinkedList<>(list));
                    iterator = previousStates.listIterator(previousStates.size());

                    list.addLast(input); // Equivalent to add for LinkedList
                    listModel.addElement(input);
                    inputField.setText("");
                }
            }
        });

        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    // Store previous state
                    previousStates.addLast(new LinkedList<>(list));
                    iterator = previousStates.listIterator(previousStates.size());

                    list.addFirst(input);
                    listModel.add(0, input);
                    inputField.setText("");
                }
            }
        });

        popButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!list.isEmpty()) {
                    // Store previous state
                    previousStates.addLast(new LinkedList<>(list));
                    iterator = previousStates.listIterator(previousStates.size());

                    list.removeLast();
                    listModel.removeElementAt(listModel.size() - 1);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    // Store previous state
                    previousStates.addLast(new LinkedList<>(list));
                    iterator = previousStates.listIterator(previousStates.size());

                    list.remove(input); // Removes the first occurrence
                    listModel.removeElement(input);
                    inputField.setText("");
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iterator.hasPrevious()) {
                    list.clear();
                    list.addAll(iterator.previous());
                    listModel.clear();
                    for (String item : list) {
                        listModel.addElement(item);
                    }
                }
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iterator.hasNext()) {
                    iterator.next(); // Move to the next state
                    if (iterator.hasNext()) {
                        list.clear();
                        list.addAll(iterator.next());
                        iterator.previous(); // Move back to the current state
                        listModel.clear();
                        for (String item : list) {
                            listModel.addElement(item);
                        }
                    }
                }
            }
        });

        traverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder builder = new StringBuilder();
                for (String item : list) {
                    builder.append(item).append(" -> ");
                }
                if (builder.length() > 0) {
                    builder.setLength(builder.length() - 4);
                }
                JOptionPane.showMessageDialog(EnhancedLinkedListGUI.this, builder.toString());
            }
        });

        popIndexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    try {
                        int index = Integer.parseInt(input);
                        if (index >= 0 && index < list.size()) {
                            // Store previous state
                            previousStates.addLast(new LinkedList<>(list));
                            iterator = previousStates.listIterator(previousStates.size());

                            String removedItem = list.remove(index);
                            listModel.removeElement(removedItem);
                            inputField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(EnhancedLinkedListGUI.this, "Invalid index");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(EnhancedLinkedListGUI.this, "Invalid index");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnhancedLinkedListGUI().setVisible(true));
    }
}



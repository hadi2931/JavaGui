import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

class DatabaseEntry {
    String type;
    Object value;

    public DatabaseEntry(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}

class SimpleDatabaseEngine {
    private HashMap<String, HashMap<String, DatabaseEntry>> databaseSpine;

    public SimpleDatabaseEngine() {
        databaseSpine = new HashMap<>();
    }

    public boolean insertData(String tableName, String dataType, String data, String index) {
        HashMap<String, DatabaseEntry> tableData = databaseSpine.get(tableName);
        if (tableData == null) {
            tableData = new HashMap<>();
            databaseSpine.put(tableName, tableData);
        }

        switch (dataType) {
            case "String":
                tableData.put(index, new DatabaseEntry("string", data));
                break;
            case "Character":
                if (data.length() == 1) {
                    tableData.put(index, new DatabaseEntry("character", data.charAt(0)));
                } else {
                    return false;
                }
                break;
            case "Number":
                try {
                    if (data.contains(".")) {
                        tableData.put(index, new DatabaseEntry("number", Double.parseDouble(data)));
                    } else {
                        tableData.put(index, new DatabaseEntry("number", Long.parseLong(data)));
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public String displayData() {
    	String data = "";
        for (String tableName : databaseSpine.keySet()) {
            HashMap<String, DatabaseEntry> tableData = databaseSpine.get(tableName);
            for (String index : tableData.keySet()) {
                DatabaseEntry entry = tableData.get(index);
                data = data + "Table: " + tableName + ", Index: " + index + ", Type: " + entry.getType() + ", Value: " + entry.getValue() + "\n";
            }
        }
        return data;
    }

    public String getData(String tableName, String index) {
        HashMap<String, DatabaseEntry> tableData = databaseSpine.get(tableName);
        if (tableData != null) {
            DatabaseEntry entry = tableData.get(index);
            if (entry != null) {
            	return "Data at index '" + index + "' in table '" + tableName + "':\nType: " + entry.getType() + "\nValue: " + entry.getValue() + "\n";
            } else {
            	return "Data not found in table: " + tableName;
            }
        } else {
        	return "Table not found.";
        }
    }

    public void clearData() {
        databaseSpine.clear();
    }

    public String deleteData(String tableName, String index) {
        HashMap<String, DatabaseEntry> tableData = databaseSpine.get(tableName);
        if (tableData != null) {
            if (tableData.containsKey(index)) {
                tableData.remove(index);
                return "Data deleted successfully.";
            } else {
            	return "Data not found in table: " + tableName;
            }
        } else {
        	return "Table not found.";
        }
    }
}

public class SimpleDatabaseGUI extends JFrame {
	private SimpleDatabaseEngine database;

    public SimpleDatabaseGUI() {
        database = new SimpleDatabaseEngine();

        setTitle("Simple Database GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        JButton insertButton = new JButton("Insert");
        JButton displayButton = new JButton("Display");
        JButton getButton = new JButton("Get");
        JButton clearButton = new JButton("Clear");
        JButton deleteButton = new JButton("Delete");
        
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInsertWindow();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayData();
            }
        });

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGetWindow();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteWindow();
            }
        });

        buttonPanel.add(insertButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(getButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel);
    }

    private void openInsertWindow() {
        JFrame insertWindow = new JFrame("Insert Entry");
        insertWindow.setSize(400, 200);
        insertWindow.setLayout(new GridLayout(6, 2));
        insertWindow.setLocationRelativeTo(null);
        JTextField tableNameField = new JTextField();
        JComboBox<String> dataTypeComboBox = new JComboBox<>(new String[]{"String", "Character", "Number"});
        JTextField dataField = new JTextField();
        JTextField indexField = new JTextField();

        JButton addButton = new JButton("Add Entry");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = tableNameField.getText();
                String dataType = dataTypeComboBox.getSelectedItem().toString();
                String data = dataField.getText();
                String index = indexField.getText();
                
                if(tableName.isBlank())
                {
                	JOptionPane.showMessageDialog(insertWindow, "Please enter table name.");
                }
                else if(data.isBlank())
                {
                	JOptionPane.showMessageDialog(insertWindow, "Please enter data.");
                }
                else if(index.isBlank())
                {
                	JOptionPane.showMessageDialog(insertWindow, "Please enter index.");
                }
                else
                {
                    boolean check = database.insertData(tableName, dataType, data, index);
                    if(check)
                    {
                    	JOptionPane.showMessageDialog(insertWindow, "Entry added successfully.");
                        insertWindow.dispose();
                    }
                    else
                    {
                    	JOptionPane.showMessageDialog(insertWindow, "Data type is wrong.");
                    }
                    
                }

            }
        });

        insertWindow.add(new JLabel("Table Name: "));
        insertWindow.add(tableNameField);
        insertWindow.add(new JLabel("Data Type: "));
        insertWindow.add(dataTypeComboBox);
        insertWindow.add(new JLabel("Data: "));
        insertWindow.add(dataField);
        insertWindow.add(new JLabel("Index: "));
        insertWindow.add(indexField);
        insertWindow.add(new JLabel());
        insertWindow.add(addButton);

        insertWindow.setVisible(true);
    }

    private void displayData() {
        JFrame displayWindow = new JFrame("Display Data");
        displayWindow.setSize(400, 300);
        displayWindow.setLocationRelativeTo(null);
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        String data = "Displaying all data\n";
        data = data + database.displayData();
        displayArea.setText(data);
        displayWindow.add(new JScrollPane(displayArea));
        displayWindow.setVisible(true);
    }

    private void openGetWindow() {
        JFrame getWindow = new JFrame("Get Data");
        getWindow.setSize(300, 100);
        getWindow.setLayout(new GridLayout(3, 2));
        getWindow.setLocationRelativeTo(null);
        JTextField tableNameField = new JTextField();
        JTextField indexField = new JTextField();

        JButton getButton = new JButton("Get Data");
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = tableNameField.getText();
                String index = indexField.getText();
                if(tableName.isBlank())
                {
                	JOptionPane.showMessageDialog(getWindow, "Please enter table name.");
                }
                else if(index.isBlank())
                {
                	JOptionPane.showMessageDialog(getWindow, "Please enter index.");
                }
                else
                {
                	JOptionPane.showMessageDialog(getWindow, database.getData(tableName, index));
                	getWindow.dispose();
                }
            }
        });

        getWindow.add(new JLabel("Table Name: "));
        getWindow.add(tableNameField);
        getWindow.add(new JLabel("Index: "));
        getWindow.add(indexField);
        getWindow.add(new JLabel());
        getWindow.add(getButton);

        getWindow.setVisible(true);
    }

    private void clearData() {
        JFrame clearWindow = new JFrame("Clear Data");
        clearWindow.setSize(300, 100);
        clearWindow.setLocationRelativeTo(null);
        JButton clearButton = new JButton("Clear Data");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.clearData();
                JOptionPane.showMessageDialog(clearWindow, "All data cleared!");
                clearWindow.dispose();
            }
        });

        clearWindow.add(clearButton);
        clearWindow.setVisible(true);
    }

    private void openDeleteWindow() {
        JFrame deleteWindow = new JFrame("Delete Data");
        deleteWindow.setSize(300, 100);
        deleteWindow.setLayout(new GridLayout(3, 2));
        deleteWindow.setLocationRelativeTo(null);
        JTextField tableNameField = new JTextField();
        JTextField indexField = new JTextField();

        JButton deleteButton = new JButton("Delete Data");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = tableNameField.getText();
                String index = indexField.getText();
                if(tableName.isBlank())
                {
                	JOptionPane.showMessageDialog(deleteWindow, "Please enter table name.");
                }
                else if(index.isBlank())
                {
                	JOptionPane.showMessageDialog(deleteWindow, "Please enter index.");
                }
                else
                {
                	JOptionPane.showMessageDialog(deleteWindow, database.deleteData(tableName, index));
                	deleteWindow.dispose();
                }
            }
        });

        deleteWindow.add(new JLabel("Table Name: "));
        deleteWindow.add(tableNameField);
        deleteWindow.add(new JLabel("Index: "));
        deleteWindow.add(indexField);
        deleteWindow.add(new JLabel());
        deleteWindow.add(deleteButton);

        deleteWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleDatabaseGUI().setVisible(true);
            }
        });
    }
}

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class PhoneBookApp extends JFrame {
    private JTable contactTable;
    private DefaultTableModel model;
    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private ArrayList<Contact> contacts;

    public PhoneBookApp() {
        contacts = loadContacts(); // Load contacts from the file
        createUI();
    }

    private void createUI() {
        setTitle("Phone Book Application By RISING");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        model = new DefaultTableModel(new String[]{"Name", "Phone", "Email", "Location", "Category"}, 0);
        contactTable = new JTable(model);
        contactTable.setFillsViewportHeight(true);
        contactTable.setRowHeight(30);
        contactTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JTableHeader tableHeader = contactTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        tableHeader.setBackground(new Color(220, 220, 220));
        tableHeader.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contacts"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        categoryFilter = new JComboBox<>(new String[]{"All", "Family", "Friends", "Work", "Other"});
        categoryFilter.setFont(new Font("Arial", Font.PLAIN, 18));
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Category:"));
        searchPanel.add(categoryFilter);

        JButton searchButton = createButton("Search", new Color(3, 169, 244));
        JButton resetButton = createButton("Reset", new Color(255, 193, 7));
        JButton exportButton = createButton("Export to CSV", new Color(76, 175, 80));
        JButton importButton = createButton("Import from CSV", new Color(156, 39, 176));
        JButton sortButton = createButton("Sort by Name", new Color(255, 152, 0));

        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        searchPanel.add(exportButton);
        searchPanel.add(importButton);
        searchPanel.add(sortButton);
        add(searchPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 15, 15, 15));
        JButton addButton = createButton("Add Contact", new Color(76, 175, 80));
        JButton editButton = createButton("Edit Contact", new Color(255, 152, 0));
        JButton deleteButton = createButton("Delete Contact", new Color(244, 67, 54));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showAddContactDialog());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());
        searchButton.addActionListener(e -> searchContacts());
        resetButton.addActionListener(e -> resetSearch());
        exportButton.addActionListener(e -> exportContacts());
        importButton.addActionListener(e -> importContacts());
        sortButton.addActionListener(e -> sortContacts());

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterContacts(searchField.getText());
            }
        });

        categoryFilter.addActionListener(e -> filterContacts(searchField.getText()));

        loadContactsToTable();
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void loadContactsToTable() {
        model.setRowCount(0);
        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail(), contact.getLocation(), contact.getCategory()});
        }
    }

    private void showAddContactDialog() {
        JDialog dialog = createContactDialog("Add Contact", null);
        dialog.setVisible(true);
    }

    private void editContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Select a contact to edit.");
            return;
        }
        Contact contact = contacts.get(selectedRow);
        JDialog dialog = createContactDialog("Edit Contact", contact);
        dialog.setVisible(true);
    }

    private JDialog createContactDialog(String title, Contact contact) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(450, 400);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField nameField = new JTextField(contact != null ? contact.getName() : "", 20);
        JTextField phoneField = new JTextField(contact != null ? contact.getPhone() : "", 20);
        JTextField emailField = new JTextField(contact != null ? contact.getEmail() : "", 20);
        JTextField locationField = new JTextField(contact != null ? contact.getLocation() : "", 20);
        JComboBox<String> categoryField = new JComboBox<>(new String[]{"Family", "Friends", "Work", "Other"});
        if (contact != null) {
            categoryField.setSelectedItem(contact.getCategory());
        }
        
        JButton actionButton = new JButton(contact != null ? "Update" : "Add");

        Font inputFont = new Font("Arial", Font.PLAIN, 18);
        nameField.setFont(inputFont);
        phoneField.setFont(inputFont);
        emailField.setFont(inputFont);
        locationField.setFont(inputFont);
        actionButton.setFont(inputFont);

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1; dialog.add(locationField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; dialog.add(categoryField, gbc);
        gbc.gridx = 1; gbc.gridy = 5; dialog.add(actionButton, gbc);

        actionButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String location = locationField.getText();
            String category = (String) categoryField.getSelectedItem();
            if (validateInput(phone, email, name, location)) {
                if (contact != null) { // Update existing contact
                    contact.setName(name);
                    contact.setPhone(phone);
                    contact.setEmail(email);
                    contact.setLocation(location);
                    contact.setCategory(category);
                } else { // Add new contact
                    contacts.add(new Contact(name, phone, email, location, category));
                }
                saveContacts();
                loadContactsToTable();
                dialog.dispose();
                showMessage(contact != null ? "Contact updated successfully!" : "Contact added successfully!");
            }
        });

        return dialog;
    }

    private void deleteContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow != -1) {
            contacts.remove(selectedRow);
            saveContacts();
            loadContactsToTable();
            showMessage("Contact deleted successfully!");
        } else {
            showMessage("Select a contact to delete.");
        }
    }

    private void searchContacts() {
        String searchTerm = searchField.getText().toLowerCase();
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        model.setRowCount(0);
        for (Contact contact : contacts) {
            boolean matches = contact.getName().toLowerCase().contains(searchTerm);
            boolean categoryMatches = selectedCategory.equals("All") || contact.getCategory().equals(selectedCategory);
            if (matches && categoryMatches) {
                model.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail(), contact.getLocation(), contact.getCategory()});
            }
        }
    }

    private void resetSearch() {
        searchField.setText("");
        categoryFilter.setSelectedIndex(0);
        loadContactsToTable();
    }

    private void filterContacts(String searchTerm) {
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        model.setRowCount(0);
        for (Contact contact : contacts) {
            boolean matches = contact.getName().toLowerCase().contains(searchTerm.toLowerCase());
            boolean categoryMatches = selectedCategory.equals("All") || contact.getCategory().equals(selectedCategory);
            if (matches && categoryMatches) {
                model.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail(), contact.getLocation(), contact.getCategory()});
            }
        }
    }

    private void exportContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("exported_contacts.csv"))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhone() + "," + contact.getEmail() + "," + contact.getLocation() + "," + contact.getCategory());
                writer.newLine();
            }
            showMessage("Contacts exported successfully!");
        } catch (IOException e) {
            showMessage("Failed to export contacts.");
        }
    }

    private void importContacts() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 5) {
                        contacts.add(new Contact(data[0], data[1], data[2], data[3], data[4]));
                    }
                }
                saveContacts();
                loadContactsToTable();
                showMessage("Contacts imported successfully!");
            } catch (IOException e) {
                showMessage("Failed to import contacts.");
            }
        }
    }

    private void sortContacts() {
        contacts.sort(Comparator.comparing(Contact::getName));
        loadContactsToTable();
        showMessage("Contacts sorted by name.");
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "|" + contact.getPhone() + "|" + contact.getEmail() + "|" + contact.getLocation() + "|" + contact.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            showMessage("Failed to save contacts.");
        }
    }

    private ArrayList<Contact> loadContacts() {
        ArrayList<Contact> contactList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 5) {
                    contactList.add(new Contact(data[0], data[1], data[2], data[3], data[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("No contacts found.");
        }
        return contactList;
    }

    private boolean validateInput(String phone, String email, String name, String location) {
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || location.isEmpty()) {
            showMessage("All fields must be filled out.");
            return false;
        }
        if (!phone.matches("\\d{10}")) {
            showMessage("Phone number must be 10 digits.");
            return false;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showMessage("Invalid email format.");
            return false;
        }
        return true;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhoneBookApp app = new PhoneBookApp();
            app.setVisible(true);
        });
    }
}

class Contact {
    private String name;
    private String phone;
    private String email;
    private String location;
    private String category;

    public Contact(String name, String phone, String email, String location, String category) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

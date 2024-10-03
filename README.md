# Phone Book Application

A feature-rich Java Swing-based PhoneBook application that enables users to manage contacts, search, categorize, sort, and import/export contacts in CSV format. 

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Phone Book Application is built using Java Swing for creating a graphical user interface, allowing users to manage their contacts with ease. Users can add, edit, delete, search, and categorize contacts. The application supports importing and exporting contacts to and from CSV files. It also includes additional functionalities like sorting and filtering contacts based on search terms and categories.

## Features

- **Add New Contacts**: Users can add contacts with fields like name, phone number, email, location, and category.
- **Edit Contacts**: Modify existing contact details.
- **Delete Contacts**: Remove contacts from the list.
- **Search Contacts**: Search contacts based on their name or category.
- **Categorize Contacts**: Contacts can be classified under various categories such as Family, Friends, Work, or Other.
- **Sort Contacts**: Sort contacts by name in alphabetical order.
- **Data Persistence**: Contacts are saved to a text file (`contacts.txt`) and automatically loaded when the application starts.
- **Import Contacts from CSV**: Import multiple contacts at once from a CSV file.
- **Export Contacts to CSV**: Export all contacts to a CSV file for backup or transfer.
- **Reset Search**: Clear search results and return to the full contact list.

## Technologies Used

- **Java**: Core language for application logic.
- **Java Swing**: GUI framework used to create the interface for managing contacts.
- **File I/O**: Used for persisting contacts in text files and importing/exporting CSV files.

## Prerequisites

Ensure you have the following installed on your system:

- **Java JDK** (version 8 or higher)
- **IDE** ( VsCode , IntelliJ IDEA, Eclipse, NetBeans, or any preferred Java IDE)

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-repo-url
    ```

2. Navigate to the project directory:

    ```bash
    cd PhoneBookApp
    ```

3. Open the project in your preferred IDE.

4. Ensure the project directory contains the following structure:

    ```
    📁 PhoneBookApp
    ┣ 📄 PhoneBookApp.java
    ┣ 📄 contacts.txt
    ┣ 📄 .gitignore
    ┣ 📄 README.md
    ┣ 📁 .git
    ```

## Running the Application

1. Open the `PhoneBookApp.java` file in your IDE and run the program.
2. The main window will appear, allowing you to manage your contacts.

## Usage

### Adding a Contact
- Click on the "Add Contact" button and fill in the required fields (Name, Phone, Email, Location, and Category).
- Hit the "Add" button to save the contact.

### Editing a Contact
- Select a contact from the table, then click the "Edit Contact" button.
- Modify the fields as needed and click "Update" to save changes.

### Deleting a Contact
- Select a contact and click "Delete Contact" to remove it.

### Searching for a Contact
- Enter a search term in the search bar, and the contact list will filter accordingly.
- You can also filter contacts by selecting a category (e.g., Family, Friends, Work, or Other) from the dropdown.

### Sorting Contacts
- Click on "Sort by Name" to arrange contacts in alphabetical order.

### Importing Contacts from CSV
- Click on "Import from CSV" to load contacts from a CSV file. Ensure the CSV file is properly formatted as `Name,Phone,Email,Location,Category`.

### Exporting Contacts to CSV
- Click on "Export to CSV" to export your contacts list to a CSV file named `exported_contacts.csv`.

## Screenshots

### Main Screen
![Main Screen](https://github.com/user-attachments/assets/7aca8cec-1da6-4361-ac01-3825a6c1a760)

### Add / Edit Contact

![ Add / Edit ](https://github.com/user-attachments/assets/b433c04f-cc49-42ab-936e-a280f6005862)

### Database ( conatacts.txt )

![contacts.txt](https://github.com/user-attachments/assets/5c7793fd-9158-468a-a645-8ecb2adef7f7)

### & Many More

## Contributing

If you wish to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch:

    ```bash
    git checkout -b feature-branch
    ```

3. Commit your changes:

    ```bash
    git commit -m 'Add new feature'
    ```

4. Push to the branch:

    ```bash
    git push origin feature-branch
    ```

5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

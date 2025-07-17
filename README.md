# File Sharing Server 

This repository contains the final project developed for the "Distributed Systems" course as part of the Computer Engineering degree at Politécnico de Coimbra, Escola Superior de Tecnologia e Gestão. The project was elaborated on June 28, 2025, by Rafael Ventura.

## Table of Contents

- Introduction
    
- Developed Work
    
    - Client Graphical User Interface
        
    - Authentication
        
    - Local Folder Selection
        
    - File Transfer
        
    - Logs
        
    - Automatic Update
        
- Conclusion

## Introduction

This project aimed to implement a distributed application for file exchange between users, utilizing Java RMI (Remote Method Invocation), Sockets, and REST web services. The application was designed to enable direct and efficient file sharing and transfer, or via an intermediary server. The development followed specific requirements, including a client graphical interface, unique user authentication, automatic updates of user and file lists, file transfer with overwrite confirmation, and action logging. Java RMI was chosen for its transparency in accessing remote objects, simplifying client-server communication.

## Developed Work

The application leverages Java RMI for client-server communication. Java RMI allows methods to be executed on objects in another Java Virtual Machine (JVM). This technology was chosen for its transparent access to remote objects, using the same Java syntax and semantics, which simplified project development. All specified requirements, including GUI, folder selection, server data input, authentication, user listing, automatic updates, file listing, file transfer, logout, error/success messages, file replacement, and logs, have been implemented.

### Client Graphical User Interface

The client's GUI was built using NetBeans visual editor to create a JFrame Form, which automatically generates the necessary Java code. The GUI architecture incorporates the following Swing components:

- **`JButton`**: For invoking methods via `actionPerformed`.

- **`JLabel`**: For displaying informative text.

- **`JList`**: For listing user, file, and log vectors.

- **`JOptionPane`**: For informative or action messages, using `showMessageDialog()` and `showConfirmDialog()`.

- **`JTextField`**: For client text input.

- **`JPanel`**: Serves as a container to group various components related to different functionalities (logs, users, files, etc.).

- **`JScrollPane`**: Provides scrollbars for `JList` components using `setViewPortView()`.


This selection of components resulted in a simple, functional, interactive, and self-explanatory GUI (Figure 1).

### Authentication

Users must pass through several validations to access the server:
- **Server IP**: Can be an IP address or "localhost" if client and server are on the same machine.
- **Port**: Must always be port 1099, as defined on the server side (`LocateRegistry.createRegistry(1099)`).
- **Username**: Can be any name, provided it's unique (case-sensitive) among connected clients.

Figure 2 illustrates a successful authentication with the username "Rafael". Figure 3 demonstrates an authentication error when another user "rafael" attempts to connect while "Rafael" is already logged in. Exception handling is in place for connection errors, such as invalid port, incorrect IP, or a blank username (Figure 4).

### Local Folder Selection

To select a local folder for sharing, the client clicks the "Selecionar pasta para partilha" button, which invokes the `JFileChooser` component (Figure 5). After both "Rafael" and "Rodrigo" clients have selected their shared folders, the files they are sharing can be listed by clicking on their respective usernames in the user list (Figure 6).

### File Transfer

File transfer occurs directly between clients29. When a user selects another client, chooses a file, and clicks "Descarregar ficheiro," the `downloadFile()` method is called. This method takes the selected user and filename as parameters, verifies the file's existence, and if found, writes it to the downloading client's shared folder (Figure 7).

An intermediate validation checks if the file already exists in the user's shared folder. If it does, a `JOptionPane` prompts for confirmation to replace the existing file. If the user proceeds and no errors occur during the transfer, an informative message confirms the successful file transfer (Figure 9).

### Logs

Log listings are unique for each user. When a user authenticates, the timestamp of their login is recorded. This timestamp is sent to the server when the client requests logs, and the server filters the logs by checking if each log's timestamp is greater than or equal to the user's authentication timestamp. Figure 10 shows that when "Rafael" logs out and back in, previous logs are not re-displayed. Meanwhile, "francisco," who remained logged in, observes Rafael's login and logout actions.

### Automatic Update

The `Timer` class is used for automatic updates of the interface (user lists, files, and logs).

`Timer` allows threads to schedule background tasks The

`scheduleAtFixedRate` method schedules a `TimerTask` to repeat updates every three seconds. It's crucial to place GUI updates within the

`SwingUtilities.invokeLater()` method because `Timer` tasks run on their own thread, while Swing applications require updates to be performed on the Event Dispatch Thread (EDT).

`invokeLater()` queues these tasks for execution by the EDT.

## Conclusion

This project successfully consolidated theoretical and practical knowledge of distributed systems, particularly remote communication using Java RMI. The implemented application fully meets all specified requirements (Table 1), offering a functional and intuitive solution for file exchange. Key features include a user-friendly GUI, effective authentication, file transfer with overwrite confirmation, and detailed action logging. While initially challenging, Java RMI proved to be a suitable choice due to its simplicity and efficiency in remote method invocation. This project provided valuable practical experience. Future work could involve integrating other technologies like Sockets and REST web services.

---

**Note:** All figures referenced in this README (e.g., Figure 1, Figure 2) can be found in the `Relatório_SD_Proj_RafaelVentura.pdf` file within this repository.

#### License

This project is licensed under the MIT License - see the **LICENSE** file for details.
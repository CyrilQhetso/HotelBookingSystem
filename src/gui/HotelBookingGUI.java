package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.*;

public class HotelBookingGUI extends JFrame {
    private Hotel hotel;
    private JTextArea displayArea;
    private JTextField customerNameField;
    private JComboBox<String> roomComboBox;
    private JLabel statusLabel;

    public HotelBookingGUI(Hotel hotel) {
        this.hotel = hotel;
        initializeGUI();
    }

    private void initializeGUI() {
        // Set Nimbus look and feel for modern styling.
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to default.
            System.err.println("Nimbus Look and Feel not available.");
        }

        setTitle("Hotel Booking System");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window.

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Booking Panel (North)
        JPanel bookingPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Book a Room"));

        bookingPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        bookingPanel.add(customerNameField);

        bookingPanel.add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>();
        bookingPanel.add(roomComboBox);

        JButton bookButton = new JButton("Book Room");
        bookingPanel.add(bookButton);
        // Empty placeholder.
        bookingPanel.add(new JLabel());

        // Book button action.
        bookButton.addActionListener(e -> {
            String customerName = customerNameField.getText().trim();
            if (customerName.isEmpty()) {
                showStatus("Please enter a Customer Name.", Color.RED);
                return;
            }
            if (roomComboBox.getSelectedItem() == null) {
                showStatus("No available room selected.", Color.RED);
                return;
            }
            String roomInfo = (String) roomComboBox.getSelectedItem();
            String roomNumber = roomInfo.split(" ")[1];
            Customer customer = new Customer(customerName);
            boolean success = hotel.bookRoom(roomNumber, customer);
            if (success) {
                showStatus("Room " + roomNumber + " booked successfully for " + customerName + ".", Color.GREEN);
            } else {
                showStatus("Booking failed. The room might not exist or is already booked.", Color.RED);
            }
            refreshDisplay();
        });

        mainPanel.add(bookingPanel, BorderLayout.NORTH);

        // Display area (Center)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Status Label for animations.
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        // Control Panel with Refresh, Booking Log, and Cancel Booking buttons.
        JPanel controlPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshDisplay());
        controlPanel.add(refreshButton);

        JButton logButton = new JButton("Show Booking Log");
        logButton.addActionListener(e -> showBookingLog());
        controlPanel.add(logButton);

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> showCancelBookingDialog());
        controlPanel.add(cancelButton);

        mainPanel.add(controlPanel, BorderLayout.PAGE_END);

        add(mainPanel);
        refreshDisplay();
    }

    // Displays a message that fades out.
    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
        // Use a Swing Timer to clear the message after 3 seconds.
        Timer timer = new Timer(3000, (ActionEvent e) -> statusLabel.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        List<Room> availableRooms = hotel.getAvailableRooms();
        roomComboBox.removeAllItems();

        if (availableRooms.isEmpty()) {
            sb.append("All rooms are fully booked, no rooms available at the moment.");
            roomComboBox.setEnabled(false);
        } else {
            roomComboBox.setEnabled(true);
            sb.append("Available Rooms:\n");
            for (Room room : availableRooms) {
                String roomEntry = "Room " + room.getRoomNumber() + " - Price: " + room.calculatePrice();
                sb.append(roomEntry).append("\n");
                roomComboBox.addItem(roomEntry);
            }
        }
        displayArea.setText(sb.toString());
    }

    private void showBookingLog() {
        StringBuilder logBuilder = new StringBuilder();
        List<Booking> bookings = hotel.getBookings();
        if (bookings.isEmpty()) {
            logBuilder.append("No bookings have been made yet.");
        } else {
            for (Booking booking : bookings) {
                logBuilder.append(booking.toString()).append("\n");
            }
        }
        JTextArea logArea = new JTextArea(logBuilder.toString());
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Booking Log", JOptionPane.INFORMATION_MESSAGE);
    }

    // Dialog for canceling a booking.
    private void showCancelBookingDialog() {
        String customerName = JOptionPane.showInputDialog(this, "Enter your Customer Name to cancel a booking:");
        if (customerName == null || customerName.trim().isEmpty()) {
            showStatus("Customer name required to cancel a booking.", Color.RED);
            return;
        }
        // Find all bookings for this customer.
        List<Booking> customerBookings = hotel.getBookings().stream()
                .filter(b -> b.getCustomer().getName().equalsIgnoreCase(customerName.trim()))
                .toList();
        if (customerBookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings found for customer: " + customerName, "Cancel Booking", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Build an array of booking descriptions.
        String[] bookingOptions = new String[customerBookings.size()];
        for (int i = 0; i < customerBookings.size(); i++) {
            bookingOptions[i] = customerBookings.get(i).toString();
        }
        String selectedBooking = (String) JOptionPane.showInputDialog(
                this,
                "Select the booking to cancel:",
                "Cancel Booking",
                JOptionPane.QUESTION_MESSAGE,
                null,
                bookingOptions,
                bookingOptions[0]);
        if (selectedBooking == null) {
            return;
        }
        // Extract the room number from the selected booking string.
        String[] parts = selectedBooking.split(" ");
        if (parts.length < 2) return;
        String roomNumber = parts[1];
        boolean success = hotel.cancelBooking(roomNumber, new Customer(customerName.trim()));
        if (success) {
            showStatus("Booking for room " + roomNumber + " canceled.", Color.GREEN);
        } else {
            showStatus("Failed to cancel booking for room " + roomNumber + ".", Color.RED);
        }
        refreshDisplay();
    }
}
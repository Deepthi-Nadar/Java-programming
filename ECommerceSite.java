import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Product {
    String name;
    String delivery; // "nextweek" or "2days"
    boolean payOnDelivery;
    boolean offer;
    boolean limitedDeal;
    int price;
    String image;

    public Product(String name, int price, String delivery, boolean payOnDelivery, boolean offer, boolean limitedDeal, String image) {
        this.name = name;
        this.price = price;
        this.delivery = delivery;
        this.payOnDelivery = payOnDelivery;
        this.offer = offer;
        this.limitedDeal = limitedDeal;
        this.image = image;
    }
}

public class ECommerceSite extends JFrame implements ItemListener {
    JCheckBox nextWeek, twoDays, payOnDelivery, offers, limitedDeal;
    JPanel productGrid;
    ArrayList<Product> products;
    ArrayList<Product> cartItems = new ArrayList<>();
    JButton cartBtn;
    JTextField searchField;

    public ECommerceSite() {
        setTitle("A-Z Shopping Site");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- TOP PANEL --------
        JPanel topPanel = new JPanel(new BorderLayout());

        // --- Site name at top ---
        JLabel siteName = new JLabel("A-Z Shopping Site", JLabel.CENTER);
        siteName.setFont(new Font("Arial", Font.BOLD, 24));
        siteName.setForeground(new Color(0, 128, 0)); // GREEN heading
        topPanel.add(siteName, BorderLayout.NORTH);

        // --- Search box centered ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchField = new JTextField(20);
        JButton searchBtn = createGreenButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        // --- Cart button on the right ---
        cartBtn = createGreenButton("Cart (0)");
        topPanel.add(cartBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        cartBtn.addActionListener(e -> showCartWindow());

        // Search action
        ActionListener searchAction = e -> applyFiltersAndSearch();
        searchField.addActionListener(searchAction);
        searchBtn.addActionListener(searchAction);

        // -------- FILTER PANEL --------
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));

        JLabel deliveryLabel = new JLabel("Delivery Day:");
        nextWeek = new JCheckBox("Get it by next week");
        twoDays = new JCheckBox("Get it by 2 days");

        JLabel paymentLabel = new JLabel("Payment Option:");
        payOnDelivery = new JCheckBox("Pay on Delivery");

        JLabel dealsLabel = new JLabel("Deals:");
        offers = new JCheckBox("Offers");
        limitedDeal = new JCheckBox("Limited time deal");

        filterPanel.add(deliveryLabel);
        filterPanel.add(nextWeek);
        filterPanel.add(twoDays);
        filterPanel.add(Box.createVerticalStrut(10));
        filterPanel.add(paymentLabel);
        filterPanel.add(payOnDelivery);
        filterPanel.add(Box.createVerticalStrut(10));
        filterPanel.add(dealsLabel);
        filterPanel.add(offers);
        filterPanel.add(limitedDeal);

        nextWeek.addItemListener(this);
        twoDays.addItemListener(this);
        payOnDelivery.addItemListener(this);
        offers.addItemListener(this);
        limitedDeal.addItemListener(this);

        add(filterPanel, BorderLayout.WEST);

        // -------- CENTER PANEL (Products) --------
        productGrid = new JPanel();
        productGrid.setLayout(new GridLayout(0, 3, 15, 15));
        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load products
        loadProducts();

        // Show all products initially
        displayProducts(products);

        setVisible(true);
    }

    private JButton createGreenButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0, 128, 0)); // green
        btn.setForeground(Color.WHITE);          // white text
        btn.setFocusPainted(false);
        return btn;
    }

    private void loadProducts() {
        products = new ArrayList<>();
        products.add(new Product("Phone A", 12000, "nextweek", true, true, false, "image1.jpg"));
        products.add(new Product("Phone B", 15000, "2days", false, false, true, "image2.jpg"));
        products.add(new Product("Laptop X", 55000, "nextweek", true, true, true, "image3.jpg"));
        products.add(new Product("Laptop Y", 45000, "2days", true, false, false, "image4.jpg"));
        for (int i = 5; i <= 20; i++) {
            products.add(new Product("Product " + i, i * 1000,
                    (i % 2 == 0 ? "nextweek" : "2days"),
                    (i % 3 == 0), (i % 2 == 0), (i % 4 == 0),
                    "image" + i + ".jpg"));
        }
    }

    private void displayProducts(ArrayList<Product> productList) {
        productGrid.removeAll();
        for (Product p : productList) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            card.setPreferredSize(new Dimension(220, 300));

            ImageIcon icon = new ImageIcon(p.image);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(imgLabel);

            JLabel nameLabel = new JLabel(p.name, JLabel.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("Price: ₹" + p.price, JLabel.CENTER);
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel deliveryLabel = new JLabel("Delivery: " + (p.delivery.equals("nextweek") ? "Next Week" : "2 Days"), JLabel.CENTER);
            deliveryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(nameLabel);
            card.add(priceLabel);
            card.add(deliveryLabel);

            if (p.payOnDelivery) {
                JLabel pod = new JLabel("Pay on Delivery Available", JLabel.CENTER);
                pod.setForeground(Color.BLUE);
                pod.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(pod);
            }
            if (p.offer) {
                JLabel offer = new JLabel("Special Offer!", JLabel.CENTER);
                offer.setForeground(Color.RED);
                offer.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(offer);
            }
            if (p.limitedDeal) {
                JLabel deal = new JLabel("Limited Time Deal!", JLabel.CENTER);
                deal.setForeground(new Color(200, 0, 0));
                deal.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(deal);
            }

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            JButton addToCartBtn = createGreenButton("Add to Cart");
            JButton buyNowBtn = createGreenButton("Buy Now");

            addToCartBtn.addActionListener(e -> {
                cartItems.add(p);
                updateCartButton();
                JOptionPane.showMessageDialog(this, p.name + " added to cart!");
            });

            buyNowBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Buying " + p.name + " for ₹" + p.price);
            });

            btnPanel.add(addToCartBtn);
            btnPanel.add(buyNowBtn);

            card.add(btnPanel);

            productGrid.add(card);
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private void showCartWindow() {
        JDialog cartDialog = new JDialog(this, "Your Cart", true);
        cartDialog.setSize(400, 400);
        cartDialog.setLayout(new BorderLayout());

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        for (Product p : cartItems) {
            JCheckBox cb = new JCheckBox(p.name + " - ₹" + p.price);
            cartPanel.add(cb);
            checkBoxes.add(cb);
        }

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        cartDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton deleteBtn = createGreenButton("Delete Selected Items");
        JButton buyBtn = createGreenButton("Buy Selected Items");

        deleteBtn.addActionListener(e -> {
            ArrayList<Product> toRemove = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    toRemove.add(cartItems.get(i));
                }
            }
            cartItems.removeAll(toRemove);
            updateCartButton();
            cartDialog.dispose();
        });

        buyBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Buying:\n");
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    sb.append(cartItems.get(i).name).append(" - ₹").append(cartItems.get(i).price).append("\n");
                }
            }
            JOptionPane.showMessageDialog(cartDialog, sb.toString());
        });

        btnPanel.add(deleteBtn);
        btnPanel.add(buyBtn);

        cartDialog.add(btnPanel, BorderLayout.SOUTH);
        cartDialog.setVisible(true);
    }

    private void updateCartButton() {
        cartBtn.setText("Cart (" + cartItems.size() + ")");
    }

    private void applyFiltersAndSearch() {
        ArrayList<Product> filtered = new ArrayList<>();
        String query = searchField.getText().toLowerCase().trim();

        for (Product p : products) {
            boolean match = true;

            if (nextWeek.isSelected() && !p.delivery.equals("nextweek")) match = false;
            if (twoDays.isSelected() && !p.delivery.equals("2days")) match = false;
            if (payOnDelivery.isSelected() && !p.payOnDelivery) match = false;
            if (offers.isSelected() && !p.offer) match = false;
            if (limitedDeal.isSelected() && !p.limitedDeal) match = false;

            if (!p.name.toLowerCase().contains(query)) match = false;

            if (match) filtered.add(p);
        }

        if (!nextWeek.isSelected() && !twoDays.isSelected() &&
                !payOnDelivery.isSelected() && !offers.isSelected() &&
                !limitedDeal.isSelected() && query.isEmpty()) {
            displayProducts(products);
        } else {
            displayProducts(filtered);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        applyFiltersAndSearch();
    }

    public static void main(String[] args) {
        new ECommerceSite();
    }
}

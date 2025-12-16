package com.imran.repository;

import com.imran.domain.Cart;
import com.imran.domain.CartItem;
import com.imran.domain.Order;
import com.imran.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository implementation responsible for managing Cart persistence.
 * Uses in-memory data structures to simulate database behavior.
 */
public class CartRepositoryImpl implements CartRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartRepositoryImpl.class);

    /**
     * Thread-safe map to store carts per user.
     * Key   → User
     * Value → Set of carts belonging to that user
     *
     * ConcurrentHashMap allows safe access by multiple threads.
     */
    private static final Map<User, Set<Cart>> CARTS = new ConcurrentHashMap<>();

    /**
     * Repository used to check whether a cart has already been converted into an order.
     */
    private OrderRepository orderRepository = new OrderRepositoryImpl();

    /**
     * Retrieves the most recent active cart of a user.
     * If the latest cart has already been used to place an order,
     * an empty Optional is returned.
     *
     * @param currentUser the user whose cart is requested
     * @return Optional containing the active cart, or empty if none is available
     */
    @Override
    public Optional<Cart> findByUser(User currentUser) {

        // Retrieve the most recent cart of the user
        var usersCart = getCart(currentUser);

        if (usersCart.isPresent()) {
            var cart = usersCart.get();

            // Retrieve all orders placed by the user
            var orders = orderRepository.findOrderByUser(currentUser);

            // If cart is already used in an order, return empty
            if (isOrderAlreadyPlacedWith(orders, cart)) {
                return Optional.empty();
            }

            // Otherwise return the active cart
            return Optional.of(cart);
        }

        return Optional.empty();
    }

    /**
     * Checks whether the given cart has already been associated with an order.
     *
     * @param orders all orders placed by the user
     * @param cart   the cart to be checked
     * @return true if the cart is already used in an order, false otherwise
     */
    private boolean isOrderAlreadyPlacedWith(Set<Order> orders, Cart cart) {
        return orders.stream()
                .anyMatch(order -> order.getCart().equals(cart));
    }

    /**
     * Retrieves the most recently created cart of a user.
     *
     * @param currentUser the user whose cart is requested
     * @return Optional containing the latest cart, or empty if none exists
     */
    private Optional<Cart> getCart(User currentUser) {

        // Get all carts belonging to the user
        Set<Cart> carts = CARTS.get(currentUser);

        // Return the last inserted cart if available
        if (carts != null && !carts.isEmpty()) {
            Cart cart = (Cart) carts.toArray()[carts.size() - 1];
            return Optional.of(cart);
        }

        return Optional.empty();
    }

    /**
     * Saves a cart for a user.
     * If the user already has carts, the new cart is added to the existing set.
     * Otherwise, a new cart set is created for the user.
     *
     * @param cart the cart to be saved
     * @return the saved cart
     */
    @Override
    public Cart save(Cart cart) {

        // Add cart to existing user's cart set
        CARTS.computeIfPresent(cart.getUser(), (user, carts) -> {
            carts.add(cart);
            return carts;
        });

        // Create a new cart set for the user if none exists
        CARTS.computeIfAbsent(cart.getUser(), user -> {
            var carts = new LinkedHashSet<Cart>();
            carts.add(cart);
            return carts;
        });

        return cart;
    }

    /**
     * Updates the latest cart of a user.
     * The updated cart replaces the most recently added cart.
     *
     * @param cart the cart to update
     * @return the updated cart
     */
    @Override
    public Cart update(Cart cart) {

        CARTS.computeIfPresent(cart.getUser(), (user, carts) -> {

            // Convert set to array to access the last element
            Cart[] cartArray = carts.toArray(Cart[]::new);

            // Replace the last cart with the updated one
            cartArray[cartArray.length - 1] = cart;

            // Restore order and uniqueness using LinkedHashSet
            return new LinkedHashSet<>(Arrays.asList(cartArray));
        });

        return cart;
    }

    /**
     * Finds a CartItem inside a cart by product ID.
     *
     * @param productId the product ID to search for
     * @param cart      the cart in which to search
     * @return Optional containing the CartItem if found, otherwise empty
     */
    @Override
    public Optional<CartItem> findCartItem(Long productId, Cart cart) {

        // Return empty if cart or cart items are missing
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return Optional.empty();
        }

        // Search for the cart item matching the product ID
        return cart.getCartItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getProduct() != null &&
                                Objects.equals(cartItem.getProduct().getId(), productId))
                .findFirst();
    }
}

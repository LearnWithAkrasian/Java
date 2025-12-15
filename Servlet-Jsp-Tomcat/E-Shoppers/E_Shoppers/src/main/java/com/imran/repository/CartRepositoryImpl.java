package com.imran.repository;

import com.imran.domain.Cart;
import com.imran.domain.CartItem;
import com.imran.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CartRepositoryImpl implements CartRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartRepositoryImpl.class);

    // ConcurrentHashMap ensures thread-safe operations for multiple users accessing carts simultaneously
    // Key: User object, Value: Set of Cart objects belonging to that user
    private static final Map<User, Set<Cart>> CARTS = new ConcurrentHashMap<>();

    /**
     * Find the most recent cart of a user.
     * @param currentUser The user whose cart we want to retrieve.
     * @return Optional containing the latest cart if exists, otherwise empty Optional.
     */
    @Override
    public Optional<Cart> findByUser(User currentUser) {
        // Get the set of carts for the current user
        Set<Cart> carts = CARTS.get(currentUser);

        // Check if the set exists and is not empty
        if (carts != null && !carts.isEmpty()) {
            // Convert set to array to get the last added cart
            Cart cart = (Cart) carts.toArray()[carts.size() - 1];

            return Optional.of(cart);// Return the last cart wrapped in Optional
        }
        return Optional.empty();
    }

    /**
     * Save a cart for a user.
     * @param cart The cart to save.
     * @return The saved cart.
     */
    @Override
    public Cart save(Cart cart) {
        // If the user already exists in the map, add the cart to their existing set
        CARTS.computeIfPresent(cart.getUser(),
                (user, carts) -> {
             carts.add(cart);// Add new cart to the set
             return carts;// Return the updated set
        });

        // If the user does not exist in the map, create a new set and add the cart
        CARTS.computeIfAbsent(cart.getUser(), user -> {
            var carts = new LinkedHashSet<Cart>();// LinkedHashSet keeps insertion order and avoids duplicates
            carts.add(cart); // Add the cart
            return carts;// Return the new set to store in the map
        });
        return cart;// Return the cart which is saved
    }

    /**
     * Update the latest cart of a user.
     * @param cart The cart object to update.
     * @return The updated cart.
     */
    @Override
    public Cart update(Cart cart) {
        // If user exists, update the last added cart in the set
        CARTS.computeIfPresent(cart.getUser(), ((user, carts) -> {
            // Convert set to array to access last element
            Cart[] objects = carts.toArray(Cart[]::new);
            // Replace the last cart with the updated cart
            objects[objects.length - 1] = cart;

            // Convert array back to LinkedHashSet to maintain order and remove duplicates
            return new LinkedHashSet<>(Arrays.asList(objects));
        }));

        return cart;
    }

    @Override
    public Optional<CartItem> findCartItem(Long productId, Cart cart) {
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return Optional.empty();
        }

        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct() != null &&
                        Objects.equals(cartItem.getProduct().getId(), productId))
                .findFirst();
    }
}

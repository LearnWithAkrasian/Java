package com.imran.service;

import com.imran.domain.Order;
import com.imran.domain.ShippingAddress;
import com.imran.domain.User;
import com.imran.dto.ShippingAddressDto;
import com.imran.exceptions.CartItemNotFoundException;
import com.imran.repository.CartRepository;
import com.imran.repository.OrderRepository;
import com.imran.repository.ShippingAddressRepository;

/**
 * Implementation of the OrderService interface.
 *
 * This service is responsible for:
 *  - Processing customer orders
 *  - Saving shipping address information
 *  - Fetching the user's cart
 *  - Creating and persisting an Order
 */
public class OrderServiceImpl implements OrderService {

    /** Repository for persisting Order entities */
    private OrderRepository orderRepository;

    /** Repository for persisting ShippingAddress entities */
    private ShippingAddressRepository shippingAddressRepository;

    /** Repository for accessing Cart data */
    private CartRepository cartRepository;

    /**
     * Constructor-based dependency injection.
     *
     * @param orderRepository repository used to save orders
     * @param shippingAddressRepository repository used to save shipping addresses
     * @param cartRepository repository used to fetch cart information
     */
    public OrderServiceImpl(OrderRepository orderRepository,
                            ShippingAddressRepository shippingAddressRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * Processes an order for the currently authenticated user.
     *
     * Steps:
     *  1. Convert ShippingAddressDto to ShippingAddress entity
     *  2. Save the shipping address
     *  3. Retrieve the user's cart
     *  4. Create a new Order
     *  5. Save the order
     *
     * @param shippingAddressDto shipping address data from the client/UI
     * @param currentUser the currently logged-in user
     * @throws CartItemNotFoundException if no cart exists for the user
     */
    @Override
    public void processOrder(ShippingAddressDto shippingAddressDto, User currentUser) {

        // Convert DTO to entity
        var shippingAddress = converToShippingAdd(shippingAddressDto);

        // Save shipping address in the database
        var savedShippingAddress = shippingAddressRepository.save(shippingAddress);

        // Retrieve cart for the current user
        var cart = cartRepository
                .findByUser(currentUser)
                .orElseThrow(() ->
                        new CartItemNotFoundException("Cart not found by user: " + currentUser));

        // Create a new order
        var order = new Order();
        order.setCart(cart);
        order.setUser(currentUser);
        order.setShipped(false); // Order is not shipped initially
        order.setShippingAddress(savedShippingAddress);

        // Persist order in the database
        orderRepository.save(order);
    }

    /**
     * Converts ShippingAddressDto to ShippingAddress entity.
     *
     * This method separates DTO-to-entity mapping logic
     * from the main business logic.
     *
     * @param shippingAddressDto data transfer object containing shipping details
     * @return populated ShippingAddress entity
     */
    private ShippingAddress converToShippingAdd(ShippingAddressDto shippingAddressDto) {

        var shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(shippingAddressDto.getAddress());
        shippingAddress.setAddress2(shippingAddressDto.getAddress2());
        shippingAddress.setCity(shippingAddressDto.getCity());
        shippingAddress.setZip(shippingAddressDto.getZip());
        shippingAddress.setState(shippingAddressDto.getState());
        shippingAddress.setCountry(shippingAddressDto.getCountry());
        shippingAddress.setMobileNumber(shippingAddressDto.getMobileNumber());

        return shippingAddress;
    }
}

package com.imran.service;

import com.imran.domain.Cart;
import com.imran.domain.CartItem;
import com.imran.domain.Product;
import com.imran.domain.User;
import com.imran.exceptions.CartItemNotFoundException;
import com.imran.exceptions.ProductNotFoundException;
import com.imran.repository.CartItemRepository;
import com.imran.repository.CartRepository;
import com.imran.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

// Manage User Cards.
// Adding products to Carts
// updating cart
// Creating new cart
// Increasing existing product quantities
// It Communicates with repos CarCartRepository, ProductRepository, CartItemRepository
public class CartServiceImpl implements CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    /**
     * Constructor injection ensures that CartServiceImpl always has a CartRepository.
     * This is a good practice for dependency management and testability.
     *
     * @param cartRepository repository used for CRUD operations on carts
     */
    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Retrieves the most recent cart for a given user.
     * If the user does not have a cart yet, a new cart is created for him.
     *
     * @param currentUser the user whose cart is to be retrieved
     * @return the latest Cart for the user, or a newly created cart if none exists
     */
    @Override
    public Cart getCartByUser(User currentUser) {

        return cartRepository.findByUser(currentUser) // retrieve the most recent cart of currentUser
                .orElseGet(() -> createNewCart(currentUser)); // if currentUser has no cart create new cart and save it
    }

    /**
     * @param productId which product will be added in the given cart
     * @param cart current cart where product will be added with given product id
     */
    @Override
    public void addProductToCart(String productId, Cart cart) {
        var product = findProduct(productId);

        addProductToCart(cart, product); // add product to the cart
        LOGGER.info("Product added to cart with id: {}", productId);
        updateCart(cart);

    }

    @Override
    public void removeProductToCart(String productId, Cart cart) {
        var  product = findProduct(productId);

        removeProductToCart(product, cart);
        LOGGER.info("Product removed from cart with id: {}", productId);
        updateCart(cart);
    }

    private void updateCart(Cart cart) {
        Integer totalItems = getTotalItem(cart); // calculate the items in the cart
        BigDecimal totalPrice = calculateTotalPrice(cart); // calculate total prices of all products

        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItems);
        cartRepository.update(cart); // update the cart's state.

        LOGGER.info("Total items added to cart: {}",  totalItems);
        LOGGER.info("Total price added to cart: {}",  totalPrice);
    }

    private Product findProduct(String productId) {
        // if product id null or length = 0 then throw an exception
        // because id will not be null.
        if (productId == null || productId.length() == 0) {
            throw new IllegalArgumentException("Product Id cannot be null");
        }

        // parsing string to Integer.
        Long id = parseProductId(productId);

        // retrieve product with the id. if not found then throw an exception
        return productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
    }
    // Check first cart have any cartItem which have the product
    // if no cartItem found then throw exception
    // otherwise remove product by one
    private void removeProductToCart(Product product, Cart cart) {
        var cartItemOptional
                = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findAny();

        var cartItem
                = cartItemOptional
                .orElseThrow(() -> new CartItemNotFoundException("Cart not found by product: " + product));

        // If product quantity is less then 2 then just remove
        // otherwise reduce by one.
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getPrice().subtract(product.getProductPrice()));
            cart.getCartItems().add(cartItem);
            cartItemRepository.update(cartItem);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.remove(cartItem);
        }
    }

    // Calculate the Total cart items of the cart
    private Integer getTotalItem(Cart cart) {

        return cart.getCartItems()
                .stream()
                .map(CartItem::getQuantity)
                .reduce(0, Integer::sum);
    }

    // Calculate the total price of the Cart
    private BigDecimal calculateTotalPrice(Cart cart) {

        return cart.getCartItems()
                .stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    // if similar cart item found then just increase it
    // otherwise create new one cart item.
    private void addProductToCart(Cart cart, Product product) {
        var cartItemOptional = findSimilarProductInCart(cart, product);

        var cartItem = cartItemOptional.map(this::increaseQuantityByOne) // if similar exist then increase quantity
                                       .orElseGet(() -> createNewCartItem(product)); // otherwise create new one and save

        cart.getCartItems().add(cartItem);
    }

    // if cart items found similar then increase the quantity and price
    private CartItem increaseQuantityByOne(CartItem cartItem) {

        cartItem.setQuantity(cartItem.getQuantity() + 1);

        BigDecimal totalPrice = cartItem.getProduct().getProductPrice().multiply(new BigDecimal(cartItem.getQuantity()));

        cartItem.setPrice(totalPrice);

        LOGGER.info("Similar product found, increased quantity is : {}",  cartItem.getQuantity());
        return cartItemRepository.update(cartItem);
    }

    // If similar cart item is not found in CartItemRepo then create a new one.
    private CartItem createNewCartItem(Product product) {
        LOGGER.info("Creating new cart item for {}", product);

        var  cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setPrice(product.getProductPrice());

        LOGGER.info("Saving {} in new cart item", product);
        return cartItemRepository.save(cartItem);
    }



    /**
    Create new cart given user.
     @param currentUser the user whose cart will be created.
     @return new cart associated with the currentUser.
     */
    private Cart createNewCart(User currentUser) {
        LOGGER.info("Creating new cart for {}", currentUser);
        Cart cart = new Cart();
        cart.setUser(currentUser);
        return cartRepository.save(cart);
    }

    // checking product id is string formate or not.
    // if not then throw an exception
    private Long parseProductId(String productId) {
        try {
            return Long.parseLong(productId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Product Id cannot be null");
        }
    }

    // finding the similar cart item in cart item repo
    private Optional<CartItem> findSimilarProductInCart(Cart cart, Product product) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst();
    }
}

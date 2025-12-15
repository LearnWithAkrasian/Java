package com.imran.repository;

import com.imran.domain.ShippingAddress;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ShippingAddressRepositoryImpl implements ShippingAddressRepository {
    private static final Set<ShippingAddress> SHIPPING_ADDRESSES = new CopyOnWriteArraySet<>();
    @Override
    public ShippingAddress save(ShippingAddress convertToShippingAddress) {
        SHIPPING_ADDRESSES.add(convertToShippingAddress);
        return convertToShippingAddress;
    }
}

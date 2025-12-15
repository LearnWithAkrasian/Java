package com.imran.repository;

import com.imran.domain.ShippingAddress;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress convertToShippingAddress);
}

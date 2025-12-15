package com.imran.service;

import com.imran.domain.ShippingAddress;
import com.imran.domain.User;
import com.imran.dto.ShippingAddressDto;

public interface OrderService {
    void processOrder(ShippingAddressDto shippingAddressDto, User currentUser);
}

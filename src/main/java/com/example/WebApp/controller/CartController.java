
package com.example.WebApp.controller;

import com.example.WebApp.model.Orders;
import com.example.WebApp.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/shop/cart")
public class CartController {

    CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/createOrder")
    @ResponseBody
    public void createOrderFromCart() {
        Orders order = cartService.createOrderFromCart();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public String getCartItems(Model model) {
        model.addAttribute("cart", cartService.findMyCart());
        return "cart";
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me")
    @ResponseBody
    public void clearCart () {
        cartService.clearCart();
    }
}

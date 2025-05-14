
package com.example.WebApp.controller;




import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.CartDto;
import com.example.WebApp.model.Cart;
import com.example.WebApp.model.Orders;
import com.example.WebApp.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


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


//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping()
//    @ResponseBody
//    public ResponseEntity<List<Cart>> getCarts() {
//        return ResponseEntity.ok(cartService.findAll());
//    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public String getCartItems(Model model) {
        model.addAttribute("cart", cartService.findMyCart());
        return "cart";
    }


//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/{userId}")
//    @ResponseBody
//    public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) {
//        return ResponseEntity.ok(cartService.findByUserId(userId));
//    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me")
    @ResponseBody
    public void clearCart () {
        cartService.clearCart();
    }
}

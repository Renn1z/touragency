package touragency.controllers;

import touragency.models.Discount;
import touragency.repositories.DiscountRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountRepository discountRepository;

    @GetMapping
    public String listDiscounts(Model model,
                                @RequestParam(required = false) String search) {
        List<Discount> discounts;
        if (search != null && !search.trim().isEmpty()) {
            discounts = discountRepository.findByDiscountNameContainingIgnoreCase(search.trim());
        } else {
            discounts = discountRepository.findAll();
        }
        model.addAttribute("discounts", discounts);
        model.addAttribute("search", search);
        return "discounts/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("discount", new Discount());
        model.addAttribute("types", List.of("PERCENTAGE", "FIXED_AMOUNT"));
        return "discounts/form";
    }

    @PostMapping("/create")
    public String createDiscount(@Valid @ModelAttribute Discount discount,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("types", List.of("PERCENTAGE", "FIXED_AMOUNT"));
            return "discounts/form";
        }

        discountRepository.save(discount);
        return "redirect:/discounts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isEmpty()) {
            return "redirect:/discounts";
        }
        model.addAttribute("discount", discount.get());
        model.addAttribute("types", List.of("PERCENTAGE", "FIXED_AMOUNT"));
        return "discounts/form";
    }

    @PostMapping("/edit/{id}")
    public String updateDiscount(@PathVariable Long id,
                                 @Valid @ModelAttribute Discount discount,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("types", List.of("PERCENTAGE", "FIXED_AMOUNT"));
            return "discounts/form";
        }

        if (!discountRepository.existsById(id)) {
            return "redirect:/discounts";
        }

        discount.setDiscountId(id);
        discountRepository.save(discount);
        return "redirect:/discounts";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable Long id) {
        if (discountRepository.existsById(id)) {
            discountRepository.deleteById(id);
        }
        return "redirect:/discounts";
    }
}
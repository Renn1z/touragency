package touragency.controllers;

import touragency.models.Customer;
import touragency.repositories.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String listCustomers(Model model,
                                @RequestParam(required = false) String search) {
        List<Customer> customers;
        if (search != null && !search.trim().isEmpty()) {
            customers = customerRepository.searchCustomers(search.trim());
        } else {
            customers = customerRepository.findAll();
        }
        model.addAttribute("customers", customers);
        model.addAttribute("search", search);
        return "customers/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute Customer customer,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customers/form";
        }

        // Проверка уникальности телефона
        if (customer.getPhone() != null &&
                customerRepository.findByPhone(customer.getPhone()).isPresent()) {
            result.rejectValue("phone", "error.customer",
                    "Клиент с таким телефоном уже существует");
            return "customers/form";
        }

        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer.get());
        return "customers/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id,
                                 @Valid @ModelAttribute Customer customer,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "customers/form";
        }

        if (!customerRepository.existsById(id)) {
            return "redirect:/customers";
        }

        customer.setCustomerId(id);
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
        return "redirect:/customers";
    }
}
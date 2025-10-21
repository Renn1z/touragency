package touragency.controllers;

import touragency.models.Contract;
import touragency.models.Customer;
import touragency.models.Tour;
import touragency.models.Discount;
import touragency.repositories.ContractRepository;
import touragency.repositories.CustomerRepository;
import touragency.repositories.TourRepository;
import touragency.repositories.DiscountRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @GetMapping
    public String listContracts(Model model,
                                @RequestParam(required = false) String search) {
        List<Contract> contracts = contractRepository.findAll();
        model.addAttribute("contracts", contracts);
        return "contracts/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Contract contract = new Contract();
        model.addAttribute("contract", contract);
        setupFormModel(model);
        return "contracts/form";
    }

    @PostMapping("/create")
    public String createContract(@Valid @ModelAttribute Contract contract,
                                 BindingResult result, Model model,
                                 @RequestParam(value = "selectedCustomers", required = false) List<Long> selectedCustomers,
                                 @RequestParam(value = "selectedTours", required = false) List<Long> selectedTours) {

        // Валидация выбранных клиентов
        if (selectedCustomers == null || selectedCustomers.isEmpty()) {
            result.rejectValue("customers", "NotEmpty.contract.customers", "Должен быть выбран хотя бы один клиент");
        } else {
            Set<Customer> customers = selectedCustomers.stream()
                    .map(customerId -> customerRepository.findById(customerId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            contract.setCustomers(customers);
        }

        // Валидация выбранных туров
        if (selectedTours == null || selectedTours.isEmpty()) {
            result.rejectValue("tours", "NotEmpty.contract.tours", "Должен быть выбран хотя бы один тур");
        } else {
            Set<Tour> tours = selectedTours.stream()
                    .map(tourId -> tourRepository.findById(tourId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            contract.setTours(tours);
        }

        if (result.hasErrors()) {
            setupFormModel(model);

            // Добавляем выбранные ID для повторного отображения формы
            if (selectedCustomers != null) {
                model.addAttribute("selectedCustomerIds", new HashSet<>(selectedCustomers));
            }
            if (selectedTours != null) {
                model.addAttribute("selectedTourIds", new HashSet<>(selectedTours));
            }

            return "contracts/form";
        }

        contractRepository.save(contract);
        return "redirect:/contracts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isEmpty()) {
            return "redirect:/contracts";
        }
        model.addAttribute("contract", contract.get());
        setupFormModel(model);

        // Добавляем выбранные ID для предзаполнения формы
        Set<Long> selectedCustomerIds = contract.get().getCustomers().stream()
                .map(Customer::getCustomerId)
                .collect(Collectors.toSet());
        model.addAttribute("selectedCustomerIds", selectedCustomerIds);

        Set<Long> selectedTourIds = contract.get().getTours().stream()
                .map(Tour::getTourId)
                .collect(Collectors.toSet());
        model.addAttribute("selectedTourIds", selectedTourIds);

        return "contracts/form";
    }

    @PostMapping("/edit/{id}")
    public String updateContract(@PathVariable Long id,
                                 @Valid @ModelAttribute Contract contract,
                                 BindingResult result, Model model,
                                 @RequestParam(value = "selectedCustomers", required = false) List<Long> selectedCustomers,
                                 @RequestParam(value = "selectedTours", required = false) List<Long> selectedTours) {

        // Валидация выбранных клиентов
        if (selectedCustomers == null || selectedCustomers.isEmpty()) {
            result.rejectValue("customers", "NotEmpty.contract.customers", "Должен быть выбран хотя бы один клиент");
        } else {
            Set<Customer> customers = selectedCustomers.stream()
                    .map(customerId -> customerRepository.findById(customerId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            contract.setCustomers(customers);
        }

        // Валидация выбранных туров
        if (selectedTours == null || selectedTours.isEmpty()) {
            result.rejectValue("tours", "NotEmpty.contract.tours", "Должен быть выбран хотя бы один тур");
        } else {
            Set<Tour> tours = selectedTours.stream()
                    .map(tourId -> tourRepository.findById(tourId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            contract.setTours(tours);
        }

        if (result.hasErrors()) {
            setupFormModel(model);

            // Добавляем выбранные ID для повторного отображения формы
            if (selectedCustomers != null) {
                model.addAttribute("selectedCustomerIds", new HashSet<>(selectedCustomers));
            }
            if (selectedTours != null) {
                model.addAttribute("selectedTourIds", new HashSet<>(selectedTours));
            }

            return "contracts/form";
        }

        if (!contractRepository.existsById(id)) {
            return "redirect:/contracts";
        }

        contract.setContractId(id);
        contractRepository.save(contract);
        return "redirect:/contracts";
    }

    @GetMapping("/delete/{id}")
    public String deleteContract(@PathVariable Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
        }
        return "redirect:/contracts";
    }

    private void setupFormModel(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("tours", tourRepository.findAll());
        model.addAttribute("discounts", discountRepository.findAll());
        model.addAttribute("statuses", List.of("DRAFT", "SIGNED", "PAID", "CANCELLED"));

        // Инициализируем атрибуты для выбранных значений, если они еще не установлены
        if (!model.containsAttribute("selectedCustomerIds")) {
            model.addAttribute("selectedCustomerIds", new HashSet<Long>());
        }
        if (!model.containsAttribute("selectedTourIds")) {
            model.addAttribute("selectedTourIds", new HashSet<Long>());
        }
    }
}
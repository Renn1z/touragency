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

import java.util.List;
import java.util.Optional;

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
        model.addAttribute("contract", new Contract());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("tours", tourRepository.findAll());
        model.addAttribute("discounts", discountRepository.findAll());
        model.addAttribute("statuses", List.of("DRAFT", "SIGNED", "PAID", "CANCELLED"));
        return "contracts/form";
    }

    @PostMapping("/create")
    public String createContract(@Valid @ModelAttribute Contract contract,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("tours", tourRepository.findAll());
            model.addAttribute("discounts", discountRepository.findAll());
            model.addAttribute("statuses", List.of("DRAFT", "SIGNED", "PAID", "CANCELLED"));
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
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("tours", tourRepository.findAll());
        model.addAttribute("discounts", discountRepository.findAll());
        model.addAttribute("statuses", List.of("DRAFT", "SIGNED", "PAID", "CANCELLED"));
        return "contracts/form";
    }

    @PostMapping("/edit/{id}")
    public String updateContract(@PathVariable Long id,
                                 @Valid @ModelAttribute Contract contract,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("tours", tourRepository.findAll());
            model.addAttribute("discounts", discountRepository.findAll());
            model.addAttribute("statuses", List.of("DRAFT", "SIGNED", "PAID", "CANCELLED"));
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
}
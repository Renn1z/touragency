package touragency.controllers;

import touragency.models.Tour;
import touragency.models.Route;
import touragency.repositories.TourRepository;
import touragency.repositories.RouteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public String listTours(Model model,
                            @RequestParam(required = false) String search) {
        List<Tour> tours;
        if (search != null && !search.trim().isEmpty()) {
            tours = tourRepository.findByTourNameContainingIgnoreCase(search.trim());
        } else {
            tours = tourRepository.findAll();
        }
        model.addAttribute("tours", tours);
        model.addAttribute("search", search);
        return "tours/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("tour", new Tour());
        model.addAttribute("routes", routeRepository.findAll());
        model.addAttribute("statuses", List.of("PLANNED", "ACTIVE", "COMPLETED", "CANCELLED"));
        return "tours/form";
    }

    @PostMapping("/create")
    public String createTour(@Valid @ModelAttribute Tour tour,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("routes", routeRepository.findAll());
            model.addAttribute("statuses", List.of("PLANNED", "ACTIVE", "COMPLETED", "CANCELLED"));
            return "tours/form";
        }

        tourRepository.save(tour);
        return "redirect:/tours";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isEmpty()) {
            return "redirect:/tours";
        }
        model.addAttribute("tour", tour.get());
        model.addAttribute("routes", routeRepository.findAll());
        model.addAttribute("statuses", List.of("PLANNED", "ACTIVE", "COMPLETED", "CANCELLED"));
        return "tours/form";
    }

    @PostMapping("/edit/{id}")
    public String updateTour(@PathVariable Long id,
                             @Valid @ModelAttribute Tour tour,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("routes", routeRepository.findAll());
            model.addAttribute("statuses", List.of("PLANNED", "ACTIVE", "COMPLETED", "CANCELLED"));
            return "tours/form";
        }

        if (!tourRepository.existsById(id)) {
            return "redirect:/tours";
        }

        tour.setTourId(id);
        tourRepository.save(tour);
        return "redirect:/tours";
    }

    @GetMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        if (tourRepository.existsById(id)) {
            tourRepository.deleteById(id);
        }
        return "redirect:/tours";
    }
}
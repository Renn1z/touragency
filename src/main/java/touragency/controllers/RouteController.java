package touragency.controllers;

import touragency.models.Route;
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
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public String listRoutes(Model model,
                             @RequestParam(required = false) String search) {
        List<Route> routes;
        if (search != null && !search.trim().isEmpty()) {
            routes = routeRepository.findByRouteNameContainingIgnoreCase(search.trim());
        } else {
            routes = routeRepository.findAll();
        }
        model.addAttribute("routes", routes);
        model.addAttribute("search", search);
        return "routes/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("route", new Route());
        return "routes/form";
    }

    @PostMapping("/create")
    public String createRoute(@Valid @ModelAttribute Route route,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "routes/form";
        }

        routeRepository.save(route);
        return "redirect:/routes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isEmpty()) {
            return "redirect:/routes";
        }
        model.addAttribute("route", route.get());
        return "routes/form";
    }

    @PostMapping("/edit/{id}")
    public String updateRoute(@PathVariable Long id,
                              @Valid @ModelAttribute Route route,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "routes/form";
        }

        if (!routeRepository.existsById(id)) {
            return "redirect:/routes";
        }

        route.setRouteId(id);
        routeRepository.save(route);
        return "redirect:/routes";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
        }
        return "redirect:/routes";
    }
}
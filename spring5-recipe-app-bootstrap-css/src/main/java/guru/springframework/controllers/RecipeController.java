package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by jt on 6/19/17.
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){

        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateById(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){

        RecipeCommand recipeCommand1 = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + recipeCommand1.getId() + "/show/";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id, Model model){
        log.debug("Deleted Id ==== " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}

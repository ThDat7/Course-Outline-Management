package com.dat.controllers;

import com.dat.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class EntityListController<T, K extends Serializable> {

    private String rootEndpoint;
    private String entityName;
    private String entityLabelName;
    private List<String> labels;
    private Environment env;
    private BaseService<T, K> service;

    public EntityListController(String entityName, String rootEndpoint, String entityLabelName, List<String> labels, Environment env, BaseService service) {
        this.entityName = entityName;
        this.rootEndpoint = rootEndpoint;
        this.entityLabelName = entityLabelName;
        this.labels = labels;
        this.env = env;
        this.service = service;
    }

    protected abstract List<List> getRecords(Map<String, String> params);

    @GetMapping
    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("rootEndpoint", rootEndpoint);
        model.addAttribute("entityLabelName", entityLabelName);
        model.addAttribute("labels", labels);
        model.addAttribute("records", getRecords(params));

        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        long count = service.count();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));

        return "list-base";
    }

    protected abstract void addAtributes(Model model);

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable K id) {
        addAtributes(model);
        T t = service.getById(id);
        model.addAttribute("rootEndpoint", rootEndpoint);
        model.addAttribute("entityName", entityName);
        model.addAttribute("entityLabelName", entityLabelName);
        model.addAttribute(entityName, t);
        return String.format("%s-detail", entityName);
    }

    @GetMapping("/create")
    public String create(Model model) {
        addAtributes(model);
        model.addAttribute("rootEndpoint", rootEndpoint);
        model.addAttribute("entityName", entityName);
        model.addAttribute("entityLabelName", entityLabelName);
        model.addAttribute(entityName, (T) new Object());
        return String.format("%s-detail", entityName);
    }

    @PostMapping
    public String add(@ModelAttribute T t) {
        if (service.addOrUpdate(t) == true)
            return String.format("redirect:/%ss/", entityName);

        return String.format("%s-detail", entityName);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable K id) {
        service.delete(id);
        return String.format("redirect:/%ss/", entityName);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    protected class SelectFormItem {
        private K id;
        private String name;
    }
}

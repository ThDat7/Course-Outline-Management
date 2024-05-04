package com.dat.controllers;

import com.dat.service.BaseService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public abstract class EntityListController {

    private String entityName;
    private String entityLabelName;
    private List<String> labels;
    private Environment env;
    private BaseService service;

    public EntityListController(String entityName, String entityLabelName, List<String> labels, Environment env, BaseService service) {
        this.entityName = entityName;
        this.entityLabelName = entityLabelName;
        this.labels = labels;
        this.env = env;
        this.service = service;
    }

    protected abstract List<List> getRecords(Map<String, String> params);

    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("entityName", entityName);
        model.addAttribute("entityLabelName", entityLabelName);
        model.addAttribute("labels", labels);
        model.addAttribute("records", getRecords(params));

        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        long count = service.count();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));

        return "list-base";
    }


}

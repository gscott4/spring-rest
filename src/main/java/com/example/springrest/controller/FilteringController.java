package com.example.springrest.controller;

import com.example.springrest.entity.FilteringBean;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Dynamic Filtering
 */
@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveFilteringBean() {
        FilteringBean filteringBean = new FilteringBean("value1", "value2", "value3");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
        FilterProvider filters = new SimpleFilterProvider().addFilter("FilteringBeanFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(filteringBean);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfFilteringBean() {
        List<FilteringBean> filteringBeanList = Arrays.asList(
                new FilteringBean("value1", "value2", "value3"),
                new FilteringBean("value11", "value22", "value33")
        );

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("FilteringBeanFilter", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(filteringBeanList);
        mapping.setFilters(filters);

        return mapping;
    }
}

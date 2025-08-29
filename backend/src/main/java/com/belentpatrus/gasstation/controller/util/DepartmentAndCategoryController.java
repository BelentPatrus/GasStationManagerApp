package com.belentpatrus.gasstation.controller.util;

import com.belentpatrus.gasstation.model.enums.Department;
import com.belentpatrus.gasstation.model.enums.ProductCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dac")
public class DepartmentAndCategoryController {

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return Arrays.asList(Department.values());
    }

    @GetMapping("/categories")
    public List<ProductCategory> getCategories() {
        return Arrays.asList(ProductCategory.values());
    }
}

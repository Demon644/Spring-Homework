package ua.logos.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ua.logos.repository.CategoryRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCategoryExistsValidator implements ConstraintValidator<CheckCategoryExists, String> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(categoryRepository.findByCategory(value) == null) {
            return true;
        }
        return false;
    }
    
}

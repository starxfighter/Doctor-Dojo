package com.doctordojo.doctordojo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.doctordojo.doctordojo.models.User;

@Component
public class LogAndRegValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	@Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        
        if (!user.getPasswordConf().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConf", "Match");
        }         
    }

}

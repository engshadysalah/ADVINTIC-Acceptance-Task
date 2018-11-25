/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vaidators;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pop Shady
 */
//id of Validator
@FacesValidator(value = "EmailValidator")
public class CustomEmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String email = (String) value;

        //Check , java Code
        if (email == null || email.length() == 0 || !email.contains("@") || !email.contains(".com")) {
            //  FacesMessage mess=new FacesMessage("In valide");
            // throw new ValidatorException(mess);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            try {
                response.sendRedirect("assests/validatedPages/RegisterValidate/EmaiValidator.html");
            } catch (IOException ex) {
                Logger.getLogger(CustomEmailValidator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}

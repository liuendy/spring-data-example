package com.jvyang.core.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jvyang.core.controllers.BaseController;
import com.jvyang.core.model.web.ChangePasswordModel;
import com.jvyang.core.service.ChangePasswordService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/password", method = RequestMethod.POST)
public class ChangePasswordController extends BaseController
{
  @Autowired ChangePasswordService service;

  @RequestMapping
  public ResponseEntity<?> index(@RequestBody ChangePasswordModel model)
  {
    String oldPassword = model.getOldPassword();
    String confirmPassword = model.getConfirmPassword();
    String newPassword = model.getNewPassword();

    if (confirmPassword.equals(newPassword))
    {
      if (service.changePassword(getUser(), oldPassword, newPassword))
      {
        return ResponseEntity.ok().body("{}");
      }
    }

    return ResponseEntity.badRequest().body("{}");
  }
}

package com.jvyang.core.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jvyang.core.controllers.BaseController;
import com.jvyang.core.model.Profile;
import com.jvyang.core.service.ProfileService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/profiles2", method = RequestMethod.GET)
public class ProfileRetrievalController extends BaseController
{
  @Autowired ProfileService profileService;

  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> index()
  {
    List<Profile> list = profileService.getProfiles(true);
    return ResponseEntity.ok().body(list);
  }
  
  @RequestMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> id(@PathVariable("id") long id)
  {
    Profile profile = profileService.getProfile(id);
    return ResponseEntity.ok().body(profile);
  }
}
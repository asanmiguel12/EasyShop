package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.configurations.UserIn;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@CrossOrigin
public class ProfileController {

    private ProfileDao profileDao;
    private UserIn userIn;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserIn userIn) {
        this.profileDao = profileDao;
        this.userIn = userIn;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal) {
        int userId = userIn.getUserId(principal);

        Profile profile = profileDao.getById(userId);

        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(Principal principal, @RequestBody Profile profile) {
        int userId = userIn.getUserId(principal);

        try {
            profileDao.update(userId, profile);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
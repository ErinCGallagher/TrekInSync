package com.example.ering.trekinsync.interfaces;

import com.example.ering.trekinsync.models.User;

/**
 * Create an interface for view related activities
 */
public interface LandingView {
    void launchProfilePage(User user);
    void launchEditProfilePage();
    void launchCreateProfilePage();
}

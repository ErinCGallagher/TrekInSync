package com.trekinsync.ering.trekinsync.interfaces;

import com.trekinsync.ering.trekinsync.models.User;

/**
 * Create an interface for view related activities
 */
public interface LandingView {
    void reloadData();
    void launchProfilePage(User user);
    void launchEditProfilePage(User user);
    void launchCreateProfilePage();
    void launchAppDetailPages(boolean isOnboarding);
}

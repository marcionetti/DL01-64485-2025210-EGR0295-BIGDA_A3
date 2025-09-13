package com.meuprojeto.view;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class SimpleMenuListener implements MenuListener {
    private final Runnable action;

    public SimpleMenuListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        action.run();
    }

    @Override
    public void menuDeselected(MenuEvent e) {}

    @Override
    public void menuCanceled(MenuEvent e) {}
}

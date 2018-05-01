package ir.filter;

import java.util.ArrayList;

public abstract class Filter
{
    private boolean enabled;

    Filter()
    {
        enabled = true;
    }

    public void enable() { enabled = true; }

    public void disable() { enabled = false; }

    public boolean isEnabled() { return enabled; }

    public abstract ArrayList<String> execute(ArrayList<String> tokens);
}

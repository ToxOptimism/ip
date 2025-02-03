package aurora.io;

import aurora.Aurora;

/**
 * Represents a stubbed Ui class for testing purposes.
 */
public class UiStub extends Ui {

    // The singleton instance
    private static UiStub singleton = null;
    private static boolean isSingletonSet = false;

    // The reference to Aurora application
    private Aurora aurora;

    public static UiStub getSingleton() {
        return singleton;
    }

    public static void setUiSingleton(UiStub ui) {
        if (isSingletonSet) {
            return;
        }
        singleton = ui;
        isSingletonSet = true;
    }

    public void setAurora(Aurora aurora) {
        this.aurora = aurora;
    }

    public void printMsg(String msg) {
        return;
    }

    public void close() {
        return;
    }

}

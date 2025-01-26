package aurora.io;

import java.util.List;

import aurora.exception.AuroraException;

public class StorageStub extends Storage {
    private static final StorageStub SINGLETON = new StorageStub();

    protected StorageStub() {
    }

    public static StorageStub of() {
        return SINGLETON;
    }

    @Override
    public void generateTaskListFile() throws AuroraException {
        return;
    }

    @Override
    public List<String> loadTaskListData() throws AuroraException {
        return null;
    }

    @Override
    public void overwriteTaskListFile(List<String> lines) throws AuroraException {
        return;
    }

    @Override
    public void appendTaskListFile(List<String> lines) throws AuroraException {
        return;
    }
}

package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class BranchOperation extends VcsOperation {
    /**
     * Branch operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
	public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
		super(type, operationArgs);
	}

	/**
     * Executes the branch operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
	@Override
	public int execute(Vcs vcs) {
		for (Branch it : vcs.getBranches()) {
			if (it.getName().equals(this.operationArgs.get(2))) {
				return ErrorCodeManager.VCS_BAD_CMD_CODE;
			}
		}
		vcs.cloneActiveSnapshot(this.operationArgs.get(2));
		return ErrorCodeManager.OK;
	}
}

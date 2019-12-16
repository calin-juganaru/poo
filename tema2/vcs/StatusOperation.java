package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class StatusOperation extends VcsOperation {
	/**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
	public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
		super(type, operationArgs);
	}

	/**
     * Executes the status operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
	@Override
	public int execute(Vcs vcs) {
		vcs.getOutputWriter().write("On branch: "
				+ vcs.getCurrentBranch().getName() + "\n");
		vcs.getOutputWriter().write("Staged changes:\n");
		for (String change : vcs.getChanges()) {
			vcs.getOutputWriter().write(change);
		}

		return ErrorCodeManager.OK;
	}
}

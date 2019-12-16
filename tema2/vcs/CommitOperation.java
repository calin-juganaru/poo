package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class CommitOperation extends VcsOperation {
	private static final int FOUR = 4;

	/**
     * Commit operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
	public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
		super(type, operationArgs);
	}

	/**
     * Executes the commit operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
	@Override
	public int execute(Vcs vcs) {
		if (this.operationArgs.get(2).equals("-m")) {
			if (vcs.emptyStaging()) {
				return ErrorCodeManager.VCS_BAD_CMD_CODE;
			}

			String message = new String(this.operationArgs.get(THREE));
			for (int i = FOUR; i < this.operationArgs.size(); ++i) {
				message += " " + this.operationArgs.get(i);
			}

			vcs.makeNewCommit(message);
			return ErrorCodeManager.OK;
		}
		return ErrorCodeManager.VCS_BAD_CMD_CODE;
	}
}

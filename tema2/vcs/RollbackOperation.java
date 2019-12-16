package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class RollbackOperation extends VcsOperation {
	/**
     * Rollback operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
	public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
		super(type, operationArgs);
	}

	/**
     * Executes the rollback operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
	@Override
	public int execute(Vcs vcs) {
		vcs.rollback();
		return ErrorCodeManager.OK;
	}

}

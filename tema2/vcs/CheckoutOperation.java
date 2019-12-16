package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class CheckoutOperation extends VcsOperation {
	/**
     * Checkout operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
	public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
		super(type, operationArgs);
	}

	/**
     * Executes the checkout operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
	@Override
	public int execute(Vcs vcs) {
		if (!vcs.emptyStaging()) {
			return ErrorCodeManager.VCS_STAGED_OP_CODE;
		}

		if (operationArgs.get(2).equals("-c")) {
			int commitID = Integer.parseInt(operationArgs.get(THREE));
			if (!vcs.moveHEAD(commitID)) {
				return ErrorCodeManager.VCS_BAD_PATH_CODE;
			}
		} else {
			String branchName = operationArgs.get(2);
			if (!vcs.moveHEAD(branchName)) {
				return ErrorCodeManager.VCS_BAD_CMD_CODE;
			}
		}

		return ErrorCodeManager.OK;
	}
}

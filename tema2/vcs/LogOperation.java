package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;
import utils.OutputWriter;

public final class LogOperation extends VcsOperation {
	/**
     * Log operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
	public LogOperation(OperationType type, ArrayList<String> operationArgs) {
		super(type, operationArgs);
	}

	/**
     * Executes the log operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
	@Override
	public int execute(Vcs vcs) {
		ArrayList<Commit> commits = vcs.getCurrentBranch().getCommits();
		OutputWriter outputWriter = vcs.getOutputWriter();
		Commit commit;

		for (int i = 0; i < commits.size() - 1; ++i) {
			commit = commits.get(i);
			outputWriter.write("Commit id: " + commit.getID()
			+ "\nMessage: " + commit.getMessage() + "\n\n");
		}

		commit = vcs.getCurrentBranch().getLastCommit();
		outputWriter.write("Commit id: " + commit.getID()
		+ "\nMessage: " + commit.getMessage() + "\n");

		return ErrorCodeManager.OK;
	}

}

package vcs;

import java.util.ArrayList;
import java.util.LinkedList;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;

public final class Vcs implements Visitor {
    private static final int THREE = 3;
	private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private LinkedList<Branch> branches;
    private Branch currentBranch;
    private Commit head;
    private int lastCommitID;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initializations.
     */
    public void init() {
    	lastCommitID = THREE;
    	this.activeSnapshot = new FileSystemSnapshot(outputWriter);
        this.currentBranch = new Branch(lastCommitID, "master", this.activeSnapshot);
        this.branches = new LinkedList<Branch>();
        this.branches.add(currentBranch);
        this.head = this.currentBranch.getLastCommit();
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
    	return vcsOperation.execute(this);
    }

    /**
     * Gets the current branch.
     * @return the current branch.
     */
    public Branch getCurrentBranch() {
    	return this.currentBranch;
    }

    /**
     * Gets the list of branches.
     * @return the list of branches.
     */
    public LinkedList<Branch> getBranches() {
        return this.branches;
    }

    /**
     * Gets the list of changes.
     * @return the list of changes.
     */
    public LinkedList<String> getChanges() {
        return this.activeSnapshot.getChanges();
    }

    /**
     * Gets the output writer.
     * @return the output writer.
     */
    public OutputWriter getOutputWriter() {
    	return this.outputWriter;
    }

    /**
     * Clones the active filesystem snapshot.
     * @param newBranchName the name of the new branch.
     */
    public void cloneActiveSnapshot(String newBranchName) {
    	lastCommitID += 2;
    	this.branches.add(new Branch(lastCommitID, newBranchName,
        		this.activeSnapshot.cloneFileSystem()));
    }

    /**
     * Creates a new commit in current branch.
     * @param message the new commit's message.
     */
    public void makeNewCommit(String message) {
    	lastCommitID += 2;
    	this.currentBranch.addCommit(lastCommitID, message, this.activeSnapshot);
    	this.activeSnapshot = this.activeSnapshot.cloneFileSystem();
    }

    /**
     * Restores the active filesystem snapshot to last commit version.
     */
    public void rollback() {
    	this.activeSnapshot = this.currentBranch.getLastCommit().getSnapshot();
    }

    /**
     * Moves HEAD pointer to the commit with commitID.
     * @param commitID
     * @return true if the operation succeeded, false otherwise.
     */
    public boolean moveHEAD(int commitID) {
    	for (Commit commit : this.currentBranch.getCommits()) {
    		if (commit.getID() == commitID) {
    			this.head = commit;

    			ArrayList<Commit> commits = this.currentBranch.getCommits();
    			while (!commits.get(commits.size() - 1).equals(commit)) {
    				commits.remove(commits.size() - 1);
    			}

    			this.activeSnapshot = new FileSystemSnapshot(outputWriter);

    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Moves HEAD pointer to the last commit of the branch with branchName.
     * @param branchName
     * @return true if the operation succeeded, false otherwise.
     */
    public boolean moveHEAD(String branchName) {
    	for (Branch branch : this.branches) {
    		if (branch.getName().equals(branchName)) {
    			this.head = branch.getLastCommit();
    			this.currentBranch = branch;
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Checks whether the current staging is empty or not.
     */
    public boolean emptyStaging() {
    	return this.activeSnapshot.getChanges().isEmpty();
    }
}

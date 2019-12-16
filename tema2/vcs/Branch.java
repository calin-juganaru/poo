package vcs;

import java.util.ArrayList;

import filesystem.FileSystemSnapshot;

public final class Branch {
	private String name;
	private ArrayList<Commit> commits;

	/**
	 * Branch constructor.
	 * @param commitID the first commit's ID.
	 * @param name the new branch name.
	 * @param snapshot the filesystem snapshot.
	 */
	public Branch(int commitID, String name, FileSystemSnapshot snapshot) {
		this.name = new String(name);
		this.commits = new ArrayList<Commit>();
		this.commits.add(new Commit(commitID, "First commit", snapshot));
	}

	/**
	 * Gets the branch name.
	 * @return the branch name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Adds a new commit to the commits list.
	 * @param commitID the new commit ID.
	 * @param message the new commit message.
	 * @param snapshot the new commit filesystem snapshot.
	 */
	public void addCommit(int commitID, String message, FileSystemSnapshot snapshot) {
		this.commits.add(new Commit(commitID, message, snapshot));
	}

	/**
	 * Gets the last commit.
	 * @return the last commit.
	 */
	public Commit getLastCommit() {
		return this.commits.get(this.commits.size() - 1);
	}

	/**
	 * Gets the commits list.
	 * @return the commits list.
	 */
	public ArrayList<Commit> getCommits() {
		return this.commits;
	}
}

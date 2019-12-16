package vcs;

import filesystem.FileSystemSnapshot;

public final class Commit {
	private int commitID;
	private String message;
	private FileSystemSnapshot snapshot;

	/**
	 * Commit constructor.
	 * @param commitID the new commit ID.
	 * @param message the new commit message.
	 * @param snapshot the new commit filesystem snapshot.
	 */
	public Commit(int commitID, String message, FileSystemSnapshot snapshot) {
		this.commitID = commitID;
		this.message = new String(message);
		this.setSnapshot(snapshot);
	}

	/**
	 * Gets the commit's message.
	 * @return the message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the commit's filesystem snapshot.
	 * @return the snapshot.
	 */
	public FileSystemSnapshot getSnapshot() {
		return snapshot;
	}

	/**
	 * Sets the filesystem snapshot.
	 * @param snapshot the new filesystem snapshot.
	 */
	public void setSnapshot(FileSystemSnapshot snapshot) {
		this.snapshot = snapshot;
	}

	/**
	 * Gets the commit ID.
	 * @return the ID.
	 */
	public int getID() {
		return this.commitID;
	}
}

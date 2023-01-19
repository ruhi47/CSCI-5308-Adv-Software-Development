package edu.dal.eauction.emailSender.email;

import java.io.File;

public class Attachment {

	public Attachment() {
		super();
	}

	private String filePath;
	private String fileName;

//	Verifies whether file exists and initializes the properties
	public boolean setAttachmentPath(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;
		boolean added = false;
		System.out.println("The full file path is {}"+ getFullFilePath());
		if (verifyAttachment()) {
			this.filePath = filePath;
			this.fileName = fileName;
			added = true;
		}
		return added;
	}

	private String getFullFilePath() {
		return filePath + fileName;
	}

	private boolean verifyAttachment() {		
		File file = new File(getFullFilePath());
		return file.exists();
	}
	
	public File getAttachment() {
		return new File(getFullFilePath());
	}

}


package General.EZModel;
import java.sql.Date;

public class UserModel {

	private String FileName;
	private String fdescription;
	private String fileSize;
	private String emailid;
	private Date createdtime;
	private Date updatedtime;
	
	public String getEmailId() {
        return emailid;
    }
	public void setEmailId(String emailid) {
        this.emailid = emailid;
    }
	public String getFileName() {
        return filename;
    }
	public void setFileName(String fileName) {
        this.filename = fileName;
    }
	public String getDescription() {
        return description;
    }
	public void setDescription(String description) {
        this.description = description;
    }
	public String getFileSize() {
        return fileSize;
    }
	public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
	public Date getCreatedTime() {
        return createdtime;
    }
	public void setCreatedTime(Date createdtime) {
        this.createdtime = createdtime;
    }
	public Date getUpdatedTime() {
        return updatedtime;
    }
	public void setUpdatedTime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

	
}

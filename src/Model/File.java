package Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Files")
public class File implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer fileId;
	private String fileName;
	private String md5hash;
	
	private String outbit;

	private Integer size;
	@Column(name = "outbit", nullable = true, length = 9)

	public String getOutbit() {
		return outbit;
	}
	public void setOutbit(String outbit) {
		this.outbit = outbit;
	}

	private Integer archivedSize;
	private Set<DbTree> stockDailyRecords = new HashSet<DbTree>(
			0);
	public File(){}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "file")

	public Set<DbTree> getStockDailyRecords() {
		return stockDailyRecords;
	}

	public void setStockDailyRecords(Set<DbTree> stockDailyRecords) {
		this.stockDailyRecords = stockDailyRecords;
	}

	@Column(name = "size", nullable = true, length = 20)
	public Integer getSize() {
		return size;
	}

	@Column(name = "md5hash", nullable = true, length = 150)
	public String getMd5hash() {
		return md5hash;
	}

	public void setMd5hash(String md5hash) {
		this.md5hash = md5hash;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Column(name = "archivedsize", nullable = true, length = 20)
	public Integer getArchivedSize() {
		return archivedSize;
	}

	public void setArchivedSize(Integer archivedSize) {
		this.archivedSize = archivedSize;
	}

	@Id

	@Column(name = "FILE_ID", unique = true, nullable = false, precision = 5, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "FILENAME", nullable = false, length = 157)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

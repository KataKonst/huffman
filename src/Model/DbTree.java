package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Tree")
public class DbTree implements java.io.Serializable {
	
	private File file;
	private Integer symbol;
	private Integer frequency;
	
	private Integer nodeId;

	public DbTree(){};
	
	@Id
	@Column(name = "nodeId", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fileId", nullable = false)
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
	@Column(name = "symbol", nullable = true, length = 20)

	public Integer getSymbol() {
		return symbol;
	}
	
	

	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}
	@Column(name = "frequency", nullable = true, length = 20)

	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	


}

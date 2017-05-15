package org.web.cape.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "example_demo")
@NamedQuery(name = "DataTableDemo.findAll", query = "SELECT d FROM DataTableDemo d")
public class DataTableDemo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String theid;

	@NotBlank(message = "测试编码不能为空!")
	private String code;

	private String memo;

	private String name;

	private String isdefault;

	public DataTableDemo() {
	}

	public String getTheid() {
		return theid;
	}

	public void setTheid(String theid) {
		this.theid = theid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return this.name;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public void setName(String name) {
		this.name = name;
	}

}
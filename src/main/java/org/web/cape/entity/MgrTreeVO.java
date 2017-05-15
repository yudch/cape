package org.web.cape.entity;

public class MgrTreeVO {
	
	private Long id;
	private Long pId;
	private Long urId;//用户角色id
	private String name;
	private boolean open = false;
	private boolean checked = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Long getUrId() {
		return urId;
	}
	public void setUrId(Long urId) {
		this.urId = urId;
	}
	
	
	
	
	
}
